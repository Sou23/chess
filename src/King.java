import java.util.ArrayList;

public class King extends Piece{

    public King(String pieceColor){
        super(pieceColor, "King");
    }

    public int[] setMoveDirections(){
        ArrayList<Integer> directions = new ArrayList<Integer>();
        int pieceIndex = getPieceIndex();
        int diagonalPP = isDirectionBlocked(1, 1, 1);
        int diagonalPN = isDirectionBlocked(1, 1, -1);
        int diagonalNP = isDirectionBlocked(1, -1, 1);
        int diagonalNN = isDirectionBlocked(1, -1, -1);
        int horizontalP = isDirectionBlocked(1, 1, 0);
        int horizontalN = isDirectionBlocked(1, -1, 0);
        int verticalN = isDirectionBlocked(1, 0, -1);
        int verticalP = isDirectionBlocked(1, 0, 1);
        for(int i = 1; i <= 1; i++){
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
