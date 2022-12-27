package idir.embag.DataModels.Users;

public class DesignationPermission {
    final int userId;
    final int designationId;
    
    public int getUserId() {
        return userId;
    }

    public int getDesignationId() {
        return designationId;
    }

    public DesignationPermission(int userId, int designationId) {
        this.userId = userId;
        this.designationId = designationId;
    }

}
