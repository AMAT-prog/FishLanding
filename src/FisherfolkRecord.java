/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author User
 */
// FisherfolkRecord.java
public class FisherfolkRecord {
    private final int fisherfolkId;
    private final String name;
    private final Integer age;
    private final String gender;
    private final String contactNumber;
    private final String address;
    private final String gear;
//    private final String boatName;
//    private final String licenseNumber;
    private final javafx.beans.property.BooleanProperty active;

    public FisherfolkRecord(int fisherfolkId, String name, Integer age, String gender,
                            String contactNumber, String address, String gear, boolean isActive) {
        this.fisherfolkId = fisherfolkId;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.contactNumber = contactNumber;
        this.address = address;
        this.gear = gear;
//        this.boatName = boatName;
//        this.licenseNumber = licenseNumber;
        this.active = new javafx.beans.property.SimpleBooleanProperty(isActive);
    }

    public int getFisherfolkId() { return fisherfolkId; }
    public String getName() { return name; }
    public Integer getAge() { return age; }
    public String getGender() { return gender; }
    public String getContactNumber() { return contactNumber; }
    public String getAddress() { return address; }
    public String getGear() { return gear; }
//    public String getBoatName() { return boatName; }
//    public String getLicenseNumber() { return licenseNumber; }
    public javafx.beans.property.BooleanProperty activeProperty() { return active; }
    public boolean isActive() { return active.get(); }
    public void setActive(boolean v) { active.set(v); }
}

