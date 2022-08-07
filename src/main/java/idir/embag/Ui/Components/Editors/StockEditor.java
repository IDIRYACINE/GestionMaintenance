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
import idir.embag.DataModels.Products.StockProduct;
import idir.embag.Types.Infrastructure.Database.Generics.AttributeWrapper;
import idir.embag.Types.Infrastructure.Database.Metadata.EStockAttributes;
import idir.embag.Types.MetaData.EWrappers;
import idir.embag.Types.Panels.Components.IDialogContent;
import idir.embag.Types.Panels.Generics.INodeView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;

public class StockEditor extends INodeView implements Initializable , IDialogContent {

    @FXML
    private Node root;

    @FXML
    private TextField articleIdField,articleNameField,articlePriceField,articleQuantityField,articleFamilyField;

    private Runnable cancelTask;

    private Consumer<Map<EEventsDataKeys,Object>> confirmTask;

    private StockProduct product;

    

    public StockEditor(StockProduct product) {
        this.product = product;
        fxmlPath = "/views/Editors/StockEditor.fxml";
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
        articlePriceField.setText(String.valueOf(product.getPrice()));
        articleQuantityField.setText(String.valueOf(product.getQuantity()));
        articleFamilyField.setText(String.valueOf(product.getFamilyCode()));
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
        product.setArticleName(articleNameField.getText());
        product.setPrice(Double.parseDouble(articlePriceField.getText()));
        product.setQuantity(Integer.parseInt(articleQuantityField.getText()));
        product.setFamilyCode(Integer.parseInt(articleFamilyField.getText()));

        DataBundler.bundleNestedData(data, EEventsDataKeys.WrappersKeys, EWrappers.AttributesCollection, getAttributeWrappers());
        data.put(EEventsDataKeys.Instance, product);

    }

    private Collection<AttributeWrapper> getAttributeWrappers(){
        Collection<AttributeWrapper> attributes = new ArrayList<AttributeWrapper>();

        attributes.add(new AttributeWrapper(EStockAttributes.ArticleId,articleIdField.getText()));
        attributes.add(new AttributeWrapper(EStockAttributes.ArticleName,articleNameField.getText()));
        attributes.add(new AttributeWrapper(EStockAttributes.Price,articlePriceField.getText()));
        attributes.add(new AttributeWrapper(EStockAttributes.Quantity,articleQuantityField.getText()));
        attributes.add(new AttributeWrapper(EStockAttributes.FamilyCode,articleFamilyField.getText()));


        return attributes;
    }

}
