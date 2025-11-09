/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class FishLanding extends Application {

    // set to SceneBuilder design size
    public static final double BASE_W = 1270;
    public static final double BASE_H = 660;

    @Override
    public void start(Stage primaryStage) {

        //////////////////////////////////////////////////////////// 4 days ///////////////////////////////////////////////////
//        if (TrialManager.isTrialExpired()) {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Trial Expired");
//            alert.setHeaderText("Your trial period has ended.");
//            alert.setContentText("Please contact the developer to activate the full version.");
//            alert.showAndWait();
//            Platform.exit();
//            return;
//        } else {
//            long daysLeft = TrialManager.getDaysLeft();
            try {
                Parent loginRoot = FXMLLoader.load(getClass().getResource("Login.fxml"));
                showScaled(primaryStage, loginRoot, "Alabat Fish Landing");
            } catch (IOException ex) {
                Logger.getLogger(FishLanding.class.getName()).log(Level.SEVERE, null, ex);
            }
//        }

        //////////////////////////////////////////////// 5 minutes //////////////////////////////////////////////////////////
//        if (TrialManager.isTrialExpired()) {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Trial Expired");
//            alert.setHeaderText("Your trial period has ended.");
//            alert.setContentText("Please contact the developer to activate the full version.");
//            alert.showAndWait();
//            Platform.exit();
//            return;
//        } else {
//            System.out.println("Trial active. Minutes left: " + TrialManager.getMinutesLeft());
//            try {
//                Parent loginRoot = FXMLLoader.load(getClass().getResource("Login.fxml"));
//                showScaled(primaryStage, loginRoot, "Alabat Fish Landing");
//            } catch (IOException ex) {
//                Logger.getLogger(FishLanding.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
    }

    /**
     * Helper to display any root as a scaled (proportional) scene.
     */
    private void showScaled(Stage stage, Parent contentRoot, String title) {
        // Ensure the designed base size is respected by the content root when possible
        if (contentRoot instanceof Region r) {
            r.setPrefSize(BASE_W, BASE_H);
            r.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
            r.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        }

        // A StackPane keeps the content centered while the window grows
        StackPane container = new StackPane(contentRoot);

        // Start at your design size
        Scene scene = new Scene(container, BASE_W, BASE_H);

        // Install the proportional scaling behavior
        ScaleUtil.install(scene, contentRoot, BASE_W, BASE_H);

        stage.setTitle(title);
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }

    /**
     * Reusable proportional scaling utility.
     * Keeps aspect ratio, scales to fit, and centers the content.
     */
    
    public static final class ScaleUtil {
        public static void install(Scene scene, Parent content, double baseW, double baseH) {
            // Compute + apply proportional scale
            Runnable apply = () -> {
                double sw = Math.max(scene.getWidth(), 1);
                double sh = Math.max(scene.getHeight(), 1);

                double sx = sw / baseW;
                double sy = sh / baseH;
                double s  = Math.min(sx, sy);   // keep aspect ratio

                content.setScaleX(s);
                content.setScaleY(s);

                // Optional: if you want hard maximum zoom, uncomment (example: 1.6x)
                // s = Math.min(s, 1.6);

                // NOTE:
                // We no longer translate/center manually. StackPane keeps it centered.
                // If your container is not a StackPane, wrap content in one (as in showScaled()).
            };

            // React to window resizes
            scene.widthProperty().addListener((o, a, b) -> apply.run());
            scene.heightProperty().addListener((o, a, b) -> apply.run());

            // Run once after the scene is first laid out
            javafx.application.Platform.runLater(apply);
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
