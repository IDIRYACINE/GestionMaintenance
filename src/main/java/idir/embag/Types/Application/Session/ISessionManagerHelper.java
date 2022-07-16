package idir.embag.Types.Application.Session;

import idir.embag.Types.Stores.Generics.StoreEvent.StoreEvent;

public interface ISessionManagerHelper {
    public void add();
    public void update();
    public void delete();
    public void notifyEvent(StoreEvent event);
    public void notifyActive();
}
