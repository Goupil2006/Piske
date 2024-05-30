package com.piske.piske;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StationController implements Initializable {

    // private StationController stationController;
    private InterfaceController interfaceController;
    private BuyController buyController;
    private GameController gameController;
    private UpgradeController upgradeController;

    public void setContollers(InterfaceController interfaceController, GameController gameController,
            BuyController buyController, UpgradeController upgradeController) {
        // this.stationController = stationController;
        this.interfaceController = interfaceController;
        this.buyController = buyController;
        this.gameController = gameController;
        this.upgradeController = upgradeController;
    }

    @FXML
    private AnchorPane plane;

    @FXML
    private ImageView[] stations;

    private Parent root;

    public boolean active = true;

    private ArrayList<Station> Stations = new ArrayList<>();

    @FXML
    private void addStation(int x, int y) throws Exception {
        System.out.println(this.buyController);
        Consumer<String> createStation = ((String type) -> {
            switch (type) {
                case "silli":
                    try {
                        System.out.println("creating silli: " + x + " " + y);
                        addSilli(x, y);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case "ira":
                    try {
                        System.out.println("creating ira: " + x + " " + y);
                        addIra(x, y);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;

                default:
                    break;

                case "biene":
                    try {
                        System.out.println("creating biene: " + x + " " + y);
                        addBiene(x, y);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;

                case "conny":
                    try {
                        System.out.println("creating conny: " + x + " " + y);
                        addConny(x, y);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;

                case "evy":
                    try {
                        System.out.println("creating evy: " + x + " " + y);
                        addEvy(x, y);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
            }
        });
        this.buyController.start(createStation);
    }

    private void addSilli(int x, int y) throws IOException {
        Station newStation = new Station(interfaceController, gameController, this, this.plane, x, y,
                "/com/piske/piske/Images/Silly.png");
        Stations.add(newStation);
    }

    private void addIra(int x, int y) throws IOException {
        Station newStation = new Station(interfaceController, gameController, this, this.plane, x, y,
                "/com/piske/piske/Images/Ira.png");
        Stations.add(newStation);
    }

    private void addBiene(int x, int y) throws IOException {
        Station newStation = new Station(interfaceController, gameController, this, this.plane, x, y,
                "/com/piske/piske/Images/Biene.png");
        Stations.add(newStation);
    }

    private void addConny(int x, int y) throws IOException {
        Station newStation = new Station(interfaceController, gameController, this, this.plane, x, y,
                "/com/piske/piske/Images/Conny.png");
        Stations.add(newStation);
    }

    private void addEvy(int x, int y) throws IOException {
        Station newStation = new Station(interfaceController, gameController, this, this.plane, x, y,
                "/com/piske/piske/Images/Evy.png");
        Stations.add(newStation);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        int topleftX = 4;
        int topleftY = 4;
        int bottomrightX = 8;
        int bottomrightY = 5;
        for (int x = topleftX; x <= bottomrightX; x++) {
            for (int y = topleftY; y <= bottomrightY; y++) {
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
                    try {
                        addStation(finalx, finaly);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
                plane.getChildren().add(station);
            }
        }
    }

    public void delay(int milliseconds, Runnable task) {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.schedule(task, milliseconds, TimeUnit.MILLISECONDS);
        executor.shutdown();
    }
}
