package idir.embag.Application.Controllers.Settings;

import java.net.URL;
import java.util.ResourceBundle;

import idir.embag.Application.Models.Settings.SettingsModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class SettingsController implements Initializable{

    private SettingsModel settingsModel;

    
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
     
    }

    @FXML
    private void recoverDatabase(){}

    @FXML
    private void backupDatabase(){}

    @FXML
    private void synchroniseClientsDatabase(){}
    
}
