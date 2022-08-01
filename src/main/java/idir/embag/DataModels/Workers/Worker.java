package idir.embag.DataModels.Workers;

public class Worker {

    private String name;
    private int phone;
    private String email;
    private int id;

    public Worker(String name, int phone, String email, int id) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(int id) {
        this.id = id;
    }

    

    
    
}
