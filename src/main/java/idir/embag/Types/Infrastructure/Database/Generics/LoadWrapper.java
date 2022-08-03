package idir.embag.Types.Infrastructure.Database.Generics;

public class LoadWrapper {
    private int limit;
    private int offset;

    
    public LoadWrapper(int limit, int offset) {
        this.limit = limit;
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

}
