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

import javax.jcr.Node;
import javax.jcr.Property;

public class TodoNode {
    private final Node node;

    public TodoNode(Node node) {
        this.node = node;
    }

    public String getName() throws Exception {
        return node.getName();
    }

    public void setJob(String job) throws Exception {
        node.setProperty("todo:job", job);
    }
    public String getJob() throws Exception {
        return node.getProperty("todo:job").getString();
    }

    public void setPriority(int priority) throws Exception {
        node.setProperty("todo:priority", priority);
    }
    public int getPriority() throws Exception {
        return (int)node.getProperty("todo:priority").getLong();
    }

    public void setCompleted(boolean isCompleted) throws Exception {
        node.setProperty("todo:isCompleted", isCompleted);
    }
    public boolean isCompleted() throws Exception {
        return node.getProperty("todo:isCompleted").getBoolean();
    }

    public void save() throws Exception {
        node.save();
    }
    public void remove() throws Exception {
        node.remove();
    }
}
