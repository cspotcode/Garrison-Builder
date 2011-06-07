
package com.ganggarrison.garrisonbuilder.util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JToggleButton;

/**
 * Simple action for handling ToggleButton actions.
 * @author cspotcode
 */
public abstract class ToggleAction implements ActionListener {

    /**
     * Called when the button is clicked.
     * @param isSelected True if the button has been selected, false if it's
     * been deselected.
     */
    public abstract void doAction(boolean isSelected);

    @Override
    public void actionPerformed(ActionEvent e) {
        JToggleButton btn = (JToggleButton)e.getSource();
        doAction(btn.isSelected());
    }

}