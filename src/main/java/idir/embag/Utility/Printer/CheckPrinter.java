package idir.embag.Utility.Printer;



import idir.embag.Application.Models.Settings.SettingsModel;
import idir.embag.Utility.Printer.PrinterSelection.PrinterSelectorModel;
import javafx.print.PageLayout;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Scale;


public class CheckPrinter {
    private Node node ;
    private StackPane pane ;
    private PageLayout pageLayout;
    private Printer printer ;
    private static SettingsModel settingsModel = SettingsModel.getInstance();


    public CheckPrinter(Node node , StackPane pane){
        this.node = node ;
        this.pane = pane;
    }

    public void printDialog(){
        if (Printer.getAllPrinters().size() > 0 ){
        PrinterSelectorModel printerSelectorModel = new PrinterSelectorModel(pane , this);
        printerSelectorModel.show();
        }
       
    }

    public void setPageLayout(PageLayout pLayout , Printer printer){
        pageLayout = pLayout;
        this.printer = printer ;
    }

    public void print(){
         double[] scales = loadScaleFromSettings();
        node.getTransforms().add(new Scale(scales[0], scales[1]));
        PrinterJob job =  PrinterJob.createPrinterJob(printer);
        
        if (job != null) {
        
          job.printPage(pageLayout,node);
          job.endJob();
            
        }
    }

    private double[] loadScaleFromSettings(){
        double[] scales = new double[2];
        scales[0] = Double.parseDouble(settingsModel.getXcoordinates()[6]);
        scales[1] = Double.parseDouble( settingsModel.getYcoordinates()[6]);
        return scales;
    }
    


  
    
}

