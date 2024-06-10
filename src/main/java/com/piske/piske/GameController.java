package com.piske.piske;

import javafx.application.Platform;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
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
    private int level;

    // set Controllers
    public void setContollers(StationController stationController, InterfaceController interfaceController,
            BuyController buyController, UpgradeController upgradeController) {
        this.stationController = stationController;
        this.interfaceController = interfaceController;
        this.buyController = buyController;
        // this.gameController = gameController;
        this.upgradeController = upgradeController;
    }

    // set difficulty and sound level and map in JSON (Datenformat)
    public void setDifAndSound(int difficulty, int sound, String mapjson, int level) {
        this.difficulty = difficulty;
        this.sound = sound;
        // Initilizing everything
        // Wellen erzeugen (Anzahl Schüler pro Welle)
        erzeugeWellen((int) difficulty / 20 + 3);
        // update wave indicator (aktuelle Welle, max Wellen)
        interfaceController.setWaveIndicator((int) (phase / 2), (int) difficulty / 20 + 3);
        this.mapjson = mapjson;
        System.out.println(mapjson);
        JSONObject Mapjson = new JSONObject(mapjson);
        // Save map to Weg (Object)
        this.loadMap(Mapjson);
        // Show Weg on display
        Utils.renderWeg(this.schuelerweg, this.gamescreen);
        // Create object for sounds
        this.sounds = new Dankeschoen(sound);
        this.level = level;
    }

    @FXML
    public AnchorPane gamescreen;

    Weg schuelerweg = new Weg();
    public int[] anzahlSchueler;
    public SchülerManager schülerManager = new SchülerManager();
    public int phase = 1;
    public Dankeschoen sounds;
    public String mapjson;
    public boolean end = false;

    public void createProjectile(int x, int y, double a, int v, int h, int w, Schüler target, int damage,
            Station station)
            throws IOException {
        // Create projectile
        Projectile p = new Projectile(x, y, a, v, h, w, gamescreen, damage, schülerManager, station);
        // Declare function for giving money to player
        Consumer<Integer> giveMoney = ((Integer money) -> {
            interfaceController.changeAmount((float) money);
        });
        // Start projectile (target (Schüler), giveMoney (function s.o.))
        p.goProjectile(target, giveMoney);
        // Überprüfen ob Spiel vorbei (wenn alle Schüler tot)
        delay(1750, () -> {
            if (this.schülerManager.length() == 0) {
                try {
                    this.endPhase(this.phase);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void loadMap(JSONObject json) {
        this.schuelerweg.reset();
        JSONArray paths = new JSONArray(json.getJSONArray("Map"));
        for (int i = 0; i < paths.length(); i++) {
            JSONArray path = paths.getJSONArray(i);
            char start = path.getString(0).charAt(0);
            char end = path.getString(1).charAt(0);
            int x = path.getInt(2);
            int y = path.getInt(3);
            this.schuelerweg.appendPfad(start, end, x, y);
        }
    }

    public void startwave(ActionEvent event) throws IOException {
        this.sounds.sagDankeschoen();
        System.out.println(phase);
        if (phase % 2 != 0) {
            System.out.println("Startwave");
            this.phase++;
            this.interfaceController.setWaveIndicator((int) (phase / 2), (int) difficulty / 20 + 3);
            // Check if last wave
            if (phase / 2 >= difficulty / 20 + 3) {
                // Set last wave true
                this.end = true;
            }
            this.erzeugeWelle(this.phase / 2);
            ((Node) event.getSource()).setVisible(false);
            this.interfaceController.uhr.runTimer((int) (anzahlSchueler[this.phase / 2 - 1] * 1.2) + 30);
            // delay((int) (anzahlSchueler[this.phase / 2 - 1] * 0.5 + 30) * 1000, () -> {
            // try {
            // this.endPhase(this.phase);
            // } catch (IOException e) {
            // throw new RuntimeException(e);
            // }
            // });
        }
    }

    public void endPhase(int phase) throws IOException {
        this.interfaceController.uhr.stop();
        // check if Welle over
        if (this.phase % 2 == 0) {
            // check if last wave
            if (end) {
                // end game
                this.endGame(1);
                return;
            }
            // initialize next wave
            this.phase++;
            Platform.runLater(() -> {
                this.interfaceController.startwavebutton.setVisible(true);
            });
        }

    }

    public boolean first = true;

    public void endGame(int state) throws IOException {
        // check if fist time game end
        if (!first) {
            return;
        }
        first = false;
        System.out.println("Game Over");
        this.stationController.active = false;
        FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/com/piske/piske/mainmenu.fxml"));
        Parent root1 = loader1.load();
        MainMenuController mainMenuController = loader1.getController();
        switch (state) {
            case 1:
                // let mainMenuController know that the player won
                mainMenuController.won(this.level);
                break;
            case 2:
                // let mainMenuController know that the player lost
                mainMenuController.lost();
                break;
            default:
                break;
        }
        System.out.println("mainMenuController loaded: " + mainMenuController);

        // Add both roots to a main container (e.g., VBox)
        VBox root = new VBox(root1);

        Scene scene = new Scene(root, 1280, 720);
        Stage stage = (Stage) gamescreen.getScene().getWindow();
        // Make sure that tasks are done in JavaFX thread
        Platform.runLater(() -> {
            stage.setScene(scene);
            stage.show();
        });
    }

    public void erzeugeWellen(int j) {
        anzahlSchueler = new int[j];
        for (int i = 0; i < j; i++) {
            anzahlSchueler[i] = (int) ((i + 1) * 5 + (Math.random() * 2 * (i + 1) * difficulty / 10));
        }
        System.out.println("Anzahl" + anzahlSchueler.length + j);
    }

    public void erzeugeWelle(int num) {
        for (int i = 0; i < anzahlSchueler[num - 1]; i++) {
            int finalI = i;
            delay(1200 * i, () -> {
                Platform.runLater(() -> {
                    System.out.println("Spawn");
                    double type = (double) ((3.5 * (Math.pow(1.1, num)) * Math.random())) / 10 * num + 1;
                    System.out.println(type);

                    schülerManager.addSchüler(new Schüler(0, 0, gamescreen, (int) type, schülerManager, this));
                    schülerManager.getSchülerAtIndex(schülerManager.length() - 1).goWeg(schuelerweg);
                });
            });
        }
    }

    // delay function for waiting (internets)
    public void delay(int milliseconds, Runnable task) {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.schedule(task, milliseconds, TimeUnit.MILLISECONDS);
        executor.shutdown();
    }

    // test
}