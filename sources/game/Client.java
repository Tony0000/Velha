package game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;

// Class to manage Client chat Box.
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;


// Class to manage Client chat Box.
public class Client {
    public static String server;
    private GamePanel game;

    /** Chat client access */
    class ChatAccess extends Observable {
        private Socket socket;
        private OutputStream outputStream;

        @Override
        public void notifyObservers(Object arg) {
            super.setChanged();
            super.notifyObservers(arg);
        }

        /** Create socket, and receiving thread */
        public ChatAccess(String server, int port) throws IOException {
            socket = new Socket(server, port);
            outputStream = socket.getOutputStream();

            Thread receivingThread = new Thread() {
                @Override
                public void run() {
                    try {
                        BufferedReader reader = new BufferedReader(
                                new InputStreamReader(socket.getInputStream()));
                        String line;
                        while ((line = reader.readLine()) != null) {
                            if(line.contains("/duel")){
                                System.out.println("ESTOU SENDO DESAFIADO");
                                askPlayer();
                            }else{
                                notifyObservers(line);
                            }
                        }
                    } catch (IOException ex) {
                        notifyObservers(ex);
                    }
                }
            };
            receivingThread.start();
        }

        private void askPlayer(){
            Object[] selectionValues = { "Yes", "No" };

            /** box for client or server */
            Object selection = JOptionPane.showInputDialog(null, "Accept duel? ",
                    "MyChatApp", JOptionPane.QUESTION_MESSAGE, null,
                    selectionValues, "Duel");
            if (selection.equals("Yes")){
                new ChallengedPlayer(game, server).start();
            }
        }
        private final String CRLF = "\r\n"; // newline

        /** Send a line of text */
        public void send(String text) {
            try {
                System.out.println("ENVIANDO -"+text);
                outputStream.write((text + CRLF).getBytes());
                outputStream.flush();
            } catch (IOException ex) {
                notifyObservers(ex);
            }
        }

        /** Close the socket */
        public void close() {
            try {
                socket.close();
            } catch (IOException ex) {
                notifyObservers(ex);
            }
        }
    }

    /** Chat client UI */
    class ChatFrame extends JFrame implements Observer {

        private JTextArea textArea;
        private JTextField inputTextField;
        private JButton sendButton;
        private ChatAccess chatAccess;
        private JPanel main, left;
        private JSplitPane splitV;

        public ChatFrame(ChatAccess chatAccess) {
            this.chatAccess = chatAccess;
            chatAccess.addObserver(this);
            buildGUI();
        }

        /** Builds the user interface */
        private void buildGUI() {
            textArea = new JTextArea();
            textArea.setEditable(false);
            textArea.setLineWrap(true);
            textArea.setBackground(Color.BLACK);
            textArea.setForeground(Color.green);
            textArea.setFont(new Font("Times New Roman", Font.BOLD, 14));
            main = new JPanel();
            left = new JPanel();

            inputTextField = new JTextField();
            inputTextField.setFont(new Font("Calibri", Font.BOLD, 14));
            sendButton = new JButton("Send");
            left.setLayout(new BorderLayout());
            left.add(inputTextField, BorderLayout.NORTH);
            left.add(new JScrollPane(textArea), BorderLayout.CENTER);
            left.add(sendButton, BorderLayout.SOUTH);

            main.setLayout(new BorderLayout());
            getContentPane().add(main);
            //-----------------------------------------



            /** Action for the inputTextField*/
            final ActionListener sendListener = new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String str = inputTextField.getText();
                    if (str != null && str.trim().length() > 0){
                        /**Se /help for invocado, imprima a lista de comandos*/
                        if(str.equals("/help")){
                            textArea.append("@username /duel-> Desafiar alguem\n/quit -> sair do chat\n/about -> ??\n");
                            /**Do contrario /quit avisa aos outros users e encerra o aplicativo*/
                        }else if(str.contains("/duel")){
                            System.out.println("ESTOU DESAFIANDO ALGUEM");
                            new ChallengerPlayer(game).start();
                            chatAccess.send(str);
                            //textArea.append(str);
                        }else{
                            chatAccess.send(str);
                            if(str.equals("/quit"))
                                dispose();
                        }

                    }

                    inputTextField.selectAll();
                    inputTextField.requestFocus();
                    inputTextField.setText("");
                }
            };
            inputTextField.addActionListener(sendListener);
            sendButton.addActionListener(sendListener);

            this.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    chatAccess.close();
                }
            });

            /**Objeto manipulador da divisoria da JPanel*/
            Toolkit tk = Toolkit.getDefaultToolkit();
            Dimension d = tk.getScreenSize();
            int width = d.width;
            //-----------------------------------------

            splitV = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
            splitV.setDividerLocation(width/5);
            splitV.setOneTouchExpandable(true);
            splitV.setLeftComponent(left);
            splitV.setRightComponent(game = new GamePanel());

            main.add(splitV, BorderLayout.CENTER);
            main.setLayout(new BoxLayout(main, BoxLayout.X_AXIS));

        }

        /** Updates the UI depending on the Object argument */
        public void update(Observable o, Object arg) {
            final Object finalArg = arg;
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    textArea.append(finalArg.toString());
                    textArea.append("\n");
                }
            });
        }
    }

    public void createClient(String[] args) {
        server = args[0];
        int port = 2222;
        ChatAccess access = null;
        try {
            access = new ChatAccess(server, port);
        } catch (IOException ex) {
            System.out.println("Cannot connect to " + server + ":" + port);
            ex.printStackTrace();
            System.exit(0);
        }
        JFrame frame = new ChatFrame(access);
        frame.setTitle("MyChat - connected to " + server + ":" + port);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setDefaultLookAndFeelDecorated(false);
        ImageIcon img = new ImageIcon("Resources/img/icon.png");
        frame.setIconImage(img.getImage());
        frame.setSize(700,400);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}