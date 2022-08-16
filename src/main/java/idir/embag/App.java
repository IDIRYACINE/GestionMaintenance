package idir.embag;

import java.io.IOException;
import idir.embag.Application.Controllers.Navigation.MainController;
import idir.embag.EventStore.Stores.StoreCenter.StoreCenter;
import idir.embag.Infrastructure.ServicesProvider;
import idir.embag.Types.Application.Navigation.INavigationController;
import idir.embag.Types.Stores.StoreCenter.IStoresCenter;
import idir.embag.Ui.Panels.Login.LoginPanel;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

@SuppressWarnings("unused")
public class App extends Application {
    public static App instance;

    public static StackPane stackPane;
    private static IStoresCenter storesCenter;
    private ServicesProvider servicesCenter;
    
    private Stage appStage;
    
    public App() {
        instance = this;
    }

    @Override
    public void start(Stage stage) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setController(new LoginPanel());
        loader.load(getClass().getResourceAsStream("/views/Panels/LoginPanel.fxml"));

        Parent root = loader.getRoot();
        Scene scene = new Scene(root);
        stage.setTitle("Embag ");
        stage.setScene(scene);          
        stage.show();       
        appStage = stage;
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void loadApp() throws IOException{
        Platform.runLater(() -> {
            try{
            loadSplashScreen();   

            INavigationController navigationController = new MainController();
            setup(navigationController);
        
            FXMLLoader loader = new FXMLLoader();
            loader.setController(navigationController);
            loader.load(getClass().getResourceAsStream("/views/Panels/Main.fxml"));
            Parent root = loader.getRoot();
            Scene scene = new Scene(root);
            appStage.setScene(scene);          
        }
            
        catch(Exception e){
            e.printStackTrace();
        }}); 
    }

    private void setup(INavigationController navigationController){
        servicesCenter = ServicesProvider.getInstance();
        storesCenter = StoreCenter.getInstance(servicesCenter,navigationController);        
        servicesCenter.getDatabaseInitialiser().createTables();
    }
    
    private void loadSplashScreen() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setController(null);
        loader.load(getClass().getResourceAsStream("/views/Panels/SplashScreenPanel.fxml"));
        Parent root = loader.getRoot();
        Scene scene = new Scene(root);
        appStage.setScene(scene);
       
    }

}