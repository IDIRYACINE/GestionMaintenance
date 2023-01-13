package idir.embag.Ui.Components.Editors;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import idir.embag.Application.Utility.DataBundler;
import idir.embag.Application.Utility.Validator.Validators;
import idir.embag.DataModels.Metadata.EEventsDataKeys;
import idir.embag.DataModels.Users.Designation;
import idir.embag.Types.Infrastructure.Database.Generics.AttributeWrapper;
import idir.embag.Types.Infrastructure.Database.Metadata.EDesignationAttributes;
import idir.embag.Types.MetaData.EWrappers;
import idir.embag.Types.Panels.Components.IDialogContent;
import idir.embag.Types.Panels.Generics.INodeView;
import idir.embag.Ui.Components.TextFieldSkins.CustomFieldSkin;
import idir.embag.Ui.Components.TextFieldSkins.SkinErrorTester;
import idir.embag.Ui.Constants.Messages;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;

public class DesignationEditor extends INodeView implements Initializable, IDialogContent {

    @FXML
    private Node root;

    @FXML
    private MFXTextField nameField, idField;

    @FXML
    private Label nameErrorLabel, idErrorLabel;

    private CustomFieldSkin nameSkin, idSkin;

    private Runnable cancelTask;

    private Consumer<Map<EEventsDataKeys, Object>> confirmTask;

    private Designation designation;

    public DesignationEditor(Designation designation) {
        this.designation = designation;
        fxmlPath = "/views/Editors/DesignationEditor.fxml";

    }

    @Override
    public void setOnConfirm(Consumer<Map<EEventsDataKeys, Object>> callback) {
        this.confirmTask = callback;
    }

    @Override
    public void setOnCancel(Runnable callback) {
        this.cancelTask = callback;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initialiseEditor();

        setupTextFieldsValidation();

    }

    private void initialiseEditor() {
        idField.setText(String.valueOf(designation.getDesignationId()));
        nameField.setText(String.valueOf(designation.getDesignationName()));
    }

    private void setupTextFieldsValidation() {
        SkinErrorTester emptyFieldTester = new SkinErrorTester(Messages.errorRequiredField, Validators::emptyField);
        SkinErrorTester invalidName = new SkinErrorTester(Messages.errorInvalidName, Validators::isName);
        SkinErrorTester invalidNumberTester = new SkinErrorTester(Messages.errorInvalidNumber, Validators::isNumber);

        nameSkin = new CustomFieldSkin(nameField, nameErrorLabel);
        nameSkin.addErrorTester(emptyFieldTester);
        nameSkin.addErrorTester(invalidName);
        nameField.setSkin(nameSkin);

        idSkin = new CustomFieldSkin(idField, idErrorLabel);
        idSkin.addErrorTester(emptyFieldTester);
        idSkin.addErrorTester(invalidNumberTester);
        idField.setSkin(idSkin);
    }

    @Override
    public Node getView() {
        return root;
    }

    @FXML
    private void onConfirm() {

        Map<EEventsDataKeys, Object> data = new HashMap<>();
        setupConfirm(data);

        confirmTask.accept(data);
        cancelTask.run();
    }

    @FXML
    private void onCancel() {
        cancelTask.run();
    }

    private void setupConfirm(Map<EEventsDataKeys, Object> data) {
        designation.setDesignationName(nameField.getText());
        designation.setDesignationId(Integer.parseInt(idField.getText()));

        DataBundler.bundleNestedData(data, EEventsDataKeys.WrappersKeys, EWrappers.AttributesCollection,
                getAttributeWrappers());
        data.put(EEventsDataKeys.Instance, designation);
    }

    private Collection<AttributeWrapper> getAttributeWrappers() {
        Collection<AttributeWrapper> attributes = new ArrayList<AttributeWrapper>();

        attributes.add(new AttributeWrapper(EDesignationAttributes.DesignationName, nameField.getText()));
        attributes.add(new AttributeWrapper(EDesignationAttributes.DesignationId, idField.getText()));

        return attributes;
    }

}
