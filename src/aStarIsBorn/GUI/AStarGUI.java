package aStarIsBorn.GUI;

import aStarIsBorn.Heuristique;
import graphe.Graphe;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class AStarGUI extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private VBox initLayout;
    private VBox aStarLayout;

    private Graphe graphe;

    private Heuristique selectedHeuristique = Heuristique.EUCLIDEAN;

    @Override
    public void start(Stage stage) throws Exception {
        this.primaryStage = stage;
        primaryStage.setTitle("A*");

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AStarGUI.class.getResource("view/RootLayout.fxml"));
            rootLayout = loader.load();
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();

            showInit();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void showInit() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(AStarGUI.class.getResource("view/InitLayout.fxml"));

        InitController controller = new InitController();
        loader.setController(controller);
        controller.setMain(this);

        try {
            initLayout = loader.load();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        rootLayout.setCenter(initLayout);
    }

    public void showResolution(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(AStarGUI.class.getResource("view/AStarResolution.fxml"));

        AStarController controller = new AStarController();
        loader.setController(controller);
        controller.setMain(this);

        try {
            aStarLayout = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        rootLayout.setCenter(aStarLayout);
    }

    public Graphe getGraphe() {
        return graphe;
    }

    public void setGraphe(Graphe graphe) {
        this.graphe = graphe;
    }

    public Heuristique getSelectedHeuristique() {
        return selectedHeuristique;
    }

    public void setSelectedHeuristique(Heuristique selectedHeuristique) {
        this.selectedHeuristique = selectedHeuristique;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }
}
