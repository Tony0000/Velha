package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Anton on 30/09/2015.
 */
public class ChallengerPlayer extends Thread {
    private ServerSocket server;
    public Socket conection;
    private int portNumber = 2221; /**Portnumber for this server and the timeout for closure*/
    private BufferedReader in;
    private GamePanel game;

    public ChallengerPlayer(GamePanel game){
        this.game = game;
    }

    public void run(){

        try {
            System.out.println("ESPERANDO CONECTAREM");
            server = new ServerSocket(portNumber);
            conection = server.accept();
            System.out.println("ALGUEM ESTABELECEU CONEXAO");
            in = new BufferedReader(new InputStreamReader(conection.getInputStream()));
            game.out = conection.getOutputStream();
            game.setSymbol("X");
            game.blockGame(false);
            String line;
            while ((line = in.readLine()) != null){
                System.out.println("RECEBI JOGADA NA POSIÇÃO "+Integer.getInteger(line));
                game.table[Integer.getInteger(line)].setText("O");
                game.blockGame(false);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
