package idir.embag.Infrastructure.ServiceProvider.Events;

public abstract class SimpleServiceEvent<T> {
    private String requesterName;

    T data;

    public SimpleServiceEvent(String requesterName, T data) {
        this.requesterName = requesterName;
        this.data = data;
    }

    public abstract int getEventId();

    public int getServiceId() {
        return 0;
    }

    public String getRequesterName() {
        return requesterName;
    }

    public T getData() {
        return data;
    }

}
