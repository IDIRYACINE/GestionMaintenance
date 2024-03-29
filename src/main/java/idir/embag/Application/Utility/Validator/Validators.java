package idir.embag.Application.Utility.Validator;

public abstract class Validators {

    public static Boolean isName(String name) {
        return !name.matches("^[a-zA-Z\\s]+$");
    }

    public static Boolean isEmail(String email){
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    public static Boolean emptyField(String text){
        return text == "";
    }

    public static Boolean isNumber(String text){
        return !text.matches("^[0-9]+$");
    }

    public static Boolean isPhoneNumber(String text){
        return text.matches("^[0-9]{10}$");
    }

    public static Boolean isPrice(String text){
        return text.matches("^[0-9]+(\\.[0-9]{1,2})?$");
    }

}
