
package com.ganggarrison.garrisonbuilder.gg2mapsavesupport;

import com.ganggarrison.garrisonbuilder.gamemap.GameMap;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOInvalidTreeException;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageOutputStream;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

// TODO this feels very half-baked.  Or whatever
/**
 * Creates PNGs with embedded level data suitable for GG2.
 * @author cspotcode
 */
public class GG2MapSaver {
    
    private static final String ZTXT = "zTXt";
    private static final String ZTXTENTRY = "zTXtEntry";
    private static final String GG2_LEVELDATA_KEYWORD_VALUE = "Gang Garrison 2 Level Data";
    private static final String ZTXTENTRY_KEYWORD = "keyword";
    private static final String ZTXTENTRY_COMPRESSIONMETHOD = "compressionMethod";
    private static final String ZTXTENTRY_COMPRESSIONMETHOD_VALUE = "deflate";
    private static final String ZTXTENTRY_TEXT = "text";
    
    void saveMapToPng(GameMap map, File pngFile) {
        // fetch the first ImageWriter capable of writing PNGs
        Iterator<ImageWriter> imageWriters = ImageIO.getImageWritersBySuffix("png");
        if(!imageWriters.hasNext()) {
            // TODO handle errors correctly
            return;
        }
        ImageWriter iw = imageWriters.next();
        
        // tell image writer to output to pngFile
        ImageOutputStream ios = null;
        try {
            ios = ImageIO.createImageOutputStream(pngFile);
        } catch (IOException ex) {
            // TODO handle errors correctly
            return;
        }
        iw.setOutput(ios);
        
        BufferedImage bgImage = map.getBackgroundImage();
        
        // grab tree of metadata so we can insert leveldata chunk
        IIOMetadata imageMetadata = iw.getDefaultImageMetadata(
                new ImageTypeSpecifier(bgImage),
                null);
        Element root = (Element) imageMetadata.getAsTree(imageMetadata.getNativeMetadataFormatName());
        
        // find the gg2 leveldata node, creating it if necessary
        NodeList childElements = root.getElementsByTagName(ZTXT);
        Element ztxtNode = null;
        if(childElements.getLength() > 0) {
            ztxtNode = (Element)childElements.item(0);
        } else {
            ztxtNode = new IIOMetadataNode("zTXt");
            root.appendChild(ztxtNode);
        }
        
        // TODO I'm stupid, there's no way the leveldata chunk will already
        // exist, this code is working way too hard
        Element levelDataNode = null;
        NodeList childNodes = ztxtNode.getElementsByTagName(ZTXTENTRY);
        for(int i = 0; i < childNodes.getLength(); i++) {
            Element n = (Element) childNodes.item(i);
            if(!GG2_LEVELDATA_KEYWORD_VALUE.equals(n.getAttribute(ZTXTENTRY_KEYWORD)))
                continue;
            levelDataNode = (Element) childNodes.item(i);
            break;
        }
        if(levelDataNode == null) {
            levelDataNode = new IIOMetadataNode(ZTXTENTRY);
            levelDataNode.setAttribute(ZTXTENTRY_KEYWORD, GG2_LEVELDATA_KEYWORD_VALUE);
            levelDataNode.setAttribute(ZTXTENTRY_COMPRESSIONMETHOD, ZTXTENTRY_COMPRESSIONMETHOD_VALUE);
            ztxtNode.appendChild(levelDataNode);
        }
        
        // set the leveldata
        levelDataNode.setAttribute(ZTXTENTRY_TEXT, new StdLevelDataBuilder(map).buildLevelDataString());
        
        try {
            imageMetadata.setFromTree(imageMetadata.getNativeMetadataFormatName(), root);
        } catch (IIOInvalidTreeException ex) {
            // TODO error handling
            return;
        }
        
        IIOImage iioImage = new IIOImage((RenderedImage)bgImage, null, imageMetadata);
        try {
            iw.write(iioImage);
        } catch (IOException ex) {
            // TODO error handling
            return;
        }
        
        try {
            ios.close();
        } catch (IOException ex) {}
        
        return;
    }
    
}
