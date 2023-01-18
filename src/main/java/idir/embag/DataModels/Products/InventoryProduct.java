package idir.embag.DataModels.Products;

public class InventoryProduct {

    private int articleId;

    private String articleName;

    private int articleCodebar;

    private int articleQuantity;

    private double articlePrice;

    private int familyCode;

    private int affectationId;

    public InventoryProduct(int articleId, String articleName, int articleCodebar, int articleQuantity,
            double articlePrice,
            int familyCode, int affectationId) {
        this.articleId = articleId;
        this.articleName = articleName;
        this.articleCodebar = articleCodebar;
        this.articleQuantity = articleQuantity;
        this.articlePrice = articlePrice;
        this.familyCode = familyCode;
        this.affectationId = affectationId;
    }

    public int getArticleCode() {
        return articleCodebar;
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

    public void setArticleCode(int articleCode) {
        this.articleCodebar = articleCode;

    }

    public void setFamilyName(String articleName) {
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

    public int getAffectationId() {
        return affectationId;
    }

    public void setAffectationId(int id) {
        affectationId = id;
    }

}
