
package com.ganggarrison.garrisonbuilder.gg2mapsavesupport;

/**
 * Creates the textual representation of a walkmask that gets embedded into a
 * PNG map file.
 * @author cspotcode
 */
public interface WalkmaskStringBuilder {
    
    /**
     * Builds string representation of the walkmask, ready for inserting into
     * embedded leveldata.
     * @return 
     */
    public String buildWalkmaskString();
    
}
