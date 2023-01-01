package idir.embag.DataModels.Session;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

import idir.embag.DataModels.Users.Designation;

public class SessionGroup {
    private int id;
    private String name;
    private Timestamp sessionId;
    private ArrayList<Designation> designations ;


    public SessionGroup(int id, String name, Timestamp sessionId, ArrayList<Designation> designations) {
        this.id = id;
        this.name = name;
        this.sessionId = sessionId;
        this.designations = designations;
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
    

    public ArrayList<Designation> getDesignations() {
        return designations;
    }



    public Collection<Integer> getDesignationsIds() {
        Collection<Integer> ids = new ArrayList<>();
        designations.forEach(designation -> ids.add(designation.getDesignationId()));
        return ids;
    }


}
