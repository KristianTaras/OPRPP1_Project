package hr.algebra.model.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class JsonLoader {

    //JsonBin.io
    //npoint.io
    //tvmaze.io
    private static final String JSON_BIN_URL = "https://api.npoint.io/04288e9fd11512aa80d4"; //Insert JsonBin.io url
    private static final ObjectMapper mapper = new ObjectMapper();

    public static List<SmartWatchDTO> loadSmartWatches() throws Exception{
        SmartWatchDTO[] array = mapper.readValue(new URL(JSON_BIN_URL), SmartWatchDTO[].class);
        return Arrays.asList(array);
    }

}
