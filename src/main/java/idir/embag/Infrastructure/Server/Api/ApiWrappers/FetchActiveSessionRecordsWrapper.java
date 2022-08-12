package idir.embag.Infrastructure.Server.Api.ApiWrappers;

import idir.embag.Types.Api.EApi;
import idir.embag.Types.Api.IApiWrapper;

public class FetchActiveSessionRecordsWrapper extends IApiWrapper{
    private int limit;
    private int offset;

    public FetchActiveSessionRecordsWrapper(int limit, int offset) {
        this.limit = limit;
        this.offset = offset;
        api = EApi.fetchActiveSessionRecords;
    }

    public int getLimit() {
        return limit;
    }

    public int getOffset() {
        return offset;
    }
    
}
