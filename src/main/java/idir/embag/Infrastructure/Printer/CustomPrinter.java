package idir.embag.Infrastructure.Printer;



import idir.embag.Infrastructure.Printer.PrinterSelection.PrinterSelectorModel;
import javafx.print.PageLayout;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Scale;


public class CustomPrinter {
    private Node node ;
    private StackPane pane ;
    private PageLayout pageLayout;
    private Printer printer ;
    public CustomPrinter(Node node , StackPane pane){
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
     
        return scales;
    }
    


  
    
}

