/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author User
 */
import java.time.LocalDate;
import java.time.LocalTime;

public class DockLogRecord {
    private final int logId;
    private final int fisherId;
    private final LocalDate dockingDate;
    private final LocalTime arrival;
    private final LocalTime departure;
    private final String remarks;

    public DockLogRecord(int logId, int fisherId,
                         LocalDate dockingDate, LocalTime arrival,
                         LocalTime departure, String remarks) {
        this.logId = logId;
        this.fisherId = fisherId;
        this.dockingDate = dockingDate;
        this.arrival = arrival;
        this.departure = departure;
        this.remarks = remarks;
    }

    // getters
    public int getLogId() { return logId; }
    public int getFisherId() { return fisherId; }
    public LocalDate getDockingDate() { return dockingDate; }
    public LocalTime getArrival() { return arrival; }
    public LocalTime getDeparture() { return departure; }
    public String getRemarks() { return remarks; }
}

