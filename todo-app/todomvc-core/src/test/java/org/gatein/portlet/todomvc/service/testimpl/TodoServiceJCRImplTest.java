package org.gatein.portlet.todomvc.service.testimpl;

import java.net.URL;

import javax.jcr.Credentials;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Repository;
import javax.jcr.Session;
import org.exoplatform.container.StandaloneContainer;
import org.exoplatform.services.jcr.RepositoryService;
import org.exoplatform.services.jcr.core.CredentialsImpl;
import org.gatein.portlet.todomvc.service.TodoService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class TodoServiceJCRImplTest {
    private TodoService todoService;

    @BeforeClass
    public static void beforeClass() throws Exception {
        String conf = "/conf/standalone/configuration.xml";
        URL confURL = TodoServiceJCRImplTest.class.getResource(conf);

        if (confURL != null) {
            StandaloneContainer.addConfigurationURL(confURL.toString());
        } else {
            StandaloneContainer.addConfigurationPath(conf);
        }

        StandaloneContainer container = StandaloneContainer.getInstance();
        Assert.assertNotNull(container);
    }

    @AfterClass
    public static void afterClass() throws Exception {

    }

    @Before
    public void before() throws Exception {
        StandaloneContainer container = StandaloneContainer.getInstance();

        //Login to JCR and reset all data
        RepositoryService repositoryServices = (RepositoryService) container.getComponentInstanceOfType(RepositoryService.class);
        Assert.assertNotNull(repositoryServices);
        Repository repository = repositoryServices.getDefaultRepository();
        Assert.assertNotNull(repository);
        Credentials credentials = new CredentialsImpl("__system", "admin".toCharArray());
        Session session = repository.login(credentials, "ws");
        Node root = session.getRootNode();
        NodeIterator iterator = root.getNodes();
        while (iterator.hasNext()) {
            Node node = iterator.nextNode();
            if (node.isNodeType("todo:todolist")) {
                node.remove();
            }
        }
        root.save();
        session.save();

        //Init service
        todoService = (TodoService) container.getComponentInstanceOfType(TodoService.class);
        Assert.assertNotNull(todoService);
    }

    @After
    public void after() throws Exception {

    }

    @Test
    public void testSaveListTodo() throws Exception {
        System.out.println("------------testSaveListTodo()------------");

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

        Assert.assertEquals(2, resultJARR.length());
        System.out.println("JSONArray after saving: " + jsonArray.toString());
        System.out.println("JSONArray result: " + resultJARR.toString());
    }

    @Test
    public void testSaveTodo() throws Exception {
        System.out.println("------------testSaveTodo()------------");

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

        JSONObject resultJO1 = todoService.getTodo("todoListId1", 1);
        JSONObject resultJO2 = todoService.getTodo("todoListId2", 1);

        Assert.assertEquals(1, resultJO1.getInt("id"));
        Assert.assertEquals(1, resultJO2.getInt("id"));

        System.out.println("JSONObject result (in todoListId1): " + resultJO1.toString());
        System.out.println("JSONObject result (in todoListId2): " + resultJO2.toString());
    }

    @Test
    public void testGetTodoList() throws Exception {
        System.out.println("------------testGetTodoList()------------");

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


        JSONArray jsonArray = todoService.getListTodos("todoListId1");
        Assert.assertEquals(1, jsonArray.length());
        System.out.println("JSONArray result (ing todoListId1): " + jsonArray.toString());

        JSONArray jsonArray1 = todoService.getListTodos("todoListId2");
        Assert.assertEquals(1, jsonArray1.length());

    }

    @Test
    public void testGetTodo() {
        System.out.println("------------testGetTodo()------------");
        try {
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("job", "job1 in todoListId1");
            jsonObject1.put("completed", false);
            jsonObject1.put("priority", 0);
            todoService.saveTodo("todoListId1", jsonObject1);

            JSONObject jsonObject = todoService.getTodo("todoListId1", 1);
            Assert.assertEquals(1, jsonObject.getInt("id"));
            System.out.println("JSONObject result (in todoListId1): " + jsonObject.toString());
        } catch (Exception e) {
            Assert.fail("cannot find this todo");
            e.printStackTrace();
        }
    }

    @Test
    public void testUpdateListTodos() throws Exception {
        System.out.println("------------testUpdateListTodos()------------");

        JSONObject jsonObject1 = new JSONObject();
        JSONArray jsonArray1 = new JSONArray();

        //ID = 1
        jsonObject1.put("job", "job1 in todoListId1");
        jsonObject1.put("completed", false);
        jsonObject1.put("priority", 2);
        jsonArray1.put(jsonObject1);

        //ID = 2
        jsonObject1 = new JSONObject();
        jsonObject1.put("job", "job2 in todoListId1");
        jsonObject1.put("completed", true);
        jsonObject1.put("priority", 2);
        jsonArray1.put(jsonObject1);

        todoService.saveListTodos("todoListId1", jsonArray1);



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

        try {
            todoService.updateListTodos("todoListId1", jsonArray);
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        JSONArray resultJARR = todoService.getListTodos("todoListId1");
        Assert.assertEquals(2, resultJARR.length());

        JSONObject obj1 = resultJARR.getJSONObject(0);
        Assert.assertEquals("job1 in todoListId1 -- update", obj1.getString("job"));

        JSONObject obj2 = resultJARR.getJSONObject(1);
        Assert.assertEquals("job2 in todoListId1 -- update", obj2.getString("job"));

        System.out.println("JSONArray result (in todoListId1): " + resultJARR.toString());
    }

    @Test
    public void testUpdateTodo() throws Exception {
        System.out.println("------------testUpdateTodo()------------");

        JSONObject jsonObject1 = new JSONObject();
        JSONArray jsonArray1 = new JSONArray();

        //ID = 1
        jsonObject1.put("job", "job1 in todoListId1");
        jsonObject1.put("completed", false);
        jsonObject1.put("priority", 2);
        jsonArray1.put(jsonObject1);

        //ID = 2
        jsonObject1 = new JSONObject();
        jsonObject1.put("job", "job2 in todoListId1");
        jsonObject1.put("completed", true);
        jsonObject1.put("priority", 2);
        jsonArray1.put(jsonObject1);

        todoService.saveListTodos("todoListId1", jsonArray1);


        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", 2);
        jsonObject.put("job", "job3 in todoListId1 -- update");
        jsonObject.put("completed", false);
        jsonObject.put("priority", 3);

        try {
            todoService.updateTodo("todoListId1", jsonObject);
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        JSONObject resultJO = todoService.getTodo("todoListId1", 2);
        Assert.assertEquals(2, resultJO.getInt("id"));
        Assert.assertEquals(3, resultJO.getInt("priority"));
        Assert.assertEquals("job3 in todoListId1 -- update", resultJO.getString("job"));

        System.out.println("JSONObject result (in todoListId1): " + resultJO.toString());
    }

    @Test
    public void testDeleteTodos() throws Exception {
        System.out.println("------------testDeleteTodos()------------");

        JSONObject jsonObject1 = new JSONObject();
        JSONArray jsonArray1 = new JSONArray();

        //ID = 1
        jsonObject1.put("job", "job1 in todoListId1");
        jsonObject1.put("completed", false);
        jsonObject1.put("priority", 2);
        jsonArray1.put(jsonObject1);

        //ID = 2
        jsonObject1 = new JSONObject();
        jsonObject1.put("job", "job2 in todoListId1");
        jsonObject1.put("completed", true);
        jsonObject1.put("priority", 2);
        jsonArray1.put(jsonObject1);

        todoService.saveListTodos("todoListId1", jsonArray1);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", 2);

        todoService.deleteTodo("todoListId1", jsonObject);

        JSONArray resultArr = todoService.getListTodos("todoListId1");

        Assert.assertEquals(1, resultArr.length());
        JSONObject obj = resultArr.getJSONObject(0);
        Assert.assertEquals(1, obj.getInt("id"));
        Assert.assertEquals("job1 in todoListId1", obj.getString("job"));

        System.out.println("JSONArray result (in todoListId1): " + resultArr.toString());
    }
}
