
package com.ganggarrison.garrisonbuilder;

import java.awt.Point;
import org.netbeans.api.visual.action.MoveProvider;
import org.netbeans.api.visual.model.ObjectScene;
import org.netbeans.api.visual.widget.Widget;
import org.openide.windows.IOProvider;

/**
 *
 * @author cspotcode
 */
public class ObjectSceneAllSelectedMoveProvider implements MoveProvider {

    private ObjectScene scene;
    private Point lastKnownPosition;

    public ObjectSceneAllSelectedMoveProvider(ObjectScene scene) {
        this.scene = scene;
    }

    @Override
    public void movementStarted(Widget widget) {
        // do nothing
    }

    @Override
    public void movementFinished(Widget widget) {
        // do nothing
    }

    @Override
    public Point getOriginalLocation(Widget widget) {
        lastKnownPosition = widget.getLocation();
        return lastKnownPosition;
    }

    @Override
    public void setNewLocation(Widget widget, Point location) {
        // compute movement delta since last movement
        Point offset = new Point(location.x - lastKnownPosition.x, location.y - lastKnownPosition.y);
        // for each selected object that is movable, move it by the offset
        for(Object o : scene.getSelectedObjects()) {
            if(o instanceof Movable) {
                Movable m = (Movable)o;
                m.setX(m.getX() + offset.x);
                m.setY(m.getY() + offset.y);
            }
        }
        lastKnownPosition = location;
    }

}
