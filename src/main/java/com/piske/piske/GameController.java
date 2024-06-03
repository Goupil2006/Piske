package com.piske.piske;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
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
    }

    @FXML
    public AnchorPane gamescreen;

    @FXML
    public Button startwavebutton;

    Weg schuelerweg = new Weg();
    public int[] anzahlSchueler;
    public SchülerManager schülerManager = new SchülerManager();
    public int phase = 1;
    public Dankeschoen sounds;

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

    public void createProjectile(int x, int y, double a, int v, int h, int w, Schüler target, int damage) {
        System.out.println("go");
        Projectile p = new Projectile(x, y, a, v, h, w, gamescreen, damage, schülerManager);
        System.out.println("goo");
        Consumer<Projectile> checkColision = ((Projectile projectile) -> {
            // schülerManager.checkColistion(projectile);
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//      //  startwavebutton.toFront();
//      //  startwavebutton.setVisible(true);
//        startwavebutton.setOnAction(event -> {
//            System.out.println("test");
//            this.startwave(event);
//        });
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

        // delay(1000, () -> {
        // System.out.println(String.valueOf(difficulty));
        // for (int i = -1; i < (int) (difficulty / 10); i++) {
        // int finalI = i + 1;
        // delay((i + 1) * 2000, () -> {
        // Platform.runLater(() -> {
        // System.out.println("Spawn");
        // schülerManager.addSchüler(new Schüler(0, 0, gamescreen));
        // System.out.println(schülerManager.length());
        // schülerManager.getSchülerAtIndex(schülerManager.length() -
        // 1).goWeg(schuelerweg);
        // });
        // });
        // }
        // });

        erzeugeWellen(4);

        delay(1500, () -> {
            System.out.println(schülerManager.getSchülerAtIndex(0).getX());
            System.out.println(schülerManager.getSchülerAtIndex(0).getY());
        });

        delay(5500, () -> {
            System.out.println(schülerManager.getSchülerAtIndex(0).getX());
            System.out.println(schülerManager.getSchülerAtIndex(0).getY());
        });

        this.sounds = new Dankeschoen();
    }

    public void startwave(ActionEvent event) {
        this.sounds.sagDankeschoen();
        System.out.println(phase);
        if (phase % 2 != 0) {
            System.out.println("Startwave");
            this.phase++;
            erzeugeWelle(this.phase / 2);
            ((Node) event.getSource()).setVisible(false);
            interfaceController.uhr.runTimer(anzahlSchueler[this.phase / 2] * 3 + 30);
            delay((anzahlSchueler[this.phase / 2] * 3) * 1000, () -> {
                this.phase++;
                Platform.runLater(() -> {
                    startwavebutton.setVisible(true);
                });
            });
        }
    }

    public void erzeugeWellen(int j) {
        anzahlSchueler = new int[j];
        for (int i = 0; i < j; i++) {
            anzahlSchueler[i] = (int) (i * 5 + (Math.random() * 10 * i * difficulty / 10));
        }
    }

    public void erzeugeWelle(int num) {
        for (int i = 0; i < anzahlSchueler[num]; i++) {
            int finalI = i;
            delay((int) ((2000 * i) + (Math.random() * 2000)), () -> {
                Platform.runLater(() -> {
                    System.out.println("Spawn");
                    schülerManager.addSchüler(new Schüler(0, 0, gamescreen));
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