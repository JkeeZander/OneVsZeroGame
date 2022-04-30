import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OneVsZeroModel {
    //logger
    Logger logger = LogManager.getLogger();

    //board
    Cell[][] board = new Cell[3][3];
    public OneVsZeroModel(){
        for(int i = 0;i<3;i++){
            for(int j = 0 ; j<3;j++){
                board[i][j] = new Cell();
            }
        }
    }

    //Config for the game data.
    LocalDateTime startTime;
    private Map<String,Integer> players = new HashMap<String,Integer>();
    private String player1;
    private String player2;
    private String winner;

    private int player1Count = 0;
    private int player2Count = 0;

    private boolean gameIsNotOver = true;

    private String currentPlayer;

    //Choose who is the first to play
    public void decidePlayerOrder(String player1,String player2){
        startTime = LocalDateTime.now();
        this.player1 = player1;
        this.player2 = player2;
        int randomDecision = (int)Math.round(Math.random());
        players.put(player1,Integer.valueOf(0));
        players.put(player2,Integer.valueOf(1));
        if(randomDecision==0){
            currentPlayer = player1;
        }else{
            currentPlayer = player2;
        }
        logger.info("First player is {}",currentPlayer);
        logger.info("Start time is {}",startTime);
    }


    public void draw(){
        for(int i =0 ; i<board.length;i++){
            for(int j = 0 ;j<board.length;j++){
                System.out.print(board[i][j].getToken() + " ");
            }
            System.out.print("\n");
        }
    }
    public int placeNumber(int x,int y) {
        if (!gameIsNotOver) {
            logger.debug("Game is over - game is stopped");
            return -1;
        }
        if (board[x][y].placeToken(Integer.valueOf(players.get(currentPlayer)))) {
            if (checkIsWin(x,y)) {
                winner = currentPlayer;
                logger.info("Winner is {}",winner);
                gameIsNotOver = false;
            }
            if (checkIsFull()) {
                logger.info("Draw - table is full and none of the players win");
                gameIsNotOver = false;
            }
            if (currentPlayer.equals(player1)) {
                currentPlayer = player2;
                player1Count++;
                draw();
            } else {
                currentPlayer = player1;
                player2Count++;
                draw();
            }
            return board[x][y].getToken();
        }else{
            return -1;
        }

    }
    private boolean checkIsFull () {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j].getToken() == 99) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkIsWin (int coordinateX,int coordinateY) {
        int row;
        int column;
        int sum = 0;
        for (row = 0; row < 3; row++) {
            if (board[row][coordinateY].getToken() == 99) {
                break;
            }
            sum += board[row][coordinateY].getToken();
        }
        if ((sum == 0 || sum == 1 || sum == 3) && row == 3) {
            return true;
        }

        sum = 0;
        for (column = 0; column < 3; column++) {
            if (board[coordinateX][column].getToken() == 99) {
                break;
            }
            sum += board[coordinateX][column].getToken();
        }
        if ((sum == 0 || sum == 1 || sum == 3) && column == 3) {
            return true;
        }
        return false;
    }



}
