package idir.embag.DataModels.Products;

public interface IProduct {
    public int getArticleId();
    public int getArticleCode();
    public String getArticleName();
    public int getFamilyCode();
    public int getQuantity();
    public double getPrice();

    public void setArticleId(int articleId);
    public void setArticleCode(int articleCode);
    public void setArticleName(String articleName);
    public void setFamilyCode(int familyCode);
    public void setQuantity(int quantity);
    public void setPrice(double price);

    
}
