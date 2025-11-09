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
   import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Optional;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


/**
 * FXML Controller class
 *
 * @author User
 */
public class DashboardflController implements Initializable {

    @FXML
    private ToggleGroup timeToggleGroup;
    @FXML
    private ScrollPane landings_pane;
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
    private TableColumn<Catch, Number> purchase_price_col;
//    private TableColumn<Catch, Double> price_col;
    @FXML
    private TableColumn<Catch, Number> total_col;
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
    private TextField tfPurchasePricePerKilo2;
//    private TextField tfPricePerKilo2;
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
    private TableColumn<FisherfolkRecord, String> gear_col;

//    private TableColumn<FisherfolkRecord, String> boat_col;
//    private TableColumn<FisherfolkRecord, String> license_col;
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
    private TextField contact_tf;
    @FXML
    private TextField fisherfolk_tf;
    @FXML
    private TextField address_tf;
    @FXML
    private TextField Gear_tf;
//    private TextField Boat_tf;
//    private TextField LicenseNumber_tf;
    

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

    // === TRANSACTION & SALES STATE ===
    private javafx.collections.ObservableList<TransactionViewRow> transData;
    private javafx.collections.transformation.FilteredList<TransactionViewRow> transFiltered;
    private javafx.collections.transformation.SortedList<TransactionViewRow> transSorted;
    private static final java.time.format.DateTimeFormatter TS_FMT =
            java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

    // NEW: consumers list
    private javafx.collections.ObservableList<ConsumerItem> allConsumers;

    // mode flags
    private boolean transactionUpdateMode = false;
    private Integer editingTransactionId = null;
    
//    private InventoryOption currentInventoryOpt = null;

    // peso formatter
    private static final java.text.NumberFormat PHP = java.text.NumberFormat.getCurrencyInstance(LOCALE_PH);
    static { PHP.setCurrency(java.util.Currency.getInstance("PHP")); }

    private Integer updateSpeciesId = null;   // used in update mode

//    //for adding/updating transaction
//    private javafx.collections.ObservableList<FisherfolkItem> allSellers;

    
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
    
    //DATA INFORMATION MODULE and DATA INFORMATION on dashboard
    // ==========================================================
    // SECTION A — Class fields (top of controller, before initialize)
    // ==========================================================

    // Switch geometry (matches your CSS)
    private static final double TRACK_WIDTH = 56;
    private static final double PADDING     = 3;
    private static final double THUMB_SIZE  = 24;
    private static final double ON_OFFSET   = TRACK_WIDTH - THUMB_SIZE - (2 * PADDING);

    // ---- DB + tools (unchanged) ----
    private static final String DB_HOST = "localhost";
    private static final String DB_PORT = "3306";
    private static final String DB_NAME = "fish_landing_db";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "root";
    private static final String MYSQLDUMP = "C:\\Program Files\\MySQL\\MySQL Server 8.0\\bin\\mysqldump.exe";
    private static final String MYSQL     = "C:\\Program Files\\MySQL\\MySQL Server 8.0\\bin\\mysql.exe";

    private java.util.concurrent.ScheduledExecutorService backupScheduler;

    // ---- Settings file (single source of truth) ----
    private static final java.nio.file.Path SETTINGS_DIR  =
            java.nio.file.Paths.get(System.getProperty("user.home"), ".fishlanding");
    private static final java.nio.file.Path SETTINGS_FILE =
            SETTINGS_DIR.resolve("settings.properties");
    private static final String KEY_AUTO_ENABLED = "autoBackup.enabled";
    private static final String KEY_AUTO_DIR     = "autoBackup.dir";
    private static final String KEY_AUTO_LAST    = "autoBackup.last";

    // Shared state for both toggles
    private final javafx.beans.property.BooleanProperty autoBackupEnabled =
            new javafx.beans.property.SimpleBooleanProperty(false);
    private java.nio.file.Path backupDir; // current chosen folder (may be null)
    private boolean suppressMirror = false; // prevent feedback loops

    private static String nowIso() {
        return java.time.LocalDateTime.now().toString();
    }
    private static String nowStamp() {
        return java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
    }
    // settings.properties keys (same file)
    private static final String KEY_LAST_BACKUP_ISO = "autoBackup.lastIso";
    
    ////=====CONSUMER====////
    // data pipes (like Fisherfolk)
    private javafx.collections.ObservableList<ConsumerRecord> consumerData;
    private javafx.collections.transformation.FilteredList<ConsumerRecord> consumerFiltered;
    private javafx.collections.transformation.SortedList<ConsumerRecord> consumerSorted;
    private Integer editingConsumerId = null;  // null = add mode, non-null = update mode

    ////=====INVENTORY STATE=====////
    private javafx.collections.ObservableList<InventoryRow> invData;
    private javafx.collections.transformation.FilteredList<InventoryRow> invFiltered;
    private javafx.collections.transformation.SortedList<InventoryRow> invSorted;



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
    
//    private TableView<TransactionRecord> txn_tv;
//    private TableColumn<TransactionRecord, String> buyer_col;
//    private TableColumn<TransactionRecord, Number> qtySold_col;
//    private TableColumn<TransactionRecord, Number> unitPrice_col;
//    private TableColumn<TransactionRecord, Number> totalPrice_col;
//    private TableColumn<TransactionRecord, String> status_col1;
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
    
    @FXML 
    private BorderPane addNewTransaction_popup;
//    private ComboBox<FisherfolkItem> transacSeller_cb;
    @FXML 
    private ComboBox<InventoryOption> transacFishType_cb;
    @FXML 
    private ComboBox<String> transacPaymentMethod_cb;
    @FXML 
    private ComboBox<String> transacPaymentStatus_cb;
    @FXML
    private TextField transacQuantity_tf;
    @FXML
    private TextField transacUnitPrice_tf;
//    private TextField transacBuyer_tf;
    @FXML
    private TextArea transacRemarks_ta;
//    private Label Seller_err;
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
//    private TableColumn<DockLogViewRow, String> dockLogBoat_col;
    @FXML
    private TableColumn<DockLogViewRow, String> dockLogGear_col;
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
    private Label dockLogGear_label;
//    private Label dockLogBoatName_label;
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

    @FXML
    private Label TodaysLandingInKG_label;
    @FXML
    private Label TodaysLandingInKG_increaseORdecreasePercentageVsLastMonth_label;
    @FXML
    private Label TotalFishStockInKg_label;
    @FXML
    private Label TotalFishStockInKG_increaseOrDecreasePercentageVsLastMonth_label;
    @FXML
    private Label TotalRegisteredFishermen_label;
    @FXML
    private Label RegisteredFishermenVsLastMonth_percentageLabel;
    @FXML
    private Label TotalSalesInPhp_label;
    @FXML
    private Label TotalSalesVsLastMonth_percentageLabel;
    
    @FXML
    private TableView<LandingRow> dashboardRecentFishLanding_tv;
    @FXML
    private TableColumn<LandingRow, String> dashboardFisherman_col;
    @FXML
    private TableColumn<LandingRow, String> dashboardFishType_col;
    @FXML
    private TableColumn<LandingRow, Number> dashboardQuantityKg_col;
    @FXML
    private TableColumn<LandingRow, Number> dashboardValuePHP_col;
    
    @FXML
    private ToggleButton dashboardLandingsTrend_WeekToggle;
    @FXML
    private ToggleButton dashboardLandingsTrend_monthToggle;
    @FXML
    private LineChart<String, Number> dashboardLandingsTrend_lineChart;
    @FXML
    private NumberAxis dashboardLineChart_numAxis;
    @FXML
    private CategoryAxis dashboardLIneChart_categAxis;
    @FXML
    private Label dashboardTrendLegend;
    @FXML
    private PieChart dashboardTopFishTypesKG_piechart;
    @FXML
    private ToggleButton autoBackupSwitch1;
    @FXML
    private Region thumb1;
    
    @FXML
    private ToggleButton dashboard_toggle;
    @FXML
    private ToggleButton landings_toggle;
    @FXML
    private ToggleButton fishermen_toggle;
    @FXML
    private ToggleButton sales_toggle;
    @FXML
    private ToggleButton docking_toggle;
    @FXML
    private ToggleButton species_toggle;
    @FXML
    private ToggleButton data_toggle;
    @FXML
    private ToggleButton account_toggle;
    @FXML
    private ToggleButton reportsAnalytics_toggle;
    
    @FXML
    private Label TotalLandingsTodayKG_label;
    @FXML
    private Label TotalLandingsTodayKG_rateVsLastDay;
    @FXML
    private Label TotalLandingsThisMonthKG_label;
    @FXML
    private Label TotalLandingsThisMonthKG_rateVsLastMonth;
    @FXML
    private Label AveragePerFisherfolkKG_label;
    @FXML
    private Label AveragePerFisherfolkKG_rateThisMonth;
    @FXML
    private Label TotalSalesPHP_label;
    @FXML
    private Label TotalSalesPHP_rateVsLastMonth;
    
    @FXML
    private AnchorPane consumer_pane;
    @FXML
    private TextField filterField_consumer;
    @FXML
    private TableView<ConsumerRecord> consumer_tv;
    @FXML
    private TableColumn<ConsumerRecord, String> consumerName_col;
    @FXML
    private TableColumn<ConsumerRecord, String> consumerContact_col;
    @FXML
    private TableColumn<ConsumerRecord, String> consumerAddress_col;
    @FXML
    private TableColumn<ConsumerRecord, ConsumerRecord> consumerStatus_col;
    @FXML
    private BorderPane addNewConsumer_popup;
    @FXML
    private TextField consumerName_tf;
    @FXML
    private Label Consumer_err;
    @FXML
    private TextField consumerAddress_tf;
    @FXML
    private TextField consumerContact;
    
    @FXML
    private ToggleButton consumer_toggle;
    @FXML
    private ComboBox<ConsumerItem> transacConsumer_cb;
    
    @FXML
    private ToggleButton inventory_toggle;
    @FXML
    private ScrollPane inventory_pane;
    @FXML
    private TableView<InventoryRow> inventory_tv;
    @FXML
    private TableColumn<InventoryRow, String> inventoryFishType_col;
    @FXML
    private TableColumn<InventoryRow, Number> inventoryStock_col;
    @FXML
    private TableColumn<InventoryRow, Number> inventorySold_col;
    @FXML
    private TableColumn<InventoryRow, Number> inventoryBalance_col;
    @FXML
    private TableColumn<InventoryRow, Number> inventoryLastPurchasePrice_col;
    @FXML
    private TableColumn<InventoryRow, Number> inventorySellingPrice_col;
    @FXML
    private TableColumn<InventoryRow, java.time.LocalDateTime> inventoryLastUpdate_col;
    @FXML
    private TextField filterField_inventory;
    
    
    
    private void wireNav() {
        // Default selection
        sideNav.selectToggle(dashboard_toggle);
        showSection(dashboard_toggle, dashboard_pane);
    }

    private void showSection(ToggleButton which, Node paneToShow) {
        // select the sidebar button (this also applies the :selected CSS)
        sideNav.selectToggle(which);

        // hide all, then show the requested pane
        dashboard_pane.setVisible(false);
        landings_pane.setVisible(false);
        fishermen_pane.setVisible(false);
        consumer_pane.setVisible(false);
        transactionANDsales_pane.setVisible(false);
        inventory_pane.setVisible(false);
        dockingLogs_pane.setVisible(false);
        reportsANDanalytics_pane.setVisible(false);
        species_pane.setVisible(false);
        accountProfile_pane.setVisible(false);
        dataInformation_pane.setVisible(false);

        paneToShow.setVisible(true);
    }

    ////////////////////////////////////////////////////////////////////////////SIDE NAVIGATION
    @FXML private void landings_btn(ActionEvent e) {
        showSection(landings_toggle, landings_pane);
    }
    @FXML private void fishermen_btn(ActionEvent e) {
        showSection(fishermen_toggle, fishermen_pane);
    }
    @FXML private void consumer_btn(ActionEvent e) {
        showSection(consumer_toggle, consumer_pane);
    }
    @FXML private void transactionSales_btn(ActionEvent e) {
        showSection(sales_toggle, transactionANDsales_pane);
    }
    @FXML private void dockingLogs_btn(ActionEvent e) {
        showSection(docking_toggle, dockingLogs_pane);
    }
    @FXML private void reportsAnalytics_btn(ActionEvent e) {
        showSection(reportsAnalytics_toggle, reportsANDanalytics_pane);
    }
    @FXML private void species_btn(ActionEvent e) {
        showSection(species_toggle, species_pane);
    }
    @FXML
    private void inventory_btn(ActionEvent event) {
        showSection(inventory_toggle, inventory_pane);
    }
    @FXML private void accountProfile_btn(ActionEvent e) {
        showSection(account_toggle, accountProfile_pane);
    }
    @FXML private void dataInformation_btn(ActionEvent e) {
        showSection(data_toggle, dataInformation_pane);
    }
    @FXML private void dashboard_btn(ActionEvent e) {
        showSection(dashboard_toggle, dashboard_pane);
    }

    
    ////////////////////////////////////////////////////////////////////////////end of Side Navigation
    ////////////////////////////////////////////////////////////////////////////DASHBOARD QUICK ACTIONS
    @FXML private void dashboard_fisherfolk(ActionEvent e) {
        showSection(fishermen_toggle, fishermen_pane);
    }
    @FXML private void dashboard_landings(ActionEvent e) {
        showSection(landings_toggle, landings_pane);
    }
    @FXML private void dashboard_sales(ActionEvent e) {
        showSection(sales_toggle, transactionANDsales_pane);
    }
    @FXML private void dashboard_analytics(ActionEvent e) {
        showSection(reportsAnalytics_toggle, reportsANDanalytics_pane);
    }
    @FXML private void dashboard_species(ActionEvent e) {
        showSection(species_toggle, species_pane);
    }

    
    ////////////////////////////////////////////////////////////////////////////end of dashboard quick actions
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        wireNav();
//        dashboard_pane.setVisible(true);
//        landings_pane.setVisible(false);
//        fishermen_pane.setVisible(false);
//        transactionANDsales_pane.setVisible(false);
//        dockingLogs_pane.setVisible(false);
//        reportsANDanalytics_pane.setVisible(false);
//        species_pane.setVisible(false);
//        accountProfile_pane.setVisible(false);
//        dataInformation_pane.setVisible(false);
        // LANDINGS or CATCHES
        LANDINGS_SEARCH();
        refreshLandingsKPIs();
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
        gear_col.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getGear()));
//        boat_col.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getBoatName()));
//        license_col.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getLicenseNumber()));

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
//                                ///// UPDATE FISHERFOLK(SELLER LISTadmi) COMBO BOX IN ADD TRANSACTION/////
//                                transacSeller_cb.setItems(mysqlconnect.loadActiveFisherfolkItems());
//                                transacSeller_cb.setConverter(new javafx.util.StringConverter<>() {
//                                    @Override public String toString(FisherfolkItem f) { return f == null ? "" : f.getName(); }
//                                    @Override public FisherfolkItem fromString(String s) { return null; }
//                                });
//                                // when seller changes -> reload catch options for that seller
//                                transacSeller_cb.getSelectionModel().selectedItemProperty().addListener((o, ov, sel) -> {
//                                    if (sel != null) {
//                                        var opts = mysqlconnect.loadCatchOptionsByFisher(sel.getId());
//                                        transacFishType_cb.setItems(opts);
//                                    } else {
//                                        transacFishType_cb.getItems().clear();
//                                    }
//                                    transacFishType_cb.getSelectionModel().clearSelection();
//                                    currentCatchOpt = null;
//                                    transacRemainingQuantity_label.setText("");
//                                    if (!transactionUpdateMode) transacUnitPrice_tf.clear();
//                                    updateTotalLabel();
//                                    hideErrors();
//                                });
//                                allSellers = mysqlconnect.loadActiveFisherfolkItems();
//                                transacSeller_cb.setItems(allSellers);
//                                ///// UPDATE FISHERFOLK OPTIONS IN LANDINGS
//                                loadFisherfolkOptions();
//                                refreshDashboardKPIs();
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
//                       (rec.getBoatName() != null && rec.getBoatName().toLowerCase().contains(q)) ||
//                       (rec.getLicenseNumber() != null && rec.getLicenseNumber().toLowerCase().contains(q)) ||
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

//        // ===== TRANSACTIONS =====
//        buyer_col.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getBuyerName()));
//        qtySold_col.setCellValueFactory(c -> new javafx.beans.property.SimpleDoubleProperty(c.getValue().getQtySold()));
//        unitPrice_col.setCellValueFactory(c -> new javafx.beans.property.SimpleDoubleProperty(c.getValue().getUnitPrice()));
//        totalPrice_col.setCellValueFactory(c -> new javafx.beans.property.SimpleDoubleProperty(c.getValue().getTotalPrice()));
//        status_col1.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getStatus()));
//
//        setNumeric2dp(qtySold_col);
//        setCurrencyPeso(unitPrice_col);
//        setCurrencyPeso(totalPrice_col);

        // ===== DOCK LOGS =====
        dockDate_col1.setCellValueFactory(c -> new javafx.beans.property.SimpleObjectProperty<>(c.getValue().getDockingDate()));
        arrival_col.setCellValueFactory(c -> new javafx.beans.property.SimpleObjectProperty<>(c.getValue().getArrival()));
        departure_col.setCellValueFactory(c -> new javafx.beans.property.SimpleObjectProperty<>(c.getValue().getDeparture()));
        remarks_col1.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getRemarks()));

        setDateFormat(dockDate_col1, "yyyy-MM-dd");
        setTimeFormat(arrival_col, "HH:mm");
        setTimeFormat(departure_col, "HH:mm");
        
        catch_tv1.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
//        txn_tv.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        dock_tv.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        ////TRANSACTION & SALES
        // column factories
//        transacBuyer_col.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getBuyerName()));
//        transacFisherfolk_col.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getFisherfolkName()));
        transacFishType_col.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getSpeciesName()));
        transacQuantity_col.setCellValueFactory(c -> new javafx.beans.property.SimpleDoubleProperty(c.getValue().getQtySold()));
        transacPricePerUnit_col.setCellValueFactory(c -> new javafx.beans.property.SimpleDoubleProperty(c.getValue().getUnitPrice()));
        transacTotalValue_col.setCellValueFactory(c -> new javafx.beans.property.SimpleDoubleProperty(c.getValue().getTotalPrice()));
        transacPaymentMethod_col.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getPaymentMethod()));
        transacStatus_col.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getPaymentStatus()));
        transacDate_col.setCellValueFactory(c -> new javafx.beans.property.SimpleObjectProperty<>(c.getValue().getTxnDate()));
        transacBuyer_col.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty((c.getValue().getConsumerName() != null && !c.getValue().getConsumerName().isBlank())
            ? c.getValue().getConsumerName()
            : c.getValue().getBuyerName()
                )
            );
        
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
System.out.println("Loaded txns: " + mysqlconnect.loadTransactionsView().size());

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
        
        // NEW: consumer list
        allConsumers = mysqlconnect.loadActiveConsumers();
        transacConsumer_cb.setItems(allConsumers);
        transacConsumer_cb.setConverter(new javafx.util.StringConverter<>() {
            @Override public String toString(ConsumerItem c) { return c == null ? "" : c.getName(); }
            @Override public ConsumerItem fromString(String s) { return null; }
        });

        // seller list
        

        // nice label in fish type combo is provided by CatchOption.toString()

        // when fish type (catch) changes -> set unit price & remaining
        // Catch list now shows ALL available catches with remaining > 0 across all fisherfolk
        transacFishType_cb.setItems(mysqlconnect.loadInventoryOptionsAvailable());
        // When user picks a species from inventory, show current selling price + balance
        transacFishType_cb.getSelectionModel().selectedItemProperty().addListener((o, ov, sel) -> {
            if (sel != null) {
                transacUnitPrice_tf.setText(String.format("%.2f", sel.getSellingPrice()));
                transacRemainingQuantity_label.setText(String.format("%.2f kg available", sel.getBalanceQty()));
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
//        
//        allSellers = mysqlconnect.loadActiveFisherfolkItems();
//        transacSeller_cb.setItems(allSellers);

        //DOCKING LOGS
        // columns
        dockLogFisherfolk_col.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getFisherfolkName()));
        dockLogGear_col.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getGear()));
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
            dockLogGear_label.setText(fi == null || fi.getGear() == null ? "—" : fi.getGear());
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
        

        //DASHBOARD
        refreshDashboardKPIs();
        //recent 3-5 landings in dashboard//
        // columns
        dashboardFisherman_col.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(c.getValue().getFisherName()));
        dashboardFishType_col.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(c.getValue().getSpeciesName()));
        dashboardQuantityKg_col.setCellValueFactory(c ->
                new javafx.beans.property.SimpleDoubleProperty(c.getValue().getQuantityKg()));
        dashboardValuePHP_col.setCellValueFactory(c ->
                new javafx.beans.property.SimpleDoubleProperty(c.getValue().getValuePhp()));

        // optional pretty formatting you already use elsewhere:
        setNumeric2dp(dashboardQuantityKg_col);
        setCurrencyPeso(dashboardValuePHP_col);

        // load 5 most recent
        dashboardRecentFishLanding_tv.setItems(loadRecentLandings(5));

        //landing trends in dashboard//
        // Axis labels
        dashboardLIneChart_categAxis.setLabel("Period");
        dashboardLineChart_numAxis.setLabel("Kg Sold");

        // Style the toggles with CSS
        dashboardLandingsTrend_WeekToggle.getStyleClass().add("toggle-pill");
        dashboardLandingsTrend_monthToggle.getStyleClass().add("toggle-pill");

        // Make them mutually exclusive
        var timeToggleGroup = new javafx.scene.control.ToggleGroup();
        dashboardLandingsTrend_WeekToggle.setToggleGroup(timeToggleGroup);
        dashboardLandingsTrend_monthToggle.setToggleGroup(timeToggleGroup);

        // default view
        dashboardLandingsTrend_monthToggle.setSelected(true);
        loadMonthlyLandingsTrend(); //draws chart

        dashboardLandingsTrend_monthToggle.selectedProperty().addListener((o, was, is) -> {
            if (is) loadMonthlyLandingsTrend();
        });
        dashboardLandingsTrend_WeekToggle.selectedProperty().addListener((o, was, is) -> {
            if (is) loadWeeklyLandingsTrend();
        });
        // hide built-in legend; use centered label instead
        dashboardLandingsTrend_lineChart.setLegendVisible(false);

        //top fish type
        loadDashboardTopFishTypesPie(4); // top-5
        
        
        // Auto-backup (both Dashboard & Data Info toggles)
        initAutoBackupUI();        // <-- new unified wiring + load settings + start scheduler if needed
        refreshLastBackupLabel();  // <-- shows “Last backup …” if any
        
        
        // ---------- CONSUMERS (INSIDE initialize) ----------

        // load data
        consumerData = mysqlconnect.loadConsumers();

        // columns
        consumerName_col.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getName()));
        consumerContact_col.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getContact()));
        consumerAddress_col.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getAddress()));

        // status toggle (same pattern as Fisherfolk)
        consumerStatus_col.setCellValueFactory(c -> new javafx.beans.property.ReadOnlyObjectWrapper<>(c.getValue()));
        consumerStatus_col.setCellFactory(col -> new javafx.scene.control.TableCell<>() {

            private final javafx.scene.control.ToggleButton toggle = new javafx.scene.control.ToggleButton();
            private final javafx.scene.control.Label label = new javafx.scene.control.Label();
            private final javafx.scene.layout.StackPane track = new javafx.scene.layout.StackPane();
            private final javafx.scene.shape.Circle thumb = new javafx.scene.shape.Circle(9);
            private final javafx.scene.layout.VBox box = new javafx.scene.layout.VBox(2);

            {
                toggle.getStyleClass().addAll("switch", "toggle-button");
                label.getStyleClass().add("status-label");
                track.getStyleClass().add("switch-track");
                thumb.getStyleClass().add("switch-thumb");

                track.setPickOnBounds(false);
                var switchGraphic = new javafx.scene.layout.StackPane(track, thumb);
                toggle.setGraphic(switchGraphic);
                toggle.setContentDisplay(javafx.scene.control.ContentDisplay.GRAPHIC_ONLY);

                box.setAlignment(javafx.geometry.Pos.CENTER);
                box.getChildren().setAll(label, toggle);

                toggle.setOnAction(e -> {
                    var row = getTableView().getItems().get(getIndex());
                    boolean newActive = toggle.isSelected();
                    boolean ok = mysqlconnect.updateConsumerActive(row.getConsumerId(), newActive);
                    if (ok) {
                        row.setActive(newActive);
                        updateVisual(newActive, true);
                    } else {
                        toggle.setSelected(row.isActive());
                        updateVisual(row.isActive(), true);
                        showInfoConsumers("Failed to update status.");
                    }
                });
            }

            @Override
            protected void updateItem(ConsumerRecord item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    toggle.setSelected(item.isActive());
                    updateVisual(item.isActive(), false);
                    setGraphic(box);
                }
            }

            private void updateVisual(boolean active, boolean animate) {
                label.setText(active ? "Active" : "Inactive");
                label.getStyleClass().removeAll("on");
                if (active) label.getStyleClass().add("on");

                toggle.getStyleClass().remove("on");
                if (active) toggle.getStyleClass().add("on");

                double toX = active ? 44 - 18 - 4 : 4;
                if (!animate) { thumb.setTranslateX(toX); return; }
                var tl = new javafx.animation.Timeline(
                    new javafx.animation.KeyFrame(javafx.util.Duration.millis(160),
                        new javafx.animation.KeyValue(thumb.translateXProperty(), toX, javafx.animation.Interpolator.EASE_BOTH)
                    )
                );
                tl.play();
            }
        });

        // filter pipeline
        consumerFiltered = new javafx.collections.transformation.FilteredList<>(consumerData, p -> true);
        filterField_consumer.textProperty().addListener((obs, ov, nv) -> {
            String q = (nv == null) ? "" : nv.trim().toLowerCase();
            consumerFiltered.setPredicate(rec -> {
                if (q.isEmpty()) return true;
                return (rec.getName() != null && rec.getName().toLowerCase().contains(q))
                    || (rec.getContact() != null && rec.getContact().toLowerCase().contains(q))
                    || (rec.getAddress() != null && rec.getAddress().toLowerCase().contains(q));
            });
        });
        consumerSorted = new javafx.collections.transformation.SortedList<>(consumerFiltered);
        consumerSorted.comparatorProperty().bind(consumer_tv.comparatorProperty());
        consumer_tv.setItems(consumerSorted);

        // popup default state
        addNewConsumer_popup.setVisible(false);
        hideConsumerErrors();

        
        //// INVENTORY
        inventoryFishType_col.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getSpeciesName()));
        inventoryStock_col.setCellValueFactory(c -> new javafx.beans.property.SimpleDoubleProperty(c.getValue().getPurchasedQty()));
        inventorySold_col.setCellValueFactory(c -> new javafx.beans.property.SimpleDoubleProperty(c.getValue().getSoldQty()));
        inventoryBalance_col.setCellValueFactory(c -> new javafx.beans.property.SimpleDoubleProperty(c.getValue().getBalanceQty()));
        inventoryLastPurchasePrice_col.setCellValueFactory(c -> new javafx.beans.property.SimpleDoubleProperty(c.getValue().getLastPurchasePrice()));
        inventorySellingPrice_col.setCellValueFactory(c -> new javafx.beans.property.SimpleDoubleProperty(c.getValue().getSellingPrice()));
        inventoryLastUpdate_col.setCellValueFactory(c -> new javafx.beans.property.SimpleObjectProperty<>(c.getValue().getUpdatedAt()));

        setNumeric2dp(inventoryStock_col);
        setNumeric2dp(inventorySold_col);
        setNumeric2dp(inventoryBalance_col);
        setCurrencyPeso(inventoryLastPurchasePrice_col);
        setCurrencyPeso(inventorySellingPrice_col);
        setDateTimeFormat(inventoryLastUpdate_col, "yyyy-MM-dd HH:mm");

        //load data
        invData = mysqlconnect.loadInventoryView();
        
        // filtered + sorted chain
        invFiltered = new FilteredList<>(invData, r -> true);
        invSorted   = new SortedList<>(invFiltered);
        invSorted.comparatorProperty().bind(inventory_tv.comparatorProperty());
        inventory_tv.setItems(invSorted);

        // search box
        filterField_inventory.textProperty().addListener((obs, oldV, newV) -> applyInventoryFilters());


    }
        
    ////////////////////////////////////////////////////////////////////////////end of initialization
    
    ////////////////////////////////////////////////////////////////////////////LANDINGS -> PURCHASES
    public  void LANDINGS_SEARCH(){ 
        catchId_col.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getCatchId()).asObject());
        fisherman_col.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getFisherfolkName()));
        fishType_col.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getSpeciesName()));
        quantity_col.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getQuantity()).asObject());
        purchase_price_col.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getPurchasePricePerKilo()));
        total_col.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getTotalValue()));
        dockDate_col.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getCatchDate()));
        dockTime_col.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getDockingTime()));
        remarks_col.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getRemarks()));
        
        setCurrencyPeso(purchase_price_col);
        setCurrencyPeso(total_col);
    
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
                                  "Are you sure you want to delete this purchase record?",
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
                                              "Purchase record deleted successfully.", 
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
                loadSpeciesDistributionAuto(); //REPORTS - SPECIES DISTRIBUTION
                loadCatchVolumesAuto(); //REPORTS - CATCH VOLUMES
                

            } catch (Exception e) {
                e.printStackTrace();
                Alert err = new Alert(Alert.AlertType.ERROR, 
                                      "Error deleting record: " + e.getMessage(), 
                                      ButtonType.OK);
                err.setHeaderText(null);
                err.showAndWait();
            }
        } //pie chart errors when i try to load species distribution here
           //case: when i try to delete landings that has still a transaction record
          refreshLandingsKPIs();
          refreshDashboardKPIs(); //today's landing kg
           // load 5 most recent
          dashboardRecentFishLanding_tv.setItems(loadRecentLandings(5));
          loadWeeklyLandingsTrend();
          loadMonthlyLandingsTrend();
          dashboardLandingsTrend_monthToggle.setSelected(true);
          //top fish type
          loadDashboardTopFishTypesPie(4); // top-5
          refreshInventoryTable();
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
        tfPurchasePricePerKilo2.setText(String.valueOf(sel.getPurchasePricePerKilo()));

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
        
        dpCatchDate2.setValue(LocalDate.now());
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
        tfPurchasePricePerKilo2.clear();
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
        tfPurchasePricePerKilo2.clear();
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
    Double purchasePricePerKilo = parsePositiveDouble(tfPurchasePricePerKilo2.getText());
    if (purchasePricePerKilo == null) {
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
    purchasePricePerKilo   = parsePositiveDouble(tfPurchasePricePerKilo2.getText());
    catchDate   = dpCatchDate2.getValue();
    dockingTime = parseOptionalTime(tfDockingTime2.getText()); // implement as before
    remarks        = normalizeEmptyToNull(taRemarks2.getText());

    if (fisher == null || species == null || quantity == null || purchasePricePerKilo == null || catchDate == null) {
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
        ps.setBigDecimal(4, java.math.BigDecimal.valueOf(purchasePricePerKilo));
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
                showInfo("Purchased saved.");
            } else {
                showInfo("Purchased updated.");
            }

            // Close popup, clear form, and reload table
            // helper that clears fields & hides errors
            addNewLanding_popup.setVisible(false);
            cbFisherfolk2.getSelectionModel().clearSelection();
            cbSpecies2.getSelectionModel().clearSelection();
            tfQuantity2.clear();
            tfPurchasePricePerKilo2.clear();
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
        
        refreshLandingsKPIs();
        refreshDashboardKPIs(); //today's landing kg
        loadSpeciesDistributionAuto(); //REPORTS - SPECIES DISTRIBUTION
        loadCatchVolumesAuto(); //REPORTS - CATCH VOLUMES
        
         // load 5 most recent
        dashboardRecentFishLanding_tv.setItems(loadRecentLandings(5));
        loadWeeklyLandingsTrend();
        loadMonthlyLandingsTrend();
        dashboardLandingsTrend_monthToggle.setSelected(true);
        //top fish type
        loadDashboardTopFishTypesPie(4); // top-5
        refreshInventoryTable();
        
        
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
    private void exportLandings(ActionEvent event) {
        // Ask where to save
        var chooser = new javafx.stage.FileChooser();
        chooser.setTitle("Export Purchased (Excel)");
        chooser.getExtensionFilters().add(
            new javafx.stage.FileChooser.ExtensionFilter("Excel Workbook (*.xlsx)", "*.xlsx")
        );
        String base = "purchased_" + java.time.LocalDate.now();
        chooser.setInitialFileName(base + ".xlsx");

        var file = chooser.showSaveDialog(TotalLandingsTodayKG_label.getScene().getWindow());
        if (file == null) return; // user cancelled

        // Query rows (join names so the sheet is readable)
        final String sql = """
            SELECT c.catch_id,
                   f.name        AS fisherfolk,
                   s.species_name AS species,
                   c.quantity,
                   c.price_per_kilo,
                   c.total_value,
                   c.catch_date,
                   c.docking_time,
                   c.remarks
            FROM catch c
            JOIN fisherfolk f ON f.fisherfolk_id = c.fisherfolk_id
            JOIN species    s ON s.species_id     = c.species_id
            ORDER BY c.catch_date DESC, c.catch_id DESC
        """;

        try (var wb = new org.apache.poi.xssf.usermodel.XSSFWorkbook();
             var c = mysqlconnect.ConnectDb();
             var ps = c.prepareStatement(sql);
             var rs = ps.executeQuery()) {

            var sh = wb.createSheet("Purchased");

            // header style
            var bold = wb.createFont(); bold.setBold(true);
            var head = wb.createCellStyle(); head.setFont(bold);

            String[] headers = {
                "Purchase ID","Fisherfolk","Species","Quantity (kg)",
                "Purchase Price / kg","Total Value","Date","Docking Time","Remarks"
            };
            int r = 0;
            var hr = sh.createRow(r++);
            for (int i = 0; i < headers.length; i++) {
                var cell = hr.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(head);
            }

            // rows
            while (rs.next()) {
                var row = sh.createRow(r++);
                int cix = 0;
                row.createCell(cix++).setCellValue(rs.getInt("catch_id"));
                row.createCell(cix++).setCellValue(rs.getString("fisherfolk"));
                row.createCell(cix++).setCellValue(rs.getString("species"));
                row.createCell(cix++).setCellValue(rs.getDouble("quantity"));
                row.createCell(cix++).setCellValue(rs.getDouble("price_per_kilo"));
                row.createCell(cix++).setCellValue(rs.getDouble("total_value"));
                var cd = rs.getDate("catch_date");
                row.createCell(cix++).setCellValue(cd == null ? "" : cd.toString());
                var dt = rs.getTime("docking_time");
                row.createCell(cix++).setCellValue(dt == null ? "" : dt.toString());
                row.createCell(cix++).setCellValue(rs.getString("remarks"));
            }

            for (int i = 0; i < headers.length; i++) sh.autoSizeColumn(i);

            try (var out = new java.io.FileOutputStream(file)) { wb.write(out); }

            // success message
            showInfoWide("Purchased exported to:\n" + file.getAbsolutePath());

        } catch (Exception ex) {
            ex.printStackTrace();
            showInfoWide("Export failed: " + ex.getMessage());
        }
    }

    
    //LANDINGS KPI'S
    // ---------- LANDINGS KPI HELPERS ----------
    // quick numeric queries (returns 0 on any issue)
    private double qd(String sql) {
        try (var c = mysqlconnect.ConnectDb();
             var st = c.createStatement();
             var rs = st.executeQuery(sql)) {
            return rs.next() ? rs.getDouble(1) : 0.0;
        } catch (Exception ex) { ex.printStackTrace(); return 0.0; }
    }

    // percentage delta = (now - prev) / prev * 100
    private Double pctDelta(double nowVal, double prevVal) {
        if (prevVal == 0) {
            if (nowVal == 0) return null;     // “—”
            return 100.0;                     // define as +100% when prev was 0
        }
        return (nowVal - prevVal) / prevVal * 100.0;
    }

    // color + arrow on a label, e.g. ▲ 12.3% / ▼ 8.0% / — 
    private void setDeltaLabel(Label lbl, Double pct) {
        if (pct == null) {
            lbl.setText("—");
            lbl.setStyle("-fx-text-fill:#e6edf3;"); // neutral
            return;
        }
        String arrow = pct >= 0 ? "▲" : "▼";
        String color = pct >= 0 ? "#22c55e" : "#e85760";
        lbl.setText(String.format("%s %.1f%%", arrow, Math.abs(pct)));
        lbl.setStyle("-fx-text-fill:" + color + ";");
    }

    // currency pretty “₱ 12,345.67”
    private String peso1(double v) {
        return "₱ " + String.format("%,.2f", v);
    }

    private void refreshLandingsKPIs() {
        // --- totals in kg from CATCH ---
        double todayKg = qd("""
            SELECT IFNULL(SUM(quantity),0)
            FROM catch
            WHERE catch_date = CURDATE()
        """);

        double yestKg = qd("""
            SELECT IFNULL(SUM(quantity),0)
            FROM catch
            WHERE catch_date = CURDATE() - INTERVAL 1 DAY
        """);

        double monthKg = qd("""
            SELECT IFNULL(SUM(quantity),0)
            FROM catch
            WHERE YEAR(catch_date)=YEAR(CURDATE())
              AND MONTH(catch_date)=MONTH(CURDATE())
        """);

        double prevMonthKg = qd("""
            SELECT IFNULL(SUM(quantity),0)
            FROM catch
            WHERE YEAR(catch_date)=YEAR(CURDATE() - INTERVAL 1 MONTH)
              AND MONTH(catch_date)=MONTH(CURDATE() - INTERVAL 1 MONTH)
        """);

        // --- average kg per fisherfolk (this month) ---
        double monthDistinctFishers = qd("""
            SELECT COUNT(DISTINCT fisherfolk_id)
            FROM catch
            WHERE YEAR(catch_date)=YEAR(CURDATE())
              AND MONTH(catch_date)=MONTH(CURDATE())
        """);
        double prevMonthDistinctFishers = qd("""
            SELECT COUNT(DISTINCT fisherfolk_id)
            FROM catch
            WHERE YEAR(catch_date)=YEAR(CURDATE() - INTERVAL 1 MONTH)
              AND MONTH(catch_date)=MONTH(CURDATE() - INTERVAL 1 MONTH)
        """);

        double avgThisMonth = (monthDistinctFishers == 0) ? 0 : (monthKg / monthDistinctFishers);
        double avgPrevMonth = (prevMonthDistinctFishers == 0) ? 0 : (prevMonthKg / prevMonthDistinctFishers);

//        // --- total sales in PHP (from TRANSACTIONS, monthly) ---
//        double salesMonth = qd("""
//            SELECT IFNULL(SUM(total_price),0)
//            FROM transactions
//            WHERE YEAR(transaction_date)=YEAR(CURDATE())
//              AND MONTH(transaction_date)=MONTH(CURDATE())
//        """);

// --- total sales in PHP (from CATCH - PURCHASES, monthly) ---
        double purchaseMonth = qd("""
            SELECT IFNULL(SUM(total_value),0)
            FROM catch
            WHERE YEAR(catch_date)=YEAR(CURDATE())
              AND MONTH(catch_date)=MONTH(CURDATE())
        """);

        double purchasePrevMonth = qd("""
            SELECT IFNULL(SUM(total_value),0)
            FROM catch
            WHERE YEAR(catch_date)=YEAR(CURDATE() - INTERVAL 1 MONTH)
              AND MONTH(catch_date)=MONTH(CURDATE() - INTERVAL 1 MONTH)
        """);

        // --- Update labels ---
        // 1) Today total landings
        TotalLandingsTodayKG_label.setText(String.format("%.2f kg", todayKg));
        setDeltaLabel(TotalLandingsTodayKG_rateVsLastDay, pctDelta(todayKg, yestKg));

        // 2) This month total landings
        TotalLandingsThisMonthKG_label.setText(String.format("%.2f kg", monthKg));
        setDeltaLabel(TotalLandingsThisMonthKG_rateVsLastMonth, pctDelta(monthKg, prevMonthKg));

        // 3) Average per fisherfolk (this month)
        AveragePerFisherfolkKG_label.setText(String.format("%.2f kg", avgThisMonth));
        setDeltaLabel(AveragePerFisherfolkKG_rateThisMonth, pctDelta(avgThisMonth, avgPrevMonth));

        // 4) Total sales in PHP (this month)
        TotalSalesPHP_label.setText(peso1(purchaseMonth));
        setDeltaLabel(TotalSalesPHP_rateVsLastMonth, pctDelta(purchaseMonth, purchasePrevMonth));
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
        Gear_tf.clear();
//        Boat_tf.clear();
//        LicenseNumber_tf.clear();
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
        } refreshDashboardKPIs(); //total no.of registered fishermen
          loadFisherfolkOptions(); // UPDATE FISHERFOLK OPTIONS IN LANDINGS
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
        Gear_tf.setText(nullToEmpty(sel.getGear()));
//        Boat_tf.setText(nullToEmpty(sel.getBoatName()));
//        LicenseNumber_tf.setText(nullToEmpty(sel.getLicenseNumber()));

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
        String gear = safeTrim(Gear_tf.getText());
//        String boat = safeTrim(Boat_tf.getText());               // optional
//        String license = safeTrim(LicenseNumber_tf.getText());   // optional

        if (!valid) return;

        // --- SQL ---
        if (editingFisherId == null) {
            // INSERT (default is_active = 1 for new)
            final String sql = "INSERT INTO fisherfolk " +
                    "(name, gender, age, contact_number, address, gear, is_active) " +
                    "VALUES (?,?,?,?,?,?,1)";
            try (var conn = mysqlconnect.ConnectDb();
                 var ps = conn.prepareStatement(sql)) {

                ps.setString(1, name);
                ps.setString(2, gender);
                if (age != null) ps.setInt(3, age); else ps.setNull(3, java.sql.Types.INTEGER);
                if (contact != null) ps.setString(4, contact); else ps.setNull(4, java.sql.Types.VARCHAR);
                if (address != null) ps.setString(5, address); else ps.setNull(5, java.sql.Types.VARCHAR);
                if (gear != null) ps.setString(6, gear); else ps.setNull(6, java.sql.Types.VARCHAR);
//                if (boat != null) ps.setString(6, boat); else ps.setNull(6, java.sql.Types.VARCHAR);
//                if (license != null) ps.setString(7, license); else ps.setNull(7, java.sql.Types.VARCHAR);

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
                    "name=?, gender=?, age=?, contact_number=?, address=?, gear=? " +
                    "WHERE fisherfolk_id=?";
            try (var conn = mysqlconnect.ConnectDb();
                 var ps = conn.prepareStatement(sql)) {

                ps.setString(1, name);
                ps.setString(2, gender);
                if (age != null) ps.setInt(3, age); else ps.setNull(3, java.sql.Types.INTEGER);
                if (contact != null) ps.setString(4, contact); else ps.setNull(4, java.sql.Types.VARCHAR);
                if (address != null) ps.setString(5, address); else ps.setNull(5, java.sql.Types.VARCHAR);
                if (gear != null) ps.setString(6, gear); else ps.setNull(6, java.sql.Types.VARCHAR);
//                if (boat != null) ps.setString(6, boat); else ps.setNull(6, java.sql.Types.VARCHAR);
//                if (license != null) ps.setString(7, license); else ps.setNull(7, java.sql.Types.VARCHAR);
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
        } refreshDashboardKPIs(); //total no.of registered fishermen
          loadFisherfolkOptions(); // UPDATE FISHERFOLK OPTIONS IN LANDINGS
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
//        txn_tv.setItems(mysqlconnect.getTransactionsByFisher(fisherId));
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
  
    private void viewTransaction(ActionEvent event) {
        FisherfolkRecord sel = fishermen_tv.getSelectionModel().getSelectedItem();
        if (sel == null) {
            showInfoWide("Please select a fisherfolk to view history.");
            return;
        }

        int fisherId = sel.getFisherfolkId();

        // load all 3 lists
        catch_tv1.setItems(mysqlconnect.getCatchesByFisher(fisherId));
//        txn_tv.setItems(mysqlconnect.getTransactionsByFisher(fisherId));
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
//        txn_tv.setItems(mysqlconnect.getTransactionsByFisher(fisherId));
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
        // chooser
        var fc = new javafx.stage.FileChooser();
        fc.setTitle("Save Fisherfolk List");
        fc.getExtensionFilters().add(
            new javafx.stage.FileChooser.ExtensionFilter("Excel Workbook (*.xlsx)", "*.xlsx")
        );
        fc.setInitialFileName("fisherfolk_export_" + java.time.LocalDate.now() + ".xlsx");

        java.io.File file = fc.showSaveDialog(((javafx.scene.Node)event.getSource()).getScene().getWindow());
        if (file == null) return;
        if (!file.getName().toLowerCase().endsWith(".xlsx")) {
            file = new java.io.File(file.getParentFile(), file.getName() + ".xlsx");
        }

        try (var wb = new org.apache.poi.xssf.usermodel.XSSFWorkbook()) {
            var sh = wb.createSheet("Fisherfolk");
            // header style
            var bold = wb.createFont(); bold.setBold(true);
            var head = wb.createCellStyle(); head.setFont(bold);

            String[] headers = {"ID","Name","Age","Gender","Contact","Address","Boat","License","Active"};
            int r = 0;
            var hr = sh.createRow(r++);
            for (int i = 0; i < headers.length; i++) {
                var c = hr.createCell(i);
                c.setCellValue(headers[i]);
                c.setCellStyle(head);
            }

            for (FisherfolkRecord f : fisherSorted) {
                var row = sh.createRow(r++);
                int c = 0;
                row.createCell(c++).setCellValue(f.getFisherfolkId());
                row.createCell(c++).setCellValue(f.getName());
                row.createCell(c++).setCellValue(f.getAge() == null ? "" : f.getAge().toString());
                row.createCell(c++).setCellValue(f.getGender());
                row.createCell(c++).setCellValue(f.getContactNumber());
                row.createCell(c++).setCellValue(f.getAddress());
                row.createCell(c++).setCellValue(f.getGear());
//                row.createCell(c++).setCellValue(f.getBoatName());
//                row.createCell(c++).setCellValue(f.getLicenseNumber());
                row.createCell(c++).setCellValue(f.isActive() ? "Active" : "Inactive");
            }
            for (int i = 0; i < headers.length; i++) sh.autoSizeColumn(i);

            try (var out = new java.io.FileOutputStream(file)) { wb.write(out); }
        } catch (Exception ex) {
            ex.printStackTrace();
            showInfoFISHERMEN("Export failed: " + ex.getMessage());
            return;
        }

        // success dialog (unchanged)
        var path = file.getAbsolutePath();
        var ta = new TextArea("Exported: " + path);
        ta.setEditable(false); ta.setWrapText(true);
        ta.setMaxWidth(Double.MAX_VALUE); ta.setMaxHeight(Double.MAX_VALUE);
        var alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Export Success");
        alert.getDialogPane().setContent(ta);
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

        String suggested = String.format("sales_%s_%s_%s.xlsx",
                statusFilter, datePart, java.time.LocalDateTime.now().format(TS_FMT));

        var fc = new javafx.stage.FileChooser();
        fc.setTitle("Save Sales Report");
        fc.getExtensionFilters().add(new javafx.stage.FileChooser.ExtensionFilter("Excel Workbook (*.xlsx)", "*.xlsx"));
        fc.setInitialFileName(suggested);

        java.io.File file = fc.showSaveDialog(transactionANDsales_pane.getScene().getWindow());
        if (file == null) return;
        if (!file.getName().toLowerCase().endsWith(".xlsx")) {
            file = new java.io.File(file.getParent(), file.getName() + ".xlsx");
        }
        exportTransactionsExcel(file);
    }

    private void exportTransactionsExcel(java.io.File file) {
        try (var wb = new org.apache.poi.xssf.usermodel.XSSFWorkbook()) {
            var sh = wb.createSheet("Sales");
            var bold = wb.createFont(); bold.setBold(true);
            var head = wb.createCellStyle(); head.setFont(bold);

            String[] headers = {
                "ID","DateTime","Buyer","Fish Type","Qty","Unit Price","Total","Payment Method","Status"
            };
            
            int r = 0;
            var hr = sh.createRow(r++);
            for (int i = 0; i < headers.length; i++) {
                var c = hr.createCell(i); c.setCellValue(headers[i]); c.setCellStyle(head);
            }

            for (var x : transSorted) {
                var row = sh.createRow(r++); int c = 0;
                row.createCell(c++).setCellValue(x.getTransactionId());
                row.createCell(c++).setCellValue(x.getTxnDate() == null ? "" : x.getTxnDate().toString());
                row.createCell(c++).setCellValue(x.getConsumerName()!=null && !x.getConsumerName().isBlank() ? x.getConsumerName() : x.getBuyerName());
//                row.createCell(c++).setCellValue(x.getBuyerName());
//                row.createCell(c++).setCellValue(x.getFisherfolkName());
                row.createCell(c++).setCellValue(x.getSpeciesName());
                row.createCell(c++).setCellValue(x.getQtySold());
                row.createCell(c++).setCellValue(x.getUnitPrice());
                row.createCell(c++).setCellValue(x.getTotalPrice());
                row.createCell(c++).setCellValue(x.getPaymentMethod());
                row.createCell(c++).setCellValue(x.getPaymentStatus());
            }
            for (int i = 0; i < headers.length; i++) sh.autoSizeColumn(i);

            try (var out = new java.io.FileOutputStream(file)) { wb.write(out); }
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
        hideErrors();

        // clear fields
//        if (transacBuyer_tf != null) transacBuyer_tf.clear();
        transacUnitPrice_tf.clear();
        transacQuantity_tf.clear();
        transacRemarks_ta.clear();
        transacRemainingQuantity_label.setText("");
        transacTotalAmount_label.setText(PHP.format(0));

        // combos
        transacConsumer_cb.getSelectionModel().clearSelection();
        transacFishType_cb.setItems(mysqlconnect.loadInventoryOptionsAvailable()); // refresh live stock

        transacPaymentMethod_cb.getSelectionModel().clearSelection();
        transacPaymentStatus_cb.getSelectionModel().clearSelection();

        // editable fields
        transacConsumer_cb.setDisable(false);
        transacFishType_cb.setDisable(false);
        transacUnitPrice_tf.setEditable(true);

        addNewTransaction_popup.setVisible(true);
        sideNavigation_vbox.setDisable(true);
        transactionANDsales_pane.setDisable(true);
    }


    @FXML
    private void updatePaymentStatus(ActionEvent event) {
        Quantity_err.setText("Please enter quantity");

        TransactionViewRow sel = transaction_tv.getSelectionModel().getSelectedItem();
        if (sel == null) { 
            showInfoWide("Please select a transaction to update."); 
            return; 
        }

        transactionUpdateMode = true;
        editingTransactionId = sel.getTransactionId();
        updateSpeciesId = sel.getSpeciesId();       // <-- species-based, not catch
        hideErrors();

        // Pre-fill editable fields
        transacQuantity_tf.setText(String.format("%.2f", sel.getQtySold()));
        transacUnitPrice_tf.setText(String.format("%.2f", sel.getUnitPrice()));
        transacRemarks_ta.setText(sel.getRemarks());
        transacPaymentMethod_cb.getSelectionModel().select(sel.getPaymentMethod());
        transacPaymentStatus_cb.getSelectionModel().select(sel.getPaymentStatus());

        // Lock Consumer (show current)
        transacConsumer_cb.getItems().setAll(new ConsumerItem(sel.getConsumerId(), sel.getConsumerName()));
        transacConsumer_cb.getSelectionModel().select(0);
        transacConsumer_cb.setDisable(true);

        // Lock Fish Type (show current)
        // Compute remaining for display (excluding this txn so the balance looks honest in edit)
        double purchased = mysqlconnect.speciesPurchasedQty(updateSpeciesId);
        double soldElse  = mysqlconnect.speciesSoldQtyExcluding(updateSpeciesId, editingTransactionId);
        double remaining = Math.max(0, purchased - soldElse);

        InventoryOption locked = new InventoryOption(
            updateSpeciesId,
            sel.getSpeciesName(),
            remaining,
            sel.getUnitPrice() // can also fetch inventory.selling_price 
        );

        transacFishType_cb.getItems().setAll(locked);
        transacFishType_cb.getSelectionModel().select(0);
        transacFishType_cb.setDisable(true);

        transacRemainingQuantity_label.setText(String.format("%.2f kg available", remaining));
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
        refreshLandingsKPIs();
        refreshDashboardKPIs(); //total sales (fully paid)
        loadSalesSeries(); //REPORTS - SALES TREND
        loadFisherfolkContribAuto(); //REPORTS - FISHERFOLK CONTRIBS
        
        refreshInventoryTable();
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

            // text search
            if (!query.isEmpty()) {
                String buyer = (row.getConsumerName() != null && !row.getConsumerName().isBlank())
                                 ? row.getConsumerName() : row.getBuyerName();
                String buyerL = buyer == null ? "" : buyer.toLowerCase();
                String speciesL = row.getSpeciesName() == null ? "" : row.getSpeciesName().toLowerCase();
                if (!(buyerL.contains(query) || speciesL.contains(query))) return false;
            }

            // status filter (unchanged)
            if (statusFilter != null) {
                String ps = row.getPaymentStatus();
                if (ps == null || !ps.equalsIgnoreCase(statusFilter)) return false;
            }

            // date range (unchanged)
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
//        Seller_err.setVisible(false);
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
//        // restore full sellers list
//        allSellers = mysqlconnect.loadActiveFisherfolkItems();
//        transacSeller_cb.setItems(allSellers);
//        transacSeller_cb.setDisable(false);
//        transacSeller_cb.getSelectionModel().clearSelection();
        
        allConsumers = mysqlconnect.loadActiveConsumers();
        transacConsumer_cb.setItems(allConsumers);
        transacConsumer_cb.setDisable(false);
        transacConsumer_cb.getSelectionModel().clearSelection();

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
        transacConsumer_cb.setDisable(false);
        transacFishType_cb.setDisable(false);

        // clear selections/fields for next Add
        transacConsumer_cb.getSelectionModel().clearSelection();
        transacFishType_cb.getItems().clear();       // will repopulate after seller is picked
//        transacBuyer_tf.clear();
        transacQuantity_tf.clear();
        transacUnitPrice_tf.clear();
        transacRemarks_ta.clear();
        transacPaymentMethod_cb.getSelectionModel().clearSelection();
        transacPaymentStatus_cb.getSelectionModel().clearSelection();
        transacRemainingQuantity_label.setText("");
        transacTotalAmount_label.setText("₱0.00");

        transactionUpdateMode = false;
        editingTransactionId = null;
        updateSpeciesId = null;
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
//            transacBuyer_tf.clear();
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
            // ADD
            ConsumerItem consumer = transacConsumer_cb.getValue();
            if (consumer == null) { Buyer_err.setText("Please select a consumer"); Buyer_err.setVisible(true); return; }

            InventoryOption io = transacFishType_cb.getValue();
            if (io == null) { FishType_err.setVisible(true); return; }

            // oversell check (species-based)
            double purchased = mysqlconnect.speciesPurchasedQty(io.getSpeciesId());
            double sold      = mysqlconnect.speciesSoldQtyExcluding(io.getSpeciesId(), null);
            double remaining = purchased - sold;

            if (qty > remaining + 1e-6) {
                Quantity_err.setText("Quantity exceeds remaining (" + String.format("%.2f", remaining) + " kg left).");
                Quantity_err.setVisible(true);
                return;
            }

            String buyerText = consumer.getName(); // safe non-null for legacy column
            ok = mysqlconnect.insertTransactionByConsumer(
                    consumer.getId(), io.getSpeciesId(),
                    qty, unit, pm, ps, remarks, buyerText
            );

        } else {
            // UPDATE (keep species the same)
            if (editingTransactionId == null || updateSpeciesId == null) {
                showInfoWide("No transaction selected.");
                return;
            }

            // oversell check, excluding THIS txn's qty
            double purchased = mysqlconnect.speciesPurchasedQty(updateSpeciesId);
            double soldElse  = mysqlconnect.speciesSoldQtyExcluding(updateSpeciesId, editingTransactionId);
            double remaining = purchased - soldElse;

            if (qty > remaining + 1e-6) {
                Quantity_err.setText("Exceeds remaining (" + String.format("%.2f", remaining) + " kg left).");
                Quantity_err.setVisible(true);
                return;
            }

            // Keep buyer_name aligned with the (locked) consumer combo if present
            ConsumerItem selectedConsumer = transacConsumer_cb.getValue();
            String buyerText = (selectedConsumer != null && selectedConsumer.getName() != null)
                    ? selectedConsumer.getName()
                    : "Unknown Buyer";

            ok = mysqlconnect.updateTransactionKeepBuyer(
                    editingTransactionId, buyerText, qty, unit, pm, ps, remarks
            );
        }

        if (ok) {
            showInfoWide(transactionUpdateMode ? "Transaction updated." : "Transaction added.");
            Quantity_err.setText("Please enter quantity");
            addNewTransaction_popup.setVisible(false);
            refreshTransactionsTable();

            transactionUpdateMode = false;
            editingTransactionId = null;
            updateSpeciesId = null;

            exitTransactionPopup();
        } else {
            showInfoWide("Save failed. Please check your inputs.");
        }

             // refresh KPIs/charts
            refreshTransactionsTable();
            refreshLandingsKPIs();
            refreshDashboardKPIs();//total sales (fully paid)
            loadSalesSeries(); //REPORTS - SALES TREND
            loadFisherfolkContribAuto();//REPORTS - FISHERFOLK CONTRIBS
            
            refreshInventoryTable();
    }
    
    @FXML
    private void addNewBuyer_onTransact(ActionEvent event) {
        editingConsumerId = null;
        clearConsumerForm();
        hideConsumerErrors();
        addNewConsumer_popup.setVisible(true);
        consumer_pane.setDisable(true);
        sideNavigation_vbox.setDisable(true);
        
        transactionANDsales_pane.setDisable(true);
        addNewTransaction_popup.setDisable(true);
    }
    
    
    ////////////////////////////////////////////////////////////////////////////end of transaction & sales
    
    ////////////////////////////////////////////////////////////////////////////DOCK LOGS
   @FXML
    private void dockLog_generateReport(ActionEvent e) {
        var rows = new java.util.ArrayList<>(dockFiltered);
        if (rows.isEmpty()) { showInfoWide("Nothing to export."); return; }

        String ts = java.time.LocalDateTime.now()
            .format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));

        var fc = new javafx.stage.FileChooser();
        fc.setTitle("Save Dock Logs Report");
        fc.getExtensionFilters().add(new javafx.stage.FileChooser.ExtensionFilter("Excel Workbook (*.xlsx)", "*.xlsx"));
        fc.setInitialFileName("dock_logs_" + ts + ".xlsx");

        java.io.File file = fc.showSaveDialog(dockLogs_tv.getScene().getWindow());
        if (file == null) return;
        if (!file.getName().toLowerCase().endsWith(".xlsx")) {
            file = new java.io.File(file.getParent(), file.getName() + ".xlsx");
        }

        try (var wb = new org.apache.poi.xssf.usermodel.XSSFWorkbook()) {
            var sh = wb.createSheet("Dock Logs");
            var bold = wb.createFont(); bold.setBold(true);
            var head = wb.createCellStyle(); head.setFont(bold);

            String[] headers = {"LogID","Fisherfolk","Boat","DockingDate","Arrival","Departure","Remarks"};
            int r = 0;
            var hr = sh.createRow(r++);
            for (int i = 0; i < headers.length; i++) {
                var c = hr.createCell(i); c.setCellValue(headers[i]); c.setCellStyle(head);
            }

            for (var x : rows) {
                var row = sh.createRow(r++); int c = 0;
                row.createCell(c++).setCellValue(x.getLogId());
                row.createCell(c++).setCellValue(x.getFisherfolkName());
                row.createCell(c++).setCellValue(x.getGear());
//                row.createCell(c++).setCellValue(x.getBoatName());
                row.createCell(c++).setCellValue(x.getDockingDate() == null ? "" : x.getDockingDate().toString());
                row.createCell(c++).setCellValue(x.getArrivalTime() == null ? "" : x.getArrivalTime().toString());
                row.createCell(c++).setCellValue(x.getDepartureTime() == null ? "" : x.getDepartureTime().toString());
                row.createCell(c++).setCellValue(x.getRemarks());
            }
            for (int i = 0; i < headers.length; i++) sh.autoSizeColumn(i);

            try (var out = new java.io.FileOutputStream(file)) { wb.write(out); }
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
                String hay = (row.getFisherfolkName() + " " + (row.getGear()==null?"":row.getGear())).toLowerCase();
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
        dockLogGear_label.setText("—");
//        dockLogBoatName_label.setText("—");

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
        var item = new FisherfolkItem(sel.getFisherfolkId(), sel.getFisherfolkName(), sel.getGear());
        dockLogFisherfolk_cb.getItems().setAll(item);
        dockLogFisherfolk_cb.getSelectionModel().select(0);
        dockLogFisherfolk_cb.setDisable(true);

//        dockLogBoatName_label.setText(item.getBoatName() == null ? "—" : item.getBoatName());
        dockLogGear_label.setText(item.getGear() == null ? "—" : item.getGear());
        
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
            dockLogGear_label.setText("—");
//            dockLogBoatName_label.setText("—");
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
        var chooser = new javafx.stage.FileChooser();
        chooser.setTitle("Export Sales Trend (Excel)");
        chooser.getExtensionFilters().add(
            new javafx.stage.FileChooser.ExtensionFilter("Excel Workbook (*.xlsx)", "*.xlsx")
        );
        String period = rbDaily.isSelected() ? "daily" : rbWeekly.isSelected() ? "weekly" : "monthly";
        String ts = java.time.LocalDateTime.now().toString().replace(':','-').substring(0,19);
        chooser.setInitialFileName("sales_trend_" + period + "_" + ts + ".xlsx");

        java.io.File xlsx = chooser.showSaveDialog(salesChart.getScene().getWindow());
        if (xlsx == null) return;
        if (!xlsx.getName().toLowerCase().endsWith(".xlsx")) {
            xlsx = new java.io.File(xlsx.getParentFile(), xlsx.getName() + ".xlsx");
        }

        var series = salesChart.getData().isEmpty() ? null : salesChart.getData().get(0);
        if (series == null) { showInfo("No data to export."); return; }

        try (var wb = new org.apache.poi.xssf.usermodel.XSSFWorkbook()) {
            var sh = wb.createSheet("Sales Trend");
            var bold = wb.createFont(); bold.setBold(true);
            var head = wb.createCellStyle(); head.setFont(bold);

            var hr = sh.createRow(0);
            hr.createCell(0).setCellValue("Period");
            hr.createCell(1).setCellValue("RevenuePHP");
            hr.getCell(0).setCellStyle(head);
            hr.getCell(1).setCellStyle(head);

            int r = 1;
            for (var dp : series.getData()) {
                var row = sh.createRow(r++);
                row.createCell(0).setCellValue(String.valueOf(dp.getXValue()));
                row.createCell(1).setCellValue(dp.getYValue().doubleValue());
            }
            sh.autoSizeColumn(0); sh.autoSizeColumn(1);

            try (var out = new java.io.FileOutputStream(xlsx)) { wb.write(out); }
        } catch (Exception ex) {
            ex.printStackTrace();
            showInfo("Export failed: " + ex.getMessage());
            return;
        }

        // save PNG snapshot next to Excel
        String base = xlsx.getName();
        int dot = base.lastIndexOf('.');
        if (dot > 0) base = base.substring(0, dot);
        java.io.File png = new java.io.File(xlsx.getParentFile(), base + ".png");
        boolean okPng = snapshotToPng(salesChart, png);

        showInfo("Exported:\n" + xlsx.getAbsolutePath() + (okPng ? "\n" + png.getAbsolutePath() : ""));
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
        
        loadSpeciesOptions(); //on adding catches/landings
    }

    @FXML
    private void btnExport_SPECIES(ActionEvent event) {
        var chooser = new javafx.stage.FileChooser();
        chooser.setTitle("Export species");
        chooser.getExtensionFilters().add(
            new javafx.stage.FileChooser.ExtensionFilter("Excel Workbook (*.xlsx)", "*.xlsx")
        );
        chooser.setInitialFileName("species_" + java.time.LocalDate.now() + ".xlsx");
        java.io.File file = chooser.showSaveDialog(species_tv.getScene().getWindow());
        if (file == null) return;
        if (!file.getName().toLowerCase().endsWith(".xlsx")) {
            file = new java.io.File(file.getParent(), file.getName() + ".xlsx");
        }

        try (var wb = new org.apache.poi.xssf.usermodel.XSSFWorkbook()) {
            var sh = wb.createSheet("Species");
            var bold = wb.createFont(); bold.setBold(true);
            var head = wb.createCellStyle(); head.setFont(bold);

            String[] headers = {"ID","Name","Description"};
            var hr = sh.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                var c = hr.createCell(i); c.setCellValue(headers[i]); c.setCellStyle(head);
            }

            int r = 1;
            for (SpeciesItem s : speciesSorted) {
                var row = sh.createRow(r++); int c = 0;
                row.createCell(c++).setCellValue(s.getId());
                row.createCell(c++).setCellValue(s.getSpeciesName());
                row.createCell(c++).setCellValue(s.getDescription());
            }
            for (int i = 0; i < headers.length; i++) sh.autoSizeColumn(i);

            try (var out = new java.io.FileOutputStream(file)) { wb.write(out); }
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
        loadSpeciesOptions(); //on adding catches/landings
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
    
//    @FXML
//    private void manualBackup_btn(ActionEvent event) {
//        var fc = new javafx.stage.FileChooser();
//        fc.setTitle("Save SQL Backup");
//        fc.getExtensionFilters().add(new javafx.stage.FileChooser.ExtensionFilter("SQL Files", "*.sql"));
//        fc.setInitialFileName("fish_landing_backup_" + nowStamp() + ".sql");
//        var file = fc.showSaveDialog(lastBackup_label.getScene().getWindow());
//        if (file == null) return;
//
//        boolean ok = runMysqldump(file.getAbsolutePath());
//        if (ok) {
//            rememberLastBackupNow();
//            showInfo("Backup saved:\n" + file.getAbsolutePath());
//        } else {
//            showWarn("Backup failed. Make sure mysqldump is installed and PATH is set.");
//        }
//    }
    

    @FXML
    private void restoreData_browseFilesBTN(ActionEvent event) {
        var fc = new javafx.stage.FileChooser();
        fc.setTitle("Choose SQL Backup to Restore");
        fc.getExtensionFilters().add(new javafx.stage.FileChooser.ExtensionFilter("SQL Files", "*.sql"));
        var file = fc.showOpenDialog(lastBackup_label.getScene().getWindow());
        if (file == null) return;

        var confirm = new javafx.scene.control.Alert(
                javafx.scene.control.Alert.AlertType.WARNING,
                "This will OVERWRITE all data in " + DB_NAME + ". This cannot be undone.\n\nProceed?",
                javafx.scene.control.ButtonType.YES, javafx.scene.control.ButtonType.NO);
        confirm.setHeaderText("Restore database?");
        confirm.showAndWait();
        if (confirm.getResult() != javafx.scene.control.ButtonType.YES) return;

        boolean ok = runMysqlSource(file.getAbsolutePath());
        if (ok) {
            showInfo("Restore complete.\n" + file.getAbsolutePath());
            // TODO: refresh UI tables here
            // e.g., refreshFishermen(); refreshCatches(); refreshTransactions(); refreshDockLogs();
            initUserProfile();
            
            species_tv.refresh();
            
            loadCatchVolumesAuto();
            loadFisherfolkContribAuto();
            loadSpeciesDistributionAuto();
            loadSalesSeries();
            
            refreshDockLogsTable();
            refreshTransactionsTable();
            reloadFisherTable();
            LANDINGS_SEARCH();
            
            refreshDockStats();
            updateTransHeaderKpis();
            
            loadFisherfolkOptions();
            loadSpeciesOptions();
            
            refreshDashboardKPIs();
            loadMonthlyLandingsTrend();
            loadWeeklyLandingsTrend();
            dashboardRecentFishLanding_tv.setItems(loadRecentLandings(5));
            loadDashboardTopFishTypesPie(4); // top-4; 
             
            initAutoBackupUI();        
            refreshLastBackupLabel();  

        } else {
            showWarn("Restore failed. Ensure 'mysql' client is available and the SQL file is valid.");
        }
    }

    private boolean runMysqldump(String outSqlPath) {
        try {
            var pb = new ProcessBuilder(
                    MYSQLDUMP,
                    "-h", DB_HOST,
                    "-P", DB_PORT,
                    "-u", DB_USER,
                    "--password=" + DB_PASS,
                    "--databases", DB_NAME,
                    "--routines",
                    "--triggers",
                    "--events"
            );
            // direct process stdout to file
            pb.redirectOutput(new java.io.File(outSqlPath));
            pb.redirectError(ProcessBuilder.Redirect.INHERIT);
            var p = pb.start();
            int code = p.waitFor();
            return code == 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private boolean runMysqlSource(String sqlPath) {
        try {
            var pb = new ProcessBuilder(
                    MYSQL,
                    "-h", DB_HOST,
                    "-P", DB_PORT,
                    "-u", DB_USER,
                    "--password=" + DB_PASS,
                    DB_NAME,
                    "-e", "SOURCE " + sqlPath
            );
            pb.redirectError(ProcessBuilder.Redirect.INHERIT);
            pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
            var p = pb.start();
            int code = p.waitFor();
            return code == 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @FXML
    private void exportALL_CSV(ActionEvent event) {
        var dc = new javafx.stage.DirectoryChooser();
        dc.setTitle("Choose folder to export CSV files");
        var dir = dc.showDialog(lastBackup_label.getScene().getWindow());
        if (dir == null) return;

        String stamp = nowStamp();
        int written = 0;
        written += exportTableToCsv("fisherfolk",     new java.io.File(dir, "fisherfolk_" + stamp + ".csv"));
        written += exportTableToCsv("species",        new java.io.File(dir, "species_" + stamp + ".csv"));
        written += exportTableToCsv("catch",          new java.io.File(dir, "catch_" + stamp + ".csv"));
        written += exportTableToCsv("transactions",   new java.io.File(dir, "transactions_" + stamp + ".csv"));
        written += exportTableToCsv("docking_logs",   new java.io.File(dir, "docking_logs_" + stamp + ".csv"));
//        written += exportTableToCsv("users",          new java.io.File(dir, "users_" + stamp + ".csv"));

        lastBackup_label.setText("Last Export (CSV): " + java.time.LocalDateTime.now());
        showInfo("Exported " + written + " CSV file(s) to:\n" + dir.getAbsolutePath());
        
           }

    private int exportTableToCsv(String table, java.io.File file) {
        String sql = "SELECT * FROM " + table;
        try (var c = mysqlconnect.ConnectDb();
             var ps = c.prepareStatement(sql);
             var rs = ps.executeQuery();
             var pw = new java.io.PrintWriter(file, java.nio.charset.StandardCharsets.UTF_8)) {

            var md = rs.getMetaData();
            int cols = md.getColumnCount();

            // header
            for (int i = 1; i <= cols; i++) {
                if (i > 1) pw.print(",");
                pw.print(csv(md.getColumnLabel(i)));
            }
            pw.println();

            // rows
            while (rs.next()) {
                for (int i = 1; i <= cols; i++) {
                    if (i > 1) pw.print(",");
                    String v = rs.getString(i);
                    pw.print(csv(v));
                }
                pw.println();
            }
            return 1;
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    
    @FXML
    private void exportALL_PDF(ActionEvent event) {
        try (var conn = mysqlconnect.ConnectDb()) {
            var file = chooseExportFile("Export All (PDF)", DB_NAME + "_export", ".pdf");
            if (file == null) return;

            var doc = new com.lowagie.text.Document(com.lowagie.text.PageSize.A4.rotate(), 24, 24, 24, 24);
            com.lowagie.text.pdf.PdfWriter.getInstance(doc, new java.io.FileOutputStream(file));
            doc.open();

            var titleFont = new com.lowagie.text.Font(com.lowagie.text.Font.HELVETICA, 18, com.lowagie.text.Font.BOLD);
            var headFont  = new com.lowagie.text.Font(com.lowagie.text.Font.HELVETICA, 11, com.lowagie.text.Font.BOLD);
            var cellFont  = new com.lowagie.text.Font(com.lowagie.text.Font.HELVETICA, 10);

            doc.add(new com.lowagie.text.Paragraph("Fish Landing – Full Data Export", titleFont));
            doc.add(new com.lowagie.text.Paragraph(
                "Generated: " + java.time.LocalDateTime.now() + "\n\n"));

            for (var spec : exportTables()) {
                var t = fetchTable(conn, spec);

                // Section header
                var h = new com.lowagie.text.Paragraph("\n" + t.name.toUpperCase(), headFont);
                doc.add(h);

                var table = new com.lowagie.text.pdf.PdfPTable(t.headers.size());
                table.setWidthPercentage(100);

                // headers
                for (var hd : t.headers) {
                    var cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(hd, headFont));
                    cell.setGrayFill(0.92f);
                    table.addCell(cell);
                }

                // rows
                for (var row : t.rows) {
                    for (Object v : row) {
                        var ph = new com.lowagie.text.Phrase(v == null ? "" : String.valueOf(v), cellFont);
                        table.addCell(new com.lowagie.text.pdf.PdfPCell(ph));
                    }
                }
                doc.add(table);
            }

            doc.close();

            lastBackup_label.setText("Last Export (PDF): " + java.time.LocalDateTime.now());
            showInfoWide("Exported to:\n" + file.getAbsolutePath());
        } catch (Exception ex) {
            ex.printStackTrace();
            showInfoWide("PDF export failed: " + ex.getMessage());
        }
    }

    @FXML
    private void exportALL_EXCEL(ActionEvent event) {
        try (var conn = mysqlconnect.ConnectDb()) {
            var file = chooseExportFile("Export All (Excel)", DB_NAME + "_export", ".xlsx");
            if (file == null) return;

            try (var wb = new org.apache.poi.xssf.usermodel.XSSFWorkbook()) {
                for (var spec : exportTables()) {
                    var t = fetchTable(conn, spec);
                    var sheet = wb.createSheet(t.name);

                    // header style (bold)
                    var bold = wb.createFont(); bold.setBold(true);
                    var headStyle = wb.createCellStyle(); headStyle.setFont(bold);

                    int r = 0;
                    var hr = sheet.createRow(r++);
                    for (int i = 0; i < t.headers.size(); i++) {
                        var cell = hr.createCell(i);
                        cell.setCellValue(t.headers.get(i));
                        cell.setCellStyle(headStyle);
                    }

                    for (var row : t.rows) {
                        var rr = sheet.createRow(r++);
                        for (int i = 0; i < row.size(); i++) {
                            var cell = rr.createCell(i);
                            Object v = row.get(i);
                            if (v == null) cell.setBlank();
                            else if (v instanceof Number n) cell.setCellValue(n.doubleValue());
                            else if (v instanceof java.sql.Date d) cell.setCellValue(d.toLocalDate().toString());
                            else if (v instanceof java.sql.Time ttime) cell.setCellValue(ttime.toLocalTime().toString());
                            else if (v instanceof java.sql.Timestamp ts) cell.setCellValue(ts.toLocalDateTime().toString());
                            else cell.setCellValue(String.valueOf(v));
                        }
                    }
                    for (int i = 0; i < t.headers.size(); i++) sheet.autoSizeColumn(i);
                }
                try (var out = new java.io.FileOutputStream(file)) { wb.write(out); }
            }

            lastBackup_label.setText("Last Export (Excel): " + java.time.LocalDateTime.now());
            showInfoWide("Exported to:\n" + file.getAbsolutePath());
        } catch (Exception ex) {
            ex.printStackTrace();
            showInfoWide("Excel export failed: " + ex.getMessage());
        }
    }
    
    private java.io.File chooseExportFile(String title, String baseName, String ext) {
        javafx.stage.FileChooser fc = new javafx.stage.FileChooser();
        fc.setTitle(title);
        fc.setInitialFileName(baseName + "_" +
                java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd_HHmm")) + ext);
        
        fc.getExtensionFilters().add(new javafx.stage.FileChooser.ExtensionFilter(
                ext.equals(".pdf") ? "PDF Files" : ext.equals(".xlsx") ? "Excel Files" : "All Files",
                "*" + ext));
        
        java.io.File file = fc.showSaveDialog(null);
        if (file == null) return null;
        
        // 🔑 Append extension if user didn’t type it
        if (!file.getName().toLowerCase().endsWith(ext)) {
            file = new java.io.File(file.getAbsolutePath() + ext);
        }
        return file;
    }


    private void showWarn(String msg) {
        var a = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.WARNING, msg, javafx.scene.control.ButtonType.OK);
        a.setHeaderText(null); a.showAndWait();
    }

// ==== EXPORT HELPERS =========================================================
    private static class TableSpec {
        final String name, sql;
        TableSpec(String name, String sql) { this.name = name; this.sql = sql; }
    }

    // All tables wanted to export (can add views too)
    private java.util.List<TableSpec> exportTables() {
        return java.util.List.of(
            new TableSpec("fisherfolk",     "SELECT * FROM fisherfolk ORDER BY fisherfolk_id"),
            new TableSpec("species",        "SELECT * FROM species ORDER BY species_id"),
            new TableSpec("catch",          "SELECT * FROM catch ORDER BY catch_id"),
            new TableSpec("transactions",   "SELECT * FROM transactions ORDER BY transaction_id"),
            new TableSpec("docking_logs",   "SELECT * FROM docking_logs ORDER BY log_id")
            // new TableSpec("v_catch_available", "SELECT * FROM v_catch_available") // optional
        );
    }


    // Read a SQL query into an in-memory table (headers + rows)
    private static class TableData {
        final String name;
        final java.util.List<String> headers = new java.util.ArrayList<>();
        final java.util.List<java.util.List<Object>> rows = new java.util.ArrayList<>();
        TableData(String name) { this.name = name; }
    }

    private TableData fetchTable(java.sql.Connection c, TableSpec spec) throws Exception {
        try (var ps = c.prepareStatement(spec.sql); var rs = ps.executeQuery()) {
            var md = rs.getMetaData();
            var t = new TableData(spec.name);
            for (int i = 1; i <= md.getColumnCount(); i++) t.headers.add(md.getColumnLabel(i));
            while (rs.next()) {
                var row = new java.util.ArrayList<Object>(md.getColumnCount());
                for (int i = 1; i <= md.getColumnCount(); i++) row.add(rs.getObject(i));
                t.rows.add(row);
            }
            return t;
        }
    }
    ////////////////////////////////////////////////////////////////////////////end of data information
    ////////////////////////////////////////////////////////////////////////////DASHBOARD
    // --- Formatting helpers ---
    private static String peso(double v) {
        return "₱" + String.format("%,.2f", v);
    }
    private static String kg(double v) {
        return String.format("%,.2f kg", v);
    }
    private static double pctChange(double current, double previous) {
        if (previous == 0) return current == 0 ? 0.0 : 100.0; // define as +100% when prev=0 and current>0
        return (current - previous) / previous * 100.0;
    }
    private void setDeltaLabel(Label label, double pct) {
        String arrow = pct >= 0 ? "▲" : "▼";
        String color = pct >= 0 ? "#22c55e" : "#e85760";
        label.setStyle("-fx-text-fill: " + color + "; -fx-font-weight: bold;");
        label.setText(arrow + " " + String.format("%.1f%%", Math.abs(pct)));
    }

    // --- SQL runner helpers ---
    private double queryDouble(String sql) {
        try (var c = mysqlconnect.ConnectDb();
             var ps = c.prepareStatement(sql);
             var rs = ps.executeQuery()) {
            return rs.next() ? rs.getDouble(1) : 0.0;
        } catch (Exception e) { e.printStackTrace(); return 0.0; }
    }
    private double queryDouble(String sql, java.sql.Timestamp t1, java.sql.Timestamp t2) {
        try (var c = mysqlconnect.ConnectDb();
             var ps = c.prepareStatement(sql)) {
            ps.setTimestamp(1, t1);
            ps.setTimestamp(2, t2);
            try (var rs = ps.executeQuery()) {
                return rs.next() ? rs.getDouble(1) : 0.0;
            }
        } catch (Exception e) { e.printStackTrace(); return 0.0; }
    }
    private double queryDouble(String sql, java.sql.Date d1, java.sql.Date d2) {
        try (var c = mysqlconnect.ConnectDb();
             var ps = c.prepareStatement(sql)) {
            ps.setDate(1, d1);
            ps.setDate(2, d2);
            try (var rs = ps.executeQuery()) {
                return rs.next() ? rs.getDouble(1) : 0.0;
            }
        } catch (Exception e) { e.printStackTrace(); return 0.0; }
    }
//////
    private double queryDouble(String sql, java.sql.Timestamp t1) {
        try (var c = mysqlconnect.ConnectDb();
             var ps = c.prepareStatement(sql)) {
            ps.setTimestamp(1, t1);
            try (var rs = ps.executeQuery()) {
                return rs.next() ? rs.getDouble(1) : 0.0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0.0;
        }
    }
    
    public void refreshDashboardKPIs() {
        // Dates we’ll need
        var today = java.time.LocalDate.now();
        var monthStart = today.withDayOfMonth(1);
        var lastMonthEnd = monthStart.minusDays(1);
        var lastMonthStart = lastMonthEnd.withDayOfMonth(1);

        // --- 1) Today’s Landing (kg) ---
        double todaysLanding = queryDouble(
            "SELECT IFNULL(SUM(quantity),0) " +
            "FROM catch WHERE catch_date = CURDATE()"
        );

        // Baseline: average daily landing last month
        double lastMonthDays = lastMonthEnd.getDayOfMonth();
        double lastMonthLandingTotal = queryDouble(
            "SELECT IFNULL(SUM(quantity),0) " +
            "FROM catch WHERE catch_date BETWEEN ? AND ?",
            java.sql.Date.valueOf(lastMonthStart),
            java.sql.Date.valueOf(lastMonthEnd)
        );
        double lastMonthDailyAvg = lastMonthDays == 0 ? 0.0 : lastMonthLandingTotal / lastMonthDays;

        TodaysLandingInKG_label.setText(kg(todaysLanding));
        setDeltaLabel(TodaysLandingInKG_increaseORdecreasePercentageVsLastMonth_label,
                      pctChange(todaysLanding, lastMonthDailyAvg));

        // --- 2) Total Fish Stock (kg): snapshot now vs snapshot at last month end ---
        // Current stock = sum(catch.quantity - sold.soFar) across all catches
        double currentStock = queryDouble(
            "SELECT IFNULL(SUM(c.quantity - IFNULL(s.sold,0)),0) AS stock " +
            "FROM catch c " +
            "LEFT JOIN (SELECT catch_id, SUM(quantity_sold) sold FROM transactions GROUP BY catch_id) s " +
            "       ON s.catch_id = c.catch_id"
        );

        // Stock as of last month end (include catches up to lastMonthEnd and sales up to lastMonthEnd)
        double stockLastMonthEnd = queryDouble(
            "SELECT IFNULL(SUM(c.quantity - IFNULL(s.sold,0)),0) AS stock " +
            "FROM catch c " +
            "LEFT JOIN (" +
            "  SELECT catch_id, SUM(quantity_sold) sold " +
            "  FROM transactions WHERE transaction_date <= ? GROUP BY catch_id" +
            ") s ON s.catch_id = c.catch_id " +
            "WHERE c.catch_date <= DATE(?)",
            java.sql.Timestamp.valueOf(lastMonthEnd.atTime(23,59,59)),
            java.sql.Timestamp.valueOf(lastMonthEnd.atTime(23,59,59))
        );


        TotalFishStockInKg_label.setText(kg(currentStock));
        setDeltaLabel(TotalFishStockInKG_increaseOrDecreasePercentageVsLastMonth_label,
                      pctChange(currentStock, stockLastMonthEnd));

        // --- 3) Total Registered Fishermen (active) + MoM change ---
        // Current count (active only)
        double currentFishermen = queryDouble("SELECT COUNT(*) FROM fisherfolk WHERE is_active=1");

        // Last month baseline:
        // If you have fisherfolk.created_at, we can approximate "registered up to last month end"
        double fishermenLastMonth = currentFishermen; // fallback
        try {
            
            fishermenLastMonth = queryDouble(
                "SELECT COUNT(*) FROM fisherfolk " +
                "WHERE is_active=1 AND created_at <= ?",//created_at <= ?
                java.sql.Timestamp.valueOf(lastMonthEnd.atTime(23,59,59))
            );

        } catch (Exception ignore) {
            // Table may not have created_at; we’ll just show 0% change
            fishermenLastMonth = currentFishermen;
        }

        TotalRegisteredFishermen_label.setText(String.format("%,.0f", currentFishermen));
        setDeltaLabel(RegisteredFishermenVsLastMonth_percentageLabel,
                      pctChange(currentFishermen, fishermenLastMonth));

        // --- 4) Total Sales in ₱ (Month-to-Date) + MoM change ---
        // Decide: revenue = Paid only? If yes, filter payment_status='Paid'.
        boolean paidOnly = true;

        String filterPaid = paidOnly ? " AND payment_status='Paid' " : " ";
        double salesMTD = queryDouble(
            "SELECT IFNULL(SUM(total_price),0) FROM transactions " +
            "WHERE transaction_date >= ? AND transaction_date < ?" + filterPaid,
            java.sql.Timestamp.valueOf(monthStart.atStartOfDay()),
            java.sql.Timestamp.valueOf(today.plusDays(1).atStartOfDay())
        );

        double salesLastMonth = queryDouble(
            "SELECT IFNULL(SUM(total_price),0) FROM transactions " +
            "WHERE transaction_date >= ? AND transaction_date <= ?" + filterPaid,
            java.sql.Timestamp.valueOf(lastMonthStart.atStartOfDay()),
            java.sql.Timestamp.valueOf(lastMonthEnd.atTime(23,59,59))
        );

        TotalSalesInPhp_label.setText(peso(salesMTD));
        setDeltaLabel(TotalSalesVsLastMonth_percentageLabel,
                      pctChange(salesMTD, salesLastMonth));
    }
    
    //recent landings in dashboard
    private javafx.collections.ObservableList<LandingRow> loadRecentLandings(int limit) {
        var list = javafx.collections.FXCollections.<LandingRow>observableArrayList();
        String sql = """
            SELECT f.name AS fisher_name,
                   s.species_name,
                   c.quantity,
                   (c.quantity * c.price_per_kilo) AS value_php
            FROM catch c
            JOIN fisherfolk f ON f.fisherfolk_id = c.fisherfolk_id
            JOIN species s    ON s.species_id     = c.species_id
            ORDER BY c.catch_date DESC, c.catch_id DESC
            LIMIT ?
        """;
        try (var conn = mysqlconnect.ConnectDb();
             var ps   = conn.prepareStatement(sql)) {
            ps.setInt(1, Math.max(1, limit));
            try (var rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new LandingRow(
                            rs.getString("fisher_name"),
                            rs.getString("species_name"),
                            rs.getDouble("quantity"),
                            rs.getDouble("value_php")
                    ));
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    // small DTO for chart points
    private static class Point { final String label; final double kg;
        Point(String label, double kg){ this.label=label; this.kg=kg; }}

    // 6a) Weekly: last 7 days (including today)
    private java.util.List<Point> loadWeeklyLandingSeries() {
        var out = new java.util.ArrayList<Point>();
        String sql = """
            SELECT DATE(catch_date) d, IFNULL(SUM(quantity),0) kg
            FROM catch
            WHERE catch_date BETWEEN CURDATE() - INTERVAL 6 DAY AND CURDATE()
            GROUP BY DATE(catch_date)
        """;
        // Build a map of date->kg from DB
        var map = new java.util.HashMap<java.time.LocalDate, Double>();
        try (var c = mysqlconnect.ConnectDb();
             var ps = c.prepareStatement(sql);
             var rs = ps.executeQuery()) {
            while (rs.next()) {
                java.sql.Date d = rs.getDate("d");
                map.put(d.toLocalDate(), rs.getDouble("kg"));
            }
        } catch (Exception e) { e.printStackTrace(); }

        // Ensure all 7 days present (fill zeros)
        var today = java.time.LocalDate.now();
        for (int i = 6; i >= 0; i--) {
            var day = today.minusDays(i);
            double kg = map.getOrDefault(day, 0.0);
            out.add(new Point(day.getMonthValue() + "/" + day.getDayOfMonth(), kg)); // e.g., 9/25
        }
        return out;
    }

    // 6b) Monthly: this year by month (Jan..Dec) or last 6 months; here: this year
    private java.util.List<Point> loadMonthlyLandingSeries() {
        var out = new java.util.ArrayList<Point>();
        int year = java.time.LocalDate.now().getYear();
        String sql = """
            SELECT MONTH(catch_date) m, IFNULL(SUM(quantity),0) kg
            FROM catch
            WHERE YEAR(catch_date) = ?
            GROUP BY MONTH(catch_date)
            ORDER BY m
        """;
        var map = new java.util.HashMap<Integer, Double>();
        try (var c = mysqlconnect.ConnectDb();
             var ps = c.prepareStatement(sql)) {
            ps.setInt(1, year);
            try (var rs = ps.executeQuery()) {
                while (rs.next()) {
                    map.put(rs.getInt("m"), rs.getDouble("kg"));
                }
            }
        } catch (Exception e) { e.printStackTrace(); }

        java.time.format.TextStyle style = java.time.format.TextStyle.SHORT; // JAN, FEB…
        var locale = java.util.Locale.getDefault();
        for (int m = 1; m <= 12; m++) {
            double kg = map.getOrDefault(m, 0.0);
            String label = java.time.Month.of(m).getDisplayName(style, locale);
            out.add(new Point(label, kg));
        }
        return out;
    }

    private void loadMonthlyLandingsTrend() {
        dashboardLandingsTrend_lineChart.getData().clear();

        // 12-month rolling window ending this month
        java.time.YearMonth endYM   = java.time.YearMonth.from(java.time.LocalDate.now());
        java.time.YearMonth startYM = endYM.minusMonths(11);

        // Preserve order with LinkedHashMap
        java.util.LinkedHashMap<java.time.YearMonth, Double> totals = new java.util.LinkedHashMap<>();
        for (int i = 0; i < 12; i++) {
            totals.put(startYM.plusMonths(i), 0.0);
        }

        // Query: sum kg per month within [start, end]
        String sql = """
            SELECT YEAR(transaction_date) AS y, MONTH(transaction_date) AS m, SUM(quantity_sold) AS qty
            FROM transactions
            WHERE transaction_date >= ? AND transaction_date < ?
            GROUP BY y, m
            ORDER BY y, m
            """;

        // start inclusive, end *exclusive* (first day of next month)
        java.time.LocalDate start = startYM.atDay(1);
        java.time.LocalDate end   = endYM.plusMonths(1).atDay(1);

        try (var conn = mysqlconnect.ConnectDb();
             var ps = conn.prepareStatement(sql)) {
            ps.setDate(1, java.sql.Date.valueOf(start));
            ps.setDate(2, java.sql.Date.valueOf(end));
            try (var rs = ps.executeQuery()) {
                while (rs.next()) {
                    var ym = java.time.YearMonth.of(rs.getInt("y"), rs.getInt("m"));
                    if (totals.containsKey(ym)) {
                        totals.put(ym, totals.get(ym) + rs.getDouble("qty"));
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // Build axis categories from the same YearMonth keys
        var cats = javafx.collections.FXCollections.<String>observableArrayList();
        var fmt  = java.time.format.DateTimeFormatter.ofPattern("MMM");
        for (var ym : totals.keySet()) cats.add(ym.format(fmt));
        dashboardLIneChart_categAxis.setCategories(cats);

        // Build series in the same order
        var series = new javafx.scene.chart.XYChart.Series<String, Number>();
        series.setName("This year (monthly)");
        dashboardTrendLegend.setText("This year (monthly)");
        for (var entry : totals.entrySet()) {
            String label = entry.getKey().format(fmt);
            series.getData().add(new javafx.scene.chart.XYChart.Data<>(label, entry.getValue()));
        }
        dashboardLandingsTrend_lineChart.getData().add(series);
    }

    private void loadWeeklyLandingsTrend() {
        dashboardLandingsTrend_lineChart.getData().clear();

        var today = java.time.LocalDate.now();
        var start = today.minusDays(6);

        // preserve order for the last 7 days
        var totals = new java.util.LinkedHashMap<java.time.LocalDate, Double>();
        for (int i = 0; i < 7; i++) totals.put(start.plusDays(i), 0.0);

        String sql = """
            SELECT transaction_date, SUM(quantity_sold) AS qty
            FROM transactions
            WHERE transaction_date BETWEEN ? AND ?
            GROUP BY transaction_date
            ORDER BY transaction_date
            """;

        try (var conn = mysqlconnect.ConnectDb();
             var ps = conn.prepareStatement(sql)) {
            ps.setDate(1, java.sql.Date.valueOf(start));
            ps.setDate(2, java.sql.Date.valueOf(today));
            try (var rs = ps.executeQuery()) {
                while (rs.next()) {
                    var d = rs.getDate("transaction_date").toLocalDate();
                    if (totals.containsKey(d)) totals.put(d, rs.getDouble("qty"));
                }
            }
        } catch (Exception ex) { ex.printStackTrace(); }

        var cats = javafx.collections.FXCollections.<String>observableArrayList();
        var fmt  = java.time.format.DateTimeFormatter.ofPattern("EEE");
        for (var d : totals.keySet()) cats.add(d.format(fmt));
        dashboardLIneChart_categAxis.setCategories(cats);

        var series = new javafx.scene.chart.XYChart.Series<String, Number>();
        series.setName("Last 7 days");
        dashboardTrendLegend.setText("Last 7 days");
        for (var e : totals.entrySet()) {
            series.getData().add(new javafx.scene.chart.XYChart.Data<>(e.getKey().format(fmt), e.getValue()));
        }
        dashboardLandingsTrend_lineChart.getData().add(series);
        
    }

    //top fish type in dashboard
   // --- DASHBOARD: Top fish types (kg) ---
    
    private void loadDashboardTopFishTypesPie(int topN) {
        final String sql = """
            SELECT s.species_name, SUM(c.quantity) AS total_kg
            FROM catch c
            JOIN species s ON s.species_id = c.species_id
            WHERE YEAR(c.catch_date) = YEAR(CURDATE())
            GROUP BY s.species_name
            ORDER BY total_kg DESC
            LIMIT ?
            """;

        var rows = new java.util.ArrayList<javafx.scene.chart.PieChart.Data>();
        double grand = 0.0;

        try (var conn = mysqlconnect.ConnectDb();
             var ps = conn.prepareStatement(sql)) {
            ps.setInt(1, topN);
            try (var rs = ps.executeQuery()) {
                while (rs.next()) {
                    String name = rs.getString("species_name");
                    double kg   = rs.getDouble("total_kg");
                    rows.add(new javafx.scene.chart.PieChart.Data(name, kg));
                    grand += kg;
                }
            }
        } catch (Exception ex) { ex.printStackTrace(); }

        var data = javafx.collections.FXCollections.<javafx.scene.chart.PieChart.Data>observableArrayList();
        for (var r : rows) {
            double pct = grand <= 0 ? 0.0 : r.getPieValue() / grand * 100.0;
            String label = String.format("%s (%.1f%%, %.2f kg)", r.getName(), pct, r.getPieValue());
            data.add(new javafx.scene.chart.PieChart.Data(label, r.getPieValue()));
        }

        
        // Push to chart, then force CSS/layout so labels are built
        dashboardTopFishTypesKG_piechart.setData(data);
        
        // ensure skin builds the label nodes
        dashboardTopFishTypesKG_piechart.setLabelsVisible(true);
        dashboardTopFishTypesKG_piechart.setLabelLineLength(12);

        // give it enough room & force a layout
        dashboardTopFishTypesKG_piechart.setPrefSize(280, 280);
        dashboardTopFishTypesKG_piechart.applyCss();
        dashboardTopFishTypesKG_piechart.layout();
        dashboardTopFishTypesKG_piechart.setStartAngle(90);      // aesthetics


        // now the .chart-pie-label Text nodes exist -> style/verify
        javafx.application.Platform.runLater(() -> {
            for (javafx.scene.Node n : dashboardTopFishTypesKG_piechart.lookupAll(".chart-pie-label")) {
                n.setStyle("-fx-fill: #e6edf3; -fx-font-size: 12px;"); // readable on dark bg
                n.setVisible(true);                                     // (paranoia) make sure visible
            }
        });
        
        dashboardTopFishTypesKG_piechart.setTitle(null);     // remove inner title
        dashboardTopFishTypesKG_piechart.setLegendVisible(true);
        
        // Tooltips (need nodes, do it after layout)
        final double grandTotal = grand;
        javafx.application.Platform.runLater(() -> {
            for (var d : dashboardTopFishTypesKG_piechart.getData()) {
                String base = d.getName().replaceAll("\\s*\\(.*\\)$", "");
                double pct  = grandTotal <= 0 ? 0.0 : d.getPieValue()/grandTotal*100.0;
                var tip = new javafx.scene.control.Tooltip(
                    String.format("%s\n%.2f kg (%.1f%%)", base, d.getPieValue(), pct));
                if (d.getNode() != null) javafx.scene.control.Tooltip.install(d.getNode(), tip);
            }
        });
//        System.out.println("Pie slices: " + dashboardTopFishTypesKG_piechart.getData().size());

    }
   
    //dashboard  data management
    @FXML
    private void exportALL_CSV_dashboard(ActionEvent event) {
        exportALL_CSV(event);     // reuse
    }

    @FXML
    private void exportALL_PDF_dashboard(ActionEvent event) {
        exportALL_PDF(event);     // reuse
    }

    @FXML
    private void exportALL_EXCEL_dashboard(ActionEvent event) {
        exportALL_EXCEL(event);     // reuse
    }


    // ==========================================================
    // SECTION B — initialize(): wire UI, load settings once
    // ==========================================================
    private void initAutoBackupUI() {

        // Animate both switches’ thumbs
        wireSwitch(autoBackupSwitch,  thumb);
        wireSwitch(autoBackupSwitch1, thumb1);

        // Mirror both buttons to the one property
        autoBackupSwitch.selectedProperty().bindBidirectional(autoBackupEnabled);
        autoBackupSwitch1.selectedProperty().bindBidirectional(autoBackupEnabled);

        // Load settings from disk (single source)
        var props = loadSettings();
        boolean savedOn = Boolean.parseBoolean(props.getProperty(KEY_AUTO_ENABLED, "false"));
        String dirStr   = props.getProperty(KEY_AUTO_DIR, "").trim();
        backupDir = dirStr.isEmpty() ? null : java.nio.file.Paths.get(dirStr);

        suppressMirror = true;
        autoBackupEnabled.set(savedOn);
        autoBackupSwitch.setSelected(savedOn);
        autoBackupSwitch1.setSelected(savedOn);
        moveThumb(thumb,  savedOn);
        moveThumb(thumb1, savedOn);
        suppressMirror = false;

        if (savedOn) startAutoBackupScheduler();

        // Single handler for both toggles (runs after user action OR programmatic changes)
        autoBackupEnabled.addListener((obs, oldVal, enabled) -> {
            if (suppressMirror) return;

            if (enabled) {
                // Enabling → ensure folder exists OR prompt the user
                if (backupDir == null || !java.nio.file.Files.isDirectory(backupDir)) {
                    var chosen = promptBackupDirectory();
                    if (chosen == null) {
                        // User cancelled → REVERT and DO NOT persist true
                        suppressMirror = true;
                        autoBackupEnabled.set(false);
                        autoBackupSwitch.setSelected(false);
                        autoBackupSwitch1.setSelected(false);
                        moveThumb(thumb,  false);
                        moveThumb(thumb1, false);
                        suppressMirror = false;
                        return;
                    }
                    backupDir = chosen;
                }
                // Persist only after we have a folder
                var ok = saveSettings(true, backupDir, /*last*/ null);
                if (!ok) {
                    // If save failed, be conservative and turn OFF
                    suppressMirror = true;
                    autoBackupEnabled.set(false);
                    autoBackupSwitch.setSelected(false);
                    autoBackupSwitch1.setSelected(false);
                    moveThumb(thumb,  false);
                    moveThumb(thumb1, false);
                    suppressMirror = false;
                    return;
                }
                startAutoBackupScheduler();
            } else {
                stopAutoBackupScheduler();
                saveSettings(false, backupDir, /*last*/ null);
            }
        });
    }

    // helper used by init
    private void wireSwitch(javafx.scene.control.ToggleButton btn, javafx.scene.layout.Region th) {
        btn.selectedProperty().addListener((o, wasOn, isOn) -> moveThumb(th, isOn));
        moveThumb(th, btn.isSelected());
    }
    private void moveThumb(javafx.scene.layout.Region th, boolean on) {
        var tt = new javafx.animation.TranslateTransition(javafx.util.Duration.millis(140), th);
        tt.setToX(on ? ON_OFFSET : 0);
        tt.playFromStart();
    }
    
    // ==========================================================
    // SECTION C — Settings I/O (used by both Dashboard & Data Info)
    // ==========================================================
    private java.util.Properties loadSettings() {
        var p = new java.util.Properties();
        try {
            if (java.nio.file.Files.exists(SETTINGS_FILE)) {
                try (var in = java.nio.file.Files.newInputStream(SETTINGS_FILE)) { p.load(in); }
            }
        } catch (Exception ex) { ex.printStackTrace(); }
        return p;
    }
    

    private boolean saveSettings(boolean enabled, java.nio.file.Path dir, String lastIso) {
        try {
            if (!java.nio.file.Files.exists(SETTINGS_DIR)) {
                java.nio.file.Files.createDirectories(SETTINGS_DIR);
            }
            var p = loadSettings(); // keep other keys
            p.setProperty(KEY_AUTO_ENABLED, Boolean.toString(enabled));
            if (dir != null) p.setProperty(KEY_AUTO_DIR, dir.toString());
            if (lastIso != null) p.setProperty(KEY_AUTO_LAST, lastIso);
            try (var out = java.nio.file.Files.newOutputStream(SETTINGS_FILE)) {
                p.store(out, "Fish Landing – Settings");
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            showInfoWide("Unable to save settings: " + ex.getMessage());
            return false;
        }
    }
    

    // Folder chooser (owner picks the Data Info label's window; use any scene node we have)
    private java.nio.file.Path promptBackupDirectory() {
        var chooser = new javafx.stage.DirectoryChooser();
        chooser.setTitle("Choose folder for automatic SQL backups");
        var wnd = lastBackup_label.getScene().getWindow();
        var f = chooser.showDialog(wnd);
        return (f == null) ? null : f.toPath();
    }
    
    // ==========================================================
    // SECTION D — Scheduler + “last backup” label (used by both modules)
    // ==========================================================
    private void startAutoBackupScheduler() {
        stopAutoBackupScheduler();
        backupScheduler = java.util.concurrent.Executors.newSingleThreadScheduledExecutor(r -> {
            var t = new Thread(r, "AutoBackupScheduler");
            t.setDaemon(true);
            return t;
        });
        long periodHours = 24;
        backupScheduler.scheduleAtFixedRate(() -> {
            try {
                if (backupDir == null) return;
                String ts = java.time.LocalDateTime.now()
                        .format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
                var out = backupDir.resolve("fish_landing_db_" + ts + ".sql").toAbsolutePath().toString();

                boolean ok = runMysqldump(out);
                if (ok) {
                    javafx.application.Platform.runLater(this::saveLastBackupNow);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }, 24, 24, java.util.concurrent.TimeUnit.HOURS);
    }
        
    private void stopAutoBackupScheduler() {
        if (backupScheduler != null) {
            backupScheduler.shutdownNow();
            backupScheduler = null;
        }
    }
    
    
    // ==========================================================
    // SECTION E — Manual backup / restore hook points (unchanged logic)
    //    - call saveSettings(..., lastIso) after a successful manual backup
    // ==========================================================
    @FXML private void manualBackup_btn(javafx.event.ActionEvent e) {
        var chooser = new javafx.stage.FileChooser();
        chooser.setTitle("Save SQL Backup");
        chooser.getExtensionFilters().add(new javafx.stage.FileChooser.ExtensionFilter("SQL files", "*.sql"));
        chooser.setInitialFileName("fish_landing_backup_" + nowStamp() + ".sql");
        var out = chooser.showSaveDialog(lastBackup_label.getScene().getWindow());
        if (out == null) return;

        if (runMysqldump(out.getAbsolutePath())) {
            saveSettings(autoBackupEnabled.get(), backupDir, nowIso());
            saveLastBackupNow();   // <- persist and refresh label
            showInfoWide("Backup saved to:\n" + out.getAbsolutePath());
        } else {
            showInfoWide("Backup failed. See logs.");
        }
    }

    //======================for last backup label
    // tiny helpers to read/write the single settings file
    private java.util.Properties readSettings() {
        var props = new java.util.Properties();
        try {
            if (java.nio.file.Files.exists(SETTINGS_FILE)) {
                try (var in = java.nio.file.Files.newInputStream(SETTINGS_FILE)) {
                    props.load(in);
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return props;
    }

    private void writeSettings(java.util.Properties props) {
        try {
            if (!java.nio.file.Files.exists(SETTINGS_DIR)) {
                java.nio.file.Files.createDirectories(SETTINGS_DIR);
            }
            try (var out = java.nio.file.Files.newOutputStream(SETTINGS_FILE)) {
                props.store(out, "Fish Landing – Settings");
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    // Formatters
    private static final java.time.format.DateTimeFormatter LAST_FMT =
            java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    // update label from settings.properties
    private void refreshLastBackupLabel() {
        var props = readSettings();
        String iso = props.getProperty(KEY_LAST_BACKUP_ISO, "").trim();
        if (iso.isEmpty()) {
            lastBackup_label.setText("No backups yet");
            return;
        }
        try {
            var t = java.time.LocalDateTime.parse(iso);
            lastBackup_label.setText("Last backup: " + t.format(LAST_FMT));
        } catch (Exception parseErr) {
            // if file has bad value, don’t crash the UI
            lastBackup_label.setText("Last backup: " + iso);
        }
    }
    
    // persist “now” and refresh label
    private void saveLastBackupNow() {
        var props = readSettings();
        props.setProperty(KEY_LAST_BACKUP_ISO, java.time.LocalDateTime.now().toString());
        writeSettings(props);
        refreshLastBackupLabel();
    }

   
    // Called by the listener inside initAutoBackupUI()
    private void onAutoBackupToggleChanged(boolean enabled) {
        if (enabled) {
            // Ensure we have a backup folder; prompt if missing
            if (backupDir == null || !java.nio.file.Files.isDirectory(backupDir)) {
                var chosen = promptBackupDirectory();
                if (chosen == null) {
                    // User cancelled → revert UI & property, do NOT persist "enabled=true"
                    suppressMirror = true;
                    autoBackupEnabled.set(false);
                    autoBackupSwitch.setSelected(false);
                    autoBackupSwitch1.setSelected(false);
                    // (optional) move thumbs visually if you don't already animate onSelection
                    suppressMirror = false;
                    return;
                }
                backupDir = chosen;

                // Persist both dir and enabled in settings.properties
                var p = readSettings();
                p.setProperty(KEY_AUTO_DIR, backupDir.toString());
                p.setProperty(KEY_AUTO_ENABLED, "true");
                writeSettings(p);
            } else {
                // We already have a folder; just persist enabled=true
                var p = readSettings();
                p.setProperty(KEY_AUTO_ENABLED, "true");
                writeSettings(p);
            }

            // Start scheduler and refresh label (shows last known backup time)
            startAutoBackupScheduler();
            refreshLastBackupLabel();

        } else {
            // Turn OFF: stop scheduler, persist enabled=false (keep dir & last)
            stopAutoBackupScheduler();
            var p = readSettings();
            p.setProperty(KEY_AUTO_ENABLED, "false");
            writeSettings(p);
            // We do not clear last backup label/history here.
        }
    }

     @FXML
    private void LOGout_btn(ActionEvent event) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirm Logout");
        confirm.setHeaderText(null);
        confirm.setContentText("Are you sure you want to log out?");

        // Show the dialog and wait for response
        Optional<ButtonType> result = confirm.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                // Load Login.fxml
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
                Parent root = loader.load();

                Stage loginStage = new Stage();
                loginStage.setScene(new Scene(root));
                loginStage.setTitle("Alabat Fish Landing");
                loginStage.show();

                // Close the current Dashboard window
                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                currentStage.close();

                // Reset session (optional)
                LoginController.currentUserId = null;

            } catch (Exception ex) {
                ex.printStackTrace();
                Alert a = new Alert(Alert.AlertType.ERROR, "Unable to log out: " + ex.getMessage(), ButtonType.OK);
                a.showAndWait();
            }
        }
    }
    
    ////////////////////////////////////////////////////////////////////////////CONSUMER
    // ---------- CONSUMERS (OUTSIDE initialize) ----------
    private void hideConsumerErrors() {
        Consumer_err.setVisible(false);
    }
    private void clearConsumerForm() {
        consumerName_tf.clear();
        consumerContact.clear();
        consumerAddress_tf.clear();
    }
    private void exitConsumerPopup() {
        addNewConsumer_popup.setVisible(false);
        consumer_pane.setDisable(false);
        sideNavigation_vbox.setDisable(false); // lock side nav when popup is open
        clearConsumerForm();
        hideConsumerErrors();
        editingConsumerId = null;
        
        transactionANDsales_pane.setDisable(false);
        addNewTransaction_popup.setDisable(false);
    }
    private void reloadConsumerTable() {
        consumerData.setAll(mysqlconnect.loadConsumers());
    }
    private void showInfoConsumers(String msg) {
        var a = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION, msg, javafx.scene.control.ButtonType.OK);
        a.setHeaderText(null);
        a.getDialogPane().setMinWidth(520);
        a.getDialogPane().setMinHeight(160);
        a.showAndWait();
    }
//    private String safeTrim(String t) {
//        if (t == null) return null; String s = t.trim(); return s.isEmpty() ? null : s;
//    }

    @FXML
    private void addConsumer(ActionEvent event) {
        editingConsumerId = null;
        clearConsumerForm();
        hideConsumerErrors();
        addNewConsumer_popup.setVisible(true);
        consumer_pane.setDisable(true);
        sideNavigation_vbox.setDisable(true);
    }

    @FXML
    private void updateConsumer(ActionEvent event) {
        var sel = consumer_tv.getSelectionModel().getSelectedItem();
        if (sel == null) { showInfoConsumers("Please select a consumer to update."); return; }
        editingConsumerId = sel.getConsumerId();

        consumerName_tf.setText(sel.getName());
        consumerContact.setText(sel.getContact());
        consumerAddress_tf.setText(sel.getAddress());

        addNewConsumer_popup.setVisible(true);
        consumer_pane.setDisable(true);
        sideNavigation_vbox.setDisable(true);
    }

    @FXML
    private void deleteConsumer(ActionEvent event) {
        var sel = consumer_tv.getSelectionModel().getSelectedItem();
        if (sel == null) { showInfoConsumers("Please select a consumer to delete."); return; }

        int id = sel.getConsumerId();
        int txnCnt = mysqlconnect.countTransactionsByConsumer(id); // -1 on error

        if (txnCnt < 0) { showInfoConsumers("Unable to check dependencies. Try again."); return; }
        if (txnCnt > 0) {
            var warn = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.WARNING,
                "Cannot delete. This consumer has linked transactions (" + txnCnt + ").\n\n" +
                "Tip: Set status to Inactive instead.",
                javafx.scene.control.ButtonType.OK);
            warn.setHeaderText("Delete blocked");
            warn.showAndWait();
            return;
        }

        var confirm = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.CONFIRMATION,
            "Delete this consumer permanently? This cannot be undone.",
            javafx.scene.control.ButtonType.YES, javafx.scene.control.ButtonType.NO);
        confirm.setHeaderText("Confirm delete");
        confirm.showAndWait();

        if (confirm.getResult() == javafx.scene.control.ButtonType.YES) {
            boolean ok = mysqlconnect.deleteConsumerById(id);
            if (ok) {
                consumerData.remove(sel);
                showInfoConsumers("Consumer deleted.");
            } else {
                showInfoConsumers("Failed to delete. Please try again.");
            }
        }
    }

    @FXML
    private void btnSave_onAddConsumer(ActionEvent event) {
        hideConsumerErrors();
        String name    = safeTrim(consumerName_tf.getText());
        String contact = safeTrim(consumerContact.getText());
        String address = safeTrim(consumerAddress_tf.getText());

        boolean valid = true;
        if (name == null) { Consumer_err.setVisible(true); valid = false; }
        if (!valid) return;

        if (editingConsumerId == null) {
            // INSERT
            boolean ok = mysqlconnect.insertConsumer(name, contact, address);
            if (ok) { showInfoConsumers("Consumer saved."); exitConsumerPopup(); reloadConsumerTable(); }
            else    { showInfoConsumers("Error saving consumer."); }
        } else {
            // UPDATE
            boolean ok = mysqlconnect.updateConsumer(editingConsumerId, name, contact, address);
            if (ok) { showInfoConsumers("Consumer updated."); exitConsumerPopup(); reloadConsumerTable(); }
            else    { showInfoConsumers("Error updating consumer."); }
        }
        
                // after successfully saving new consumer:
        allConsumers = mysqlconnect.loadActiveConsumers();
        transacConsumer_cb.setItems(allConsumers);
        // Optional: preselect the newly added name 

    }

    @FXML
    private void btnClear_onAddConsumer(ActionEvent event) {
        clearConsumerForm();
        hideConsumerErrors();
    }

    @FXML
    private void btnCancel_onAddConsumer(ActionEvent event) {
        exitConsumerPopup();
    }

    @FXML
    private void export_consumer_list(ActionEvent event) {
        var fc = new javafx.stage.FileChooser();
        fc.setTitle("Save Consumer List");
        fc.getExtensionFilters().add(
            new javafx.stage.FileChooser.ExtensionFilter("Excel Workbook (*.xlsx)", "*.xlsx")
        );
        fc.setInitialFileName("consumers_export_" + java.time.LocalDate.now() + ".xlsx");
        java.io.File file = fc.showSaveDialog(((javafx.scene.Node)event.getSource()).getScene().getWindow());
        if (file == null) return;
        if (!file.getName().toLowerCase().endsWith(".xlsx")) {
            file = new java.io.File(file.getParentFile(), file.getName() + ".xlsx");
        }

        try (var wb = new org.apache.poi.xssf.usermodel.XSSFWorkbook()) {
            var sh = wb.createSheet("Consumers");
            var bold = wb.createFont(); bold.setBold(true);
            var head = wb.createCellStyle(); head.setFont(bold);

            String[] headers = {"ID","Name","Contact","Address","Status"};
            int r = 0;
            var hr = sh.createRow(r++);
            for (int i = 0; i < headers.length; i++) {
                var c = hr.createCell(i);
                c.setCellValue(headers[i]);
                c.setCellStyle(head);
            }

            for (ConsumerRecord x : consumerSorted) {
                var row = sh.createRow(r++);
                int c = 0;
                row.createCell(c++).setCellValue(x.getConsumerId());
                row.createCell(c++).setCellValue(x.getName());
                row.createCell(c++).setCellValue(x.getContact());
                row.createCell(c++).setCellValue(x.getAddress());
                row.createCell(c++).setCellValue(x.isActive() ? "Active" : "Inactive");
            }
            for (int i = 0; i < headers.length; i++) sh.autoSizeColumn(i);

            try (var out = new java.io.FileOutputStream(file)) { wb.write(out); }
        } catch (Exception ex) {
            ex.printStackTrace();
            showInfoConsumers("Export failed: " + ex.getMessage());
            return;
        }

        var path = file.getAbsolutePath();
        var ta = new javafx.scene.control.TextArea("Exported: " + path);
        ta.setEditable(false); ta.setWrapText(true);
        ta.setMaxWidth(Double.MAX_VALUE); ta.setMaxHeight(Double.MAX_VALUE);
        var alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setHeaderText("Export Success");
        alert.getDialogPane().setContent(ta);
        alert.getDialogPane().setMinWidth(600);
        alert.showAndWait();
    }

    @FXML
    private void viewTransaction_Consumer(ActionEvent event) {
//        var sel = consumer_tv.getSelectionModel().getSelectedItem();
//        if (sel == null) { showInfoConsumers("Please select a consumer to view transactions."); return; }
//
//        int consumerId = sel.getConsumerId();
//
//        // Preferred path: if schema has transactions.consumer_id
//        var items = mysqlconnect.getTransactionsByConsumer(consumerId);
//
//        // Fallback path: if haven't added consumer_id yet,
//        // mysqlconnect.getTransactionsByConsumer() will internally match by buyer_name = consumer.name
//
//        // Now show them (can reuse an existing TableView popup or tab)
//        txn_tv.setItems(items);                         // if already have txn_tv in UI
//        // Optionally switch to a Transactions tab/popup here
    }
    
////////////////////////////////////////////////////////////////////////////////end of consumer
////////////////////////////////////////////////////////////////////////////////INVENTORY
    private void applyInventoryFilters() {
        final String q = filterField_inventory.getText() == null
                ? "" : filterField_inventory.getText().trim().toLowerCase();

        invFiltered.setPredicate(row -> {
            if (row == null) return false;
            if (q.isEmpty()) return true;

            String species = row.getSpeciesName() == null ? "" : row.getSpeciesName().toLowerCase();
            return species.contains(q);
        });
    }

    @FXML
private void editInventory(ActionEvent event) {
    var sel = inventory_tv.getSelectionModel().getSelectedItem();
    if (sel == null) {
        showInfoWide("Select a species to edit selling price.");
        return;
    }

    var td = new javafx.scene.control.TextInputDialog(
        sel.getSellingPrice() == null ? "" : String.format("%.2f", sel.getSellingPrice())
    );
    td.setTitle("Edit Selling Price");
    td.setHeaderText("Species: " + sel.getSpeciesName());
    td.setContentText("New selling price (per kg):");
    var opt = td.showAndWait();
    if (opt.isEmpty()) return;

    try {
        double newPrice = Double.parseDouble(opt.get().trim());
        if (newPrice < 0) throw new NumberFormatException();

        boolean ok = mysqlconnect.upsertInventorySellingPrice(
                sel.getSpeciesId(),
                newPrice,
                null,   // lastPurchasePriceOrNull
                null,   // avgPurchasePriceOrNull
                null    // restockThresholdOrNull
        );

        if (ok) {
            showInfoWide("Selling price updated.");
            refreshInventoryTable();
        } else {
            showInfoWide("Update failed.");
        }
    } catch (NumberFormatException nfe) {
        showInfoWide("Please enter a valid price.");
    }
}


    private void refreshInventoryTable() {
        var fresh = mysqlconnect.loadInventoryView();
        if (invData == null) {
            // safety fallback if called very early
            invData = fresh;
            invFiltered = new FilteredList<>(invData, r -> true);
            invSorted = new SortedList<>(invFiltered);
            invSorted.comparatorProperty().bind(inventory_tv.comparatorProperty());
            inventory_tv.setItems(invSorted);
        } else {
            invData.setAll(fresh);
        }
    }


    @FXML private void addInventory(ActionEvent e) {
        showInfoWide("Tip: Add inventory by recording a Purchase (not here). You can set selling price by editing a row.");
    }

    @FXML private void deleteInventory(ActionEvent e) {
        showInfoWide("Deleting inventory rows is disabled to keep totals consistent. Delete species only if necessary.");
    }

    @FXML
    private void btnExport_INVENTORY(ActionEvent event) {
        var fc = new javafx.stage.FileChooser();
        fc.setTitle("Export Inventory");
        fc.getExtensionFilters().add(new javafx.stage.FileChooser.ExtensionFilter("Excel Workbook (*.xlsx)", "*.xlsx"));
        fc.setInitialFileName("inventory_" + java.time.LocalDate.now() + ".xlsx");
        var f = fc.showSaveDialog(inventory_pane.getScene().getWindow());
        if (f == null) return;
        if (!f.getName().toLowerCase().endsWith(".xlsx")) {
            f = new java.io.File(f.getParentFile(), f.getName() + ".xlsx");
        }

        try (var wb = new org.apache.poi.xssf.usermodel.XSSFWorkbook()) {
            var sh = wb.createSheet("Inventory");
            var bold = wb.createFont(); bold.setBold(true);
            var head = wb.createCellStyle(); head.setFont(bold);

            String[] headers = {
                "Fish Type","Stock (kg)","Sold (kg)","Balance (kg)",
                "Last Purchase Price","Selling Price","Last Update"
            };
            int r = 0;
            var hr = sh.createRow(r++);
            for (int i = 0; i < headers.length; i++) {
                var c = hr.createCell(i);
                c.setCellValue(headers[i]); c.setCellStyle(head);
            }

            for (var row : invSorted) { // exactly what’s visible (sorted+filtered)
                var x = sh.createRow(r++); int c = 0;
                x.createCell(c++).setCellValue(row.getSpeciesName());
                x.createCell(c++).setCellValue(row.getPurchasedQty());
                x.createCell(c++).setCellValue(row.getSoldQty());
                x.createCell(c++).setCellValue(row.getBalanceQty());
                
                Double last = row.getLastPurchasePrice();
                x.createCell(c++).setCellValue(last == null ? 0.0 : last);

                Double sell = row.getSellingPrice();
                x.createCell(c++).setCellValue(sell == null ? 0.0 : sell);
                
                x.createCell(c++).setCellValue(row.getUpdatedAt() == null ? "" : row.getUpdatedAt().toString());
            }
            for (int i = 0; i < headers.length; i++) sh.autoSizeColumn(i);

            try (var out = new java.io.FileOutputStream(f)) { wb.write(out); }
            showInfoWide("Exported: " + f.getAbsolutePath());
        } catch (Exception ex) {
            ex.printStackTrace();
            showInfoWide("Export failed: " + ex.getMessage());
        }
    }

    

    

    

    

}
