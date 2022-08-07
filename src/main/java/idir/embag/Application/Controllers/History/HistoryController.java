package idir.embag.Application.Controllers.History;

import idir.embag.DataModels.Metadata.EHistoryTypes;
import idir.embag.Types.Application.History.IHistoryController;
import idir.embag.Types.Application.History.IHistoryHelper;
import javafx.scene.Node;

public class HistoryController implements  IHistoryController {

    private IHistoryHelper selectedHelper ,sessionHelper,recordHelper;

    public HistoryController(IHistoryHelper sessionHelper, IHistoryHelper recordHelper) {
        this.sessionHelper = sessionHelper;
        this.recordHelper = recordHelper;
    }



    @Override
    public void search() {
       selectedHelper.search();
    }



    @Override
    public void refresh() {
        selectedHelper.refresh();
    }



    @Override
    public void selectHistoryHelper(EHistoryTypes type) {
        switch (type) {
            case SessionRecord:
                selectedHelper = recordHelper;
                break;
            case Session:
                selectedHelper = sessionHelper;
                break;
        }
    }



    @Override
    public Node getTableView() {
        return selectedHelper.getView();
    }

   
    
}
