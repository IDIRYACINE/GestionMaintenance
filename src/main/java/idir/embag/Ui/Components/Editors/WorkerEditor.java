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
import idir.embag.DataModels.Workers.Worker;
import idir.embag.Types.Infrastructure.Database.Generics.AttributeWrapper;
import idir.embag.Types.Infrastructure.Database.Metadata.EWorkerAttributes;
import idir.embag.Types.MetaData.EWrappers;
import idir.embag.Types.Panels.Components.IDialogContent;
import idir.embag.Types.Panels.Generics.INodeView;
import idir.embag.Ui.Components.TextFieldSkins.CustomFieldSkin;
import idir.embag.Ui.Components.TextFieldSkins.SkinErrorTester;
import idir.embag.Ui.Constants.Messages;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class WorkerEditor extends INodeView implements Initializable, IDialogContent {

    @FXML
    private Node root;

    @FXML
    private TextField workerNameField, workerPhoneField, workerEmailField;

    @FXML
    private Label workerNameErrorLabel, workerPhoneErrorLabel, workerEmailErrorLabel;

    private Runnable cancelTask;

    private Consumer<Map<EEventsDataKeys, Object>> confirmTask;

    private Worker worker;

    public WorkerEditor(Worker worker) {
        this.worker = worker;
        fxmlPath = "/views/Editors/WorkerEditor.fxml";

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
        initialiseWorkerEditor();

        setupTextFieldsValidation();
    }

    private void initialiseWorkerEditor() {
        workerEmailField.setText(worker.getEmail());
        workerNameField.setText(worker.getName());
        workerPhoneField.setText(String.valueOf(worker.getPhone()));
    }

    private void setupTextFieldsValidation() {
        SkinErrorTester emptyFieldTester = new SkinErrorTester(Messages.errorRequiredField, Validators::emptyField);
        SkinErrorTester invalidName = new SkinErrorTester(Messages.errorInvalidName, Validators::isName);
        SkinErrorTester invalidNumberTester = new SkinErrorTester(Messages.errorInvalidNumber, Validators::isNumber);
        SkinErrorTester invalidPhoneTester = new SkinErrorTester(Messages.errorInvalidPhone, Validators::isPhoneNumber);


        CustomFieldSkin nameSkin = new CustomFieldSkin(workerNameField, workerNameErrorLabel);
        nameSkin.addErrorTester(emptyFieldTester);
        nameSkin.addErrorTester(invalidName);
        workerNameField.setSkin(nameSkin);

        CustomFieldSkin phoneSkin = new CustomFieldSkin(workerPhoneField, workerPhoneErrorLabel);
        phoneSkin.addErrorTester(emptyFieldTester);
        phoneSkin.addErrorTester(invalidNumberTester);
        workerPhoneField.setSkin(phoneSkin);

        CustomFieldSkin emailSkin = new CustomFieldSkin(workerEmailField, workerEmailErrorLabel);
        emailSkin.addErrorTester(emptyFieldTester);
        emailSkin.addErrorTester(invalidPhoneTester);
        workerEmailField.setSkin(emailSkin);
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

        DataBundler.bundleNestedData(data, EEventsDataKeys.WrappersKeys, EWrappers.AttributesCollection,
                getAttributeWrappers());
        data.put(EEventsDataKeys.Instance, worker);
    }

    private Collection<AttributeWrapper> getAttributeWrappers() {
        Collection<AttributeWrapper> attributes = new ArrayList<AttributeWrapper>();

        String email = workerEmailField.getText();
        if (!email.equals(worker.getEmail())) {
            attributes.add(new AttributeWrapper(EWorkerAttributes.WorkerEmail, email));
            worker.setEmail(email);
        }
        
        String name = workerNameField.getText();
        if (!name.equals(worker.getName())) {
            attributes.add(new AttributeWrapper(EWorkerAttributes.WorkerName, name));
            worker.setName(name);
        }

        int phone = Integer.parseInt(workerPhoneField.getText());
        if (phone != worker.getPhone()) {
            worker.setPhone(phone);
            attributes.add(new AttributeWrapper(EWorkerAttributes.WorkerPhone, workerPhoneField.getText()));

        }

        return attributes;
    }

}
