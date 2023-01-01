package idir.embag.Application.State;

import idir.embag.DataModels.AppState.AppStateWrapper;
import idir.embag.DataModels.Users.User;

public class AppState {
    
    private static AppState instance;
    
    private User currentUser;

    private int userCurrId;
    private int workerCurrId;
    private int sessionGroupCurrId;
    
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

    public int getUserCurrId() {
        return userCurrId;
    }

    public int getWorkerCurrId() {
        return workerCurrId;
    }

    public int getSessionGroupCurrId() {
        return sessionGroupCurrId;
    }
    
    public void nextUserCurrId() {
        userCurrId++;
    }

    public void nextWorkerCurrId() {
        workerCurrId++;
    }

    public void nextSessionGroupCurrId() {
        sessionGroupCurrId++;
    }

    public void setAppState(AppStateWrapper appStateWrapper) {
        this.userCurrId = appStateWrapper.userCurrId;
        this.workerCurrId = appStateWrapper.workerCurrId;
        this.sessionGroupCurrId = appStateWrapper.sessionGroupCurrId;
    }

}
