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
    private final String gear;   // NEW (nullable is fine)

    // keep the old 2-arg ctor for backward compatibility
    public FisherfolkItem(int id, String name) {
        this(id, name, null);
    }
    
    // new 3-arg ctor (for dock logs - get also the boatname->gear)
    public FisherfolkItem(int id, String name, String gear) {
        this.id = id;
        this.name = name;
        this.gear = gear;
    }
    
    public int getId() { return id; }
    public String getName() { return name; }
    public String getGear() { return gear; } //added 

    @Override
    public String toString() { return name; } // so ComboBox shows the name
}




