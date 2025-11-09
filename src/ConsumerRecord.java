/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author User
 */
public class ConsumerRecord {
    private final int consumerId;
    private final String name;
    private final String contact;
    private final String address;
    private final javafx.beans.property.BooleanProperty active;

    public ConsumerRecord(int consumerId, String name, String contact, String address, boolean isActive) {
        this.consumerId = consumerId;
        this.name = name;
        this.contact = contact;
        this.address = address;
        this.active = new javafx.beans.property.SimpleBooleanProperty(isActive);
    }

    public int getConsumerId() { return consumerId; }
    public String getName() { return name; }
    public String getContact() { return contact; }
    public String getAddress() { return address; }

    public boolean isActive() { return active.get(); }
    public void setActive(boolean v) { active.set(v); }
    public javafx.beans.property.BooleanProperty activeProperty() { return active; }
}

