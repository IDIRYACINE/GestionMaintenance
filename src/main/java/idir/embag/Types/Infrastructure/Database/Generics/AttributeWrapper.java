package idir.embag.Types.Infrastructure.Database.Generics;

import idir.embag.DataModels.Metadata.EEventDataKeys;

public class AttributeWrapper{
    
    private String attributeName;

    private String value;

    public AttributeWrapper(EEventDataKeys attributeName, Object value) {
        this.attributeName = attributeName.toString();
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
        this.value = (String) value;
    }
    
}
