package idir.embag.Infrastructure.Database.Generics;

public abstract class IQuery {
    
    protected <T> String InsertWrapperToQuery(AttributeWrapper[] attrs){
        String result = " (";
        String values = " VALUES(";
        int length = attrs.length;

        for(int i=0 ; i < length ; i++){
            if(i<length){    
            result += attrs[i].getAttributeName()+",";
            values += attrs[i].getValue()+",";
            }
            else{
                result += attrs[i].getAttributeName()+")";
                values += attrs[i].getValue()+")";
            }
        }
        return result + values;
    }

    protected <T> String UpdateWrapperToQuery(AttributeWrapper[] attrs){
        String result = " Set ";
        
        int length = attrs.length;

        for(int i=0 ; i < length ; i++){
            if(i<length){    
            result += attrs[i].getAttributeName()+"=" +attrs[i].getValue() +",";
    
            }
            else{
                result += attrs[i].getAttributeName()+"=" +attrs[i].getValue() +")";
            }
        }
        return result ;
    }

}
