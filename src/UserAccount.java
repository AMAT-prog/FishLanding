/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author User
 */
// UserAccount.java
public class UserAccount {
    private final int userId;
    private final String username;
    private final String role;         // "Admin" | "Staff"
    private final String name;
    private final String contact;
    private final java.sql.Timestamp updatedAt;
    private final byte[] photo;        // may be null

    public UserAccount(int userId, String username, String role,
                       String name, String contact, java.sql.Timestamp updatedAt, byte[] photo) {
        this.userId = userId; this.username = username; this.role = role;
        this.name = name; this.contact = contact; this.updatedAt = updatedAt; this.photo = photo;
    }

    public int getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getRole() { return role; }
    public String getName() { return name; }
    public String getContact() { return contact; }
    public java.sql.Timestamp getUpdatedAt() { return updatedAt; }
    public byte[] getPhoto() { return photo; }
}

