package idir.embag.Infrastructure.Server.Api.Commands.Login;

import idir.embag.Infrastructure.Server.Api.ApiWrappers.LoginWrapper;
import idir.embag.Infrastructure.Server.Api.Commands.CommandsEnum;
import idir.embag.Infrastructure.ServiceProvider.Events.SimpleServiceEvent;

public class LoginEvent extends SimpleServiceEvent<LoginWrapper>{
    public static final int eventId = CommandsEnum.Login.ordinal();

    public LoginEvent(String requesterName, LoginWrapper data) {
        super(requesterName, data);
    }

    @Override
    public int getEventId() {
        return eventId;
    }
    
}
