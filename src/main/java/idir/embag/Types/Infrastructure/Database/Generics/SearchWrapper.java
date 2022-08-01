package idir.embag.Types.Infrastructure.Database.Generics;

import java.util.Collection;

public class SearchWrapper {
    
    private Collection<AttributeWrapper> SearchAttributes;

    public SearchWrapper(Collection<AttributeWrapper> searchAttributes) {

        SearchAttributes = searchAttributes;
    }

    public Collection<AttributeWrapper> getSearchAttributes() {
        return SearchAttributes;
    }
    
}
