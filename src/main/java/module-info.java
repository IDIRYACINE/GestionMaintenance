module idir.embag {
    
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;
    requires java.sql;
    requires org.xerial.sqlitejdbc;
    requires java.desktop;
    requires javafx.base;
    requires de.jensd.fx.glyphs.fontawesome;
    requires de.jensd.fx.glyphs.materialdesignicons;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml ;
    requires javafx.swing;
    requires org.apache.commons.compress;
    requires ini4j;

    opens idir.embag to javafx.fxml;
   
    opens idir.embag.Models to javafx.base,javafx.fxml;
    opens idir.embag.Utility.Printer.PrinterSelection to javafx.base , javafx.fxml;
    opens idir.embag.Controllers.Settings to javafx.base,javafx.fxml;

    exports idir.embag;
}
