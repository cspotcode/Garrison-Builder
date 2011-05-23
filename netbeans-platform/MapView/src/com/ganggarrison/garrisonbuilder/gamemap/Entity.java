
package com.ganggarrison.garrisonbuilder.gamemap;

import com.ganggarrison.garrisonbuilder.Movable;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * An entity in a map.
 * @author cspotcode
 */
public class Entity implements Movable {
    private EntityType type;
    private int x, y;

    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public Entity(EntityType type, int x, int y) {
        this.type = type;
        this.x = x;
        this.y = y;
    }

    public EntityType getType() {
        return type;
    }

    public void setType(EntityType type) {
        EntityType oldType = this.type;
        this.type = type;
        pcs.firePropertyChange("type", oldType, type);
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public void setX(int x) {
        int oldX = this.x;
        this.x = x;
        pcs.firePropertyChange("x", oldX, x);
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void setY(int y) {
        int oldY = this.y;
        this.y = y;
        pcs.firePropertyChange("y", oldY, y);
    }

    public synchronized void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }

    public synchronized void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

}
