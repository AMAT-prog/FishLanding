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
import java.util.Properties;
import javafx.scene.image.Image;

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
            // No recovery key configured — inform admin
            recoveryKey_error.setText("No recovery key configured on this machine.");
            recoveryKey_error.setVisible(true);
            return;
        }

        if (!configured.equals(entered.trim())) {
            recoveryKey_error.setText("Invalid recovery key.");
            recoveryKey_error.setVisible(true);
            return;
        }

        // Recovery key valid. We still need to set currentUserId (some parts of app expect logged-in user).
        // Strategy: pick the first user found (or a specific fallback), and set its id.
        try (Connection c = mysqlconnect.ConnectDb();
             PreparedStatement ps = c.prepareStatement("SELECT user_id FROM users ORDER BY user_id LIMIT 1");
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) currentUserId = rs.getInt("user_id");
            else currentUserId = null;
        } catch (Exception ex) {
            ex.printStackTrace();
            currentUserId = null;
        }

        // Open dashboard
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
            Parent root = loader.load();

            Stage st = new Stage();
            st.setScene(new Scene(root));
            st.setTitle("Fish Landing - Dashboard");
            // optionally set icon if you want: st.getIcons().add(...);

            // Set the application icon.
            st.getIcons().add(new Image("C:\\Users\\User\\OneDrive\\Documents\\NetBeansProjects\\FishLanding\\FishLanding\\src\\image\\MainIcon.png"));
            // show new stage
            st.show();

            // close the login window
            Stage current = (Stage) username_tf.getScene().getWindow();
            current.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            // if opening fails, show an alert instead of silently failing
            Alert a = new Alert(Alert.AlertType.ERROR, "Unable to open dashboard: " + ex.getMessage(), ButtonType.OK);
            a.showAndWait();
        }
    }

    
}

