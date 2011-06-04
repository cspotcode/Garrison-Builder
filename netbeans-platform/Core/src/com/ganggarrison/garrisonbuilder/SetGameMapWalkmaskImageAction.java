
package com.ganggarrison.garrisonbuilder;

import com.ganggarrison.garrisonbuilder.gamemap.GameMap;
import java.awt.image.BufferedImage;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.windows.IOProvider;

@ActionID(category="Maps", id="com.ganggarrison.garrisonbuilder.SetGameMapWalkmaskImageAction")
@ActionRegistration(displayName="Set walkmask",
                    iconBase="com/ganggarrison/garrisonbuilder/setWalkmask.png"
                    )
@ActionReferences(value={
    @ActionReference(path="Toolbars/File", position=200)
})
public final class SetGameMapWalkmaskImageAction extends OpenImageAction {

    private final GameMap context;

    public SetGameMapWalkmaskImageAction(GameMap context) {
        this.context = context;
    }

    @Override
    public void imageOpened(BufferedImage i) {
        context.setWalkmaskImage(i);
        IOProvider.getDefault().getIO("Output", false).getOut()
                .println("Loaded new walkmask image.");
    }
}
