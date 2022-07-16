package idir.embag.Types.Infrastructure.DataConverters;


public interface IDataConverter {
    
    public void exportData(String outputPath);
    public void importData(String sourcePath);
    public void setupNewPage();
    public void setTitle(String title);
    public <T> void setColumns(T[] columns);
    public <T> void setData(T[] data);

}
