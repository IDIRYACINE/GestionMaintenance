package idir.embag.Utility.Database.Implementations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;


public abstract class SearchQuery {
    private static ArrayList<String> values = new ArrayList<String>();
    private static ArrayList<Integer> positions = new ArrayList<Integer>();
    private static int valueCounter = 0 ;

    public static PreparedStatement filterRawQuery(String rawQueryString ,String queryString, Connection conn) throws SQLException{
        queryString = generateQuery(rawQueryString, queryString); 
        return  insertQueryValues(conn, queryString);
        
    }

    private static String generateQuery(String rawQueryString , String queryString){
        String[] sections = rawQueryString.split(";");

        int SECTIONS_COUNT = sections.length ;

        for (int i = 0 ; i < SECTIONS_COUNT ; i++ ){
            String[] values = sections[i].split(":|/");

            queryString += queryFormater(values );
            if (i != SECTIONS_COUNT - 1) { queryString += " AND " ;}              
        }
        queryString += ";";

        return queryString ;
    }

    private static PreparedStatement insertQueryValues(Connection conn , String queryString) throws SQLException{
        PreparedStatement statement = conn.prepareStatement(queryString);
        for (int i = 0 ; i < values.size() ; i++){
            String tempValue = values.get(i);
            int position = positions.get(i);
            statement.setString(position, tempValue);
            
        }
        valueCounter = 0 ;
        return statement ;
    }

    private static String queryFormater(String[] values ){
        String result = "";
        switch(values[0]){
            case "id" : result = singleValueFormater(values[0], values[1]);
                break ;
            case "location" : result = singleValueFormater(values[0], values[1]);
                break ;
            case "receiver" :  result =singleValueFormater(values[0], values[1]);
                break ;
            case "date" : result = rangeValueFormater(values[0],values[1], values[2]);
                break;
            case "amount" :result = rangeValueFormater(values[0],values[1], values[2]);
                break;                
        }
        return " (" + result + ") ";
    }

    private static String singleValueFormater(String columnName , String columnValue ){
        String result = columnName + " = ? " ;
        registerValue(columnValue);
        return result;
    }


    private static String rangeValueFormater(String columnName , String minValue , String maxValue){
        String result = columnName + " BETWEEN ? AND ? "  ;
        registerValue( minValue);
        registerValue( maxValue);
        return result;
    }

    private static void registerValue(String value){
        values.add(value);
        positions.add(valueCounter + 1) ;
        valueCounter = valueCounter + 1 ;
    }
   
    
}
