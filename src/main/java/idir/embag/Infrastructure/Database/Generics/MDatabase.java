package idir.embag.Infrastructure.Database.Generics;

public class MDatabase {

public static enum Tables{
    Stock,
    Sessions,
    Inventory,
    Workers,
    SessionsGroups,
    FamilliesCode,
    SessionsRecords,
    CodeBars,
    SessionWorkers,
    ActiveSession,
}

public static enum StockAttributes {
    ArticleId,
    ArticleName,
    Price,
    Quantity,
}

public static enum InventoryAttributes{
    ArticleId,
    ArticleName,
    StockId,
    Price,
   
}

public static enum FamilliesCodeAttributes{
    FamilyCode,
    FamilyName,
}

public static enum CodeBarsAttributes{
    Code,
    Type,
    Link,
}

public static enum SessionsAttributes{
    SessionId,
    StartDate,
    EndDate,
    PriceShiftValue,
    QuantityShiftValue,
}

public static enum SessionsRecordsAttributes{
    RecordId,
    SessionId ,
    WorkerId ,
    GroupId ,
    InventoryId ,
    RecordDate ,
    StockQuantity ,
    RecordQuantity ,
    StockPrice ,
    QuantityShift ,
    PriceShift ,
}

public static enum SessionsGroupsAttributes{
    Id,
    SessionId,
    Name,
}

public static enum WorkersAttributes{
    WorkerId,
    Name,
    Email,
    Phone
}

public static enum SessionWorkersAttributes{
    Id,
    WorkerId,
    GroupId,

}


public static enum ActiveSessionAttributes{
    Id,
    WorkerId,
    Password
}
    
}
