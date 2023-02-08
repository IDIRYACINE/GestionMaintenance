package idir.embag.Infrastructure.ServiceProvider.Events;

public abstract class SimpleServiceCommand<D,E extends SimpleServiceEvent<D>> {

    public abstract void execute(E event);

    public abstract int getEventId();

    static public class EmptyCommand extends SimpleServiceCommand<Void, SimpleServiceEvent<Void>> {

        @Override
        public void execute(SimpleServiceEvent<Void> event) {
            throw new UnsupportedOperationException("Empty command");
        }

        @Override
        public int getEventId() {
            return 0;
        }
        
    }

}
