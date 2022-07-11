package idir.embag.Application.Models.Generics;

import java.util.List;


public interface ISearchable<T,R> {

    public List<R> search();

    public void setSearchParameters(T[] searchFields , String[] searchValues);

    public void setSearchResultFields(T[] resultFields);
}
