package idir.embag.DataModels.Users;

public class AffectationPermission {
    final int id;
    final int affectationId;
    
    public int getId() {
        return id;
    }

    public int getAffectationId() {
        return affectationId;
    }

    public AffectationPermission(int id, int affectationId) {
        this.id = id;
        this.affectationId = affectationId;
    }

}
