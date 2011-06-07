
package com.ganggarrison.garrisonbuilder;

import com.ganggarrison.garrisonbuilder.util.OutputHelper;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;

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
        BufferedImage image = null;
        try {
            image = ImageIO.read(selectedFile);
        } catch (IOException ex) {
            OutputHelper.getDefault()
                    .println("Error: unable to read image "
                            + selectedFile.getPath());
        }
        if(image != null)
            imageOpened(image);
        else
            OutputHelper.getDefault()
                    .println("Error: unable to read image "
                            + selectedFile.getPath()
                            + ": image is in an unsupported format");
    }
    
    /**
     * Handler callback for when an Image is successfully loaded
     * @param i 
     */
    public abstract void imageOpened(BufferedImage i);
}
