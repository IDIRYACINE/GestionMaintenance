package idir.embag.Application.Utility.Serialisers;

import java.lang.reflect.Type;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import idir.embag.DataModels.Session.SessionRecord;
import idir.embag.Types.Infrastructure.Database.Metadata.EInventoryAttributes;
import idir.embag.Types.Infrastructure.Database.Metadata.ESessionRecordAttributes;

public class SessionRecordAdapter implements JsonSerializer<SessionRecord>, JsonDeserializer<SessionRecord> {

    @Override
    public JsonElement serialize(SessionRecord src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonO = new JsonObject();
        jsonO.addProperty(EInventoryAttributes.ArticleId.name(), src.getArticleId());
        jsonO.addProperty(ESessionRecordAttributes.ArticleName.name(), src.getArticleName());
        jsonO.addProperty(ESessionRecordAttributes.RecordDate.name(), src.getDate());
        jsonO.addProperty(ESessionRecordAttributes.StockPrice.name(), src.getPrix());
        jsonO.addProperty(ESessionRecordAttributes.StockQuantity.name(), src.getQuantityStock());
        jsonO.addProperty(ESessionRecordAttributes.RecordQuantity.name(), src.getQuantityInventory());
        jsonO.addProperty(ESessionRecordAttributes.QuantityShift.name(), src.getQuantityShift());
        jsonO.addProperty(ESessionRecordAttributes.PriceShift.name(), src.getPriceShift());
        jsonO.addProperty(ESessionRecordAttributes.GroupId.name(), src.getGroupId());
        jsonO.addProperty(ESessionRecordAttributes.WorkerName.name(), src.getworkerName());

        return jsonO;
    }

    @Override
    public SessionRecord deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {

        JsonObject jsonO = json.getAsJsonObject();
        return new SessionRecord(
                jsonO.get("InventoryId").getAsInt(),
                jsonO.get(ESessionRecordAttributes.ArticleName.name()).getAsString(),
                jsonO.get(ESessionRecordAttributes.RecordDate.name()).getAsString(),
                jsonO.get(ESessionRecordAttributes.StockPrice.name()).getAsString(),
                jsonO.get(ESessionRecordAttributes.StockQuantity.name()).getAsString(),
                jsonO.get(ESessionRecordAttributes.RecordQuantity.name()).getAsString(),
                jsonO.get(ESessionRecordAttributes.QuantityShift.name()).getAsString(),
                jsonO.get(ESessionRecordAttributes.PriceShift.name()).getAsString(),
                jsonO.get(ESessionRecordAttributes.GroupId.name()).getAsString(),
                jsonO.get(ESessionRecordAttributes.WorkerName.name()).getAsString());
    }

}
