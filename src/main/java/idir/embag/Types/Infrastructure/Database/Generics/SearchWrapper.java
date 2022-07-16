package idir.embag.Types.Infrastructure.Database.Generics;

import java.util.List;

public class SearchWrapper {
    
    private List<Object> resultAttributes;
    private List<Object> SearchAttributes;

    public SearchWrapper(List<Object> resultAttributes, List<Object> searchAttributes) {
        this.resultAttributes = resultAttributes;
        SearchAttributes = searchAttributes;
    }

    public List<Object> getResultAttributes() {
        return resultAttributes;
    }

    public List<Object> getSearchAttributes() {
        return SearchAttributes;
    }
    
    
    
}
