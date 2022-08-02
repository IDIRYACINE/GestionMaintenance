package idir.embag.DataModels.Session;

public class Session {
    private int sessionId;
    private String sessionStartDate;
    private String sessionEndDate;
    private double priceShift;
    private double quantityShift;
    
    public Session(int sessionId, String sessionStartDate, String sessionEndDate, double priceShift, double quantityShift) {
        this.sessionId = sessionId;
        this.sessionStartDate = sessionStartDate;
        this.sessionEndDate = sessionEndDate;
        this.priceShift = priceShift;
        this.quantityShift = quantityShift;
    }

    public int getSessionId() {
        return sessionId;
    }

    public String getSessionStartDate() {
        return sessionStartDate;
    }

    public String getSessionEndDate() {
        return sessionEndDate;
    }

    public double getPriceShift() {
        return priceShift;
    }

    public double getQuantityShift() {
        return quantityShift;
    }

    
}
