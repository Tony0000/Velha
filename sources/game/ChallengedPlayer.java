package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Anton on 30/09/2015.
 */
public class ChallengedPlayer extends Thread {

    private Socket socket;
    private int portNumber = 2221;
    private static BufferedReader in;



    public void run(){
        try {
            System.out.println("TENTANDO CONECTAR");
            socket = new Socket("localhost", portNumber);
            GamePanel.out = socket.getOutputStream();
            System.out.println("CONECTADO");
            GamePanel.setSymbol("O");
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String position;
            while ((position = in.readLine()) != null){
                System.out.println("RECEBI JOGADA NA POSIÇÃO "+Integer.getInteger(position));
                GamePanel.table[Integer.getInteger(position)].setText("X");
                GamePanel.blockGame(true);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
