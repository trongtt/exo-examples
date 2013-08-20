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
package org.gatein.portlet.todomvc.service.testimpl;

import javax.jcr.Credentials;
import javax.jcr.Repository;
import javax.jcr.Session;

import org.exoplatform.container.StandaloneContainer;
import org.exoplatform.services.jcr.RepositoryService;
import org.exoplatform.services.jcr.core.CredentialsImpl;
import org.gatein.portlet.todomvc.service.impl.jcr.JCRSessionProvider;
import org.junit.Assert;

public class StandaloneJCRSessionProvider implements JCRSessionProvider {
    @Override
    public Session getSession() throws Exception {
        StandaloneContainer container = StandaloneContainer.getInstance();
        RepositoryService repositoryServices = (RepositoryService)container.getComponentInstanceOfType(RepositoryService.class);
        Repository repository = repositoryServices.getDefaultRepository();
        Credentials credentials = new CredentialsImpl("__system", "admin".toCharArray());
        Session session = repository.login(credentials, "ws");

        return session;
    }
}
