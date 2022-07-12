package idir.embag.Infrastructure;

public class StoreProvider {
    
    private static StoreProvider instance;

    private StoreProvider(){}

    public static StoreProvider getInstance(){
        if(instance == null){
            instance = new StoreProvider();
        }
        return instance;
    }

    


}
