package aStarIsBorn.GUI;

import aStarIsBorn.Heuristique;
import graphe.Graphe;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;

public class InitController {

    private AStarGUI main;

    @FXML
    private TextField nbRows;
    @FXML
    private TextField nbCols;
    @FXML
    private TextField x1;
    @FXML
    private TextField y1;
    @FXML
    private TextField x2;
    @FXML
    private TextField y2;

    @FXML
    private MenuButton heuristique;

    public AStarGUI getMain() {
        return main;
    }

    public void setMain(AStarGUI main) {
        this.main = main;
    }

    @FXML
    public void start(){

        try {
            int rows = Integer.parseInt(nbRows.getText());
            int cols = Integer.parseInt(nbCols.getText());
            int xs = Integer.parseInt(x1.getText());
            int ys = Integer.parseInt(y1.getText());
            int xe = Integer.parseInt(x2.getText());
            int ye = Integer.parseInt(y2.getText());

            if (
                    rows <= 0 || rows > 1000 || cols <= 0 || cols > 1000
                    || xs <= 0 || xs >= cols || ys <= 0 || ys >= rows || xe <= 0 || xe >= cols || ye <= 0 || ye >= rows
            ) {
                throw new NumberFormatException();
            }

            main.setGraphe(Graphe.getRandomGraphe(
                    rows,cols,xs,ys,xe,ye
            ));
            main.showResolution();

        }catch (NumberFormatException e){
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Wrong format for entries", ButtonType.OK);
                alert.showAndWait();
            });
        }
    }

    @FXML
    public void eucSelected(ActionEvent e){
        main.setSelectedHeuristique(Heuristique.EUCLIDEAN);
        heuristique.setText("Euclidian");
    }

    @FXML
    public void manSelected(ActionEvent e){
        main.setSelectedHeuristique(Heuristique.MANHATTAN);
        heuristique.setText("Manhattan");
    }
}
