import java.util.ArrayList;

public class Knight extends Piece{
    private final int[][] moveLogic = {{2,1},{1,2}}; 
    
    public Knight(String pieceColor){
        super(pieceColor, "Knight");
    }

    private int[] setMoveDirections(){
        ArrayList<Integer> directions = new ArrayList<Integer>();
        int pieceIndex = getPieceIndex() + 1;
        int index = pieceIndex%8 == 0 ? 8 : pieceIndex%8;
        int newLoc;
        for(int[] move : moveLogic){
            if (index + move[1] <= 8){
                newLoc = pieceIndex + move[0] * 8 + move[1];
                if(newLoc <= 64 && newLoc >= 1 && !isBlocked(newLoc - 1)){
                    directions.add(newLoc - 1); 
                }
                newLoc = pieceIndex + move[0] * (-8) + move[1];
                if(newLoc <= 64 && newLoc >= 1 && !isBlocked(newLoc - 1)){
                    directions.add(newLoc - 1); 
                }
            }
            if (index - move[1] >= 1){
                newLoc = pieceIndex + move[0] * 8 - move[1];
                if(newLoc <= 64 && newLoc >= 1 && !isBlocked(newLoc - 1)){
                    directions.add(newLoc - 1); 
                }
                newLoc = pieceIndex + move[0] * (-8) - move[1];
                if(newLoc <= 64 && newLoc >= 1 && !isBlocked(newLoc - 1)){
                    directions.add(newLoc - 1); 
                }
            }
        }
        return directions.stream().mapToInt(i -> i).toArray();
    }

    @Override
    public void highlightDirection(int[] locations){
        super.setMoveDirections(this.setMoveDirections());
        super.highlightDirection(getMoveDirections());
    }
}
