package idir.embag.Utility.Printer.PrinterSelection;

import java.io.IOException;



import idir.embag.Utility.Printer.CheckPrinter;
import javafx.fxml.FXMLLoader;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class PrinterSelectorModel  {
    private PrinterSelectionController controller;
    private CheckPrinter checkPrinter;

    public PrinterSelectorModel(StackPane parentPanel , CheckPrinter checkPrinter) {
        this.checkPrinter = checkPrinter;
        loadFxml(parentPanel);
    }

    public void show(){
    }

    public void close(){
    }


    private void loadFxml(StackPane parentPanel) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Forms/PrinterDialog.fxml"));
        try {
            loader.load();
            Pane dialogPanel = (Pane)loader.getRoot();
            controller = loader.getController();
            controller.setUp(this);
        } catch (IOException e) {
           
            e.printStackTrace();
        }
       
    }

    public void createPageLayout(Printer printer , PageOrientation pageOrientation, double leftMargin , double rightMargin ,double topMargin, double bottomMargin){
        PageLayout pageLayout = printer.createPageLayout(Paper.A4, pageOrientation,leftMargin,rightMargin, topMargin,bottomMargin);
        checkPrinter.setPageLayout(pageLayout,printer);
    }

    public void printPage( ){
        checkPrinter.print();
    }



}
