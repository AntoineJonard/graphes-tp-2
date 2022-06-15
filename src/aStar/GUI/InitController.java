package aStar.GUI;

import aStar.Heuristique;
import graphe.Graphe;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.File;

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
    @FXML
    private Button filename;

    private File loadFile;

    public AStarGUI getMain() {
        return main;
    }

    public void setMain(AStarGUI main) {
        this.main = main;
    }

    @FXML
    public void start(){

        Graphe graphe = null;

        if (loadFile != null){
            graphe = new Graphe(loadFile.getName());
        }else {
            try {
                int rows = Integer.parseInt(nbRows.getText());
                int cols = Integer.parseInt(nbCols.getText());
                int xs = Integer.parseInt(x1.getText());
                int ys = Integer.parseInt(y1.getText());
                int xe = Integer.parseInt(x2.getText());
                int ye = Integer.parseInt(y2.getText());

                if (
                        rows <= 0 || rows > 1000 || cols <= 0 || cols > 1000
                                || xs < 0 || xs >= cols || ys < 0 || ys >= rows || xe < 0 || xe >= cols || ye < 0 || ye >= rows
                ) {
                    throw new NumberFormatException();
                }

                graphe = Graphe.getRandomGraphe(
                        rows,cols,xs,ys,xe,ye
                );

            }catch (NumberFormatException e){
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Wrong format for entries", ButtonType.OK);
                    alert.showAndWait();
                });
            }
        }

        main.setGraphe(graphe);
        main.showResolution();
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

    @FXML
    public void find(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select attachment");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Texte Files", "*.txt"));
        File selectedFile = fileChooser.showOpenDialog(getMain().getPrimaryStage());

        if (selectedFile != null){
            loadFile = selectedFile;
            filename.setText(selectedFile.getName());
        }
    }
}
