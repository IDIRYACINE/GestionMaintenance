package idir.embag.Ui.Components.TextFieldSkins;

import java.util.function.Function;

public class SkinErrorTester {
    private String message;
    private Function<String,Boolean> tester;

    public SkinErrorTester(String message, Function<String,Boolean> tester) {
        this.message = message;
        this.tester = tester;
    }


    public SkinErrorTester(String errorRequiredField, Object tester2) {
    }

    public String getMessage() {
        return message;
    }

    public Function<String,Boolean> getTester() {
        return tester;
    }

}
