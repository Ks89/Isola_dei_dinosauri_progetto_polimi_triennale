package isoladinosauri;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.*;

import client.ClientGui;
import client.Gui;
import client.LoginGui;
import client.gui.GuiLocale;
import client.gui.LoginGuiLocale;

/**
 * Classe richiamata dal main per simulare il gioco in Locale con grafica
 */
public class Main {

	private static final String HOME = "Home";
	private static final JFrame frame = new JFrame("Isola Dinosauri");
	private static Container contentPane = frame.getContentPane();
	private static CardLayout cardLayout = new CardLayout();

	public static void main (String[] args) {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setMinimumSize(new Dimension(300,250));
		frame.setResizable(false);

		JPanel homePanel = new JPanel(new GridLayout(4,1));
		JLabel titolo = new JLabel("Isola dei dinosauri");
		titolo.setHorizontalAlignment(SwingConstants.CENTER);
		homePanel.add(titolo);
		homePanel.add(createButton("Locale"));
		homePanel.add(createButton("Socket"));
		homePanel.add(createButton("Rmi"));

		JPanel localePanel = new JPanel(new GridLayout(3,1));
		JLabel locale = new JLabel("LOCALE");
		locale.setHorizontalAlignment(SwingConstants.CENTER);
		localePanel.add(locale);

		JButton guiLocale = new JButton("Avvia");
		guiLocale.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent event){
						GuiLocale gui = new GuiLocale();
						LoginGuiLocale loginGui = new LoginGuiLocale(gui);
						loginGui.aggiuntaGiocatore();
						frame.dispose();
					}
				}
		);
		localePanel.add(guiLocale);
		localePanel.add(createButton(HOME));

		
		JPanel socketPanel = new JPanel(new GridLayout(3,1));
		JLabel socket = new JLabel("SOCKET");
		socket.setHorizontalAlignment(SwingConstants.CENTER);
		socketPanel.add(socket);
		JButton guiSocket = new JButton("Avvia");
		guiSocket.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent event){
						ClientGui client = new ClientGui("localhost", 1234);
						try {
							client.inizializzaClient();
							Gui gui = new Gui(client);
							LoginGui loginGui = new LoginGui(gui);
							loginGui.aggiuntaGiocatore();
						} catch (UnknownHostException e) {
							JOptionPane.showMessageDialog(null, "UnknownHostException: Host sconosciuto!");
						} catch (IOException e) {
							JOptionPane.showMessageDialog(null, "IOException: Server non raggiungibile!");
						}					
						frame.dispose();
					}
				}
		);
		socketPanel.add(guiSocket);
		socketPanel.add(createButton(HOME));

		JPanel rmiPanel = new JPanel(new GridLayout(3,1));
		JLabel rmi = new JLabel("RMI");
		rmi.setHorizontalAlignment(SwingConstants.CENTER);
		rmiPanel.add(rmi);
		JButton guiRmi = new JButton("Avvia");
		rmiPanel.add(guiRmi);
		rmiPanel.add(createButton(HOME));

		contentPane.setLayout(cardLayout);
		contentPane.add(homePanel, HOME);
		contentPane.add(localePanel, "Locale");
		contentPane.add(socketPanel, "Socket");
		contentPane.add(rmiPanel, "Rmi");
		
		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * Metodo che esegue la creazione di un JButton assegnando come nome e azione la String action ricevuta in ingresso.
	 * @param action String per impostare il testo nel pulsante e l'actionCommand.
	 * @return Un JButton con testo e ActionCommand uguali.
	 */
	private static JButton createButton(String action) {
		JButton b = new JButton(action);
		b.setActionCommand(action);
		b.addActionListener(actionHandler);
		return b;
	}

	private static ActionListener actionHandler = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			cardLayout.show(contentPane, e.getActionCommand());
		}
	};
}
