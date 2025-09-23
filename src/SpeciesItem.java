/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author User
 */
// SpeciesItem.java
public class SpeciesItem {
    private int id;
    private String speciesName;
    private String description; // nullable

    public SpeciesItem(int id, String speciesName) {
        this(id, speciesName, null);
    }
    public SpeciesItem(int id, String speciesName, String description) {
        this.id = id;
        this.speciesName = speciesName;
        this.description = description;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getSpeciesName() { return speciesName; }
    public void setSpeciesName(String speciesName) { this.speciesName = speciesName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    @Override public String toString() { return speciesName; }
}


