package idir.embag.Ui.Views.Settings;


import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class SettingsView implements ISettingsView,Initializable {

    private Pane panel ;
    public SettingsView(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/SettingsPanel.fxml"));     
            loader.load();
            panel = loader.getRoot();
            //SettingsController settingsController = loader.getController();
        }
        catch(Exception e){
    
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
        
    }

    public Pane getPanel(){
        return panel;
    }

    @Override
    public Node getView() {
        
        return panel;
    }

}
