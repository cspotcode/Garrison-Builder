
package com.ganggarrison.garrisonbuilder.jsonentitytypeloader;

import com.ganggarrison.garrisonbuilder.entitytypesources.EntityTypeSource;
import com.ganggarrison.garrisonbuilder.gamemap.EntityType;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.module.SimpleModule;
import org.codehaus.jackson.type.TypeReference;
import org.openide.windows.IOProvider;

/**
 * EntityTypeSource that produces types by loading their definitions from a JSON
 * configuration file.
 * @author cspotcode
 */
public class JsonEntityTypeSource implements EntityTypeSource {

    @Override
    public EntityType[] getEntityTypes() {
        // mapper to turn json into EntityTypes
        ObjectMapper mapper = new ObjectMapper();
        
        // attach filename-to-Image converter
        SimpleModule imageLoadingModule = new SimpleModule("ImageLoading", new Version(1, 0, 0, null));
        imageLoadingModule.addDeserializer(Image.class, new FilenameToImageDeserializer());
        mapper.registerModule(imageLoadingModule);
        
        List<EntityType> entTypes = null;
        // attempt loading a list of EntityTypes from the file
        try {
            entTypes = mapper.readValue(new File("C:\\Users\\cspotcode\\Documents\\dev\\Garrison-Builder-Git\\netbeans-platform\\build\\testuserdir\\entitytypes.json"), new TypeReference<List<EntityType>>() {});
        } catch (JsonParseException ex) {
            IOProvider.getDefault().getIO("Output", false).getOut()
                    .println("Error parsing JSON entity type configuration.");
        } catch (JsonMappingException ex) {
            IOProvider.getDefault().getIO("Output", false).getOut()
                    .println("Error creating entity types from JSON configuration:\n" + ex.getLocalizedMessage());
        } catch (IOException ex) {
            IOProvider.getDefault().getIO("Output", false).getOut()
                    .println("IO error loading entities from JSON configuration. Maybe entitytypes.json doesn't exist?");
        }
        
        if(entTypes == null) return new EntityType[] {};
        // TODO better error feedback to the user: don't silently fail

        IOProvider.getDefault().getIO("Output", false).getOut()
                .println("Loaded " + entTypes.size() + " entity type" + (entTypes.size() > 1 ? "s" : "") + " from JSON configuration.");
        
        return entTypes.toArray(new EntityType[] {}); 
    }
    
}
