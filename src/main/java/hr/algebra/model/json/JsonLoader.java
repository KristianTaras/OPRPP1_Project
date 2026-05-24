package hr.algebra.model.json;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URL;
import java.util.List;

public class JsonLoader {

    private static final String JSON_BIN_URL = "https://api.jsonbin.io/v3/qs/6a1320fbee5a733b1211786b"; //Insert JsonBin.io url
    private static final ObjectMapper mapper = new ObjectMapper();

    public static List<SmartWatchDTO> loadSmartWatches() throws Exception{
        JsonBinResponse response = mapper.readValue(new URL(JSON_BIN_URL), JsonBinResponse.class);
        return response.record;
    }

}
