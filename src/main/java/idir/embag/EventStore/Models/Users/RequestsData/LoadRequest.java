package idir.embag.EventStore.Models.Users.RequestsData;

import idir.embag.DataModels.Users.User;

public class LoadRequest {
    
    private boolean loadUser;
    private boolean loadAllDesignations;
    private boolean LoadUserUngrantedDesignations;
    private User user;

  

    public static LoadRequest loadUserRequest(){
        LoadRequest loadRequest = new LoadRequest();

        loadRequest.loadUser = true;
        loadRequest.loadAllDesignations = false;
        loadRequest.LoadUserUngrantedDesignations = false;

        return loadRequest;
    }
    
    public static LoadRequest loadAllDesignations(){
        LoadRequest loadRequest = new LoadRequest();

        loadRequest.loadUser = false;
        loadRequest.loadAllDesignations = true;
        loadRequest.LoadUserUngrantedDesignations = false;

        return loadRequest;
    }

    public static LoadRequest loadUserUngrantedDesignationsRequest(User user){
        LoadRequest loadRequest = new LoadRequest();

        loadRequest.loadUser = false;
        loadRequest.loadAllDesignations = false;
        loadRequest.LoadUserUngrantedDesignations = true;
        loadRequest.user = user;

        return loadRequest;
    }

    public boolean isLoadUsers() {
        return loadUser;
    }

    public boolean isLoadAllDesignations() {
        return loadAllDesignations;
    }

    public boolean isLoadUserUngrantedDesignations() {
        return LoadUserUngrantedDesignations;
    }

    public User getUser() {
        return user;
    }

    
}
