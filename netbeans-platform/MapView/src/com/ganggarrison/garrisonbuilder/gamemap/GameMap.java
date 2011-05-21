
package com.ganggarrison.garrisonbuilder.gamemap;

import java.awt.Image;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.openide.nodes.Node;

/**
 * This is a level in the game, not to be confused with a Map data structure.
 * Comprised of many entities.
 * @author cspotcode
 */
public class GameMap implements Node.Cookie {
    private Set<Entity> entities = null;

    private int width;
    private int height;
    
    private Image backgroundImage;
    private Image walkmaskImage;
    
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
    
    public Image getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(Image backgroundImage) {
        Image old = this.backgroundImage;
        this.backgroundImage = backgroundImage;
        pcs.firePropertyChange("backgroundImage", old, backgroundImage);
    }

    public Image getWalkmaskImage() {
        return walkmaskImage;
    }

    public void setWalkmaskImage(Image walkmaskImage) {
        Image old = this.walkmaskImage;
        this.walkmaskImage = walkmaskImage;
        pcs.firePropertyChange("walkmaskImage", old, walkmaskImage);
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
