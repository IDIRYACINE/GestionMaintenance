package idir.embag.DataModels.Users;

public class Designation {
    private int designationId;
    private String designationName;

    public Designation(int designationId, String designationName) {
        this.designationId = designationId;
        this.designationName = designationName;
    }

    public int getDesignationId() {
        return designationId;
    }

    public String getDesignationName() {
        return designationName;
    }

}
