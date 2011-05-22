
package com.ganggarrison.garrisonbuilder;

import com.ganggarrison.garrisonbuilder.gamemap.GameMap;
import java.awt.Image;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;

@ActionID(category="Maps", id="com.ganggarrison.garrisonbuilder.SetGameMapWalkmaskImageAction")
@ActionRegistration(displayName="Set walkmask",
                    iconBase="com/ganggarrison/garrisonbuilder/setWalkmask.png"
                    )
@ActionReferences(value={
    @ActionReference(path="Toolbars/File")
})
public final class SetGameMapWalkmaskImageAction extends OpenImageAction {

    private final GameMap context;

    public SetGameMapWalkmaskImageAction(GameMap context) {
        this.context = context;
    }

    @Override
    public void imageOpened(Image i) {
        context.setWalkmaskImage(i);
    }
}
