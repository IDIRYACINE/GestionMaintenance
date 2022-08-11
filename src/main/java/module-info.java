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
    requires org.apache.poi.ooxml ;
    requires org.apache.commons.compress;
    requires org.yaml.snakeyaml;
    requires Java.WebSocket;
    requires okhttp3;
    requires com.google.gson;

    opens idir.embag to javafx.fxml;

    opens idir.embag.Ui.Panels.Historique to javafx.base,javafx.fxml;
    opens idir.embag.Ui.Panels.Session to javafx.base,javafx.fxml;
    opens idir.embag.Ui.Panels.Workers to javafx.base,javafx.fxml;
    opens idir.embag.Ui.Panels.Settings to javafx.base,javafx.fxml;
    opens idir.embag.Ui.Panels.Stock to javafx.base,javafx.fxml;
    opens idir.embag.Ui.Panels.Login to javafx.base,javafx.fxml;
    opens idir.embag.Ui.Components to javafxj.base,javafx.fxml;
    opens idir.embag.Application.Controllers.Navigation to javafx.base,javafx.fxml;
    opens idir.embag.Infrastructure.Printer.PrinterSelection to javafx.base,javafx.fxml;
    opens idir.embag.Application.Controllers.Settings to javafx.base,javafx.fxml;
    opens idir.embag.Ui.Dialogs.FilterDialog to  javafx.base,javafx.fxml;
    opens idir.embag.Ui.Dialogs.MangerDialog to  javafx.base,javafx.fxml;
    opens idir.embag.Ui.Components.Editors to  javafx.base,javafx.fxml;
    opens idir.embag.Ui.Dialogs.ConfirmationDialog to  javafx.base,javafx.fxml;
    opens idir.embag.Ui.Dialogs.ExportDialogs to  javafx.base,javafx.fxml;

    opens idir.embag.DataModels.ApiBodyResponses to com.google.gson;
    opens idir.embag.DataModels.Products to com.google.gson;
    opens idir.embag.DataModels.Session to com.google.gson;
    opens idir.embag.DataModels.Workers to com.google.gson;

    exports idir.embag;
}
