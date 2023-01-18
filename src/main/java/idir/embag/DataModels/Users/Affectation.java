package idir.embag.DataModels.Users;

public class Affectation {
    private int affectationId;
    private String affectationName;

    public Affectation(int designationId, String affectationName) {
        this.affectationId = designationId;
        this.affectationName = affectationName;
    }

    public int getAffectationId() {
        return affectationId;
    }

    public void setAffectationId(int designationId) {
        this.affectationId = designationId;
    }

    public void setAffectationName(String designationName) {
        this.affectationName = designationName;
    }

    public String getAffectationName() {
        return affectationName;
    }

}
