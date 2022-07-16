package idir.embag.DataModels.Session;

public class SessionGroup {
    private int id;
    private String name;
    private int sessionId;

    public SessionGroup(int id, String name, int sessionId) {
        this.id = id;
        this.name = name;
        this.sessionId = sessionId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getSessionId() {
        return sessionId;
    }

    

}
