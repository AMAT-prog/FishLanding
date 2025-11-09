/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author User
 */
// ConsumerItem.java
public class ConsumerItem {
    private final int id;
    private final String name;
    private final String contact; // optional
    public ConsumerItem(int id, String name) { this(id, name, null); }
    public ConsumerItem(int id, String name, String contact) {
        this.id = id; this.name = name; this.contact = contact;
    }
    public int getId() { return id; }
    public String getName() { return name; }
    public String getContact() { return contact; }
    @Override public String toString() { return name; }  // for ComboBox display
}

