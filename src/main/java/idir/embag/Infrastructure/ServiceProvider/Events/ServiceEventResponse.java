package idir.embag.Infrastructure.ServiceProvider.Events;

public abstract class ServiceEventResponse {
    final int messageId;
    final ServiceEventResponseStatus responseType;

    public ServiceEventResponse(int messageId,
            ServiceEventResponseStatus responseType) {
        this.messageId = messageId;
        this.responseType = responseType;
    }

    static public class UnhandeledEventResponse extends ServiceEventResponse {
        public UnhandeledEventResponse(int messageId) {
            super(messageId, ServiceEventResponseStatus.unhandled);
        }
    }

}
