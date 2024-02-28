package pl.edu.pw.zpoif.exchangeit.util.alphavintageapi.parser;

import com.fasterxml.jackson.databind.JsonNode;
import pl.edu.pw.zpoif.exchangeit.constants.alphavintageapi.Endpoint;
import pl.edu.pw.zpoif.exchangeit.model.alphavintage.AlphaVintageObject;
import pl.edu.pw.zpoif.exchangeit.model.alphavintage.GlobalQuote;

import java.util.List;

public class GlobalQuoteParser extends JsonParser {

    public static List<AlphaVintageObject> parse(Endpoint endpoint, JsonNode root) {
        JsonNode data = root.get("Global Quote");
        return List.of(OBJECT_MAPPER.convertValue(data, GlobalQuote.class));
    }


}
