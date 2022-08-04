package idir.embag.Types.Panels.Components;

import java.util.Map;
import java.util.function.Consumer;

import idir.embag.DataModels.Metadata.EEventsDataKeys;
import javafx.scene.Node;

public interface IDialogContent {
    public Node getView();
    public void setOnConfirm(Consumer<Map<EEventsDataKeys,Object>> callback);
    public void setOnCancel(Runnable callback);
}
