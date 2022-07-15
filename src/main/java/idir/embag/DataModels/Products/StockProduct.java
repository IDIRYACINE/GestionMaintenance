package idir.embag.DataModels.Products;

public class StockProduct implements IProduct{

    private int articleId;

    private String articleName;

    private int articleCodebar;

    private int articleQuantity;

    private double articlePrice;
    
    private int familyCode;

    

    public StockProduct(int articleId, String articleName, int articleCodebar, int articleQuantity, double articlePrice,
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
    
}
