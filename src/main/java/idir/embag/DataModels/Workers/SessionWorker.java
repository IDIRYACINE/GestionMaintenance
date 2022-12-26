package idir.embag.DataModels.Workers;

public class SessionWorker {
    private int workerId;
    private int id;
    private String workerName;
    private String password;
    private String groupName;
    private int groupId;
    private String username;


    public SessionWorker(int workerId, String workerName, String username,String password,String groupName,int groupId) {
        this.workerId = workerId;
        this.workerName = workerName;
        this.password = password;
        this.groupName = groupName;
        this.groupId = groupId;
        this.username = username;
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
        return id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
}
