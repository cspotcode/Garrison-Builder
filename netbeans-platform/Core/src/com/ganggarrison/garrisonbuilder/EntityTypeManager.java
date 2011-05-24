
package com.ganggarrison.garrisonbuilder;

import com.ganggarrison.garrisonbuilder.entitytypesources.EntityTypeSource;
import com.ganggarrison.garrisonbuilder.entitytypesources.EntityTypeSource;
import com.ganggarrison.garrisonbuilder.gamemap.EntityType;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.Action;
import org.netbeans.spi.palette.DragAndDropHandler;
import org.netbeans.spi.palette.PaletteActions;
import org.netbeans.spi.palette.PaletteController;
import org.netbeans.spi.palette.PaletteFactory;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.nodes.AbstractNode;
import org.openide.util.Lookup;
import org.openide.util.datatransfer.ExTransferable;
import org.openide.util.lookup.Lookups;

// TODO this class does too much stuff; functionality is not coherent
/**
 * Stores the set of loaded EntityTypes.  Also has logic to load them from
 * EntityTypeSource's
 * Also can create a palette for them.
 * @author cspotcode
 */
public class EntityTypeManager {
    private static EntityTypeManager singletonInstance = null;
    
    private Set<EntityType> types = null;

    private EntityTypeManager() {
        types = new HashSet<EntityType>();
        loadEntitiesFromSources();
    }

    public static EntityTypeManager getInstance() {
        if(singletonInstance == null) {
            singletonInstance = new EntityTypeManager();
        }
        return singletonInstance;
    }

    public Set<EntityType> getEntitySet() {
        return Collections.unmodifiableSet(types);
    }

    public PaletteController getPalette() {
        Node rootNode = new AbstractNode(Children.create(new EntityTypeCategoryChildFactory("Entities", Children.create(new EntityTypeChildFactory(), false)), false));

        PaletteController paletteController = PaletteFactory.createPalette(rootNode, new EntityTypePaletteActions(), null, new EntityTypePaletteDnDHandler());
        return paletteController;
        // TODO worry about caching the palettecontroller?  will it ever be created more than once?
    }

    private void loadEntitiesFromSources() {
        // get all type sources from the global lookup
        Collection<EntityTypeSource> sources = (Collection<EntityTypeSource>) Lookup.getDefault().lookupAll(EntityTypeSource.class);
        
        for(EntityTypeSource source : sources) {
            // get all new entity types and add them to the set of all types
            EntityType[] newTypes = source.getEntityTypes();
            types.addAll(Arrays.asList(newTypes));
        }
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
