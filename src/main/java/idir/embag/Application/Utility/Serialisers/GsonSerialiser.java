package idir.embag.Application.Utility.Serialisers;

import java.sql.Timestamp;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import idir.embag.DataModels.Products.InventoryProduct;
import idir.embag.DataModels.Session.SessionRecord;

public abstract class GsonSerialiser {
    private static Gson gson ;
    public static <T> T deserialise(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

    public static <T> String serialise(T object) {
        return gson.toJson(object);
    }

   

    public static void init(){
        GsonBuilder builder = new GsonBuilder();

        builder.registerTypeAdapter(Timestamp.class, new TimeStampAdapter());
        builder.registerTypeAdapter(SessionRecord.class, new SessionRecordAdapter());
        builder.registerTypeAdapter(InventoryProduct.class, new InventoryProductAdapter());


        gson = builder.create();

    }
}
