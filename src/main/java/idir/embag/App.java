package idir.embag;



import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import idir.embag.Application.Controllers.Navigation.MainController;
import idir.embag.EventStore.Stores.StoreCenter.StoreCenter;
import idir.embag.Infrastructure.ServicesCenter;
import idir.embag.Types.Application.Navigation.INavigationController;
import idir.embag.Types.Stores.StoreCenter.IStoresCenter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

@SuppressWarnings("unused")
public class App extends Application {
    public static ExecutorService executorService = Executors.newSingleThreadExecutor();
    public static StackPane stackPane;

    private IStoresCenter storesCenter;
    private ServicesCenter servicesCenter;

    @Override
    public void start(Stage stage) throws IOException{

        INavigationController navigationController = new MainController();
        setup(navigationController);
       
        FXMLLoader loader = new FXMLLoader();
        loader.setController(navigationController);
        loader.load(getClass().getResourceAsStream("/views/Main.fxml"));
        Parent root = loader.getRoot();
        Scene scene = new Scene(root);
        stage.setTitle("Embag ");
        stage.setScene(scene);          
        stage.show();       

       
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void setup(INavigationController navigationController){
        servicesCenter = ServicesCenter.getInstance();
        storesCenter = StoreCenter.getInstance(servicesCenter,navigationController);        
        servicesCenter.getDatabaseInitialiser().createTables();
    }
   

}