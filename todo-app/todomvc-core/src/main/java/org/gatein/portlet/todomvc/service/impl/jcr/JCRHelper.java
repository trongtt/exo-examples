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
package org.gatein.portlet.todomvc.service.impl.jcr;

import javax.jcr.Node;
import javax.jcr.Session;

import org.gatein.portlet.todomvc.model.jcr.TodoNodeList;

public class JCRHelper {
    private final JCRSessionProvider sessionProvider;

    public JCRHelper(JCRSessionProvider sessionProvider) {
        this.sessionProvider = sessionProvider;
    }

    public TodoNodeList getTodoNodeList(String todoListId) throws Exception {
        Session session = sessionProvider.getSession();
        Node root = session.getRootNode();

        Node todoList = null;
        try {
            todoList = root.getNode(todoListId);
        } catch (Exception ex) {
            todoList = root.addNode(todoListId, "todo:todolist");
            root.save();
            session.save();
        }

        if(todoList == null) {
            throw new Exception("Can not add todolist node");
        }

        return new TodoNodeList(todoList);
    }
}
