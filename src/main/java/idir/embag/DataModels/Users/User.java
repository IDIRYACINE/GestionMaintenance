package idir.embag.DataModels.Users;

import java.util.ArrayList;

public class User {
    private int userId;
    private String userName;
    private String password;
    private boolean isAdmin;
    private ArrayList<Affectation> designations ;

    public User(int userId, String userName, String password, boolean isAdmin, ArrayList<Affectation> designations) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.isAdmin = isAdmin;
        if(designations == null)
            this.designations = new ArrayList<>();
        else
            this.designations = designations;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public void setDesignations(ArrayList<Affectation> designations) {
        this.designations = designations;
    }

    public ArrayList<Affectation> getDesignations() {
        return designations;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public ArrayList<Integer> getDesignationsIds() {
        ArrayList<Integer> ids = new ArrayList<>();
        designations.forEach(designation -> ids.add(designation.getAffectationId()));
        return ids;
    }

    public void addDesignation(Affectation designation) {
        designations.add(designation);
    }

    public void removeDesignation(Affectation designation) {
        for (int i = 0; i < designations.size(); i++) {
            if(designations.get(i).getAffectationId() == designation.getAffectationId()){
                designations.remove(i);
                break;
            }
        }
    }

}
