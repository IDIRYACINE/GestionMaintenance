package idir.embag.Ui.Components.Editors;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import idir.embag.Application.Utility.DataBundler;
import idir.embag.DataModels.Metadata.EEventsDataKeys;
import idir.embag.DataModels.Products.InventoryProduct;
import idir.embag.Types.Infrastructure.Database.Generics.AttributeWrapper;
import idir.embag.Types.Infrastructure.Database.Metadata.EInventoryAttributes;
import idir.embag.Types.MetaData.EWrappers;
import idir.embag.Types.Panels.Components.IDialogContent;
import idir.embag.Types.Panels.Generics.INodeView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;

public class InventoryEditor extends INodeView implements Initializable , IDialogContent {

    
    @FXML
    private Node root;

    @FXML
    private TextField articleIdField,articleNameField,articleFamilyField,stockIdField,articleCodeField;

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
        articleIdField.setText(String.valueOf(product.getArticleId()));
        articleNameField.setText(product.getArticleName());
        articleFamilyField.setText(String.valueOf(product.getFamilyCode()));
        stockIdField.setText(String.valueOf(product.getStockId()));
        articleCodeField.setText(String.valueOf(product.getArticleCode()));
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
        product.setStockId(Integer.parseInt(stockIdField.getText()));

        System.out.println(product.getArticleCode());

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
