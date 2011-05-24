
package com.ganggarrison.garrisonbuilder.testentitytypes;

import com.ganggarrison.garrisonbuilder.entitytypesources.EntityTypeSource;
import com.ganggarrison.garrisonbuilder.gamemap.EntityType;
import java.awt.Image;
import java.awt.Point;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import javax.imageio.ImageIO;
import org.openide.util.Exceptions;
import org.openide.windows.IOProvider;

/**
 *
 * @author cspotcode
 */
public class TestEntityTypesSource implements EntityTypeSource {

    @Override
    public EntityType[] getEntityTypes() {
        // build a set of dummy entities for testing
        Set<EntityType> types = new HashSet<EntityType>();
        
        EntityType t1 = new EntityType("testEnt1");
        Image i;
        try {
            i = ImageIO.read(getClass().getResourceAsStream("defaultEntMapImage.png"));
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex); // TODO properly handle this
            return new EntityType[] {};
        }
        t1.setMapImage(i);
        t1.setPaletteImage(i);
        t1.setMapImageOrigin(new Point(40, 40));
        types.add(t1);
        EntityType t2 = new EntityType("testEnt2");
        t2.setMapImage(i);
        t2.setPaletteImage(i);
        t2.setMapImageOrigin(new Point(100, 0));
        types.add(t2);
        
        IOProvider.getDefault().getIO("Output", false).getOut()
                .println("Created 2 dummy entity types for testing purposes.");
        
        return types.toArray(new EntityType[] {});
    }
    
}
