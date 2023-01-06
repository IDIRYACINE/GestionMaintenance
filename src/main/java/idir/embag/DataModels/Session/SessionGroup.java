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
    private int supervisorId;


    public SessionGroup(int id, String name, Timestamp sessionId, ArrayList<Designation> designations,int supervisorId) {
        this.id = id;
        this.name = name;
        this.sessionId = sessionId;
        this.designations = designations;
        this.supervisorId = supervisorId;
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



    public int getSupervisorId() {
        return supervisorId;
    }

    public void setSupervisorId(int supervisorId) {
        this.supervisorId = supervisorId;
    }

    public Collection<Integer> getDesignationsIds() {
        Collection<Integer> ids = new ArrayList<>();
        designations.forEach(designation -> ids.add(designation.getDesignationId()));
        return ids;
    }

    public void setDesignations(ArrayList<Designation> designations) {
        this.designations = designations;
    }


}
