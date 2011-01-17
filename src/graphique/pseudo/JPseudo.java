package graphique.pseudo;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import reseau.Global;

import jeux.Poker;

@SuppressWarnings("serial")
/**
 * Fenetre qui demande le pseudo
 *@author Fabien
 *@version 0.1
 **/
public class JPseudo extends JFrame implements ActionListener {
	// Label informant l'utilisateur
	private JLabel info = new JLabel("Quel pseudo choisissez vous ?");
	
	// Label où l'utilisateur rentre son pseudo
	private String texte = "Entrez votre pseudo ici.";
	private JTextField pseudo = new JTextField(texte);
	
	// Label où l'utilisateur rentre l'adresse IP du serveur
	private JLabel infoIP = new JLabel("Adresse IP du serveur : ");
	private JTextField ip = new JTextField("255.255.255.255");
	
	// Choisit si on est le dealer ou pas
	private JCheckBox dealer = new JCheckBox("Suis-je le dealer(Cochez pour oui) :", false);
	
	// Bouton de validation du pseudo
	private JButton btn_valider = new JButton("Valider");
	
	// Classe appelante
	private Poker poker;
	
	/**
	 * Affichage de la fenètre et vérification du pseudo
	 * @param poker Programme principal qui lance cette fenètre
	 **/
	public JPseudo (Poker poker) {
		this.poker = poker;
		this.setTitle("Choix du pseudo");
		this.setResizable(false);
		this.setBounds(100, 100, 400, 200);
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		this.setLayout(layout);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		pseudo.selectAll();
		
		// Ajout des composants de la fenètre
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 3;
		constraints.insets = new Insets(10, 10, 10, 10);
		layout.addLayoutComponent(info, constraints);
		constraints.gridx = 3;
		layout.addLayoutComponent(pseudo, constraints);
		constraints.gridx = 0;
		constraints.gridy = 1;
		layout.addLayoutComponent(infoIP, constraints);
		constraints.gridx = 3;
		layout.addLayoutComponent(ip, constraints);
		constraints.gridx = 2;
		constraints.gridy = 2;
		layout.addLayoutComponent(dealer, constraints);
		constraints.gridwidth = 2;
		constraints.gridy = 3;
		layout.addLayoutComponent(btn_valider, constraints);
		this.add(info);
		this.add(pseudo);
		this.add(infoIP);
		this.add(ip);
		this.add(dealer);
		this.add(btn_valider);
		btn_valider.addActionListener(this);
	}

	/**
	 * La fenètre passe visible
	 **/
	public void setVisible () {
		this.setVisible(true);
	}

	/**
	 * Actions a réaliser lorsque l'on clique sur le bouton valider
	 * @param e Action qui a réveillé le listener
	 **/
	public void actionPerformed(ActionEvent e) {
		String pseudo = this.pseudo.getText();
		if ((!pseudo.equalsIgnoreCase(texte))&&(!pseudo.equalsIgnoreCase(""))) {
			this.setVisible(false);
			if (ip.getText().equalsIgnoreCase("localhost") || ip.getText().equalsIgnoreCase("127.0.0.1")) {
				try {
					Global.IPServeur = InetAddress.getLocalHost().getHostAddress();
				} catch (UnknownHostException e1) {
					e1.printStackTrace();
				}
			} else {
				if (dealer.isSelected()) {
					try {
						Global.IPServeur = InetAddress.getLocalHost().getHostAddress();
					} catch (UnknownHostException e1) {
						e1.printStackTrace();
					}
				} else {
					Global.IPServeur = ip.getText();
				}
			}
			Global.isServeur = dealer.isSelected();
			Global.pseudo = pseudo;
			poker.lancementPartie();
		}
	}
}
