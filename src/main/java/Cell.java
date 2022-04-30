public class Cell {
    private int token = 99;
    private int coordinateX;
    private int coordinateY;

    Cell(int i ,int j){
        coordinateX = i;
        coordinateY = j;
    }

    public int getToken(){
        return token;
    }

    public boolean placeToken(int token){
        if(this.token==99) {
            this.token=token;
            return true;
        }else{
            System.out.print("Error placing in the full square");
            return false;
        }


    }
}
