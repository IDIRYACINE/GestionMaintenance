package idir.embag.DataModels.Others;

import idir.embag.DataModels.Products.IProduct;

public class FamilyCode implements IProduct{

    private String articleName;

    private int familyCode;

    

    public FamilyCode( String articleName ,int familyCode) {
        this.articleName = articleName;
        this.familyCode = familyCode;
    }

    @Override
    public int getArticleCode() {
        return 0;
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
        return 0;
    }

    @Override
    public double getPrice() {
        return 0;
    }

    @Override
    public int getArticleId() {
        return 0;
    }
    
    
}
