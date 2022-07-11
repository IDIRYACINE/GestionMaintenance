package idir.embag.Application.Models.Settings;

import java.io.File;
import java.io.IOException;

import org.ini4j.*;

public class SettingsModel {
    private Wini ini ;
   

   
     
    private static SettingsModel settingsModel ;
    

    private SettingsModel(){
     
        
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

   
    public void resetSettings(){
        try{
        createDefaultIniFile();
        }
        catch(Exception exception){
        }
    }

    public void saveSettings(){
        try {
        
           
        //ini.put(COORDINATES_BLOC[1], COORDINATES_PROPERITY[6]+"X" ,xCoordinates[6]);
            
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
       
             
        //ini.put(COORDINATES_BLOC[1], COORDINATES_PROPERITY[6]+"X" ,xCoordinates[6]);
      
        
        ini.store();
        
    }

   

    private void loadSettings(File settingsFile) throws IOException{
        ini = new Wini(settingsFile);
          
        //yCoordinates[6] = ini.get(COORDINATES_BLOC[1], COORDINATES_PROPERITY[6]+"Y");
    }

}
