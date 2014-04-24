/*
 * Copyright (C) 2009 eXo Platform SAS.
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
package t3.examples.exo.kernel;

import junit.framework.TestCase;

import org.exoplatform.container.PortalContainer;
import org.exoplatform.services.cache.CacheService;
import org.exoplatform.services.cache.ExoCache;

import java.io.Serializable;

/**
 * @author <a href="trongtt@gmail.com">Trong Tran</a>
 * @version $Revision$
 */
public class TestCacheService extends TestCase
{

   CacheService service_;

   public TestCacheService(String name)
   {
      super(name);
   }

   public void setUp() throws Exception
   {
      service_ = (CacheService)PortalContainer.getInstance().getComponentInstanceOfType(CacheService.class);
   }

   public void testInitialization()
   {
       assertNotNull(service_);
       ExoCache<Serializable, Object> instance = service_.getCacheInstance("DefaultCacheConfig");
       assertEquals(100, instance.getMaxSize());
       assertEquals(300, instance.getLiveTime());
       assertEquals(0, instance.getCacheSize());
   }

   public void testCacheService_1() throws Exception {
       ExoCache<Serializable, Object> cache = service_.getCacheInstance("TestCache");
       assertEquals("TestCache", cache.getName());
       cache.put("name1", "value1");
       cache.setLiveTime(0);
       cache.put("name2", "value2");
       assertEquals(1, cache.getCacheSize());
       assertEquals("value1", cache.getCachedObjects().get(0));
   }
}
