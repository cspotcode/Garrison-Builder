
package com.ganggarrison.garrisonbuilder;

import com.ganggarrison.garrisonbuilder.gamemap.GameMap;
import com.ganggarrison.garrisonbuilder.util.OutputHelper;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;

/**
 * Action that creates a new GameMap and MapEditorTopComponent to edit it.
 * @author cspotcode
 */
@ActionID(id = "NewMapAction", category = "Maps")
@ActionRegistration(iconInMenu = true, displayName = "New Map", iconBase = "com/ganggarrison/garrisonbuilder/newMap.png")
@ActionReference(path = "Toolbars/File", position = 0)
public class NewMapAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        GameMap m = new GameMap();
        MapEditorTopComponent metc = new MapEditorTopComponent(m);
        metc.open();
        metc.requestActive();
        OutputHelper.getDefault()
            .println("Created new map.");
    }

}
