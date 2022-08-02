package idir.embag.Ui.Components.Editors;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import idir.embag.DataModels.Metadata.EEventDataKeys;
import idir.embag.DataModels.Products.IProduct;
import idir.embag.Types.Infrastructure.Database.Generics.AttributeWrapper;
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

    private Consumer<Map<EEventDataKeys,Object>> confirmTask;

    private IProduct product;

    public InventoryEditor(IProduct product) {
        this.product = product;
        fxmlPath = "/views/Editors/InventoryEditor.fxml";

    }

    @Override
    public void setOnConfirm(Consumer<Map<EEventDataKeys, Object>> callback) {
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
        
        Map<EEventDataKeys,Object> data = new HashMap<>();
        setupConfirm(data);

        confirmTask.accept(data);
        cancelTask.run();
    }
    
    @FXML
    private void onCancel(){
        cancelTask.run();
    }

    private void setupConfirm(Map<EEventDataKeys,Object> data){
        product.setArticleId(Integer.parseInt(articleIdField.getText()));
        product.setArticleName(articleNameField.getText());
        product.setFamilyCode(Integer.parseInt(articleFamilyField.getText()));
        product.setStockId(Integer.parseInt(stockIdField.getText()));
        product.setArticleCode(Integer.parseInt(articleCodeField.getText()));

        data.put(EEventDataKeys.AttributeWrappersList,getAttributeWrappers());
        data.put(EEventDataKeys.ArticleId, product.getArticleId());
        data.put(EEventDataKeys.StockId, product.getStockId());

    }

    private Collection<AttributeWrapper> getAttributeWrappers(){
        Collection<AttributeWrapper> attributes = new ArrayList<AttributeWrapper>();
        
        attributes.add(new AttributeWrapper(EEventDataKeys.ArticleId,articleIdField.getText()));
        //attributes.add(new AttributeWrapper(EEventDataKeys.ArticleName,articleNameField.getText()));
        attributes.add(new AttributeWrapper(EEventDataKeys.ArticleCode,articleFamilyField.getText()));
        attributes.add(new AttributeWrapper(EEventDataKeys.StockId,stockIdField.getText()));

        return attributes;
    }

    
}
