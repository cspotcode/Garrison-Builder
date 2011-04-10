/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ganggarrison.garrisonbuilder;

import java.awt.Image;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.imageio.ImageIO;
import javax.swing.Action;
import org.netbeans.spi.palette.DragAndDropHandler;
import org.netbeans.spi.palette.PaletteActions;
import org.netbeans.spi.palette.PaletteController;
import org.netbeans.spi.palette.PaletteFactory;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.nodes.AbstractNode;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.datatransfer.ExTransferable;
import org.openide.util.lookup.Lookups;

// TODO this class does too much stuff; functionality is not coherent
/**
 * Stores the set of loaded EntityTypes.  Also has logic to load them.
 * Also can create a palette for them.
 * @author cspotcode
 */
public class EntityTypeManager {
    private static EntityTypeManager singletonInstance = null;
    
    private Set<EntityType> types = null;

    private EntityTypeManager() {
        types = new HashSet<EntityType>();
        createTestEntities();
    }

    public static EntityTypeManager getInstance() {
        if(singletonInstance == null) {
            singletonInstance = new EntityTypeManager();
        }
        return singletonInstance;
    }

    public void loadEntitiesFromConfig(InputStream inStream) {
        // TODO implement me
    }

    public Set<EntityType> getEntitySet() {
        return Collections.unmodifiableSet(types);
    }

    public void createTestEntities() {
        EntityType t1 = new EntityType("testEnt1");
        Image i;
        try {
            i = ImageIO.read(getClass().getResourceAsStream("defaultEntMapImage.png"));
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex); // TODO properly handle this
            return;
        }
        t1.setMapImage(i);
        t1.setPalletteImage(i);
        types.add(t1);
        EntityType t2 = new EntityType("testEnt2");
        t1.setMapImage(i);
        t1.setPalletteImage(i);
        types.add(t2);
    }

    public PaletteController getPalette() {
        Node rootNode = new AbstractNode(Children.create(new EntityTypeCategoryChildFactory("Entities", Children.create(new EntityTypeChildFactory(), false)), false));

        PaletteController paletteController = PaletteFactory.createPalette(rootNode, new EntityTypePaletteActions(), null, new EntityTypePaletteDnDHandler());
        return paletteController;
        // TODO worry about caching the palettecontroller?  will it ever be created more than once?
    }

    /**
     * Creates a child node for each entity type.
     */
    private class EntityTypeChildFactory extends ChildFactory<EntityType> {

        @Override
        protected boolean createKeys(List<EntityType> toPopulate) {
            // TODO check for Thread.interrupted() and stop creating keys
            Set<EntityType> typeSet = getEntitySet();
            for(EntityType type : typeSet) {
                toPopulate.add(type);
            }
            return true;
        }

        @Override
        protected Node createNodeForKey(EntityType entType) {
            // create leaf child
            Node n = new AbstractNode(Children.LEAF, Lookups.fixed(new Object[] {entType}));
            n.setDisplayName(entType.getHumanReadableName());
            n.setName(entType.getName());
            n.setValue("EntityType", entType);
            return n;
        }
    }

    /**
     * creates a single node with the given name and children.
     */
    private class EntityTypeCategoryChildFactory extends ChildFactory<String> {

        String name;
        Children children;
        public EntityTypeCategoryChildFactory(String name, Children children) {
            this.name = name;
            this.children = children;
        }

        @Override
        protected boolean createKeys(List<String> toPopulate) {
            toPopulate.add(name);
            return true;
        }

        @Override
        protected Node createNodeForKey(String key) {
            AbstractNode n = new AbstractNode(children);
            n.setDisplayName(key);
            n.setName(key);
            n.setShortDescription(key);
            return n;
        }
    }

    private class EntityTypePaletteActions extends PaletteActions {

        @Override
        public Action[] getImportActions() {
            return null;
        }

        @Override
        public Action[] getCustomPaletteActions() {
            return null;
        }

        @Override
        public Action[] getCustomCategoryActions(Lookup category) {
            return null;
        }

        @Override
        public Action[] getCustomItemActions(Lookup item) {
            return null;
        }

        @Override
        public Action getPreferredAction(Lookup item) {
            return null;
        }

    }

    private class EntityTypePaletteDnDHandler extends DragAndDropHandler {

        @Override
        public void customize(ExTransferable t, Lookup item) {
            final Object o;
            o = item.lookup(EntityType.class);
            if(o != null) {
                t.put(new ExTransferable.Single(new DataFlavor(EntityType.class, "HumanscanRead")) {

                    @Override
                    protected Object getData() throws IOException, UnsupportedFlavorException {
                        return o;
                    }
                });
            }
        }

    }
}
