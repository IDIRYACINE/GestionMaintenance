package idir.embag.Types.Application.Navigation;

import idir.embag.Types.Panels.Components.IDialogContent;

public interface INavigationController {

    
    public void navigateToPanel(int panelId);
    
    public void displayPopup(IDialogContent content);
    
}
