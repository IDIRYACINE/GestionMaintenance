package idir.embag.DataModels.Session;

public class SessionRecord {
    private int articleId;
    private String articleName;
    private String date;
    private String prix;
    private String quantityStock;
    private String quantityInventory;
    private String quantityShift;
    private String priceShift;
    private String groupId;
    private String workerName;

    public SessionRecord(int articleId, String articleName, String date, String prix, String quantityStock,
            String quantityInventory, String quantityShift, String priceShift, String groupId,
            String workerName) {

        this.articleId = articleId;
        this.articleName = articleName;
        this.date = date;
        this.prix = prix;
        this.quantityStock = quantityStock;
        this.quantityInventory = quantityInventory;
        this.quantityShift = quantityShift;
        this.priceShift = priceShift;
        this.groupId = groupId;
        this.workerName = workerName;
    }

    public int getArticleId() {
        return articleId;
    }

    public String getArticleName() {
        return articleName;
    }

    public String getDate() {
        return date;
    }

    public String getPrix() {
        return prix;
    }

    public String getQuantityStock() {
        return quantityStock;
    }

    public String getQuantityInventory() {
        return quantityInventory;
    }

    public String getQuantityShift() {
        return quantityShift;
    }

    public String getPriceShift() {
        return priceShift;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getworkerName() {
        return workerName;
    }

    public int getRecordId() {
        return 0;
    }
    
    
}
