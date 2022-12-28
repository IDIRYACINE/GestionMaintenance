package idir.embag.DataModels.Users;

import java.util.ArrayList;

public class User {
    private int userId;
    private String userName;
    private String password;
    private boolean isAdmin;
    private ArrayList<Designation> designations;

    public User(int userId, String userName, String password, boolean isAdmin, ArrayList<Designation> designations) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.designations = designations;
        this.isAdmin = isAdmin;
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

    public void setDesignations(ArrayList<Designation> designations) {
        this.designations = designations;
    }

    public ArrayList<Designation> getDesignations() {
        return designations;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

}
