package idir.embag.Infrastructure.ServiceProivder.Events;

import java.util.function.Consumer;

public abstract class ServiceEvent<E extends RawServiceEventData, R extends ServiceEventResponse> {
    public final int serviceId;
    final int eventId;
    final String eventName;

    final ServiceEventData<E> eventData;

    final Consumer<R> callback;

    public ServiceEvent(int serviceId, int eventId, String eventName, ServiceEventData<E> eventData,
            Consumer<R> callback) {
        this.serviceId = serviceId;
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventData = eventData;
        this.callback = callback;
    }

}