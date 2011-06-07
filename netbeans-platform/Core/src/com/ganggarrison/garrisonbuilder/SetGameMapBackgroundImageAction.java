
package com.ganggarrison.garrisonbuilder;

import com.ganggarrison.garrisonbuilder.gamemap.GameMap;
import com.ganggarrison.garrisonbuilder.util.OutputHelper;
import java.awt.image.BufferedImage;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;

@ActionID(category="Maps", id="com.ganggarrison.garrisonbuilder.SetGameMapBackgroundImageAction")
@ActionRegistration(displayName="Set background",
                    iconBase="com/ganggarrison/garrisonbuilder/setBackground.png"
                    )
@ActionReferences(value={
    @ActionReference(path="Toolbars/File", position=100)
})
public final class SetGameMapBackgroundImageAction extends OpenImageAction {

    private final GameMap context;

    public SetGameMapBackgroundImageAction(GameMap context) {
        this.context = context;
    }

    @Override
    public void imageOpened(BufferedImage i) {
        context.setBackgroundImage(i);
        OutputHelper.getDefault()
                .println("Loaded new background image.");
    }
}
