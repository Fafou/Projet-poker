package jeux;

import graphique.boutons.ActionsBoutons;
import graphique.boutons.PanelBoutons;
import graphique.carte.Carte;
import graphique.pseudo.JPseudo;
import graphique.table.JTable;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import calcul.calculMain;

import reseau.Global;

/**
 * Lancement du jeux
 *@author Fabien
 *@version 0.1
 **/
public class Poker {
	/**
	 * Fonction principale, elle lance la fenètre où on rentre le pseudo
	 * @param args Ici, on ne traite pas les arguments
	 */
	public static void main(String[] args) {
		/*
		Poker poker = new Poker();
		JPseudo jpseudo = new JPseudo(poker);
		jpseudo.setVisible();
		*/
		List<Carte> l= new ArrayList<Carte>();
		Carte c1= new Carte(1, 11);
		Carte c2= new Carte(1, 2);
		Carte c3= new Carte(1, 2);
		Carte c4= new Carte(1, 10);
		Carte c5= new Carte(1, 11);
		Carte c6= new Carte(1, 1);
		Carte c7= new Carte(1, 13);
		l.add(c1);
		l.add(c2);
		l.add(c3);
		l.add(c4);
		l.add(c5);
		l.add(c6);
		l.add(c7);
		System.out.println(calculMain.isCouleur(l));

		
	
		
	}

	/**
	 * Lancemant de la partie de poker avec le bon pseudo
	 * @param pseudo Nom de la personne jouant
	 */
	public void lancementPartie (String pseudo) {
		JFrame frame = new JFrame("Poker - " + pseudo);
		frame.setResizable(false);
		JTable table = Global.getJTable(); // créé dans la classe Global
		PanelBoutons boutons = new PanelBoutons();
		ActionsBoutons AcBtn = new ActionsBoutons(boutons);
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
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

