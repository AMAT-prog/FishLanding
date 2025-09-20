/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author User
 */
public class FisherfolkItem {
    private final int id;
    private final String name;
    private final String boatName;   // NEW (nullable is fine)

    // keep the old 2-arg ctor for backward compatibility
    public FisherfolkItem(int id, String name) {
        this(id, name, null);
    }
    
    // new 3-arg ctor (for dock logs - get also the boatname)
    public FisherfolkItem(int id, String name, String boatName) {
        this.id = id;
        this.name = name;
        this.boatName = boatName;
    }
    
    public int getId() { return id; }
    public String getName() { return name; }
    public String getBoatName() { return boatName; } //added 

    @Override
    public String toString() { return name; } // so ComboBox shows the name
}




