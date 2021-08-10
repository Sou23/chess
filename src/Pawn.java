import java.util.ArrayList;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Pawn extends Piece{
    private int firstExtraStep = 1;

    public Pawn(String pieceColor){
        super(pieceColor, "Pawn");
    }

    private int[] setMoveDirections(){
        ArrayList<Integer> directions = new ArrayList<Integer>();
        int step = getPieceColor().equals(Chess.BLACK_P) ? 8 : -8;
        int pieceIndex = getPieceIndex() + 1;
        int newLoc, index = pieceIndex%8 == 0 ? 8 : pieceIndex%8;
    
        for(int i = 0; i <= firstExtraStep; i++){
            newLoc = pieceIndex + step * (i + 1);
            if(newLoc <= 64 && newLoc >= 1){
                if(i == 0){
                    if(newLoc >=1 && newLoc <= 64 && index < 8 && super.canTakeDown(newLoc)){
                        directions.add(newLoc);
                    }
                    if(newLoc-2 >=1 && newLoc-2 <= 64 && index > 1 && super.canTakeDown(newLoc-2)){
                        directions.add(newLoc - 2);
                    }
                }
                if(!this.isBlocked(newLoc - 1)){
                    directions.add(newLoc - 1);
                }else{
                    break;
                }
            }else{
                break;
            }
        }
        return directions.stream().mapToInt(x->x).toArray();
    }

    @Override
    public void highlightDirection(int[] locations){
        super.setMoveDirections(this.setMoveDirections());
        super.highlightDirection(getMoveDirections());
    }

    @Override
    public void movePiece(int newLocation){
        super.movePiece(newLocation);
        pawnConvert(this);
        firstExtraStep = 0;
    }

    @Override
    public boolean isBlocked(int index){
        JPanel panel = (JPanel)Game.BOARD_PANELS.getComponent(index);
        return panel.getComponentCount() > 0;
    }

    private void pawnConvert(final Pawn pawn){
        int indexBorder = getPieceColor().equals(Chess.BLACK_P) ? 56 : 0; 
        int index = getPieceIndex();
        if(index >= indexBorder && index < indexBorder + 8){
            JFrame convertPawn = new JFrame("Select a piece");
            convertPawn.setLayout(new GridLayout(2,2));
            convertPawn.setSize(172, 172);
            convertPawn.setResizable(false);
            convertPawn.setFocusable(true);
            convertPawn.setVisible(true);
            Piece[] pieces = {
                new Queen(getPieceColor()), new Bishop(getPieceColor()),
                new Knight(getPieceColor()), new Rook(getPieceColor())
            };
            JPanel panel;
            for(Piece p : pieces){
                panel = new JPanel();
                panel.add(p);
                panel.addMouseListener(new MouseAdapter(){
                    public void mousePressed(MouseEvent e){
                        JPanel panelPressed = (JPanel)e.getComponent();
                        Piece piece = (Piece)panelPressed.getComponent(0);
                        JPanel pawnPanel = (JPanel)Game.BOARD_PANELS.getComponent(pawn.getPieceIndex());
                        pawnPanel.add(piece);
                        pawnPanel.remove(pawn);
                        pawnPanel.revalidate();
                        pawnPanel.repaint();
                        JPanel panels = (JPanel)panelPressed.getParent();
                        JFrame frame = (JFrame)panels.getParent();
                        frame.dispose();
                    }
                });
                convertPawn.add(panel);
            }
        }
    }
}