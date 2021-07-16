package pacman;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * This is the pane organizer class. It contains the two panes needed in the game.
 */
public class PaneOrganizer {
    private BorderPane _root;

    /**
     * This is the pane organizer constructor. It instantiates and sets up the different graphic elements.
     */
    public PaneOrganizer(){
        _root = new BorderPane();
        Pane gamePane = new Pane();
        Pane bottomPane = new Pane();
        Label scoreLabel = new Label();
        Label liveLabel = new Label();
        Button quitButton = new Button();
        this.setupQuitButton(quitButton, gamePane);

        this.setupRootPane();
        this.setupGamePane(gamePane);
        this.setupBottomPane(bottomPane);
        this.setupScoreLabel(scoreLabel, gamePane);
        this.setupLiveLabel(liveLabel, gamePane);
        new Game(gamePane, scoreLabel, liveLabel);
    }

    /**
     * This method sets up the root pane.
     */
    private void setupRootPane(){
        _root.setPrefSize(Constants.STAGE_WIDTH, Constants.STAGE_HEIGHT);
        _root.setStyle("-fx-background-color: darkblue");
    }

    /**
     * This method sets up the game pane.
     */
    private void setupGamePane(Pane gamePane){
        gamePane.setPrefSize(Constants.GAME_PANE_WIDTH, Constants.GAME_PANE_HEIGHT);
        _root.getChildren().add(gamePane);
    }

    /**
     * This method sets up the pane at the bottom that contains the score, lives and quit button.
     */
    private void setupBottomPane(Pane bottomPane){
        bottomPane.setPrefSize(Constants.BOTTOM_PANE_WIDTH, Constants.BOTTOM_PANE_HEIGHT);
        _root.getChildren().add(bottomPane);
    }

    /**
     * This method sets up the label that contains the score.
     */
    private void setupScoreLabel(Label scoreLabel, Pane gamePane){
        scoreLabel.setPrefSize(Constants.LABEL_WIDTH, Constants.LABEL_HEIGHT);
        scoreLabel.setLayoutX(Constants.SCORE_LABEL_X);
        scoreLabel.setLayoutY(Constants.LABEL_Y);
        gamePane.getChildren().add(scoreLabel);
    }

    /**
     * This method sets up the label that contains the lives.
     */
    private void setupLiveLabel(Label liveLabel, Pane gamePane){
        liveLabel.setPrefSize(Constants.LABEL_WIDTH, Constants.LABEL_HEIGHT);
        liveLabel.setLayoutX(Constants.LIVE_LABEL_X);
        liveLabel.setLayoutY(Constants.LABEL_Y);
        gamePane.getChildren().add(liveLabel);
    }

    /**
     * This method sets up the quit button.
     */
    private void setupQuitButton(Button quitButton, Pane gamePane){
        quitButton.setPrefSize(Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT);
        quitButton.setLayoutX(Constants.QUIT_BUTTON_X);
        quitButton.setLayoutY(Constants.QUIT_BUTTON_Y);
        quitButton.setText("Quit");
        quitButton.setFont(Font.font("Verdana", FontWeight.BOLD, Constants.QUIT_FONT_SIZE));
        quitButton.setOnMouseClicked(new QuitHandler());
        gamePane.getChildren().add(quitButton);
    }

    /**
     * This method returns the root pane.
     */
    public BorderPane getRoot(){
        return _root;
    }

    /**
     * This is the Quit Handler class. It quits the game.
     */
    private class QuitHandler implements EventHandler<MouseEvent> {

        /**
         * This is the overrided handle method. It exits the game when called.
         */
        @Override
        public void handle(MouseEvent event) {
            System.exit(0);
        }
    }
}
