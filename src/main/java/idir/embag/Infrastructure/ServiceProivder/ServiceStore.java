package idir.embag.Infrastructure.ServiceProivder;

import java.util.ArrayList;
import java.util.List;

import idir.embag.Infrastructure.ServiceProivder.Algorithms.Comparator;
import idir.embag.Infrastructure.ServiceProivder.Algorithms.SearchAlgorithm;
import idir.embag.Infrastructure.ServiceProivder.Events.ServiceEvent;

@SuppressWarnings("rawtypes")
public abstract class ServiceStore {
    List<Service> _services = new ArrayList<Service>();
    final SearchAlgorithm<Service, Integer, Comparator<Service, Integer>> _searchAlgorithm;

    public ServiceStore(SearchAlgorithm<Service, Integer, Comparator<Service, Integer>> _searchAlgorithm) {
        this._searchAlgorithm = _searchAlgorithm;
    }

    void registerService(Service service) {
        _services.add(service);
    }

    void unregisterService(Service service) {
        _services.remove(service);
    }

    void sendEvent(ServiceEvent event) {
        Service service = _searchAlgorithm.search(_services, event.serviceId);
        if (service != null) {
            service.onEventForCallback(event);
        }
    }
}
