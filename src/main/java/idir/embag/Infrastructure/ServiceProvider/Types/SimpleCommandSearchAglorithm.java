package idir.embag.Infrastructure.ServiceProvider.Types;

import idir.embag.Infrastructure.ServiceProvider.Algorithms.Comparator;
import idir.embag.Infrastructure.ServiceProvider.Algorithms.SearchAlgorithm;
import idir.embag.Infrastructure.ServiceProvider.Events.SimpleServiceCommand;

@SuppressWarnings("rawtypes")
public interface SimpleCommandSearchAglorithm extends SearchAlgorithm<SimpleServiceCommand, Integer, Comparator<SimpleServiceCommand, Integer>> { 


}