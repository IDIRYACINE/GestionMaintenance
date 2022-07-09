package idir.embag.Utility.Database;

public class MDatabase {

static enum Tables{
    Stock,
    Sessions,
    Inventory,
    Workers,
    SessionsGroups,
    FamilliesCode,
    SessionsRecords,
    CodeBars,
    SessionWorkers,
}

static enum StockAttributes {
    ArticleId,
    ArticleName,
    Price,
    Quantity,
}

static enum InventoryAttributes{
    ArticleId,
    ArticleName,
    StockId,
    Price,
   
}

static enum FamilliesCodeAttributes{
    Id,
    FamilyCode,
    FamilyName,
}

static enum CodeBarsAttributes{
    Code,
    Type,
    Link,
}

static enum SessionsAttributes{
    SessionId,
    StartDate,
    EndDate,
    WorkersCount,
    WorkersPay,
    PriceShiftValue,
    QuantityShiftValue,
}

static enum SessionsRecordsAttributes{
    Id,
    ArticleId,
    Date,
    Time,
    WorkerId,
    PriceShift,
    QuantityShift,
    SessionId,
}

static enum SessionsGroupsAttributes{
    Id,
    SessionId,
    Name,
    WorkersId,
}

static enum WorkersAttributes{
    Id,
    Name,
    Email,
    Phone
}

static enum SessionWorkersAttributes{
    WorkerId,
    Username,
    Password
}
    
}
