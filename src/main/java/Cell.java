import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Cell {
    private int token = 99;
    Logger logger = LogManager.getLogger();

    public int getToken(){
        return token;
    }

    /**
     * stores the token(number) inside the cell if it is empty
     * @param token a number that is placed in the cell
     * @return a boolean if the operation was successful
     */
    public boolean placeToken(int token){
        if(this.token==99) {
            this.token=token;
            logger.info("Token placed to the cell successfully");
            return true;
        }else{
            logger.debug("Error placing in the full cell");
            return false;
        }


    }
}
