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

import org.json.JSONObject;
import org.json.JSONString;

import java.util.Date;

/**
 * @author <a href="trongson.tran1228@gmail.com">Son Tran Trong</a>
 * @date 10/24/2012
 */
public class Todo implements JSONString
{
   public static final int NO_PRIORITY = 0;

   public static final int LOW_PRIORITY = 3;

   public static final int MEDIUM_PRIORITY = 2;

   public static final int HIGH_PRIORITY = 1;

   public static final String INVALID_PRIORITY = "invalid priority";

   private int id;

   private String job;

   private String content;

   private Date dueDate;

   private boolean isCompleted;

   private int priority;

   public Todo(JSONObject json) throws Exception
   {
      setId(json.getInt("id"));
      buildTodo(json);
   }

   public int getId()
   {
      return id;
   }

   public void setId(int id)
   {
      this.id = id;
   }

   public String getJob()
   {
      return job;
   }

   public void setJob(String job)
   {
      this.job = job;
   }

   public String getContent()
   {
      return content;
   }

   public void setContent(String content)
   {
      this.content = content;
   }

   public boolean isCompleted()
   {
      return isCompleted;
   }

   public void setCompleted(boolean isCompleted)
   {
      this.isCompleted = isCompleted;
   }

   public Date getDueDate()
   {
      return dueDate;
   }

   public void setDueDate(Date dueDate)
   {
      this.dueDate = dueDate;
   }

   public int getPriority()
   {
      return priority;
   }

   public void setPriority(int priority)
   {
      this.priority = priority;
   }

   public void buildTodo(JSONObject json) throws Exception
   {
      if (json.getInt("priority") > LOW_PRIORITY || json.getInt("priority") < NO_PRIORITY)
         throw new Exception("invalid priority");

      if (json.getString("job") == null || json.getString("job").length() == 0)
         throw new Exception("job cannot be null");
      
      setJob(json.getString("job"));
      setCompleted(json.getBoolean("completed"));
      setPriority(json.getInt("priority"));
   }

   @Override
   public String toJSONString()
   {
      StringBuffer sb = new StringBuffer();
      sb.append("{")
         .append(JSONObject.quote("id")).append(":").append(id)
         .append(",").append(JSONObject.quote("job")).append(":").append(JSONObject.quote(job))
         .append(",").append(JSONObject.quote("completed")).append(":").append(isCompleted)
         .append(",").append(JSONObject.quote("priority")).append(":").append(priority)
         .append("}");
      
      return sb.toString();
   }
}
