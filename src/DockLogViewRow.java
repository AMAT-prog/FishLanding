/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author User
 */

//for table in DOCKING LOGS
import java.time.LocalDate;
import java.time.LocalTime;

public class DockLogViewRow {
    private final int logId;
    private final int fisherfolkId;
    private final String fisherfolkName;
    private final String boatName;
    private final LocalDate dockingDate;
    private final LocalTime arrivalTime;
    private final LocalTime departureTime; // may be null
    private final String remarks;

    public DockLogViewRow(int logId, int fisherfolkId, String fisherfolkName, String boatName,
                          LocalDate dockingDate, LocalTime arrivalTime, LocalTime departureTime, String remarks) {
        this.logId = logId;
        this.fisherfolkId = fisherfolkId;
        this.fisherfolkName = fisherfolkName;
        this.boatName = boatName;
        this.dockingDate = dockingDate;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
        this.remarks = remarks;
    }

    public int getLogId() { return logId; }
    public int getFisherfolkId() { return fisherfolkId; }
    public String getFisherfolkName() { return fisherfolkName; }
    public String getBoatName() { return boatName; }
    public LocalDate getDockingDate() { return dockingDate; }
    public LocalTime getArrivalTime() { return arrivalTime; }
    public LocalTime getDepartureTime() { return departureTime; }
    public String getRemarks() { return remarks; }
}

