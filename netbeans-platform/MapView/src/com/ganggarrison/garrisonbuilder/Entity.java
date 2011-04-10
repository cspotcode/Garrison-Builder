
package com.ganggarrison.garrisonbuilder;

/**
 * An entity in a map.
 * @author cspotcode
 */
public class Entity {
    private EntityType type;
    private int x, y;

    public Entity(EntityType type, int x, int y) {
        this.type = type;
        this.x = x;
        this.y = y;
    }

    public EntityType getType() {
        return type;
    }

    public void setType(EntityType type) {
        this.type = type;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
