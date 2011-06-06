
package com.ganggarrison.garrisonbuilder;

import com.ganggarrison.garrisonbuilder.entitytypesources.EntityTypeSource;
import com.ganggarrison.garrisonbuilder.gamemap.EntityType;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.openide.util.Lookup;

// TODO this class does too much stuff; functionality is not coherent
/**
 * Stores the set of loaded EntityTypes.  Also has logic to load them from
 * EntityTypeSource's
 * @author cspotcode
 */
public class EntityTypeManager {
    private static EntityTypeManager singletonInstance = null;
    
    private Set<EntityType> types = null;

    private EntityTypeManager() {
        types = new HashSet<EntityType>();
        loadEntitiesFromSources();
    }

    public static EntityTypeManager getInstance() {
        if(singletonInstance == null) {
            singletonInstance = new EntityTypeManager();
        }
        return singletonInstance;
    }

    public Set<EntityType> getEntitySet() {
        return Collections.unmodifiableSet(types);
    }

    private void loadEntitiesFromSources() {
        // get all type sources from the global lookup
        Collection<EntityTypeSource> sources = (Collection<EntityTypeSource>) Lookup.getDefault().lookupAll(EntityTypeSource.class);
        
        for(EntityTypeSource source : sources) {
            // get all new entity types and add them to the set of all types
            EntityType[] newTypes = source.getEntityTypes();
            types.addAll(Arrays.asList(newTypes));
        }
    }

}
