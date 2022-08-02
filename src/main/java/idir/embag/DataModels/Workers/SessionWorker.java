package idir.embag.DataModels.Workers;

public class SessionWorker {
    private int workerId;
    private int Id;
    private String workerName;
    private String password;
    private String groupName;
    private int groupId;


    public SessionWorker(int workerId, String workerName, String password,String groupName,int groupId) {
        this.workerId = workerId;
        this.workerName = workerName;
        this.password = password;
        this.groupName = groupName;
        this.groupId = groupId;
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

    public int getGroupId() {
        return groupId;
    }

    public Object getId() {
        return Id;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
