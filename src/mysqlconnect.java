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


    
//    ////GET RESERVED RECORD
//    public static ObservableList<savelists> getSavelists(){
//     Connection conn = ConnectDb();
//     ObservableList<savelists> list = FXCollections.observableArrayList();
////     String try2= "21b - 6626"; //di tinatanggap pag walang space
//
//        System.out.println("mysql: "+MAINkioskController.passValue);
//     try{
//         PreparedStatement ps = conn.prepareStatement("Select * from reserved1 WHERE BORROWER_ID='"+MAINkioskController.passValue+"'");  
//         ResultSet rs = ps.executeQuery();
//       
//        while (rs.next()){
//          list.add(new savelists (Integer.parseInt(rs.getString("RESERVATION_ID")),rs.getString("BORROWER_ID"),rs.getString("BOOK_ID"),rs.getString("RESERVE_DATE"),rs.getString("EXPIRATION_DATE"),
//                  rs.getString("STATUS"),rs.getString("BORROWER_TYPE"),rs.getString("REMARKS"),rs.getString("BOOK_TITLE")));  
//        }
//      }
//     catch (Exception e){
//    } 
//     return list;
//    }
//    
//    public static ObservableList<savelists> getSavelistsADMIN(){
//     Connection conn = ConnectDb();
//     ObservableList<savelists> list = FXCollections.observableArrayList();
////     String try2= "21b - 6626"; //di tinatanggap pag walang space
//      try{
//         PreparedStatement ps = conn.prepareStatement("Select * from reserved1 ORDER BY RESERVATION_ID DESC");  
//         ResultSet rs = ps.executeQuery();
//       
//        while (rs.next()){
//          list.add(new savelists (Integer.parseInt(rs.getString("RESERVATION_ID")),rs.getString("BORROWER_ID"),rs.getString("BOOK_ID"),rs.getString("RESERVE_DATE"),rs.getString("EXPIRATION_DATE"),
//                  rs.getString("STATUS"),rs.getString("BORROWER_TYPE"),rs.getString("REMARKS"),rs.getString("BOOK_TITLE")));  
//        }
//      }
//     catch (Exception e){
//    } 
//     return list;
//    }
//    
//    
//     ////GET BORROWED RECORD
//    public static ObservableList<borrowedlists> getBorrowedlists(){
//     Connection conn = ConnectDb();
//     ObservableList<borrowedlists> list = FXCollections.observableArrayList();
//
//     try{
//         PreparedStatement ps = conn.prepareStatement("Select * from borrowed1 WHERE BORROWER_ID='"+MAINkioskController.passValue+"'");  
//         ResultSet rs = ps.executeQuery();
//       
//        while (rs.next()){
//          list.add(new borrowedlists (Integer.parseInt(rs.getString("TRANSACTION_ID")),
//                  rs.getString("BORROWER_ID"),
//                  rs.getString("BOOK_ID"),
//                  rs.getString("BORROW_TYPE"),
//                  rs.getString("BORROW_DATE"),
//                  rs.getString("DUE_DATE"),
//                  rs.getString("RETURN_DATE"),
//                  rs.getString("STATUS"),
//                  rs.getInt("PENALTY"),
//                  rs.getString("BORROWER_TYPE"),
//                  rs.getString("REMARKS"),
//                  rs.getString("BOOK_TITLE")));  
//        }
//      }
//     catch (Exception e){
//    } 
//     return list;
//    }
//    
//    ////GET BORROWED RECORD
//    public static ObservableList<borrowedlists> getBorrowedlistsADMIN(){
//     Connection conn = ConnectDb();
//     ObservableList<borrowedlists> list = FXCollections.observableArrayList();
//
//     try{
//         PreparedStatement ps = conn.prepareStatement("Select * from borrowed1 ORDER BY TRANSACTION_ID DESC");  
//         ResultSet rs = ps.executeQuery();
//       
//        while (rs.next()){
//          list.add(new borrowedlists (Integer.parseInt(rs.getString("TRANSACTION_ID")),
//                  rs.getString("BORROWER_ID"),
//                  rs.getString("BOOK_ID"),
//                  rs.getString("BORROW_TYPE"),
//                  rs.getString("BORROW_DATE"),
//                  rs.getString("DUE_DATE"),
//                  rs.getString("RETURN_DATE"),
//                  rs.getString("STATUS"),
//                  rs.getInt("PENALTY"),
//                  rs.getString("BORROWER_TYPE"),
//                  rs.getString("REMARKS"),
//                  rs.getString("BOOK_TITLE")));  
//        }
//      }
//     catch (Exception e){
//    } 
//     return list;
//    }
//    
//    
//    ////GET 
//    public static ObservableList<logrecordsuser> getlogrecordsuser(){
//     Connection conn = ConnectDb();
//     ObservableList<logrecordsuser> list = FXCollections.observableArrayList();
//
//     try{
//         PreparedStatement ps = conn.prepareStatement("Select * from logrecordsuser WHERE ID_NUMBER='"+MAINkioskController.passValue+"'");  
//         ResultSet rs = ps.executeQuery();
//       
//        while (rs.next()){
//          list.add(new logrecordsuser (rs.getString("NAME"), rs.getString("ID_NUMBER"),
//                  rs.getString("TIME_IN"),
//                  rs.getString("TIME_OUT")));  
//        }
//      }
//     catch (Exception e){
//    } 
//     return list;
//    }
//    
//     ////GET 
//    public static ObservableList<logrecordsuser> getlogrecordsuserADMIN(){
//     Connection conn = ConnectDb();
//     ObservableList<logrecordsuser> list = FXCollections.observableArrayList();
//
//     try{
//         PreparedStatement ps = conn.prepareStatement("Select * from logrecordsuser ORDER BY TIME_IN DESC");  
//         ResultSet rs = ps.executeQuery();
//       
//        while (rs.next()){
//          list.add(new logrecordsuser (rs.getString("NAME"), rs.getString("ID_NUMBER"),
//                  rs.getString("TIME_IN"),
//                  rs.getString("TIME_OUT")));  
//        }
//      }
//     catch (Exception e){
//    } 
//     return list;
//    }
//    
//     ////GET 
//    public static ObservableList<logrecordsadmin> getlogrecordsadmin(){
//     Connection conn = ConnectDb();
//     ObservableList<logrecordsadmin> list = FXCollections.observableArrayList();
//
//     try{
//         PreparedStatement ps = conn.prepareStatement("Select * from logrecordsadmin ORDER BY TIME_IN DESC");  
//         ResultSet rs = ps.executeQuery();
//       
//        while (rs.next()){
//          list.add(new logrecordsadmin (rs.getString("NAME"), 
//                  rs.getString("TIME_IN"),
//                  rs.getString("TIME_OUT")));  
//        }
//      }
//     catch (Exception e){
//    } 
//     return list;
//    }
//    
//    
//    ////////FOR CATEGORY
//     public static ObservableList<category> getCATEGORY(){
//    
//    Connection conn = ConnectDb();
//     ObservableList<category> list = FXCollections.observableArrayList();
//      
//     
//     try{
//         PreparedStatement ps = conn.prepareStatement("Select * from books");  // where ID=?, ITEMS=?, PRICE=?, STOCKS=?
//      // ps.setString(1, (String)TableView.getSelectionModel().getSelectedItem());
//         ResultSet rs = ps.executeQuery();
//       
//        while (rs.next()){
//          list.add(new category (rs.getString("CATEGORY")));
//            
//        }
//     }
//     catch (Exception e){
//    } 
//     return list;
//    }
//     
//     
//      ////GET users 
//    public static ObservableList<users> getUsers(){
//     Connection conn = ConnectDb();
//     ObservableList<users> list = FXCollections.observableArrayList();
//
//     try{
//         PreparedStatement ps = conn.prepareStatement("Select * from users");  
//         ResultSet rs = ps.executeQuery();
//       
//        while (rs.next()){
//          list.add(new users (rs.getInt("ID"),
//                  rs.getString("NAME"),
//                  rs.getString("GENDER"),
//                  rs.getString("EMAIL"),
//                  rs.getString("DEPARTMENT"),
//                  rs.getString("TYPE"),
//                  rs.getString("IDNUMBER"),
//                  rs.getString("PASSWORD")));  
//        }
//      }
//     catch (Exception e){
//    } 
//     return list;
//    }
     
}