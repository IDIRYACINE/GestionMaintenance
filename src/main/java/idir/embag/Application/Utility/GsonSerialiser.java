package idir.embag.Application.Utility;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public abstract class GsonSerialiser {
    private static Gson gson = new GsonBuilder().registerTypeAdapter(Timestamp.class, new GsonSerialiser.TimestampTypeAdapter()).create();

    public static <T> T deserialise(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

    public static <T> String serialise(T object) {
        return gson.toJson(object);
    }

    private static class TimestampTypeAdapter implements JsonSerializer<Timestamp>, JsonDeserializer<Timestamp> {

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
}
