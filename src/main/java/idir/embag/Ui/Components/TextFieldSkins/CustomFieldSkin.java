package idir.embag.Ui.Components.TextFieldSkins;

import java.util.ArrayList;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.skin.TextFieldSkin;

public class CustomFieldSkin extends TextFieldSkin {
    Label errorLabel;
    ArrayList<SkinErrorTester> testers = new ArrayList<>();

    public CustomFieldSkin(TextField control, Label errorLabel) {
        super(control);
        this.errorLabel = errorLabel;
    }

    @Override
    public void replaceText(int start, int end, String text) {

        SkinErrorTester tester = null;
        for (SkinErrorTester t : testers) {

            if (t.getTester().apply(text)) {
                tester = t;
                break;
            }
        }

        if (tester != null)
            errorLabel.setText(tester.getMessage());

        else {
            errorLabel.setText("");
            super.replaceText(start, end, text);
        }
    }

    public void addErrorTester(SkinErrorTester tester) {
        testers.add(tester);
    }

    public boolean checkError() {
        SkinErrorTester tester = null;
        for (SkinErrorTester t : testers) {

            if (t.getTester().apply(getSkinnable().getText())) {
                tester = t;
                break;
            }
        }

        if (tester != null) {
            errorLabel.setText(tester.getMessage());
            return true;
        }

        else {
            errorLabel.setText("");
            return false;
        }
    }

}
