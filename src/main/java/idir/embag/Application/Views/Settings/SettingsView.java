package idir.embag.Application.Views.Settings;


import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

public class SettingsView {

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

    public Pane getPanel(){
        return panel;
    }
}
