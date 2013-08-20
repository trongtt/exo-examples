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

import javax.jcr.Session;

import org.exoplatform.container.PortalContainer;
import org.exoplatform.services.jcr.RepositoryService;
import org.exoplatform.services.jcr.core.ManageableRepository;
import org.exoplatform.services.jcr.ext.app.SessionProviderService;
import org.exoplatform.services.jcr.ext.common.SessionProvider;

public class PortalJCRSessionProvider implements JCRSessionProvider {
    @Override
    public Session getSession() throws Exception {
        PortalContainer container = PortalContainer.getInstanceIfPresent();

        RepositoryService repositoryService = (RepositoryService)container.getComponentInstanceOfType(RepositoryService.class);
        SessionProviderService sessionProviderService = (SessionProviderService) container.getComponentInstanceOfType(SessionProviderService.class);

        ManageableRepository repository = repositoryService.getDefaultRepository();
        SessionProvider sessionProvider = sessionProviderService.getSessionProvider(null);
        Session session = sessionProvider.getSession(repository.getConfiguration().getDefaultWorkspaceName(), repository);
        return session;
    }
}
