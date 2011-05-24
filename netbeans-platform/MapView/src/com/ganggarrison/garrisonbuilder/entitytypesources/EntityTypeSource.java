
package com.ganggarrison.garrisonbuilder.entitytypesources;

import com.ganggarrison.garrisonbuilder.gamemap.EntityType;

/**
 * Interface for a source of entity types.  The global lookup is queried for all
 * such sources and the entity types they create are shown to the user in the
 * editor.
 * @author cspotcode
 */
public interface EntityTypeSource {
    
    /**
     * returns all EntityTypes that this source can provide
     * @return 
     */
    public EntityType[] getEntityTypes();
}
