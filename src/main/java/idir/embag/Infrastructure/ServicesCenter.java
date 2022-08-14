package idir.embag.Infrastructure;

import idir.embag.Infrastructure.Initialisers.DatabaseInitialiser;
import idir.embag.Infrastructure.Server.Server;
import idir.embag.Types.Infrastructure.Server.IServer;

public class ServicesCenter {
    
    private static ServicesCenter instance;

    public static ServicesCenter getInstance(){
        if(instance == null){
            instance = new ServicesCenter();
        }
        return instance;
    }

    
    
    private ServicesCenter() {
        databaseInitialiser = new DatabaseInitialiser();
        remoteServer =  new Server("localhost",3000, "embag343adminvcs", 0);
    }


    private DatabaseInitialiser databaseInitialiser;

    public DatabaseInitialiser getDatabaseInitialiser() {
        return databaseInitialiser;
    }

    private IServer remoteServer;

    public IServer getRemoteServer() {
        return remoteServer;
    }
    
}
