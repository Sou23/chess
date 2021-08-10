import java.util.ArrayList;

public class Rook extends Piece{
    public Rook(String pieceColor){
        super(pieceColor, "Rook");
    }
    public int[] setMoveDirections(){
        ArrayList<Integer> directions = new ArrayList<Integer>();
        int pieceIndex = getPieceIndex();
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
        }
        return directions.stream().mapToInt(i -> i).toArray();
    }

    @Override
    public void highlightDirection(int[] locations){
        super.setMoveDirections(this.setMoveDirections());
        super.highlightDirection(getMoveDirections());
    }
}
