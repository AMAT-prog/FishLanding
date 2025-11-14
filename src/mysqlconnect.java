/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
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

        //FISHERMEN RECORDS in mysqlconnect.java
    public static javafx.collections.ObservableList<FisherfolkRecord> loadFisherfolk() {
        var list = javafx.collections.FXCollections.<FisherfolkRecord>observableArrayList();
        String sql = "SELECT fisherfolk_id, name, age, gender, contact_number, address, " +
                     "gear, is_active " +
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
                    rs.getString("gear"),
//                    rs.getString("boat_name"),
//                    rs.getString("license_number"),
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
    
    public static ObservableList<TransactionViewRow> loadTransactionsView() {
    var list = FXCollections.<TransactionViewRow>observableArrayList();
    String sql = """
        SELECT
            t.transaction_id,
            t.consumer_id,
            COALESCE(t.species_id, ca.species_id) AS species_id,
            t.buyer_name,
            csm.name AS consumer_name,
            sp.species_name,
            t.quantity_sold,
            t.unit_price,
            t.total_price,
            t.payment_method,
            t.remarks,
            t.payment_status,
            t.transaction_date
        FROM transactions t
        LEFT JOIN consumers csm ON csm.consumer_id = t.consumer_id
        LEFT JOIN catch ca      ON ca.catch_id     = t.catch_id         -- legacy support
        LEFT JOIN species sp    ON sp.species_id   = COALESCE(t.species_id, ca.species_id)
        ORDER BY t.transaction_date DESC
        """;
    try (var conn = ConnectDb();
         var ps = conn.prepareStatement(sql);
         var rs = ps.executeQuery()) {

        while (rs.next()) {
            var ts = rs.getTimestamp("transaction_date");
            list.add(new TransactionViewRow(
                rs.getInt("transaction_id"),
                rs.getInt("consumer_id"),
                rs.getInt("species_id"),                 // << use the selected species_id
                rs.getString("buyer_name"),
                rs.getString("consumer_name"),
                rs.getString("species_name"),
                rs.getDouble("quantity_sold"),
                rs.getDouble("unit_price"),
                rs.getDouble("total_price"),
                rs.getString("payment_method"),
                rs.getString("remarks"),
                rs.getString("payment_status"),
                ts != null ? ts.toLocalDateTime() : null
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
    // ALSO USED ON DOCKING LOGS (for getting boatname->gear)
    public static javafx.collections.ObservableList<FisherfolkItem> loadActiveFisherfolkItems() {
        var list = javafx.collections.FXCollections.<FisherfolkItem>observableArrayList();
        String sql = "SELECT fisherfolk_id, name, gear FROM fisherfolk WHERE is_active=1 ORDER BY name";
        try (java.sql.Connection c = ConnectDb();
             java.sql.PreparedStatement ps = c.prepareStatement(sql);
             java.sql.ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new FisherfolkItem(
                    rs.getInt("fisherfolk_id"),
                    rs.getString("name"),
                    rs.getString("gear")
                ));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    
    // B) Catch options (available stock) — no fisher name in label
    public static javafx.collections.ObservableList<CatchOption> loadCatchOptionsAvailable() {
        var list = javafx.collections.FXCollections.<CatchOption>observableArrayList();
        String sql = """
            SELECT c.catch_id, c.fisherfolk_id, c.species_id, s.species_name,
                   c.price_per_kilo,
                   (c.quantity - IFNULL(SUM(t.quantity_sold),0)) AS remaining_qty,
                   c.catch_date
            FROM catch c
            JOIN species s ON s.species_id = c.species_id
            LEFT JOIN transactions t ON t.catch_id = c.catch_id
            GROUP BY c.catch_id, c.fisherfolk_id, c.species_id, s.species_name,
                     c.price_per_kilo, c.quantity, c.catch_date
            HAVING remaining_qty > 0
            ORDER BY c.catch_date DESC, s.species_name
            """;
        try (var c = ConnectDb(); var ps = c.prepareStatement(sql); var rs = ps.executeQuery()) {
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
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

   public static boolean insertTransactionByConsumer(int consumerId, int speciesId,
                                                  double qty, double unitPrice,
                                                  String payMethod, String payStatus,
                                                  String remarks, String buyerText) {
    String sql = """
        INSERT INTO transactions
            (consumer_id, species_id, catch_id,
             buyer_name, quantity_sold, unit_price,
             payment_method, payment_status, remarks)
        VALUES (
          ?, ?, 
          (SELECT catch_id 
             FROM catch 
            WHERE species_id = ? 
            ORDER BY catch_date DESC 
            LIMIT 1),
          ?, ?, ?, ?, ?, ?
        )
        """;
    try (var c = ConnectDb(); var ps = c.prepareStatement(sql)) {
        ps.setInt(1, consumerId);        // consumer_id
        ps.setInt(2, speciesId);         // species_id
        ps.setInt(3, speciesId);         // for the subquery (catch_id)
        ps.setString(4, buyerText);
        ps.setBigDecimal(5, java.math.BigDecimal.valueOf(qty));
        ps.setBigDecimal(6, java.math.BigDecimal.valueOf(unitPrice));
        ps.setString(7, payMethod);
        ps.setString(8, payStatus);
        ps.setString(9, remarks);

        ps.executeUpdate();
        return true;
    } catch (java.sql.SQLException e) {
        // If DB oversell trigger fires, message will contain our text
        if (e.getMessage() != null 
                && e.getMessage().toLowerCase().contains("quantity exceeds remaining stock")) {
            System.out.println("DB rejected oversell: " + e.getMessage());
            return false;
        }
        e.printStackTrace();
        return false;
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}



    // update while keeping same species/catch linkage
    public static boolean updateTransactionKeepBuyer(int txnId, String buyer,
                                                     double qty, double unitPrice,
                                                     String payMethod, String payStatus, String remarks) {
        String sql = """
            UPDATE transactions
               SET buyer_name=?, quantity_sold=?, unit_price=?,
                   payment_method=?, payment_status=?, remarks=?
             WHERE transaction_id=?
            """;
        try (var c = ConnectDb(); var ps = c.prepareStatement(sql)) {
            ps.setString(1, buyer);
            ps.setBigDecimal(2, BigDecimal.valueOf(qty));
            ps.setBigDecimal(3, BigDecimal.valueOf(unitPrice));
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
    
    //transaction (on selecting consumer/buyer) load consumer
    public static javafx.collections.ObservableList<ConsumerItem> loadActiveConsumers() {
        var list = javafx.collections.FXCollections.<ConsumerItem>observableArrayList();
        String sql = "SELECT consumer_id, name, contact FROM consumers WHERE is_active=1 ORDER BY name";
        try (var c = ConnectDb(); var ps = c.prepareStatement(sql); var rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new ConsumerItem(
                    rs.getInt("consumer_id"),
                    rs.getString("name"),
                    rs.getString("contact")
                ));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
    
      //on fish type options on the sales (fish type combo box)
    public static ObservableList<InventoryOption> loadInventoryOptionsAvailable() {
        var list = FXCollections.<InventoryOption>observableArrayList();
        String sql = """
            SELECT species_id, species_name, balance_qty, selling_price
            FROM v_inventory
            WHERE balance_qty > 0
            ORDER BY species_name
            """;
        try (var c = ConnectDb(); var ps = c.prepareStatement(sql); var rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new InventoryOption(
                    rs.getInt("species_id"),
                    rs.getString("species_name"),
                    rs.getDouble("balance_qty"),
                    rs.getDouble("selling_price")
                ));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
   
   //Oversell check (species-based)
   public static double speciesPurchasedQty(int speciesId) {
        String sql = "SELECT IFNULL(SUM(quantity),0) FROM catch WHERE species_id=?";
        try (var c = ConnectDb(); var ps = c.prepareStatement(sql)) {
            ps.setInt(1, speciesId);
            try (var rs = ps.executeQuery()) { if (rs.next()) return rs.getDouble(1); }
        } catch (Exception e) { e.printStackTrace(); }
        return 0.0;
    }
    public static double speciesSoldQtyExcluding(int speciesId, Integer excludeTxnId) {
        String sql = (excludeTxnId == null)
            ? "SELECT IFNULL(SUM(quantity_sold),0) FROM transactions WHERE species_id=?"
            : "SELECT IFNULL(SUM(quantity_sold),0) FROM transactions WHERE species_id=? AND transaction_id<>?";
        try (var c = ConnectDb(); var ps = c.prepareStatement(sql)) {
            ps.setInt(1, speciesId);
            if (excludeTxnId != null) ps.setInt(2, excludeTxnId);
            try (var rs = ps.executeQuery()) { if (rs.next()) return rs.getDouble(1); }
        } catch (Exception e) { e.printStackTrace(); }
        return 0.0;
    }




    //DOCKING LOGS
    // Load logs with fisher name + boat (for the table)
    public static javafx.collections.ObservableList<DockLogViewRow> loadDockLogsView() {
        var list = javafx.collections.FXCollections.<DockLogViewRow>observableArrayList();
        String sql = """
            SELECT dl.log_id, dl.fisherfolk_id, f.name AS fisher_name, f.gear,
                   dl.docking_date, dl.arrival_time, dl.departure_time, dl.remarks
            FROM docking_logs dl
            JOIN fisherfolk f ON dl.fisherfolk_id = f.fisherfolk_id
            ORDER BY dl.docking_date DESC, dl.arrival_time ASC
            """;
        try (java.sql.Connection c = ConnectDb();
             java.sql.PreparedStatement ps = c.prepareStatement(sql);
             java.sql.ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new DockLogViewRow(
                    rs.getInt("log_id"),
                    rs.getInt("fisherfolk_id"),
                    rs.getString("fisher_name"),
                    rs.getString("gear"),
//                    rs.getString("boat_name"),
                    rs.getDate("docking_date").toLocalDate(),
                    rs.getTime("arrival_time").toLocalTime(),
                    rs.getTime("departure_time") == null ? null : rs.getTime("departure_time").toLocalTime(),
                    rs.getString("remarks")
                ));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    // Delete one log
    public static boolean deleteDockLogById(int logId) {
        String sql = "DELETE FROM docking_logs WHERE log_id=?";
        try (java.sql.Connection c = ConnectDb();
             java.sql.PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, logId);
            return ps.executeUpdate() == 1;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    // Count dockings today
    public static int countDockingsToday() {
        String sql = "SELECT COUNT(*) FROM docking_logs WHERE docking_date = CURDATE()";
        try (var c = ConnectDb(); var ps = c.prepareStatement(sql); var rs = ps.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }

    // Most active fisher in last 7 days (name + count)
    public static String mostActiveFisherLast7Days() {
        String sql = """
            SELECT f.name, COUNT(*) AS cnt
            FROM docking_logs dl
            JOIN fisherfolk f ON f.fisherfolk_id = dl.fisherfolk_id
            WHERE dl.docking_date >= (CURDATE() - INTERVAL 7 DAY)
            GROUP BY dl.fisherfolk_id
            ORDER BY cnt DESC, f.name ASC
            LIMIT 1
            """;
        try (var c = ConnectDb(); var ps = c.prepareStatement(sql); var rs = ps.executeQuery()) {
            if (rs.next()) return rs.getString(1) + " (" + rs.getInt(2) + ")";
        } catch (Exception e) { e.printStackTrace(); }
        return "—";
    }

    // add/updating dock logs
    public static boolean insertDockLog(int fisherId, java.time.LocalDate date,
                                        java.time.LocalTime arrival, java.time.LocalTime departure,
                                        String remarks) {
        String sql = "INSERT INTO docking_logs (fisherfolk_id, docking_date, arrival_time, departure_time, remarks) " +
                     "VALUES (?,?,?,?,?)";
        try (java.sql.Connection c = ConnectDb();
             java.sql.PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, fisherId);
            ps.setDate(2, java.sql.Date.valueOf(date));
            ps.setTime(3, java.sql.Time.valueOf(arrival));
            if (departure == null) ps.setNull(4, java.sql.Types.TIME);
            else ps.setTime(4, java.sql.Time.valueOf(departure));
            if (remarks == null || remarks.isBlank()) ps.setNull(5, java.sql.Types.VARCHAR);
            else ps.setString(5, remarks);
            return ps.executeUpdate() == 1;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    public static boolean updateDockLogDeparture(int logId, java.time.LocalTime departure, String remarks) {
        String sql = "UPDATE docking_logs SET departure_time = ?, remarks = ? WHERE log_id = ?";
        try (java.sql.Connection c = ConnectDb();
             java.sql.PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setTime(1, java.sql.Time.valueOf(departure));
            if (remarks == null || remarks.isBlank()) ps.setNull(2, java.sql.Types.VARCHAR);
            else ps.setString(2, remarks);
            ps.setInt(3, logId);
            return ps.executeUpdate() == 1;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }
    //REPORTS & ANALYTICS
    //SALES
    // --- simple pair ---
    public static final class PeriodRevenue {
        public final String label;   // e.g. "2025-09-20", "2025-W38", "2025-09"
        public final double revenue;
        public PeriodRevenue(String label, double revenue) { this.label = label; this.revenue = revenue; }
    }

    // Common WHERE clause builder
    private static String paidAndRangeWhere(boolean withRange) {
        return " WHERE payment_status='Paid' " + (withRange ? " AND transaction_date BETWEEN ? AND ? " : " ");
    }

    public static java.util.List<PeriodRevenue> salesDaily(java.time.LocalDate start, java.time.LocalDate end) {
        var list = new java.util.ArrayList<PeriodRevenue>();
        boolean bounded = (start != null && end != null);
        String sql = "SELECT DATE(transaction_date) d, SUM(total_price) rev " +
                     "FROM transactions" + paidAndRangeWhere(bounded) +
                     "GROUP BY d ORDER BY d";
        try (var c = ConnectDb(); var ps = c.prepareStatement(sql)) {
            if (bounded) {
                ps.setTimestamp(1, java.sql.Timestamp.valueOf(start.atStartOfDay()));
                ps.setTimestamp(2, java.sql.Timestamp.valueOf(end.plusDays(1).atStartOfDay().minusSeconds(1)));
            }
            try (var rs = ps.executeQuery()) {
                while (rs.next()) list.add(new PeriodRevenue(rs.getString("d"), rs.getDouble("rev")));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public static java.util.List<PeriodRevenue> salesWeekly(java.time.LocalDate start, java.time.LocalDate end) {
        var list = new java.util.ArrayList<PeriodRevenue>();
        boolean bounded = (start != null && end != null);
        String sql = "SELECT YEARWEEK(transaction_date, 3) yw, SUM(total_price) rev " +
                     "FROM transactions" + paidAndRangeWhere(bounded) +
                     "GROUP BY yw ORDER BY yw";
        try (var c = ConnectDb(); var ps = c.prepareStatement(sql)) {
            if (bounded) {
                ps.setTimestamp(1, java.sql.Timestamp.valueOf(start.atStartOfDay()));
                ps.setTimestamp(2, java.sql.Timestamp.valueOf(end.plusDays(1).atStartOfDay().minusSeconds(1)));
            }
            try (var rs = ps.executeQuery()) {
                while (rs.next()) {
                    String label = rs.getString("yw"); // e.g. 2025xx → show as 2025-W38
                    if (label != null && label.length() >= 6)
                        label = label.substring(0,4) + "-W" + label.substring(4);
                    list.add(new PeriodRevenue(label, rs.getDouble("rev")));
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public static java.util.List<PeriodRevenue> salesMonthly(java.time.LocalDate start, java.time.LocalDate end) {
        var list = new java.util.ArrayList<PeriodRevenue>();
        boolean bounded = (start != null && end != null);
        String sql = "SELECT DATE_FORMAT(transaction_date,'%Y-%m') ym, SUM(total_price) rev " +
                     "FROM transactions" + paidAndRangeWhere(bounded) +
                     "GROUP BY ym ORDER BY ym";
        try (var c = ConnectDb(); var ps = c.prepareStatement(sql)) {
            if (bounded) {
                ps.setTimestamp(1, java.sql.Timestamp.valueOf(start.atStartOfDay()));
                ps.setTimestamp(2, java.sql.Timestamp.valueOf(end.plusDays(1).atStartOfDay().minusSeconds(1)));
            }
            try (var rs = ps.executeQuery()) {
                while (rs.next()) list.add(new PeriodRevenue(rs.getString("ym"), rs.getDouble("rev")));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    // ---- Fisherfolk Contributions (from TRANSACTIONS via catch) ----
    public static javafx.util.Pair<String, javafx.scene.chart.XYChart.Series<String, Number>>
    loadFisherfolkContrib(java.time.LocalDate start, java.time.LocalDate end, boolean paidOnly) {

            var series = new javafx.scene.chart.XYChart.Series<String, Number>();
            String label = "";

            // Core join: transactions + consumers
            String joinCore = """
                FROM transactions t
                LEFT JOIN consumers c
                       ON c.consumer_id = t.consumer_id
                """;

            // Dynamic filters (status + date range) applied on t.*
            StringBuilder filters = new StringBuilder(" WHERE 1=1 ");
            if (paidOnly) {
                filters.append(" AND t.payment_status = 'Paid' ");
            }
            boolean hasStart = (start != null);
            boolean hasEnd   = (end   != null);
            if (hasStart) filters.append(" AND t.transaction_date >= ? ");
            if (hasEnd)   filters.append(" AND t.transaction_date <  ? "); // next-day exclusive

            // 1) Label query: MIN/MAX over the filtered transaction_date range
            String labelSql = """
                SELECT
                  DATE_FORMAT(MIN(t.transaction_date),'%M %Y') AS s,
                  DATE_FORMAT(MAX(t.transaction_date),'%M %Y') AS e
                """ + joinCore + filters.toString();

            // 2) Data query: sum total_price per consumer (or buyer_name if no consumer record)
            String dataSql = """
                SELECT
                  COALESCE(c.consumer_id, 0) AS consumer_key,
                  COALESCE(c.name, t.buyer_name, 'Unknown') AS buyer_label,
                  COALESCE(SUM(t.total_price), 0) AS total_sales
                """ + joinCore + filters.toString() + """
                GROUP BY
                  COALESCE(c.consumer_id, 0),
                  COALESCE(c.name, t.buyer_name, 'Unknown')
                ORDER BY total_sales DESC
                LIMIT 5
                """;

            try (var conn = ConnectDb()) {
                // --- label
                try (var ps = conn.prepareStatement(labelSql)) {
                    int idx = 1;
                    if (hasStart) ps.setTimestamp(idx++, java.sql.Timestamp.valueOf(start.atStartOfDay()));
                    if (hasEnd)   ps.setTimestamp(idx++, java.sql.Timestamp.valueOf(end.plusDays(1).atStartOfDay()));
                    try (var rs = ps.executeQuery()) {
                        if (rs.next()) {
                            String s = rs.getString("s");
                            String e = rs.getString("e");
                            label = (s != null && e != null && !s.equals(e))
                                    ? (s + " — " + e)
                                    : (s != null ? s : "");
                        }
                    }
                }

                // --- data
                try (var ps = conn.prepareStatement(dataSql)) {
                    int idx = 1;
                    if (hasStart) ps.setTimestamp(idx++, java.sql.Timestamp.valueOf(start.atStartOfDay()));
                    if (hasEnd)   ps.setTimestamp(idx++, java.sql.Timestamp.valueOf(end.plusDays(1).atStartOfDay()));
                    try (var rs = ps.executeQuery()) {
                        while (rs.next()) {
                            String buyerLabel = rs.getString("buyer_label");
                            double totalSales = rs.getDouble("total_sales");
                            series.getData().add(
                                new javafx.scene.chart.XYChart.Data<>(buyerLabel, totalSales)
                            );
                        }
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            series.setName(paidOnly ? "Top Consumers (Paid sales)" : "Top Consumers (All sales)");
            return new javafx.util.Pair<>(label, series);


    }


    
    // ---- Species Distribution (from CATCH) ----
    public static javafx.util.Pair<String, javafx.collections.ObservableList<javafx.scene.chart.PieChart.Data>>
    loadSpeciesDistribution(java.time.LocalDate start, java.time.LocalDate end) {

        var data = javafx.collections.FXCollections.<javafx.scene.chart.PieChart.Data>observableArrayList();
        String label = "";

        // ---- Species Sales Distribution (from TRANSACTIONS) ----
        String labelSql = """
            SELECT
              DATE_FORMAT(MIN(t.transaction_date),'%M %Y') AS s,
              DATE_FORMAT(MAX(t.transaction_date),'%M %Y') AS e
            FROM transactions t
            JOIN species s ON s.species_id = t.species_id
            WHERE t.transaction_date BETWEEN ? AND ?
              AND t.payment_status = 'Paid'
        """;

        String dataSql = """
            SELECT
              s.species_name,
              SUM(t.quantity_sold) AS total_qty
            FROM transactions t
            JOIN species s ON s.species_id = t.species_id
            WHERE t.transaction_date BETWEEN ? AND ?
              AND t.payment_status = 'Paid'
            GROUP BY s.species_id, s.species_name
            ORDER BY total_qty DESC
        """;


        try (var conn = ConnectDb()) {
            try (var ps = conn.prepareStatement(labelSql)) {
                ps.setDate(1, java.sql.Date.valueOf(start));
                ps.setDate(2, java.sql.Date.valueOf(end));
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        String s = rs.getString("s");
                        String e = rs.getString("e");
                        label = s != null && e != null && !s.equals(e) ? (s + " — " + e) : (s != null ? s : "");
                    }
                }
            }
            try (var ps = conn.prepareStatement(dataSql)) {
                ps.setDate(1, java.sql.Date.valueOf(start));
                ps.setDate(2, java.sql.Date.valueOf(end));
                try (var rs = ps.executeQuery()) {
                    while (rs.next()) {
                        data.add(new javafx.scene.chart.PieChart.Data(
                            rs.getString("species_name"),
                            rs.getDouble("total_qty")));
                    }
                }
            }
        } catch (Exception ex) { ex.printStackTrace(); }

        return new javafx.util.Pair<>(label, data);
    }

    // ---- Catch Volumes (STACKED by species, grouped by month) ----
    public static javafx.util.Pair<String, java.util.Map<String, java.util.List<javafx.scene.chart.XYChart.Data<String, Number>>>>
    loadCatchVolumes(java.time.LocalDate start, java.time.LocalDate end) {

        var map = new java.util.LinkedHashMap<String, java.util.List<javafx.scene.chart.XYChart.Data<String, Number>>>();
        String label = "";

        // ---- Purchase Volumes (STACKED by species, grouped by month) ----
        String labelSql = """
            SELECT
              DATE_FORMAT(MIN(c.catch_date),'%M %Y') AS s,
              DATE_FORMAT(MAX(c.catch_date),'%M %Y') AS e
            FROM catch c
            WHERE c.catch_date BETWEEN ? AND ?
        """;

        String dataSql = """
            SELECT
              DATE_FORMAT(c.catch_date,'%Y-%m') AS ym,
              s.species_name,
              SUM(c.quantity) AS total_qty
            FROM catch c
            JOIN species s ON s.species_id = c.species_id
            WHERE c.catch_date BETWEEN ? AND ?
            GROUP BY ym, s.species_name
            ORDER BY ym, s.species_name
        """;


        try (var conn = ConnectDb()) {
            try (var ps = conn.prepareStatement(labelSql)) {
                ps.setDate(1, java.sql.Date.valueOf(start));
                ps.setDate(2, java.sql.Date.valueOf(end));
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        String s = rs.getString("s"), e = rs.getString("e");
                        label = s != null && e != null && !s.equals(e) ? (s + " — " + e) : (s != null ? s : "");
                    }
                }
            }
            try (var ps = conn.prepareStatement(dataSql)) {
                ps.setDate(1, java.sql.Date.valueOf(start));
                ps.setDate(2, java.sql.Date.valueOf(end));
                try (var rs = ps.executeQuery()) {
                    while (rs.next()) {
                        String sp = rs.getString("species_name");
                        String month = rs.getString("ym");
                        double qty = rs.getDouble("total_qty");
                        map.computeIfAbsent(sp, k -> new java.util.ArrayList<>())
                           .add(new javafx.scene.chart.XYChart.Data<>(month, qty));
                    }
                }
            }
        } catch (Exception ex) { ex.printStackTrace(); }

        return new javafx.util.Pair<>(label, map);
    }
    
    //SPECIES
    // ===== SPECIES: load all =====
    public static javafx.collections.ObservableList<SpeciesItem> loadSpecies() {
        var list = javafx.collections.FXCollections.<SpeciesItem>observableArrayList();
        String sql = "SELECT species_id, species_name, description FROM species ORDER BY species_name";
        try (java.sql.Connection c = ConnectDb();
             java.sql.PreparedStatement ps = c.prepareStatement(sql);
             java.sql.ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new SpeciesItem(
                    rs.getInt("species_id"),
                    rs.getString("species_name"),
                    rs.getString("description")
                ));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    // ===== SPECIES: insert =====
    public static boolean insertSpecies(String name, String desc) {
        String sql = "INSERT INTO species (species_name, description) VALUES (?, ?)";
        try (java.sql.Connection c = ConnectDb();
             java.sql.PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, name);
            if (desc == null || desc.isBlank()) ps.setNull(2, java.sql.Types.VARCHAR);
            else ps.setString(2, desc);
            return ps.executeUpdate() == 1;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    // ===== SPECIES: update =====
    public static boolean updateSpecies(int id, String name, String desc) {
        String sql = "UPDATE species SET species_name=?, description=? WHERE species_id=?";
        try (java.sql.Connection c = ConnectDb();
             java.sql.PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, name);
            if (desc == null || desc.isBlank()) ps.setNull(2, java.sql.Types.VARCHAR);
            else ps.setString(2, desc);
            ps.setInt(3, id);
            return ps.executeUpdate() == 1;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    // ===== SPECIES: delete (blocked if used by catch) =====
    public static boolean deleteSpecies(int id) {
        // hard block if referenced by catch
        String chk = "SELECT COUNT(*) FROM catch WHERE species_id=?";
        String del = "DELETE FROM species WHERE species_id=?";
        try (java.sql.Connection c = ConnectDb()) {
            try (java.sql.PreparedStatement ps = c.prepareStatement(chk)) {
                ps.setInt(1, id);
                try (java.sql.ResultSet rs = ps.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        return false; // caller will show message explaining why
                    }
                }
            }
            try (java.sql.PreparedStatement ps = c.prepareStatement(del)) {
                ps.setInt(1, id);
                return ps.executeUpdate() == 1;
            }
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    //ACCOUNT PROFILE
    public static UserAccount loadUserById(int userId) {
        String sql = "SELECT user_id, username, role, name, contact_number, created_at, photo FROM users WHERE user_id=?";
        try (var c = ConnectDb(); var ps = c.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (var rs = ps.executeQuery()) {
                if (rs.next()) {
                    byte[] pic = rs.getBytes("photo");
                    return new UserAccount(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("role"),
                        rs.getString("name"),
                        rs.getString("contact_number"),
                        rs.getTimestamp("created_at"),
                        pic
                    );
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    public static boolean updateUserPersonal(int userId, String name, String contact, String role) {
        String sql = "UPDATE users SET name=?, contact_number=?, role=? WHERE user_id=?";
        try (var c = ConnectDb(); var ps = c.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, contact);
            ps.setString(3, role);
            ps.setInt(4, userId);
            return ps.executeUpdate() == 1;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    public static boolean updateUserPhoto(int userId, byte[] photoBytes) {
        String sql = "UPDATE users SET photo=? WHERE user_id=?";
        try (var c = ConnectDb(); var ps = c.prepareStatement(sql)) {
            if (photoBytes == null) ps.setNull(1, java.sql.Types.BLOB);
            else ps.setBytes(1, photoBytes);
            ps.setInt(2, userId);
            return ps.executeUpdate() == 1;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    public static boolean verifyCurrentUsername(int userId, String currentUsername) {
        String sql = "SELECT 1 FROM users WHERE user_id=? AND username=?";
        try (var c = ConnectDb(); var ps = c.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setString(2, currentUsername);
            try (var rs = ps.executeQuery()) { return rs.next(); }
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    public static boolean verifyCurrentPassword(int userId, String currentPassword) {
        // NOTE: if you hash passwords, check the hash here instead.
        String sql = "SELECT 1 FROM users WHERE user_id=? AND password=?";
        try (var c = ConnectDb(); var ps = c.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setString(2, currentPassword);
            try (var rs = ps.executeQuery()) { return rs.next(); }
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    public static boolean updateUsername(int userId, String newUsername) {
        String sql = "UPDATE users SET username=? WHERE user_id=?";
        try (var c = ConnectDb(); var ps = c.prepareStatement(sql)) {
            ps.setString(1, newUsername); ps.setInt(2, userId);
            return ps.executeUpdate() == 1;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    public static boolean updatePassword(int userId, String newPassword) {
        // Hash here if you use hashing.
        String sql = "UPDATE users SET password=? WHERE user_id=?";
        try (var c = ConnectDb(); var ps = c.prepareStatement(sql)) {
            ps.setString(1, newPassword); ps.setInt(2, userId);
            return ps.executeUpdate() == 1;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }


    // ---------- CONSUMERS in mysqlconnect.java ----------

    public static javafx.collections.ObservableList<ConsumerRecord> loadConsumers() {
        var list = javafx.collections.FXCollections.<ConsumerRecord>observableArrayList();
        String sql = "SELECT consumer_id, name, contact, address, is_active FROM consumers ORDER BY name ASC";
        try (var conn = mysqlconnect.ConnectDb();
             var ps = conn.prepareStatement(sql);
             var rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new ConsumerRecord(
                    rs.getInt("consumer_id"),
                    rs.getString("name"),
                    rs.getString("contact"),
                    rs.getString("address"),
                    rs.getBoolean("is_active")
                ));
            }
        } catch (Exception ex) { ex.printStackTrace(); }
        return list;
    }

    public static boolean insertConsumer(String name, String contact, String address) {
        String sql = "INSERT INTO consumers (name, contact, address, is_active) VALUES (?, ?, ?, 1)";
        try (var conn = mysqlconnect.ConnectDb();
             var ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            if (contact != null) ps.setString(2, contact); else ps.setNull(2, java.sql.Types.VARCHAR);
            if (address != null) ps.setString(3, address); else ps.setNull(3, java.sql.Types.VARCHAR);
            return ps.executeUpdate() > 0;
        } catch (Exception ex) { ex.printStackTrace(); return false; }
    }

    public static boolean updateConsumer(int id, String name, String contact, String address) {
        String sql = "UPDATE consumers SET name=?, contact=?, address=? WHERE consumer_id=?";
        try (var conn = mysqlconnect.ConnectDb();
             var ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            if (contact != null) ps.setString(2, contact); else ps.setNull(2, java.sql.Types.VARCHAR);
            if (address != null) ps.setString(3, address); else ps.setNull(3, java.sql.Types.VARCHAR);
            ps.setInt(4, id);
            return ps.executeUpdate() > 0;
        } catch (Exception ex) { ex.printStackTrace(); return false; }
    }

    public static boolean deleteConsumerById(int id) {
        String sql = "DELETE FROM consumers WHERE consumer_id=?";
        try (var conn = mysqlconnect.ConnectDb();
             var ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception ex) { ex.printStackTrace(); return false; }
    }

    public static boolean updateConsumerActive(int id, boolean active) {
        String sql = "UPDATE consumers SET is_active=? WHERE consumer_id=?";
        try (var conn = mysqlconnect.ConnectDb();
             var ps = conn.prepareStatement(sql)) {
            ps.setBoolean(1, active);
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        } catch (Exception ex) { ex.printStackTrace(); return false; }
    }

    /** If transactions has consumer_id FK (recommended), we use that.
     *  If not yet migrated, we fallback to buyer_name match. */
//    public static javafx.collections.ObservableList<TransactionRecord> getTransactionsByConsumer(int consumerId) {
//        var list = javafx.collections.FXCollections.<TransactionRecord>observableArrayList();
//
//        String sqlFk = """
//            SELECT t.transaction_id, t.transaction_date, t.catch_id,
//                   t.quantity_sold, t.unit_price, t.total_price,
//                   t.payment_method, t.payment_status, t.remarks
//            FROM transactions t
//            WHERE t.consumer_id = ?
//            ORDER BY t.transaction_date DESC
//        """;
//
//        String sqlFallback = """
//            SELECT t.transaction_id, t.transaction_date, t.catch_id,
//                   t.quantity_sold, t.unit_price, t.total_price,
//                   t.payment_method, t.payment_status, t.remarks
//            FROM transactions t
//            JOIN consumers c ON c.name = t.buyer_name
//            WHERE c.consumer_id = ?
//            ORDER BY t.transaction_date DESC
//        """;
//
//        String sqlToUse = sqlFk;
//        try (var conn = mysqlconnect.ConnectDb()) {
//            // probe if consumer_id exists on transactions
//            try (var chk = conn.getMetaData().getColumns(null, null, "transactions", "consumer_id")) {
//                if (!chk.next()) sqlToUse = sqlFallback;
//            }
//            try (var ps = conn.prepareStatement(sqlToUse)) {
//                ps.setInt(1, consumerId);
//                try (var rs = ps.executeQuery()) {
//                    while (rs.next()) {
//                        list.add(new TransactionRecord(
//                            rs.getInt("transaction_id"),
//                            rs.getTimestamp("transaction_date").toLocalDateTime(),
//                            rs.getInt("catch_id"),
//                            rs.getBigDecimal("quantity_sold").doubleValue(),
//                            rs.getBigDecimal("unit_price").doubleValue(),
//                            rs.getBigDecimal("total_price").doubleValue(),
//                            rs.getString("payment_method"),
//                            rs.getString("payment_status"),
//                            rs.getString("remarks")
//                        ));
//                    }
//                }
//            }
//        } catch (Exception ex) { ex.printStackTrace(); }
//        return list;
//    }
//
 
    /** Count dependencies to protect deletes. Uses consumer_id if present, else buyer_name link. */
    public static int countTransactionsByConsumer(int consumerId) {
        String sqlFk = "SELECT COUNT(*) FROM transactions WHERE consumer_id = ?";
        String sqlFallback = "SELECT COUNT(*) FROM transactions t JOIN consumers c ON c.name = t.buyer_name WHERE c.consumer_id = ?";

        try (var conn = mysqlconnect.ConnectDb()) {
            String sql = sqlFk;
            try (var chk = conn.getMetaData().getColumns(null, null, "transactions", "consumer_id")) {
                if (!chk.next()) sql = sqlFallback;
            }
            try (var ps = conn.prepareStatement(sql)) {
                ps.setInt(1, consumerId);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) return rs.getInt(1);
                }
            }
        } catch (Exception ex) { ex.printStackTrace(); }
        return -1; // error
    }
    
    public static ObservableList<TransactionViewRow> getTransactionsByConsumer(int consumerId) {
        var list = FXCollections.<TransactionViewRow>observableArrayList();

        String sql = """
            SELECT t.transaction_id,
                   t.consumer_id,
                   t.species_id,
                   t.buyer_name,
                   csm.name AS consumer_name,
                   sp.species_name,
                   t.quantity_sold,
                   t.unit_price,
                   t.total_price,
                   t.payment_method,
                   t.remarks,
                   t.payment_status,
                   t.transaction_date
            FROM transactions t
            LEFT JOIN consumers csm ON csm.consumer_id = t.consumer_id
            JOIN species sp         ON sp.species_id   = t.species_id
            WHERE t.consumer_id = ?
            ORDER BY t.transaction_date DESC
            """;

        try (var conn = ConnectDb();
             var ps   = conn.prepareStatement(sql)) {

            ps.setInt(1, consumerId);

            try (var rs = ps.executeQuery()) {
                while (rs.next()) {
                    var ts = rs.getTimestamp("transaction_date");
                    list.add(new TransactionViewRow(
                            rs.getInt("transaction_id"),
                            rs.getInt("consumer_id"),
                            rs.getInt("species_id"),
                            rs.getString("buyer_name"),
                            rs.getString("consumer_name"),
                            rs.getString("species_name"),
                            rs.getDouble("quantity_sold"),
                            rs.getDouble("unit_price"),
                            rs.getDouble("total_price"),
                            rs.getString("payment_method"),
                            rs.getString("remarks"),
                            rs.getString("payment_status"),
                            ts != null ? ts.toLocalDateTime() : null
                    ));
                }
            }
        } catch (Exception e) { e.printStackTrace(); }

        return list;
    }

    
    ////// INVENTORY
    public static javafx.collections.ObservableList<InventoryRow> loadInventoryView() {
        var list = javafx.collections.FXCollections.<InventoryRow>observableArrayList();
        String sql = """
    SELECT species_id,
           species_name,
           purchased_qty,
           sold_qty,
           balance_qty,
           selling_price,
           last_purchase_price,
           avg_purchase_price,
           updated_at
    FROM v_inventory
    ORDER BY species_name
""";

        try (var c = ConnectDb(); var ps = c.prepareStatement(sql); var rs = ps.executeQuery()) {
            while (rs.next()) {
                java.sql.Timestamp ts = rs.getTimestamp("updated_at");
                list.add(new InventoryRow(
                    rs.getInt("species_id"),
                    rs.getString("species_name"),
                    rs.getDouble("purchased_qty"),
                    rs.getDouble("sold_qty"),
                    rs.getDouble("balance_qty"),
                    rs.getDouble("last_purchase_price"),
                    rs.getDouble("avg_purchase_price"),
                    rs.getDouble("selling_price"),
                    ts != null ? ts.toLocalDateTime() : null
                ));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }


    public static boolean updateInventoryKnobs(int speciesId, double sellingPrice, double restockThreshold) {
        String sql = "INSERT INTO inventory (species_id, selling_price, restock_threshold) " +
                     "VALUES (?,?,?) " +
                     "ON DUPLICATE KEY UPDATE selling_price=VALUES(selling_price), restock_threshold=VALUES(restock_threshold)";
        try (var c = ConnectDb(); var ps = c.prepareStatement(sql)) {
            ps.setInt(1, speciesId);
            ps.setBigDecimal(2, java.math.BigDecimal.valueOf(sellingPrice));
            ps.setBigDecimal(3, java.math.BigDecimal.valueOf(restockThreshold));
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    
    // Upsert selling price (edit in UI)
    public static boolean upsertInventorySellingPrice(
            int speciesId,
            double sellingPrice,
            Double lastPurchasePriceOrNull,
            Double avgPurchasePriceOrNull,
            Double restockThresholdOrNull
    ) {
        String sql = """
            INSERT INTO inventory (species_id, selling_price, last_purchase_price, avg_purchase_price, restock_threshold, updated_at)
            VALUES (?,?,?,?,?, CURRENT_TIMESTAMP)
            ON DUPLICATE KEY UPDATE
                selling_price = VALUES(selling_price),
                last_purchase_price = VALUES(last_purchase_price),
                avg_purchase_price = VALUES(avg_purchase_price),
                restock_threshold = VALUES(restock_threshold),
                updated_at = CURRENT_TIMESTAMP
            """;
        try (var c = ConnectDb(); var ps = c.prepareStatement(sql)) {
            ps.setInt(1, speciesId);
            ps.setBigDecimal(2, java.math.BigDecimal.valueOf(sellingPrice));
            if (lastPurchasePriceOrNull == null) ps.setNull(3, java.sql.Types.DECIMAL);
            else ps.setBigDecimal(3, java.math.BigDecimal.valueOf(lastPurchasePriceOrNull));
            if (avgPurchasePriceOrNull == null) ps.setNull(4, java.sql.Types.DECIMAL);
            else ps.setBigDecimal(4, java.math.BigDecimal.valueOf(avgPurchasePriceOrNull));
            if (restockThresholdOrNull == null) ps.setNull(5, java.sql.Types.DECIMAL);
            else ps.setBigDecimal(5, java.math.BigDecimal.valueOf(restockThresholdOrNull));
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // additional profit tab on reports & analytics module
    public static ProfitSummary loadProfitSummary(LocalDate start, LocalDate end) {
    if (start == null || end == null) {
        throw new IllegalArgumentException("start/end cannot be null");
    }
    LocalDate endExclusive = end.plusDays(1);

    double grossSales = 0.0;
    double purchaseCost = 0.0;

    // For chart
    var monthMap = new java.util.LinkedHashMap<String, double[]>(); 
    // key = "YYYY-MM", value[0] = gross, value[1] = cost

    String grossSqlTotal = """
        SELECT COALESCE(SUM(total_price),0)
        FROM transactions
        WHERE payment_status='Paid'
          AND transaction_date >= ?
          AND transaction_date <  ?
    """;

    String costSqlTotal = """
        SELECT COALESCE(SUM(quantity * price_per_kilo),0)
        FROM catch
        WHERE catch_date >= ?
          AND catch_date <  ?
    """;

    String grossPerMonthSql = """
        SELECT DATE_FORMAT(transaction_date,'%Y-%m') ym,
               COALESCE(SUM(total_price),0) total_gross
        FROM transactions
        WHERE payment_status='Paid'
          AND transaction_date >= ?
          AND transaction_date <  ?
        GROUP BY ym
        ORDER BY ym
    """;

    String costPerMonthSql = """
        SELECT DATE_FORMAT(catch_date,'%Y-%m') ym,
               COALESCE(SUM(quantity * price_per_kilo),0) total_cost
        FROM catch
        WHERE catch_date >= ?
          AND catch_date <  ?
        GROUP BY ym
        ORDER BY ym
    """;

    try (var conn = ConnectDb()) {
        // ---- totals ----
        try (var ps = conn.prepareStatement(grossSqlTotal)) {
            ps.setTimestamp(1, java.sql.Timestamp.valueOf(start.atStartOfDay()));
            ps.setTimestamp(2, java.sql.Timestamp.valueOf(endExclusive.atStartOfDay()));
            try (var rs = ps.executeQuery()) {
                if (rs.next()) grossSales = rs.getDouble(1);
            }
        }

        try (var ps = conn.prepareStatement(costSqlTotal)) {
            ps.setDate(1, java.sql.Date.valueOf(start));
            ps.setDate(2, java.sql.Date.valueOf(endExclusive));
            try (var rs = ps.executeQuery()) {
                if (rs.next()) purchaseCost = rs.getDouble(1);
            }
        }

        // ---- per month: gross ----
        try (var ps = conn.prepareStatement(grossPerMonthSql)) {
            ps.setTimestamp(1, java.sql.Timestamp.valueOf(start.atStartOfDay()));
            ps.setTimestamp(2, java.sql.Timestamp.valueOf(endExclusive.atStartOfDay()));
            try (var rs = ps.executeQuery()) {
                while (rs.next()) {
                    String ym = rs.getString("ym");
                    double val = rs.getDouble("total_gross");
                    monthMap.computeIfAbsent(ym, k -> new double[2])[0] = val;
                }
            }
        }

        // ---- per month: cost ----
        try (var ps = conn.prepareStatement(costPerMonthSql)) {
            ps.setDate(1, java.sql.Date.valueOf(start));
            ps.setDate(2, java.sql.Date.valueOf(endExclusive));
            try (var rs = ps.executeQuery()) {
                while (rs.next()) {
                    String ym = rs.getString("ym");
                    double val = rs.getDouble("total_cost");
                    monthMap.computeIfAbsent(ym, k -> new double[2])[1] = val;
                }
            }
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    // Build chart series
    var grossSeries = new javafx.scene.chart.XYChart.Series<String, Number>();
    grossSeries.setName("Gross Sales");

    var costSeries = new javafx.scene.chart.XYChart.Series<String, Number>();
    costSeries.setName("Purchase Cost");

    for (var entry : monthMap.entrySet()) {
        String ym = entry.getKey();         // "2025-01"
        double[] vals = entry.getValue();   // [gross, cost]
        grossSeries.getData().add(new javafx.scene.chart.XYChart.Data<>(ym, vals[0]));
        costSeries.getData().add(new javafx.scene.chart.XYChart.Data<>(ym, vals[1]));
    }

    return new ProfitSummary(grossSales, purchaseCost, grossSeries, costSeries);
}



}