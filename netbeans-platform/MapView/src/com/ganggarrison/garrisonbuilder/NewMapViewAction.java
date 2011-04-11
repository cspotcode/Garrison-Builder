
package com.ganggarrison.garrisonbuilder;

import com.ganggarrison.garrisonbuilder.GameMap.GameMap;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Action that creates a new GameMap and MapViewTopComponent to edit it.
 * @author cspotcode
 */
public class NewMapViewAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        GameMap m = new GameMap();
        m.setWidth(800);
        m.setHeight(600);
        MapViewTopComponent mvtc = new MapViewTopComponent(m);
        mvtc.open();
        //mvtc.requestActive();
    }

}
