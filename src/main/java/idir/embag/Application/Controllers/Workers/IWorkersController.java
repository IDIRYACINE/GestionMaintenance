package idir.embag.Application.Controllers.Workers;

import idir.embag.EventStore.Stores.Generics.StoreEvent.StoreEvent;

public interface IWorkersController {

    public void notifyActive();
    public void notifyEvent(StoreEvent event);
    public void addWorker();
    public void updateWorker();
    public void archiveWorker();
    public void addWorkerToSession();
    public void searchWorkers();

}
