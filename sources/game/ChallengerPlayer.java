package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Anton on 30/09/2015.
 */
public class ChallengerPlayer extends Thread {
    private ServerSocket server;
    public static Socket conection;
    private int portNumber = 2221; /**Portnumber for this server and the timeout for closure*/
    private BufferedReader in;

    public void run(){

        try {
            System.out.println("ESPERANDO CONECTAREM");
            server = new ServerSocket(portNumber);
            conection = server.accept();
            System.out.println("ALGUEM ESTABELECEU CONEXAO");
            in = new BufferedReader(new InputStreamReader(conection.getInputStream()));
            GamePanel.out = conection.getOutputStream();
            GamePanel.setSymbol("X");
            String line;
            while ((line = in.readLine()) != null){
                System.out.println("RECEBI JOGADA NA POSIÇÃO "+Integer.getInteger(line));
                GamePanel.table[Integer.getInteger(line)].setText("O");
                GamePanel.blockGame(false);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
