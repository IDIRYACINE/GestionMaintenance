package idir.embag.Ui.Components;

import java.util.function.Consumer;

import javafx.scene.Node;

public interface IDialogContent {
    public Node getView();
    public void setOnConfirm(Consumer<Object> callback);
    public void setOnCancel(Runnable callback);
}
