
package com.ganggarrison.garrisonbuilder.gg2mapsavesupport;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;

/**
 * Uses the alpha-channel of a BufferedImage to build a walkmask string.
 * String does not include {WALKMASK} tags or leading or trailing newlines.
 * @author cspotcode
 */
public class AlphaWalkmaskStringBuilder implements WalkmaskStringBuilder {

    private BufferedImage image;
    
    public AlphaWalkmaskStringBuilder(BufferedImage image) {
        this.image = image;
    }
    
    @Override
    public String buildWalkmaskString() {
        
        StringBuilder sb = new StringBuilder((image.getWidth() * image.getHeight() + 5) / 6);
        
        Raster alphaRaster = image.getAlphaRaster();
        int bitPos = 1<<5;
        char buffer = 0;
        for(int y = 0; y < image.getHeight(); y++) {
            for(int x = 0; x < image.getWidth(); x++) {
                // if non-zero alpha, it's a wall
                buffer += alphaRaster.getSample(x, y, 0) > 0 ? bitPos : 0;
                if(bitPos > 1) {
                    bitPos >>= 1;
                } else {
                    sb.append((char)(buffer + 32));
                    bitPos = 1<<5;
                    buffer = 0;
                }
            }
        }
        if(bitPos != 1<<5) {
            sb.append((char)(buffer + 32));
        }
        
        StringBuilder ret = new StringBuilder();
        ret.append(image.getWidth()).append("\n");
        ret.append(image.getHeight()).append("\n");
        ret.append(sb.toString());

        return ret.toString();
    }
    
}
