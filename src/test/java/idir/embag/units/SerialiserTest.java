package idir.embag.units;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Timestamp;

import org.junit.jupiter.api.Test;

import idir.embag.Application.Utility.GsonSerialiser;
import idir.embag.DataModels.ApiBodyResponses.DLoginResponse;

public class SerialiserTest {

    @Test
    public void deserialise() {
        String json = "{\"isAutherised\":true}";
        DLoginResponse parsedResponse = GsonSerialiser.deserialise(json, DLoginResponse.class);
        assertEquals(true, parsedResponse.isAutherised);
    }

    @Test
    public void serialise() {
        DLoginResponse temp = new DLoginResponse();
        temp.isAutherised = false;

        String json = GsonSerialiser.serialise(temp);
        String expected = "{\"isAutherised\":false}";

        assertEquals(expected, json);
    }

    @Test
    public void serialiseTimeStampWithGson(){
       

        Timestamp timestamp = new Timestamp(1671377892091l);
        String json = GsonSerialiser.serialise(timestamp); 
        json = json.replace("\"", "");   
        assertEquals(timestamp.toString(),json);
    }

}
