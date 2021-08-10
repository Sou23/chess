import javax.swing.*;

public class Piece extends JLabel{
    private final String pieceColor;
    private int[] moveDirections;

    public Piece(String pieceColor, String pieceName){
        this.pieceColor = pieceColor;
        ImageIcon img = new ImageIcon("Pieces/" + pieceName + " " + pieceColor+ ".png");
        this.setIcon(img);
    }
    public void setMoveDirections(int[] moveDirections){
        this.moveDirections = moveDirections;
    }
    public int[] getMoveDirections(){
        return this.moveDirections;
    }

    public String getPieceColor(){
        return pieceColor;
    }
    
    public void highlightDirection(int[] locations){
        for(int index : locations){
            JPanel panel = (JPanel)Game.BOARD_PANELS.getComponent(index);
            panel.setBorder(Chess.SELECTED);
        }
    }

    public static void resetHighlight(){
        JPanel panel;
        for (int i = 0; i < Game.BOARD_PANELS.getComponentCount(); i++) {
            panel = (JPanel)Game.BOARD_PANELS.getComponent(i);
            if(panel.getBorder() == Chess.SELECTED);{
                panel.setBorder(Chess.UNSELECTED);
            }
        }
        Game.BOARD_PANELS.revalidate();
        Game.BOARD_PANELS.repaint();
    }

    public int getPieceIndex(){
        JPanel panel = (JPanel) this.getParent();
        for(int i = 0; i < Game.BOARD_PANELS.getComponentCount(); i++){
            if(Game.BOARD_PANELS.getComponent(i).equals(panel)){
                return i;
            }
        }
        return -1;
    }

    public void movePiece(int newLocation){
        JPanel newPanel = (JPanel)Game.BOARD_PANELS.getComponent(newLocation);
        if (newPanel.getBorder() == Chess.SELECTED){
            if(canTakeDown(newLocation)){
                takeDown(newLocation);
            }else{
                newPanel.add(this);
                resetHighlight();
            }
        }
    }

    public boolean isBlocked(int index){
        JPanel panel = (JPanel)Game.BOARD_PANELS.getComponent(index);
        if(panel.getComponentCount() > 0){
            Piece piece = (Piece)panel.getComponent(0);
            return pieceColor.equals(piece.getPieceColor());
        }
        return false;
    }

    public boolean canTakeDown(int index){
        JPanel panel = (JPanel)Game.BOARD_PANELS.getComponent(index);
        if(panel.getComponentCount() > 0){
            Piece piece = (Piece)panel.getComponent(0);
            return pieceColor.equals(piece.getPieceColor()) ? false : true;
        }
        return false;
    }

    public void takeDown(int newLocation){
        JPanel panelNew = (JPanel)Game.BOARD_PANELS.getComponent(newLocation);
        Game.addDeadPiece(panelNew);   
        panelNew.remove(0);     
        panelNew.add(this);
        resetHighlight();
    }

    public int isDirectionBlocked(int  x, int directionX, int directionY){
        int testIndex = getPieceIndex() + 1;
        int index = testIndex%8 == 0 ? 8 : testIndex%8;
        boolean test;
        for(int i = 1; i <= x; i++){
            testIndex = testIndex + (8 * directionY) + directionX;
            if(testIndex <= 64 && testIndex >= 1){
                if(x == 1){
                    test = directionX == 1 ? index < 8 : directionX == -1 ? index > 1 : true;
                }else{
                    test = directionX == 1 ? i <= x-index : directionX == -1 ? index != i : true;
                }
                if(test){
                    if(isBlocked(testIndex - 1)){
                        return i - 1;
                    }else if(canTakeDown(testIndex - 1)){
                        return i;
                    }
                } else{
                    return i - 1;
                }
            } else{
                return i - 1;
            }
        }
        return x;
    }
}