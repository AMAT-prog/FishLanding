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

    //TRANSACTION AND SALES
    public static javafx.collections.ObservableList<TransactionViewRow> loadTransactionsView() {
        var list = javafx.collections.FXCollections.<TransactionViewRow>observableArrayList();

        String sql = """
            SELECT t.transaction_id,
                   t.fisherfolk_id,
                   t.catch_id,
                   t.buyer_name,
                   f.name AS fisherfolk_name,
                   s.species_name,
                   t.quantity_sold,
                   t.unit_price,
                   t.total_price,
                   t.payment_method,
                   t.payment_status,
                   t.transaction_date
            FROM transactions t
            JOIN fisherfolk f ON t.fisherfolk_id = f.fisherfolk_id
            JOIN catch c ON t.catch_id = c.catch_id
            JOIN species s ON c.species_id = s.species_id
            ORDER BY t.transaction_date DESC
            """;

        try (java.sql.Connection conn = ConnectDb();
             java.sql.PreparedStatement ps = conn.prepareStatement(sql);
             java.sql.ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                java.sql.Timestamp ts = rs.getTimestamp("transaction_date");
                java.time.LocalDateTime ldt = ts != null ? ts.toLocalDateTime() : null;

                list.add(new TransactionViewRow(
                rs.getInt("transaction_id"),
                rs.getInt("fisherfolk_id"),
                rs.getInt("catch_id"),
                rs.getString("buyer_name"),
                rs.getString("fisherfolk_name"),
                rs.getString("species_name"),
                rs.getDouble("quantity_sold"),
                rs.getDouble("unit_price"),
                rs.getDouble("total_price"),
                rs.getString("payment_method"),
                rs.getString("payment_status"),
                rs.getTimestamp("transaction_date") != null ? rs.getTimestamp("transaction_date").toLocalDateTime() : null
            ));

            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public static boolean deleteTransactionById(int id) {
        String sql = "DELETE FROM transactions WHERE transaction_id=?";
        try (java.sql.Connection conn = ConnectDb();
             java.sql.PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }
    
    //on adding/updating transactions
    // 1) Load active fisherfolk for the Seller combo
    public static javafx.collections.ObservableList<FisherfolkItem> loadActiveFisherfolkItems() {
        var list = javafx.collections.FXCollections.<FisherfolkItem>observableArrayList();
        String sql = "SELECT fisherfolk_id, name FROM fisherfolk WHERE is_active=1 ORDER BY name";
        try (java.sql.Connection c = ConnectDb();
             java.sql.PreparedStatement ps = c.prepareStatement(sql);
             java.sql.ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new FisherfolkItem(rs.getInt("fisherfolk_id"), rs.getString("name")));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    // 2) Load catch options by fisher (remaining > 0) from the view v_catch_available
    public static javafx.collections.ObservableList<CatchOption> loadCatchOptionsByFisher(int fisherId) {
        var list = javafx.collections.FXCollections.<CatchOption>observableArrayList();
        String sql = """
            SELECT catch_id, fisherfolk_id, species_id, species_name, price_per_kilo,
                   remaining_qty, catch_date
            FROM v_catch_available
            WHERE fisherfolk_id = ? AND remaining_qty > 0
            ORDER BY catch_date DESC, species_name
            """;
        try (java.sql.Connection c = ConnectDb();
             java.sql.PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, fisherId);
            try (java.sql.ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new CatchOption(
                        rs.getInt("catch_id"),
                        rs.getInt("fisherfolk_id"),
                        rs.getInt("species_id"),
                        rs.getString("species_name"),
                        rs.getDouble("price_per_kilo"),
                        rs.getDouble("remaining_qty"),
                        rs.getDate("catch_date").toLocalDate()
                    ));
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    // 3) INSERT transaction (total_price is generated; do NOT insert it)
    public static boolean insertTransaction(String buyer, int fisherId, int catchId,
                                            double qty, double unitPrice,
                                            String payMethod, String payStatus, String remarks) {
        String sql = """
            INSERT INTO transactions
                (buyer_name, fisherfolk_id, catch_id,
                 quantity_sold, unit_price, payment_method, payment_status, remarks)
            VALUES (?,?,?,?,?,?,?,?)
            """;
        try (java.sql.Connection c = ConnectDb();
             java.sql.PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, buyer);
            ps.setInt(2, fisherId);
            ps.setInt(3, catchId);
            ps.setBigDecimal(4, java.math.BigDecimal.valueOf(qty));
            ps.setBigDecimal(5, java.math.BigDecimal.valueOf(unitPrice));
            ps.setString(6, payMethod);
            ps.setString(7, payStatus);
            ps.setString(8, remarks);
            return ps.executeUpdate() == 1;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    // 4) UPDATE transaction (do not allow changing catch_id here to keep it simple)
    public static boolean updateTransaction(int txnId, String buyer,
                                            double qty, double unitPrice,
                                            String payMethod, String payStatus, String remarks) {
        String sql = """
            UPDATE transactions
               SET buyer_name=?, quantity_sold=?, unit_price=?,
                   payment_method=?, payment_status=?, remarks=?
             WHERE transaction_id=?
            """;
        try (java.sql.Connection c = ConnectDb();
             java.sql.PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, buyer);
            ps.setBigDecimal(2, java.math.BigDecimal.valueOf(qty));
            ps.setBigDecimal(3, java.math.BigDecimal.valueOf(unitPrice));
            ps.setString(4, payMethod);
            ps.setString(5, payStatus);
            ps.setString(6, remarks);
            ps.setInt(7, txnId);
            return ps.executeUpdate() == 1;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }
    
    public static double sumSoldForCatchExcluding(int txnId, int catchId) {
        String sql = "SELECT IFNULL(SUM(quantity_sold),0) AS sold_qty " +
                     "FROM transactions WHERE catch_id=? AND transaction_id<>?";
        try (Connection c = ConnectDb();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, catchId); 
            ps.setInt(2, txnId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getDouble("sold_qty");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0.0;
    }
    
    // total landed quantity for a catch
    public static double getCatchQuantity(int catchId) {
        String sql = "SELECT quantity FROM catch WHERE catch_id=?";
        try (Connection c = ConnectDb();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, catchId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getDouble(1);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return 0.0;
    }




}