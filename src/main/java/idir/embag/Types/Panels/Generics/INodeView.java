package idir.embag.Types.Panels.Generics;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

public abstract class INodeView {
    protected String fxmlPath;
    protected Object controller = this;

    public abstract Node getView();

    public void loadFxml(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));   
            loader.setController(controller);  
            loader.load();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

}
