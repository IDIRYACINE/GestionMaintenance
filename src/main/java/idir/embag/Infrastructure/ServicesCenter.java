package idir.embag.Infrastructure;

import idir.embag.Infrastructure.Initialisers.DatabaseInitialiser;

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
    }


    private DatabaseInitialiser databaseInitialiser;

    public DatabaseInitialiser getDatabaseInitialiser() {
        return databaseInitialiser;
    }

}
