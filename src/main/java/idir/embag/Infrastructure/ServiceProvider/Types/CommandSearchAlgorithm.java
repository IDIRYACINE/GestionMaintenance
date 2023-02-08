package idir.embag.Infrastructure.ServiceProvider.Types;

import idir.embag.Infrastructure.ServiceProvider.Algorithms.Comparator;
import idir.embag.Infrastructure.ServiceProvider.Algorithms.SearchAlgorithm;
import idir.embag.Infrastructure.ServiceProvider.Events.ServiceCommand;

@SuppressWarnings("rawtypes")
public interface CommandSearchAlgorithm extends SearchAlgorithm<ServiceCommand, Integer, Comparator<ServiceCommand, Integer>> { 


}