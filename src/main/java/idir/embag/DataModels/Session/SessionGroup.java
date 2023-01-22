package idir.embag.DataModels.Session;

import java.sql.Timestamp;
import java.util.ArrayList;

import idir.embag.DataModels.Users.Affectation;

public class SessionGroup {
    private int id;
    private String name;
    private Timestamp sessionId;
    private ArrayList<Affectation> affectations ;
    private int supervisorId;


    public SessionGroup(int id, String name, Timestamp sessionId, ArrayList<Affectation> affectations,int supervisorId) {
        this.id = id;
        this.name = name;
        this.sessionId = sessionId;
        this.affectations = affectations;
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
    

    public ArrayList<Affectation> getAffectations() {
        return affectations;
    }



    public int getSupervisorId() {
        return supervisorId;
    }

    public void setSupervisorId(int supervisorId) {
        this.supervisorId = supervisorId;
    }

    public ArrayList<Integer> getAffectationsIds() {
        ArrayList<Integer> ids = new ArrayList<>();
        affectations.forEach(designation -> ids.add(designation.getAffectationId()));
        return ids;
    }

    public void setAffectations(ArrayList<Affectation> designations) {
        this.affectations = designations;
    }


}
