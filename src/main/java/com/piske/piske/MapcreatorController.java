package com.piske.piske;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.jcraft.jsch.JSchException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MapcreatorController {
    @FXML
    private AnchorPane gamescreen;

    @FXML
    private Pane Selectareea;

    @FXML
    private Button uploadButton;

    @FXML
    private Button checkMap;

    @FXML
    private Button leftRight;

    @FXML
    private Button leftUp;

    @FXML
    private Button upRight;

    @FXML
    private Button rightDown;

    @FXML
    private Button downLeft;

    @FXML
    private Button downUp;

    @FXML
    private Button createStation;

    @FXML
    private Accordion accordion1;

    @FXML
    private Accordion accordion2;

    @FXML
    private TextArea textArea;

    @FXML
    private TextField filename;

    @FXML
    public Button done;

    private Weg weg = new Weg();
    private int x = 0;
    private int y = 0;
    private boolean first = true;

    private int[][] stations = new int[10][2];
    private int currentStation = 0;

    public SftpClient sftpClient = new SftpClient("gis-informatik.de", 22, "marc.bernard");

    @FXML
    private void initialize() {
        Selectareea.setVisible(false);
        uploadButton.setVisible(false);
        for (int x = 0; x <= 11; x++) {
            for (int y = 0; y <= 10; y++) {
                ImageView station = new ImageView();
                station.setFitHeight(72.0);
                station.setFitWidth(72.0);
                station.setLayoutX(x * 72.0);
                station.setLayoutY(y * 72.0);
                station.setPickOnBounds(true);
                station.setPreserveRatio(true);
                Image image = new Image(getClass().getResourceAsStream("/com/piske/piske/Images/addStation.png"));
                station.setImage(image);
                int finalx = x;
                int finaly = y;
                station.setOnMouseClicked(event -> {
                    Selectareea.setVisible(true);
                    this.x = finalx;
                    this.y = finaly;
                    this.first = true;
                });
                gamescreen.getChildren().add(station);
            }
        }

        // Event handlers for buttons
        leftRight.setOnAction(event -> {
            // Handle leftRight button click
            this.addElement("leftRight");
        });

        leftUp.setOnAction(event -> {
            // Handle leftUp button click
            this.addElement("leftUp");
        });

        upRight.setOnAction(event -> {
            // Handle upRight button click
            this.addElement("upRight");
        });

        rightDown.setOnAction(event -> {
            // Handle rightDown button click
            this.addElement("rightDown");
        });

        downLeft.setOnAction(event -> {
            // Handle downLeft button click
            this.addElement("downLeft");
        });

        downUp.setOnAction(event -> {
            // Handle downUp button click
            this.addElement("downUp");
        });

        createStation.setOnAction(event -> {
            // Handle createStation button click
            this.addElement("Station");
        });

        done.setOnAction(event -> {
            System.out.println("Back");
            FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/com/piske/piske/mainmenu.fxml"));
            Parent root1 = null;
            try {
                root1 = loader1.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            MainMenuController mainMenuController = loader1.getController();
            System.out.println("mainMenuController loaded: " + mainMenuController);

            // Add both roots to a main container (e.g., VBox)
            VBox root = new VBox(root1);

            Scene scene = new Scene(root, 1280, 720);
            Stage stage = (Stage) this.gamescreen.getScene().getWindow();
            Platform.runLater(() -> {
                stage.setScene(scene);
                stage.show();
            });
        });

        checkMap.setOnAction(event -> {
            // Handle checkMap button click

            boolean isWegPossible = weg.isPossibleToWalkBySchuler();
            if (isWegPossible) {
                weg.appendPfad('w', 'e', -1, weg.tail.mapY);
                Platform.runLater(() -> {
                    uploadButton.setVisible(true);
                });

            } else {
                System.out.println("Not accepted");
            }
        });

        uploadButton.setOnAction(event -> {
            // Handle uploadButton button click
            // Upload the map
            // Create JSON object
            JSONObject mapObject = new JSONObject();
            JSONArray mapArray = new JSONArray();
            Pfad current = Weg.getHead();
            while (current != null) {
                JSONArray path = new JSONArray();
                path.put(String.valueOf(current.getStart()));
                path.put(String.valueOf(current.getEnd()));
                path.put(current.getMapX());
                path.put(current.getMapY());
                mapArray.put(path);
                current = current.getNext();
            }
            mapObject.put("Map", mapArray);

            // Read in station
            JSONArray stationArray = new JSONArray();
            for (int i = 0; i < currentStation; i++) {
                JSONArray station = new JSONArray();
                station.put(stations[i][0]);
                station.put(stations[i][1]);
                stationArray.put(station);
            }
            mapObject.put("Stations", stationArray);

            // Write JSON object to file
            File file = new File("map.json");
            try (FileWriter fileWriter = new FileWriter(file)) {
                System.out.println("File created at: " + file.getAbsolutePath());
                fileWriter.write(mapObject.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Upload file to server
            try {
                sftpClient.authPassword("Piske12!");
            } catch (JSchException e) {
                throw new RuntimeException(e);
            }

            if (!filename.getText().isEmpty()) {
                try {
                    sftpClient.uploadFile(file.getAbsolutePath(), "/home/www/Maps/" + filename.getText() + ".json");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/com/piske/piske/mainmenu.fxml"));
                Parent root1 = null;
                try {
                    root1 = loader1.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                MainMenuController mainMenuController = loader1.getController();
                System.out.println("mainMenuController loaded: " + mainMenuController);

                // Add both roots to a main container (e.g., VBox)
                VBox root = new VBox(root1);

                Scene scene = new Scene(root, 1280, 720);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Platform.runLater(() -> {
                    stage.setScene(scene);
                    stage.show();
                });
            }
        });
    }

    public void addElement(String type) {
        if (this.first) {
            this.first = false;
            // Add element to the map
            switch (type) {
                case "leftRight":
                    // Add leftRight element
                    Utils.createStraightPathView(x, y, false, gamescreen);
                    weg.appendPfad('w', 'e', this.x, this.y);
                    break;
                case "leftUp":
                    // Add leftUp element
                    Utils.createCurvePathView(x, y, 3, gamescreen);
                    weg.appendPfad('w', 'n', this.x, this.y);
                    break;
                case "upRight":
                    // Add upRight element
                    Utils.createCurvePathView(x, y, 0, gamescreen);
                    weg.appendPfad('e', 'n', this.x, this.y);
                    break;
                case "rightDown":
                    // Add rightDown element
                    Utils.createCurvePathView(x, y, 1, gamescreen);
                    weg.appendPfad('s', 'e', this.x, this.y);
                    break;
                case "downLeft":
                    // Add downLeft element
                    Utils.createCurvePathView(x, y, 2, gamescreen);
                    weg.appendPfad('w', 's', this.x, this.y);
                    break;
                case "downUp":
                    // Add downUp element
                    Utils.createStraightPathView(x, y, true, gamescreen);
                    weg.appendPfad('s', 'n', this.x, this.y);
                    break;
                case "Station":
                    // Add Station element
                    ImageView stationImage = new ImageView();
                    stationImage.setFitHeight(72.0);
                    stationImage.setFitWidth(72.0);
                    stationImage.setLayoutX(x * 72.0);
                    stationImage.setLayoutY(y * 72.0);
                    stationImage.setPickOnBounds(true);
                    stationImage.setPreserveRatio(true);
                    Image image = new Image(getClass().getResourceAsStream("/com/piske/piske/Images/Silly.png"));
                    stationImage.setImage(image);
                    gamescreen.getChildren().add(stationImage);
                    stations[currentStation] = new int[] { x, y };
                    currentStation++;
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + type);
            }
            Selectareea.setVisible(false);
        }


    }
}
