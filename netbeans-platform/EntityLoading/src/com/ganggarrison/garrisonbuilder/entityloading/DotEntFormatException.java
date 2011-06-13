
package com.ganggarrison.garrisonbuilder.entityloading;

/**
 * Exception thrown when legacy saved entity data (contents of a .ent file or
 * {ENTITIES} section embedded in a PNG map) is invalid
 * @author cspotcode
 */
public class DotEntFormatException extends Exception {

    DotEntFormatException(String message) {
        super(message);
    }
    
}
