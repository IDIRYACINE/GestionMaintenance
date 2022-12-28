package idir.embag.Application.State;

import idir.embag.DataModels.Users.User;

public class AppState {
    
    private static AppState instance;
    
    private User currentUser;
    
    private AppState() {
        
    }
    
    public static AppState getInstance() {
        if(instance == null) {
            instance = new AppState();
        }
        return instance;
    }


    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
    

}
