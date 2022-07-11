module idir.embag {
    
    requires transitive javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.xerial.sqlitejdbc;
    requires MaterialFX;
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

    opens idir.embag.Ui.Views.Historique to javafx.base,javafx.fxml;
    opens idir.embag.Ui.Views.Session to javafx.base,javafx.fxml;
    opens idir.embag.Ui.Views.Workers to javafx.base,javafx.fxml;
    opens idir.embag.Ui.Views.Settings to javafx.base,javafx.fxml;
    opens idir.embag.Ui.Views.Stock to javafx.base,javafx.fxml;
    opens idir.embag.Ui.Components to javafx.base,javafx.fxml;
    opens idir.embag.Application.Controllers.Navigation to javafx.base,javafx.fxml;
    opens idir.embag.Utility.Printer.PrinterSelection to javafx.base,javafx.fxml;
    opens idir.embag.Application.Controllers.Settings to javafx.base,javafx.fxml;

    exports idir.embag;
}
