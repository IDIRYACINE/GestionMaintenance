package idir.embag.Application.Controllers.Settings;

import java.net.URL;
import java.util.ResourceBundle;

import idir.embag.Application.Models.Settings.SettingsModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

public class SettingsController implements Initializable{

    private SettingsModel settingsModel;

    @FXML
    private TextField amountX ,locationX,dateX ,receiverX, firstStrAmountX , secondStrAmountX ,scaleX ;

    @FXML
    private TextField amountY ,locationY,dateY,receiverY ,firstStrAmountY , secondStrAmountY  , scaleY  ;
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       settingsModel = SettingsModel.getInstance();
       loadInitialSettings();
    }

    @FXML
    private void saveSettings(){
        settingsModel.updateAmountCoordinates(amountX.getText(), amountY.getText());
        settingsModel.updateDateCoordinates(dateX.getText(), dateY.getText());
        settingsModel.updateLocationCoordinates(locationX.getText(), locationY.getText());
        settingsModel.updatefirstStrAmountCoordinates(firstStrAmountX.getText(), firstStrAmountY.getText());
        settingsModel.updateReceiverCoordinates(receiverX.getText(), receiverY.getText());
        settingsModel.updatesecondStrAmountCoordinates(secondStrAmountX.getText(), secondStrAmountY.getText());
        settingsModel.updateScaleCoordinates(scaleX.getText(), scaleY.getText());
        settingsModel.saveSettings();
    }

    @FXML
    private void resetSettings(){
        settingsModel.resetSettings();
        loadInitialSettings();

    }

   private void loadInitialSettings(){

        String[] xCoordinates = settingsModel.getXcoordinates();
        String[] yCoordinates = settingsModel.getYcoordinates();
        TextField[] xFields = {amountX ,firstStrAmountX , secondStrAmountX,receiverX,locationX,dateX,scaleX};
        TextField[] yFields = {amountY,firstStrAmountY,secondStrAmountY,receiverY,locationY,dateY,scaleY};
        for (int i = 0 ; i < xCoordinates.length;i++){
            xFields[i].setText(xCoordinates[i]);
            yFields[i].setText(yCoordinates[i]);
            
        }

    }
    
}
