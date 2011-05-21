package com.ganggarrison.garrisonbuilder;

import com.ganggarrison.garrisonbuilder.gamemap.GameMap;
import java.io.File;
import java.io.IOException;
import javax.swing.text.AbstractDocument.Content;
import org.openide.cookies.SaveCookie;
import org.openide.util.Lookup;

/**
 * Saves a GameMap into the legacy .entity format
 * @author cspotcode
 */
public class GameMapLegacySaver implements SaveCookie {
    public void save(GameMap gm, File f) {
        //f. TODO FINISH THIS
    }

    @Override
    public void save() throws IOException {
        
    }
}
