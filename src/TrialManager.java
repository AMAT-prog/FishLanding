/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author User
 */

import java.io.*;
import java.nio.file.*;
import java.time.*;
import java.util.Properties;

public class TrialManager {
    private static final String FOLDER = System.getenv("ProgramData") + "\\FishLanding";
    private static final String FILE = FOLDER + "\\trial.properties";
    private static final int TRIAL_DAYS = 4; // change to 4 weeks = 28 days if needed

    public static boolean isTrialExpired() {
        try {
            File folder = new File(FOLDER);
            if (!folder.exists()) folder.mkdirs();

            File trialFile = new File(FILE);
            Properties props = new Properties();

            // If first run — create the file
            if (!trialFile.exists()) {
                props.setProperty("startDate", LocalDate.now().toString());
                try (FileOutputStream out = new FileOutputStream(trialFile)) {
                    props.store(out, "FishLanding Trial Info");
                }
                return false; // just started, valid
            }

            // Read existing start date
            try (FileInputStream in = new FileInputStream(trialFile)) {
                props.load(in);
            }
            LocalDate startDate = LocalDate.parse(props.getProperty("startDate"));
            long daysUsed = Duration.between(startDate.atStartOfDay(), LocalDate.now().atStartOfDay()).toDays();

            return daysUsed >= TRIAL_DAYS;
        } catch (Exception e) {
            e.printStackTrace();
            return true; // fail-safe: if something's wrong, treat as expired
        }
    }

    public static long getDaysLeft() {
        try {
            Properties props = new Properties();
            try (FileInputStream in = new FileInputStream(FILE)) {
                props.load(in);
            }
            LocalDate startDate = LocalDate.parse(props.getProperty("startDate"));
            long used = Duration.between(startDate.atStartOfDay(), LocalDate.now().atStartOfDay()).toDays();
            return Math.max(0, TRIAL_DAYS - used);
        } catch (Exception e) {
            return 0;
        }
    }
}


////5 minute version
//import java.io.*;
//import java.nio.file.*;
//import java.time.*;
//import java.util.Properties;
//
//public class TrialManager {
//    private static final String FOLDER = System.getenv("ProgramData") + "\\FishLanding";
//    private static final String FILE = FOLDER + "\\trial.properties";
//    private static final int TRIAL_MINUTES = 5; // trial period in minutes
//
//    public static boolean isTrialExpired() {
//        try {
//            File folder = new File(FOLDER);
//            if (!folder.exists()) folder.mkdirs();
//
//            File trialFile = new File(FILE);
//            Properties props = new Properties();
//
//            // First run — create file with current timestamp
//            if (!trialFile.exists()) {
//                props.setProperty("startMillis", String.valueOf(System.currentTimeMillis()));
//                try (FileOutputStream out = new FileOutputStream(trialFile)) {
//                    props.store(out, "FishLanding Trial Info (minutes)");
//                }
//                return false; // valid for now
//            }
//
//            // Read saved start time
//            try (FileInputStream in = new FileInputStream(trialFile)) {
//                props.load(in);
//            }
//
//            long startMillis = Long.parseLong(props.getProperty("startMillis"));
//            long elapsedMinutes = Duration.between(
//                    Instant.ofEpochMilli(startMillis),
//                    Instant.now()
//            ).toMinutes();
//
//            return elapsedMinutes >= TRIAL_MINUTES;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return true; // fail-safe: treat as expired if error
//        }
//    }
//
//    public static long getMinutesLeft() {
//        try {
//            Properties props = new Properties();
//            try (FileInputStream in = new FileInputStream(FILE)) {
//                props.load(in);
//            }
//
//            long startMillis = Long.parseLong(props.getProperty("startMillis"));
//            long elapsedMinutes = Duration.between(
//                    Instant.ofEpochMilli(startMillis),
//                    Instant.now()
//            ).toMinutes();
//
//            return Math.max(0, TRIAL_MINUTES - elapsedMinutes);
//        } catch (Exception e) {
//            return 0;
//        }
//    }
//}
