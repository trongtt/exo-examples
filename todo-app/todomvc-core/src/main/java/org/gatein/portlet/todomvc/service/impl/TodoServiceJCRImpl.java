/*
 * Copyright (C) 2012 eXo Platform SAS.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.gatein.portlet.todomvc.service.impl;

import java.util.List;
import javax.jcr.Node;
import org.gatein.portlet.todomvc.model.jcr.TodoNode;
import org.gatein.portlet.todomvc.model.jcr.TodoNodeList;
import org.gatein.portlet.todomvc.service.TodoService;
import org.gatein.portlet.todomvc.service.impl.jcr.JCRHelper;
import org.json.JSONArray;
import org.json.JSONObject;

public class TodoServiceJCRImpl implements TodoService {
    public static final String JCR_PROPERTY_JOB = "job";
    public static final String JCR_PROPERTY_PRIORITY = "priority";
    public static final String JCR_PROPERTY_COMPLETED = "completed";

    public static final String JSON_KEY_ID = "id";
    public static final String JSON_KEY_COMPLETED = "completed";
    public static final String JSON_KEY_DISPLAY = "display";
    public static final String JSON_KEY_EDITING = "editing";
    public static final String JSON_KEY_JOB = "job";
    public static final String JSON_KEY_PRIORITY = "priority";

    private JCRHelper jcrHelper;

    public TodoServiceJCRImpl(JCRHelper jcrHelper) {
        this.jcrHelper = jcrHelper;
    }

    public TodoNodeList getTodoNodeList(String todoListId) throws Exception {
        return this.jcrHelper.getTodoNodeList(todoListId);
    }

    @Override
    public void saveListTodos(String todoListId, JSONArray jsonArray) throws Exception {
        int size = jsonArray.length();
        for(int i = 0; i < size; i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            this.saveTodo(todoListId, jsonObject);
        }
    }

    @Override
    public void saveTodo(String todoListId, JSONObject jsonObject) throws Exception {
        TodoNodeList todoList = this.getTodoNodeList(todoListId);


        String job = jsonObject.getString(JSON_KEY_JOB);
        int priority = jsonObject.getInt(JSON_KEY_PRIORITY);
        boolean completed = jsonObject.getBoolean(JSON_KEY_COMPLETED);

        TodoNode todo = todoList.newTodo();
        todo.setCompleted(completed);
        todo.setPriority(priority);
        todo.setJob(job);

        todoList.save();
        todo.save();
    }

    @Override
    public JSONArray getListTodos(String todoListId) throws Exception {
        TodoNodeList todoNodeList = this.getTodoNodeList(todoListId);

        JSONArray array = new JSONArray();

        List<TodoNode> todoNodes = todoNodeList.getTodos();
        for(TodoNode todo : todoNodes) {
            JSONObject object = new JSONObject();

            object.put(JSON_KEY_ID, todo.getName());
            object.put(JSON_KEY_PRIORITY, todo.getPriority());
            object.put(JSON_KEY_COMPLETED, todo.isCompleted());
            object.put(JSON_KEY_JOB, todo.getJob());

            array.put(object);
        }

        return array;
    }

    @Override
    public JSONObject getTodo(String todoListId, int todoId) throws Exception {
        TodoNodeList todoNodeList = this.getTodoNodeList(todoListId);

        TodoNode todo = todoNodeList.getTodo(String.valueOf(todoId));

        JSONObject object = new JSONObject();
        object.put(JSON_KEY_ID, Integer.valueOf(todo.getName()));
        object.put(JSON_KEY_PRIORITY, todo.getPriority());
        object.put(JSON_KEY_COMPLETED, todo.isCompleted());
        object.put(JSON_KEY_JOB, todo.getJob());

        return object;
    }

    @Override
    public void updateListTodos(String todoListId, JSONArray jsonArray) throws Exception {
        int size = jsonArray.length();
        for(int i = 0; i < size; i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            this.updateTodo(todoListId, jsonObject);
        }
    }

    @Override
    public void updateTodo(String todoListId, JSONObject jsonObject) throws Exception {

        int id = jsonObject.getInt(JSON_KEY_ID);

        TodoNodeList todoNodeList = this.getTodoNodeList(todoListId);
        TodoNode todo = todoNodeList.getTodo(String.valueOf(id));


        String job = jsonObject.getString(JSON_KEY_JOB);
        int priority = jsonObject.getInt(JSON_KEY_PRIORITY);
        boolean completed = jsonObject.getBoolean(JSON_KEY_COMPLETED);
        todo.setPriority(priority);
        todo.setCompleted(completed);
        todo.setJob(job);

        todoNodeList.save();
    }

    @Override
    public void deleteListTodos(String todoListId, JSONArray jsonArray) throws Exception {
        int size = jsonArray.length();
        for(int i = 0; i < size; i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            this.deleteTodo(todoListId, jsonObject);
        }
    }

    @Override
    public void deleteTodo(String todoListId, JSONObject jsonObject) throws Exception {
        TodoNodeList todoNodeList = this.getTodoNodeList(todoListId);

        int id = jsonObject.getInt(JSON_KEY_ID);
        TodoNode todo = todoNodeList.getTodo(String.valueOf(id));
        todo.remove();

        todoNodeList.save();
    }
}
