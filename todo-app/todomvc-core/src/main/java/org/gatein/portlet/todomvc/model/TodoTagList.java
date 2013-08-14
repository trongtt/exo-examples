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

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="trongson.tran1228@gmail.com">Son Tran Trong</a>
 * @date 10/24/2012
 */
public class TodoTagList
{
   private String id;

   private Map<Integer, TodoTag> todoTagsMap;

   public TodoTagList()
   {
      this("", new HashMap<Integer, TodoTag>());
   }

   public TodoTagList(String id, Map<Integer, TodoTag> todoTagsMap)
   {
      this.id = id;
      this.todoTagsMap = todoTagsMap;
   }

   public String getId()
   {
      return id;
   }

   public void setId(String id)
   {
      this.id = id;
   }

   public Map<Integer, TodoTag> getTodoTagsMap()
   {
      return todoTagsMap;
   }

   public void setTodoTagsMap(Map<Integer, TodoTag> todoTagsMap)
   {
      this.todoTagsMap = todoTagsMap;
   }

}
