package idir.embag.EventStore.Models.Workers;

import idir.embag.Types.Infrastructure.Database.IWorkerQuery;

public class WorkersModel {

    IWorkerQuery workerQuery;

    public WorkersModel(IWorkerQuery workerQuery) {
        this.workerQuery = workerQuery;
    }
    
}
