package idir.embag.DataModels.Session;

import java.sql.Timestamp;

public class SessionGroup {
    private int id;
    private String name;
    private Timestamp sessionId;

    public SessionGroup(int id, String name, Timestamp sessionId) {
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

    public Timestamp getSessionId() {
        return sessionId;
    }

    public void setName(String name) {
        this.name = name;
    }

}
