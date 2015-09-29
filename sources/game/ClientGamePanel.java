package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Anton on 28/09/2015.
 */
public class ClientGamePanel extends JPanel{

    protected static JButton table[];
    protected static String symbol;
    protected static String[] tabuleiro;
    private String server;
    private Socket gamesocket;

    public ClientGamePanel(String sv)
    {
        server = sv;
        tabuleiro = new String[9];
        for (int i=0; i<9; i++)
            tabuleiro[i] = "";

        setLayout(new GridLayout(3,3)); //Define a distribuiçao dos table, modo Grid, 3x3
        table = new JButton[9]; //Os table do jogo
        for(int i = 0; i < 9; i++)
        {
            table[i] = new JButton(""); //Aloca cada um zerado
            table[i].setEnabled(true);
        }

        for(int i = 0; i < 9; i++)
        {
            table[i].setFont(new Font("Calibri", Font.BOLD, 100));
            final int j = i;
            table[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //Game.Jogada(j);
                    //unblockGame(false);
                }
            });
            table[i].setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            add(table[i]);
        }
        setVisible(true);
    }

    public void setGame(String oponent){
        try{
            gamesocket = new Socket(server, 2221);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
