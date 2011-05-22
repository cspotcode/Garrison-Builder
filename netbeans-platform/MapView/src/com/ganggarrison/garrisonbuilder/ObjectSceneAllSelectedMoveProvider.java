
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
        IOProvider.getDefault().getIO("Output", false).getOut()
            .println("Movement starting");
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
        // for each selected object, move its widgets by the offset delta
        for(Object o : scene.getSelectedObjects()) {
            // TODO move the objects, not the widgets?
            // widgets will receive move notifications from objects anyway, and objects will be correctly moved
            for(Widget w : scene.findWidgets(o)) {
                Point newPos = w.getLocation();
                newPos.translate(offset.x, offset.y);
                w.setPreferredLocation(newPos);
            }
        }
        lastKnownPosition = location;
        IOProvider.getDefault().getIO("Output", false).getOut()
            .println("offset by " + offset.x + ", " + offset.y);
    }

}
