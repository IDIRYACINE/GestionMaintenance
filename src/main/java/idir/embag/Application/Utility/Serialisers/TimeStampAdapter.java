package idir.embag.Application.Utility.Serialisers;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class TimeStampAdapter implements JsonSerializer<Timestamp>, JsonDeserializer<Timestamp> {

    @Override
    public JsonElement serialize(Timestamp src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.toString());
    }

    @Override
    public Timestamp deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        return Timestamp.valueOf(json.getAsString());
    }

}
