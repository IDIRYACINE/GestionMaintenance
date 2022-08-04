package idir.embag.Types.Infrastructure.Database.Generics;

@SuppressWarnings("rawtypes")
public class AttributeWrapper{
    
    private String attributeName;

    private String value;

    public AttributeWrapper(Enum attributeName, Object value) {
        this.attributeName = attributeName.toString();
        this.value = value.toString();
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
        this.value = value.toString();
    }
    
}
