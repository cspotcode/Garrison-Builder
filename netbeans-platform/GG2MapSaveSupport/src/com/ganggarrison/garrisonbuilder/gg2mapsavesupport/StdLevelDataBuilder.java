
package com.ganggarrison.garrisonbuilder.gg2mapsavesupport;

import com.ganggarrison.garrisonbuilder.gamemap.Entity;
import com.ganggarrison.garrisonbuilder.gamemap.GameMap;

/**
 * Builds a leveldata string in classic, ugly GG2 format
 * @author cspotcode
 */
public class StdLevelDataBuilder implements LevelDataBuilder {
    
    GameMap map;
    WalkmaskStringBuilder walkmaskStringBuilder;
    
    public StdLevelDataBuilder(GameMap map) {
        this.map = map;
        walkmaskStringBuilder = new AlphaWalkmaskStringBuilder(map.getWalkmaskImage());
    }
    
    public StdLevelDataBuilder(GameMap map, WalkmaskStringBuilder walkmaskStringBuilder) {
        this.map = map;
        this.walkmaskStringBuilder = walkmaskStringBuilder;
    }

    @Override
    public String buildLevelDataString() {
        StringBuilder sb = new StringBuilder();
        
        // write entities
        sb.append("{ENTITIES}\n");
        for(Entity ent : map.getEntitySet()) {
            sb.append(ent.getType().getId()).append("\n");
            sb.append(ent.getX()).append("\n");
            sb.append(ent.getY()).append("\n");
        }
        sb.append("{END ENTITIES}");
        
        sb.append("\n");
        
        // write walkmask
        sb.append("{WALKMASK}\n");
        sb.append(walkmaskStringBuilder.buildWalkmaskString());
        sb.append("\n{END WALKMASK}");
        
        return sb.toString();
    }
}
