package aStarIsBorn.GUI;

import aStarIsBorn.Heuristique;
import graphe.Graphe;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
        main.setGraphe(Graphe.getRandomGraphe(
                Integer.parseInt(nbRows.getText()),
                Integer.parseInt(nbCols.getText()),
                Integer.parseInt(x1.getText()),
                Integer.parseInt(y1.getText()),
                Integer.parseInt(x2.getText()),
                Integer.parseInt(y2.getText())
                ));
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
}
