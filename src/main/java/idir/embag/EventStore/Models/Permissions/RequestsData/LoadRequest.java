package idir.embag.EventStore.Models.Permissions.RequestsData;

import idir.embag.DataModels.Session.SessionGroup;

public class LoadRequest {
    
    private boolean loadGroups;
    private boolean loadAllDesignations;
    private boolean LoadGroupUngrantedDesignations;
    private SessionGroup group;

  

    public static LoadRequest loadGroups(){
        LoadRequest loadRequest = new LoadRequest();

        loadRequest.loadGroups = true;
        loadRequest.loadAllDesignations = false;
        loadRequest.LoadGroupUngrantedDesignations = false;

        return loadRequest;
    }
    
    public static LoadRequest loadAllDesignations(){
        LoadRequest loadRequest = new LoadRequest();

        loadRequest.loadGroups = false;
        loadRequest.loadAllDesignations = true;
        loadRequest.LoadGroupUngrantedDesignations = false;

        return loadRequest;
    }

    public static LoadRequest loadGroupUngrantedDesignationsRequest(SessionGroup group){
        LoadRequest loadRequest = new LoadRequest();

        loadRequest.loadGroups = false;
        loadRequest.loadAllDesignations = false;
        loadRequest.LoadGroupUngrantedDesignations = true;
        loadRequest.group = group;

        return loadRequest;
    }

    public boolean isLoadGroups() {
        return loadGroups;
    }

    public boolean isLoadAllDesignations() {
        return loadAllDesignations;
    }

    public boolean isLoadGroupUngrantedDesignations() {
        return LoadGroupUngrantedDesignations;
    }

    public SessionGroup getGroup() {
        return group;
    }

    
}
