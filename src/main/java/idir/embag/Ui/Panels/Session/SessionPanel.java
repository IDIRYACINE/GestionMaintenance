package idir.embag.Ui.Panels.Session;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import idir.embag.DataModels.Products.EProductAttributes;
import idir.embag.DataModels.Workers.SessionRecord;
import idir.embag.DataModels.Workers.Worker;
import idir.embag.Ui.Components.FilterDialog;
import idir.embag.Ui.Panels.Generics.INodeView;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialogBuilder;
import io.github.palexdev.materialfx.dialogs.MFXStageDialog;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class SessionPanel extends INodeView implements Initializable {
    
    @FXML
    private VBox root;

    @FXML
    private MFXButton btnAdd, btnEdit, btnDelete, btnRefresh,btnSearch;
    
    @FXML
    private MFXTableView<SessionRecord> tableStock;

   

    public SessionPanel() {
        fxmlPath = "/views/SessionPanel.fxml";
    }

    @Override
    public Node getView() {
        return root;
    }


    @FXML
    private void manageWorkers(){
       
        List<EProductAttributes> workers =  new ArrayList<EProductAttributes>();
        workers.add(EProductAttributes.ArticleCode);
        FilterDialog<EProductAttributes> fDialog = new FilterDialog<EProductAttributes>(workers);
        fDialog.loadFxml();
        
        
        MFXStageDialog dialog = MFXGenericDialogBuilder.build()
        .setContent(fDialog.getView())
        .toStageDialogBuilder()
        .get();

        dialog.show();
       
       
        
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
    
}
