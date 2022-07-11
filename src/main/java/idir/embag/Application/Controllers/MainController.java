package idir.embag.Application.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import idir.embag.App;
import idir.embag.Application.Views.Settings.SettingsView;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.fxml.FXML;

public class MainController implements Initializable{

    @FXML
    private StackPane rightPanel ;
    @FXML
    private VBox leftPanel ;
    @FXML
    private FontAwesomeIconView checkIcon , dataIcon,exportIcon ,settingsIcon ;
    @FXML 
    private Label dataLabel , checkLabel , exportLabel , settingsLabel;
    @FXML
    private HBox dataBox , checkBox , exportBox , settingsBox;

    private Label activeLabel ;
    private FontAwesomeIconView activeIcon ;
    private HBox activeBox;

    private VBox dataPanel , checkPanel , exportPanel ;

    private Pane settingsPanel ;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
          try {
            loadPanels();
            App.stackPane = rightPanel;
          } catch (IOException e) {
            e.printStackTrace();
          }
          setInitialScene();
          
    }
     
    @FXML
    private void dataClicked( ) throws IOException{
      setNode(dataPanel);
      setActiveStyle(dataBox,dataLabel, dataIcon);
    }
    
    @FXML
    private void checkClicked( ) throws IOException{
      setNode(checkPanel);
      setActiveStyle(checkBox,checkLabel, checkIcon);
    }
    @FXML
    private void exportClicked( ) throws IOException{
      setNode(exportPanel);
      setActiveStyle(exportBox,exportLabel, exportIcon);
    }
    
    @FXML
    private void settingsClicked()throws IOException{
      setNode(settingsPanel);
      setActiveStyle(settingsBox, settingsLabel , settingsIcon);
    }

    private void loadPanels() throws IOException{
       
      SettingsView settingsView = new SettingsView(); 
      settingsPanel = settingsView.getPanel();

    }



    private void setNode(Node node){
      rightPanel.getChildren().clear();
      rightPanel.getChildren().add(node);

    }

    private void setActiveStyle(HBox hBox,Label label , FontAwesomeIconView icon){
    
     setPassiveStyle(activeBox,activeLabel, activeIcon);
      label.setStyle("-fx-text-fill:#3683dc;");
      icon.setStyle("-fx-fill:#3683dc;");
      hBox.setStyle("-fx-background-color: #eaf4fe;");
      activeIcon = icon;
      activeLabel = label;
      activeBox = hBox;
     
    }

    private void setPassiveStyle(HBox hBox,Label label , FontAwesomeIconView icon ){
      label.setStyle("-fx-text-fill:#818893;");
      icon.setStyle("-fx-fill:#818893;");
      hBox.setStyle("-fx-background-color: ffffff");
    }
    private void setInitialScene(){
      activeLabel = dataLabel;
      activeIcon = dataIcon;
      activeBox = dataBox;
      String css = getClass().getResource("/css/main.css").toExternalForm();
      leftPanel.getStylesheets().add(css);
      setActiveStyle(dataBox, dataLabel, dataIcon);
      setNode(dataPanel);

    };

  
    
}
