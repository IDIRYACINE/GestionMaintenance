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
    requires org.yaml.snakeyaml;

    opens idir.embag to javafx.fxml;

    opens idir.embag.Ui.Panels.Historique to javafx.base,javafx.fxml;
    opens idir.embag.Ui.Panels.Session to javafx.base,javafx.fxml;
    opens idir.embag.Ui.Panels.Workers to javafx.base,javafx.fxml;
    opens idir.embag.Ui.Panels.Settings to javafx.base,javafx.fxml;
    opens idir.embag.Ui.Panels.Stock to javafx.base,javafx.fxml;
    opens idir.embag.Ui.Components to javafxj.base,javafx.fxml;
    opens idir.embag.Application.Controllers.Navigation to javafx.base,javafx.fxml;
    opens idir.embag.Infrastructure.Printer.PrinterSelection to javafx.base,javafx.fxml;
    opens idir.embag.Application.Controllers.Settings to javafx.base,javafx.fxml;
    opens idir.embag.Ui.Dialogs.FilterDialog to  javafx.base,javafx.fxml;
    opens idir.embag.Ui.Dialogs.MangerDialog to  javafx.base,javafx.fxml;
    opens idir.embag.Ui.Components.Editors to  javafx.base,javafx.fxml;
    opens idir.embag.Ui.Dialogs.ConfirmationDialog to  javafx.base,javafx.fxml;
    opens idir.embag.Ui.Dialogs.ExportDialogs to  javafx.base,javafx.fxml;


    exports idir.embag;
}
