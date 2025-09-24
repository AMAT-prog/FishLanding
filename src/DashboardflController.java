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
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;
import javafx.scene.layout.VBox;
import javafx.beans.binding.Bindings;
import javafx.scene.layout.Region;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.util.Duration;


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
    @FXML
    private VBox sideNavigation_vbox;
    
    @FXML
    private AnchorPane fishermen_pane;
    @FXML
    private TextField filterField_fishermen;
    @FXML
    private TableView<FisherfolkRecord> fishermen_tv;
    @FXML
    private TableColumn<FisherfolkRecord, String> name_col;
    @FXML
    private TableColumn<FisherfolkRecord, Number> age_col;
    @FXML
    private TableColumn<FisherfolkRecord, String> gender_col;
    @FXML
    private TableColumn<FisherfolkRecord, String> contact_col;
    @FXML
    private TableColumn<FisherfolkRecord, String> address_col;
    @FXML
    private TableColumn<FisherfolkRecord, String> boat_col;
    @FXML
    private TableColumn<FisherfolkRecord, String> license_col;
    // status is a Button/Toggle column (use TableColumn<FisherfolkRecord, FisherfolkRecord>)
    @FXML
    private TableColumn<FisherfolkRecord, FisherfolkRecord> status_col; 
    
    @FXML
    private BorderPane addNewFisherfolk_popup;
    @FXML
    private Label Fisherfolk_err;
    @FXML
    private ComboBox<String> gender_cb;
    @FXML
    private TextField age_tf;
    @FXML
    private Label gender_err;
    @FXML
    private TextField Boat_tf;
    @FXML
    private TextField contact_tf;
    @FXML
    private TextField fisherfolk_tf;
    @FXML
    private TextField LicenseNumber_tf;
    @FXML
    private TextField address_tf;

    /**
     * Initializes the controller class.
     */
    
    //LANDINGS OR CATCH
    ObservableList <Catch> CATCHdataList;
    private FilteredList<Catch> CATCHfilteredData;
    private Integer editingCatchId = null;   // null = adding new, non-null = updating existing
    
    //FISHERMEN
    private javafx.collections.ObservableList<FisherfolkRecord> fisherData;
    private javafx.collections.transformation.FilteredList<FisherfolkRecord> fisherFiltered;
    private javafx.collections.transformation.SortedList<FisherfolkRecord> fisherSorted;
    private Integer editingFisherId = null; //Track whether adding or editing
    //for philippine currency (peso)
    private static final java.util.Locale LOCALE_PH = java.util.Locale.forLanguageTag("en-PH");
    // or: new java.util.Locale("en","PH")
    
    //TRANSACTION & SALES
    private javafx.collections.ObservableList<TransactionViewRow> transData;
    private javafx.collections.transformation.FilteredList<TransactionViewRow> transFiltered;
    private javafx.collections.transformation.SortedList<TransactionViewRow> transSorted;
    private static final java.time.format.DateTimeFormatter TS_FMT =
        java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

    //for adding/updating transaction
    private javafx.collections.ObservableList<FisherfolkItem> allSellers;
    // mode flags
    private boolean transactionUpdateMode = false;
    private Integer editingTransactionId = null;
    private CatchOption currentCatchOpt = null;
    // peso formatter
    private static final java.text.NumberFormat PHP = java.text.NumberFormat.getCurrencyInstance(LOCALE_PH);
    static { PHP.setCurrency(java.util.Currency.getInstance("PHP")); }
    private Integer updateCatchId = null; // set when entering update mode

    //DOCKING LOGS
    private javafx.collections.ObservableList<DockLogViewRow> dockData;
    private javafx.collections.transformation.FilteredList<DockLogViewRow> dockFiltered;
    private javafx.collections.transformation.SortedList<DockLogViewRow> dockSorted;
    // --- Dock Log Add/Update state ---
    private boolean dockUpdateMode = false;     // false = Add, true = Update-Departure
    private Integer editingDockLogId = null;    // set in update mode

    //REPORTS & ANALYTICS
    private final java.text.NumberFormat pesoFmt =
        java.text.NumberFormat.getCurrencyInstance(new java.util.Locale("en", "PH"));
    
    //SPECIES
    // --- state
    private javafx.collections.ObservableList<SpeciesItem> speciesData;
    private javafx.collections.transformation.FilteredList<SpeciesItem> speciesFiltered;
    private javafx.collections.transformation.SortedList<SpeciesItem> speciesSorted;

    private boolean speciesUpdateMode = false; // false = add, true = edit
    private Integer editingSpeciesId = null;

    //ACCOUNT PROFILE
    private final javafx.collections.ObservableList<String> ROLES =
        javafx.collections.FXCollections.observableArrayList("Admin","Staff");

    // Which user is logged in? If having a session, set this accordingly:
    private int currentUserId = 1;  // <-- replace with the real logged-in user id

    // Keep original values so Cancel can restore:
    private String origName, origContact, origRole;
    private byte[]  origPhoto;
    
    //DATA INFORMATION
    // Keep these consistent with CSS:
    private static final double TRACK_WIDTH = 56;
    private static final double PADDING     = 3;   // -fx-padding
    private static final double THUMB_SIZE  = 24;
    
    @FXML
    private BorderPane viewFisherHistory_popup;
    @FXML
    private TabPane history_tabpane;
    @FXML
    private TableView<CatchRecord> catch_tv1;
    @FXML
    private TableColumn<CatchRecord, LocalDate> catchDate_col;
    @FXML
    private TableColumn<CatchRecord, String> species_col;
    @FXML
    private TableColumn<CatchRecord, Number> quantity_col1;
    @FXML
    private TableColumn<CatchRecord, Number> price_col1;
    @FXML
    private TableColumn<CatchRecord, Number> total_col1;
    @FXML
    private TableView<TransactionRecord> txn_tv;
    @FXML
    private TableColumn<TransactionRecord, String> buyer_col;
    @FXML
    private TableColumn<TransactionRecord, Number> qtySold_col;
    @FXML
    private TableColumn<TransactionRecord, Number> unitPrice_col;
    @FXML
    private TableColumn<TransactionRecord, Number> totalPrice_col;
    @FXML
    private TableColumn<TransactionRecord, String> status_col1;
    @FXML
    private TableView<DockLogRecord> dock_tv;
    @FXML
    private TableColumn<DockLogRecord, LocalDate> dockDate_col1;
    @FXML
    private TableColumn<DockLogRecord, LocalTime> arrival_col;
    @FXML
    private TableColumn<DockLogRecord, LocalTime> departure_col;
    @FXML
    private TableColumn<DockLogRecord, String> remarks_col1;
    
    @FXML
    private ScrollPane transactionANDsales_pane;
    @FXML
    private Label totalRevenueToday_transacLabel;
    @FXML
    private Label totalPendingAmount_transacLabel;
    @FXML
    private Label TotalPartialUnpaidOrders_transacLabel;
    @FXML
    private Label totalPaidAmount_transacLabel;
    @FXML
    private Label TotalPaidOrders_transacLabel;
    @FXML
    private TextField filterField_transactions;
    @FXML
    private TableView<TransactionViewRow> transaction_tv;
    @FXML
    private TableColumn<TransactionViewRow, String> transacBuyer_col;
    @FXML
    private TableColumn<TransactionViewRow, String> transacFisherfolk_col;
    @FXML
    private TableColumn<TransactionViewRow, String> transacFishType_col;
    @FXML
    private TableColumn<TransactionViewRow, Number> transacQuantity_col;
    @FXML
    private TableColumn<TransactionViewRow, Number> transacPricePerUnit_col;
    @FXML
    private TableColumn<TransactionViewRow, Number> transacTotalValue_col;
    @FXML
    private TableColumn<TransactionViewRow, String> transacPaymentMethod_col;
    @FXML
    private TableColumn<TransactionViewRow, String> transacStatus_col;
    @FXML
    private TableColumn<TransactionViewRow, java.time.LocalDateTime> transacDate_col;
    @FXML
    private ToggleButton all_transacStatus;
    @FXML
    private ToggleGroup transaction_toggle;
    @FXML
    private ToggleButton paid_transacStatus;
    @FXML
    private ToggleButton unpaid_transacStatus;
    @FXML
    private ToggleButton partial_transacStatus;
    @FXML
    private DatePicker dpStartDate_transac;
    @FXML
    private DatePicker dpEndDate_transac;
    
    @FXML private BorderPane               addNewTransaction_popup;
    @FXML private ComboBox<FisherfolkItem> transacSeller_cb;
    @FXML private ComboBox<CatchOption>    transacFishType_cb;
    @FXML private ComboBox<String>         transacPaymentMethod_cb;
    @FXML private ComboBox<String>         transacPaymentStatus_cb;
    @FXML
    private TextField transacQuantity_tf;
    @FXML
    private TextField transacUnitPrice_tf;
    @FXML
    private TextField transacBuyer_tf;
    @FXML
    private TextArea transacRemarks_ta;
    @FXML
    private Label Seller_err;
    @FXML
    private Label FishType_err;
    @FXML
    private Label Buyer_err;
    @FXML
    private Label PMethod_err;
    @FXML
    private Label Quantity_err;
    @FXML
    private Label PStatus_err;
    @FXML
    private Label transacTotalAmount_label;
    @FXML
    private Label transacRemainingQuantity_label;
    @FXML
    private Label price_err;
    
    @FXML
    private ScrollPane dockingLogs_pane;
    @FXML
    private Label totalDockToday_label;
    @FXML
    private Label mostActiveLast7days_label;
    @FXML
    private TextField filterField_dockLogs;
    @FXML
    private TableView<DockLogViewRow> dockLogs_tv;
    @FXML
    private TableColumn<DockLogViewRow, String> dockLogFisherfolk_col;
    @FXML
    private TableColumn<DockLogViewRow, String> dockLogBoat_col;
    @FXML
    private TableColumn<DockLogViewRow, java.time.LocalDate> dockLogDate_col;
    @FXML
    private TableColumn<DockLogViewRow, java.time.LocalTime> dockLogArrival_col;
    @FXML
    private TableColumn<DockLogViewRow, java.time.LocalTime> dockLogDeparture_col;
    @FXML
    private TableColumn<DockLogViewRow, String> dockLogRemarks_col;
    @FXML
    private DatePicker dpStartDate_dockLog;
    @FXML
    private DatePicker dpEndDate_dockLog;
    @FXML
    private BorderPane addNewDockLog_popup;
    @FXML
    private ComboBox<FisherfolkItem> dockLogFisherfolk_cb;
    @FXML
    private Label dockLogFisherfolk_err;
    @FXML
    private Label dockLogDockDate_err;
    @FXML
    private Label dockLogArrivalTime_err;
    @FXML
    private Label dockLogBoatName_label;
    @FXML
    private DatePicker dockLogDate_dp;
    @FXML
    private TextField dockLogArrivalTime_tf;
    @FXML
    private TextField dockLogDepartureTime_tf;
    @FXML
    private TextArea dockLogRemarks_ta;
    
    @FXML
    private ScrollPane reportsANDanalytics_pane;
    @FXML
    private ToggleGroup reports_toggle;
    @FXML
    private ToggleButton rbDaily;
    @FXML
    private ToggleButton rbWeekly;
    @FXML
    private ToggleButton rbMonthly;
    @FXML
    private LineChart<String, Number> salesChart;
    @FXML
    private NumberAxis salesYAxis;
    @FXML
    private CategoryAxis salesXAxis;
    @FXML
    private DatePicker dpStart;
    @FXML
    private DatePicker dpEnd;
    @FXML
    private TabPane reportsTabPane;
    @FXML
    private Tab salesTab;
    @FXML
    private Tab speciesTab;
    @FXML
    private Tab contribTab;
    @FXML
    private Tab volumesTab;
    @FXML
    private BarChart<String,Number> contribBarChart;
    @FXML
    private PieChart speciesPieChart;
    @FXML
    private StackedBarChart<String,Number> catchStackedBarChart;
    @FXML
    private Label speciesDateLabel;
    @FXML
    private Label fisherfolkDateLabel;
    @FXML
    private Label catchVolumeDateLabel;
    
    @FXML
    private ScrollPane species_pane;
    @FXML
    private TableView<SpeciesItem> species_tv;
    @FXML
    private TableColumn<SpeciesItem, String> speciesName_col;
    @FXML
    private TableColumn<SpeciesItem, String> speciesDescription_col;
    @FXML
    private TextField filterField_species;
    @FXML
    private BorderPane addSpecies_popup;
    @FXML
    private Label speciesName_err;
    @FXML
    private TextField speciesName_tf;
    @FXML
    private TextArea speciesDescription_tf;
    
    @FXML
    private ScrollPane accountProfile_pane;
    @FXML
    private TextField accountName_tf;
    @FXML
    private TextField accountContact_tf;
    @FXML
    private ComboBox<String> accountRole_cb;
    @FXML
    private HBox cancelSavebtn_hbox;
    @FXML
    private TextField newUsername_tf;
    @FXML
    private PasswordField currentPassword_pf;
    @FXML
    private PasswordField newPassword_pf;
    @FXML
    private PasswordField confirmNewPassword_pf;
    @FXML
    private Label password_err;
    @FXML
    private TextField currentUsername_tf;
    @FXML
    private Label username_err;
    @FXML
    private Label updatedDate_label;
    @FXML
    private ImageView accountPhoto_iv;
    @FXML
    private TextField currentPassword_tf_visible;
    @FXML
    private TextField newPassword_tf_visible;
    @FXML
    private TextField confirmNewPassword_tf_visible;
    @FXML
    private Button uploadPhoto_btn;
    
    @FXML
    private ToggleButton autoBackupSwitch;
    @FXML
    private Region thumb;
    @FXML
    private Label lastBackup_label;
    @FXML
    private ScrollPane dataInformation_pane;

    
    ////////////////////////////////////////////////////////////////////////////SIDE NAVIGATION
    @FXML
    private void dashboard_btn(ActionEvent event) {
        dashboard_pane.setVisible(true);
         
        landings_pane.setVisible(false);
        fishermen_pane.setVisible(false);
        transactionANDsales_pane.setVisible(false);
        dockingLogs_pane.setVisible(false);
        reportsANDanalytics_pane.setVisible(false);
        species_pane.setVisible(false);
        accountProfile_pane.setVisible(false);
        dataInformation_pane.setVisible(false);
    }

    @FXML
    private void landings_btn(ActionEvent event) {
        landings_pane.setVisible(true);
       
        dashboard_pane.setVisible(false);
        fishermen_pane.setVisible(false);
        transactionANDsales_pane.setVisible(false);
        dockingLogs_pane.setVisible(false);
        reportsANDanalytics_pane.setVisible(false);
        species_pane.setVisible(false);
        accountProfile_pane.setVisible(false);
        dataInformation_pane.setVisible(false);
    }

    @FXML
    private void fishermen_btn(ActionEvent event) {
        fishermen_pane.setVisible(true);
        
        dashboard_pane.setVisible(false);
        landings_pane.setVisible(false);
        transactionANDsales_pane.setVisible(false);
        dockingLogs_pane.setVisible(false);
        reportsANDanalytics_pane.setVisible(false);
        species_pane.setVisible(false);
        accountProfile_pane.setVisible(false);
        dataInformation_pane.setVisible(false);
    }

    @FXML
    private void transactionSales_btn(ActionEvent event) {
        transactionANDsales_pane.setVisible(true);
        
        dashboard_pane.setVisible(false);
        fishermen_pane.setVisible(false);
        landings_pane.setVisible(false);
        dockingLogs_pane.setVisible(false);
        reportsANDanalytics_pane.setVisible(false);
        species_pane.setVisible(false);
        accountProfile_pane.setVisible(false);
        dataInformation_pane.setVisible(false);
    }

    @FXML
    private void dockingLogs_btn(ActionEvent event) {
        dockingLogs_pane.setVisible(true);
        
        dashboard_pane.setVisible(false);
        landings_pane.setVisible(false);
        fishermen_pane.setVisible(false);
        transactionANDsales_pane.setVisible(false);
        reportsANDanalytics_pane.setVisible(false);
        species_pane.setVisible(false);
        accountProfile_pane.setVisible(false);
        dataInformation_pane.setVisible(false);
    }

    @FXML
    private void reportsAnalytics_btn(ActionEvent event) {
        reportsANDanalytics_pane.setVisible(true);
        
        dashboard_pane.setVisible(false);
        landings_pane.setVisible(false);
        fishermen_pane.setVisible(false);
        transactionANDsales_pane.setVisible(false);
        dockingLogs_pane.setVisible(false);
        species_pane.setVisible(false);
        accountProfile_pane.setVisible(false);
        dataInformation_pane.setVisible(false);
    }

    @FXML
    private void species_btn(ActionEvent event) {
        species_pane.setVisible(true);
         
        landings_pane.setVisible(false);
        fishermen_pane.setVisible(false);
        transactionANDsales_pane.setVisible(false);
        dockingLogs_pane.setVisible(false);
        reportsANDanalytics_pane.setVisible(false);
        dashboard_pane.setVisible(false);
        accountProfile_pane.setVisible(false);
        dataInformation_pane.setVisible(false);
    }

    @FXML
    private void dataInformation_btn(ActionEvent event) {
        dataInformation_pane.setVisible(true);
         
        landings_pane.setVisible(false);
        fishermen_pane.setVisible(false);
        transactionANDsales_pane.setVisible(false);
        dockingLogs_pane.setVisible(false);
        reportsANDanalytics_pane.setVisible(false);
        species_pane.setVisible(false);
        accountProfile_pane.setVisible(false);
        dashboard_pane.setVisible(false);
    }

    @FXML
    private void accountProfile_btn(ActionEvent event) {
        accountProfile_pane.setVisible(true);
         
        landings_pane.setVisible(false);
        fishermen_pane.setVisible(false);
        transactionANDsales_pane.setVisible(false);
        dockingLogs_pane.setVisible(false);
        reportsANDanalytics_pane.setVisible(false);
        species_pane.setVisible(false);
        dashboard_pane.setVisible(false);
        dataInformation_pane.setVisible(false);
    }
    
    ////////////////////////////////////////////////////////////////////////////end of Side Navigation
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dashboard_pane.setVisible(true);
        landings_pane.setVisible(false);
        fishermen_pane.setVisible(false);
        transactionANDsales_pane.setVisible(false);
        dockingLogs_pane.setVisible(false);
        reportsANDanalytics_pane.setVisible(false);
        species_pane.setVisible(false);
        accountProfile_pane.setVisible(false);
        dataInformation_pane.setVisible(false);
        // LANDINGS or CATCHES
        LANDINGS_SEARCH();
        hideAllErrors();
        loadFisherfolkOptions();
        loadSpeciesOptions();
        
        //FISHERMEN
        fisherData = mysqlconnect.loadFisherfolk();

        // columns
        name_col.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getName()));
        age_col.setCellValueFactory(c -> new javafx.beans.property.SimpleIntegerProperty(c.getValue().getAge() == null ? 0 : c.getValue().getAge()));
        gender_col.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getGender()));
        contact_col.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getContactNumber()));
        address_col.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getAddress()));
        boat_col.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getBoatName()));
        license_col.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getLicenseNumber()));

        // status toggle button cell
        status_col.setCellValueFactory(c -> new javafx.beans.property.ReadOnlyObjectWrapper<>(c.getValue()));
        status_col.setCellFactory(col -> new javafx.scene.control.TableCell<>() {

            private final javafx.scene.control.ToggleButton toggle = new javafx.scene.control.ToggleButton();
            private final javafx.scene.control.Label label = new javafx.scene.control.Label();
            private final javafx.scene.layout.StackPane track = new javafx.scene.layout.StackPane();
            private final javafx.scene.shape.Circle thumb = new javafx.scene.shape.Circle(9); // 18px diameter
            private final javafx.scene.layout.VBox box = new javafx.scene.layout.VBox(2);     // label gap

            {
                // style classes
                toggle.getStyleClass().addAll("switch", "toggle-button");
                label.getStyleClass().add("status-label");
                track.getStyleClass().add("switch-track");
                thumb.getStyleClass().add("switch-thumb");

                // build the "switch"
                track.setPickOnBounds(false);
                var switchGraphic = new javafx.scene.layout.StackPane(track, thumb);
                toggle.setGraphic(switchGraphic);
                toggle.setContentDisplay(javafx.scene.control.ContentDisplay.GRAPHIC_ONLY);

                // layout: label above, switch below (flip order if you want the label under the toggle)
                box.setAlignment(javafx.geometry.Pos.CENTER);
                box.getChildren().setAll(label, toggle);

                // click handler (DB write + optimistic UI)
                toggle.setOnAction(e -> {
                    var row = getTableView().getItems().get(getIndex());
                    boolean newActive = toggle.isSelected();

                    // write to DB
                    boolean ok = mysqlconnect.updateFisherfolkActive(row.getFisherfolkId(), newActive);
                    if (ok) {
                        row.setActive(newActive);
                        updateVisual(newActive, true);
                    } else {
                        // revert on failure
                        toggle.setSelected(row.isActive());
                        updateVisual(row.isActive(), true);
                        showInfoFISHERMEN("Failed to update status.");
                    }
                });
            }

            @Override
            protected void updateItem(FisherfolkRecord item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    toggle.setSelected(item.isActive());
                    updateVisual(item.isActive(), false); // no animation on initial paint
                    setGraphic(box);
                }
            }

            /** Update label text/color, track color class, and thumb position (with optional animation). */
            private void updateVisual(boolean active, boolean animate) {
                // label
                label.setText(active ? "Active" : "Inactive");
                label.getStyleClass().removeAll("on");
                if (active) label.getStyleClass().add("on");

                // track color via style class on the toggle
                toggle.getStyleClass().remove("on");
                if (active) toggle.getStyleClass().add("on");

                // thumb position (translateX) — animate smoothly
                double toX = active ? 44 - 18 - 4 : 4; // right = trackWidth - thumbWidth - margin; left = margin
                // initialize left margin (so it's perfect on first paint)
                if (!animate) {
                    thumb.setTranslateX(toX);
                    return;
                }
                var tl = new javafx.animation.Timeline(
                    new javafx.animation.KeyFrame(javafx.util.Duration.millis(160),
                        new javafx.animation.KeyValue(thumb.translateXProperty(), toX, javafx.animation.Interpolator.EASE_BOTH)
                    )
                );
                tl.play();
            }
        });

        // filter
        fisherFiltered = new javafx.collections.transformation.FilteredList<>(fisherData, p -> true);
        filterField_fishermen.textProperty().addListener((obs, oldV, newV) -> {
            String q = newV == null ? "" : newV.trim().toLowerCase();
            fisherFiltered.setPredicate(rec -> {
                if (q.isEmpty()) return true;
                return (rec.getName() != null && rec.getName().toLowerCase().contains(q)) ||
                       (rec.getBoatName() != null && rec.getBoatName().toLowerCase().contains(q)) ||
                       (rec.getLicenseNumber() != null && rec.getLicenseNumber().toLowerCase().contains(q)) ||
                       (rec.getContactNumber() != null && rec.getContactNumber().toLowerCase().contains(q)) ||
                       (rec.getAddress() != null && rec.getAddress().toLowerCase().contains(q));
            });
        });

        fisherSorted = new javafx.collections.transformation.SortedList<>(fisherFiltered);
        fisherSorted.comparatorProperty().bind(fishermen_tv.comparatorProperty());
        fishermen_tv.setItems(fisherSorted);
        
        //adding and updating fisherfolks
            // gender options
        gender_cb.getItems().setAll("Male", "Female", "Other");
            // hide popup at start
        addNewFisherfolk_popup.setVisible(false);
        hideFisherErrors();
        
        //for viewing tables in fisherfolk history (catches/transactions/dock logs)
        // ===== CATCHES =====
        species_col.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getSpeciesName()));
        quantity_col1.setCellValueFactory(c -> new javafx.beans.property.SimpleDoubleProperty(c.getValue().getQuantity()));
        price_col1.setCellValueFactory(c -> new javafx.beans.property.SimpleDoubleProperty(c.getValue().getPricePerKilo()));
        total_col1.setCellValueFactory(c -> new javafx.beans.property.SimpleDoubleProperty(c.getValue().getTotalValue()));
        catchDate_col.setCellValueFactory(c -> new javafx.beans.property.SimpleObjectProperty<>(c.getValue().getCatchDate()));

        // nice formatting
        setNumeric2dp(quantity_col1);
        setCurrencyPeso(price_col1);
        setCurrencyPeso(total_col1);
        setDateFormat(catchDate_col, "yyyy-MM-dd");

        // ===== TRANSACTIONS =====
        buyer_col.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getBuyerName()));
        qtySold_col.setCellValueFactory(c -> new javafx.beans.property.SimpleDoubleProperty(c.getValue().getQtySold()));
        unitPrice_col.setCellValueFactory(c -> new javafx.beans.property.SimpleDoubleProperty(c.getValue().getUnitPrice()));
        totalPrice_col.setCellValueFactory(c -> new javafx.beans.property.SimpleDoubleProperty(c.getValue().getTotalPrice()));
        status_col1.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getStatus()));

        setNumeric2dp(qtySold_col);
        setCurrencyPeso(unitPrice_col);
        setCurrencyPeso(totalPrice_col);

        // ===== DOCK LOGS =====
        dockDate_col1.setCellValueFactory(c -> new javafx.beans.property.SimpleObjectProperty<>(c.getValue().getDockingDate()));
        arrival_col.setCellValueFactory(c -> new javafx.beans.property.SimpleObjectProperty<>(c.getValue().getArrival()));
        departure_col.setCellValueFactory(c -> new javafx.beans.property.SimpleObjectProperty<>(c.getValue().getDeparture()));
        remarks_col1.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getRemarks()));

        setDateFormat(dockDate_col1, "yyyy-MM-dd");
        setTimeFormat(arrival_col, "HH:mm");
        setTimeFormat(departure_col, "HH:mm");
        
        catch_tv1.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        txn_tv.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        dock_tv.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        ////TRANSACTION & SALES
        // column factories
        transacBuyer_col.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getBuyerName()));
        transacFisherfolk_col.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getFisherfolkName()));
        transacFishType_col.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getSpeciesName()));
        transacQuantity_col.setCellValueFactory(c -> new javafx.beans.property.SimpleDoubleProperty(c.getValue().getQtySold()));
        transacPricePerUnit_col.setCellValueFactory(c -> new javafx.beans.property.SimpleDoubleProperty(c.getValue().getUnitPrice()));
        transacTotalValue_col.setCellValueFactory(c -> new javafx.beans.property.SimpleDoubleProperty(c.getValue().getTotalPrice()));
        transacPaymentMethod_col.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getPaymentMethod()));
        transacStatus_col.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getPaymentStatus()));
        transacDate_col.setCellValueFactory(c -> new javafx.beans.property.SimpleObjectProperty<>(c.getValue().getTxnDate()));

        // formatting
        setNumeric2dp(transacQuantity_col);
        setCurrencyPeso(transacPricePerUnit_col);
        setCurrencyPeso(transacTotalValue_col);
        setDateTimeFormat(transacDate_col, "yyyy-MM-dd HH:mm");

        // data
        transData = mysqlconnect.loadTransactionsView();

        // filter chain
        transFiltered = new javafx.collections.transformation.FilteredList<>(transData, r -> true);
        transSorted = new javafx.collections.transformation.SortedList<>(transFiltered);
        transSorted.comparatorProperty().bind(transaction_tv.comparatorProperty());
        transaction_tv.setItems(transSorted);

        // search
        filterField_transactions.textProperty().addListener((o, ov, nv) -> applyTransFilters());

        // status toggles (put them in one ToggleGroup in FXML or code)
        transaction_toggle.selectedToggleProperty().addListener((o, ov, nv) -> applyTransFilters());

        // date filters
        dpStartDate_transac.valueProperty().addListener((o, ov, nv) -> applyTransFilters());
        dpEndDate_transac.valueProperty().addListener((o, ov, nv) -> applyTransFilters());

        // initial selection for status = ALL
        all_transacStatus.setSelected(true);

        // optional: resize policy
        transaction_tv.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // compute header KPI labels initially
        updateTransHeaderKpis();
        
        //adding or updating TRANSACTIONS
        // fill static combos
        transacPaymentMethod_cb.getItems().setAll("Cash","Credit","Bank Transfer","Other");
        transacPaymentStatus_cb.getItems().setAll("Paid","Partial","Unpaid");

        // seller list
        transacSeller_cb.setItems(mysqlconnect.loadActiveFisherfolkItems());
        transacSeller_cb.setConverter(new javafx.util.StringConverter<>() {
            @Override public String toString(FisherfolkItem f) { return f == null ? "" : f.getName(); }
            @Override public FisherfolkItem fromString(String s) { return null; }
        });

        // when seller changes -> reload catch options for that seller
        transacSeller_cb.getSelectionModel().selectedItemProperty().addListener((o, ov, sel) -> {
            if (sel != null) {
                var opts = mysqlconnect.loadCatchOptionsByFisher(sel.getId());
                transacFishType_cb.setItems(opts);
            } else {
                transacFishType_cb.getItems().clear();
            }
            transacFishType_cb.getSelectionModel().clearSelection();
            currentCatchOpt = null;
            transacRemainingQuantity_label.setText("");
            if (!transactionUpdateMode) transacUnitPrice_tf.clear();
            updateTotalLabel();
            hideErrors();
        });

        // nice label in fish type combo is provided by CatchOption.toString()

        // when fish type (catch) changes -> set unit price & remaining
        transacFishType_cb.getSelectionModel().selectedItemProperty().addListener((o, ov, sel) -> {
            currentCatchOpt = sel;
            if (sel != null) {
                transacUnitPrice_tf.setText(String.format("%.2f", sel.getPricePerKilo()));
                transacRemainingQuantity_label.setText(String.format("%.2f kg available", sel.getRemainingQty()));
            } else {
                transacUnitPrice_tf.clear();
                transacRemainingQuantity_label.setText("");
            }
            updateTotalLabel();
            hideErrors();
        });

        // live total computation
        transacQuantity_tf.textProperty().addListener((o,ov,nv) -> updateTotalLabel());
        transacUnitPrice_tf.textProperty().addListener((o,ov,nv) -> updateTotalLabel());

        // numeric filters (optional)
        makeNumeric(transacQuantity_tf);
        makeNumeric(transacUnitPrice_tf);

        // start hidden
        addNewTransaction_popup.setVisible(false);
        
        allSellers = mysqlconnect.loadActiveFisherfolkItems();
        transacSeller_cb.setItems(allSellers);

        //DOCKING LOGS
        // columns
        dockLogFisherfolk_col.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getFisherfolkName()));
        dockLogBoat_col.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getBoatName()));
        dockLogDate_col.setCellValueFactory(c -> new javafx.beans.property.SimpleObjectProperty<>(c.getValue().getDockingDate()));
        dockLogArrival_col.setCellValueFactory(c -> new javafx.beans.property.SimpleObjectProperty<>(c.getValue().getArrivalTime()));
        dockLogDeparture_col.setCellValueFactory(c -> new javafx.beans.property.SimpleObjectProperty<>(c.getValue().getDepartureTime()));
        dockLogRemarks_col.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getRemarks()));

        // pretty formatters (optional)
        setDateFormat(dockLogDate_col, "yyyy-MM-dd");
        setTimeFormat(dockLogArrival_col, "HH:mm");
        setTimeFormat(dockLogDeparture_col, "HH:mm");

        // load data
        dockData = mysqlconnect.loadDockLogsView();

        dockFiltered = new javafx.collections.transformation.FilteredList<>(dockData, r -> true);
        dockSorted = new javafx.collections.transformation.SortedList<>(dockFiltered);
        dockSorted.comparatorProperty().bind(dockLogs_tv.comparatorProperty());
        dockLogs_tv.setItems(dockSorted);

        // search filter
        filterField_dockLogs.textProperty().addListener((obs, old, val) -> applyDockFilters());

        // date filters
        dpStartDate_dockLog.valueProperty().addListener((o, a, b) -> applyDockFilters());
        dpEndDate_dockLog.valueProperty().addListener((o, a, b) -> applyDockFilters());

        // initial stats
        refreshDockStats();
        
        //adding/update dock logs
        //show boat name when choosing fisherfolk in cb
        dockLogFisherfolk_cb.getSelectionModel().selectedItemProperty().addListener((o, a, b) -> {
            var fi = (FisherfolkItem) b;
            dockLogBoatName_label.setText(fi == null || fi.getBoatName() == null ? "—" : fi.getBoatName());
        });

        //REPORTS & ANALYTICS
        salesChart.setAnimated(false);
        salesYAxis.setForceZeroInRange(true);
        salesChart.setCreateSymbols(true);
        ((NumberAxis) salesChart.getYAxis()).setForceZeroInRange(false);

        // default to monthly current year
        rbDaily.setSelected(true);
        // optional: default range = Jan 1..Dec 31 of this year
        var now = java.time.LocalDate.now();
        dpStart.setValue(now.with(java.time.temporal.TemporalAdjusters.firstDayOfYear()));
        dpEnd.setValue(now.with(java.time.temporal.TemporalAdjusters.lastDayOfYear()));

        // listeners
        rbDaily.setOnAction(e -> loadSalesSeries());
        rbWeekly.setOnAction(e -> loadSalesSeries());
        rbMonthly.setOnAction(e -> loadSalesSeries());
        dpStart.valueProperty().addListener((o,a,b) -> loadSalesSeries());
        dpEnd.valueProperty().addListener((o,a,b) -> loadSalesSeries());

        loadSalesSeries();
        loadSpeciesDistributionAuto();
        loadFisherfolkContribAuto();
        loadCatchVolumesAuto();
        
        // Fisherfolk Contributions (BarChart)
        contribBarChart.setCategoryGap(18);   // space between categories
        contribBarChart.setBarGap(6);         // space between series within a category

        // Stacked bar: usually one category per period, but spacing helps when many
        catchStackedBarChart.setCategoryGap(20);

        // If y-axis values get cramped:
        NumberAxis y1 = (NumberAxis) contribBarChart.getYAxis();
        y1.setMinorTickCount(0);
        y1.setTickLabelGap(8);
        y1.setForceZeroInRange(false);

        NumberAxis y2 = (NumberAxis) catchStackedBarChart.getYAxis();
        y2.setMinorTickCount(0);
        y2.setTickLabelGap(8);
        y2.setForceZeroInRange(false);

        // Optional tooltips (nice UX on bars & pie slices)
        contribBarChart.getData().forEach(s ->
            s.getData().forEach(d ->
                Tooltip.install(d.getNode(), new Tooltip(
                    s.getName() + ": " + d.getYValue()))
        ));
        speciesPieChart.getData().forEach(d ->
            Tooltip.install(d.getNode(), new Tooltip(
                d.getName() + " (" + String.format("%.1f%%", d.getPieValue()) + ")"))
        );

        //SPECIES
        // SPECIES table
        speciesName_col.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getSpeciesName()));
        speciesDescription_col.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(
                c.getValue().getDescription() == null ? "" : c.getValue().getDescription()));

        speciesData = mysqlconnect.loadSpecies();

        speciesFiltered = new javafx.collections.transformation.FilteredList<>(speciesData, s -> true);
        speciesSorted = new javafx.collections.transformation.SortedList<>(speciesFiltered);
        speciesSorted.comparatorProperty().bind(species_tv.comparatorProperty());
        species_tv.setItems(speciesSorted);

        // filter by name
        filterField_species.textProperty().addListener((obs, o, v) -> {
            String q = v == null ? "" : v.trim().toLowerCase();
            speciesFiltered.setPredicate(s -> q.isEmpty() || (s.getSpeciesName() != null && s.getSpeciesName().toLowerCase().contains(q)));
        });

        // hide popup initially
        addSpecies_popup.setVisible(false);
        speciesName_err.setVisible(false);

        //ACCOUNT PROFILE
        initUserProfile();
        
        //DATA INFORMATION
//        Region thumb = (Region) autoBackupSwitch.getGraphic();
//        thumb.translateXProperty().bind(
//                Bindings.when(autoBackupSwitch.selectedProperty())
//                        .then(48 - 22 - 4)    // trackWidth - thumbSize - 2*padding = 48 - 22 - 4 = 22
//                        .otherwise(0)
//        );
//        autoBackupSwitch.accessibleTextProperty().bind(
//            Bindings.when(autoBackupSwitch.selectedProperty()).then("On").otherwise("Off")
//        );

        // If kept the fixed CSS sizes, a constant works:
        final double ON_OFFSET = TRACK_WIDTH - THUMB_SIZE - (2 * PADDING);

        // Smooth slide animation when toggling
        autoBackupSwitch.selectedProperty().addListener((obs, wasOn, isOn) -> {
            double target = isOn ? ON_OFFSET : 0;
            TranslateTransition tt = new TranslateTransition(Duration.millis(140), thumb);
            tt.setToX(target);
            tt.play();
        });

        // Set initial position (in case it's pre-selected)
        thumb.setTranslateX(autoBackupSwitch.isSelected() ? ON_OFFSET : 0);

        // If later, change sizes dynamically, replace the above with a binding:
        // thumb.translateXProperty().bind(Bindings.createDoubleBinding(
        //     () -> autoBackupSwitch.isSelected()
        //           ? autoBackupSwitch.getWidth() - thumb.getWidth() - (2 * PADDING)
        //           : 0,
        //     autoBackupSwitch.selectedProperty(),
        //     autoBackupSwitch.widthProperty(),
        //     thumb.widthProperty()
        // ));
    
    }
        
    ////////////////////////////////////////////////////////////////////////////end of initialization
    
    ////////////////////////////////////////////////////////////////////////////LANDINGS
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
        sideNavigation_vbox.setDisable(true);
    }
    
    @FXML
    private void addLanding(ActionEvent event) {
        addNewLanding_popup.setVisible(true);
        landings_pane.setDisable(true);
        sideNavigation_vbox.setDisable(true);
    }
    
    @FXML
    private void btnCancel_onAddLanding(ActionEvent event) {
        addNewLanding_popup.setVisible(false);
        landings_pane.setDisable(false);
        sideNavigation_vbox.setDisable(false);
        
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
         // 1) run the existing validation (show error labels if needed)
    //    - already have errFisherfolk2, errSpecies2, errQuantity2, errPrice2, errDockingTime2
    //    Reuse the exact validation from previous add code.

    // Validated and converted to:
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
    
        addNewLanding_popup.setVisible(false);
        landings_pane.setDisable(false);
        sideNavigation_vbox.setDisable(false);
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

    private Double parsePositiveDouble(String text) {
    if (text == null) return null;
    try {
        double v = Double.parseDouble(text.trim());
        return v > 0 ? v : null;
    } catch (NumberFormatException e) { return null; }
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
    private void generateReport(ActionEvent event) {
    }
    
    ////////////////////////////////////////////////////////////////////////////end of Landings

    ////////////////////////////////////////////////////////////////////////////FISHERMEN
    private void hideFisherErrors() {
        Fisherfolk_err.setVisible(false);
        gender_err.setVisible(false);
    }
    
    private void clearFisherForm() {
        fisherfolk_tf.clear();
        gender_cb.getSelectionModel().clearSelection();
        age_tf.clear();
        contact_tf.clear();
        address_tf.clear();
        Boat_tf.clear();
        LicenseNumber_tf.clear();
    }

    private void exitFisherPopup() {
        addNewFisherfolk_popup.setVisible(false);
        fishermen_pane.setDisable(false);
        sideNavigation_vbox.setDisable(false);
        clearFisherForm();
        hideFisherErrors();
        editingFisherId = null;
    }
    
    private void reloadFisherTable() {
        // If populate via FilteredList/SortedList:
        fisherData.setAll(mysqlconnect.loadFisherfolk()); 
    }
    
    private void updateToggleAppearance(javafx.scene.control.ToggleButton t, boolean active) {
        t.setText(active ? "Active" : "Inactive");
        // optional styling: t.setStyle(active ? "-fx-background-color:#16a34a;" : "-fx-background-color:#9ca3af;");
    }

    private void showInfoFISHERMEN(String msg) {
        var alert = new javafx.scene.control.Alert(
            javafx.scene.control.Alert.AlertType.INFORMATION,
            msg,
            javafx.scene.control.ButtonType.OK
        );
        alert.setHeaderText(null);

        // 🔑 Force larger size
//        alert.getDialogPane().setMinWidth(500);  // adjust to fit long text
//        alert.getDialogPane().setMinHeight(200);
        alert.getDialogPane().setExpandableContent(new javafx.scene.control.Label(msg));
        ((Label) alert.getDialogPane().getExpandableContent()).setWrapText(true);


        alert.showAndWait();
    }


    @FXML
    private void deleteFisherfolk(ActionEvent event) {
        FisherfolkRecord sel = fishermen_tv.getSelectionModel().getSelectedItem();
        if (sel == null) {
            showInfoFISHERMEN("Please select a fisherfolk to delete.");
            return;
        }

        int id = sel.getFisherfolkId();
        int catchCnt = mysqlconnect.countCatchByFisher(id);
        int txnCnt   = mysqlconnect.countTransactionsByFisher(id);

        if (catchCnt < 0 || txnCnt < 0) {
            showInfoFISHERMEN("Unable to check dependencies. Try again.");
            return;
        }

        if (catchCnt > 0 || txnCnt > 0) {
            String where = (catchCnt > 0 ? catchCnt + " in Landings" : "") +
                           ((catchCnt > 0 && txnCnt > 0) ? " and " : "") +
                           (txnCnt > 0 ? txnCnt + " in Transactions" : "");
            var alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.WARNING,
                "Cannot delete. This fisherfolk still has linked records (" + where + ").\n\n" +
                "Tip: Set status to Inactive instead, so they can no longer be used in new landings/transactions.",
                javafx.scene.control.ButtonType.OK);
            alert.setHeaderText("Delete blocked");
            // make the dialog bigger
//            alert.getDialogPane().setMinWidth(600);
//            alert.getDialogPane().setMinHeight(250);
            alert.getDialogPane().setExpandableContent(new javafx.scene.control.Label());
            ((Label) alert.getDialogPane().getExpandableContent()).setWrapText(true);

            alert.showAndWait();
            return;
        }

        var confirm = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.CONFIRMATION,
                "Delete this fisherfolk permanently?\nThis cannot be undone.",
                javafx.scene.control.ButtonType.YES, javafx.scene.control.ButtonType.NO);
        confirm.setHeaderText("Confirm delete");
        confirm.showAndWait();

        if (confirm.getResult() == javafx.scene.control.ButtonType.YES) {
            boolean ok = mysqlconnect.deleteFisherById(id);
            if (ok) {
                // remove from underlying source list (avoid UnsupportedOperationException on Sorted/Filtered list)
                fisherData.remove(sel);
                showInfoFISHERMEN("Fisherfolk deleted.");
            } else {
                showInfoFISHERMEN("Failed to delete. Please try again.");
            }
        }
    }

    @FXML
    private void updateFisherfolk(ActionEvent event) {
        FisherfolkRecord sel = fishermen_tv.getSelectionModel().getSelectedItem();
        if (sel == null) {
            showInfoWide("Please select a fisherfolk to update.");
            return;
        }

        editingFisherId = sel.getFisherfolkId();  // edit mode
        hideFisherErrors();

        // Prefill
        fisherfolk_tf.setText(nullToEmpty(sel.getName()));
        gender_cb.getSelectionModel().select(nullToEmpty(sel.getGender()));
        age_tf.setText(sel.getAge() == null ? "" : String.valueOf(sel.getAge()));
        contact_tf.setText(nullToEmpty(sel.getContactNumber()));
        address_tf.setText(nullToEmpty(sel.getAddress()));
        Boat_tf.setText(nullToEmpty(sel.getBoatName()));
        LicenseNumber_tf.setText(nullToEmpty(sel.getLicenseNumber()));

        addNewFisherfolk_popup.setVisible(true);
        fishermen_pane.setDisable(true);
        sideNavigation_vbox.setDisable(true);
    }

    private String nullToEmpty(String s) { return s == null ? "" : s; }


    @FXML
    private void addFisherfolk(ActionEvent event) {
        addNewFisherfolk_popup.setVisible(true);
        fishermen_pane.setDisable(true);
        sideNavigation_vbox.setDisable(true);
        
        editingFisherId = null;  // add mode
        clearFisherForm();
        hideFisherErrors();

    }

    @FXML
    private void btnClear_onAddFisherfolk(ActionEvent event) {
        clearFisherForm();
        hideFisherErrors();
    }

    @FXML
    private void btnCancel_onAddFisherfolk(ActionEvent event) {
        exitFisherPopup();
    }

    @FXML
    private void btnSave_onAddFisherfolk(ActionEvent event) {
        hideFisherErrors();

        // --- validation ---
        boolean valid = true;

        String name = safeTrim(fisherfolk_tf.getText());
        if (name == null) { Fisherfolk_err.setVisible(true); valid = false; }

        String gender = gender_cb.getValue();
        if (gender == null || gender.isBlank()) { gender_err.setVisible(true); valid = false; }

        Integer age = parsePositiveIntOrNull(age_tf.getText()); // optional
        String contact = safeTrim(contact_tf.getText());         // optional
        String address = safeTrim(address_tf.getText());         // optional
        String boat = safeTrim(Boat_tf.getText());               // optional
        String license = safeTrim(LicenseNumber_tf.getText());   // optional

        if (!valid) return;

        // --- SQL ---
        if (editingFisherId == null) {
            // INSERT (default is_active = 1 for new)
            final String sql = "INSERT INTO fisherfolk " +
                    "(name, gender, age, contact_number, address, boat_name, license_number, is_active) " +
                    "VALUES (?,?,?,?,?,?,?,1)";
            try (var conn = mysqlconnect.ConnectDb();
                 var ps = conn.prepareStatement(sql)) {

                ps.setString(1, name);
                ps.setString(2, gender);
                if (age != null) ps.setInt(3, age); else ps.setNull(3, java.sql.Types.INTEGER);
                if (contact != null) ps.setString(4, contact); else ps.setNull(4, java.sql.Types.VARCHAR);
                if (address != null) ps.setString(5, address); else ps.setNull(5, java.sql.Types.VARCHAR);
                if (boat != null) ps.setString(6, boat); else ps.setNull(6, java.sql.Types.VARCHAR);
                if (license != null) ps.setString(7, license); else ps.setNull(7, java.sql.Types.VARCHAR);

                int rows = ps.executeUpdate();
                if (rows > 0) {
                    showInfoWide("Fisherfolk saved.");
                    exitFisherPopup();
                    reloadFisherTable();
                } else {
                    showInfoWide("Nothing was saved.");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                showInfoWide("Error saving: " + ex.getMessage());
            }

        } else {
            // UPDATE (do not touch is_active here)
            final String sql = "UPDATE fisherfolk SET " +
                    "name=?, gender=?, age=?, contact_number=?, address=?, boat_name=?, license_number=? " +
                    "WHERE fisherfolk_id=?";
            try (var conn = mysqlconnect.ConnectDb();
                 var ps = conn.prepareStatement(sql)) {

                ps.setString(1, name);
                ps.setString(2, gender);
                if (age != null) ps.setInt(3, age); else ps.setNull(3, java.sql.Types.INTEGER);
                if (contact != null) ps.setString(4, contact); else ps.setNull(4, java.sql.Types.VARCHAR);
                if (address != null) ps.setString(5, address); else ps.setNull(5, java.sql.Types.VARCHAR);
                if (boat != null) ps.setString(6, boat); else ps.setNull(6, java.sql.Types.VARCHAR);
                if (license != null) ps.setString(7, license); else ps.setNull(7, java.sql.Types.VARCHAR);
                ps.setInt(8, editingFisherId);

                int rows = ps.executeUpdate();
                if (rows > 0) {
                    showInfoWide("Fisherfolk updated.");
                    exitFisherPopup();
                    reloadFisherTable();
                } else {
                    showInfoWide("No changes were applied.");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                showInfoWide("Error updating: " + ex.getMessage());
            }
        }
    }

    private String safeTrim(String t) {
        if (t == null) return null;
        String s = t.trim();
        return s.isEmpty() ? null : s;
    }

    private Integer parsePositiveIntOrNull(String txt) {
        if (txt == null || txt.trim().isEmpty()) return null;
        try {
            int v = Integer.parseInt(txt.trim());
            return v >= 0 ? v : null; // allow 0 if you want
        } catch (NumberFormatException e) { return null; }
    }
    
    private void showInfoWide(String msg) {
        var a = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION, msg, javafx.scene.control.ButtonType.OK);
        a.setHeaderText(null);
        a.getDialogPane().setMinWidth(560);
        a.getDialogPane().setMinHeight(180);
        a.showAndWait();
    }

    @FXML
    private void viewCatches(ActionEvent e) {
        FisherfolkRecord sel = fishermen_tv.getSelectionModel().getSelectedItem();
        if (sel == null) {
            showInfoWide("Please select a fisherfolk to view history.");
            return;
        }

        int fisherId = sel.getFisherfolkId();

        // load all 3 lists
        catch_tv1.setItems(mysqlconnect.getCatchesByFisher(fisherId));
        txn_tv.setItems(mysqlconnect.getTransactionsByFisher(fisherId));
        dock_tv.setItems(mysqlconnect.getDockLogsByFisher(fisherId));

        // resize columns after data is loaded
//        autoResizeColumns(catch_tv1);
//        autoResizeColumns(txn_tv);
//        autoResizeColumns(dock_tv);
        
        viewFisherHistory_popup.setVisible(true);
        history_tabpane.getSelectionModel().select(0); // open on "Catches"
        
        fishermen_pane.setDisable(true);
        sideNavigation_vbox.setDisable(true);
        
    }
  
    @FXML
    private void viewTransaction(ActionEvent event) {
        FisherfolkRecord sel = fishermen_tv.getSelectionModel().getSelectedItem();
        if (sel == null) {
            showInfoWide("Please select a fisherfolk to view history.");
            return;
        }

        int fisherId = sel.getFisherfolkId();

        // load all 3 lists
        catch_tv1.setItems(mysqlconnect.getCatchesByFisher(fisherId));
        txn_tv.setItems(mysqlconnect.getTransactionsByFisher(fisherId));
        dock_tv.setItems(mysqlconnect.getDockLogsByFisher(fisherId));

        // resize columns after data is loaded
//        autoResizeColumns(catch_tv1);
//        autoResizeColumns(txn_tv);
//        autoResizeColumns(dock_tv);
        
        viewFisherHistory_popup.setVisible(true);
        history_tabpane.getSelectionModel().select(1); // open on "Transactions"
        
        fishermen_pane.setDisable(true);
        sideNavigation_vbox.setDisable(true);
        
    }

    @FXML
    private void viewDockLogs(ActionEvent event) {
        FisherfolkRecord sel = fishermen_tv.getSelectionModel().getSelectedItem();
        if (sel == null) {
            showInfoWide("Please select a fisherfolk to view history.");
            return;
        }

        int fisherId = sel.getFisherfolkId();

        // load all 3 lists
        catch_tv1.setItems(mysqlconnect.getCatchesByFisher(fisherId));
        txn_tv.setItems(mysqlconnect.getTransactionsByFisher(fisherId));
        dock_tv.setItems(mysqlconnect.getDockLogsByFisher(fisherId));
        
        // resize columns after data is loaded
//        autoResizeColumns(catch_tv1);
//        autoResizeColumns(txn_tv);
//        autoResizeColumns(dock_tv);

        viewFisherHistory_popup.setVisible(true);
        history_tabpane.getSelectionModel().select(2); // open on "Dock Logs"
        
        fishermen_pane.setDisable(true);
        sideNavigation_vbox.setDisable(true);
        
    }
    
    @FXML
    private void export_fisherfolk_list(ActionEvent event) {
        String filename = "fisherfolk_export_" + java.time.LocalDate.now() + ".csv";
        try (var pw = new java.io.PrintWriter(filename, java.nio.charset.StandardCharsets.UTF_8)) {
            pw.println("ID,Name,Age,Gender,Contact,Address,Boat,License,Active");
            for (FisherfolkRecord f : fisherSorted) {
                pw.printf("%d,%s,%s,%s,%s,%s,%s,%s,%s%n",
                    f.getFisherfolkId(),
                    csv(f.getName()),
                    f.getAge() == null ? "" : f.getAge().toString(),
                    csv(f.getGender()),
                    csv(f.getContactNumber()),
                    csv(f.getAddress()),
                    csv(f.getBoatName()),
                    csv(f.getLicenseNumber()),
                    f.isActive() ? "Active" : "Inactive"
                );
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            showInfoFISHERMEN("Export failed: " + ex.getMessage());
            return;
        }
//        showInfoFISHERMEN("Exported: " + new java.io.File(filename).getAbsolutePath());
        String path = new java.io.File(filename).getAbsolutePath();
        TextArea textArea = new TextArea("Exported: " + path);
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Export Success");
        alert.getDialogPane().setContent(textArea);
        alert.getDialogPane().setMinWidth(600);

        alert.showAndWait();

    }

    //ALSO USED IN TRANSACTION & SALES (for generate sales report)
    private String csv(String s) {
        if (s == null) return "";
        String v = s.replace("\"","\"\"");
        return "\"" + v + "\"";
    }

    @FXML
    private void EXIT_fisherHistory(ActionEvent event) {
        viewFisherHistory_popup.setVisible(false);
        fishermen_pane.setDisable(false);
        sideNavigation_vbox.setDisable(false);
        
    }
    
    // ---------- small helpers for formatting fishermen history (3 tabs) ----------
    private <T> void setDateFormat(TableColumn<T, java.time.LocalDate> col, String pattern) {
        col.setCellFactory(tc -> new javafx.scene.control.TableCell<>() {
            @Override protected void updateItem(java.time.LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.format(java.time.format.DateTimeFormatter.ofPattern(pattern)));
            }
        });
    }

    private <T> void setTimeFormat(TableColumn<T, java.time.LocalTime> col, String pattern) {
        col.setCellFactory(tc -> new javafx.scene.control.TableCell<>() {
            @Override protected void updateItem(java.time.LocalTime item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.format(java.time.format.DateTimeFormatter.ofPattern(pattern)));
            }
        });
    }
    //USED BY TRANSACTION & SALES ALSO
    private <T> void setNumeric2dp(TableColumn<T, Number> col) {
        col.setCellFactory(tc -> new javafx.scene.control.TableCell<>() {
            @Override protected void updateItem(Number item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : String.format("%.2f", item.doubleValue()));
                setAlignment(javafx.geometry.Pos.CENTER_RIGHT);//added (transactions)
            }
        });
    }
    //USED BY TRANSACTION & SALES ALSO
    private <T> void setCurrencyPeso(TableColumn<T, Number> col) {
        col.setCellFactory(tc -> new javafx.scene.control.TableCell<>() {
            final java.text.NumberFormat fmt;
            {
                // create once per cell
                fmt = java.text.NumberFormat.getCurrencyInstance(LOCALE_PH);
                fmt.setCurrency(java.util.Currency.getInstance("PHP")); // ensure ₱
                fmt.setMaximumFractionDigits(2);
                fmt.setMinimumFractionDigits(2);
            }
            @Override protected void updateItem(Number item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : fmt.format(item.doubleValue()));
                setAlignment(javafx.geometry.Pos.CENTER_RIGHT);
            }
        });
    }

    private void autoResizeColumns(TableView<?> table) {
        for (TableColumn<?, ?> column : table.getColumns()) {
            column.setPrefWidth(USE_COMPUTED_SIZE);
            column.setMinWidth(80); // keep at least 80 px
        }
    }

    ////////////////////////////////////////////////////////////////////////////end of fishermen
    
    ////////////////////////////////////////////////////////////////////////////TRANSACTION AND SALES
    @FXML
    private void transaction_generateReport(ActionEvent event) {
        final String statusFilter =
            paid_transacStatus.isSelected() ? "Paid" :
            unpaid_transacStatus.isSelected() ? "Unpaid" :
            partial_transacStatus.isSelected() ? "Partial" : "All";
        final java.time.LocalDate start = dpStartDate_transac.getValue();
        final java.time.LocalDate end   = dpEndDate_transac.getValue();

        String datePart =
            (start == null && end == null) ? "AllDates" :
            (start != null && end != null) ? (start + "_to_" + end) :
            (start != null ? ("From_" + start) : ("Until_" + end));

        String suggested = String.format("sales_%s_%s_%s.csv",
                statusFilter, datePart, java.time.LocalDateTime.now().format(TS_FMT));

        javafx.stage.FileChooser fc = new javafx.stage.FileChooser();
        fc.setTitle("Save Sales Report");
        fc.getExtensionFilters().add(new javafx.stage.FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv"));
        fc.setInitialFileName(suggested);
        // optional: choose a default directory
        // fc.setInitialDirectory(new java.io.File(System.getProperty("user.home"), "Documents"));

        java.io.File file = fc.showSaveDialog(transactionANDsales_pane.getScene().getWindow());
        if (file == null) return; // user cancelled

        // ensure .csv extension
        if (!file.getName().toLowerCase().endsWith(".csv")) {
            file = new java.io.File(file.getParentFile(), file.getName() + ".csv");
        }
        exportTransactionsCsv(file);
    }
    
    private void exportTransactionsCsv(java.io.File file) {
        try (var pw = new java.io.PrintWriter(file, java.nio.charset.StandardCharsets.UTF_8)) {
            pw.println("ID,DateTime,Buyer,Fisherfolk,Fish Type,Qty,Unit Price,Total,Payment Method,Status");
            for (var r : transSorted) { // current filtered+sorted rows
                pw.printf("%d,%s,%s,%s,%s,%.2f,%.2f,%.2f,%s,%s%n",
                    r.getTransactionId(),
                    r.getTxnDate() == null ? "" : r.getTxnDate().toString(),
                    csv(r.getBuyerName()),
                    csv(r.getFisherfolkName()),
                    csv(r.getSpeciesName()),
                    r.getQtySold(),
                    r.getUnitPrice(),
                    r.getTotalPrice(),
                    csv(r.getPaymentMethod()),
                    csv(r.getPaymentStatus())
                );
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            showInfoWide("Export failed: " + ex.getMessage());
            return;
        }
        showInfoWide("Report saved: " + file.getAbsolutePath());
    }

    @FXML
    private void addTransaction(ActionEvent event) {
        Quantity_err.setText("Please enter quantity");
        
        transactionUpdateMode = false;
        editingTransactionId = null;

        // clear fields
        hideErrors();
        transacBuyer_tf.clear();
        transacUnitPrice_tf.clear();
        transacQuantity_tf.clear();
        transacRemarks_ta.clear();
        transacRemainingQuantity_label.setText("");
        transacTotalAmount_label.setText(PHP.format(0));

        // combos fresh
        transacSeller_cb.getSelectionModel().clearSelection();
        transacFishType_cb.getItems().clear();
        transacPaymentMethod_cb.getSelectionModel().clearSelection();
        transacPaymentStatus_cb.getSelectionModel().clearSelection();

        // editable everything
        transacSeller_cb.setDisable(false);
        transacFishType_cb.setDisable(false);
        transacUnitPrice_tf.setEditable(true);

        addNewTransaction_popup.setVisible(true);
        // disable background panes 
        sideNavigation_vbox.setDisable(true);
        transactionANDsales_pane.setDisable(true);
    }


    @FXML
    private void updatePaymentStatus(ActionEvent event) {
        Quantity_err.setText("Please enter quantity");
       
        var sel = transaction_tv.getSelectionModel().getSelectedItem();
        if (sel == null) {
            showInfoWide("Please select a transaction to update.");
            return;
        }

        transactionUpdateMode = true;
        editingTransactionId = sel.getTransactionId();
        updateCatchId = sel.getCatchId();  // <-- keep the catch id for validation
        hideErrors();

        // Pre-fill fields
        transacBuyer_tf.setText(sel.getBuyerName());
        transacQuantity_tf.setText(String.format("%.2f", sel.getQtySold()));
        transacUnitPrice_tf.setText(String.format("%.2f", sel.getUnitPrice()));
        transacRemarks_ta.setText(""); // you can load actual remarks if your view includes it
        transacPaymentMethod_cb.getSelectionModel().select(sel.getPaymentMethod());
        transacPaymentStatus_cb.getSelectionModel().select(sel.getPaymentStatus());

        // Seller + FishType (lock them; show current)
        // We need to put the single item into combos so user sees them.
        transacSeller_cb.getItems().setAll(new FisherfolkItem(0, sel.getFisherfolkName())); // id not used while locked
        transacSeller_cb.getSelectionModel().select(0);
        transacSeller_cb.setDisable(true);

        transacFishType_cb.getItems().setAll(
            new CatchOption(0, 0, 0, sel.getSpeciesName(), sel.getUnitPrice(), 0, 
                            sel.getTxnDate() != null ? sel.getTxnDate().toLocalDate() : null));
        transacFishType_cb.getSelectionModel().select(0);
        transacFishType_cb.setDisable(true);

        // Remaining label is informational only in update mode
        transacRemainingQuantity_label.setText("");

        updateTotalLabel();
        
        addNewTransaction_popup.setVisible(true);
        sideNavigation_vbox.setDisable(true);
        transactionANDsales_pane.setDisable(true);
    }


    @FXML
    private void deleteTransaction(ActionEvent event) {
        var sel = transaction_tv.getSelectionModel().getSelectedItem();
        if (sel == null) {
            showInfoWide("Please select a transaction to delete.");
            return;
        }

        var confirm = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.CONFIRMATION,
                "Delete this transaction? This cannot be undone.",
                javafx.scene.control.ButtonType.YES, javafx.scene.control.ButtonType.NO);
        confirm.setHeaderText("Confirm delete");
        confirm.getDialogPane().setMinWidth(520);
        confirm.showAndWait();

        if (confirm.getResult() == javafx.scene.control.ButtonType.YES) {
            boolean ok = mysqlconnect.deleteTransactionById(sel.getTransactionId());
            if (ok) {
                // remove from the source list (not the Sorted/Filtered wrapper)
                transData.remove(sel);
                updateTransHeaderKpis();
                showInfoWide("Transaction deleted.");
            } else {
                showInfoWide("Failed to delete. Please try again.");
            }
        }
    }

    @FXML
    private void onClearFilterDate_transac(ActionEvent event) {
        dpStartDate_transac.setValue(null);
        dpEndDate_transac.setValue(null);
        applyTransFilters();
    }

    private <S> void setDateTimeFormat(TableColumn<S, java.time.LocalDateTime> col, String pattern) {
        var fmt = java.time.format.DateTimeFormatter.ofPattern(pattern);
        col.setCellFactory(tc -> new javafx.scene.control.TableCell<>() {
            @Override protected void updateItem(java.time.LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.format(fmt));
            }
        });
    }

    //KPI FILTERS
    private void applyTransFilters() {
        // snapshot everything the lambda will use
        final String query = (filterField_transactions.getText() == null)
                ? "" : filterField_transactions.getText().trim().toLowerCase();

        final String statusFilter;
        if (paid_transacStatus.isSelected())       statusFilter = "Paid";
        else if (unpaid_transacStatus.isSelected()) statusFilter = "Unpaid";
        else if (partial_transacStatus.isSelected())statusFilter = "Partial";
        else                                        statusFilter = null;  // ALL

        final java.time.LocalDate startDate = dpStartDate_transac.getValue();
        final java.time.LocalDate endDate   = dpEndDate_transac.getValue();

        transFiltered.setPredicate(row -> {
            if (row == null) return false;

            // search buyer/fisherfolk
            if (!query.isEmpty()) {
                String buyer  = row.getBuyerName() == null ? "" : row.getBuyerName().toLowerCase();
                String fisher = row.getFisherfolkName() == null ? "" : row.getFisherfolkName().toLowerCase();
                if (!(buyer.contains(query) || fisher.contains(query))) return false;
            }

            // status filter
            if (statusFilter != null) {
                String ps = row.getPaymentStatus();
                if (ps == null || !ps.equalsIgnoreCase(statusFilter)) return false;
            }

            // date range (use LocalDate part of timestamp)
            if (startDate != null || endDate != null) {
                if (row.getTxnDate() == null) return false;
                var d = row.getTxnDate().toLocalDate();
                if (startDate != null && d.isBefore(startDate)) return false;
                if (endDate   != null && d.isAfter(endDate))     return false;
            }

            return true;
        });

        updateTransHeaderKpis(); // recalc totals after filtering
    }

    private void updateTransHeaderKpis() {
        double totalPaid = 0, totalPendingAmount = 0, revenueToday = 0;
        int countPaid = 0, countPending = 0;

        var today = java.time.LocalDate.now();

        for (var r : transFiltered) {
            if ("Paid".equalsIgnoreCase(r.getPaymentStatus())) {
                totalPaid += r.getTotalPrice();
                countPaid++;
                if (r.getTxnDate() != null && r.getTxnDate().toLocalDate().equals(today)) {
                    revenueToday += r.getTotalPrice();
                }
            } else if ("Partial".equalsIgnoreCase(r.getPaymentStatus()) ||
                       "Unpaid".equalsIgnoreCase(r.getPaymentStatus())) {
                totalPendingAmount += r.getTotalPrice();
                countPending++;
            }
        }

        var peso = java.text.NumberFormat.getCurrencyInstance(LOCALE_PH);
        peso.setCurrency(java.util.Currency.getInstance("PHP"));

        totalPaidAmount_transacLabel.setText(peso.format(totalPaid));
        totalPendingAmount_transacLabel.setText(peso.format(totalPendingAmount));
        totalRevenueToday_transacLabel.setText(peso.format(revenueToday));
        TotalPaidOrders_transacLabel.setText("Order: "+String.valueOf(countPaid));
        TotalPartialUnpaidOrders_transacLabel.setText("Order: "+String.valueOf(countPending));
    }
    
    //HELPERS
    private void makeNumeric(TextField tf) {
        tf.textProperty().addListener((obs, oldV, newV) -> {
            if (newV == null || newV.isEmpty()) return;
            if (!newV.matches("\\d*(\\.\\d{0,2})?")) tf.setText(oldV); // up to 2 decimals
        });
    }

    private double d(TextField tf) {
        try { return Double.parseDouble(tf.getText()); } catch (Exception e) { return 0.0; }
    }

    private void updateTotalLabel() {
        double qty = d(transacQuantity_tf);
        double unit = d(transacUnitPrice_tf);
        transacTotalAmount_label.setText(PHP.format(qty * unit));
    }

    private void hideErrors() {
        Seller_err.setVisible(false);
        FishType_err.setVisible(false);
        Buyer_err.setVisible(false);
        PMethod_err.setVisible(false);
        Quantity_err.setVisible(false);
        PStatus_err.setVisible(false);
        price_err.setVisible(false);
    }
    
    private void refreshTransactionsTable() {
        var fresh = mysqlconnect.loadTransactionsView();
        // If you use Filtered/Sorted lists, update the source list:
         transData.setAll(fresh);
        // Otherwise:
//        transaction_tv.setItems(fresh);
        // Re-apply filters if you have them, then recompute header KPIs:
         applyTransFilters();
    }
    
    private void restoreAddModeCombos() {
        // restore full sellers list
        allSellers = mysqlconnect.loadActiveFisherfolkItems();
        transacSeller_cb.setItems(allSellers);
        transacSeller_cb.setDisable(false);
        transacSeller_cb.getSelectionModel().clearSelection();

        // fish types list is empty until a seller is chosen
        transacFishType_cb.getItems().clear();
        transacFishType_cb.setDisable(false);
        transacFishType_cb.getSelectionModel().clearSelection();
    }
    
    private void exitTransactionPopup() {
        addNewTransaction_popup.setVisible(false);
        transactionANDsales_pane.setDisable(false);
        sideNavigation_vbox.setDisable(false);
        
        // unlock
        transacSeller_cb.setDisable(false);
        transacFishType_cb.setDisable(false);

        // clear selections/fields for next Add
        transacSeller_cb.getSelectionModel().clearSelection();
        transacFishType_cb.getItems().clear();       // will repopulate after seller is picked
        transacBuyer_tf.clear();
        transacQuantity_tf.clear();
        transacUnitPrice_tf.clear();
        transacRemarks_ta.clear();
        transacPaymentMethod_cb.getSelectionModel().clearSelection();
        transacPaymentStatus_cb.getSelectionModel().clearSelection();
        transacRemainingQuantity_label.setText("");
        transacTotalAmount_label.setText("₱0.00");

        transactionUpdateMode = false;
        editingTransactionId = null;
        currentCatchOpt = null;
    }



   @FXML
    private void btnCancel_onAddTransaction(ActionEvent event) {
        restoreAddModeCombos();
        exitTransactionPopup();
    }

    @FXML
    private void btnClear_onAddTransaction(ActionEvent event) {
        if (transactionUpdateMode) {
            // In update, maybe keep combos locked but clear editable fields
            transacBuyer_tf.clear();
            transacQuantity_tf.clear();
            transacUnitPrice_tf.clear();
            transacRemarks_ta.clear();
            transacPaymentMethod_cb.getSelectionModel().clearSelection();
            transacPaymentStatus_cb.getSelectionModel().clearSelection();
        } else {
            addTransaction(null); // reuse add-mode init
        }
    }

 
    @FXML
    private void btnSave_onAddTransaction(ActionEvent event) {
        hideErrors();

        // validate common fields
        String buyer = transacBuyer_tf.getText() == null ? "" : transacBuyer_tf.getText().trim();
        if (buyer.isEmpty()) { Buyer_err.setVisible(true); return; }

        String pm = transacPaymentMethod_cb.getValue();
        if (pm == null || pm.isBlank()) { PMethod_err.setVisible(true); return; }

        String ps = transacPaymentStatus_cb.getValue();
        if (ps == null || ps.isBlank()) { PStatus_err.setVisible(true); return; }

        double qty = d(transacQuantity_tf);
        if (qty <= 0) { Quantity_err.setVisible(true); return; }

        double unit = d(transacUnitPrice_tf);
        if (unit <= 0) { price_err.setVisible(true); return; }

        String remarks = transacRemarks_ta.getText();

        boolean ok;

        if (!transactionUpdateMode) {
            // ADD: need valid seller + catch
            FisherfolkItem seller = transacSeller_cb.getValue();
            if (seller == null) { Seller_err.setVisible(true); return; }

            CatchOption co = transacFishType_cb.getValue();
            if (co == null) { FishType_err.setVisible(true); return; }

            // client-side oversell check
            if (qty > co.getRemainingQty() + 1e-6) {
                Quantity_err.setText("Quantity exceeds remaining.");
                Quantity_err.setVisible(true);
                return;
            }

            ok = mysqlconnect.insertTransaction(
                    buyer, seller.getId(), co.getCatchId(),
                    qty, unit, pm, ps, remarks
                    
            );
                
                restoreAddModeCombos();
                exitTransactionPopup();
                
        } else {
            // UPDATE: keep catch the same (locked)
            if (editingTransactionId == null || updateCatchId == null) {
                showInfoWide("No transaction selected.");
                return;
            }

            // validate against remaining (excluding this transaction)
            int txnId   = editingTransactionId;
            int catchId = updateCatchId;

            double newQty = d(transacQuantity_tf);

            double catchQty     = mysqlconnect.getCatchQuantity(catchId);
            double soldElse     = mysqlconnect.sumSoldForCatchExcluding(txnId, catchId);
            double remaining    = catchQty - soldElse; // what the user may still take

            if (newQty > remaining + 1e-9) {
                Quantity_err.setText("Exceeds remaining (" + String.format("%.2f", remaining) + " kg left).");
                Quantity_err.setVisible(true);
                return;
            }

            ok = mysqlconnect.updateTransaction(
                    editingTransactionId, buyer, newQty, unit, pm, ps, remarks
            );
        }
        
        if (ok) {
            showInfoWide(transactionUpdateMode ? "Transaction updated." : "Transaction added.");
            // reset error label back to default text for next time
            Quantity_err.setText("Please enter quantity");

            addNewTransaction_popup.setVisible(false);
            refreshTransactionsTable();

            // reset flags and UI state
            transactionUpdateMode = false;
            editingTransactionId = null;
            updateCatchId = null;

            restoreAddModeCombos();
            exitTransactionPopup();
        } else {
            showInfoWide("Save failed. Please check your inputs.");
        }
        refreshTransactionsTable();
    }
    ////////////////////////////////////////////////////////////////////////////end of transaction & sales
    
    ////////////////////////////////////////////////////////////////////////////DOCK LOGS
   @FXML
    private void dockLog_generateReport(ActionEvent e) {
        // Export the CURRENTLY FILTERED rows
        java.util.List<DockLogViewRow> rows = new java.util.ArrayList<>(dockFiltered);

        if (rows.isEmpty()) {
            showInfoWide("Nothing to export.");
            return;
        }

        String ts = java.time.LocalDateTime.now()
            .format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String defaultName = "dock_logs_" + ts + ".csv";

        // optional: let user choose location
        javafx.stage.FileChooser fc = new javafx.stage.FileChooser();
        fc.setTitle("Save Dock Logs Report");
        fc.getExtensionFilters().add(new javafx.stage.FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        fc.setInitialFileName(defaultName);
        java.io.File file = fc.showSaveDialog(dockLogs_tv.getScene().getWindow());
        if (file == null) return;

        try (var pw = new java.io.PrintWriter(file, java.nio.charset.StandardCharsets.UTF_8)) {
            pw.println("LogID,Fisherfolk,Boat,DockingDate,Arrival,Departure,Remarks");
            for (var r : rows) {
                pw.printf("%d,%s,%s,%s,%s,%s,%s%n",
                        r.getLogId(),
                        csv(r.getFisherfolkName()),
                        csv(r.getBoatName()),
                        r.getDockingDate(),
                        r.getArrivalTime() == null ? "" : r.getArrivalTime(),
                        r.getDepartureTime() == null ? "" : r.getDepartureTime(),
                        csv(r.getRemarks())
                );
            }
            showInfoWide("Exported: " + file.getAbsolutePath());
        } catch (Exception ex) {
            ex.printStackTrace();
            showInfoWide("Export failed: " + ex.getMessage());
        }
    }

    @FXML
    private void deleteDockLog(ActionEvent event) {
        DockLogViewRow sel = dockLogs_tv.getSelectionModel().getSelectedItem();
        if (sel == null) {
            showInfoWide("Please select a dock log to delete.");
            return;
        }
        var confirm = new javafx.scene.control.Alert(
            javafx.scene.control.Alert.AlertType.CONFIRMATION,
            "Delete this dock log for " + sel.getFisherfolkName() + " on " + sel.getDockingDate() + "?",
            javafx.scene.control.ButtonType.YES, javafx.scene.control.ButtonType.NO);
        confirm.setHeaderText("Confirm delete");
        confirm.showAndWait();
        if (confirm.getResult() != javafx.scene.control.ButtonType.YES) return;

        boolean ok = mysqlconnect.deleteDockLogById(sel.getLogId());
        if (ok) {
            dockData.remove(sel);
            refreshDockStats();
            showInfoWide("Dock log deleted.");
            refreshDockLogsTable();
        } else {
            showInfoWide("Delete failed.");
        }
    }

    @FXML
    private void onClearFilterDate_DockLog(ActionEvent event) {
        dpStartDate_dockLog.setValue(null);
        dpEndDate_dockLog.setValue(null);
        applyDockFilters();
    }

    private void applyDockFilters() {
        final String q = (filterField_dockLogs.getText() == null) ? "" : filterField_dockLogs.getText().trim().toLowerCase();
        final java.time.LocalDate start = dpStartDate_dockLog.getValue();
        final java.time.LocalDate end   = dpEndDate_dockLog.getValue();

        dockFiltered.setPredicate(row -> {
            // search fisherfolk or boat
            if (!q.isEmpty()) {
                String hay = (row.getFisherfolkName() + " " + (row.getBoatName()==null?"":row.getBoatName())).toLowerCase();
                if (!hay.contains(q)) return false;
            }
            // date range
            if (start != null && row.getDockingDate().isBefore(start)) return false;
            if (end != null && row.getDockingDate().isAfter(end)) return false;
            return true;
        });

        refreshDockStats(); // update labels after filter
    }

    private void refreshDockStats() {
        // label: dockings today (from DB)
        totalDockToday_label.setText(String.valueOf(mysqlconnect.countDockingsToday()));

        // label: most active fisher last 7 days (from DB)
        mostActiveLast7days_label.setText(mysqlconnect.mostActiveFisherLast7Days());
    }

    @FXML
    private void addDockLog(ActionEvent event) {
        dockUpdateMode = false;
        editingDockLogId = null;

        hideDockErrors();

        // load active fisherfolk (id, name, boat)
        dockLogFisherfolk_cb.setDisable(false);
        dockLogFisherfolk_cb.setItems(mysqlconnect.loadActiveFisherfolkItems()); // ObservableList<FisherfolkItem>
        dockLogFisherfolk_cb.getSelectionModel().clearSelection();
        dockLogBoatName_label.setText("—");

        // default date = today, editable
        dockLogDate_dp.setValue(java.time.LocalDate.now());
        dockLogDate_dp.setDisable(false);

        // times empty; arrival required, departure optional on ADD
        dockLogArrivalTime_tf.clear();
        dockLogArrivalTime_tf.setPromptText("HH:mm");
        dockLogArrivalTime_tf.setDisable(false);

        dockLogDepartureTime_tf.clear();
        dockLogDepartureTime_tf.setPromptText("HH:mm (optional)");
        dockLogDepartureTime_tf.setDisable(false);

        dockLogRemarks_ta.clear();

        // show popup, disable background panes
        addNewDockLog_popup.setVisible(true);
        dockingLogs_pane.setDisable(true);
        sideNavigation_vbox.setDisable(true);
    }

    @FXML
    private void updateDockLogDeparture(ActionEvent event) {
        var sel = dockLogs_tv.getSelectionModel().getSelectedItem();
        if (sel == null) { showInfoWide("Please select a dock log to update."); return; }

        dockUpdateMode = true;
        editingDockLogId = sel.getLogId();

        hideDockErrors();

        // fill fields from selected row, but lock fisherfolk/date/arrival
        var item = new FisherfolkItem(sel.getFisherfolkId(), sel.getFisherfolkName(), sel.getBoatName());
        dockLogFisherfolk_cb.getItems().setAll(item);
        dockLogFisherfolk_cb.getSelectionModel().select(0);
        dockLogFisherfolk_cb.setDisable(true);

        dockLogBoatName_label.setText(item.getBoatName() == null ? "—" : item.getBoatName());

        dockLogDate_dp.setValue(sel.getDockingDate());
        dockLogDate_dp.setDisable(true);

        dockLogArrivalTime_tf.setText(sel.getArrivalTime() == null ? "" : sel.getArrivalTime().toString());
        dockLogArrivalTime_tf.setDisable(true);

        dockLogDepartureTime_tf.setText(sel.getDepartureTime() == null ? "" : sel.getDepartureTime().toString());
        dockLogDepartureTime_tf.setPromptText("HH:mm");
        dockLogDepartureTime_tf.setDisable(false); // this is what we are updating

        dockLogRemarks_ta.setText(sel.getRemarks() == null ? "" : sel.getRemarks());

        addNewDockLog_popup.setVisible(true);
        dockingLogs_pane.setDisable(true);
        sideNavigation_vbox.setDisable(true);
    }
 
    @FXML
    private void btnCancel_onAddDockLog(ActionEvent event) {
        addNewDockLog_popup.setVisible(false);
        dockingLogs_pane.setDisable(false);
        sideNavigation_vbox.setDisable(false);
        dockUpdateMode = false;
        editingDockLogId = null;
    }

    @FXML
    private void btnClear_onAddDockLog(ActionEvent event) {
        hideDockErrors();
        if (dockUpdateMode) {
            // only editable: departure + remarks in update mode
            dockLogDepartureTime_tf.clear();
            dockLogRemarks_ta.clear();
        } else {
            dockLogFisherfolk_cb.getSelectionModel().clearSelection();
            dockLogBoatName_label.setText("—");
            dockLogDate_dp.setValue(java.time.LocalDate.now());
            dockLogArrivalTime_tf.clear();
            dockLogDepartureTime_tf.clear();
            dockLogRemarks_ta.clear();
        }
    }

    @FXML
    private void btnSave_onAddDockLog(ActionEvent event) {
        hideDockErrors();

        if (!dockUpdateMode) {
            // -------- ADD MODE --------
            FisherfolkItem fi = (FisherfolkItem) dockLogFisherfolk_cb.getValue();
            if (fi == null) { dockLogFisherfolk_err.setVisible(true); return; }

            var date = dockLogDate_dp.getValue();
            if (date == null) { dockLogDockDate_err.setVisible(true); return; }

            var arr = parseTime(dockLogArrivalTime_tf.getText());
            if (arr == null) { dockLogArrivalTime_err.setText("Use 24h HH:mm"); dockLogArrivalTime_err.setVisible(true); return; }

            // departure optional
            var depText = dockLogDepartureTime_tf.getText();
            java.time.LocalTime dep = (depText == null || depText.isBlank()) ? null : parseTime(depText);
            if (depText != null && !depText.isBlank() && dep == null) {
                dockLogArrivalTime_err.setText(""); // keep arrival msg clean
                // reuse same label or add another label for departure
                showInfoWide("Invalid departure time. Use 24h HH:mm.");
                return;
            }
            if (dep != null && !dep.isAfter(arr)) {
                showInfoWide("Departure must be after arrival.");
                return;
            }

            String remarks = dockLogRemarks_ta.getText();

            boolean ok = mysqlconnect.insertDockLog(fi.getId(), date, arr, dep, remarks);
            if (ok) {
                showInfoWide("Dock log added.");
                addNewDockLog_popup.setVisible(false);
                dockingLogs_pane.setDisable(false);
                sideNavigation_vbox.setDisable(false);
                dockUpdateMode = false;
                editingDockLogId = null;
                refreshDockLogsTable(); // reload table
            } else {
                showInfoWide("Save failed.");
            }

        } else {
            // -------- UPDATE-DEPARTURE MODE --------
            if (editingDockLogId == null) { showInfoWide("Nothing to update."); return; }

            // Only departure time is required here; remarks optional
            var depText = dockLogDepartureTime_tf.getText();
            if (depText == null || depText.isBlank()) {
                showInfoWide("Please enter departure time (HH:mm).");
                return;
            }
            var dep = parseTime(depText);
            if (dep == null) { showInfoWide("Invalid departure time. Use 24h HH:mm."); return; }

            // Optional: sanity vs arrival (we can read the disabled field)
            var arr = parseTime(dockLogArrivalTime_tf.getText());
            if (arr != null && !dep.isAfter(arr)) { showInfoWide("Departure must be after arrival."); return; }

            String remarks = dockLogRemarks_ta.getText();

            boolean ok = mysqlconnect.updateDockLogDeparture(editingDockLogId, dep, remarks);
            if (ok) {
                showInfoWide("Departure updated.");
                addNewDockLog_popup.setVisible(false);
                dockingLogs_pane.setDisable(false);
                sideNavigation_vbox.setDisable(false);
                dockUpdateMode = false;
                editingDockLogId = null;
                refreshDockLogsTable();
            } else {
                showInfoWide("Update failed.");
            }
        }
    }

    // helpers
    private void hideDockErrors() {
        dockLogFisherfolk_err.setVisible(false);
        dockLogDockDate_err.setVisible(false);
        dockLogArrivalTime_err.setVisible(false);
    }
    private java.time.LocalTime parseTime(String s) {
        if (s == null) return null;
        String t = s.trim();
        if (t.isEmpty()) return null;
        try {
            return java.time.LocalTime.parse(t, java.time.format.DateTimeFormatter.ofPattern("HH:mm"));
        } catch (Exception e) {
            return null;
        }
    }
    private void refreshDockLogsTable() {
        // re-fetch from DB
        var fresh = mysqlconnect.loadDockLogsView();
        // if needed to preserve sorting/filtering: replace items on the backing list
        dockData.setAll(fresh); // dockData is the ObservableList backing the FilteredList
        applyDockFilters();     // re-apply current search/date filters
        refreshDockStats();     // update “today” & “most active” labels
    }
    ////////////////////////////////////////////////////////////////////////////end Dock Logs
    ////////////////////////////////////////////////////////////////////////////REPORTS & ANALYTICS
    private void loadSalesSeries() {
        var start = dpStart.getValue();
        var end   = dpEnd.getValue();

        java.util.List<mysqlconnect.PeriodRevenue> rows;
        String seriesName;
        if (rbDaily.isSelected()) {
            rows = mysqlconnect.salesDaily(start, end);
            seriesName = "Daily Revenue";
        } else if (rbWeekly.isSelected()) {
            rows = mysqlconnect.salesWeekly(start, end);
            seriesName = "Weekly Revenue";
        } else {
            rows = mysqlconnect.salesMonthly(start, end);
            seriesName = "Monthly Revenue";
        }

        var series = new javafx.scene.chart.XYChart.Series<String, Number>();
        series.setName(seriesName);
        for (var r : rows) {
            var dp = new javafx.scene.chart.XYChart.Data<String, Number>(r.label, r.revenue);
            // tooltip with peso format
            javafx.scene.control.Tooltip t = new javafx.scene.control.Tooltip(pesoFmt.format(r.revenue));
            javafx.scene.control.Tooltip.install(dp.getNode() == null ? new javafx.scene.shape.Rectangle(0,0) : dp.getNode(), t);
            series.getData().add(dp);
        }

        salesChart.getData().setAll(series);
    }
    
    private java.time.LocalDate startOfMonth() {
        return java.time.LocalDate.now().withDayOfMonth(1);
    }
    private java.time.LocalDate endOfMonth() {
        return java.time.LocalDate.now().withDayOfMonth(java.time.LocalDate.now().lengthOfMonth());
    }

    private void loadSpeciesDistributionAuto() {
        var res = mysqlconnect.loadSpeciesDistribution(startOfMonth(), endOfMonth());
        speciesPieChart.setData(javafx.collections.FXCollections.observableArrayList(res.getValue()));

        // add % to labels (optional)
        double total = speciesPieChart.getData().stream().mapToDouble(javafx.scene.chart.PieChart.Data::getPieValue).sum();
        speciesPieChart.getData().forEach(d -> {
            double pct = total == 0 ? 0 : (d.getPieValue() / total) * 100;
            d.setName(d.getName() + String.format(" (%.1f%%)", pct));
        });

        speciesDateLabel.setText(res.getKey().isBlank() ? "All time" : res.getKey());
    }

    private void loadFisherfolkContribAuto() {
        var res = mysqlconnect.loadFisherfolkContrib(startOfMonth(), endOfMonth(), true); // paidOnly=true
        contribBarChart.getData().setAll(res.getValue());
        fisherfolkDateLabel.setText(res.getKey().isBlank() ? "All time" : res.getKey());
    }

    private void loadCatchVolumesAuto() {
        var res = mysqlconnect.loadCatchVolumes(startOfMonth(), endOfMonth());
        catchStackedBarChart.getData().clear();
        res.getValue().forEach((species, list) -> {
            var s = new javafx.scene.chart.XYChart.Series<String,Number>();
            s.setName(species);
            s.getData().addAll(list);
            catchStackedBarChart.getData().add(s);
        });
        catchVolumeDateLabel.setText(res.getKey().isBlank() ? "All time" : res.getKey());
    }
    
    private void reloadReports(java.time.LocalDate start, java.time.LocalDate end) {
        var sp = mysqlconnect.loadSpeciesDistribution(start, end);
        speciesPieChart.setData(javafx.collections.FXCollections.observableArrayList(sp.getValue()));
        speciesDateLabel.setText(sp.getKey());

        var ff = mysqlconnect.loadFisherfolkContrib(start, end, true);
        contribBarChart.getData().setAll(ff.getValue());
        fisherfolkDateLabel.setText(ff.getKey());

        var cv = mysqlconnect.loadCatchVolumes(start, end);
        catchStackedBarChart.getData().clear();
        cv.getValue().forEach((k,v) -> {
            var s = new javafx.scene.chart.XYChart.Series<String,Number>();
            s.setName(k); s.getData().addAll(v);
            catchStackedBarChart.getData().add(s);
        });
        catchVolumeDateLabel.setText(cv.getKey());
    }

    // ====== SNAPSHOT CHART TO PNG ======
    private boolean snapshotToPng(javafx.scene.Node node, java.io.File outFile) {
        try {
            javafx.scene.image.WritableImage img = node.snapshot(null, null);
            javax.imageio.ImageIO.write(
                javafx.embed.swing.SwingFXUtils.fromFXImage(img, null),
                "png",
                outFile
            );
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            showInfo("Failed to save chart image: " + ex.getMessage());
            return false;
        }
    }

    // ====== GENERIC PRINT OF ANY NODE ======
    private void printNode(javafx.scene.Node node) {
        javafx.print.PrinterJob job = javafx.print.PrinterJob.createPrinterJob();
        if (job != null && job.showPrintDialog(node.getScene().getWindow())) {
            if (job.printPage(node)) job.endJob();
        }
    }
    
    @FXML
    private void btnExport(ActionEvent event) {
        // Determine which tab is active and delegate.
        // Example ids: salesTab, speciesTab, contribTab, volumesTab
        var selectedTab = reportsTabPane.getSelectionModel().getSelectedItem();
        if (selectedTab == salesTab) {
            exportSales();
        } else if (selectedTab == speciesTab) {
            exportChartOnly(speciesPieChart, "species_distribution");
        } else if (selectedTab == contribTab) {
            exportChartOnly(contribBarChart, "fisherfolk_contributions");
        } else if (selectedTab == volumesTab) {
            exportChartOnly(catchStackedBarChart, "catch_volumes");
        }
    }
    private void exportSales() {
        // 1) Pick CSV destination
        var chooser = new javafx.stage.FileChooser();
        chooser.setTitle("Export Sales Trend (CSV)");
        chooser.getExtensionFilters().add(new javafx.stage.FileChooser.ExtensionFilter("CSV file (*.csv)", "*.csv"));
        // sensible default name with timestamp
        String period = rbDaily.isSelected() ? "daily" : rbWeekly.isSelected() ? "weekly" : "monthly";
        String ts = java.time.LocalDateTime.now().toString().replace(':','-').substring(0,19);
        chooser.setInitialFileName("sales_trend_" + period + "_" + ts + ".csv");

        java.io.File csvFile = chooser.showSaveDialog(salesChart.getScene().getWindow());
        if (csvFile == null) return; // user cancelled

        // 2) Write CSV of current series
        var series = salesChart.getData().isEmpty() ? null : salesChart.getData().get(0);
        if (series == null) { showInfo("No data to export."); return; }

        try (var pw = new java.io.PrintWriter(csvFile, java.nio.charset.StandardCharsets.UTF_8)) {
            pw.println("Period,RevenuePHP");
            for (var dp : series.getData()) {
                pw.printf("%s,%.2f%n", dp.getXValue(), dp.getYValue().doubleValue());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            showInfo("Export failed: " + ex.getMessage());
            return;
        }

        // 3) Save a PNG of the chart next to the CSV
        String baseName = csvFile.getName();
        int dot = baseName.lastIndexOf('.');
        if (dot > 0) baseName = baseName.substring(0, dot);
        java.io.File pngFile = new java.io.File(csvFile.getParentFile(), baseName + ".png");

        boolean okPng = snapshotToPng(salesChart, pngFile);

        showInfo("Exported:\n" + csvFile.getAbsolutePath() + (okPng ? "\n" + pngFile.getAbsolutePath() : ""));
    }

    private void exportChartOnly(javafx.scene.Node chart, String defaultBaseName) {
        var chooser = new javafx.stage.FileChooser();
        chooser.setTitle("Save Chart Image (PNG)");
        chooser.getExtensionFilters().add(new javafx.stage.FileChooser.ExtensionFilter("PNG image (*.png)", "*.png"));
        String ts = java.time.LocalDateTime.now().toString().replace(':','-').substring(0,19);
        chooser.setInitialFileName(defaultBaseName + "_" + ts + ".png");
        var file = chooser.showSaveDialog(chart.getScene().getWindow());
        if (file == null) return;
        if (snapshotToPng(chart, file)) showInfo("Saved: " + file.getAbsolutePath());
    }

    @FXML
    private void btnPrint(ActionEvent event) {
         var selectedTab = reportsTabPane.getSelectionModel().getSelectedItem();
        if (selectedTab == salesTab) {
            printNode(salesChart);
        } else if (selectedTab == speciesTab) {
            printNode(speciesPieChart);
        } else if (selectedTab == contribTab) {
            printNode(contribBarChart);
        } else if (selectedTab == volumesTab) {
            printNode(catchStackedBarChart);
        }
    }
    
    ////////////////////////////////////////////////////////////////////////////end of reports & analytics
    ////////////////////////////////////////////////////////////////////////////SPECIES
    // --- tiny helpers
    private void hideSpeciesErrors() { speciesName_err.setVisible(false); }

    private void showInfoSpecies(String msg) {
        var a = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION, msg,
                javafx.scene.control.ButtonType.OK);
        a.setHeaderText(null); a.showAndWait();
    }
    
    @FXML
    private void addSpecies(ActionEvent event) {
        speciesUpdateMode = false;
        editingSpeciesId = null;

        hideSpeciesErrors();
        speciesName_tf.clear();
        speciesDescription_tf.clear();

        addSpecies_popup.setVisible(true);
        // optionally disable background panes 
        sideNavigation_vbox.setDisable(true);
        species_pane.setDisable(true);
    }

    @FXML
    private void updateSpecies(ActionEvent event) {
        SpeciesItem sel = species_tv.getSelectionModel().getSelectedItem();
        if (sel == null) { showInfoSpecies("Please select a species to edit."); return; }

        speciesUpdateMode = true;
        editingSpeciesId = sel.getId();

        hideSpeciesErrors();
        speciesName_tf.setText(sel.getSpeciesName());
        speciesDescription_tf.setText(sel.getDescription());

        addSpecies_popup.setVisible(true);
        sideNavigation_vbox.setDisable(true);
        species_pane.setDisable(true);
    }

    @FXML
    private void deleteSpecies(ActionEvent event) {
        SpeciesItem sel = species_tv.getSelectionModel().getSelectedItem();
        if (sel == null) { showInfoSpecies("Please select a species to delete."); return; }

        var confirm = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.CONFIRMATION,
                "Delete species \"" + sel.getSpeciesName() + "\"?\nThis can’t be undone.",
                javafx.scene.control.ButtonType.YES, javafx.scene.control.ButtonType.NO);
        confirm.setHeaderText("Confirm delete");
        confirm.showAndWait();
        if (confirm.getResult() != javafx.scene.control.ButtonType.YES) return;

        boolean ok = mysqlconnect.deleteSpecies(sel.getId());
        if (!ok) {
            showInfoSpecies("Cannot delete.\nThis species is still used by existing Catch records.");
            return;
        }

        speciesData.remove(sel); // reflects immediately
        showInfoSpecies("Species deleted.");
    }

    @FXML
    private void btnExport_SPECIES(ActionEvent event) {
        var chooser = new javafx.stage.FileChooser();
        chooser.setTitle("Export species");
        chooser.getExtensionFilters().add(new javafx.stage.FileChooser.ExtensionFilter("CSV file", "*.csv"));
        chooser.setInitialFileName("species_" + java.time.LocalDate.now() + ".csv");
        java.io.File file = chooser.showSaveDialog(species_tv.getScene().getWindow());
        if (file == null) return;

        try (var pw = new java.io.PrintWriter(file, java.nio.charset.StandardCharsets.UTF_8)) {
            pw.println("ID,Name,Description");
            for (SpeciesItem s : speciesSorted) {
                pw.printf("%d,%s,%s%n",
                    s.getId(),
                    csv(s.getSpeciesName()),
                    csv(s.getDescription()));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            showInfoSpecies("Export failed: " + ex.getMessage());
            return;
        }
        showInfoSpecies("Exported to:\n" + file.getAbsolutePath());
    }

    // on adding or editing species (popup form)
    @FXML
    private void btnCancel_onAddSpecies(ActionEvent event) {
        addSpecies_popup.setVisible(false);
        speciesName_err.setVisible(false);
        // re-enable background panes 
        sideNavigation_vbox.setDisable(false);
        species_pane.setDisable(false);
    }

    @FXML
    private void btnClear_onAddSpecies(ActionEvent event) {
        hideSpeciesErrors();
        speciesName_tf.clear();
        speciesDescription_tf.clear();
    }

    @FXML
    private void btnSave_onAddSpecies(ActionEvent event) {
        hideSpeciesErrors();

        String name = speciesName_tf.getText() == null ? "" : speciesName_tf.getText().trim();
        String desc = speciesDescription_tf.getText() == null ? null : speciesDescription_tf.getText().trim();

        if (name.isEmpty()) {
            speciesName_err.setVisible(true);
            return;
        }

        boolean ok;
        if (!speciesUpdateMode) {
            ok = mysqlconnect.insertSpecies(name, desc);
            if (ok) {
                // quick refresh: append the newly added by reloading (or fetch last insert id)
                speciesData.setAll(mysqlconnect.loadSpecies());
            }
        } else {
            ok = mysqlconnect.updateSpecies(editingSpeciesId, name, desc);
            if (ok) {
                // update the in-memory row so the table refreshes without requery
                for (SpeciesItem s : speciesData) {
                    if (s.getId() == editingSpeciesId) {
                        s.setSpeciesName(name);
                        s.setDescription(desc);
                        break;
                    }
                }
                // force table refresh
                species_tv.refresh();
            }
        }

        if (ok) {
            addSpecies_popup.setVisible(false);
            speciesUpdateMode = false;
            editingSpeciesId = null;
            showInfoSpecies("Saved.");
        } else {
            showInfoSpecies("Save failed. Please try again.");
        }
        sideNavigation_vbox.setDisable(false);
        species_pane.setDisable(false);
    }
    ////////////////////////////////////////////////////////////////////////////end of species
    ////////////////////////////////////////////////////////////////////////////ACCOUNT PROFILE
    
    // ===== INIT =====
    private void initUserProfile() {
        accountRole_cb.setItems(ROLES);

        loadUserIntoForm();

        // start in read-only mode
        setPersonalEditable(false);
        cancelSavebtn_hbox.setDisable(true);

        // bind visibility of plain/hidden password fields
        bindPasswordMirror(currentPassword_pf, currentPassword_tf_visible);
        bindPasswordMirror(newPassword_pf, newPassword_tf_visible);
        bindPasswordMirror(confirmNewPassword_pf, confirmNewPassword_tf_visible);
    }

    private void loadUserIntoForm() {
        UserAccount u = mysqlconnect.loadUserById(currentUserId);
        if (u == null) return;

        accountName_tf.setText(u.getName());
        accountContact_tf.setText(u.getContact());
        accountRole_cb.getSelectionModel().select(u.getRole());

        origName = u.getName(); origContact = u.getContact(); origRole = u.getRole();
        origPhoto = u.getPhoto();

        // photo
        if (u.getPhoto() != null && u.getPhoto().length > 0) {
            accountPhoto_iv.setImage(new javafx.scene.image.Image(
                new java.io.ByteArrayInputStream(u.getPhoto())));
        } else {
            // fallback avatar (put an image in resources)
            var defaultImg = getClass().getResource("/img/ACCOUNT.png");
            if (defaultImg != null)
                accountPhoto_iv.setImage(new javafx.scene.image.Image(defaultImg.toExternalForm()));
            else
                accountPhoto_iv.setImage(null);
        }

        // last updated
        if (u.getUpdatedAt() != null) {
            updatedDate_label.setText("Last updated: " + u.getUpdatedAt().toString());
        } else {
            updatedDate_label.setText("Last updated: —");
        }
    }

    private void setPersonalEditable(boolean editable) {
        accountName_tf.setEditable(editable);
        accountContact_tf.setEditable(editable);
        accountRole_cb.setDisable(!editable);
        uploadPhoto_btn.setDisable(!editable);
    }

    // Mirror helper so eye icon can flip between PasswordField and TextField
    private void bindPasswordMirror(PasswordField pf, TextField tfVisible) {
        tfVisible.managedProperty().bind(tfVisible.visibleProperty());
        tfVisible.setVisible(false);
        tfVisible.textProperty().bindBidirectional(pf.textProperty());
    }

    // ====== PERSONAL INFO ======
    @FXML
    private void btnUploadPic_onAccountProfile(ActionEvent e) {
        var chooser = new javafx.stage.FileChooser();
        chooser.getExtensionFilters().add(
            new javafx.stage.FileChooser.ExtensionFilter("Image Files", "*.png","*.jpg","*.jpeg"));
        var f = chooser.showOpenDialog(accountPhoto_iv.getScene().getWindow());
        if (f == null) return;
        try {
            byte[] bytes = java.nio.file.Files.readAllBytes(f.toPath());
            // optimistic UI
            accountPhoto_iv.setImage(new javafx.scene.image.Image(new java.io.ByteArrayInputStream(bytes)));
            if (!mysqlconnect.updateUserPhoto(currentUserId, bytes)) {
                showInfo("Failed to update photo.");
                loadUserIntoForm(); // revert
                return;
            }
            // refresh last updated
            loadUserIntoForm();
            showInfo("Photo updated.");
        } catch (Exception ex) {
            ex.printStackTrace();
            showInfo("Upload failed: " + ex.getMessage());
        }
    }

    @FXML
    private void btnEdit_onAccountProfile(ActionEvent e) {
        cancelSavebtn_hbox.setDisable(false);
        setPersonalEditable(true);
        accountName_tf.requestFocus();
    }

    @FXML
    private void btnCancel_onPersonalInfo(ActionEvent e) {
        // restore originals
        accountName_tf.setText(origName);
        accountContact_tf.setText(origContact);
        accountRole_cb.getSelectionModel().select(origRole);
        // photo restored by reload
        loadUserIntoForm();

        cancelSavebtn_hbox.setDisable(true);
        setPersonalEditable(false);
    }

    @FXML
    private void btnSave_onPersonalInfo(ActionEvent e) {
        String name = trim(accountName_tf.getText());
        String contact = trim(accountContact_tf.getText());
        String role = (String) accountRole_cb.getValue();

        if (name.isBlank()) { showInfo("Name is required."); return; }
        if (role == null)   { showInfo("Please choose a role."); return; }

        boolean ok = mysqlconnect.updateUserPersonal(currentUserId, name, contact, role);
        if (ok) {
            showInfo("Profile saved.");
            cancelSavebtn_hbox.setDisable(true);
            setPersonalEditable(false);
            loadUserIntoForm();
        } else {
            showInfo("Save failed.");
        }
    }

    // ====== SECURITY (USERNAME/PASSWORD) ======
    @FXML
    private void btnCancel_onSecurity(ActionEvent e) {
        textfieldclear();
        messagelabelHide();
    }
    private void textfieldclear(){
        currentUsername_tf.clear();
        newUsername_tf.clear();
        currentPassword_pf.clear(); newPassword_pf.clear(); confirmNewPassword_pf.clear();
    }
    private void messagelabelHide(){
        username_err.setVisible(false); password_err.setVisible(false);
    }
    
    @FXML
    private void btnSave_onSecurity(ActionEvent e) {
        username_err.setVisible(false);
        password_err.setVisible(false);

        // 1) Username change (optional if both fields filled)
        String curU = trim(currentUsername_tf.getText());
        String newU = trim(newUsername_tf.getText());
        if (!curU.isBlank() || !newU.isBlank()) {
            if (curU.isBlank() || newU.isBlank()) {
                username_err.setText("Fill both current and new username.");
                username_err.setVisible(true); return;
            }
            if (!mysqlconnect.verifyCurrentUsername(currentUserId, curU)) {
                username_err.setText("Current username doesn't match.");
                username_err.setVisible(true); return;
            }
            if (!mysqlconnect.updateUsername(currentUserId, newU)) {
                username_err.setText("Failed to update username (maybe taken).");
                username_err.setVisible(true); return;
            }
            username_err.setText("Username updated.");
            username_err.setVisible(true);
        }

        // 2) Password change (optional)
        String curP = currentPassword_pf.getText();
        String newP = newPassword_pf.getText();
        String cfmP = confirmNewPassword_pf.getText();
        if (!curP.isBlank() || !newP.isBlank() || !cfmP.isBlank()) {
            if (curP.isBlank() || newP.isBlank() || cfmP.isBlank()) {
                password_err.setText("Fill all password fields.");
                password_err.setVisible(true); return;
            }
            if (!mysqlconnect.verifyCurrentPassword(currentUserId, curP)) {
                password_err.setText("Current password is incorrect.");
                password_err.setVisible(true); return;
            }
            if (!newP.equals(cfmP)) {
                password_err.setText("Passwords don't match!");
                password_err.setVisible(true); return;
            }
            if (!mysqlconnect.updatePassword(currentUserId, newP)) {
                password_err.setText("Failed to update password.");
                password_err.setVisible(true); return;
            }
            password_err.setText("Password updated.");
            password_err.setVisible(true);
        }

        // refresh "last updated"
        loadUserIntoForm();
        textfieldclear();
//        // optionally clear security fields
//        btnCancel_onSecurity(null);
    }

    // ====== eye icons (press to show / release to hide) ======
    @FXML private void showCurrentPass(javafx.scene.input.MouseEvent e) {
        toggleShow(currentPassword_pf, currentPassword_tf_visible);
    }
    @FXML private void showNewPass(javafx.scene.input.MouseEvent e) {
        toggleShow(newPassword_pf, newPassword_tf_visible);
    }
    @FXML private void showConfirmNewPass(javafx.scene.input.MouseEvent e) {
        toggleShow(confirmNewPassword_pf, confirmNewPassword_tf_visible);
    }

    private void toggleShow(PasswordField pf, TextField tfVisible) {
        boolean show = !tfVisible.isVisible();
        tfVisible.setVisible(show);
        pf.setVisible(!show);
    }

    // ===== helpers =====
    private String trim(String s) { return s == null ? "" : s.trim(); }
    ////////////////////////////////////////////////////////////////////////////end of account profile
    ////////////////////////////////////////////////////////////////////////////DATA INFORMATION
    @FXML
    private void manualBackup_btn(ActionEvent event) {
    }

    @FXML
    private void restoreData_browseFilesBTN(ActionEvent event) {
    }

    @FXML
    private void exportALL_CSV(ActionEvent event) {
    }

    @FXML
    private void exportALL_PDF(ActionEvent event) {
    }

    @FXML
    private void exportALL_EXCEL(ActionEvent event) {
    }



    
}
