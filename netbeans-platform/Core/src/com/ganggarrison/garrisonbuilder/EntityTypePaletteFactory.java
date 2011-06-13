
package com.ganggarrison.garrisonbuilder;

import com.ganggarrison.garrisonbuilder.entitytypemanager.EntityTypeManager;
import com.ganggarrison.garrisonbuilder.gamemap.EntityType;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.beans.IntrospectionException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.Action;
import org.netbeans.spi.palette.DragAndDropHandler;
import org.netbeans.spi.palette.PaletteActions;
import org.netbeans.spi.palette.PaletteController;
import org.netbeans.spi.palette.PaletteFactory;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.Lookup;
import org.openide.util.datatransfer.ExTransferable;

/**
 * Given an EntityTypeManager, will create a palette of entity types.  Caches
 * palettes so subsequent calls to getPalette with the same EntityTypeManager
 * return the same palette.
 * @author cspotcode
 */
public class EntityTypePaletteFactory {
    private final static Map<EntityTypeManager, PaletteController> palettes
            = new HashMap<EntityTypeManager, PaletteController>();
    
    public static PaletteController getPalette(EntityTypeManager etm) {
        if(palettes.containsKey(etm)) {
            return palettes.get(etm);
        }
        Node rootNode = new AbstractNode(
                Children.create(
                        new EntityTypeCategoryChildFactory(
                                "Entities",
                                Children.create(
                                        new EntityTypeChildFactory(etm),
                                        false
                                )
                        ),
                        false
                )
        );
        PaletteController paletteController = PaletteFactory.createPalette(
                rootNode,
                new EntityTypePaletteActions(),
                null,
                new EntityTypePaletteDnDHandler()
        );
        palettes.put(etm, paletteController);
        return paletteController;
    }
    
    /**
     * Creates a child node for each entity type.
     */
    private static class EntityTypeChildFactory extends ChildFactory<EntityType> {

        private final EntityTypeManager etm;
        
        public EntityTypeChildFactory(EntityTypeManager etm) {
            this.etm = etm;
        }
        
        @Override
        protected boolean createKeys(List<EntityType> toPopulate) {
            // TODO check for Thread.interrupted() and stop creating keys
            Set<EntityType> typeSet = etm.getEntitySet();
            for(EntityType type : typeSet) {
                toPopulate.add(type);
            }
            return true;
        }

        @Override
        protected Node createNodeForKey(EntityType entType) {
            // create leaf child
            Node n;
            try {
                n = new EntityTypePaletteNode(entType);
            } catch (IntrospectionException ex) {
                // can't introspect the node, just don't add it to the palette??
                return null;
            }
            return n;
        }
    }

    /**
     * creates a single node with the given name and children.
     */
    private static class EntityTypeCategoryChildFactory extends ChildFactory<String> {

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

    private static class EntityTypePaletteActions extends PaletteActions {

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

    private static class EntityTypePaletteDnDHandler extends DragAndDropHandler {

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
