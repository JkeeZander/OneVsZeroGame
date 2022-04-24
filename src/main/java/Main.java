
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.util.*;
import java.time.*;

import javax.sql.DataSource;

/**
 * JavaFX App
 */
public class Main extends Application {
    //squares
    private Cell[][] cell = new Cell[3][3];

    //Game data - player names, count of moves of each player and the start date and time.
    LocalDateTime startTime = LocalDateTime.now();
    private String player1;
    private String player2;
    private String winner;
    private int player1Count = 0;
    private int player2Count = 0;

    private Map<String,Integer> players = new HashMap<String,Integer>();
    private boolean gameIsNotOver = true;
    private String currentPlayer;

    //Variable used for deciding which player will go first
    private int randomDecision = (int)Math.round(Math.random());



    @Override
    public void start(Stage primaryStage) {

        BorderPane borderPane = new BorderPane();
        getUserNames(borderPane);

        Scene scene = new Scene(borderPane, 450, 450);
        primaryStage.setTitle("OneVsZero");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    private GridPane displayGrid(){
        GridPane pane = new GridPane();
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                pane.add(cell[i][j] = new Cell(i, j), i, j);
        return pane;
    }

    private void decidePlayerOrder(){
        players.put(player1,Integer.valueOf(0));
        players.put(player2,Integer.valueOf(1));
        if(randomDecision==0){
            currentPlayer = player1;
            System.out.print("First player is" + currentPlayer);

        }else{
            currentPlayer = player2;
        }
    }

    private void getUserNames(BorderPane pane){
        StackPane intro = new StackPane();
        TextField player1Name = new TextField();
        TextField player2Name = new TextField();
        Button submitPlayerNames = new Button("Submits");
        submitPlayerNames.setOnAction(e ->{
            player1 = player1Name.getText();
            player2 = player2Name.getText();
            if(!player1.isBlank() && !player2.isBlank() && !player1.equals(player2)){
                decidePlayerOrder();
                pane.setCenter(displayGrid());
            }
        });
        intro.getChildren().addAll(player1Name,player2Name,submitPlayerNames);
        intro.setAlignment(player1Name, Pos.TOP_CENTER);
        intro.setAlignment(player2Name,Pos.CENTER);
        intro.setAlignment(submitPlayerNames,Pos.BOTTOM_CENTER);
        pane.setCenter(intro);
    }


    public class Cell extends Pane{
        private int token = 99;
        private int coordinateX;
        private int coordinateY;

        Cell(int i ,int j){
            this.setPrefSize(150,150);
            setStyle("-fx-border-color: black");
            this.setOnMouseClicked(e->handleClick());
            coordinateX = i;
            coordinateY = j;
        }

        public int getToken(){
            return token;
        }

        private void handleClick(){
            if(!gameIsNotOver){
                return;
            }

            if(placeToken(Integer.valueOf(players.get(currentPlayer)))) {

                if (checkIsWin()) {
                    winner = currentPlayer;
                    System.out.println("WInner is " + winner);
                    gameIsNotOver = false;
                    return;
                }

                if (checkIsFull()) {
                    System.out.println("Draw - no winner");
                    gameIsNotOver = false;
                    return;
                }

                if (currentPlayer.equals(player1)) {
                    currentPlayer = player2;
                    player1Count++;
                } else {
                    currentPlayer = player1;
                    player2Count++;
                }
            }

        }

        private boolean placeToken(int token){
            if(this.token==99) {
                this.token=token;
                Text text = new Text();
                text.setX(this.getHeight()/2);
                text.setY(this.getWidth()/2);
                text.setText(String.valueOf(token));
                this.getChildren().add(text);
                return true;
            }else{
                System.out.print("Error placing in the full square");
                return false;
            }


        }

        private boolean checkIsWin(){
            int row;
            int column;
            int sum = 0;
            for(row = 0 ; row<3;row++){
                if(cell[row][coordinateY].getToken()==99) {
                    break;
                }
                sum+=cell[row][coordinateY].getToken();
            }
            if((sum==0 || sum==1 || sum==3) && row==3){
                return true;
            }

            sum = 0;
            for(column = 0;column<3;column++){
                if(cell[coordinateX][column].getToken()==99){
                    break;
                }
                sum+=cell[coordinateX][column].getToken();
            }
            if((sum==0 || sum==1 || sum==3) && column==3){
                return true;
            }

            return false;

        }

        private boolean checkIsFull(){
            for(int i = 0 ;i<cell.length;i++){
                for(int j = 0;j<cell[i].length;j++){
                    if(cell[i][j].getToken()==99){
                        return false;
                    }
                }
            }
            return true;
        }
    }

    public static void main(String[] args) {
        launch();
    }

}