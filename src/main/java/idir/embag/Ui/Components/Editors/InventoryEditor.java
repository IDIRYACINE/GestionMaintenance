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
import idir.embag.DataModels.Products.InventoryProduct;
import idir.embag.Types.Infrastructure.Database.Generics.AttributeWrapper;
import idir.embag.Types.Infrastructure.Database.Metadata.EInventoryAttributes;
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

public class InventoryEditor extends INodeView implements Initializable , IDialogContent {

    
    @FXML
    private Node root;

    @FXML
    private TextField articleIdField,articleNameField,articleFamilyField,stockIdField,articleCodeField;
   
    @FXML
    private Label articleIdErrorLabel, articleNameErrorLabel, articleFamilyErrorLabel, stockIdErrorLabel, articleCodeErrorLabel;

    private CustomFieldSkin articleIdSkin, articleNameSkin, articleFamilySkin, stockIdSkin, articleCodeSkin ;

    private Runnable cancelTask;

    private Consumer<Map<EEventsDataKeys,Object>> confirmTask;

    private InventoryProduct product;

    public InventoryEditor(InventoryProduct product) {
        this.product = product;
        fxmlPath = "/views/Editors/InventoryEditor.fxml";

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
        articleIdField.setText(String.valueOf(product.getArticleId()));
        articleNameField.setText(product.getArticleName());
        articleFamilyField.setText(String.valueOf(product.getFamilyCode()));
        stockIdField.setText(String.valueOf(product.getDesignationId()));
        articleCodeField.setText(String.valueOf(product.getArticleCode()));
    }

    private void setupTextFieldsValidation() {
        SkinErrorTester emptyFieldTester = new SkinErrorTester(Messages.errorRequiredField, Validators::emptyField);
        SkinErrorTester invalidName = new SkinErrorTester(Messages.errorInvalidName, Validators::isName);
        SkinErrorTester invalidNumberTester = new SkinErrorTester(Messages.errorInvalidNumber, Validators::isNumber);
 

        articleIdSkin = new CustomFieldSkin(articleIdField,articleIdErrorLabel);
        articleIdSkin.addErrorTester(emptyFieldTester);
        articleIdSkin.addErrorTester(invalidNumberTester);
        articleIdField.setSkin(articleIdSkin);

        articleNameSkin = new CustomFieldSkin(articleNameField,articleNameErrorLabel);
        articleNameSkin.addErrorTester(emptyFieldTester);
        articleNameSkin.addErrorTester(invalidName);
        articleNameField.setSkin(articleNameSkin);

        articleFamilySkin = new CustomFieldSkin(articleFamilyField,articleFamilyErrorLabel);
        articleFamilySkin.addErrorTester(emptyFieldTester);
        articleFamilySkin.addErrorTester(invalidNumberTester);
        articleFamilyField.setSkin(articleFamilySkin);

        stockIdSkin = new CustomFieldSkin(stockIdField,stockIdErrorLabel);
        stockIdSkin.addErrorTester(emptyFieldTester);
        stockIdSkin.addErrorTester(invalidNumberTester);
        stockIdField.setSkin(stockIdSkin);

        articleCodeSkin = new CustomFieldSkin(articleCodeField,articleCodeErrorLabel);
        articleCodeSkin.addErrorTester(emptyFieldTester);
        articleCodeSkin.addErrorTester(invalidNumberTester);
        articleCodeField.setSkin(articleCodeSkin);
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
        product.setArticleId(Integer.parseInt(articleIdField.getText()));
        product.setFamilyName(articleNameField.getText());
        product.setFamilyCode(Integer.parseInt(articleFamilyField.getText()));
        product.setDesignationId(Integer.parseInt(stockIdField.getText()));


        DataBundler.bundleNestedData(data, EEventsDataKeys.WrappersKeys, EWrappers.AttributesCollection, getAttributeWrappers());
        data.put(EEventsDataKeys.Instance, product);

    }

    private Collection<AttributeWrapper> getAttributeWrappers(){
        Collection<AttributeWrapper> attributes = new ArrayList<AttributeWrapper>();
        
        attributes.add(new AttributeWrapper(EInventoryAttributes.ArticleId,articleIdField.getText()));
        //attributes.add(new AttributeWrapper(EEventDataKeys.ArticleName,articleNameField.getText()));
        attributes.add(new AttributeWrapper(EInventoryAttributes.ArticleCode,articleCodeField.getText()));
        attributes.add(new AttributeWrapper(EInventoryAttributes.StockId,stockIdField.getText()));

        return attributes;
    }

    
}
