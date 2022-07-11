package idir.embag.Application.Models.Settings;

import java.io.File;
import java.io.IOException;

import org.ini4j.*;

public class SettingsModel {
    private Wini ini ;
    private String[] xCoordinates;
    private String[] yCoordinates;

    private static final String[] defaultX = {"503","171","28","111","300","518","0.75"};
    private static final String[] defaultY = {"6","38","63","90","110","110","0.75"}; 
    private static final String[] COORDINATES_BLOC = {"Print coordinations","Print scales"} ;
    private static final String[] COORDINATES_PROPERITY = {"amount","firstStrAmount","secondStrAmount",
       "receiver","location","date" , "scale"};
     
    private static SettingsModel settingsModel ;
    

    private SettingsModel(){
        xCoordinates = new String[7];
        yCoordinates = new String[7];
        loadIniFile();
        
    }

    public static SettingsModel getInstance(){
        if (settingsModel != null){
            return settingsModel;
        }
        else {
            settingsModel = new SettingsModel();
            return settingsModel;
        }
    }

    public String[] getYcoordinates(){
        return yCoordinates;
    }

    public String[] getXcoordinates(){    
        return xCoordinates;
    }

    public void updateAmountCoordinates(String x , String y){
        xCoordinates[0] = x ;
        yCoordinates[0] = y;
    }

    public void updatefirstStrAmountCoordinates(String x , String y){
        xCoordinates[1] = x ;
        yCoordinates[1] = y;
    }

    public void updatesecondStrAmountCoordinates(String x , String y){
        xCoordinates[2] = x ;
        yCoordinates[2] = y;
    }

    public void updateDateCoordinates(String x , String y){
        xCoordinates[3] = x ;
        yCoordinates[3] = y;
    }

    public void updateLocationCoordinates(String x , String y){
        xCoordinates[4] = x ;
        yCoordinates[4] = y;
    }

    public void updateReceiverCoordinates(String x , String y){
        xCoordinates[5] = x ;
        yCoordinates[5] = y;
    }

    public void updateScaleCoordinates(String x , String y){
        xCoordinates[6] = x ;
        yCoordinates[6] = y;

    }

    public void resetSettings(){
        try{
        createDefaultIniFile();
        }
        catch(Exception exception){
        }
    }

    public void saveSettings(){
        try {
        
            for (int j = 0 ; j < COORDINATES_PROPERITY.length -1 ; j++){
                ini.put(COORDINATES_BLOC[0], COORDINATES_PROPERITY[j]+"X" ,xCoordinates[j]);
                ini.put(COORDINATES_BLOC[0], COORDINATES_PROPERITY[j]+"Y" ,yCoordinates[j]);
            }
            ini.put(COORDINATES_BLOC[1], COORDINATES_PROPERITY[6]+"X" ,xCoordinates[6]);
            ini.put(COORDINATES_BLOC[1], COORDINATES_PROPERITY[6]+"Y" ,yCoordinates[6]);
            
        ini.store();
        }
        catch(IOException exception){

        }
    }

    private void loadIniFile(){
        File settingsFile = new File("settings.ini");
        try{
            if (!settingsFile.exists()){
                settingsFile.createNewFile();
                ini = new Wini(settingsFile);
                createDefaultIniFile();
            }
            else{
                loadSettings(settingsFile);
            }
            
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
    }

    private void createDefaultIniFile() throws IOException{
        //children.add(createLineBar(14, 21, -10, 60, 24, -10));*/
       
            for (int j=0 ; j < COORDINATES_PROPERITY.length - 1 ; j++){
                ini.put(COORDINATES_BLOC[0], COORDINATES_PROPERITY[j]+"X" ,defaultX[j]);
                ini.put(COORDINATES_BLOC[0], COORDINATES_PROPERITY[j]+"Y" ,defaultY[j]);
                xCoordinates[j] = defaultX[j];
                yCoordinates[j] = defaultY[j];
            }
            xCoordinates[6] = defaultX[6];
            yCoordinates[6] = defaultY[6];            
        ini.put(COORDINATES_BLOC[1], COORDINATES_PROPERITY[6]+"X" ,xCoordinates[6]);
        ini.put(COORDINATES_BLOC[1], COORDINATES_PROPERITY[6]+"Y" ,yCoordinates[6]);
        
        ini.store();
        
    }

   

    private void loadSettings(File settingsFile) throws IOException{
        ini = new Wini(settingsFile);
            for (int j=0 ; j < COORDINATES_PROPERITY.length ; j++){
                xCoordinates[j] = ini.get(COORDINATES_BLOC[0], COORDINATES_PROPERITY[j]+"X");
                yCoordinates[j] = ini.get(COORDINATES_BLOC[0], COORDINATES_PROPERITY[j]+"Y");

            }
            xCoordinates[6] = ini.get(COORDINATES_BLOC[1], COORDINATES_PROPERITY[6]+"X");
            yCoordinates[6] = ini.get(COORDINATES_BLOC[1], COORDINATES_PROPERITY[6]+"Y");
    }

}
