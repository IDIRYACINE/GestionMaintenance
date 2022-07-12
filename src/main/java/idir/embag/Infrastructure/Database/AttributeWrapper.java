package idir.embag.Infrastructure.Database;

public class AttributeWrapper<T>{
    private T attributeName;

    private Object value;

    public AttributeWrapper(T attributeName, Object value) {
        this.attributeName = attributeName;
        this.value = value;
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
        this.value = value;
    }
    
}
