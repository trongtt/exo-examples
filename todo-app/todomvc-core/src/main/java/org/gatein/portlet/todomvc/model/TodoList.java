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

package org.gatein.portlet.todomvc.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author <a href="trongson.tran1228@gmail.com">Son Tran Trong</a>
 * @date 10/24/2012
 */
public class TodoList
{
   private String id;

   private Map<Integer, Todo> todosMap;

   public TodoList(String id)
   {
      this(id, new HashMap<Integer, Todo>());
   }

   public TodoList(String id, Map<Integer, Todo> todosList)
   {
      this.id = id;
      this.todosMap = todosList;
   }

   public String getId()
   {
      return id;
   }

   public void setId(String id)
   {
      this.id = id;
   }

   public Map<Integer, Todo> getTodosMap()
   {
      return todosMap;
   }

   public void setTodosMap(Map<Integer, Todo> todosMap)
   {
      this.todosMap = todosMap;
   }

   public void saveListTodos(JSONArray jsonArray) throws Exception
   {
      int jsonLength = jsonArray.length();
      for (int i = 0; i < jsonLength; i++)
      {
         JSONObject jsonObj = (JSONObject)jsonArray.get(i);
         saveTodo(jsonObj);
      }
   }

   public void saveTodo(JSONObject jsonObject) throws Exception
   {
      Iterator<Integer> todosId = todosMap.keySet().iterator();
      int lastId = 0;
      while (todosId.hasNext())
      {
         int temp = todosId.next().intValue();
         if (lastId <= temp)
         {
            lastId = temp;
         }
      }
      lastId += 1;

      if (jsonObject != null)
      {
         jsonObject.put("id", lastId);
         todosMap.put(new Integer(lastId), new Todo(jsonObject));
      }
   }

   public JSONArray getListTodos() throws Exception
   {
      Iterator<Integer> todosId = todosMap.keySet().iterator();
      JSONArray jsonArray = new JSONArray();
      while (todosId.hasNext())
      {
         Integer id = todosId.next();
         jsonArray.put(getTodo(id));
      }

      return jsonArray;
   }

   public JSONObject getTodo(Integer id) throws Exception
   {
      Todo todo = todosMap.get(id);
      JSONObject jsonObject = new JSONObject(todo.toJSONString());
      return jsonObject;
   }

   public void updateListTodos(JSONArray jsonArray) throws Exception
   {
      int jsonLength = jsonArray.length();
      for (int i = 0; i < jsonLength; i++)
      {
         JSONObject jsonObj = (JSONObject)jsonArray.get(i);
         updateTodo(jsonObj);
      }
   }

   public void updateTodo(JSONObject jsonObject) throws Exception
   {
      Todo todo = todosMap.get(new Integer(jsonObject.getInt("id")));
      todo.buildTodo(jsonObject);
   }

   public void deleteListTodos(JSONArray jsonArray) throws Exception
   {
      int jsonLength = jsonArray.length();
      for (int i = 0; i < jsonLength; i++)
      {
         JSONObject jsonObj = (JSONObject)jsonArray.get(i);
         deleteTodo(jsonObj);
      }
   }

   public void deleteTodo(JSONObject jsonObject) throws Exception
   {
      todosMap.remove(new Integer(jsonObject.getInt("id")));
   }
}
