package idir.embag.Infrastructure.ServiceProvider.Algorithms.BinarySearch;

import java.util.function.BiFunction;

import idir.embag.Infrastructure.ServiceProvider.Algorithms.Comparator;

public class BinarySearchComparator<S, T> extends Comparator<S, T> {

    public final BiFunction<S, T, Boolean> isGreaterThan;

    public BinarySearchComparator(BiFunction<S, T, Boolean> isGreaterThan, BiFunction<S, T, Boolean> isLessThan) {
        this.isGreaterThan = isGreaterThan;
        this.isLessThan = isLessThan;
    }

    public final BiFunction<S, T, Boolean> isLessThan;

}