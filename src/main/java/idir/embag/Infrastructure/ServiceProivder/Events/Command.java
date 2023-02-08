package idir.embag.Infrastructure.ServiceProivder.Events;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import idir.embag.Infrastructure.ServiceProivder.Events.ServiceEventResponse.UnhandeledEventResponse;

@SuppressWarnings("rawtypes")
public abstract class Command<A extends ServiceEventData, B extends RawServiceEventData, O extends ServiceEventResponse> {
    public final int commandId;
    final String commandName;

    Command(int commandId, String commandName) {
        this.commandId = commandId;
        this.commandName = commandName;
    }

    abstract Future<O> handleEvent(A eventData);

    abstract Future<O> handleRawEvent(B eventData);

    public static class EmptyCommand
            extends Command<EmptyCommandData, EmptyCommandRawData, ServiceEventResponse.UnhandeledEventResponse> {
        EmptyCommand(int commandId) {
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
        Future<UnhandeledEventResponse> handleEvent(EmptyCommandData eventData) {
            return emptyCommandFuture;
        }

        @Override
        Future<UnhandeledEventResponse> handleRawEvent(EmptyCommandRawData eventData) {
            return emptyCommandFuture;
        }

    }

    static class EmptyCommandData extends ServiceEventData<EmptyCommandRawData> {
        EmptyCommandData(String requesterId) {
            super(requesterId);
        }

        @Override
        EmptyCommandRawData toRawServiceEventData() {
            return null;
        }

    }

    static class EmptyCommandRawData extends RawServiceEventData {

        public EmptyCommandRawData(String requesterId, int messageId, int eventId) {
            super(requesterId, messageId, eventId);
        }

    }
}
