package idir.embag.Utility.Printer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import idir.embag.Application.Models.Settings.SettingsModel;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class PrintModel extends Pane{
    private static Font defaultFont =  new Font("Serif Bold", 13f);
    private static Font smallFont = new Font("Serif Bold",12f);
    private static SettingsModel settingsModel = SettingsModel.getInstance();

    public PrintModel(){
       //TODO:Auto generated example
    }

    private void setUpBounds(){
        double ccpCheckWidth = 642.3;
        double ccpCheckHeight = 264.6;
        setMinSize(ccpCheckWidth,ccpCheckHeight);
    }

    private void setUpLabels(String amount , String amountStringF ,String amountStringS , String receiver , String location ){
        ObservableList<Node> children =  getChildren();
        double[] xCoordinates = new double[7];
        double[] yCoordinates = new double[7];


        children.add(createLabel(amount, 122, TextAlignment.LEFT,Pos.CENTER_RIGHT, defaultFont, xCoordinates[0], yCoordinates[0]));
        children.add(createLabel(amountStringF, 390, TextAlignment.LEFT,Pos.CENTER_LEFT, defaultFont, xCoordinates[1], yCoordinates[1]));
        children.add(createLabel(amountStringS, 530, TextAlignment.LEFT,Pos.CENTER_LEFT, defaultFont,  xCoordinates[2], yCoordinates[2]));
        children.add(createLabel(receiver, 480, TextAlignment.CENTER,Pos.CENTER_LEFT, defaultFont,  xCoordinates[5], yCoordinates[5]));
        children.add(createLabel(location, 180, TextAlignment.RIGHT,Pos.CENTER_RIGHT, smallFont,  xCoordinates[4], yCoordinates[4]));
        children.add(createLabel(getDate(), 92, TextAlignment.RIGHT,Pos.CENTER_LEFT, smallFont,  xCoordinates[3], yCoordinates[3]));
        children.add(createLineBar(4, 6, 0, 54, 23, 0));
        children.add(createLineBar(14, 21, -10, 60, 24, -10));

    }


    private Label createLabel(String value , double width ,TextAlignment textAlignment ,Pos labelAlignment,Font font, double posX , double posY){
        Label label = new Label(value);
        label.setMinWidth(width);
        label.setTextAlignment(textAlignment);
        label.setFont(font);
        label.setAlignment(labelAlignment);
        label.setLayoutX(posX);
        label.setLayoutY(posY);
        return label;
    }

    private String getDate(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");  
        LocalDateTime now = LocalDateTime.now();  
        return dtf.format(now) ;
    }

    private Line createLineBar(double Xset , double Yset , double Xstart ,double Xend , double Ystart , double Yend){
        Line line = new Line();
        line.setStartX(Xstart);
        line.setStartY(Ystart);
        line.setEndX(Xend);
        line.setEndY(Yend);
        line.setLayoutX(Xset);
        line.setLayoutY(Yset);
        return line ;
    }



}
