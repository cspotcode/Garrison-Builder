
package com.ganggarrison.garrisonbuilder;

import com.ganggarrison.garrisonbuilder.gamemap.Entity;
import com.ganggarrison.garrisonbuilder.gamemap.GameMap;
import com.ganggarrison.garrisonbuilder.gamemap.EntityType;
import com.ganggarrison.garrisonbuilder.gamemap.GameMapChangeListener;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.Set;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import org.netbeans.api.visual.action.AcceptProvider;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.ConnectorState;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.model.ObjectScene;
import org.netbeans.api.visual.widget.ImageWidget;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Widget;
import org.netbeans.spi.palette.PaletteController;
import org.openide.util.Exceptions;
import org.openide.util.lookup.Lookups;
import org.openide.windows.IOProvider;
import org.openide.windows.TopComponent;

/**
 * Editor TopComponent for a GameMap.  This is the primary window pane where
 * users will see and modify the map.
 * @author cspotcode
 */
@TopComponent.Description(
        iconBase="com/ganggarrison/garrisonbuilder/newMap.png",
        preferredID="GameMapEditorPane"
        )
public class MapViewTopComponent extends TopComponent implements PropertyChangeListener, GameMapChangeListener {

    private ObjectScene scene = null;
    private LayerWidget guiVisualsLayer;
    private LayerWidget bgWmLayer;
    private JComponent sceneView;

    final GameMap map;

    private ScaledImageWidget backgroundWidget;
    private ScaledImageWidget walkmaskWidget;

    public MapViewTopComponent(GameMap map) {
        this.map = map;

        setDisplayName("GG2 Map");
        setName("NAME");
        setToolTipText("TOOL TIP TEXT");

        // create scene to visualize the map
        scene = new ObjectScene();
        guiVisualsLayer = new LayerWidget(scene);
        scene.addChild(guiVisualsLayer);
        bgWmLayer = new LayerWidget(scene);
        scene.addChild(bgWmLayer); 
        bgWmLayer.bringToBack();

        // create a scrolling view onto the scene
        setLayout(new BorderLayout());
        sceneView = scene.createView();
        JScrollPane scrollPane = new JScrollPane(sceneView);
        add(scrollPane, BorderLayout.CENTER);
        
        // add background and walkmask widgets
        backgroundWidget = new ScaledImageWidget(scene);
        walkmaskWidget = new ScaledImageWidget(scene);
        backgroundWidget.setScale(6);
        walkmaskWidget.setScale(6);
        backgroundWidget.setPreferredLocation(new Point(0, 0));
        walkmaskWidget.setPreferredLocation(new Point(0, 0));
        bgWmLayer.addChild(backgroundWidget);
        bgWmLayer.addChild(walkmaskWidget);

        // TODO get the scene to size itself to the map correctly
        
        sceneView.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                sceneView.requestFocus();
            }
        });
        
        // other custom action behaviors
        scene.getActions().addAction(new MyAction());
        
        // allow dragging a rectangle to select entities
        scene.getActions().addAction(ActionFactory.createRectangularSelectAction(scene, guiVisualsLayer));

        // add zooming support
        scene.getActions().addAction(ActionFactory.createMouseCenteredZoomAction(1.3));
        scene.getInputBindings().setZoomActionModifiers(0); 
        
        // pan the scene by holding down middle-mouse-button
        scene.getActions().addAction(ActionFactory.createPanAction());

        // allows dropping of palette entities onto the map
        scene.getActions().addAction(ActionFactory.createAcceptAction(new MyAcceptProvider()));

        associateLookup(Lookups.fixed(new Object[] {getPalette(), map}));

        map.addPropertyChangeListener(this);
        map.addGameMapChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals("width")) {
            Dimension d = scene.getPreferredSize();
            d.width = (Integer)evt.getNewValue();
            scene.setPreferredSize(d);
            return;
        }
        if(evt.getPropertyName().equals("backgroundImage")) {
            Image i = (Image)evt.getNewValue();
            backgroundWidget.setImage(i);
            return;
        }
        if(evt.getPropertyName().equals("walkmaskImage")) {
            Image i = (Image)evt.getNewValue();
            walkmaskWidget.setImage(i);
            return;
        }
    }

    private PaletteController getPalette() {
        return EntityTypePaletteFactory.getPalette(EntityTypeManager.getInstance());
    }

    @Override
    public void entityAdded(GameMap m, Entity e) {
        EntityWidget w = new EntityWidget(scene, e);
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
    
    private class MyAction extends WidgetAction.Adapter {

        @Override
        public State keyTyped(Widget widget, WidgetKeyEvent event) {
            // TODO use Netbeans keymap options to configure this
            
            // delete key: delete all selected entities
            if(event.getKeyChar() == 127) {
                // TODO what's going on when I do this cast?
                Set<Entity> selectedEntities = (Set<Entity>) scene.getSelectedObjects();
                Entity[] ents = selectedEntities.toArray(new Entity[] {});
                for(Entity ent : ents) {
                    map.removeEntity(ent);
                }
                IOProvider.getDefault().getIO("Output", false).getOut()
                        .println("Deleted " + ents.length + " entities.");
                return State.CONSUMED;
            }
            return State.REJECTED;
        }

        /*@Override
        public State mousePressed(Widget widget, WidgetMouseEvent event) {
            // must get focus or else keyboard events will not be received
            sceneView.requestFocus();
            // TODO doesn't work when a sub-widget is clicked and traps the event
            
            return State.REJECTED;
        }*/
        
    }
}
