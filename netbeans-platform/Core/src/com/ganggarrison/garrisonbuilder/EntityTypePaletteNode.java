
package com.ganggarrison.garrisonbuilder;

import com.ganggarrison.garrisonbuilder.gamemap.EntityType;
import java.awt.Image;
import java.beans.IntrospectionException;
import org.openide.nodes.BeanNode;
import org.openide.nodes.Children;
import org.openide.util.lookup.Lookups;

/**
 * Netbeans Node subclass for showing EntityTypes in the palette
 * @author cspotcode
 */
class EntityTypePaletteNode extends BeanNode<EntityType> {
    
    public EntityTypePaletteNode(EntityType entType) throws IntrospectionException {
        // create leaf node, putting EntityType into the lookup
        super(entType, Children.LEAF, Lookups.fixed(new Object[] {entType}));
        
        setDisplayName(entType.getHumanReadableName());
        setName(entType.getName());
        setValue("EntityType", entType);
    }

    @Override
    public Image getIcon(int type) {
        return getBean().getPaletteImage();
    }
    
}
