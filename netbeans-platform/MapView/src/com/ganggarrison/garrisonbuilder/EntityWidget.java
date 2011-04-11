
package com.ganggarrison.garrisonbuilder;

import com.ganggarrison.garrisonbuilder.GameMap.Entity;
import java.awt.Image;
import java.awt.Point;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import org.netbeans.api.visual.widget.ImageWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;

/**
 * View for an entity in a scene
 * @author cspotcode
 */
public class EntityWidget extends Widget implements PropertyChangeListener {

    Entity ent;
    ImageWidget imageWidget;

    public EntityWidget(Scene s, Entity e) {
        super(s);
        ent = e;
        imageWidget = new ImageWidget(s);
        addChild(imageWidget);
        setupToMatchEntity(e);
        moveToEntityPosition(ent);
        e.addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String prop = evt.getPropertyName();
        if(prop.equals("x") || prop.equals("y")) {
            moveToEntityPosition(ent);
        } else if(prop.equals("type")) {
            setupToMatchEntity(ent);
        }
    }

    /**
     * Setup self to properly visualize the given entity
     * @param e
     */
    private void setupToMatchEntity(Entity e) {
        Image i = e.getType().getMapImage();
        imageWidget.setImage(i);
    }

    /**
     * Move self to entity's position
     */
    private void moveToEntityPosition(Entity e) {
        Point p = new Point(ent.getX(), ent.getY());
        this.setPreferredLocation(p);
    }

}
