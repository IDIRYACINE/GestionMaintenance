package idir.embag.Types.Infrastructure.Database.Generics;

public class AttributeWrapper{
    
    private String attributeName;

    private String value;

    public AttributeWrapper(String attributeName, Object value) {
        this.attributeName = attributeName;
        setValue(value);
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        if(!(value instanceof String)){
            this.value = value.toString();
            return;
        }
        this.value = (String) value;
    }
    
}
