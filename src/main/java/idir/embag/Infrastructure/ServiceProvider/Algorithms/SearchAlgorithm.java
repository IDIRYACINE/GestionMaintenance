package idir.embag.Infrastructure.ServiceProvider.Algorithms;


public interface SearchAlgorithm<E,T,C extends Comparator<E,T>> {
    
    public E search(E[] list, T target);
}
