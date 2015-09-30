package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStream;


/**
 * Created by Anton on 28/09/2015.
 */
public class GamePanel extends JPanel{

    protected static JButton table[];
    protected static String symbol;
    protected static String[] tabuleiro;
    public static String server;
    public static OutputStream out;


    public GamePanel(String sv)
    {
        server = sv;
        tabuleiro = new String[9];
        for (int i=0; i<9; i++)
            tabuleiro[i] = "";

        setLayout(new GridLayout(3,3)); //Define a distribuiçao dos table, modo Grid, 3x3
        table = new JButton[9]; //Os table do jogo
        for(int i = 0; i < 9; i++)
        {
            table[i] = new JButton(""); //Cria cada botão desativado
            table[i].setEnabled(false);
        }

        for(int i = 0; i < 9; i++)
        {
            table[i].setFont(new Font("Calibri", Font.BOLD, 100));
            final int j = i;
            table[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    table[j].setText("X");
                    table[j].setEnabled(false);
                    sendMove(j);
                    System.out.println("ENVIEI JOGADA NA POSICAO "+String.valueOf(j));
                }
            });
            table[i].setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            add(table[i]);
        }
        setVisible(true);
    }

    public static void blockGame(boolean state){
        for(JButton each : table){
            if(each.getText().equals(""))
                each.setEnabled(state);
        }

    }

    public void sendMove(int position) {
        try {
            out.write((String.valueOf(position)).getBytes());
            out.flush();
            System.out.println("JOGADA ENVIADA");
            blockGame(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void close(){
        try {
            out.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void setSymbol(String mysymbol){
        symbol = mysymbol;
        if(symbol.equals("X"))
            blockGame(false);
    }

}
