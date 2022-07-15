package idir.embag.DataModels.Products;

public class InventoryProduct implements IProduct{

    private int articleId;

    private String articleName;

    private int articleCodebar;

    private int articleQuantity;

    private double articlePrice;
    
    private int familyCode;

    

    public InventoryProduct(int articleId, String articleName, int articleCodebar, int articleQuantity, double articlePrice,
            int familyCode) {
        this.articleId = articleId;
        this.articleName = articleName;
        this.articleCodebar = articleCodebar;
        this.articleQuantity = articleQuantity;
        this.articlePrice = articlePrice;
        this.familyCode = familyCode;
    }

    @Override
    public int getArticleCode() {
        return articleCodebar;
    }

    @Override
    public String getArticleName() {
        return articleName;
    }

    @Override
    public int getFamilyCode() {
        return familyCode;
    }

    @Override
    public int getQuantity() {
        return articleQuantity;
    }

    @Override
    public double getPrice() {
        return articlePrice;
    }

    @Override
    public int getArticleId() {
        return articleId;
    }


    @Override
    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    @Override
    public void setArticleCode(int articleCode) {
        this.articleCodebar = articleCode;
        
    }

    @Override
    public void setArticleName(String articleName) {
       this.articleName = articleName;
    }

    @Override
    public void setFamilyCode(int familyCode) {
        this.familyCode = familyCode;
        
    }

    @Override
    public void setQuantity(int quantity) {
        this.articleQuantity = quantity;
    }

    @Override
    public void setPrice(double price) {
        this.articlePrice = price;
    }
    
    
}
