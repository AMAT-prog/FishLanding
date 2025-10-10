/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.InputStream;
import java.sql.*;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Window;

/**
 * FXML Controller class
 *
 * @author User
 */

public class LoginController implements Initializable {

    @FXML private Pane loginCard;
    @FXML private TextField username_tf;
    @FXML private PasswordField password_pf;   // real (masked) field
    @FXML private TextField password_tf;       // visible field (swapped in when eye toggled)
    @FXML private ImageView eye_iv;            // the eye icon imageview (clickable)
    @FXML private Label signIn_error;
    @FXML private Pane recoveryKey_pane;
    @FXML private TextField recoveryKey_tf;
    @FXML private Label recoveryKey_error;

    // If other code expects a current user id (used currentUserId elsewhere),
    // set it here after successful login:
    public static Integer currentUserId = null;

    // SETTINGS file for recovery key
    private static final java.nio.file.Path SETTINGS_DIR =
            java.nio.file.Paths.get(System.getProperty("user.home"), ".fishlanding");
    private static final java.nio.file.Path SETTINGS_FILE = SETTINGS_DIR.resolve("settings.properties");
    private static final String KEY_RECOVERY = "recovery.key";

    private boolean passwordVisible = false;
    
    //FOR CHANGING USERNAME AND PASSWORD AFTER SUCCESSFULLY USING RECOVERY KEY
    private static final String SQL_FIND_FIRST_USER = "SELECT user_id FROM users ORDER BY user_id LIMIT 1";
    private static final String SQL_USERNAME_EXISTS = "SELECT COUNT(*) FROM users WHERE username=? AND user_id<>?";
    private static final String SQL_UPDATE_USER     = "UPDATE users SET username=?, password=? WHERE user_id=?";
    private static final String SQL_INSERT_USER     = "INSERT INTO users(username, password) VALUES(?,?)";

    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // start with masked password field visible, plain text hidden
        password_tf.setVisible(false);
        password_tf.setManaged(false);

        // hide recovery pane
        recoveryKey_pane.setVisible(false);
        recoveryKey_pane.setManaged(false);

        signIn_error.setVisible(false);

        // keep both password fields in sync
        password_pf.textProperty().addListener((obs, oldV, newV) -> {
            if (!passwordVisible) password_tf.setText(newV);
        });
        password_tf.textProperty().addListener((obs, oldV, newV) -> {
            if (passwordVisible) password_pf.setText(newV);
        });

        // eye click toggles visibility
        eye_iv.setOnMouseClicked(e -> togglePasswordVisibility());

        // Optionally show tooltip on eye
        eye_iv.setPickOnBounds(true);
    }

    // Toggle between masked PasswordField and plain TextField
    private void togglePasswordVisibility() {
        passwordVisible = !passwordVisible;
        if (passwordVisible) {
            // show text field with the password
            password_tf.setText(password_pf.getText());
            password_tf.setVisible(true);
            password_tf.setManaged(true);
            password_pf.setVisible(false);
            password_pf.setManaged(false);
            // optionally change eye icon to "open" image
        } else {
            // show password field (masked)
            password_pf.setText(password_tf.getText());
            password_pf.setVisible(true);
            password_pf.setManaged(true);
            password_tf.setVisible(false);
            password_tf.setManaged(false);
            // optionally change eye icon to "closed" image
        }
    }

    // sign in button pressed
    @FXML
    private void signIn_btn(javafx.event.ActionEvent event) {
        signIn_error.setVisible(false);

        String username = (username_tf.getText() == null) ? "" : username_tf.getText().trim();
        String password = passwordVisible ? password_tf.getText() : password_pf.getText();

        if (username.isEmpty() || password == null || password.isEmpty()) {
            signIn_error.setText("Enter username and password.");
            signIn_error.setVisible(true);
            return;
        }

        // authenticate against DB
        try (Connection c = mysqlconnect.ConnectDb()) {
            String sql = "SELECT user_id, username, password, name, role FROM users WHERE username=? LIMIT 1";
            try (PreparedStatement ps = c.prepareStatement(sql)) {
                ps.setString(1, username);
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        signIn_error.setText("Invalid username or password.");
                        signIn_error.setVisible(true);
                        return;
                    }
                    int uid = rs.getInt("user_id");
                    String dbPass = rs.getString("password");

                    // NOTE: if hashed passwords was stored, replace this check with hash verify
                    if (!Objects.equals(dbPass, password)) {
                        signIn_error.setText("Invalid username or password.");
                        signIn_error.setVisible(true);
                        return;
                    }

                    // Success: set current user id for the rest of the app
                    currentUserId = uid;

                    // open dashboard
                    openDashboardAndCloseThis();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            signIn_error.setText("Login failed: " + ex.getMessage());
            signIn_error.setVisible(true);
        }
    }

    // Show recovery pane
    @FXML
    private void ForgotPassword(MouseEvent event) {
        loginCard.setDisable(true);
        recoveryKey_pane.setVisible(true);
        recoveryKey_pane.setManaged(true);
    }

    // Recovery Enter pressed - if recovery key matches, open dashboard
    @FXML
private void recoveryEnter_btn(javafx.event.ActionEvent event) {
    recoveryKey_error.setVisible(false);
    String entered = recoveryKey_tf.getText();
    if (entered == null || entered.isBlank()) {
        recoveryKey_error.setText("Enter recovery key.");
        recoveryKey_error.setVisible(true);
        return;
    }

    String configured = readRecoveryKeyFromSettings();
    if (configured == null || configured.isBlank()) {
        recoveryKey_error.setText("No recovery key configured on this machine.");
        recoveryKey_error.setVisible(true);
        return;
    }
    if (!configured.equals(entered.trim())) {
        recoveryKey_error.setText("Invalid recovery key.");
        recoveryKey_error.setVisible(true);
        return;
    }

    //  Valid recovery key — open popup to change credentials
    Window owner = recoveryKey_pane.getScene() != null ? recoveryKey_pane.getScene().getWindow() : null;
    Optional<Credentials> result = showChangeCredentialsDialog(owner);

    if (result.isEmpty()) {
        // Cancel clicked — just return to login
        recoveryKey_pane.setVisible(false);
        recoveryKey_pane.setManaged(false);
        loginCard.setDisable(false);
        return;
    }

    Credentials cred = result.get();

    // === Update DB ===
    try (Connection c = mysqlconnect.ConnectDb()) {
        c.setAutoCommit(false);
        try {
            Integer uid = currentUserId;
            if (uid == null) uid = findFirstUserId(c);

            if (usernameExists(c, cred.username, uid)) {
                c.rollback();
                new Alert(Alert.AlertType.ERROR, "⚠️ Username already exists.").showAndWait();
                recoveryKey_tf.clear();
                return;
            }

            updateOrCreateUser(c, uid, cred);
            if (uid == null) uid = findFirstUserId(c);
            currentUserId = uid;
            c.commit();

        } catch (Exception inner) {
            c.rollback();
            inner.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "⚠️ Failed to update credentials: " + inner.getMessage()).showAndWait();
            recoveryKey_tf.clear();
            return;
        } finally {
            c.setAutoCommit(true);
        }
    } catch (Exception ex) {
        ex.printStackTrace();
        new Alert(Alert.AlertType.ERROR, "⚠️ Database error: " + ex.getMessage()).showAndWait();
        recoveryKey_tf.clear();
        return;
    }

    // Success
    recoveryKey_tf.clear();
    recoveryKey_pane.setVisible(false);
    recoveryKey_pane.setManaged(false);
    loginCard.setDisable(false);
    openDashboardAndCloseThis();
}


    // Cancel recovery — go back to login form
    @FXML
    private void recoveryCancel_btn(javafx.event.ActionEvent event) {
        loginCard.setDisable(false);
        recoveryKey_pane.setVisible(false);
        recoveryKey_pane.setManaged(false);
        recoveryKey_tf.clear();
        recoveryKey_error.setVisible(false);
    }

    // ----------------------
    // Utility: read the recovery key from settings.properties
    private String readRecoveryKeyFromSettings() {
        java.util.Properties p = new java.util.Properties();
        try {
            if (java.nio.file.Files.exists(SETTINGS_FILE)) {
                try (InputStream in = java.nio.file.Files.newInputStream(SETTINGS_FILE)) {
                    p.load(in);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return p.getProperty(KEY_RECOVERY, "").trim();
    }

    // Utility: load Dashboard and close login window
    private void openDashboardAndCloseThis() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Dashboardfl.fxml"));
            Parent dashRoot = loader.load();

            // Ensure base size
            if (dashRoot instanceof Region r) {
                r.setPrefSize(FishLanding.BASE_W, FishLanding.BASE_H);
                r.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
                r.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
            }

            // Wrap in a centering container
            StackPane container = new StackPane(dashRoot);
            Scene scene = new Scene(container, FishLanding.BASE_W, FishLanding.BASE_H);

            //  Apply the same proportional scaling
            FishLanding.ScaleUtil.install(scene, dashRoot, FishLanding.BASE_W, FishLanding.BASE_H);

            Stage st = new Stage();
            st.setScene(scene);
            st.setTitle("Fish Landing - Dashboard");
            st.show();

            // close the login window
            Stage current = (Stage) username_tf.getScene().getWindow();
            current.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            Alert a = new Alert(Alert.AlertType.ERROR, "Unable to open dashboard: " + ex.getMessage(), ButtonType.OK);
            a.showAndWait();
        }
    }


    //FOR UPDATING USERS AFTER SUCCESSFUL RECOVERY KEY
    // Simple holder for dialog result
    private static final class Credentials {
        final String username;
        final String password;
        Credentials(String u, String p) { this.username = u; this.password = p; }
    }

    // --- Call this after recovery key is confirmed ---
   private Optional<Credentials> showChangeCredentialsDialog(Window owner) {
    Dialog<Credentials> dialog = new Dialog<>();
    dialog.setTitle("Set New Credentials");
    dialog.setHeaderText("Enter new username and password");
    if (owner != null) dialog.initOwner(owner);
    dialog.initModality(Modality.APPLICATION_MODAL);

    ButtonType saveBtnType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
    ButtonType cancelBtnType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
    dialog.getDialogPane().getButtonTypes().addAll(saveBtnType, cancelBtnType);

    TextField usernameTf = new TextField();
    usernameTf.setPromptText("New username");

    PasswordField passTf = new PasswordField();
    passTf.setPromptText("New password");

    PasswordField confirmTf = new PasswordField();
    confirmTf.setPromptText("Confirm new password");

    // ⚠ visible warning label
    Label errorLbl = new Label();
    errorLbl.setStyle("-fx-text-fill:#e74c3c; -fx-font-weight:bold;");
    errorLbl.setVisible(false);

    GridPane grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(12));
    grid.addRow(0, new Label("Username:"), usernameTf);
    grid.addRow(1, new Label("Password:"), passTf);
    grid.addRow(2, new Label("Confirm:"), confirmTf);
    grid.add(errorLbl, 0, 3, 2, 1);

    dialog.getDialogPane().setContent(grid);

    // Disable Save until fields filled
    Node saveBtn = dialog.getDialogPane().lookupButton(saveBtnType);
    saveBtn.setDisable(true);
    ChangeListener<String> enabler = (o,a,b) -> {
        boolean ready = !usernameTf.getText().isBlank()
                     && !passTf.getText().isBlank()
                     && !confirmTf.getText().isBlank();
        saveBtn.setDisable(!ready);
        errorLbl.setVisible(false);
    };
    usernameTf.textProperty().addListener(enabler);
    passTf.textProperty().addListener(enabler);
    confirmTf.textProperty().addListener(enabler);

    //  CRITICAL: validate BEFORE closing using an event filter
    ((Button) saveBtn).addEventFilter(ActionEvent.ACTION, ev -> {
        String u = usernameTf.getText().trim();
        String p1 = passTf.getText();
        String p2 = confirmTf.getText();

        if (u.isEmpty()) {
            errorLbl.setText("⚠ Username is required.");
            errorLbl.setVisible(true);
            ev.consume(); // keep dialog open
            return;
        }
        if (p1.length() < 6) {
            errorLbl.setText("⚠ Password must be at least 6 characters.");
            errorLbl.setVisible(true);
            passTf.clear();
            confirmTf.clear();
            // optional: highlight fields
            passTf.setStyle("-fx-border-color:#e74c3c; -fx-border-radius:4;");
            confirmTf.setStyle("-fx-border-color:#e74c3c; -fx-border-radius:4;");
            ev.consume();
            return;
        }
        if (!p1.equals(p2)) {
            errorLbl.setText("⚠ Passwords do not match!");
            errorLbl.setVisible(true);
            passTf.clear();
            confirmTf.clear();
            // also clear recovery key on main login pane
            if (recoveryKey_tf != null) recoveryKey_tf.clear();
            // optional: highlight fields
            passTf.setStyle("-fx-border-color:#e74c3c; -fx-border-radius:4;");
            confirmTf.setStyle("-fx-border-color:#e74c3c; -fx-border-radius:4;");
            ev.consume(); // prevent close
        }
    });

    dialog.setResultConverter(btn -> {
        if (btn == saveBtnType) {
            // Only runs if event wasn't consumed (i.e., valid)
            return new Credentials(usernameTf.getText().trim(), passTf.getText());
        }
        return null; // cancel/close
    });

    Platform.runLater(usernameTf::requestFocus);
    return dialog.showAndWait();
}



    // --- DB helpers ---
    private Integer findFirstUserId(Connection c) throws SQLException {
        try (PreparedStatement ps = c.prepareStatement(SQL_FIND_FIRST_USER);
             ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getInt(1) : null;
        }
    }

    private boolean usernameExists(Connection c, String username, Integer excludeUserId) throws SQLException {
        try (PreparedStatement ps = c.prepareStatement(SQL_USERNAME_EXISTS)) {
            ps.setString(1, username);
            ps.setInt(2, excludeUserId == null ? -1 : excludeUserId);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt(1) > 0;
            }
        }
    }

    private void updateOrCreateUser(Connection c, Integer userId, Credentials cred) throws SQLException {
        // TIP: hash passwords in real apps (e.g., BCrypt). For now, it updates as plain text.
        if (userId != null) {
            try (PreparedStatement ps = c.prepareStatement(SQL_UPDATE_USER)) {
                ps.setString(1, cred.username);
                ps.setString(2, cred.password);
                ps.setInt(3, userId);
                ps.executeUpdate();
            }
        } else {
            try (PreparedStatement ps = c.prepareStatement(SQL_INSERT_USER)) {
                ps.setString(1, cred.username);
                ps.setString(2, cred.password);
                ps.executeUpdate();
            }
        }
    }
    
}

