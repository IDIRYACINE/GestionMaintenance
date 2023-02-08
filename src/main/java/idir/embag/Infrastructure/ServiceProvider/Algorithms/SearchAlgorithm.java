package idir.embag.Infrastructure.ServiceProvider.Algorithms;

import java.util.List;

public abstract class SearchAlgorithm<E,T,C extends Comparator<E,T>> {
    
    public abstract E search(List<E> list, T target);
}
