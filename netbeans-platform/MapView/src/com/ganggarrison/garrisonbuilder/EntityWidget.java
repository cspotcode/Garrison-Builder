
package com.ganggarrison.garrisonbuilder;

import com.ganggarrison.garrisonbuilder.gamemap.Entity;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.border.BorderFactory;
import org.netbeans.api.visual.model.ObjectScene;
import org.netbeans.api.visual.model.ObjectState;
import org.netbeans.api.visual.widget.ImageWidget;
import org.netbeans.api.visual.widget.Widget;

/**
 * View for an entity in a scene
 * @author cspotcode
 */
public class EntityWidget extends Widget implements PropertyChangeListener {

    Entity ent;
    ImageWidget imageWidget;

    public EntityWidget(ObjectScene s, Entity e) {
        super(s);
        ent = e;
        imageWidget = new ImageWidget(s);
        addChild(imageWidget);
        setupToMatchEntity(e);
        moveToEntityPosition(ent);

        getActions().addAction(s.createSelectAction());
        
        getActions().addAction(ActionFactory.createMoveAction(
                ActionFactory.createSnapToGridMoveStrategy(6, 6),
                new ObjectSceneAllSelectedMoveProvider(s)
        ));
        
        // TODO moving entityWidget needs to also move the entity

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

    @Override
    protected void notifyStateChanged(ObjectState previousState, ObjectState state) {
        super.notifyStateChanged(previousState, state);
        // if widget has been selected
        if(!previousState.isSelected() && state.isSelected()) {
            setBorder(BorderFactory.createDashedBorder(Color.black, 3, 3, true));
            bringToFront();
        }
        // if widget has been deselected
        if(previousState.isSelected() && !state.isSelected()) {
            setBorder(BorderFactory.createEmptyBorder());
        }
    }

}
