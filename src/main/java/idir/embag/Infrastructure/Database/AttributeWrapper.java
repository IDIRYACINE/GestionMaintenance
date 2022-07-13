package idir.embag.Infrastructure.Database;

public class AttributeWrapper<T>{
    private T attributeName;

    private String value;

    public AttributeWrapper(T attributeName, Object value) {
        this.attributeName = attributeName;
        setValue(value);
    }

    public T getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(T attributeName) {
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
