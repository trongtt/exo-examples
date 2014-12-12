/*
 * Copyright (C) 2003-2007 eXo Platform SAS.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see<http://www.gnu.org/licenses/>.
 */
package t3.examples.cometd;

import org.exoplatform.container.ExoContainer;
import org.exoplatform.container.ExoContainerContext;
import org.exoplatform.ws.frameworks.cometd.ContinuationService;
import org.exoplatform.ws.frameworks.json.impl.JsonException;
import org.exoplatform.ws.frameworks.json.impl.JsonGeneratorImpl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

public class CometdHelloWorldPortlet extends GenericPortlet
{
   // Kick off a thread which sending an message for every 15 seconds
   // This thread will be destroyed when 5 messages are sent.
   public void processAction(ActionRequest actionRequest, ActionResponse actionResponse) throws PortletException,
      IOException
   {

      final String message = actionRequest.getParameter("message");

      final ContinuationService continuation = getContinuationService();
      if (continuation == null)
         return;

      final Map<String, String> msg = new HashMap<String, String>();
      msg.put("subcribe", "/eXo/portal/notification");
      msg.put("sender", "Cometd Hello World Portlet");

      final String userName = actionRequest.getRemoteUser();
      System.out.println("DEBUG: Sending message \"" + message + "\"");
      final JsonGeneratorImpl jsonGenerator = new JsonGeneratorImpl();
      Thread t = new Thread() {
          @Override
        public void run() {
            int i = 0;
            while (++i < 6) {
                try
                {
                    msg.put("message", message + " [" + System.currentTimeMillis() + "]");
                    continuation.sendMessage(userName, "/eXo/portal/notification", jsonGenerator.createJsonObjectFromMap(msg), msg.toString());
                }
                catch (JsonException e)
                {
                    e.printStackTrace();
                }
                try {
                    sleep(1500);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
      };
      t.start();

   }

   protected void doView(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException,
      IOException
   {
      renderResponse.setContentType("text/html; charset=UTF-8");

      ContinuationService continuationService = getContinuationService();

      String userName = renderRequest.getRemoteUser();
      String userToken = continuationService.getUserToken(userName);
      renderRequest.setAttribute("userName", userName);
      renderRequest.setAttribute("userToken", userToken);

      PortletContext context = getPortletContext();
      PortletRequestDispatcher rd = context.getRequestDispatcher("/index.jsp");
      rd.include(renderRequest, renderResponse);
   }

   private ContinuationService getContinuationService()
   {
      ExoContainer container = ExoContainerContext.getCurrentContainer();
      ContinuationService continuation =
         (ContinuationService)container.getComponentInstanceOfType(ContinuationService.class);
      return continuation;

   }
}
