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
    // ALSO USED ON DOCKING LOGS (for getting boatname)
    public static javafx.collections.ObservableList<FisherfolkItem> loadActiveFisherfolkItems() {
        var list = javafx.collections.FXCollections.<FisherfolkItem>observableArrayList();
        String sql = "SELECT fisherfolk_id, name, boat_name FROM fisherfolk WHERE is_active=1 ORDER BY name";
        try (java.sql.Connection c = ConnectDb();
             java.sql.PreparedStatement ps = c.prepareStatement(sql);
             java.sql.ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new FisherfolkItem(
                    rs.getInt("fisherfolk_id"),
                    rs.getString("name"),
                    rs.getString("boat_name")
                ));
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

    //DOCKING LOGS
    // Load logs with fisher name + boat (for the table)
    public static javafx.collections.ObservableList<DockLogViewRow> loadDockLogsView() {
        var list = javafx.collections.FXCollections.<DockLogViewRow>observableArrayList();
        String sql = """
            SELECT dl.log_id, dl.fisherfolk_id, f.name AS fisher_name, f.boat_name,
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
                    rs.getString("boat_name"),
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

    //fisherfolk contributions
//    public static java.util.List<javafx.scene.chart.XYChart.Data<String, Number>> loadFisherfolkContributions() {
//        var list = new java.util.ArrayList<javafx.scene.chart.XYChart.Data<String, Number>>();
//        String sql = "SELECT f.name, SUM(t.quantity_sold) AS qty_sold " +
//                     "FROM transactions t JOIN fisherfolk f ON t.fisherfolk_id=f.fisherfolk_id " +
//                     "WHERE t.payment_status='Paid' " +
//                     "GROUP BY f.fisherfolk_id, f.name ORDER BY qty_sold DESC LIMIT 10";
//        try (var c = ConnectDb(); var ps = c.prepareStatement(sql); var rs = ps.executeQuery()) {
//            while (rs.next()) {
//                list.add(new javafx.scene.chart.XYChart.Data<>(rs.getString("name"), rs.getDouble("qty_sold")));
//            }
//        } catch (Exception e) { e.printStackTrace(); }
//        return list;
//    }
    // ---- Fisherfolk Contributions (from TRANSACTIONS) ----
    public static javafx.util.Pair<String, javafx.scene.chart.XYChart.Series<String, Number>>
    loadFisherfolkContrib(java.time.LocalDate start, java.time.LocalDate end, boolean paidOnly) {

        var series = new javafx.scene.chart.XYChart.Series<String, Number>();
        String label = "";

        String labelSql = """
            SELECT DATE_FORMAT(MIN(t.transaction_date),'%M %Y') AS s,
                   DATE_FORMAT(MAX(t.transaction_date),'%M %Y') AS e
            FROM transactions t
            WHERE t.transaction_date BETWEEN ? AND ?
              """ + (paidOnly ? "AND t.payment_status='Paid'" : "") + """
        """;

        String dataSql = """
            SELECT f.name, SUM(t.quantity_sold) AS qty
            FROM transactions t
            JOIN fisherfolk f ON f.fisherfolk_id = t.fisherfolk_id
            WHERE t.transaction_date BETWEEN ? AND ?
              """ + (paidOnly ? "AND t.payment_status='Paid'" : "") + """
            GROUP BY f.fisherfolk_id, f.name
            ORDER BY qty DESC
            LIMIT 12
        """;

        try (var conn = ConnectDb()) {
            try (var ps = conn.prepareStatement(labelSql)) {
                ps.setTimestamp(1, java.sql.Timestamp.valueOf(start.atStartOfDay()));
                ps.setTimestamp(2, java.sql.Timestamp.valueOf(end.plusDays(1).atStartOfDay().minusSeconds(1)));
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        String s = rs.getString("s"), e = rs.getString("e");
                        label = s != null && e != null && !s.equals(e) ? (s + " — " + e) : (s != null ? s : "");
                    }
                }
            }
            try (var ps = conn.prepareStatement(dataSql)) {
                ps.setTimestamp(1, java.sql.Timestamp.valueOf(start.atStartOfDay()));
                ps.setTimestamp(2, java.sql.Timestamp.valueOf(end.plusDays(1).atStartOfDay().minusSeconds(1)));
                try (var rs = ps.executeQuery()) {
                    while (rs.next()) {
                        series.getData().add(new javafx.scene.chart.XYChart.Data<>(
                            rs.getString("name"), rs.getDouble("qty")));
                    }
                }
            }
        } catch (Exception ex) { ex.printStackTrace(); }

        series.setName(paidOnly ? "Top Fisherfolk (Paid sales)" : "Top Fisherfolk (All sales)");
        return new javafx.util.Pair<>(label, series);
    }

    
    //species distribution
//    public static java.util.List<javafx.scene.chart.PieChart.Data> loadSpeciesDistribution() {
//        var list = new java.util.ArrayList<javafx.scene.chart.PieChart.Data>();
//        String sql = "SELECT s.species_name, SUM(c.quantity) AS total_qty " +
//                     "FROM catch c JOIN species s ON c.species_id=s.species_id " +
//                     "GROUP BY s.species_id, s.species_name";
//        try (var c = ConnectDb(); var ps = c.prepareStatement(sql); var rs = ps.executeQuery()) {
//            while (rs.next()) {
//                list.add(new javafx.scene.chart.PieChart.Data(rs.getString("species_name"), rs.getDouble("total_qty")));
//            }
//        } catch (Exception e) { e.printStackTrace(); }
//        return list;
//    }
    // ---- Species Distribution (from CATCH) ----
    public static javafx.util.Pair<String, javafx.collections.ObservableList<javafx.scene.chart.PieChart.Data>>
    loadSpeciesDistribution(java.time.LocalDate start, java.time.LocalDate end) {

        var data = javafx.collections.FXCollections.<javafx.scene.chart.PieChart.Data>observableArrayList();
        String label = "";

        String labelSql = """
            SELECT DATE_FORMAT(MIN(c.catch_date),'%M %Y') AS s,
                   DATE_FORMAT(MAX(c.catch_date),'%M %Y') AS e
            FROM catch c
            WHERE c.catch_date BETWEEN ? AND ?
        """;
        String dataSql = """
            SELECT s.species_name, SUM(c.quantity) AS total_qty
            FROM catch c
            JOIN species s ON s.species_id = c.species_id
            WHERE c.catch_date BETWEEN ? AND ?
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

    //catch volume
//    public static java.util.Map<String, java.util.List<javafx.scene.chart.XYChart.Data<String, Number>>> loadCatchVolumes() {
//        // Map<species, List<Data<month, qty>>>
//        var map = new java.util.LinkedHashMap<String, java.util.List<javafx.scene.chart.XYChart.Data<String, Number>>>();
//        String sql = "SELECT DATE_FORMAT(c.catch_date,'%Y-%m') ym, s.species_name, SUM(c.quantity) total_qty " +
//                     "FROM catch c JOIN species s ON c.species_id=s.species_id " +
//                     "GROUP BY ym, s.species_name ORDER BY ym, s.species_name";
//        try (var c = ConnectDb(); var ps = c.prepareStatement(sql); var rs = ps.executeQuery()) {
//            while (rs.next()) {
//                String species = rs.getString("species_name");
//                String month = rs.getString("ym");
//                double qty = rs.getDouble("total_qty");
//                map.computeIfAbsent(species, k -> new java.util.ArrayList<>())
//                   .add(new javafx.scene.chart.XYChart.Data<>(month, qty));
//            }
//        } catch (Exception e) { e.printStackTrace(); }
//        return map;
//    }
    // ---- Catch Volumes (STACKED by species, grouped by month) ----
    public static javafx.util.Pair<String, java.util.Map<String, java.util.List<javafx.scene.chart.XYChart.Data<String, Number>>>>
    loadCatchVolumes(java.time.LocalDate start, java.time.LocalDate end) {

        var map = new java.util.LinkedHashMap<String, java.util.List<javafx.scene.chart.XYChart.Data<String, Number>>>();
        String label = "";

        String labelSql = """
            SELECT DATE_FORMAT(MIN(c.catch_date),'%M %Y') AS s,
                   DATE_FORMAT(MAX(c.catch_date),'%M %Y') AS e
            FROM catch c
            WHERE c.catch_date BETWEEN ? AND ?
        """;

        String dataSql = """
            SELECT DATE_FORMAT(c.catch_date,'%Y-%m') ym,
                   s.species_name,
                   SUM(c.quantity) total_qty
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





}