import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;;

public class Game {
    public static JPanel BOARD_PANELS, DEAD_WHITE, DEAD_BLACK, SCORE, BLACK_TIME, WHITE_TIME;
    public static JLabel TIME_B, TIME_W;
    private static final int SCORE_WIDTH = 80, SCORE_HIGHT = 344, TIME_HIGHT = 69;

    public static void main(String[] args) throws IOException {
        BOARD_PANELS = new Chess();
        JFrame frame = new JFrame();
        DEAD_BLACK = new JPanel(new GridLayout(8,2));
        DEAD_BLACK.setPreferredSize(new Dimension(SCORE_WIDTH,SCORE_HIGHT-TIME_HIGHT));
        DEAD_WHITE = new JPanel(new GridLayout(8, 2));
        DEAD_WHITE.setPreferredSize(new Dimension(SCORE_WIDTH,SCORE_HIGHT-TIME_HIGHT));

        TIME_B = new JLabel("10:00");
        TIME_B.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 35));
        BLACK_TIME = new JPanel();
        BLACK_TIME.setSize(new Dimension(SCORE_WIDTH, TIME_HIGHT));
        BLACK_TIME.add(TIME_B);
        WHITE_TIME = new JPanel();
        WHITE_TIME.setSize(new Dimension(SCORE_WIDTH, TIME_HIGHT));
        TIME_W = new JLabel("10:00");
        TIME_W.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 35));
        WHITE_TIME.add(TIME_W);

        JPanel blackScore = new JPanel(new BorderLayout());
        blackScore.add(BLACK_TIME, BorderLayout.NORTH);
        blackScore.add(DEAD_BLACK, BorderLayout.SOUTH);
        JPanel whiteScore = new JPanel(new BorderLayout());
        whiteScore.add(WHITE_TIME, BorderLayout.NORTH);
        whiteScore.add(DEAD_WHITE, BorderLayout.SOUTH);

        SCORE = new JPanel(new GridLayout(2,1));
        SCORE.add(blackScore);
        SCORE.add(whiteScore);
        
        frame.setLayout(new BorderLayout());
        frame.add(SCORE, BorderLayout.EAST);
        frame.add(BOARD_PANELS, BorderLayout.CENTER);
        frame.setSize(688+SCORE_WIDTH,688);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Runnable timer = new Runnable(){
            int i = 0;
            public void run(){
                i++;
                if(Chess.turn.equals(Chess.BLACK_P)){
                    TIME_W.setBorder(BorderFactory.createLineBorder(Color.BLUE, 0));
                    TIME_B.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
                    if(i%20 == 0){
                        TIME_B.setText(timeLeft(TIME_B));
                    }
                }else{
                    TIME_B.setBorder(BorderFactory.createLineBorder(Color.BLUE, 0));
                    TIME_W.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
                    if(i%20 == 0){
                        TIME_W.setText(timeLeft(TIME_W));
                    }
                }
                
            }
        };
        ScheduledExecutorService exe = Executors.newScheduledThreadPool(1);
        exe.scheduleAtFixedRate(timer, 0, 50, TimeUnit.MILLISECONDS);
    }

    public static void addDeadPiece(JPanel panel){
        Piece piece = (Piece)panel.getComponent(0);
        ImageIcon icon = (ImageIcon)piece.getIcon();
        ImageIcon scaledIcon = new ImageIcon(icon.getImage().getScaledInstance(32, 32, Image.SCALE_DEFAULT));
        JLabel deadPiece = new JLabel(scaledIcon);
        if(piece.getPieceColor().equals(Chess.WHITE_P)){
            DEAD_WHITE.add(deadPiece);
            DEAD_WHITE.revalidate();
            DEAD_WHITE.repaint();
        }else{
            DEAD_BLACK.add(deadPiece);
            DEAD_BLACK.revalidate();
            DEAD_BLACK.repaint();
        }
    }

    private static String timeLeft(JLabel label){
        int minuts = Integer.parseInt(label.getText().toString().substring(0, 2));
        int seconds = Integer.parseInt(label.getText().toString().substring(3, 5));
        String m,s;
        if (minuts == 0 && seconds == 0){
            return "Over";
        }else if(seconds == 0){
            minuts--;
            seconds = 60;
        }
        seconds--;
        m = minuts < 10 ? "0"+Integer.toString(minuts) : Integer.toString(minuts);
        s = seconds < 10 ? "0"+Integer.toString(seconds) : Integer.toString(seconds);
        return m + ":" + s;
    }
}