package idir.embag.Infrastructure.ServiceProvider.Algorithms;

import java.util.List;

public interface SearchAlgorithm<E,T,C extends Comparator<E,T>> {
    
    public E search(List<E> list, T target);
}
