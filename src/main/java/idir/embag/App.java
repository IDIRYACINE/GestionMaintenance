package idir.embag;



import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {
    public static ExecutorService executorService = Executors.newSingleThreadExecutor();
    public static StackPane stackPane;
    
    @Override
    public void start(Stage stage) throws IOException{
        FXMLLoader loader = new FXMLLoader();
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

   

}