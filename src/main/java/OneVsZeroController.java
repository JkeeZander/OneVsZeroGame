import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;


public class OneVsZeroController {
    //logger
    Logger logger = LogManager.getLogger();

    OneVsZeroModel model;
    @FXML
    TextField player1Field;
    @FXML
    TextField player2Field;
    @FXML
    Button submitPlayerNames;
    @FXML
    BorderPane pane;
    @FXML
    GridPane statBar;

    @FXML
    public void initialize(){
        List<Map.Entry<String,Integer>> list = JsonFileWriterReader.getInstance().getTopScoreList();
        for(int i = 0 ;i<list.size();i++){
            statBar.add(new Text(list.get(i).toString()),i,0);
        }
    }

    /**
     * initialize model for further deployment. THe model has a board represented as an array
     * that will be used for controlling the game
     * @author Altan Dzhumaev
     */
    public OneVsZeroController(){
        model = new OneVsZeroModel();
    }

    /**
     * Handles beginning of the game, displaying the game board and passing configuration information
     * (player names) to the model
     */
    public void handleIntro(){
        String player1 = player1Field.getText();
        String player2 = player2Field.getText();
        if(!player1.isBlank() && !player2.isBlank() && !player1.equals(player2)){
            model.decidePlayerOrder(player1,player2);
            pane.setCenter(displayGrid());
            logger.info("Game started!");
        }else{
            logger.error("Player name(s) is(are) blank or player names are identical");
        }
    }

    /**
     * creates a game board and displays it when passed to the scene
     * @return a GridPane that will contain the game board
     */
    private GridPane displayGrid(){
        GridPane pane = new GridPane();
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                pane.add(createCell(), i, j);
        return pane;
    }

    /**
     * UI configuration for each cell. Sets design and size for the cell and places handle for click
     * @return a pane which represents cell
     */
    private Pane createCell(){
        Pane pane = new Pane();
        pane.setPrefSize(150,150);
        pane.setStyle("-fx-border-color:black");
        pane.setOnMouseClicked(e->handleClick(e));
        return pane;
    }

    /**
     * Handles click on the cell, placing token based on the current player move.
     * @param event an event that activates when the user presses on the cell
     */
    private void handleClick(MouseEvent event){
        Pane pane = (Pane) event.getSource();
        int row = GridPane.getRowIndex(pane);
        int column = GridPane.getColumnIndex(pane);
        Text text = new Text();
        text.setX(pane.getHeight()/2);
        text.setY(pane.getWidth()/2);
        int token = model.placeNumber(row,column);
        if(token!=-1) {
            text.setText(String.valueOf(token));
            pane.getChildren().add(text);
        }

    }
}

