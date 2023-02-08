package idir.embag.Infrastructure.ServiceProivder.Algorithms;

import java.util.List;

public abstract class SearchAlgorithm<E,T,C extends Comparator<E,T>> {
    SearchAlgorithm() {
    }
    public abstract E search(List<E> list, T target);
}
