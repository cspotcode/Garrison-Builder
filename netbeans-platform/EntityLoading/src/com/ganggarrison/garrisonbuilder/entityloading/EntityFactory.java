
package com.ganggarrison.garrisonbuilder.entityloading;

import com.ganggarrison.garrisonbuilder.entitytypemanager.EntityTypeManager;
import com.ganggarrison.garrisonbuilder.gamemap.Entity;
import com.ganggarrison.garrisonbuilder.gamemap.EntityType;
import java.util.Set;

/**
 * Creates entities by referring to an EntityTypeManager to retrieve the correct
 * EntityTypes
 * @author cspotcode
 */
public class EntityFactory {
    
    private final EntityTypeManager etm;
    
    /**
     * Creates a factory that will find all EntityTypes from the given
     * EntityTypeManager
     * @param etm 
     */
    public EntityFactory(EntityTypeManager etm) {
        this.etm = etm;
    }
    
    /**
     * Given an entity id string, creates a new Entity.  Returns null if no
     * EntityType for the given id exists.
     * @return 
     */
    public Entity createEntityFromId(String id) {
        // TODO EntityTypeManager should be expanded to make these queries
        // easier and more efficient
        Set<EntityType> entitySet = etm.getEntitySet();
        for(EntityType et : entitySet) {
            if(et.getId().equals(id)) {
                return new Entity(et, 0, 0);
            }
        }
        return null;
    }
}
