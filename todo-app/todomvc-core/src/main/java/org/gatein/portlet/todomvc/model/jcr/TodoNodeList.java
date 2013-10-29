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
package org.gatein.portlet.todomvc.model.jcr;

import java.util.LinkedList;
import java.util.List;

import javax.jcr.Node;
import javax.jcr.NodeIterator;

public class TodoNodeList {
    private final Node node;
    private long currentId;

    public TodoNodeList(Node node) {
        this.node = node;

        try {
            if(!node.hasProperty("todo:maxChildId")) {
                currentId = 0;
                this.node.setProperty("todo:maxChildId", currentId);
                this.node.save();
            }

            this.currentId = node.getProperty("todo:maxChildId").getLong();
        } catch (Exception e) {
            currentId = 0;
        }
    }

    public String getName() throws Exception {
        return node.getName();
    }

    public TodoNode newTodo() throws Exception {
        String nodeName = String.valueOf(++this.currentId);

        return this.newTodo(nodeName);
    }

    public TodoNode newTodo(String name) throws Exception {
        Node todo = this.node.addNode(name, "todo:todo");
        return new TodoNode(todo);
    }
    public TodoNode getTodo(String name) throws Exception {
        Node todo = node.getNode(name);
        return new TodoNode(todo);
    }

    public List<TodoNode> getTodos() throws Exception {
        NodeIterator iterator = node.getNodes();
        List<TodoNode> list = new LinkedList<TodoNode>();

        while (iterator.hasNext()) {
            Node todo = iterator.nextNode();
            if(!todo.getName().equals("*")) {
                list.add(new TodoNode(todo));
            }
        }
        return list;
    }

    public void save() throws Exception {
        node.setProperty("todo:maxChildId", this.currentId);
        node.save();
    }
}
