package org.openhab.io.tag.internal;

import org.openhab.core.binding.BindingConfig;

/**
 * Represents the configuration of a tagged item.
 * 
 * @author Dan Cunningham
 *
 */
public class TagBindingConfig implements BindingConfig {
    public String name;
    public String label;
    public String type;
    public String[] tags;
}