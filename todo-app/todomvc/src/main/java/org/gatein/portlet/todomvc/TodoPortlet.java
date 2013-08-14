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
package org.gatein.portlet.todomvc;

import org.gatein.portlet.todomvc.service.TodoService;
import org.gatein.portlet.todomvc.service.impl.TodoServiceImpl;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.portlet.GenericPortlet;
import javax.portlet.PortletException;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

/**
 * @author <a href="mailto:phuong.vu@exoplatform.com">Vu Viet Phuong</a>
 * @editor <a href="trongson.tran1228@gmail.com">Son Tran Trong</a>
 * @version $Id$
 *
 */
public class TodoPortlet extends GenericPortlet
{
   TodoService todoService;

   @Override
   public void init() throws PortletException
   {
      super.init();
      todoService = new TodoServiceImpl();
   }

   @Override
   public void render(RenderRequest request, RenderResponse response) throws PortletException, IOException
   {
      PortletRequestDispatcher prd = getPortletContext().getRequestDispatcher("/todos/todo.jsp");
      prd.include(request, response);
   }

   @Override
   public void serveResource(ResourceRequest request, ResourceResponse response) throws PortletException, IOException
   {
      String userName = null;
      if (request.getUserPrincipal() != null && request.getUserPrincipal().getName() != null)
      {
         userName = request.getUserPrincipal().getName();
      }

      if (userName == null)
      {
         writeJsonToClient(request, response, null);
      }
      if (userName != null)
      {
         String method = request.getParameter("todo:method");
         if (method == null || method.length() == 0)
         {
            method = request.getMethod();
         }
         try
         {
            String data = parseRequestPayloadData(request);

            if (method.equals("POST"))
            {
               create(userName, data, request, response);
            }
            else if (method.equals("PUT"))
            {
               update(userName, data, request, response);
            }
            else if (method.equals("GET"))
            {
               read(userName, data, request, response);
            }
            else if (method.equals("DELETE"))
            {
               String ids = request.getParameter("todo:ids");
               delete(userName, ids, request, response);
            }
            else if (method.equals("PUTLIST"))
            {
               updateList(userName, data, request, response);
            }
         }
         catch (Exception e)
         {
            throw new PortletException();
         }
      }
   }

   private String parseRequestPayloadData(ResourceRequest request) throws Exception
   {
      StringBuilder sb = new StringBuilder();
      BufferedReader bf = new BufferedReader(request.getReader());
      char[] charBuffer = new char[128];

      while ((bf.read(charBuffer)) > 0)
      {
         sb.append(charBuffer);
      }
      bf.close();
      return sb.toString();
   }

   public void create(String userName, String data, ResourceRequest request, ResourceResponse response)
      throws Exception
   {
      if (todoService != null)
      {
         JSONObject json = new JSONObject(data);
         todoService.saveTodo(userName, json);
         writeJsonToClient(request, response, json);
      }
   }

   public void update(String userName, String data, ResourceRequest request, ResourceResponse response)
      throws Exception
   {
      if (todoService != null)
      {
         JSONObject json = new JSONObject(data);
         todoService.updateTodo(userName, json);
         writeJsonToClient(request, response, json);
      }
   }

   public void updateList(String userName, String data, ResourceRequest request, ResourceResponse response)
      throws Exception
   {
      if (todoService != null)
      {
         JSONArray jsonArray = new JSONArray(data);
         todoService.updateListTodos(userName, jsonArray);
         writeJsonToClient(request, response, jsonArray);
      }
   }

   public void read(String userName, String data, ResourceRequest request, ResourceResponse response) throws Exception
   {
      if (todoService != null)
      {
         JSONArray jsonArray = todoService.getListTodos(userName);
        
         writeJsonToClient(request, response, jsonArray);
      }
   }

   public void delete(String userName, String ids, ResourceRequest request, ResourceResponse response) throws Exception
   {
      if (todoService != null)
      {
         JSONArray jsonArray = new JSONArray();
         String[] listIds = ids.split("\\|");
         for (String id : listIds)
         {
            JSONObject json = new JSONObject();
            json.put("id", id);
            jsonArray.put(json);
         }
         todoService.deleteListTodos(userName, jsonArray);
      }
   }

   public void writeJsonToClient(ResourceRequest request, ResourceResponse response, Object json)
      throws PortletException, IOException
   {
      if (json == null)
      {
         PortletRequestDispatcher prd = getPortletContext().getRequestDispatcher("/todos/todo.jsp");
         prd.include(request, response);
      }
      response.setCharacterEncoding("UTF-8");
      response.setContentType("application/json");
      PrintWriter pwriter = response.getWriter();
      pwriter.print(json);
      pwriter.close();
   }
}
