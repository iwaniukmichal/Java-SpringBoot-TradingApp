package pl.edu.pw.zpoif.exchangeit.model.alphavintage;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.databind.JsonNode;

public abstract class AlphaVintageObject {
    @JsonAnySetter
    public void handleUnknownField(String key, JsonNode value) {
        System.out.println("Unknown field: " + key + " with value: " + value);
    }
}
