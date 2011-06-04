
package com.ganggarrison.garrisonbuilder;

import com.ganggarrison.garrisonbuilder.gamemap.GameMap;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.windows.IOProvider;

/**
 * Action that creates a new GameMap and MapViewTopComponent to edit it.
 * @author cspotcode
 */
@ActionID(id = "NewMapViewAction", category = "Maps")
@ActionRegistration(iconInMenu = true, displayName = "New Map", iconBase = "com/ganggarrison/garrisonbuilder/newMap.png")
@ActionReference(path = "Toolbars/File", position = 0)
public class NewMapViewAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        GameMap m = new GameMap();
        m.setWidth(800);
        m.setHeight(600);
        MapViewTopComponent mvtc = new MapViewTopComponent(m);
        mvtc.open();
        mvtc.requestActive();
        IOProvider.getDefault().getIO("Output", false).getOut()
            .println("Created new map.");
    }

}
