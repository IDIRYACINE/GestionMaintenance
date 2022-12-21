package idir.embag.Ui.Panels.Session;

import java.net.URL;
import java.util.ResourceBundle;
import idir.embag.Application.Controllers.Session.SessionController;
import idir.embag.Application.Utility.DataBundler;
import idir.embag.DataModels.ApiBodyResponses.DSessionResponse;
import idir.embag.DataModels.Metadata.EEventsDataKeys;
import idir.embag.EventStore.Stores.StoreCenter.StoreCenter;
import idir.embag.Types.Panels.Generics.INodeView;
import idir.embag.Types.Stores.Generics.IEventSubscriber;
import idir.embag.Types.Stores.Generics.StoreDispatch.EStores;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEvents;
import idir.embag.Types.Stores.Generics.StoreEvent.StoreEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class SessionPanel extends INodeView implements Initializable,IEventSubscriber {
    
    @FXML
    private VBox root;

    private ActiveSessionFrame activeSessionFrame;

    private NoSessionFrame noSessionFrame;

    public SessionPanel() {
        fxmlPath = "/views/Panels/SessionPanel.fxml";
        SessionController controller = new SessionController();

        activeSessionFrame = new ActiveSessionFrame(controller);
        activeSessionFrame.loadFxml();

        noSessionFrame = new NoSessionFrame(controller);
        noSessionFrame.loadFxml();

        StoreCenter.getInstance().subscribeToEvents(EStores.NavigationStore, EStoreEvents.SessionEvent, this);

    }

    @Override
    public Node getView() {
        return root;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        root.getChildren().add(0, noSessionFrame.getView());
    }

    @Override
    public void notifyEvent(StoreEvent event) {

        switch(event.getAction()){
            case ApiResponse:
                DSessionResponse data = DataBundler.retrieveValue(event.getData(), EEventsDataKeys.ApiResponse);
                reactToApiResponse(data);
              break;
            case OpenSession:
                root.getChildren().set(0, activeSessionFrame.getView());
            break;
  
            default:
              break;
        }
        
    }

    private void reactToApiResponse(DSessionResponse response){
        if(response.sessionId != null){
            root.getChildren().set(0, activeSessionFrame.getView());
            return;
        }
        root.getChildren().set(0, noSessionFrame.getView());
    }
    
}
