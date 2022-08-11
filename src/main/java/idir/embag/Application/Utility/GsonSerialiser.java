package idir.embag.Application.Utility;

import com.google.gson.Gson;

public abstract class GsonSerialiser {
    private static Gson gson = new Gson();

    public static<T> T deserialise(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

    public static<T> String serialise(T object) {
        return gson.toJson(object);
    }
}
