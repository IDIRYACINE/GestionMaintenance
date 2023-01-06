package idir.embag.Ui.Dialogs.MangerDialog;

import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import idir.embag.DataModels.Metadata.EEventsDataKeys;
import idir.embag.DataModels.Session.SessionGroup;
import idir.embag.DataModels.Workers.SessionWorker;
import idir.embag.DataModels.Workers.Worker;
import idir.embag.Types.Panels.Components.IDialogContent;
import idir.embag.Types.Panels.Generics.INodeView;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

public class AddSessionWorkerDialog extends INodeView implements Initializable, IDialogContent {

    @FXML
    private VBox root;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private MFXComboBox<SessionGroup> groupComboBox;

    private Runnable cancelTask;

    private Consumer<Map<EEventsDataKeys, Object>> confirmTask;

    private Worker worker;
    private Collection<SessionGroup> groups;

    public AddSessionWorkerDialog(Worker worker, Collection<SessionGroup> groups) {
        fxmlPath = "/views/Forms/AddSessionWorkerDialog.fxml";
        this.worker = worker;
        this.groups = groups;

    }

    @Override
    public Node getView() {
        return root;
    }

    @Override
    public void setOnConfirm(Consumer<Map<EEventsDataKeys, Object>> callback) {
        confirmTask = callback;
    }

    @Override
    public void setOnCancel(Runnable callback) {
        cancelTask = callback;

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        groupComboBox.getItems().addAll(groups);
        groupComboBox.selectFirst();

        groupComboBox.setConverter(new StringConverter<SessionGroup>() {
            @Override
            public String toString(SessionGroup object) {
                if (object == null) {
                    return "";
                }
                return object.getName();
            }

            @Override
            public SessionGroup fromString(String string) {
                return null;
            }
        });

    }

    @FXML
    public void onCancel() {
        cancelTask.run();
    }

    @FXML
    public void onConfirm() {

        SessionWorker sessionWorker = createSessionWorker();

        Map<EEventsDataKeys, Object> data = new HashMap<>();

        data.put(EEventsDataKeys.Instance, sessionWorker);

        confirmTask.accept(data);

        cancelTask.run();

    }

    private SessionWorker createSessionWorker() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        SessionGroup group = groupComboBox.getSelectedItem();

        SessionWorker sessionWorker = new SessionWorker(worker.getId(), worker.getName(), username, password,
                group.getName(), group.getId(),-1);

        return sessionWorker;
    }

}
