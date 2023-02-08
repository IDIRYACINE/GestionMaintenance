package idir.embag.Infrastructure.Server.Api.Commands.CloseSession;

import idir.embag.Infrastructure.Server.ServicesEnum;
import idir.embag.Infrastructure.Server.Api.Commands.CommandsEnum;

public abstract class CloseSessionMetadata {

    public static int eventId = CommandsEnum.closeSession.ordinal();
    public static String eventName = CommandsEnum.closeSession.name();
    public static int serviceId = ServicesEnum.api.ordinal();
}
