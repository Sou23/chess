import java.util.ArrayList;

public class Queen extends Piece{
    public Queen(String pieceColor){
        super(pieceColor, "Queen");
    }

    public int[] setMoveDirections(){
        ArrayList<Integer> directions = new ArrayList<Integer>();
        int pieceIndex = getPieceIndex();
        int diagonalPP = isDirectionBlocked(8, 1, 1);
        int diagonalPN = isDirectionBlocked(8, 1, -1);
        int diagonalNP = isDirectionBlocked(8, -1, 1);
        int diagonalNN = isDirectionBlocked(8, -1, -1);
        int horizontalP = isDirectionBlocked(8, 1, 0);
        int horizontalN = isDirectionBlocked(8, -1, 0);
        int verticalN = isDirectionBlocked(8, 0, -1);
        int verticalP = isDirectionBlocked(8, 0, 1);
        for(int i = 1; i <= 7; i++){
            if( i <= verticalN){
                directions.add(pieceIndex + 8 * (-i));
            }
            if(i <= verticalP){
                directions.add(pieceIndex + 8 * i);
            }
            if(i <= horizontalN){
                directions.add(pieceIndex - i);
            }
            if(i <= horizontalP){
                directions.add(pieceIndex + i);
            }
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
