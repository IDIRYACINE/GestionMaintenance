package idir.embag.Types.Application.Workers;

import idir.embag.DataModels.Workers.Worker;

public interface IWorkersController {

    public void notifyActive();
    public void add();
    public void update(Worker worker);
    public void archive(Worker worker);
    public void addWorkerToSession(Worker worker);
    public void searchWorkers();
    public void refresh();


}
