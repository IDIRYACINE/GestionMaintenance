package idir.embag.Infrastructure;

import java.util.function.BiFunction;
import idir.embag.Infrastructure.ServiceProvider.Service;
import idir.embag.Infrastructure.ServiceProvider.ServiceStore;
import idir.embag.Infrastructure.ServiceProvider.Algorithms.Comparator;
import idir.embag.Infrastructure.ServiceProvider.Algorithms.SearchAlgorithm;
import idir.embag.Infrastructure.ServiceProvider.Algorithms.BinarySearch.BinarySearchAlgorithm;
import idir.embag.Infrastructure.ServiceProvider.Algorithms.BinarySearch.BinarySearchComparator;

@SuppressWarnings({"rawtypes", "unchecked"})
public class ServicesProvider extends ServiceStore {

    private ServicesProvider(SearchAlgorithm<Service, Integer, Comparator<Service, Integer>> searchAlgorithm) {
        super(searchAlgorithm);
    }

    private static ServicesProvider instance;

    public static ServicesProvider getInstance() {
        if (instance == null) {
            SearchAlgorithm<Service, Integer,Comparator<Service, Integer>> searchAlgorithm = (SearchAlgorithm) createSearchAlgorithm();

            instance = new ServicesProvider(searchAlgorithm);
        }
        return instance;
    }


    private static BinarySearchAlgorithm<Service, Integer> createSearchAlgorithm() {
        BinarySearchAlgorithm<Service, Integer> searchAlgorithm = new BinarySearchAlgorithm<Service, Integer>();
        
        BiFunction<Service, Integer,Boolean> isGreaterThan = (service, id) -> service.serviceId > id;
        BiFunction<Service, Integer,Boolean> isLessThan = (service, id) -> service.serviceId < id;

        BinarySearchComparator<Service, Integer> comparator = new BinarySearchComparator<Service, Integer>(isGreaterThan,isLessThan);

        searchAlgorithm.setComparator(comparator);
        
        return searchAlgorithm;
    }

}
