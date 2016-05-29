/**
 * Copyright (c) 2010-2016 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.io.tag.internal;

import org.openhab.core.binding.BindingConfig;
import org.openhab.core.items.Item;
import org.openhab.io.tag.TagBindingProvider;
import org.openhab.model.item.binding.AbstractGenericBindingProvider;
import org.openhab.model.item.binding.BindingConfigParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * Tags are simple comma delimited strings like:
 * <code>{ tag="tag1,tag2,tag:3" }</code>
 *
 * @author Dan Cunningham
 *
 * @since 1.9
 */
public class TagGenericBindingProvider extends AbstractGenericBindingProvider implements TagBindingProvider {

    private static final Logger logger = LoggerFactory.getLogger(TagGenericBindingProvider.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public String getBindingType() {
        return "tag";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validateItemType(Item item, String bindingConfig) throws BindingConfigParseException {
        // all item types are accepted
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void processBindingConfiguration(String context, Item item, String bindingConfig)
            throws BindingConfigParseException {
        super.processBindingConfiguration(context, item, bindingConfig);

        if (bindingConfig == null) {
            logger.warn("Could not process NULL config for item " + item.getName());
            return;
        }

        String[] tags = bindingConfig.split(",");
        TagBindingConfig config = new TagBindingConfig();
        config.tags = tags;
        addBindingConfig(item, config);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String[] getTags(String itemName) {
        TagBindingConfig config = (TagBindingConfig) bindingConfigs.get(itemName);
        return config != null ? config.tags : new String[] {};
    }

    static private class TagBindingConfig implements BindingConfig {
        public String[] tags;
    }

}
