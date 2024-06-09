package com.piske.piske;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainMenuController implements Initializable {

    private StationController stationController;
    private InterfaceController interfaceController;
    private BuyController buyController;
    // private MainMenuController mainMenuController;
    private GameController gameController;
    private Parent root2;
    private Parent root3;
    private Parent root4;
    private Parent root5;

    @FXML
    private Slider difficulty;

    @FXML
    private Slider sound;

    @FXML
    private AnchorPane mapselector;

    @FXML
    private Button startbutton;

    @FXML
    private Button startbutton2;

    @FXML
    private Button startbutton3;

    @FXML
    private Button startbutton4;

    @FXML
    private Label won;

    @FXML
    private Label lost;

    private GameController gameContoller;

    int difficultyValue = 1;
    int soundValue = 1;
    String mapjson = "{\n" + //
            "    \"Name\": \"Stadtmarkt\",\n" + //
            "    \"Stations\": [\n" + //
            "        [4,4],\n" + //
            "        [5,4],\n" + //
            "        [6,4],\n" + //
            "        [7,4],\n" + //
            "        [8,4],\n" + //
            "        [4,5],\n" + //
            "        [5,5],\n" + //
            "        [6,5],\n" + //
            "        [7,5],\n" + //
            "        [8,5]\n" + //
            "    ],\n" + //
            "    \"Map\": [\n" + //
            "    [\"w\", \"e\", 0, 4],\n" + //
            "    [\"w\", \"e\", 1, 4],\n" + //
            "    [\"w\", \"n\", 2, 4],\n" + //
            "    [\"s\", \"n\", 2, 3],\n" + //
            "    [\"s\", \"e\", 2, 2],\n" + //
            "    [\"w\", \"e\", 3, 2],\n" + //
            "    [\"w\", \"e\", 4, 2],\n" + //
            "    [\"w\", \"e\", 5, 2],\n" + //
            "    [\"w\", \"e\", 6, 2],\n" + //
            "    [\"w\", \"e\", 7, 2],\n" + //
            "    [\"w\", \"e\", 8, 2],\n" + //
            "    [\"w\", \"e\", 9, 2],\n" + //
            "    [\"w\", \"s\", 10, 2],\n" + //
            "    [\"n\", \"s\", 10, 3],\n" + //
            "    [\"n\", \"s\", 10, 4],\n" + //
            "    [\"n\", \"s\", 10, 5],\n" + //
            "    [\"n\", \"s\", 10, 6],\n" + //
            "    [\"n\", \"w\", 10, 7],\n" + //
            "    [\"e\", \"w\", 9, 7],\n" + //
            "    [\"e\", \"w\", 8, 7],\n" + //
            "    [\"e\", \"w\", 7, 7],\n" + //
            "    [\"e\", \"w\", 6, 7],\n" + //
            "    [\"e\", \"w\", 5, 7],\n" + //
            "    [\"e\", \"w\", 4, 7],\n" + //
            "    [\"e\", \"w\", 3, 7],\n" + //
            "    [\"e\", \"n\", 2, 7],\n" + //
            "    [\"s\", \"n\", 2, 6],\n" + //
            "    [\"s\", \"w\", 2, 5],\n" + //
            "    [\"e\", \"w\", 1, 5],\n" + //
            "    [\"e\", \"w\", 0, 5],\n" + //
            "    [\"e\", \"w\", -1, 5]\n" + //
            "],\n" + //
            "    \"Schueler\": [\n" + //
            "        [1],\n" + //
            "        [2],\n" + //
            "        [3],\n" + //
            "        [3],\n" + //
            "        [3],\n" + //
            "        [2],\n" + //
            "        [2],\n" + //
            "        [2],\n" + //
            "        [1],\n" + //
            "        [1],\n" + //
            "        [1],\n" + //
            "        [1],\n" + //
            "        [1],\n" + //
            "        [4],\n" + //
            "        [4],\n" + //
            "        [1],\n" + //
            "        [1],\n" + //
            "        [1],\n" + //
            "        [3],\n" + //
            "        [3],\n" + //
            "        [2],\n" + //
            "        [2],\n" + //
            "        [2],\n" + //
            "        [1],\n" + //
            "        [1],\n" + //
            "        [1],\n" + //
            "        [3],\n" + //
            "        [4],\n" + //
            "        [3],\n" + //
            "        [2],\n" + //
            "        [1]\n" + //
            "    ]\n" + //
            "}";

    public SftpClient sftpClient = new SftpClient("gis-informatik.de", 22, "marc.bernard");

    private void gameStart(javafx.scene.input.MouseEvent event, int level) throws Exception {
        // Load StationController
        FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/com/piske/piske/Station.fxml"));
        Parent root2 = loader2.load();
        StationController stationController = loader2.getController();
        System.out.println("stationController loaded: " + stationController);

        // Load BuyController
        FXMLLoader loader3 = new FXMLLoader(getClass().getResource("/com/piske/piske/Buy.fxml"));
        Parent root3 = loader3.load();
        BuyController buyController = loader3.getController();
        System.out.println("buyController loaded: " + buyController);

        // Load InterfaceContoller
        FXMLLoader loader4 = new FXMLLoader(getClass().getResource("/com/piske/piske/Interface.fxml"));
        Parent root4 = loader4.load();
        InterfaceController interfaceController = loader4.getController();
        System.out.println("interfaceController loaded: " + interfaceController);

        // Load GameController
        FXMLLoader loader5 = new FXMLLoader(getClass().getResource("/com/piske/piske/game.fxml"));
        Parent root5 = loader5.load();
        gameContoller = loader5.getController();
        System.out.println("gameContoller loaded: " + gameContoller);

        // Load UpgradeController
        FXMLLoader loader6 = new FXMLLoader(getClass().getResource("/com/piske/piske/Upgrade.fxml"));
        Parent root6 = loader6.load();
        UpgradeController upgradeController = loader6.getController();
        System.out.println("UpgradeContoller loaded: " + upgradeController);

        gameContoller.setContollers(stationController, interfaceController, buyController, upgradeController);
        this.gameContoller.setDifAndSound(difficultyValue, soundValue, this.mapjson, level);
        interfaceController.setContollers(stationController, gameContoller, buyController, upgradeController);
        stationController.setContollers(interfaceController, gameContoller, buyController, upgradeController);
        buyController.setContollers(stationController, interfaceController, gameContoller, upgradeController);
        upgradeController.setContollers(stationController, interfaceController, gameContoller, buyController);

        AnchorPane root = new AnchorPane(root5, root4, root3, root2, root6);

        // Create a Scene
        Scene scene = new Scene(root, 1280, 720);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void mapStart(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/piske/piske/Mapcreator.fxml"));
        Parent root = loader.load();
        MapcreatorController mapcreatorController = loader.getController();
        System.out.println("mapcreatorController loaded: " + mapcreatorController);

        VBox rootVBox = new VBox(root);

        Scene scene = new Scene(rootVBox, 1280, 720);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Platform.runLater(() -> {
            stage.setScene(scene);
            stage.show();
        });
    }

    public void won(int level) {
        won.setVisible(true);
        delay(3000, () -> {
            won.setVisible(false);
        });
        System.out.println(level);
        switch (level) {
            case 1:
            case 2:
            case 3:
            case 4:
                if (getStand() > level) {
                    return;
                }
                System.out.println("standright");
                //System.out.println(getClass().getResource("/Stand.txt").toString().split("jar:file://")[1]);
                try {
                    FileWriter writer = new FileWriter("Stand.txt");
                    writer.write(String.valueOf(level));
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                delay(100, () -> {
                    updatebuttons();
                });


                break;
            default:
                break;
        }
    }

    public void lost() {
        lost.setVisible(true);
        delay(3000, () -> {
            lost.setVisible(false);
        });
    }

    public int getStand() {
        try {
            String stand = Files.readString(Paths.get(new File("Stand.txt").getAbsolutePath()));
            if (stand.equals("")) {
                return 0;
            } else {
                return Integer.parseInt(stand);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void updatebuttons() {
        if (getStand() == 0) {
            Platform.runLater(() -> {
                startbutton2.setVisible(false);
                startbutton3.setVisible(false);
                startbutton4.setVisible(false);
            });

        } else if (getStand() == 1) {
            Platform.runLater(() -> {
            startbutton3.setVisible(false);
            startbutton4.setVisible(false);
            });
        } else if (getStand() == 2) {
                Platform.runLater(() -> {

            startbutton4.setVisible(false);
        });
        } else {
                Platform.runLater(() -> {

            startbutton2.setVisible(true);
            startbutton3.setVisible(true);
            startbutton4.setVisible(true);});
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updatebuttons();
        won.setVisible(false);
        lost.setVisible(false);
        difficulty.valueProperty().addListener((observable, oldValue, newValue) -> {
            this.difficultyValue = newValue.intValue();
        });
        sound.valueProperty().addListener((observable, oldValue, newValue) -> {
            this.soundValue = newValue.intValue();
        });
        startbutton.setOnMouseClicked(event -> {
            try {
                this.gameStart(event, 1);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        startbutton2.setOnMouseClicked(event -> {
            this.mapjson = getMapjson(2);
            try {
                this.gameStart(event, 2);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        startbutton3.setOnMouseClicked(event -> {
            this.mapjson = getMapjson(3);
            try {
                this.gameStart(event, 3);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        startbutton4.setOnMouseClicked(event -> {
            this.mapjson = getMapjson(4);
            try {
                this.gameStart(event, 4);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        try {
            sftpClient.authPassword("Piske12!");
        } catch (JSchException e) {
            throw new RuntimeException(e);
        }
        List<String> mapList = null;
        try {
            mapList = sftpClient.listFiles("/home/www/Maps/");
        } catch (SftpException e) {
            throw new RuntimeException(e);
        } catch (JSchException e) {
            throw new RuntimeException(e);
        }

        // Create labels for each map and add them to mapselector
        int i = 0;
        for (String map : mapList) {
            if (i > 1) {
                Label label = new Label(map);
                label.setLayoutY((i - 2) * 25);
                label.setStyle("-fx-padding: 10px;");
                label.setOnMouseClicked(event -> {
                    // Handle label click event
                    try {
                        this.mapjson = sftpClient.downloadFile("/home/www/Maps/" + map);
                    } catch (SftpException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println(this.mapjson);
                    try {
                        this.gameStart(event, -1);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    // Add your logic here
                });
                mapselector.getChildren().add(label);
            }
            i++;
        }
    }

    public String getMapjson(int a) {
        InputStream inputStream = getClass().getResourceAsStream("/com/piske/piske/Maps/Map" + a + ".json");
        if (inputStream == null) {
            try {
                throw new FileNotFoundException("Map4.json not found");
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        byte[] data = null;
        try {
            data = inputStream.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            return new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public void delay(int milliseconds, Runnable task) {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.schedule(task, milliseconds, TimeUnit.MILLISECONDS);
        executor.shutdown();
    }
}
