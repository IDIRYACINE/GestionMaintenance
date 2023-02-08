package idir.embag.Infrastructure.ServiceProvider;

import java.util.ArrayList;
import java.util.List;

import idir.embag.Infrastructure.ServiceProvider.Algorithms.Comparator;
import idir.embag.Infrastructure.ServiceProvider.Algorithms.SearchAlgorithm;
import idir.embag.Infrastructure.ServiceProvider.Events.ServiceEvent;

@SuppressWarnings("rawtypes")
public abstract class ServiceStore {
    List<Service> _services = new ArrayList<Service>();
    final SearchAlgorithm<Service, Integer, Comparator<Service, Integer>> _searchAlgorithm;

    public ServiceStore(SearchAlgorithm<Service, Integer, Comparator<Service, Integer>> _searchAlgorithm) {
        this._searchAlgorithm = _searchAlgorithm;
    }

    public void registerService(Service service) {
        _services.add(service);
    }

    public void unregisterService(Service service) {
        _services.remove(service);
    }

    public void sendEvent(ServiceEvent event) {
        Service service = _searchAlgorithm.search(_services, event.serviceId);
        if (service != null) {
            service.onEventForCallback(event);
        }
    }
}
