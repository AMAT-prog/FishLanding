/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;
/**
 *
 * @author User
 */
public class mysqlconnect {
    public static Connection conn = null;
    public static  PreparedStatement pst,pst1;
    public static ResultSet rs,rs1;
 
  
     
    public static Connection ConnectDb(){
        
        try{
       Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/fish_landing_db", "root", "root");//, "root","root"
           // JOptionPane.showMessageDialog(null,"Connection Established!");
            return conn;
} catch (Exception e){
                JOptionPane.showMessageDialog(null,e);
                return null;
}
 }
    
    // GET CATCH RECORDS
// Shows fisherfolk_name and species_name while keeping IDs
public static ObservableList<Catch> getCatch() {
    ObservableList<Catch> list = FXCollections.observableArrayList();

    String sql =
        "SELECT " +
        "  c.catch_id, " +
        "  c.fisherfolk_id, f.name AS fisherfolk_name, " +
        "  c.species_id,   s.species_name, " +
        "  c.quantity, c.price_per_kilo, " +
        "  (c.quantity * c.price_per_kilo) AS total_value, " +
        "  c.catch_date, c.docking_time, c.remarks " +
        "FROM catch c " +
        "JOIN fisherfolk f ON c.fisherfolk_id = f.fisherfolk_id " +
        "JOIN species   s ON c.species_id    = s.species_id " +
        "ORDER BY c.catch_date DESC, c.docking_time DESC";

    try (Connection conn = ConnectDb();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            list.add(new Catch(
                rs.getInt("catch_id"),
                rs.getInt("fisherfolk_id"),
                rs.getString("fisherfolk_name"),
                rs.getInt("species_id"),
                rs.getString("species_name"),
                rs.getDouble("quantity"),
                rs.getDouble("price_per_kilo"),
                rs.getDouble("total_value"),
                rs.getDate("catch_date").toLocalDate(),
                rs.getTime("docking_time") != null
                    ? rs.getTime("docking_time").toLocalTime()
                    : null,
                rs.getString("remarks")
            ));
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}

        //FISHERMEN RECORDS
    public static javafx.collections.ObservableList<FisherfolkRecord> loadFisherfolk() {
        var list = javafx.collections.FXCollections.<FisherfolkRecord>observableArrayList();
        String sql = "SELECT fisherfolk_id, name, age, gender, contact_number, address, " +
                     "boat_name, license_number, is_active " +
                     "FROM fisherfolk ORDER BY name ASC";
        try (var conn = mysqlconnect.ConnectDb();
             var ps = conn.prepareStatement(sql);
             var rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new FisherfolkRecord(
                    rs.getInt("fisherfolk_id"),
                    rs.getString("name"),
                    (Integer) (rs.getObject("age") == null ? null : rs.getInt("age")),
                    rs.getString("gender"),
                    rs.getString("contact_number"),
                    rs.getString("address"),
                    rs.getString("boat_name"),
                    rs.getString("license_number"),
                    rs.getBoolean("is_active")
                ));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public static boolean updateFisherfolkActive(int id, boolean active) {
        String sql = "UPDATE fisherfolk SET is_active=? WHERE fisherfolk_id=?";
        try (var conn = mysqlconnect.ConnectDb();
             var ps = conn.prepareStatement(sql)) {
            ps.setBoolean(1, active);
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static int countCatchByFisher(int id) {
        String sql = "SELECT COUNT(*) FROM catch WHERE fisherfolk_id=?";
        try (var conn = mysqlconnect.ConnectDb();
             var ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (var rs = ps.executeQuery()) { rs.next(); return rs.getInt(1); }
        } catch (Exception ex) { ex.printStackTrace(); return -1; }
    }

    public static int countTransactionsByFisher(int id) {
        String sql = "SELECT COUNT(*) FROM transactions WHERE fisherfolk_id=?";
        try (var conn = mysqlconnect.ConnectDb();
             var ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (var rs = ps.executeQuery()) { rs.next(); return rs.getInt(1); }
        } catch (Exception ex) { ex.printStackTrace(); return -1; }
    }

    public static boolean deleteFisherById(int id) {
        String sql = "DELETE FROM fisherfolk WHERE fisherfolk_id=?";
        try (var conn = mysqlconnect.ConnectDb();
             var ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    //fishermen catches, dock logs, transactions each (HISTORY PER FISHERMAN)
    public static ObservableList<CatchRecord> getCatchesByFisher(int fisherId) {
        var list = FXCollections.<CatchRecord>observableArrayList();
        String sql = """
            SELECT c.catch_id, s.species_name, c.quantity, c.price_per_kilo,
                   (c.quantity * c.price_per_kilo) AS total_value, c.catch_date
            FROM catch c
            JOIN species s ON c.species_id = s.species_id
            WHERE c.fisherfolk_id = ?
            ORDER BY c.catch_date DESC
            """;
        try (Connection conn = ConnectDb();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, fisherId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new CatchRecord(
                    rs.getInt("catch_id"),
                    fisherId,
                    0, rs.getString("species_name"), // speciesId optional
                    rs.getDouble("quantity"),
                    rs.getDouble("price_per_kilo"),
                    rs.getDouble("total_value"),
                    rs.getDate("catch_date").toLocalDate(),
                    null, // dockingTime optional
                    null  // remarks optional
                ));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public static ObservableList<TransactionRecord> getTransactionsByFisher(int fisherId) {
        var list = FXCollections.<TransactionRecord>observableArrayList();
        String sql = """
            SELECT t.transaction_id, t.buyer_name, t.quantity_sold, t.unit_price,
                   t.total_price, t.payment_status
            FROM transactions t
            WHERE t.fisherfolk_id = ?
            ORDER BY t.transaction_date DESC
            """;
        try (Connection conn = ConnectDb();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, fisherId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new TransactionRecord(
                    rs.getInt("transaction_id"),
                    fisherId,
                    rs.getString("buyer_name"),
                    rs.getDouble("quantity_sold"),
                    rs.getDouble("unit_price"),
                    rs.getDouble("total_price"),
                    rs.getString("payment_status")
                ));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public static ObservableList<DockLogRecord> getDockLogsByFisher(int fisherId) {
        var list = FXCollections.<DockLogRecord>observableArrayList();
        String sql = """
            SELECT log_id, docking_date, arrival_time, departure_time, remarks
            FROM docking_logs
            WHERE fisherfolk_id = ?
            ORDER BY docking_date DESC
            """;
        try (Connection conn = ConnectDb();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, fisherId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new DockLogRecord(
                    rs.getInt("log_id"),
                    fisherId,
                    rs.getDate("docking_date").toLocalDate(),
                    rs.getTime("arrival_time").toLocalTime(),
                    rs.getTime("departure_time") != null ? rs.getTime("departure_time").toLocalTime() : null,
                    rs.getString("remarks")
                ));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

}