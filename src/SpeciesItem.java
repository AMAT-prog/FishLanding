/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author User
 */
public class SpeciesItem {
    private final int id;
    private final String speciesName;

    public SpeciesItem(int id, String speciesName) {
        this.id = id;
        this.speciesName = speciesName;
    }
    public int getId() { return id; }
    public String getSpeciesName() { return speciesName; }

    @Override
    public String toString() { return speciesName; }
}

