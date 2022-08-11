package idir.embag.units;

import org.junit.jupiter.api.Test;

import idir.embag.Application.Utility.GsonSerialiser;
import idir.embag.DataModels.ApiBodyResponses.DLoginResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

}
