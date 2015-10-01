package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by Anton on 30/09/2015.
 */
public class ChallengedPlayer extends Thread {

    private Socket socket;
    private int portNumber = 2221;
    private BufferedReader in;
    private GamePanel game;
    private String server;

    public ChallengedPlayer(GamePanel game, String server){
        this.game = game;
        this.server = server;
    }



    public void run(){
        try {
            System.out.println("TENTANDO CONECTAR");
            socket = new Socket(server, portNumber);
            game.out = socket.getOutputStream();
            System.out.println("CONECTADO");
            game.setSymbol("O");
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String position;
            while ((position = in.readLine()) != null){
                System.out.println("RECEBI JOGADA NA POSIÇÃO "+Integer.getInteger(position));
                game.table[Integer.getInteger(position)].setText("X");
                game.blockGame(true);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
