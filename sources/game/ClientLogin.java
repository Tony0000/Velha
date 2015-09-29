package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Antonio on 23/09/2015.
 */
public class ClientLogin extends JFrame implements ActionListener, KeyListener{

    private JTextField user, password;
    private JButton login, cancel;
    private ClientDB db = new ClientDB();

    /** Constructor of the login window for the client*/
    public ClientLogin(){
        super("TicTacToe Chat-Game");
        login = new JButton("Login");
        cancel = new JButton("Cancel");

        login.addActionListener(this);
        cancel.addActionListener(this);

        user = new JTextField();
        user.addKeyListener(this);
        user.setFont(new Font("Calibri", Font.PLAIN, 20));
        password = new JTextField();
        password.addKeyListener(this);
        password.setFont(new Font("Calibri", Font.PLAIN, 20));

        Container c = getContentPane();
        c.setLayout(new GridLayout(3, 2));
        c.add(new JLabel("User: "));
        c.add(user);
        c.add(new JLabel("Password: "));
        c.add(password);
        c.add(login);
        c.add(cancel);
        setLocation(400, 300);
        setSize(300, 150);
        ImageIcon img = new ImageIcon("img/server_earth.png");
        setIconImage(img.getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

            if(e.getSource() == login) {
                checkLogin();
            }else if(e.getSource() == cancel && hasFilledFields()){
                clearFields();

            }else if(e.getSource() == cancel && !hasFilledFields()){
                dispose();
            }
    }

    /** Check if the username and password fields are empty and try to login */
    private void checkLogin(){

        if(hasFilledFields()){
            if(db.tryLogin(user.getText(), password.getText())){
                dispose();
                String[] arguments = new String[] { "localhost", user.getText() };
                new Client().createClient(arguments);
                return;
            }
        }
        clearFields();

    }

    private boolean hasFilledFields(){
        if(!user.getText().equals("") && !password.getText().equals("")){
            return true;
        }else{
            return false;
        }
    }

    /** Clear the username and password fields */
    private void clearFields(){
        user.setText("");
        password.setText("");
        JOptionPane.showMessageDialog(null, "Tente novamente.");
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (e.getSource() == password) {
            if (key == KeyEvent.VK_ENTER) {
                checkLogin();
            }
        }else if(e.getSource() == user){
            if(key == KeyEvent.VK_ENTER){
                password.requestFocusInWindow();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
