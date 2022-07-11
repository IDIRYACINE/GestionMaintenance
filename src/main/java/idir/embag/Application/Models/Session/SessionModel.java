package idir.embag.Application.Models.Session;

import idir.embag.DataModels.Session.SessionGroup;
import idir.embag.DataModels.Workers.ESessionWorker;
import idir.embag.DataModels.Workers.SessionRecord;
import idir.embag.DataModels.Workers.SessionWorker;
import idir.embag.DataModels.Workers.Worker;

public class SessionModel implements ISessionModel{

    @Override
    public boolean doesSessionExists() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void createSession() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void closeSession() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void refreshSession() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void createWorkersGroup(SessionGroup group) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void addWorkerToGroup(Worker worker) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void removeWorkerFromGroup(SessionWorker worker) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void updateWorker(SessionWorker worker, ESessionWorker[] updatedFields, String[] updatedValues) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void receiveSessionRecord(SessionRecord record) {
        // TODO Auto-generated method stub
        
    }
    
}
