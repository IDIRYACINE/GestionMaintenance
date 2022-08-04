package idir.embag.Types.Infrastructure.Database.Generics;

import java.sql.SQLException;
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

    protected String InsertCollectionToQuery(Collection<AttributeWrapper[]> collection){
        String attributes = " ";
        String values = " VALUES ";

        int collectionLength = collection.size();
        int lastCollectionIndex = collectionLength - 1;

        int collectionIndex = 0;

        Iterator<AttributeWrapper[]> collectionIterator = collection.iterator();

        AttributeWrapper[] firstAttrs = collection.iterator().next();

        attributes += formatAttributesNames(firstAttrs);

        while (collectionIterator.hasNext()) { 
            AttributeWrapper[] attrs = collectionIterator.next();

            values += formatAttributesValues(attrs);

            if(collectionIndex != lastCollectionIndex){
                values += ",";
            }

            collectionIndex++;

        }
        return attributes + values;
    }

    private String formatAttributesValues(AttributeWrapper[] attrs){
        String result = "(";
        int length = attrs.length;
        int lastElementIndex = length - 1;

        for (int i = 0 ;i<lastElementIndex;i++){
            result += "'" + attrs[i].getValue() + "',";

        }
        result += "'" + attrs[lastElementIndex].getValue() + "')";

        return result;
    }

    private String formatAttributesNames(AttributeWrapper[] attrs){
        String result = "(";
        int length = attrs.length;
        int lastElementIndex = length - 1;

        for (int i = 0 ;i<lastElementIndex;i++){
            result += "'"+ attrs[i].getAttributeName() + "',";

        }
        result += "'"+ attrs[lastElementIndex].getAttributeName() + "')";

        return result;
    }

    public abstract void CreateIndexes() throws SQLException;

}
