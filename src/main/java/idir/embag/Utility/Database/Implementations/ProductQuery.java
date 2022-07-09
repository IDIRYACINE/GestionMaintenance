package idir.embag.Utility.Database.Implementations;

import java.sql.SQLException;

public interface ProductQuery {
    
    public void RegisterStockProduct() throws SQLException;
    public void UnregisterStockProduct() throws SQLException;
    public void UpdateStockProduct() throws SQLException;

    public void RegisterInventoryProduct() throws SQLException;
    public void UnregisterInventoryProduct() throws SQLException;
    public void UpdateInventoryProduct() throws SQLException;

    public void RegisterFamilyCode() throws SQLException;
    public void UpdateFamilyCode() throws SQLException;
    public void UnregisterFamilyCode() throws SQLException;


    public void CreateFamiLCodeTable() throws SQLException;
    public void CreateInventoryTable() throws SQLException;
    public void CreateStockTable() throws SQLException;
    
}
