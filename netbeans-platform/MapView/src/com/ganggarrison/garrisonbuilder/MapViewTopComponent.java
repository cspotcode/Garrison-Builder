
package com.ganggarrison.garrisonbuilder;

import com.ganggarrison.garrisonbuilder.GameMap.Entity;
import com.ganggarrison.garrisonbuilder.GameMap.GameMap;
import com.ganggarrison.garrisonbuilder.GameMap.EntityType;
import com.ganggarrison.garrisonbuilder.GameMap.GameMapChangeListener;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import org.netbeans.api.visual.action.AcceptProvider;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.ConnectorState;
import org.netbeans.api.visual.model.ObjectScene;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.Widget;
import org.netbeans.spi.palette.PaletteController;
import org.openide.util.Exceptions;
import org.openide.util.lookup.Lookups;
import org.openide.windows.TopComponent;

/**
 * Editor TopComponent for a GameMap.  This is the primary window pane where
 * users will see and modify the map.
 * @author cspotcode
 */
public class MapViewTopComponent extends TopComponent implements PropertyChangeListener, GameMapChangeListener {

    ObjectScene scene = null;

    final GameMap map;

    public MapViewTopComponent(GameMap map)  {
        this.map = map;
        map.addPropertyChangeListener(this);
        map.addGameMapChangeListener(this);

        setLayout(new BorderLayout());

        scene = new ObjectScene();
        Dimension size = new Dimension(map.getWidth(), map.getHeight());
        //scene.setPreferredSize(size);
        //scene.setMaximumSize(size);
        //scene.setMinimumSize(size);

        JComponent view = scene.createView();
        //view.setPreferredSize(size);
        //view.setMinimumSize(size);

        JScrollPane scrollPane = new JScrollPane(view);
        // TODO get the scene to size itself to the map correctly
        add(scrollPane, BorderLayout.CENTER);
        LabelWidget w = new LabelWidget(scene, "Hello world!!111");
        w.setPreferredSize(new Dimension(600, 300));
        scene.addChild(w);

        // add zooming support (hold CTRL and use mouse wheel)
        scene.getActions().addAction(ActionFactory.createZoomAction(1.5, false));
        
        // TODO panning doesn't seem to be working (supposed to hold down mouse wheel)
        scene.getActions().addAction(ActionFactory.createPanAction());

        scene.getActions().addAction(ActionFactory.createAcceptAction(new MyAcceptProvider()));

        associateLookup(Lookups.fixed(new Object[] {getPalette()}));
    }

    @Override
    protected String preferredID() {
        return "GameMapEditorPane";
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals("width")) {
            Dimension d = scene.getPreferredSize();
            d.width = (Integer)evt.getNewValue();
            scene.setPreferredSize(d);
        }
    }

    private PaletteController getPalette() {
        return EntityTypeManager.getInstance().getPalette();
    }

    @Override
    public void entityAdded(GameMap m, Entity e) {
        EntityWidget w = new EntityWidget(scene, e);
        w.getActions().addAction(ActionFactory.createMoveAction());
        scene.addChild(w);
        scene.repaint(); // TODO shouldn't a repaint happen automatically because a widget has been added?
        scene.addObject(e, w);
    }

    @Override
    public void entityRemoved(GameMap m, Entity e) {
        Widget w = scene.findWidget(e); // TODO error-check this?
        scene.removeObject(e);
        scene.removeChild(w);
    }

    private class MyAcceptProvider implements AcceptProvider {

        @Override
        public ConnectorState isAcceptable(Widget widget, Point point, Transferable transferable) {
            // is an EntityType being dragged?
            Object o = null;
            try {
                o = transferable.getTransferData(new DataFlavor(EntityType.class, "Entity Type"));
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            } catch (UnsupportedFlavorException ex) {
                // we couldn't get an EntityType, dropping won't work
                o = null;
            }

            return (o != null ? ConnectorState.ACCEPT : ConnectorState.REJECT);
        }

        @Override
        public void accept(Widget widget, Point point, Transferable transferable) {
            EntityType entType;
            try {
                entType = (EntityType) transferable.getTransferData(new DataFlavor(EntityType.class, "DOESN'T MATTER??"));
            } catch (UnsupportedFlavorException ex) {
                // do nothing, we couldn't get the flavor of data we wanted
                return;
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
                return;
            }
            Point p = widget.convertLocalToScene(point);
            map.addEntity(new Entity(entType, p.x, p.y));
        }
    };
}
