
package com.ganggarrison.garrisonbuilder.GameMap;

/**
 * WIP interface for objects that want to be notified of changes in a GameMap.
 * GameMap already reports property changes to PropertyChangeListeners, so this
 * code should probably be redone.
 * @author cspotcode
 */
public interface GameMapChangeListener {

    public void entityAdded(GameMap m, Entity e);

    public void entityRemoved(GameMap m, Entity e);

}
