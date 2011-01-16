package jeux;

import graphique.boutons.JBoutons;
import graphique.pseudo.JPseudo;
import graphique.table.JTable;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.net.InetAddress;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import reseau.Global;
import reseau.client.ConnexionAuServeur;
import reseau.serveur.ConnexionDuServeur;

/**
 * Lancement du jeux
 *@author Fabien
 *@version 0.1
 **/
public class Poker {
	/**
	 * Fenètre principale
	 */
	private JFrame frame;
	
	/**
	 * Fonction principale, elle lance la fenètre où on rentre le pseudo
	 * @param args Ici, on ne traite pas les arguments
	 */
	public static void main(String[] args) {
		Poker poker = new Poker();
		JPseudo jPseudo = new JPseudo(poker);
		jPseudo.setVisible();
	}
	
	/**
	 * Le pseudo est rendré, on doit donc lancer le client
	 * et le serveur si necessaire
	 */
	public void lancementPartie () {
		this.makeFrame();
		
		if (Global.isServeur) {
			ConnexionDuServeur serveur = new ConnexionDuServeur();
			serveur.sEnregister();
		}

		try {
			ConnexionAuServeur client = new ConnexionAuServeur(InetAddress.getLocalHost().getHostAddress());
			client.connexion();
		} catch (Exception e) {
			frame.setVisible(false);
			JOptionPane.showMessageDialog(new JFrame("Message"), "L'adresse IP du serveur est invalide\nou aucun serveur n'est lancé à cette adresse !");
			JPseudo jPseudo = new JPseudo(this);
			jPseudo.setVisible();
		}
	}
	
	/**
	 * Fabrique la fenètre principale
	 */
	private void makeFrame () {
		frame = new JFrame("Poker - " + Global.pseudo);
		frame.setResizable(false);
		
		JTable table = new JTable();
		JBoutons boutons = new JBoutons();
		Global.jTable = table;
		Global.boutons = boutons;
		
		frame.setBounds(5, 5, 1270, 750);
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		frame.setLayout(layout);

		constraints.gridx  = 0;
		constraints.gridy = 0;
		constraints.gridheight = 10;
		constraints.fill = GridBagConstraints.BOTH;
		layout.addLayoutComponent(table, constraints);
		frame.add(table);
		constraints.gridy = 10;
		constraints.gridheight = 1;
		layout.addLayoutComponent(boutons, constraints);
		frame.add(boutons);
		
		boutons.afficherBoutons(new int[] {0});
		
		frame.setVisible(true);
		
		AdapterFermeture fermeture = new AdapterFermeture();
		frame.addWindowListener(fermeture);
	}
}

