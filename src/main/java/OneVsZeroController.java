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
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;



public class OneVsZeroController {
    OneVsZeroModel model;
    @FXML
    TextField player1Field;
    @FXML
    TextField player2Field;
    @FXML
    Button submitPlayerNames;
    @FXML
    BorderPane pane;
    public OneVsZeroController(){
        model = new OneVsZeroModel();
    }

    public void handleIntro(){
        String player1 = player1Field.getText();
        String player2 = player2Field.getText();
        if(!player1.isBlank() && !player2.isBlank() && !player1.equals(player2)){
            model.decidePlayerOrder(player1,player2);
            pane.setCenter(displayGrid());
        }
    }


    private GridPane displayGrid(){
        GridPane pane = new GridPane();
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                pane.add(createCell(), i, j);
        return pane;
    }

    private Pane createCell(){
        Pane pane = new Pane();
        pane.setPrefSize(150,150);
        pane.setStyle("-fx-border-color:black");
        pane.setOnMouseClicked(e->handleClick(e));
        return pane;
    }
    private void handleClick(MouseEvent event){
        Pane pane = (Pane) event.getSource();
        int row = GridPane.getRowIndex(pane);
        int column = GridPane.getColumnIndex(pane);
        Text text = new Text();
        text.setX(pane.getHeight()/2);
        text.setY(pane.getWidth()/2);
        int token = model.getTokenForText();
        if(token!=-1) {
            text.setText(String.valueOf(token));
            pane.getChildren().add(text);
        }
        model.placeNumber(row,column);


    }


/*
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
        ///for every cell
        this.setPrefSize(150,150);
        setStyle("-fx-border-color: black");
        this.setOnMouseClicked(e->handleClick());

        private void handleClick(){


        }

//place token
         /*           this.token=token;
       // Text text = new Text();
            text.setX(this.getHeight()/2);
            text.setY(this.getWidth()/2);
            text.setText(String.valueOf(token));
            this.getChildren().add(text);

          */


}

