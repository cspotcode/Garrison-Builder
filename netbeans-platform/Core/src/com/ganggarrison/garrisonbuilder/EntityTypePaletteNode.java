/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ganggarrison.garrisonbuilder;

import com.ganggarrison.garrisonbuilder.gamemap.EntityType;
import com.sun.org.apache.bcel.internal.generic.LOOKUPSWITCH;
import java.awt.Image;
import java.beans.IntrospectionException;
import org.openide.nodes.BeanNode;
import org.openide.nodes.Children;
import org.openide.util.Lookup;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.AbstractLookup.Content;
import org.openide.util.lookup.InstanceContent;
import org.openide.util.lookup.Lookups;
import org.openide.util.lookup.ProxyLookup;

/**
 *
 * @author cspotcode
 */
class EntityTypePaletteNode extends BeanNode<EntityType> {
    
    public EntityTypePaletteNode(EntityType entType) throws IntrospectionException {
        // create leaf node, putting EntityType into the lookup
        super(entType, Children.LEAF, Lookups.fixed(new Object[] {entType}));
    }

    @Override
    public Image getIcon(int type) {
        return getBean().getPaletteImage();
    }
    
}
