/**
 * Copyright (c) 2010-2016 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.io.tag;

import org.openhab.core.binding.BindingProvider;
import org.openhab.io.tag.internal.TagBindingConfig;

/**
 * Interface for classes that map tags to items
 *
 * @author Dan Cunningham
 * @since 1.9
 *
 */
public interface TagBindingProvider extends BindingProvider {
    /**
     * Get all tags for a given itemName
     *
     * @param itemName
     * @return Array of tags
     */
    public TagBindingConfig getTagBindingConfig(String itemName);
}
