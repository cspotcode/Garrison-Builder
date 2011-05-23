
package com.ganggarrison.garrisonbuilder;

/**
 * Interface for items that can be moved around the map.  Editor will allow
 * drag-and-drop movement of these items.
 * @author cspotcode
 */
public interface Movable {
    public int getX();
    public int getY();
    public void setX(int x);
    public void setY(int y);
}
