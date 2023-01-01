package idir.embag.DataModels.Users;

public class DesignationPermission {
    final int id;
    final int designationId;
    
    public int getId() {
        return id;
    }

    public int getDesignationId() {
        return designationId;
    }

    public DesignationPermission(int id, int designationId) {
        this.id = id;
        this.designationId = designationId;
    }

}
