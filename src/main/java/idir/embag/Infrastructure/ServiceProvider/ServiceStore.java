package idir.embag.Infrastructure.ServiceProvider;

import idir.embag.Infrastructure.ServiceProvider.Algorithms.Comparator;
import idir.embag.Infrastructure.ServiceProvider.Algorithms.SearchAlgorithm;
import idir.embag.Infrastructure.ServiceProvider.Events.ServiceEvent;

@SuppressWarnings("rawtypes")
public abstract class ServiceStore {
    Service[] services ;
    final SearchAlgorithm<Service, Integer, Comparator<Service, Integer>> _searchAlgorithm;

    public ServiceStore(SearchAlgorithm<Service, Integer, Comparator<Service, Integer>> _searchAlgorithm) {
        this._searchAlgorithm = _searchAlgorithm;
    }

    public void registerService(Service service) {
        services[service.serviceId] = service;
    }

    public void unregisterService(Service service) {
       
    }

    public void sendEvent(ServiceEvent event) {
        Service service = _searchAlgorithm.search(services, event.serviceId);
        if (service != null) {
            
        }
    }
}
