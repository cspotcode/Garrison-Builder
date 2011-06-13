
package com.ganggarrison.garrisonbuilder.entityloading;

import com.ganggarrison.garrisonbuilder.gamemap.Entity;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Creates entities by parsing the contents of .ent file or the {ENTITIES}
 * section of a GG2 map.
 * @author cspotcode
 */
public class DotEntFileLoader {
    
    private final EntityFactory ef;
    
    public DotEntFileLoader(EntityFactory ef) {
        this.ef = ef;
    }
    
    /**
     * Creates a set of entities from the {ENTITIES} section of a GG2 map, or
     * the contents of a .ent file.  (both should be identical)
     * @param entitySection
     * @return 
     */
    Set<Entity> createEntities(String entitySection) throws DotEntFormatException {
        Set<Entity> entities = new HashSet<Entity>();
        
        List<String> lines = Arrays.asList(entitySection.split("\n"));
        Iterator<String> iter = lines.iterator();
        
        String id;
        int x;
        int y;
        try {
            while(iter.hasNext()) {
                // read entity id
                id = iter.next();
                String temp = iter.next();
                try {
                    x = Integer.parseInt(temp);
                } catch(NumberFormatException e) {
                    throw new DotEntFormatException("Invalid x coordinate.  Entity id: \"" + id + "\", x coordinate: \"" + temp + "\"");
                }
                temp = iter.next();
                try {
                    y = Integer.parseInt(temp);
                } catch(NumberFormatException e) {
                    throw new DotEntFormatException("Invalid y coordinate.  Entity id: \"" + id + "\", y coordinate: \"" + temp + "\"");
                }
                Entity ent = ef.createEntityFromId(id);
                if(ent == null) {
                    throw new DotEntFormatException("Unknown entity id: \"" + id + "\"");
                }
                ent.setX(x);
                ent.setY(y);
                entities.add(ent);
            }
        } catch(NoSuchElementException e) {
            throw new DotEntFormatException("Entities section ended unexpectedly.");
        }
        return entities;
    }
}
