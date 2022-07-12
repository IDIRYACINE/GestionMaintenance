package idir.embag.Ui.Panels.Generics;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

public abstract class INodeView {
    protected String fxmlPath;

    public abstract Node getView();

    public void loadFxml(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));   
            loader.setController(this);  
            loader.load();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

}
