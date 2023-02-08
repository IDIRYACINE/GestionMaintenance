package idir.embag.Infrastructure.ServiceProvider.Events;

abstract class ServiceEventData<T extends RawServiceEventData> {
    final String requesterId;
    int messageId = 0;

    public ServiceEventData(String requesterId, int messageId) {
        this.requesterId = requesterId;
        this.messageId = messageId;
    }

    public ServiceEventData(String requesterId) {
        this.requesterId = requesterId;
    }

    abstract T toRawServiceEventData();

    void setMessageId(int messageId) {
        this.messageId = messageId;
    }

}
