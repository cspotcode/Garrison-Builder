
package com.ganggarrison.garrisonbuilder.jsonentitytypeloader;

import com.ganggarrison.garrisonbuilder.util.OutputHelper;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jackson.map.TypeDeserializer;
import org.openide.windows.IOProvider;

/**
 * JSON deserializer that reads image filename from JSON and produces Image
 * instance.
 * @author cspotcode
 */
class FilenameToImageDeserializer<Image> extends JsonDeserializer<Image> {

    @Override
    public Image deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
        // read a string value, which should be an image filename
        String filename = jp.readValueAs(String.class);
        OutputHelper.getDefault()
                .println("Read filename for an image from JSON: " + filename);
        return (Image) ImageIO.read(new File(filename));
    }

    @Override
    public Object deserializeWithType(JsonParser jp, DeserializationContext ctxt, TypeDeserializer typeDeserializer) throws IOException, JsonProcessingException {
        // I'm assuming deserializing an Image will never have any extra type
        // information.  How could it?
        return deserialize(jp, ctxt);
    }
    
}
