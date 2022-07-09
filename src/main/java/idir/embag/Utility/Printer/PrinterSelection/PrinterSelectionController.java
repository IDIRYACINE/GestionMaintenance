package idir.embag.Utility.Printer.PrinterSelection;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.fxml.Initializable;
import javafx.print.PageOrientation;
import javafx.print.Printer;

public class PrinterSelectionController  implements Initializable{
    @FXML
    private TextField TopMargin,BottomMargin,LeftMargin,RightMargin;
    @FXML
    private RadioButton PaysageRadio,LandScapeRadio;
    @FXML
    private ComboBox<String> PrinterSelector;

    private final HashMap <String , Printer> printersMap = new HashMap<String ,Printer>();
    private final ToggleGroup pageOreintationToggle = new ToggleGroup();
    private String selectedPrinterName ;
    private double topMargin,bottomMargin,leftMargin,rightMargin;
    private PrinterSelectorModel printerSelectorModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUpPrinterSelectorOptions();
        initPrinterSelectionBox();
        initMarginListener();
        PaysageRadio.setToggleGroup(pageOreintationToggle);
        LandScapeRadio.setToggleGroup(pageOreintationToggle);
    }

    private void setUpPrinterSelectorOptions(){
        
        ObservableList<String> printersNames  =  FXCollections.observableArrayList();
        Printer[] availlablePrinters = getAvaillablePrinters();
        int PRINTERS_COUNT =  availlablePrinters.length;

        for (int i=0 ; i < PRINTERS_COUNT ; i++){
            String printerName = availlablePrinters[i].getName();
            printersNames.add(printerName);
            printersMap.put(printerName, availlablePrinters[i]);
        }

        PrinterSelector.setItems(printersNames);
    }

    private void initPrinterSelectionBox(){
        PrinterSelector.getSelectionModel().selectedItemProperty().addListener(
        (observable, oldValue, newValue) -> { selectedPrinterName = newValue;}
        );
    }

    private void initMarginListener(){
        TopMargin.textProperty().addListener(marginListener);
        BottomMargin.textProperty().addListener(marginListener);
        LeftMargin.textProperty().addListener(marginListener);
        RightMargin.textProperty().addListener(marginListener);
    }

    private ChangeListener<String> marginListener = new ChangeListener<String>(){
        Pattern pattern = Pattern.compile("(?<=id=).+?(?=,)");
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            Matcher matcher = pattern.matcher(observable.toString());
            String fieldName = "";
            while(matcher.find()){
                fieldName = matcher.group().toString();
            }
            switch (fieldName){
                case "TopMargin" : topMargin = Double.parseDouble(TopMargin.getText());
                break;
                case "BottomMargin" : bottomMargin = Double.parseDouble(BottomMargin.getText());
                break;
                case "LeftMargin" : leftMargin = Double.parseDouble(LeftMargin.getText());;
                break;
                default : rightMargin = Double.parseDouble(RightMargin.getText());;

            } 
        }
    };

    @FXML 
    void print(){
        PageOrientation pageOrientation = getPageOreintation();
        Printer printer = printersMap.get(selectedPrinterName);
        printerSelectorModel.createPageLayout(printer,pageOrientation,leftMargin,rightMargin,topMargin,bottomMargin);
        printerSelectorModel.printPage();
        printerSelectorModel.close();
    }
    @FXML
    void close(){
        printerSelectorModel.close();
    }

   
    private PageOrientation getPageOreintation(){
        if (pageOreintationToggle.getSelectedToggle() == PaysageRadio ){
            return PageOrientation.PORTRAIT;
        }
        else {
            return PageOrientation.LANDSCAPE;
        }
    }

    public void setUp(PrinterSelectorModel pModel){
        printerSelectorModel = pModel;
    }

    private Printer[] getAvaillablePrinters(){
        ObservableSet<Printer> printerSet = Printer.getAllPrinters();

        int PRINTERS_COUNT = printerSet.size();

        Printer[] availlablePrinters = new Printer[PRINTERS_COUNT] ;
        printerSet.toArray(availlablePrinters);
        
       return availlablePrinters;
    }

}
