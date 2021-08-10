import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;
import java.io.*;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Chess extends JPanel{
    public static Border SELECTED = BorderFactory.createLineBorder(Color.RED, 2);
    public static Border UNSELECTED = null;
    public static final String WHITE_P = "White";
    public static final String BLACK_P = "Black";
    private int newLocation = -1, oldLocation = -1, highlighted = 0;
    public static String turn = BLACK_P;

    public Chess() throws IOException {
        initBoard();
        initPieces();
        this.setVisible(true);
    }

    private void initBoard() throws IOException{
        JPanel panel;        
        for(int i=0; i<64; i++){
          panel = new JPanel();
          panel.addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent e){
                addEvent(e);
            }
          });
          this.add(panel);
        }
        this.setSize(688,688);
        this.setLayout(new GridLayout(8,8));
        colorBoard();
    }

    private void addEvent(MouseEvent e){
        newLocation = getPanelIndex((JPanel) e.getComponent());
                JPanel oldPanel, newPanel = (JPanel)e.getComponent();
                Piece piece;
                if(newPanel.getComponentCount() > 0){
                    piece = (Piece)newPanel.getComponent(0);
                    if(piece.getPieceColor().equals(turn)){
                        if(highlighted == 0){
                            piece.highlightDirection(piece.getMoveDirections());
                            highlighted = 1;
                        }else{
                            oldPanel = (JPanel)this.getComponent(oldLocation);
                            highlighted = 0;
                            Piece.resetHighlight();
                            if(!newPanel.equals(oldPanel)){
                                piece.highlightDirection(piece.getMoveDirections());
                                highlighted = 1;
                            }
                        }
                    }else if(newPanel.getBorder() == SELECTED){
                        oldPanel = (JPanel)this.getComponent(oldLocation);
                        piece = (Piece) oldPanel.getComponent(0);
                        if(getPanelIndex(newPanel) != getPanelIndex(oldPanel) && piece.getPieceColor().equals(turn)){
                            piece.movePiece(newLocation);
                            turn = piece.getPieceColor().equals(BLACK_P) ? WHITE_P : BLACK_P;
                        }
                        Piece.resetHighlight();
                        highlighted = 0;
                    }
                    oldLocation = newLocation;
                }else if (newPanel.getBorder() == SELECTED){
                    JPanel pnl = (JPanel)this.getComponent(oldLocation); 
                    piece = (Piece)pnl.getComponent(0);
                    if(piece.getPieceColor().equals(turn)){
                        piece.movePiece(newLocation);
                        turn = piece.getPieceColor().equals(BLACK_P) ? WHITE_P : BLACK_P;
                    }
                    Piece.resetHighlight();
                    highlighted = 0;
                }else{
                    Piece.resetHighlight();
                    highlighted = 0;
                }
    }

    private void colorBoard(){
        Color color = Color.WHITE;

        for (int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                this.getComponent(i*8+j).setBackground(color);
                color = color.equals(Color.WHITE) ? Color.LIGHT_GRAY : Color.WHITE;
            }
            color = color.equals(Color.WHITE) ? Color.LIGHT_GRAY : Color.WHITE;
        }
    }

    private void initPieces(){
        JPanel panel;
        Piece[] piecesW ={new Rook(WHITE_P),new Knight(WHITE_P),new Bishop(WHITE_P), new Queen(WHITE_P),
            new King(WHITE_P), new Bishop(WHITE_P), new Knight(WHITE_P), new Rook(WHITE_P)
        }; 
        Piece[] piecesB ={new Rook(BLACK_P),new Knight(BLACK_P),new Bishop(BLACK_P), new Queen(BLACK_P),
            new King(BLACK_P), new Bishop(BLACK_P), new Knight(BLACK_P), new Rook(BLACK_P)
        }; 
        for(int i = 0; i < 16; i++){
            if(i < 8){
                panel = (JPanel)this.getComponent(i);
                panel.add(piecesB[i]);
                panel = (JPanel)this.getComponent(56 + i);
                panel.add(piecesW[i]);
            }else{
                panel = (JPanel)this.getComponent(i);
                panel.add(new Pawn(BLACK_P));
                panel = (JPanel)this.getComponent(40 + i);
                panel.add(new Pawn(WHITE_P));
            }
        }
        this.revalidate();
        this.repaint();
    }

    public int getPanelIndex(JPanel panel){
        JPanel pnl;
        for(int i = 0; i < this.getComponentCount(); i++){
            pnl = (JPanel)this.getComponent(i);
            if(pnl.equals(panel)){
                return i;
            }
        }
        return -1;
    }
}