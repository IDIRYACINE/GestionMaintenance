package idir.embag.DataModels.Session;

import java.sql.Timestamp;

public class Session {
    private Timestamp sessionId;
    private String sessionStartDate;
    private String sessionEndDate;
    private double priceShift;
    private double quantityShift;
    private boolean isActive;
    
    public Session(Timestamp sessionId,boolean isActive, String sessionStartDate,
        String sessionEndDate, double priceShift, double quantityShift) {

        this.sessionId = sessionId;
        this.isActive = isActive;
        this.sessionStartDate = sessionStartDate;
        this.sessionEndDate = sessionEndDate;
        this.priceShift = priceShift;
        this.quantityShift = quantityShift;
    }

    public Timestamp getSessionId() {
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

    public boolean isActive() {
        return isActive;
    }
    
}
