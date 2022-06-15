package aStar.GUI;

import aStar.A_StarListener;
import aStar.Heuristique;
import graphe.Graphe;
import graphe.Sommet;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class AStarController implements A_StarListener {

    private AStarGUI main;

    private List<List<Rectangle>> cells;

    @FXML
    private GridPane grid;
    @FXML
    private VBox parent;

    @FXML
    public void initialize(){

        cells = new ArrayList<>();

        Graphe graphe = main.getGraphe();

        for (int row = 0 ; row < graphe.nbLigne() ; row ++){
            grid.addRow(row);
        }
        for (int col = 0 ; col < graphe.nbColonne() ; col ++){
            grid.addColumn(col);
        }


        for (int row = 0 ; row < graphe.nbLigne() ; row ++){

            cells.add(new ArrayList<>());

            for (int col = 0 ; col < graphe.nbColonne() ; col ++){

                Sommet sommet = graphe.getSommet(col,row);

                Rectangle rectangle = new Rectangle();
                Color fill = null;

                switch (sommet.getType()) {
                    case OBSTACLE : fill = Color.BLACK; break;
                    case COMMON : fill = Color.WHITE; break;
                    case START : fill = Color.BLUE; break;
                    case END : fill = Color.BLUE; break;
                    default : fill = Color.GRAY;
                }

                rectangle.setFill(fill);
                double width = 700./graphe.nbColonne();
                double heigth = 700./graphe.nbLigne();
                rectangle.setWidth(Math.min(width,heigth));
                rectangle.setHeight(Math.min(width,heigth));

                grid.add(rectangle,col,row);
                cells.get(row).add(rectangle);

                GridPane.setFillWidth(rectangle,true);
                GridPane.setFillHeight(rectangle, true);
            };
        }

        A_StarAsync resolution = new A_StarAsync(this, graphe, getMain().getSelectedHeuristique());

        resolution.start();

    }

    public AStarGUI getMain() {
        return main;
    }

    public void newResolution(){
        main.showInit();
    }

    public void setMain(AStarGUI main) {
        this.main = main;
    }

    @Override
    public void onNewCurrent(Sommet current) {
        Platform.runLater(() -> {
            cells.get(current.getY()).get(current.getX()).setFill(Color.GREEN);
        });
    }

    @Override
    public void onNewDiscovered(Sommet discovered) {
        Platform.runLater(() -> {
            cells.get(discovered.getY()).get(discovered.getX()).setFill(Color.YELLOW);
        });
    }

    @Override
    public void onResolutionFound(List<Sommet> solution) {
        if (solution != null) {
            Platform.runLater(() -> {
                solution.forEach(s -> {
                    cells.get(s.getY()).get(s.getX()).setFill(Color.BLUE);
                });
            });
        }else {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "No solution for this graphe", ButtonType.OK);
                alert.showAndWait();
            });
        }
    }
}
