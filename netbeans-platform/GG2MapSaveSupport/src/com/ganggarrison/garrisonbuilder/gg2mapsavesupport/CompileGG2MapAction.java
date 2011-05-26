
package com.ganggarrison.garrisonbuilder.gg2mapsavesupport;

import com.ganggarrison.garrisonbuilder.gamemap.GameMap;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.filesystems.FileChooserBuilder;

/**
 * Action to compile the current GameMap into a PNG
 * @author cspotcode
 */
@ActionID(category="Maps", id="CompileGG2MapAction")
@ActionRegistration(
        displayName="Compile GG2 Map",
        iconBase="com/ganggarrison/garrisonbuilder/gg2mapsavesupport/saveMap.png"
        )
@ActionReference(path="Toolbars/File")
public class CompileGG2MapAction implements ActionListener {

    final GameMap context;
    
    public CompileGG2MapAction(GameMap gameMap) {
        context = gameMap;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // ask the user where to save the image
        File outputFile = new FileChooserBuilder(CompileGG2MapAction.class)
                .setFilesOnly(true)
                .setTitle("Save compiled PNG Map")
                .addFileFilter(new FileNameExtensionFilter("PNG Image", "png"))
                .showSaveDialog();
        if(outputFile == null)
            return;
        GG2MapSaver saver = new GG2MapSaver();
        saver.saveMapToPng(context, outputFile);
    }
}
