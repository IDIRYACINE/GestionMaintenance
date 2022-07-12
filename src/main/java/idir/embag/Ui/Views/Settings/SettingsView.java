package idir.embag.Ui.Views.Settings;


import java.net.URL;
import java.util.ResourceBundle;

import idir.embag.Application.Controllers.Settings.SettingsController;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class SettingsView implements ISettingsView,Initializable {

    private Pane panel ;

    public SettingsView(){
      
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
        
    }


    @Override
    public Node getView() {        
        return panel;
    }


    @Override
    public void loadFxml() {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/SettingsPanel.fxml"));   
            SettingsController controller = new SettingsController();
            loader.setController(controller);  
            panel = loader.load();
          
        }
        catch(Exception e){
    
        }
        
    }

}
