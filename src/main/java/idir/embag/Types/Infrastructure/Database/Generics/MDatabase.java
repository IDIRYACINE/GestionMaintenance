package idir.embag.Types.Infrastructure.Database.Generics;

public class MDatabase {

public static class Tables{
    public static final String Stock = "Stock";
    public static final String Sessions = "Sessions";
    public static final String Inventory = "Inventory";
    public static final String Workers = "Workers";
    public static final String SessionsGroups = "SessionsGroups";
    public static final String FamilyCodes = "FamilyCodes";
    public static final String SessionsRecords = "SessionsRecords";
    public static final String CodeBars = "CodeBars";
    public static final String SessionWorkers = "SessionWorkers";
    public static final String ActiveSession = "ActiveSession";
}

public static class StockAttributes {
    public static final String ArticleId = "ArticleId";
    public static final String ArticleName = "ArticleName";
    public static final String Price = "Price";
    public static final String Quantity = "Quantity";
    public static final String FamilyCode = "FamilyCode";
}

public static class InventoryAttributes{
    public static final String ArticleId = "ArticleId";
    public static final String ArticleCode = "ArticleCode";
    public static final String ArticleName = "ArticleName";
    public static final String StockId = "StockId";
    public static final String Price = "Price";
    public static final String FamilyCode = "FamilyCode";

   
}

public static class FamilliesCodeAttributes{
    public static final String FamilyCode = "FamilyCode";
    public static final String FamilyName = "FamilyName";
}

public static class CodeBarsAttributes{
    public static final String Code = "Code";
    public static final String Type = "Type";
    public static final String Link = "Link";
}

public static class SessionsAttributes{
    public static final String SessionId = "SessionId";
    public static final String StartDate = "StartDate";
    public static final String EndDate = "EndDate";
    public static final String PriceShiftValue = "PriceShiftValue";
    public static final String QuantityShiftValue = "QuantityShiftValue";
}

public static class SessionsRecordsAttributes{
    public static final String RecordId = "RecordId";
    public static final String SessionId = "SessionId";
    public static final String WorkerId  = "WorkerId";
    public static final String GroupId = "GroupId";
    public static final String InventoryId = "InventoryId";
    public static final String RecordDate = "RecordDate";
    public static final String StockQuantity = "StockQuantity";
    public static final String RecordQuantity = "RecordQuantity";
    public static final String StockPrice = "StockPrice";
    public static final String QuantityShift = "QuantityShift";
    public static final String PriceShift = "PriceShift";
}

public static class SessionGroupsAttributes{
    public static final String Id = "GroupId";
    public static final String SessionId = "SessionId";
    public static final String Name = "GroupName";
}

public static class WorkersAttributes{
    public static final String WorkerId = "WorkerId";
    public static final String Name = "WorkerName";
    public static final String Email = "WorkerEmail";
    public static final String Phone = "WorkerPhone";
}

public static class SessionWorkersAttributes{
    public static final String WorkerId = "WorkerId";
    public static final String GroupId = "GroupId";
    public static final String Password = "Password";

}

public static class WorkersIndexes {
    public static final String NamePhoneIndex = "WorkersNamePhoneIndex";
}

public static class InventoryIdexes {
    public static final String CodeBarIndex = "InventoryCodeBarIndex";
}

    
}
