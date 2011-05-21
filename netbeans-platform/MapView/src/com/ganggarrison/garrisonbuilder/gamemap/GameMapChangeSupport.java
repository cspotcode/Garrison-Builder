
package com.ganggarrison.garrisonbuilder.gamemap;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author cspotcode
 */
public class GameMapChangeSupport {
    private Set<GameMapChangeListener> listeners;

    private GameMap map;

    public GameMapChangeSupport(GameMap map) {
        this.map = map;
        listeners = new HashSet<GameMapChangeListener>();
    }

    public void fireEntityAdded(Entity e) {
        for(GameMapChangeListener l : listeners) {
            l.entityAdded(map, e);
        }
    }

    public void fireEntityRemoved(Entity e) {
        for(GameMapChangeListener l : listeners) {
            l.entityRemoved(map, e);
        }
    }

    public void addGameMapChangeListener(GameMapChangeListener listener) {
        listeners.add(listener);
    }

    public void removeGameMapChangeListener(GameMapChangeListener listener) {
        listeners.remove(listener);
    }
}
