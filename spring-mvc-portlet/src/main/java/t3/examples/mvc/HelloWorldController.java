package t3.examples.mvc;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.mvc.Controller;


public class HelloWorldController implements Controller {

    public void handleActionRequest(ActionRequest request,
			ActionResponse response) throws Exception {
	}
    
	public ModelAndView handleRenderRequest(RenderRequest request,
			RenderResponse response) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("helloWorldMessage", "Hello World Trong Tran !!!");
		return new ModelAndView("helloWorld", model);
	}
}
