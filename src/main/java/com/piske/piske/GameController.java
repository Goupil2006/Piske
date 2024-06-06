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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
        erzeugeWellen((int) difficulty / 20 + 3);
        interfaceController.setWaveIndicator((int) (phase / 2), (int) difficulty / 20 + 3);
    }

    @FXML
    public AnchorPane gamescreen;

    Weg schuelerweg = new Weg();
    public int[] anzahlSchueler;
    public SchülerManager schülerManager = new SchülerManager();
    public int phase = 1;
    public Dankeschoen sounds;

    public void createProjectile(int x, int y, double a, int v, int h, int w, Schüler target, int damage) {
        System.out.println("go");
        Projectile p = new Projectile(x, y, a, v, h, w, gamescreen, damage, schülerManager);
        System.out.println("goo");
        Consumer<Integer> giveMoney = ((Integer money) -> {
            interfaceController.changeAmount((float) money);
        });
        p.goProjectile(target, giveMoney);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        JSONObject Mapjson = null;
        try {
            Mapjson = this.readJson();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.loadMap(Mapjson);
        Utils.renderWeg(this.schuelerweg, this.gamescreen);
        this.sounds = new Dankeschoen(sound);
    }

    public JSONObject readJson() throws IOException {
        // Map1.json einlesen;
        InputStream inputStream = getClass().getResourceAsStream("/com/piske/piske/Maps/Map1.json");
        try {
            if (inputStream == null) {
                throw new FileNotFoundException("Map1.json not found");
            }
            byte[] data = inputStream.readAllBytes();
            String json = new String(data, "UTF-8");

            return new JSONObject(json);
        } catch (JSONException e) {
            throw new RuntimeException("Invalid JSON format", e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Map1.json not found", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Unsupported encoding", e);
        } catch (IOException e) {
            throw new RuntimeException("Error reading file", e);
        }
    }

    public void loadMap(JSONObject json) {
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
            if (phase / 2 >= difficulty / 20 + 3) {
                System.out.println("done");
                ActionEvent finalevent = event;
                delay((int) (anzahlSchueler[this.phase / 2 - 1] * 0.5 + 30) * 1000, () -> {
                    try {
                        this.endGame(finalevent);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

            }
            this.erzeugeWelle(this.phase / 2);
            ((Node) event.getSource()).setVisible(false);
            this.interfaceController.uhr.runTimer((int) (anzahlSchueler[this.phase / 2 - 1] * 0.5) + 30);
            delay((int) (anzahlSchueler[this.phase / 2 - 1] * 0.5 + 30) * 1000, () -> {
                this.phase++;
                Platform.runLater(() -> {
                    ((Node) event.getSource()).setVisible(true);
                });
            });
        }
    }

    public void endGame(ActionEvent event) throws IOException {
        System.out.println("Game Over");
        FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/com/piske/piske/mainmenu.fxml"));
        Parent root1 = loader1.load();
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
            delay(500 * i, () -> {
                Platform.runLater(() -> {
                    System.out.println("Spawn");
                    int type = Math.floor(Math.random() * (1/(difficulty / 20 + 3)) * num * 3 + 1);
                    //if (num > 6) {
                   //     type = (Math.random()*2+1);
                    //}
                   // if (num > 3) {
                     //   type = (Math.random()*2);;
                   // }
                    if (difficulty > 20) {
                        type = (int) num / difficulty + 1;
                    }
                    schülerManager.addSchüler(new Schüler(0, 0, gamescreen, type));
                    schülerManager.getSchülerAtIndex(schülerManager.length() - 1).goWeg(schuelerweg);
                });
            });
        }
    }

    public void delay(int milliseconds, Runnable task) {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.schedule(task, milliseconds, TimeUnit.MILLISECONDS);
        executor.shutdown();
    }

    // test
}