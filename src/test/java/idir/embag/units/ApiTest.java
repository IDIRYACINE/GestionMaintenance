package idir.embag.units;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.sql.Timestamp;

import org.junit.jupiter.api.Test;

import idir.embag.Application.Utility.GsonSerialiser;
import idir.embag.DataModels.Session.Session;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

class ApiTest {

    @Test
    void openSession() {

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        Session session = new Session(timestamp, false,
                null, null, 0.0, 0.0);

        OkHttpClient client = new OkHttpClient();
        String json = GsonSerialiser.serialise(session);

        MediaType mType = MediaType.parse("application/json");

        Request request = new Request.Builder()
                .url("http://localhost:3050/api/testPost")
                .post(RequestBody.create(json, mType))
                .addHeader("content-type", "application/json")
                .build();

            
        try {
            Response response = client.newCall(request).execute();
            assertEquals(json, response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}