
package com.ganggarrison.garrisonbuilder.util;

import org.openide.windows.IOProvider;
import org.openide.windows.OutputWriter;

/**
 * Helper class for any code wishing to show messages to the user
 * @author cspotcode
 */
public class OutputHelper {
    /**
     * Returns the OutputWriter for the default "Output" text window.
     */
    public static OutputWriter getDefault() {
        return IOProvider.getDefault().getIO("Output", false).getOut();
    }
}
