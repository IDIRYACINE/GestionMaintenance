package idir.embag.Application.Controllers.Navigation;

import idir.embag.Ui.Components.IDialogContent;

public interface INavigationController {

    
    public void navigateToPanel(int panelId);
    
    public void displayPopup(IDialogContent content);
    
}
