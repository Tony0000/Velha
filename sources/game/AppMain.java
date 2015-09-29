package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AppMain extends JFrame implements ActionListener{

	private JButton server, client;

	/** Main window which you must choose between being a Server or a Client */
	public AppMain(){
		server = new JButton("Server");
		server.addActionListener(this);
		client = new JButton("Client");
		client.addActionListener(this);

		Container c = getContentPane();
		c.setLayout(new GridLayout(2,2));
		c.add(new JLabel("Choose Application"));
		c.add(new JLabel(""));
		c.add(server);
		c.add(client);



		pack();
		setLocation(400,300);
        setDefaultLookAndFeelDecorated(false);
        ImageIcon img = new ImageIcon("Resources/img/server_earth.png");
        setIconImage(img.getImage());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		dispose();
		/**Call Server Constructor*/
		if(e.getSource() == server){
			new Server().createServer();
		/**Call Client Constructor*/
		}else if(e.getSource() == client){
			new ClientLogin();
		}
	}

	public static void main(String[] args) {

		AppMain game = new AppMain();
	}
}
