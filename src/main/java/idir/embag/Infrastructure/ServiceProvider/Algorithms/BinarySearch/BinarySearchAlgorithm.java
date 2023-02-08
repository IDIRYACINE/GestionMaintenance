package idir.embag.Infrastructure.ServiceProvider.Algorithms.BinarySearch;

import java.util.List;

import idir.embag.Infrastructure.ServiceProvider.Algorithms.SearchAlgorithm;

public class BinarySearchAlgorithm<E, T>
        extends SearchAlgorithm<E, T, BinarySearchComparator<E, T>> {

    BinarySearchComparator<E, T> comparator;

    public BinarySearchAlgorithm(BinarySearchComparator<E, T> comparator) {
        this.comparator = comparator;
    }

    public BinarySearchAlgorithm() {
    }

    @Override
    public E search(List<E> list, T target) {
        int min = 0;
        int max = list.size() - 1;

        while (min <= max) {
            int mid = (min + max) / 2;
            E midVal = list.get(mid);

            if (comparator.isLessThan.apply(midVal, target)) {
                min = mid + 1;
            } else if (comparator.isGreaterThan.apply(midVal, target)) {
                max = mid - 1;
            } else {
                return midVal;
            }
        }

        return null;
    }

    public void setComparator(BinarySearchComparator<E, T> comparator) {
        this.comparator = comparator;
    }

}