package idir.embag.DataModels.Products;

public class StockProduct {

    private int articleId;

    private String articleName;

    private int articleQuantity;

    private double articlePrice;
    
    private int familyCode;

    

    public StockProduct(int articleId, String articleName, int articleQuantity, double articlePrice,
            int familyCode) {
        this.articleId = articleId;
        this.articleName = articleName;
        this.articleQuantity = articleQuantity;
        this.articlePrice = articlePrice;
        this.familyCode = familyCode;
    }

  
    public String getArticleName() {
        return articleName;
    }

    public int getFamilyCode() {
        return familyCode;
    }

    public int getQuantity() {
        return articleQuantity;
    }

    public double getPrice() {
        return articlePrice;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public void setArticleName(String articleName) {
       this.articleName = articleName;
    }

    public void setFamilyCode(int familyCode) {
        this.familyCode = familyCode;
        
    }

    public void setQuantity(int quantity) {
        this.articleQuantity = quantity;
        
    }

    public void setPrice(double price) {
        this.articlePrice = price;
    }
    
}
