/**
 * Copyright (c) 2010-2016 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.io.tag.internal;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.openhab.core.binding.AbstractBinding;
import org.openhab.io.net.http.SecureHttpContext;
import org.openhab.io.tag.TagBindingProvider;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedService;
import org.osgi.service.http.HttpContext;
import org.osgi.service.http.HttpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Tag binding provides a simple way to "tag" items and retrieve those items through a
 * REST based interface
 *
 * @author Dan Cunningham
 * @since 1.9
 */
public class TagBinding extends AbstractBinding<TagBindingProvider>implements ManagedService {

    private static final String REST_ENDPOINT = "/rest/tag";

    private static final Logger logger = LoggerFactory.getLogger(TagBinding.class);

    ServiceRegistration<TagBinding> registration;

    protected HttpService httpService;

    public void setHttpService(HttpService httpService) {
        logger.info("sethttpservice called");
        this.httpService = httpService;
    }

    public void unsetHttpService(HttpService httpService) {
        this.httpService = null;
    }

    public void addBindingProvider(TagBindingProvider bindingProvider) {
        super.addBindingProvider(bindingProvider);
    }

    public void removeBindingProvider(TagBindingProvider bindingProvider) {
        super.removeBindingProvider(bindingProvider);
    }

    @Override
    public void activate() {
        logger.debug("Activate!");
        super.activate();
        try {
            httpService.registerServlet(REST_ENDPOINT, new TagServlet(), new Hashtable<String, String>(),
                    createHttpContext());
        } catch (Exception e) {
            logger.error("Could not register tag servlet", e);
        }
    };

    @Override
    public void deactivate() {
        registration.unregister();
        super.deactivate();
        try {
            httpService.unregister(REST_ENDPOINT);
        } catch (Exception e) {
            logger.error("Could not unregister tag servlet", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("rawtypes")
    public void updated(Dictionary config) throws ConfigurationException {
        // we have no configuration
    }

    /**
     * Retrieve all items and their tags
     *
     * @return items with tags
     */
    public Map<String, String[]> getTaggedItems() {
        HashMap<String, String[]> taggedItems = new HashMap<String, String[]>();
        for (TagBindingProvider provider : providers) {
            Collection<String> names = provider.getItemNames();
            for (String name : names) {
                taggedItems.put(name, provider.getTags(name));
            }
        }
        return taggedItems;
    }

    /**
     * Creates a {@link SecureHttpContext} which handles the security for this
     * servlet
     *
     * @return a {@link SecureHttpContext}
     */
    protected HttpContext createHttpContext() {
        if (this.httpService == null) {
            logger.error("cannot create http context httpservice null");
            return null;
        } else {
            HttpContext defaultHttpContext = httpService.createDefaultHttpContext();
            return new SecureHttpContext(defaultHttpContext, "openHAB.org");
        }
    }

    /**
     * Simple REST based end point to return tagged items
     *
     * @author Dan Cunningham
     *
     */
    class TagServlet extends HttpServlet {
        private static final long serialVersionUID = 1L;
        ObjectMapper objectMapper = new ObjectMapper();

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            PrintWriter pw = resp.getWriter();
            try {
                pw.print(objectMapper.writeValueAsString(getTaggedItems()));
            } finally {
                pw.close();
            }
        }
    }
}
