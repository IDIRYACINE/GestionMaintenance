package idir.embag.Infrastructure.ServiceProvider.Events;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import idir.embag.Infrastructure.ServiceProvider.Events.ServiceEventResponse.UnhandeledEventResponse;

@SuppressWarnings("rawtypes")
public abstract class ServiceCommand<A extends ServiceEventData, B extends RawServiceEventData, O extends ServiceEventResponse> {
    public final int commandId;
    final String commandName;

    public ServiceCommand(int commandId, String commandName) {
        this.commandId = commandId;
        this.commandName = commandName;
    }

    public abstract Future<O> handleEvent(A eventData);

    public abstract Future<O> handleRawEvent(B eventData);

    public static class EmptyCommand
            extends ServiceCommand<EmptyCommandData, EmptyCommandRawData, ServiceEventResponse.UnhandeledEventResponse> {
        public EmptyCommand(int commandId) {
            super(commandId, "EmptyCommand");
        }

        static final Future<ServiceEventResponse.UnhandeledEventResponse> emptyCommandFuture = new Future<ServiceEventResponse.UnhandeledEventResponse>() {

            @Override
            public boolean cancel(boolean mayInterruptIfRunning) {
                return false;
            }

            @Override
            public UnhandeledEventResponse get() throws InterruptedException, ExecutionException {
                return new ServiceEventResponse.UnhandeledEventResponse(0);
            }

            @Override
            public UnhandeledEventResponse get(long arg0, TimeUnit arg1) {
                return new ServiceEventResponse.UnhandeledEventResponse(0);

            }

            @Override
            public boolean isCancelled() {
                return false;
            }

            @Override
            public boolean isDone() {
                return false;
            }

        };

        @Override
        public Future<UnhandeledEventResponse> handleEvent(EmptyCommandData eventData) {
            return emptyCommandFuture;
        }

        @Override
        public Future<UnhandeledEventResponse> handleRawEvent(EmptyCommandRawData eventData) {
            return emptyCommandFuture;
        }

    }

    static class EmptyCommandData extends ServiceEventData<EmptyCommandRawData> {
        EmptyCommandData(String requesterId) {
            super(requesterId);
        }

        @Override
        public EmptyCommandRawData toRawServiceEventData() {
            return null;
        }

    }

    static class EmptyCommandRawData extends RawServiceEventData {

        public EmptyCommandRawData(String requesterId, int messageId, int eventId) {
            super(requesterId, messageId, eventId);
        }

    }
}
