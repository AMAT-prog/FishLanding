/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.sql.Types;
import java.sql.DriverManager;  // only if you use DriverManager directly
import javafx.collections.FXCollections;
import java.sql.ResultSet;


/**
 * FXML Controller class
 *
 * @author User
 */
public class DashboardflController implements Initializable {

    @FXML
    private ToggleButton weekToggle;
    @FXML
    private ToggleGroup timeToggleGroup;
    @FXML
    private ToggleButton monthToggle;
    @FXML
    private LineChart<?, ?> lineChart;
    @FXML
    private ScrollPane landings_pane;
    @FXML
    private Label TotalLandingsToday_label;
    @FXML
    private Label TotalLandingsThisMonth_label;
    @FXML
    private Label AveragePerFisherfolk_label;
    @FXML
    private Label TotalSales_label;
    @FXML
    private TextField filterField_fishLandings;
    @FXML
    private TableView<Catch> catch_tv;
    @FXML
    private TableColumn<Catch, Integer> catchId_col;
    @FXML
    private TableColumn<Catch, String> fisherman_col;
    @FXML
    private TableColumn<Catch, String> fishType_col;
    @FXML
    private TableColumn<Catch, Double> quantity_col;
    @FXML
    private TableColumn<Catch, Double> price_col;
    @FXML
    private TableColumn<Catch, Double> total_col;
    @FXML
    private TableColumn<Catch, LocalDate> dockDate_col;
    @FXML
    private TableColumn<Catch, LocalTime> dockTime_col;
    @FXML
    private TableColumn<Catch, String> remarks_col;
    @FXML
    private DatePicker dpStartDate;
    @FXML
    private DatePicker dpEndDate;
    @FXML
    private BorderPane addNewLanding_popup;
    @FXML
    private ComboBox<FisherfolkItem> cbFisherfolk2;
    @FXML
    private Label errFisherfolk2;
    @FXML
    private ComboBox<SpeciesItem> cbSpecies2;
    @FXML
    private Label errSpecies2;
    @FXML
    private TextField tfQuantity2;
    @FXML
    private Label errQuantity2;
    @FXML
    private TextField tfPricePerKilo2;
    @FXML
    private Label errPrice2;
    @FXML
    private DatePicker dpCatchDate2;
    @FXML
    private TextField tfDockingTime2;
    @FXML
    private Label errDockingTime2;
    @FXML
    private Label errDate2;
    @FXML
    private TextArea taRemarks2;
    @FXML
    private Label lblTotalValue2;
    @FXML
    private ScrollPane dashboard_pane;
    @FXML
    private ToggleGroup sideNav;

    ObservableList <Catch> CATCHdataList;
    private FilteredList<Catch> CATCHfilteredData;
    
    
    // Track edit mode
    private Integer editingCatchId = null;   // null = adding new, non-null = updating existing

    /**
     * Initializes the controller class.
     */
    public  void LANDINGS_SEARCH(){ 
        catchId_col.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getCatchId()).asObject());
        fisherman_col.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getFisherfolkName()));
        fishType_col.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getSpeciesName()));
        quantity_col.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getQuantity()).asObject());
        price_col.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getPricePerKilo()).asObject());
        total_col.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getTotalValue()).asObject());
        dockDate_col.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getCatchDate()));
        dockTime_col.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getDockingTime()));
        remarks_col.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getRemarks()));
    
        CATCHdataList = mysqlconnect.getCatch(); 
     
        CATCHfilteredData = new FilteredList<>(CATCHdataList, b-> true);
        // Text search listener (kept from the code, but delegate to updateFilters())
        filterField_fishLandings.textProperty().addListener((obs, oldV, newV) -> updateFilters());

        // Live filtering when dates change:
         dpStartDate.valueProperty().addListener((obs, o, n) -> updateFilters());
         dpEndDate.valueProperty().addListener((obs, o, n) -> updateFilters());

            SortedList<Catch> sortedData = new SortedList<>(CATCHfilteredData);
            sortedData.comparatorProperty().bind(catch_tv.comparatorProperty());
            catch_tv.setItems(sortedData);

            updateFilters();
   }
    
    

private void updateFilters() {
    final String q = filterField_fishLandings.getText() == null ? "" :
                     filterField_fishLandings.getText().trim().toLowerCase();
    LocalDate start = dpStartDate.getValue();
    LocalDate end   = dpEndDate.getValue();

    // If user inverted the dates, auto-swap for convenience
    if (start != null && end != null && end.isBefore(start)) {
        LocalDate tmp = start;
        start = end;
        end = tmp;
        // reflect the swap in the UI
        dpStartDate.setValue(start);
        dpEndDate.setValue(end);
    }

    final LocalDate fStart = start;
    final LocalDate fEnd   = end;

    CATCHfilteredData.setPredicate(c -> {
        // 1) Text match (fisherfolk name or species)
        boolean matchesText = true;
        if (!q.isEmpty()) {
            String name = c.getFisherfolkName() == null ? "" : c.getFisherfolkName().toLowerCase();
            String specie = c.getSpeciesName() == null ? "" : c.getSpeciesName().toLowerCase();
            matchesText = name.contains(q) || specie.contains(q);
        }

        // 2) Date range match
        // Assuming c.getCatchDate() returns a LocalDate (as in the columns)
        LocalDate d = c.getCatchDate();
        boolean matchesDate = true; // default allow when no range set
        if (fStart != null || fEnd != null) {
            if (d == null) return false; // if filtering by date, exclude rows with no date
            if (fStart != null && d.isBefore(fStart)) return false;
            if (fEnd   != null && d.isAfter(fEnd))   return false;
        }

        return matchesText && matchesDate;
    });
}
    
    private void hideAllErrors() {
    errFisherfolk2.setVisible(false);
    errSpecies2.setVisible(false);
    errQuantity2.setVisible(false);
    errPrice2.setVisible(false);
    errDockingTime2.setVisible(false);
    errDate2.setVisible(false);
    }   
    
    private void loadFisherfolkOptions() {
    ObservableList<FisherfolkItem> items = FXCollections.observableArrayList();
    String sql = "SELECT fisherfolk_id, name FROM fisherfolk WHERE is_active = TRUE ORDER BY name ASC";

    try (Connection conn = mysqlconnect.ConnectDb();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
            items.add(new FisherfolkItem(rs.getInt("fisherfolk_id"),
                                         rs.getString("name")));
        }
        cbFisherfolk2.setItems(items);
        cbFisherfolk2.getSelectionModel().clearSelection();
    } catch (Exception ex) {
        ex.printStackTrace();
        // optional: show alert if you want
    }
}

private void loadSpeciesOptions() {
    ObservableList<SpeciesItem> items = FXCollections.observableArrayList();
    String sql = "SELECT species_id, species_name FROM species ORDER BY species_name ASC";

    try (Connection conn = mysqlconnect.ConnectDb();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
            items.add(new SpeciesItem(rs.getInt("species_id"),
                                      rs.getString("species_name")));
        }
        cbSpecies2.setItems(items);
        cbSpecies2.getSelectionModel().clearSelection();
    } catch (Exception ex) {
        ex.printStackTrace();
    }
}

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        LANDINGS_SEARCH();
        hideAllErrors();
        
        loadFisherfolkOptions();
        loadSpeciesOptions();
    }    

    @FXML
    private void SEARCHBAR_mouseclicked(MouseEvent event) {
    }

    @FXML
    private void onClearFilterDate(ActionEvent event) {
        dpStartDate.setValue(null);
        dpEndDate.setValue(null);
    // To clear the text box when clearing date filters
        filterField_fishLandings.clear();
    
        updateFilters();
    }

    @FXML
    private void addLanding(ActionEvent event) {
        addNewLanding_popup.setVisible(true);
        landings_pane.setDisable(true);
    }
    
    @FXML
    private void btnCancel_onAddLanding(ActionEvent event) {
        addNewLanding_popup.setVisible(false);
        landings_pane.setDisable(false);
        
        //clear landing form
        cbFisherfolk2.getSelectionModel().clearSelection();
        cbSpecies2.getSelectionModel().clearSelection();
        tfQuantity2.clear();
        tfPricePerKilo2.clear();
        tfDockingTime2.clear();  
        dpCatchDate2.setValue(null);
        taRemarks2.clear();
        hideAllErrors();
        
        // exit edit mode
        editingCatchId = null; 
    }

    @FXML
    private void btnClear_onAddLanding(ActionEvent event) {
        cbFisherfolk2.getSelectionModel().clearSelection();
        cbSpecies2.getSelectionModel().clearSelection();
        tfQuantity2.clear();
        tfPricePerKilo2.clear();
        tfDockingTime2.clear();  // optional
        dpCatchDate2.setValue(null);
        taRemarks2.clear();
        hideAllErrors();
    }

    @FXML
    private void btnSave_onAddLanding(ActionEvent event) {
    hideAllErrors();
    boolean valid = true;

    // 1) Validate fisherfolk selection
    FisherfolkItem fisher = cbFisherfolk2.getValue();
    if (fisher == null) {
        errFisherfolk2.setVisible(true);
        valid = false;
    }

    // 2) Validate species selection
    SpeciesItem species = cbSpecies2.getValue();
    if (species == null) {
        errSpecies2.setVisible(true);
        valid = false;
    }

    // 3) Validate quantity > 0
    Double quantity = parsePositiveDouble(tfQuantity2.getText());
    if (quantity == null) {
        errQuantity2.setVisible(true);
        valid = false;
    }

    // 4) Validate price_per_kilo > 0
    Double pricePerKilo = parsePositiveDouble(tfPricePerKilo2.getText());
    if (pricePerKilo == null) {
        errPrice2.setVisible(true);
        valid = false;
    }

    // 5) Validate date (required by schema)
    LocalDate catchDate = dpCatchDate2.getValue();
    if (catchDate == null) {
       errDockingTime2.setVisible(true);
        // Minimal: show a quick dialog.
        // showInfo("Please select a Catch Date.");
        valid = false;
    }

    // 6) Docking time. If provided, must be HH:mm 24h
    LocalTime dockingTime = null;
    String timeText = tfDockingTime2.getText() == null ? "" : tfDockingTime2.getText().trim();
    if (!timeText.isEmpty()) {
        try {
            // Accept HH:mm or HH:mm:ss; normalize if user enters just HH:mm
            if (timeText.length() == 5) { // e.g. "06:45"
                dockingTime = LocalTime.parse(timeText + ":00");
            } else {
                dockingTime = LocalTime.parse(timeText); // "06:45:00"
            }
        } catch (DateTimeParseException ex) {
            errDockingTime2.setVisible(true); // "Use 24h format, e.g., 06:45"
            valid = false;
        }
    }

    // Remarks (optional)
    String remarks = taRemarks2.getText();
    if (remarks != null) {
        remarks = remarks.trim();
        if (remarks.isEmpty()) remarks = null;
    }

    if (!valid) {
        // Stop if any validation failed
        return;
    }
         // 1) run your existing validation here (show error labels if needed)
    //    — you already have errFisherfolk2, errSpecies2, errQuantity2, errPrice2, errDockingTime2
    //    Reuse the exact validation from your previous add code.

    // Assuming you validated and converted to:
    fisher = cbFisherfolk2.getValue();
    species   = cbSpecies2.getValue();
    quantity       = parsePositiveDouble(tfQuantity2.getText());
    pricePerKilo   = parsePositiveDouble(tfPricePerKilo2.getText());
    catchDate   = dpCatchDate2.getValue();
    dockingTime = parseOptionalTime(tfDockingTime2.getText()); // implement as before
    remarks        = normalizeEmptyToNull(taRemarks2.getText());

    if (fisher == null || species == null || quantity == null || pricePerKilo == null || catchDate == null) {
        return; // validation already showed labels; stop
    }

    // 2) Choose SQL depending on add vs edit
    final String insertSql =
        "INSERT INTO catch (fisherfolk_id, species_id, quantity, price_per_kilo, catch_date, docking_time, remarks) " +
        "VALUES (?,?,?,?,?,?,?)";

    final String updateSql =
        "UPDATE catch SET fisherfolk_id=?, species_id=?, quantity=?, price_per_kilo=?, catch_date=?, docking_time=?, remarks=? " +
        "WHERE catch_id=?";

    try (Connection conn = mysqlconnect.ConnectDb();
         PreparedStatement ps = conn.prepareStatement(
             (editingCatchId == null) ? insertSql : updateSql
         )) {

        // common params 1..7
        ps.setInt(1, fisher.getId());
        ps.setInt(2, species.getId());
        ps.setBigDecimal(3, java.math.BigDecimal.valueOf(quantity));
        ps.setBigDecimal(4, java.math.BigDecimal.valueOf(pricePerKilo));
        ps.setDate(5, java.sql.Date.valueOf(catchDate));

        if (dockingTime != null) {
            ps.setTime(6, java.sql.Time.valueOf(dockingTime));
        } else {
            ps.setNull(6, java.sql.Types.TIME);
        }

        if (remarks != null && !remarks.isBlank()) {
            ps.setString(7, remarks);
        } else {
            ps.setNull(7, java.sql.Types.VARCHAR);
        }

        // add WHERE param for update
        if (editingCatchId != null) {
            ps.setInt(8, editingCatchId);
        }

        int rows = ps.executeUpdate();
        if (rows > 0) {
            // Success
            if (editingCatchId == null) {
                showInfo("Landing saved.");
            } else {
                showInfo("Landing updated.");
            }

            // Close popup, clear form, and reload table
            // helper that clears fields & hides errors
            addNewLanding_popup.setVisible(false);
            cbFisherfolk2.getSelectionModel().clearSelection();
            cbSpecies2.getSelectionModel().clearSelection();
            tfQuantity2.clear();
            tfPricePerKilo2.clear();
            tfDockingTime2.clear();  
            dpCatchDate2.setValue(null);
            taRemarks2.clear();
            hideAllErrors(); 
            // re-query and setItems()
            LANDINGS_SEARCH();    

            // Exit edit mode
            editingCatchId = null;
        } else {
            showInfo("No changes were applied.");
        }

    } catch (Exception ex) {
        ex.printStackTrace();
        showInfo("Error: " + ex.getMessage());
    }
//    // 7) INSERT into MySQL
//    final String sql = "INSERT INTO catch " +
//            "(fisherfolk_id, species_id, quantity, price_per_kilo, catch_date, docking_time, remarks) " +
//            "VALUES (?,?,?,?,?,?,?)";
//
//    try (Connection conn = mysqlconnect.ConnectDb();
//         PreparedStatement ps = conn.prepareStatement(sql)) {
//
//        ps.setInt(1, fisher.getId());
//        ps.setInt(2, species.getId());
//        ps.setBigDecimal(3, java.math.BigDecimal.valueOf(quantity));
//        ps.setBigDecimal(4, java.math.BigDecimal.valueOf(pricePerKilo));
//        ps.setDate(5, java.sql.Date.valueOf(catchDate));
//
//        if (dockingTime != null) {
//            ps.setTime(6, Time.valueOf(dockingTime));
//        } else {
//            ps.setNull(6, Types.TIME);
//        }
//
//        if (remarks != null) {
//            ps.setString(7, remarks);
//        } else {
//            ps.setNull(7, Types.VARCHAR);
//        }
//
//        int rows = ps.executeUpdate();
//        if (rows > 0) {
//            showInfo("Catch record saved successfully.");
//            //clear landing form
//                cbFisherfolk2.getSelectionModel().clearSelection();
//                cbSpecies2.getSelectionModel().clearSelection();
//                tfQuantity2.clear();
//                tfPricePerKilo2.clear();
//                tfDockingTime2.clear();  // optional
//                dpCatchDate2.setValue(null);
//                taRemarks2.clear();
//                hideAllErrors();
//            // TableView, refresh/reload:
//                LANDINGS_SEARCH();
//            // tableView.setItems(mysql.getCatchRecords());
//        } else {
//            showInfo("Nothing was saved. Please try again.");
//        }
//    } catch (Exception ex) {
//        ex.printStackTrace();
//        showInfo("Error while saving: " + ex.getMessage());
//    }
        addNewLanding_popup.setVisible(false);
        landings_pane.setDisable(false);
    }
    private Double parsePositiveDouble(String text) {
    if (text == null) return null;
    try {
        double v = Double.parseDouble(text.trim());
        return v > 0 ? v : null;
    } catch (NumberFormatException e) { return null; }
}

private LocalTime parseOptionalTime(String text) {
    if (text == null) return null;
    String t = text.trim();
    if (t.isEmpty()) return null;
    try {
        // accept HH:mm and HH:mm:ss
        if (t.length() == 5) t = t + ":00";
        return java.time.LocalTime.parse(t);
    } catch (Exception ex) {
        // show your errDockingTime2 label here if you want
        errDockingTime2.setVisible(true);
        return null;
    }
}

private String normalizeEmptyToNull(String s) {
    return (s == null || s.trim().isEmpty()) ? null : s.trim();
}

    
    private void showInfo(String msg) {
    Alert a = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
    a.setHeaderText(null);
    a.showAndWait();
}
    
    
    @FXML
    private void deleteLanding(ActionEvent event) {
        // get selected row
    Catch selected = catch_tv.getSelectionModel().getSelectedItem();

    if (selected == null) {
        Alert alert = new Alert(Alert.AlertType.WARNING, 
                                "Please select a record to delete.", 
                                ButtonType.OK);
        alert.setHeaderText(null);
        alert.showAndWait();
        return;
    }

    // confirm deletion
    Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, 
                              "Are you sure you want to delete this landing record?",
                              ButtonType.YES, ButtonType.NO);
    confirm.setHeaderText(null);
    confirm.showAndWait();

    if (confirm.getResult() == ButtonType.YES) {
        String sql = "DELETE FROM catch WHERE catch_id = ?";

        try (Connection conn = mysqlconnect.ConnectDb();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, selected.getCatchId());
            int rows = ps.executeUpdate();

            if (rows > 0) { //means deleted already
                LANDINGS_SEARCH(); //reload table
//                catch_tv.getItems().remove(selected); // remove from UI
                Alert success = new Alert(Alert.AlertType.INFORMATION, 
                                          "Landing record deleted successfully.", 
                                          ButtonType.OK);
                success.setHeaderText(null);
                success.showAndWait();
            } else {
                Alert fail = new Alert(Alert.AlertType.ERROR, 
                                       "Failed to delete record. It may not exist.", 
                                       ButtonType.OK);
                fail.setHeaderText(null);
                fail.showAndWait();
            }

        } catch (Exception e) {
            e.printStackTrace();
            Alert err = new Alert(Alert.AlertType.ERROR, 
                                  "Error deleting record: " + e.getMessage(), 
                                  ButtonType.OK);
            err.setHeaderText(null);
            err.showAndWait();
        }
    }
    }

    @FXML
    private void updateLanding(ActionEvent event) {
        
        Catch sel = catch_tv.getSelectionModel().getSelectedItem();
    if (sel == null) {
        new Alert(Alert.AlertType.WARNING, "Please select a record to update.", ButtonType.OK).showAndWait();
        return;
    }

    // put controller into EDIT mode
    editingCatchId = sel.getCatchId();

    // prefill combos by IDs
    selectFisherById(sel.getFisherfolkId());
    selectSpeciesById(sel.getSpeciesId());

    // numeric fields
    tfQuantity2.setText(String.valueOf(sel.getQuantity()));
    tfPricePerKilo2.setText(String.valueOf(sel.getPricePerKilo()));

    // date
    dpCatchDate2.setValue(sel.getCatchDate());

    // time (optional)
    if (sel.getDockingTime() != null) {
        // show HH:mm (you can switch to HH:mm:ss if you prefer)
        String hhmm = String.format("%02d:%02d", sel.getDockingTime().getHour(), sel.getDockingTime().getMinute());
        tfDockingTime2.setText(hhmm);
    } else {
        tfDockingTime2.clear();
    }

    // remarks (may be null)
    taRemarks2.setText(sel.getRemarks() != null ? sel.getRemarks() : "");
    
    // finally show the popup
    addNewLanding_popup.setVisible(true);
    landings_pane.setDisable(true);
    }
    
private void selectFisherById(int id) {
    for (FisherfolkItem it : cbFisherfolk2.getItems()) {
        if (it.getId() == id) { cbFisherfolk2.getSelectionModel().select(it); return; }
    }
    cbFisherfolk2.getSelectionModel().clearSelection();
}

private void selectSpeciesById(int id) {
    for (SpeciesItem it : cbSpecies2.getItems()) {
        if (it.getId() == id) { cbSpecies2.getSelectionModel().select(it); return; }
    }
    cbSpecies2.getSelectionModel().clearSelection();
}


    @FXML
    private void generateReport(ActionEvent event) {
    }

    @FXML
    private void dashboard_btn(ActionEvent event) {
        dashboard_pane.setVisible(true);
         
        landings_pane.setVisible(false);
    }

    @FXML
    private void landings_btn(ActionEvent event) {
        landings_pane.setVisible(true);
       
        dashboard_pane.setVisible(false);
    }

    @FXML
    private void fishermen_btn(ActionEvent event) {
    }

    @FXML
    private void transactionSales_btn(ActionEvent event) {
    }

    @FXML
    private void dockingLogs_btn(ActionEvent event) {
    }

    @FXML
    private void reportsAnalytics_btn(ActionEvent event) {
    }

    @FXML
    private void species_btn(ActionEvent event) {
    }

    @FXML
    private void dataInformation_btn(ActionEvent event) {
    }

    @FXML
    private void accountProfile_btn(ActionEvent event) {
    }

    

    
    
}
