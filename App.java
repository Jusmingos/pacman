package pacman;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
  * This is the App class. It sets up and starts the game.
  *
  */

public class App extends Application {

    /**
     * This is the overrided start method. It sets up the stage and the scene.
     */
    @Override
    public void start(Stage stage) {
        stage.setTitle("Pacman");
        Scene scene = new Scene(new PaneOrganizer().getRoot());
        stage.setScene(scene);
        stage.show();
    }

    /*
    * Here is the mainline!
    */
    public static void main(String[] argv) {
        // launch is a method inherited from Application
        launch(argv);
    }
}
