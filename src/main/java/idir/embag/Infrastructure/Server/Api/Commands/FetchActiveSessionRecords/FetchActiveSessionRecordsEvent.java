package idir.embag.Infrastructure.Server.Api.Commands.FetchActiveSessionRecords;

import idir.embag.Infrastructure.Server.Api.ApiWrappers.FetchActiveSessionRecordsWrapper;
import idir.embag.Infrastructure.Server.Api.Commands.CommandsEnum;
import idir.embag.Infrastructure.ServiceProvider.Events.SimpleServiceEvent;

public class FetchActiveSessionRecordsEvent extends SimpleServiceEvent<FetchActiveSessionRecordsWrapper>{
    public static final int eventId = CommandsEnum.FetchActiveSessionRecords.ordinal();
    
    public FetchActiveSessionRecordsEvent(String requesterName, FetchActiveSessionRecordsWrapper data) {
        super(requesterName, data);
    }

    @Override
    public int getEventId() {
        return eventId;
    }
}
