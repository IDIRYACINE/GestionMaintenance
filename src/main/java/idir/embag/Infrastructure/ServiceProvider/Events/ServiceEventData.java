package idir.embag.Infrastructure.ServiceProvider.Events;

public abstract class ServiceEventData<T extends RawServiceEventData> {
    public final String requesterId;
    public int messageId = 0;

    public ServiceEventData(String requesterId, int messageId) {
        this.requesterId = requesterId;
        this.messageId = messageId;
    }

    public ServiceEventData(String requesterId) {
        this.requesterId = requesterId;
    }

    public abstract T toRawServiceEventData();

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

}
