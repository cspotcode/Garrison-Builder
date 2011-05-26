
package com.ganggarrison.garrisonbuilder;

import com.ganggarrison.garrisonbuilder.gamemap.GameMap;
import java.awt.image.BufferedImage;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.windows.IOProvider;

@ActionID(category="Maps", id="com.ganggarrison.garrisonbuilder.SetGameMapBackgroundImageAction")
@ActionRegistration(displayName="Set background",
                    iconBase="com/ganggarrison/garrisonbuilder/setBackground.png"
                    )
@ActionReferences(value={
    @ActionReference(path="Toolbars/File")
})
public final class SetGameMapBackgroundImageAction extends OpenImageAction {

    private final GameMap context;

    public SetGameMapBackgroundImageAction(GameMap context) {
        this.context = context;
    }

    @Override
    public void imageOpened(BufferedImage i) {
        context.setBackgroundImage(i);
        IOProvider.getDefault().getIO("Output", false).getOut()
                .println("Loaded new background image.");
    }
}
