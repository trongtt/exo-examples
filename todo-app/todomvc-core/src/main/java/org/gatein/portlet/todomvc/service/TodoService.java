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

package org.gatein.portlet.todomvc.service;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author <a href="trongson.tran1228@gmail.com">Son Tran Trong</a>
 * @date 10/24/2012
 */
public interface TodoService
{

   public void saveListTodos(String todoListId, JSONArray jsonArray) throws Exception;

   public void saveTodo(String todoListId, JSONObject jsonObject) throws Exception;

   public JSONArray getListTodos(String todoListId) throws Exception;

   public JSONObject getTodo(String todoListId, int todoId) throws Exception;

   public void updateListTodos(String todoListId, JSONArray jsonArray) throws Exception;

   public void updateTodo(String todoListId, JSONObject jsonObject) throws Exception;

   public void deleteListTodos(String todoListId, JSONArray jsonArray) throws Exception;

   public void deleteTodo(String todoListId, JSONObject jsonObject) throws Exception;
}
