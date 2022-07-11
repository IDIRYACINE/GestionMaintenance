package idir.embag.EventStore.Models.Session;

import idir.embag.DataModels.Session.SessionGroup;
import idir.embag.DataModels.Workers.ESessionWorker;
import idir.embag.DataModels.Workers.SessionRecord;
import idir.embag.DataModels.Workers.SessionWorker;
import idir.embag.DataModels.Workers.Worker;

public interface ISessionModel {
    
    public boolean doesSessionExists();
    public void createSession();
    public void closeSession();
    public void refreshSession();

    public void createWorkersGroup(SessionGroup group);
    public void addWorkerToGroup(Worker worker);
    public void removeWorkerFromGroup(SessionWorker worker);
    public void updateWorker(SessionWorker worker , ESessionWorker[] updatedFields , String[] updatedValues);

    public void receiveSessionRecord(SessionRecord record);
    
    
}
