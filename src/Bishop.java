import java.util.ArrayList;

public class Bishop extends Piece{
    public Bishop(String pieceColor){
        super(pieceColor, "Bishop");
    }

    public int[] setMoveDirections(){
        ArrayList<Integer> directions = new ArrayList<Integer>();
        int pieceIndex = getPieceIndex();
        int diagonalPP = isDirectionBlocked(8, 1, 1);
        int diagonalPN = isDirectionBlocked(8, 1, -1);
        int diagonalNP = isDirectionBlocked(8, -1, 1);
        int diagonalNN = isDirectionBlocked(8, -1, -1);

        for(int i = 1; i <= 7; i++){
            if(i <= diagonalPP){
                directions.add(pieceIndex + i * 8 + i);
            }
            if(i <= diagonalPN){
                directions.add(pieceIndex + i * (-8) + i);
            }
            if(i <= diagonalNP){
                directions.add(pieceIndex + i * 8 - i);
            }
            if(i <= diagonalNN){
                directions.add(pieceIndex + i * (-8) - i);
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
