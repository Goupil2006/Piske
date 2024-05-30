package com.piske.piske;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javafx.scene.input.MouseEvent;
import java.beans.Visibility;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class GameController implements Initializable {

    private StationController stationController;
    private InterfaceController interfaceController;
    private BuyController buyController;
    // private GameController gameController;
    private UpgradeController upgradeController;

    private int difficulty;
    private int sound;

    public void setContollers(StationController stationController, InterfaceController interfaceController,
            BuyController buyController, UpgradeController upgradeController) {
        this.stationController = stationController;
        this.interfaceController = interfaceController;
        this.buyController = buyController;
        // this.gameController = gameController;
        this.upgradeController = upgradeController;
    }

    public void setDifAndSound(int difficulty, int sound) {
        this.difficulty = difficulty;
        this.sound = sound;
    }

    @FXML
    public AnchorPane gamescreen;

    @FXML
    private Button addStation1;

    Weg schuelerweg = new Weg();

    public int[] anzahlSchueler;

    public SchülerManager schülerManager = new SchülerManager();

    public void createStraightPathView(int x, int y, boolean r) {
        x = x * 72;
        y = y * 72;
        y += 21;
        ImageView imageView = new ImageView();
        imageView.setLayoutX(x);
        imageView.setLayoutY(y);
        if (r) {
            imageView.setRotate(90);
        } else {
            imageView.setRotate(0);
        }
        Image image = new Image(getClass().getResourceAsStream("/com/piske/piske/Images/weg.png"));
        imageView.setImage(image);
        gamescreen.getChildren().add(imageView);
    }

    public void update() {
        gamescreen.layout();
    }

    public void createProjectile(int x, int y, double a, int v, int h, int w, Schüler target) {
        System.out.println("go");
        Projectile p = new Projectile(x, y, a, v, h, w, gamescreen);
        System.out.println("goo");
        Consumer<Projectile> checkColision = ((Projectile projectile) -> {
            schülerManager.checkColistion(projectile);
        });
        p.goProjectile(target, checkColision);
    }

    public void createCurvePathView(int x, int y, int r) {
        x = x * 72;
        y = y * 72;

        ImageView imageView = new ImageView();
        if (r == 0) {
            imageView.setRotate(0);
            x += 21;
        } else if (r == 1) {
            imageView.setRotate(90);
            y += 21;
            x += 21;
        } else if (r == 2) {
            imageView.setRotate(180);
            y += 21;
        } else if (r == 3) {
            imageView.setRotate(270);
        }
        imageView.setLayoutX(x);
        imageView.setLayoutY(y);
        Image image = new Image(getClass().getResourceAsStream("/com/piske/piske/Images/wegL.png"));
        imageView.setImage(image);
        gamescreen.getChildren().add(imageView);
    }

    public void renderWeg(Weg w) {
        Pfad temp = Weg.getHead();
        while (Weg.getTail() != temp) {
            if (temp.getStart() == 'n' && temp.getEnd() == 's' || temp.getStart() == 's' && temp.getEnd() == 'n') {
                createStraightPathView(temp.getMapX(), temp.getMapY(), true);
            } else if (temp.getStart() == 'e' && temp.getEnd() == 'w'
                    || temp.getStart() == 'w' && temp.getEnd() == 'e') {
                createStraightPathView(temp.getMapX(), temp.getMapY(), false);
            } else if (temp.getStart() == 'n' && temp.getEnd() == 'e'
                    || temp.getStart() == 'e' && temp.getEnd() == 'n') {
                createCurvePathView(temp.getMapX(), temp.getMapY(), 0);
            } else if (temp.getStart() == 's' && temp.getEnd() == 'e'
                    || temp.getStart() == 'e' && temp.getEnd() == 's') {
                createCurvePathView(temp.getMapX(), temp.getMapY(), 1);
            } else if (temp.getStart() == 's' && temp.getEnd() == 'w'
                    || temp.getStart() == 'w' && temp.getEnd() == 's') {
                createCurvePathView(temp.getMapX(), temp.getMapY(), 2);
            } else if (temp.getStart() == 'n' && temp.getEnd() == 'w'
                    || temp.getStart() == 'w' && temp.getEnd() == 'n') {
                createCurvePathView(temp.getMapX(), temp.getMapY(), 3);
            }
            temp = temp.getNext();
        }

    }

    public void renderMap() {

    }

    // public void changebuymenu() {
    // System.out.println("changebuymenu");
    //
    // if (gamescreen.lookup("#buyhead").isVisible()) {
    // gamescreen.lookup("#buyhead").visibleProperty().bind(new
    // SimpleBooleanProperty(true));
    // } else {
    // gamescreen.lookup("#buyhead").visibleProperty().bind(new
    // SimpleBooleanProperty(false));
    // }
    // }

    public void refreshScene(MouseEvent event) {
        try {
            // Load the FXML file
            Parent root = FXMLLoader.load(getClass().getResource("/com/piske/piske/game.fxml"));

            // Get the current stage
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Create a new scene with the root from the FXML file
            Scene scene = new Scene(root);

            // Set the scene on the stage
            stage.setScene(scene);

            // Show the stage
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Pfad erstellen
        // Map1.json einlesen;
        InputStream inputStream = getClass().getResourceAsStream("/com/piske/piske/Maps/Map1.json");
        try {
            if (inputStream == null) {
                throw new FileNotFoundException("Map1.json not found");
            }
            byte[] data = inputStream.readAllBytes();
            String json = new String(data, "UTF-8");

            JSONObject paths2 = new JSONObject(json);
            JSONArray paths = new JSONArray(paths2.getJSONArray("Map"));
            for (int i = 0; i < paths.length(); i++) {
                JSONArray path = paths.getJSONArray(i);
                char start = path.getString(0).charAt(0);
                char end = path.getString(1).charAt(0);
                int x = path.getInt(2);
                int y = path.getInt(3);
                schuelerweg.appendPfad(start, end, x, y);
            }
        } catch (JSONException e) {
            throw new RuntimeException("Invalid JSON format", e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Map1.json not found", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Unsupported encoding", e);
        } catch (IOException e) {
            throw new RuntimeException("Error reading file", e);
        }

        renderWeg(schuelerweg);

        delay(1000, () -> {
            System.out.println(String.valueOf(difficulty));
            for (int i = -1; i < (int) (difficulty / 10); i++) {
                int finalI = i + 1;
                delay((i + 1) * 2000, () -> {
                    Platform.runLater(() -> {
                        System.out.println("Spawn");
                        schülerManager.addSchüler(new Schüler(0, 0, gamescreen));
                        schülerManager.getSchülerAtIndex(finalI).goWeg(schuelerweg);
                    });
                });
            }
        });

        // erzeugeWellen(4);

        delay(1500, () -> {
            System.out.println(schülerManager.getSchülerAtIndex(0).getX());
            System.out.println(schülerManager.getSchülerAtIndex(0).getY());
        });

        delay(5500, () -> {
            System.out.println(schülerManager.getSchülerAtIndex(0).getX());
            System.out.println(schülerManager.getSchülerAtIndex(0).getY());
        });
    }

    // public void erzeugeWellen(int j ){
    // anzahlSchueler = new int[j];
    // for (int i = 0; i< j; i++) {
    // AnzahlSchueler[i] = i * 5 + (Math.random() * 10 * i);
    // }
    // }
    //
    // public void erzeugeWelle(int num) {
    // for (i = 0; i< num; i++){
    // delay(500, () -> {
    // new Schüler(0, 0, gamescreen);
    // Schüler.goWeg(schuelerweg);
    // })
    // }
    // }

    public void delay(int milliseconds, Runnable task) {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.schedule(task, milliseconds, TimeUnit.MILLISECONDS);
        executor.shutdown();
    }

    // test
}