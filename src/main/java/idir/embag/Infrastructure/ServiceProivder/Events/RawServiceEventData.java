package idir.embag.Infrastructure.ServiceProivder.Events;

public abstract class RawServiceEventData {
    final String requesterId;
    final int messageId;
    final int eventId;

    public RawServiceEventData(String requesterId, int messageId, int eventId) {
        this.requesterId = requesterId;
        this.messageId = messageId;
        this.eventId = eventId;
    }

}