
package com.ganggarrison.garrisonbuilder;

import com.ganggarrison.garrisonbuilder.gamemap.GameMap;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.Exceptions;

/**
 * Abstract Action that prompts the user for an image file, then loads it, then
 * does something with it.
 * @author cspotcode
 */
public abstract class OpenImageAction implements ActionListener {

    @Override
    public final void actionPerformed(ActionEvent ev) {
        JFileChooser chooser = new JFileChooser();
        int retVal = chooser.showOpenDialog(null);
        // don't proceed if user cancelled the dialog
        if(retVal != JFileChooser.APPROVE_OPTION)
            return;
        File selectedFile = chooser.getSelectedFile();
        
        // attempt to open the file as an image
        Image image = null;
        try {
            image = ImageIO.read(selectedFile);
        } catch (IOException ex) {
            // unable to load the image
            // TODO add error feedback
        }
        if(image != null)
            imageOpened(image);
        // TODO add failure feedback
    }
    
    /**
     * Handler callback for when an Image is successfully loaded
     * @param i 
     */
    public abstract void imageOpened(Image i);
}
