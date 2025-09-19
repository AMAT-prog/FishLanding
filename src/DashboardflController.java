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
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;
import javafx.scene.layout.VBox;


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

    
    ////////////////////////////////////////////////////////////////////////////SIDE NAVIGATION
    @FXML
    private void dashboard_btn(ActionEvent event) {
        dashboard_pane.setVisible(true);
         
        landings_pane.setVisible(false);
        fishermen_pane.setVisible(false);
        transactionANDsales_pane.setVisible(false);
    }

    @FXML
    private void landings_btn(ActionEvent event) {
        landings_pane.setVisible(true);
       
        dashboard_pane.setVisible(false);
        fishermen_pane.setVisible(false);
        transactionANDsales_pane.setVisible(false);
    }

    @FXML
    private void fishermen_btn(ActionEvent event) {
        fishermen_pane.setVisible(true);
        
        dashboard_pane.setVisible(false);
        landings_pane.setVisible(false);
        transactionANDsales_pane.setVisible(false);
    }

    @FXML
    private void transactionSales_btn(ActionEvent event) {
        transactionANDsales_pane.setVisible(true);
        
        dashboard_pane.setVisible(false);
        fishermen_pane.setVisible(false);
        landings_pane.setVisible(false);
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
    
    ////////////////////////////////////////////////////////////////////////////end of Side Navigation
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dashboard_pane.setVisible(true);
        landings_pane.setVisible(false);
        fishermen_pane.setVisible(false);
        transactionANDsales_pane.setVisible(false);
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



    
    
}
