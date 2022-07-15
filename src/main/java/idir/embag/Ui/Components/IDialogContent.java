package idir.embag.Ui.Components;

import java.util.Map;
import java.util.function.Consumer;

import idir.embag.DataModels.Metadata.EEventDataKeys;
import javafx.scene.Node;

public interface IDialogContent {
    public Node getView();
    public void setOnConfirm(Consumer<Map<EEventDataKeys,Object>> callback);
    public void setOnCancel(Runnable callback);
}
