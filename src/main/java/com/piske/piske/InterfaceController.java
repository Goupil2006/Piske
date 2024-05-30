package com.piske.piske;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class InterfaceController implements Initializable {

    @FXML
    private VBox contentBox;

    @FXML
    private Parent root;

    @FXML
    private AnchorPane buyinclude;

    @FXML
    private ImageView uhrzeiger;

    public void test(Runnable test) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/piske/piske/Buy.fxml"));
        root = loader.load();

        BuyController buycontorller = loader.getController();
        buycontorller.show();
        // test.run();
        // printNode(rootinterface, 0);
    }

    public static void printNode(Node node, int level) {
        // Print the current node with indentation
        String indent = " ".repeat(level * 2);
        System.out.println(indent + node.getClass().getSimpleName() + ": " + node);

        // If the node is a Parent, recursively print its children
        if (node instanceof Parent) {
            Parent parent = (Parent) node;
            for (Node child : parent.getChildrenUnmodifiable()) {
                printNode(child, level + 1);
            }
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Uhr uhr = new Uhr(uhrzeiger);
        uhr.runTimer(12);

    }
}
