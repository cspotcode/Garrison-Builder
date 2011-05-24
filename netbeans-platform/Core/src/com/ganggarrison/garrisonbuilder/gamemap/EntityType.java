
package com.ganggarrison.garrisonbuilder.gamemap;

import java.awt.Image;
import java.awt.Point;

/**
 * A type of map entity. Contains the definition of the entity, with enough info
 * to create many such instances of this type of entity.
 * @author cspotcode
 */
public class EntityType {

    // name of this entity
    private String name;
    // string to show the user when identifying this entity
    private String humanReadableName;
    // name of this entity for saving into map files, recognized by gg2's map loader
    private String id;
    // image to show in the map editor view
    private Image mapImage;
    // point within the image that represents entity's position
    private Point mapImageOrigin;
    // image to show in the pallette of entities
    private Image paletteImage;

    public EntityType() {
        this.name = this.id = this.humanReadableName = "";
        mapImage = null;
        paletteImage = null;
        mapImageOrigin = new Point(0, 0);
    }
    
    public EntityType(String name) {
        this.name = name;
        this.id = name;
        this.humanReadableName = name;
        mapImage = null;
        paletteImage = null;
        mapImageOrigin = new Point(0, 0);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHumanReadableName() {
        return humanReadableName;
    }

    public void setHumanReadableName(String humanReadableName) {
        this.humanReadableName = humanReadableName;
    }

    public Image getMapImage() {
        return mapImage;
    }

    public void setMapImage(Image mapImage) {
        this.mapImage = mapImage;
    }
    
    public Point getMapImageOrigin() {
        return mapImageOrigin;
    }

    public void setMapImageOrigin(Point mapImageOrigin) {
        this.mapImageOrigin = mapImageOrigin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Image getPaletteImage() {
        return paletteImage;
    }

    public void setPaletteImage(Image palletteImage) {
        this.paletteImage = palletteImage;
    }
}
