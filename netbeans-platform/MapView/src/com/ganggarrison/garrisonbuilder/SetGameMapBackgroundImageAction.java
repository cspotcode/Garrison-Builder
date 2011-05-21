
package com.ganggarrison.garrisonbuilder;

import com.ganggarrison.garrisonbuilder.gamemap.GameMap;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;

@org.openide.awt.
public final class SetGameMapBackgroundImageAction implements ActionListener {

    private final GameMap context;

    public SetGameMapBackgroundImageAction(GameMap context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        // TODO use context
        System.out.println("Background change action invoked.");
        
    }
}
