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
import idir.embag.DataModels.Products.FamilyCode;
import idir.embag.Types.Infrastructure.Database.Generics.AttributeWrapper;
import idir.embag.Types.Infrastructure.Database.Metadata.EFamilyCodeAttributes;
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

public class FamilyCodeEditor extends INodeView implements Initializable , IDialogContent {

    
    @FXML
    private Node root;

    @FXML
    private TextField familyNameField,familyCodeField;


    @FXML
    private Label familyNameErrorLabel, familyCodeErrorLabel;

    private CustomFieldSkin familyNameSkin, familyCodeSkin;

    private Runnable cancelTask;

    private Consumer<Map<EEventsDataKeys,Object>> confirmTask;

    private FamilyCode product;

    
    public FamilyCodeEditor(FamilyCode product) {
        this.product = product;
        fxmlPath = "/views/Editors/FamilyCodeEditor.fxml";

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
        familyCodeField.setText(String.valueOf(product.getFamilyCode()));
           familyNameField.setText(String.valueOf(product.getFamilyName()));
    }

    private void setupTextFieldsValidation() {
        SkinErrorTester emptyFieldTester = new SkinErrorTester(Messages.errorRequiredField, Validators::emptyField);
           SkinErrorTester invalidName = new SkinErrorTester(Messages.errorInvalidName, Validators::isName);
           SkinErrorTester invalidNumberTester = new SkinErrorTester(Messages.errorInvalidNumber, Validators::isNumber);

           familyNameSkin = new CustomFieldSkin(familyNameField, familyNameErrorLabel);
           familyNameSkin.addErrorTester(emptyFieldTester);
           familyNameSkin.addErrorTester(invalidName);
           familyNameField.setSkin(familyNameSkin);

           familyCodeSkin = new CustomFieldSkin(familyCodeField, familyCodeErrorLabel);
           familyCodeSkin.addErrorTester(emptyFieldTester);
           familyCodeSkin.addErrorTester(invalidNumberTester);
           familyCodeField.setSkin(familyCodeSkin);
    }

    @Override
    public Node getView() {
        return root;
    }

    @FXML
    private void onConfirm(){
        
        Map<EEventsDataKeys,Object> data = new HashMap<>();
        setupConfirm(data);

        confirmTask.accept(data);
        cancelTask.run();
    }
    
    @FXML
    private void onCancel(){
        cancelTask.run();
    }

    private void setupConfirm(Map<EEventsDataKeys,Object> data){
        product.setFamilyName(familyNameField.getText());
        product.setFamilyCode(Integer.parseInt(familyCodeField.getText()));

        DataBundler.bundleNestedData(data, EEventsDataKeys.WrappersKeys, EWrappers.AttributesCollection, getAttributeWrappers());
        data.put(EEventsDataKeys.Instance, product);
    }

    private Collection<AttributeWrapper> getAttributeWrappers(){
        Collection<AttributeWrapper> attributes = new ArrayList<AttributeWrapper>();

        attributes.add(new AttributeWrapper(EFamilyCodeAttributes.FamilyName,familyNameField.getText()));
        attributes.add(new AttributeWrapper(EFamilyCodeAttributes.FamilyCode,familyCodeField.getText()));


        return attributes;
    }

    
}
