
package com.ganggarrison.garrisonbuilder.GameMap;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * This is a level in the game, not to be confused with a Map data structure.
 * Comprised of many entities.
 * @author cspotcode
 */
public class GameMap {
    private Set<Entity> entities = null;

    private int width;
    private int height;
    
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    private final GameMapChangeSupport gmcs = new GameMapChangeSupport(this);

    public GameMap() {
        entities = new HashSet<Entity>();
        width = 0;
        height = 0;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        int oldHeight = this.height;
        this.height = height;
        pcs.firePropertyChange("height", oldHeight, height);
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        int oldWidth = this.width;
        this.width = width;
        pcs.firePropertyChange("width", oldWidth, width);
    }

    public boolean addEntity(Entity ent) {
        boolean b = entities.add(ent);
        if(b) {
            gmcs.fireEntityAdded(ent);
        }
        return b;
    }

    public boolean removeEntity(Entity ent) {
        boolean b = entities.remove(ent);
        if(b) {
            gmcs.fireEntityRemoved(ent);
        }
        return b;
    }

    public Set<Entity> getEntitySet() {
        return Collections.unmodifiableSet(entities);
    }

    public synchronized void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }

    public synchronized void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public void addGameMapChangeListener(GameMapChangeListener listener) {
        gmcs.addGameMapChangeListener(listener);
    }

    public void removeGameMapChangeListener(GameMapChangeListener listener) {
        gmcs.addGameMapChangeListener(listener);
    }

}
