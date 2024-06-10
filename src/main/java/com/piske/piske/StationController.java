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

import org.json.JSONArray;
import org.json.JSONObject;

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

        JSONObject mapJson = new JSONObject(gameController.mapjson);
        this.loadStations(mapJson);
    }

    @FXML
    private AnchorPane plane;

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
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case "ira":
                    try {
                        System.out.println("creating ira: " + x + " " + y);
                        addIra(x, y);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    break;

                default:
                    break;

                case "biene":
                    try {
                        System.out.println("creating biene: " + x + " " + y);
                        addBiene(x, y);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    break;

                case "conny":
                    try {
                        System.out.println("creating conny: " + x + " " + y);
                        addConny(x, y);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    break;

                case "evy":
                    try {
                        System.out.println("creating evy: " + x + " " + y);
                        addEvy(x, y);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    break;
            }
        });
        System.out.println("buynew");
        this.upgradeController.stop();
        this.buyController.start(createStation);
    }

    private void addSilli(int x, int y) throws Exception {
        this.newStation(interfaceController, gameController, this, this.plane, x, y,
                "/com/piske/piske/Images/Silly.png", "Silli", 300, 1000, 10, 50);
    }

    private void addIra(int x, int y) throws Exception {
        this.newStation(interfaceController, gameController, this, this.plane, x, y,
                "/com/piske/piske/Images/Ira.png", "Ira", 1000, 1000, 5, 60);
    }

    private void addBiene(int x, int y) throws Exception {
        this.newStation(interfaceController, gameController, this, this.plane, x, y,
                "/com/piske/piske/Images/Biene.png", "Biene", 500, 2000, 15, 80);
    }

    private void addConny(int x, int y) throws Exception {
        this.newStation(interfaceController, gameController, this, this.plane, x, y,
                "/com/piske/piske/Images/Conny.png", "Conny", 150, 1000, 20, 100);
    }

    private void addEvy(int x, int y) throws Exception {
        this.newStation(interfaceController, gameController, this, this.plane, x, y,
                "/com/piske/piske/Images/Evy.png", "Evy", 400, 1500, 15, 200);
    }

    public void newStation(InterfaceController interfaceController, GameController gameController,
            StationController stationController, AnchorPane plane, int x,
            int y, String grafic, String name, int range, int speed, int damage, int price) throws Exception {
        if (this.interfaceController.getMoney() >= price) {
            // create Station
            Station station = new Station(interfaceController, gameController, stationController, plane, x, y, grafic,
                    name, range, speed, damage, price);
            // let upgradeController know about the new station
            this.upgradeController.addStation(station);
            // Add Station to stationarray (Arraylist)
            Stations.add(station);
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void removeStation(Station station) {
        this.plane.getChildren().remove(station.imageView);
        station.changeecsists();
        this.Stations.remove(station);
    }

    public void loadStations(JSONObject json) {
        // create Stations
        JSONArray stations = json.getJSONArray("Stations");
        for (int i = 0; i < stations.length(); i++) {
            // create Staion
            ImageView station = new ImageView();
            station.setFitHeight(72.0);
            station.setFitWidth(72.0);
            station.setLayoutX(stations.getJSONArray(i).getInt(0) * 72.0);
            station.setLayoutY(stations.getJSONArray(i).getInt(1) * 72.0);
            station.setPickOnBounds(true);
            station.setPreserveRatio(true);
            Image image = new Image(getClass().getResourceAsStream("/com/piske/piske/Images/addStation.png"));
            station.setImage(image);
            int finalx = stations.getJSONArray(i).getInt(0);
            int finaly = stations.getJSONArray(i).getInt(1);
            station.setOnMouseClicked(event -> {
                // if (this.gameController.phase % 2 != 0) {
                try {
                    addStation(finalx, finaly);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                // }

            });
            // add to screen
            plane.getChildren().add(station);
        }
    }

    // delay function for waiting (internets)
    public void delay(int milliseconds, Runnable task) {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.schedule(task, milliseconds, TimeUnit.MILLISECONDS);
        executor.shutdown();
    }
}
