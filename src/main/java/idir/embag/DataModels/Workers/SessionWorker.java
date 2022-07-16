package idir.embag.DataModels.Workers;

public class SessionWorker {
    private int workerId;
    private String workerName;
    private String password;
    private String groupName;


    public SessionWorker(int workerId, String workerName, String password) {
        this.workerId = workerId;
        this.workerName = workerName;
        this.password = password;
    }

    public int getWorkerId() {
        return workerId;
    }

    public String getWorkerName() {
        return workerName;
    }

    public String getPassword() {
        return password;
    }
    
    public String getGroupName() {
        return groupName;
    }
    
}
