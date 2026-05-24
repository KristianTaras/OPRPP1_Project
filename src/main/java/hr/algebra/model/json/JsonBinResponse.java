package hr.algebra.model.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonBinResponse {
    public List<SmartWatchDTO> record;
}
