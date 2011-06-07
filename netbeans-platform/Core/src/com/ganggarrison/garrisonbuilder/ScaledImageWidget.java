
package com.ganggarrison.garrisonbuilder;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import org.netbeans.api.visual.widget.ImageWidget;
import org.netbeans.api.visual.widget.Scene;

/**
 * Like ImageWidget but supports scaling by some int factor.
 * @author cspotcode
 */
public class ScaledImageWidget extends ImageWidget {

    // TODO explore possibility of non-int scale
    private int scale;

    public ScaledImageWidget(Scene scene) {
        super(scene);
    }

    public float getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }
    
    @Override
    protected Rectangle calculateClientArea() {
        Image i = getImage();
        if(i != null)
            return new Rectangle(scale * i.getWidth(null), scale * i.getHeight(null));
        else
            return new Rectangle();
    }

    @Override
    protected void paintWidget() {
        Image i = getImage();
        if(i == null) return;
        Graphics2D g = getGraphics();
        g.drawImage(i, 0, 0, i.getWidth(null) * scale, i.getHeight(null) * scale, null);
    }
    
}
