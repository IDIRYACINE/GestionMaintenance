package idir.embag.Infrastructure.Database.Generics;

import java.util.Iterator;
import java.util.Map;
import idir.embag.DataModels.Metadata.EEventDataKeys;

public abstract class IQuery {
    
    protected String InsertWrapperToQuery(Map<EEventDataKeys,Object> attrs){
        String result = " (";
        String values = " VALUES(";

        Iterator<EEventDataKeys> keys = attrs.keySet().iterator();

        Iterator<Object> rawValues = attrs.values().iterator();
        
        int length = attrs.size();
        int lastElementIndex = length - 1;
        int i = 0;

        while (keys.hasNext()) {

            if(i<lastElementIndex){    
            result += keys.next() +",";
            values += rawValues.next() +",";
            }
            else{
                result += keys.next() +")";
                values += rawValues.next() +")";
            }

            i++;

        }

        return result + values;
    }

    protected <T> String UpdateWrapperToQuery(Map<EEventDataKeys,Object> attrs){
        String result = " Set ";

        Iterator<EEventDataKeys> keys = attrs.keySet().iterator();
        Iterator<Object> rawValues = attrs.values().iterator();
        
        int length = attrs.size();
        int lastElementIndex = length - 1;
        int i = 0;

        while (keys.hasNext()) {
            if(i<lastElementIndex){    
            result += keys.next() + "=" + rawValues.next() + ",";
            }
            else{
                result += keys.next() + "=" + rawValues.next() + ")";
            }
            i++;
        }

        return result ;
    }

}
