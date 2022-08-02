package idir.embag.Types.Application.History;

import idir.embag.DataModels.Metadata.EHistoryTypes;

public interface IHistoryController {
    public void search();
    public void refresh();
    public void selectHistoryHelper(EHistoryTypes type);
}
