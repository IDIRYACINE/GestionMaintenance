package idir.embag.Infrastructure.Server.Api.ApiWrappers;

import java.util.ArrayList;

import okhttp3.HttpUrl.Builder;
import idir.embag.Application.Utility.Serialisers.GsonSerialiser;
import idir.embag.Types.Api.EApi;
import idir.embag.Types.Api.IApiWrapper;

public class FetchActiveSessionRecordsWrapper extends IApiWrapper{
    private int limit;
    private int offset;
    private ArrayList<Integer> permissions;

    public FetchActiveSessionRecordsWrapper(int limit, int offset,ArrayList<Integer> permissions) {
        this.limit = limit;
        this.offset = offset;
        this.permissions = permissions;
        api = EApi.fetchActiveSessionRecords;
    }

    public int getLimit() {
        return limit;
    }

    public int getOffset() {
        return offset;
    }

    public ArrayList<Integer> getPermissions() {
        return permissions;
    }

    @Override
    public Builder getApiUrl() {
        Builder urlBuilder =  super.getApiUrl();
        String sPermissions = GsonSerialiser.serialise(permissions);

        urlBuilder.addQueryParameter("permissions", sPermissions);
        urlBuilder.addQueryParameter("offset", String.valueOf(offset));
        urlBuilder.addQueryParameter("limit", String.valueOf(limit));
        return urlBuilder;
    }



  
        
    }

    
    
