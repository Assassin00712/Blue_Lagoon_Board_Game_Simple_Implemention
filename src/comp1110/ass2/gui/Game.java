package comp1110.ass2.gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

// FIXME Task 14
// FIXME Task 15
public class Game extends Application {

    private final Group root = new Group();
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 700;

    private final Group controls = new Group();
    private TextField stateTextField;


    /**
     * Create a basic text field for input and a refresh button.
     */
    private void makeControls() {
        Label playerLabel = new Label("Enter number of players:");
        stateTextField = new TextField();
        stateTextField.setPrefWidth(200);
        Button button = new Button("Start Game");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                int numberOfPlayers = Integer.parseInt(stateTextField.getText());
                Label startLabel = new Label(numberOfPlayers + " Players Start!");
                startLabel.setFont(Font.font(20));
                startLabel.setLayoutX(WINDOW_WIDTH/2 - 20);
                startLabel.setLayoutY(WINDOW_HEIGHT/2 - 10);
                root.getChildren().add(startLabel);

            }
        });
        HBox hb = new HBox();
        hb.getChildren().addAll(playerLabel, stateTextField, button);
        hb.setSpacing(10);
        hb.setLayoutX(50);
        hb.setLayoutY(WINDOW_HEIGHT - 50);
        controls.getChildren().add(hb);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(this.root, WINDOW_WIDTH, WINDOW_HEIGHT);

        root.getChildren().add(controls);
        makeControls();

        stage.setScene(scene);
        stage.show();
    }
}
