package org.gatein.portlet.todomvc.service.testimpl;

import junit.framework.TestCase;

import org.exoplatform.container.PortalContainer;
import org.gatein.portlet.todomvc.service.TodoService;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Unit test for simple App.
 */
public class TodoServiceImplTest extends TestCase
{
   public void testSaveListTodo() throws Exception
   {
      System.out.println("------------testSaveListTodo()------------");
      PortalContainer container = PortalContainer.getInstance();
      TodoService todoService = (TodoService)container.getComponentInstanceOfType(TodoService.class);

      JSONArray jsonArray = new JSONArray();
      JSONObject jsonObject = new JSONObject();

      jsonObject.put("job", "job1");
      jsonObject.put("completed", false);
      jsonObject.put("priority", 0);
      jsonArray.put(jsonObject);

      jsonObject = new JSONObject();
      jsonObject.put("job", "job2");
      jsonObject.put("completed", false);
      jsonObject.put("priority", 1);
      jsonArray.put(jsonObject);

      System.out.println("JSONArray before saving: " + jsonArray.toString());

      todoService.saveListTodos("todoListId1", jsonArray);

      JSONArray resultJARR = todoService.getListTodos("todoListId1");

      assertEquals(2, resultJARR.length());
      System.out.println("JSONArray after saving: " + jsonArray.toString());
      System.out.println("JSONArray result: " + resultJARR.toString());
   }

   public void testSaveTodo() throws Exception
   {
      System.out.println("------------testSaveTodo()------------");
      PortalContainer container = PortalContainer.getInstance();
      TodoService todoService = (TodoService)container.getComponentInstanceOfType(TodoService.class);

      JSONObject jsonObject1 = new JSONObject();
      jsonObject1.put("job", "job1 in todoListId1");
      jsonObject1.put("completed", false);
      jsonObject1.put("priority", 0);
      todoService.saveTodo("todoListId1", jsonObject1);

      JSONObject jsonObject2 = new JSONObject();
      jsonObject2.put("job", "job2 in todoListId2");
      jsonObject2.put("completed", false);
      jsonObject2.put("priority", 0);
      todoService.saveTodo("todoListId2", jsonObject2);

      JSONObject resultJO1 = todoService.getTodo("todoListId1", 3);
      JSONObject resultJO2 = todoService.getTodo("todoListId2", 1);

      assertEquals(3, resultJO1.getInt("id"));
      assertEquals(1, resultJO2.getInt("id"));

      System.out.println("JSONObject result (in todoListId1): " + resultJO1.toString());
      System.out.println("JSONObject result (in todoListId2): " + resultJO2.toString());
   }

   public void testGetTodoList() throws Exception
   {
      System.out.println("------------testGetTodoList()------------");
      PortalContainer container = PortalContainer.getInstance();
      TodoService todoService = (TodoService)container.getComponentInstanceOfType(TodoService.class);

      JSONArray jsonArray = todoService.getListTodos("todoListId1");

      assertEquals(3, jsonArray.length());

      System.out.println("JSONArray result (ing todoListId1): " + jsonArray.toString());

   }

   public void testGetTodo()
   {
      System.out.println("------------testGetTodo()------------");
      PortalContainer container = PortalContainer.getInstance();
      TodoService todoService = (TodoService)container.getComponentInstanceOfType(TodoService.class);
      try
      {
         JSONObject jsonObject = todoService.getTodo("todoListId1", 2);
         assertEquals(2, jsonObject.getInt("id"));
         System.out.println("JSONObject result (in todoListId1): " + jsonObject.toString());
      }
      catch (Exception e)
      {
         fail("cannot find this todo");
         e.printStackTrace();
      }
   }

   public void testUpdateListTodos() throws Exception
   {
      System.out.println("------------testUpdateListTodos()------------");
      PortalContainer container = PortalContainer.getInstance();
      TodoService todoService = (TodoService)container.getComponentInstanceOfType(TodoService.class);

      JSONObject jsonObject = new JSONObject();
      JSONArray jsonArray = new JSONArray();

      jsonObject.put("id", 1);
      jsonObject.put("job", "job1 in todoListId1 -- update");
      jsonObject.put("completed", false);
      jsonObject.put("priority", 2);
      jsonArray.put(jsonObject);

      jsonObject = new JSONObject();
      jsonObject.put("id", 2);
      jsonObject.put("job", "job2 in todoListId1 -- update");
      jsonObject.put("completed", true);
      jsonObject.put("priority", 2);
      jsonArray.put(jsonObject);

      try
      {
         todoService.updateListTodos("todoListId1", jsonArray);
      }
      catch (Exception e1)
      {
         e1.printStackTrace();
      }

      JSONArray resultJARR = todoService.getListTodos("todoListId1");
      assertEquals(3, resultJARR.length());
      System.out.println("JSONArray result (in todoListId1): " + resultJARR.toString());
   }

   public void testUpdateTodo() throws Exception
   {
      System.out.println("------------testUpdateTodo()------------");
      PortalContainer container = PortalContainer.getInstance();
      TodoService todoService = (TodoService)container.getComponentInstanceOfType(TodoService.class);

      JSONObject jsonObject = new JSONObject();
      jsonObject.put("id", 3);
      jsonObject.put("job", "job3 in todoListId1 -- update");
      jsonObject.put("completed", false);
      jsonObject.put("priority", 3);

      try
      {
         todoService.updateTodo("todoListId1", jsonObject);
      }
      catch (Exception e1)
      {
         e1.printStackTrace();
      }

      JSONObject resultJO = todoService.getTodo("todoListId1", 3);
      assertEquals(3, resultJO.getInt("id"));
      assertEquals(3, resultJO.getInt("priority"));
      System.out.println("JSONObject result (in todoListId1): " + resultJO.toString());
   }

   public void testDeleteTodos() throws Exception
   {
      System.out.println("------------testDeleteTodos()------------");
      PortalContainer container = PortalContainer.getInstance();
      TodoService todoService = (TodoService)container.getComponentInstanceOfType(TodoService.class);

      JSONObject jsonObject = new JSONObject();
      jsonObject.put("id", 3);

      todoService.deleteTodo("todoListId1", jsonObject);

      JSONArray resultArr = todoService.getListTodos("todoListId1");

      assertEquals(2, resultArr.length());
      System.out.println("JSONArray result (in todoListId1): " + resultArr.toString());
   }
}
