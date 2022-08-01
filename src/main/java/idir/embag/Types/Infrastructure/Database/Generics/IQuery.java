package idir.embag.Types.Infrastructure.Database.Generics;

import java.util.Collection;
import java.util.Iterator;

public abstract class IQuery {
    
    protected String InsertWrapperToQuery(Collection<AttributeWrapper> attrs){
        String result = " (";
        String values = " VALUES(";

        int length = attrs.size();
        int lastElementIndex = length - 1;
        int i = 0;

        Iterator<AttributeWrapper> iterator = attrs.iterator();


        while (iterator.hasNext()) {
            AttributeWrapper attr = iterator.next();

            if(i<lastElementIndex){    
            result += attr.getAttributeName() +",";
            values += "'" +attr.getValue() +"',";
            }
            else{
                result += attr.getAttributeName() +")";
                values += "'" +attr.getValue() +"')";
            }

            i++;

        }

        return result + values;
    }

    protected <T> String UpdateWrapperToQuery(Collection<AttributeWrapper> attrs){
        String result = " Set ";
                
        int length = attrs.size();
        int lastElementIndex = length - 1;
        int i = 0;

        Iterator<AttributeWrapper> iterator = attrs.iterator();

        while (iterator.hasNext()) {
            AttributeWrapper attr = iterator.next();
            if(i<lastElementIndex){    
            result += attr.getAttributeName() + "= '" +  attr.getValue() + "',";
            }
            else{
                result += attr.getAttributeName() + "= ' " + attr.getValue()+ " '" ;
            }
            i++;
        }

        return result ;
    }

    protected String SearchWrapperToWhereClause(SearchWrapper searchWrapper){
        String result = "";
        Collection<AttributeWrapper> attrs = searchWrapper.getSearchAttributes();

        int length = attrs.size();
        int lastElementIndex = length - 1;
        int i = 0;

        Iterator<AttributeWrapper> iterator = attrs.iterator();

        while (iterator.hasNext()) {
            AttributeWrapper attr = iterator.next();
            if(i<lastElementIndex){    
            result += attr.getAttributeName() + "= '" +  attr.getValue() + "'AND";
            }
            else{
                result += attr.getAttributeName() + "= '" + attr.getValue()+ "'" ;
            }
            i++;
        }

        return result;
    }

}
