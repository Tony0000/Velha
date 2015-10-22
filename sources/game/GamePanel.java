package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * Created by Anton on 28/09/2015.
 */
public class GamePanel extends JPanel{

    protected JButton table[];
    protected String symbol;
    protected String[] tabuleiro;
    public PrintWriter out;


    public GamePanel()
    {
        tabuleiro = new String[9];
        for (int i=0; i<9; i++)
            tabuleiro[i] = "";

        setLayout(new GridLayout(3,3)); //Define a distribuiçao dos table, modo Grid, 3x3
        table = new JButton[9]; //Os table do jogo
        for(int i = 0; i < 9; i++)
        {
            table[i] = new JButton(""); //Cria cada botão em branco
            table[i].setEnabled(false);
        }


        for(int i = 0; i < 9; i++)
        {
            table[i].setFont(new Font("Calibri", Font.BOLD, 100));
            final int j = i;
            table[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    sendMove(j);
                    System.out.println("ENVIEI JOGADA NA POSICAO "+String.valueOf(j));
                }
            });
            table[i].setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            add(table[i]);
        }
        setVisible(true);
    }

    public void blockGame(boolean state){
        for(int i = 0; i < 9; i++)
        {
            if(tabuleiro[i].equals(""))
                table[i].setEnabled(!state);
        }

    }

    public void sendMove(int position) {
        table[position].setText(symbol);
        tabuleiro[position] = symbol;
        table[position].setEnabled(false);
        out.write((String.valueOf(position)));
        out.flush();
        System.out.println("JOGADA ENVIADA");
        blockGame(true);
    }

    public void close(){
        out.close();
    }

    public void setSymbol(String mysymbol){
        symbol = mysymbol;
        if(symbol.equals("X"))
            blockGame(false);
    }

}
