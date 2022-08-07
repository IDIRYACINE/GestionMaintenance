package idir.embag.DataModels.Products;

public class FamilyCode {

    private String articleName;

    private int familyCode;
    
    public FamilyCode( String articleName ,int familyCode) {
        this.articleName = articleName;
        this.familyCode = familyCode;
    }

    public String getFamilyName() {
        return articleName;
    }

    public int getFamilyCode() {
        return familyCode;
    }


    public void setFamilyName(String articleName) {
       this.articleName = articleName;
    }

    public void setFamilyCode(int familyCode) {
        this.familyCode = familyCode;
        
    }

}
