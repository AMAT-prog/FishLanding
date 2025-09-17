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

    public FisherfolkItem(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public int getId() { return id; }
    public String getName() { return name; }

    @Override
    public String toString() { return name; } // so ComboBox shows the name
    
    
}


