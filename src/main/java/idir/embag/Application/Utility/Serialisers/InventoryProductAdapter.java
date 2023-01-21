package idir.embag.Application.Utility.Serialisers;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import idir.embag.DataModels.Products.InventoryProduct;
import idir.embag.Types.Infrastructure.Database.Metadata.EInventoryAttributes;

public class InventoryProductAdapter implements JsonSerializer<InventoryProduct>, JsonDeserializer<InventoryProduct> {

    @Override
    public InventoryProduct deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject jsonO = json.getAsJsonObject();
        return new InventoryProduct(jsonO.get(EInventoryAttributes.ArticleId.name()).getAsInt(),
                jsonO.get(EInventoryAttributes.ArticleName.name()).getAsString(),
                jsonO.get(EInventoryAttributes.ArticleCode.name()).getAsInt(),
                1, jsonO.get(EInventoryAttributes.ArticlePrice.name()).getAsDouble(),
                jsonO.get(EInventoryAttributes.FamilyCode.name()).getAsInt(),
                jsonO.get(EInventoryAttributes.AffectationId.name()).getAsInt()

        );
    }

    @Override
    public JsonElement serialize(InventoryProduct src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonO = new JsonObject();
        jsonO.addProperty(EInventoryAttributes.ArticleId.name(), src.getArticleId());
        jsonO.addProperty(EInventoryAttributes.ArticleName.name(), src.getArticleName());
        jsonO.addProperty(EInventoryAttributes.ArticlePrice.name(), src.getPrice());
        jsonO.addProperty(EInventoryAttributes.AffectationId.name(), src.getAffectationId());
        jsonO.addProperty(EInventoryAttributes.FamilyCode.name(), src.getFamilyCode());

        return jsonO;
    }

}
