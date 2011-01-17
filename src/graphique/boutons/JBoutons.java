package graphique.boutons;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import reseau.Global;

/**
 * Panel qui affiche les boutons des actions possibles
 * @author Fabien
 * @version 0.1
 */
@SuppressWarnings("serial")
public class JBoutons extends JPanel implements ActionListener {
	/**
	 * Différant boutons que l'on peux ajouter
	 */
	private JButton rejoindre;
	private JButton seCoucher;
	private JButton suivre;
	private JButton relancer;
	private JButton fin;
	private JButton check;
	private JButton ouvrir;
	private JButton commencer;
	private JButton quitter;
	private JButton tapis;
	
	/**
	 * Champs pour afficher les valeurs du suivit et de la relance
	 */
	private JLabel etiSuivre;
	private JLabel valSuivre;
	private JLabel etiRelancer;
	private JLabel valRelancer;
	
	/**
	 * Layout qui gère l'affichage
	 */
	private GridBagLayout layout;
	private GridBagConstraints contraintes;
	
	/**
	 * Constructeur affiche juste le bouton Rejoindre partie et Quitter
	 */
	public JBoutons () {
		// Initialisation des boutons
		rejoindre = new JButton("Rejoindre");
		seCoucher = new JButton("Se coucher");
		suivre = new JButton("Suivre");
		relancer = new JButton("Relancer");
		fin = new JButton("Fin enchère");
		check = new JButton("Check");
		ouvrir = new JButton("Ouvrir");
		commencer = new JButton("Commencer");
		quitter = new JButton("Quitter");
		tapis = new JButton("Tapis");
		
		// Initialisation des champs
		etiSuivre = new JLabel("Suivre :");
		valSuivre = new JLabel("0");
		etiRelancer = new JLabel("Relance : ");
		valRelancer = new JLabel("0");
		
		
		// Initialisation du layout
		layout = new GridBagLayout();
		contraintes = new GridBagConstraints();
		contraintes.insets = new Insets(5, 5, 5, 5);
		this.setLayout(layout);
		
		// Ajout du listener sur les boutons
		rejoindre.addActionListener(this);
		seCoucher.addActionListener(this);
		suivre.addActionListener(this);
		relancer.addActionListener(this);
		fin.addActionListener(this);
		check.addActionListener(this);
		ouvrir.addActionListener(this);
		commencer.addActionListener(this);
		quitter.addActionListener(this);
		tapis.addActionListener(this);
		
		this.setPreferredSize(new Dimension(1000, 150));
		this.setVisible(true);
	}
	
	/**
	 * Fonction qui affiche les bontouns des actions possibles
	 * @param boutons Actions que le joueur peut réaliser
	 * 		0 -> rejoindre
	 * 		1 -> Se coucher
	 * 		2 -> Suivre
	 * 		3 -> Relancer
	 * 		4 -> Fin de l'enchère
	 * 		5 -> Parole
	 * 		6 -> Ouvrir
	 * 		8 -> lancer partie
	 * 		9 -> Tapis
	 */
	public void afficherBoutons (int[] boutons) {
		this.razAffichage();
		contraintes.gridwidth = GridBagConstraints.RELATIVE;
		for (int i = 0; i < boutons.length; i++) {
			switch (boutons[i]) {
			case 0:
				layout.addLayoutComponent(rejoindre, contraintes);
				this.add(rejoindre);
				break;
			case 1:
				layout.addLayoutComponent(seCoucher, contraintes);
				this.add(seCoucher);
				break;
			case 2:
				layout.addLayoutComponent(suivre, contraintes);
				this.add(suivre);
				break;
			case 3:
				layout.addLayoutComponent(relancer, contraintes);
				this.add(relancer);
				break;
			case 4:
				layout.addLayoutComponent(fin, contraintes);
				this.add(fin);
				break;
			case 5:
				layout.addLayoutComponent(check, contraintes);
				this.add(check);
				break;
			case 6:
				layout.addLayoutComponent(ouvrir, contraintes);
				this.add(ouvrir);
				break;
			case 8:
				layout.addLayoutComponent(commencer, contraintes);
				this.add(commencer);
				break;
			case 9:
				layout.addLayoutComponent(tapis, contraintes);
				this.add(tapis);
				break;
			}
		}
		contraintes.gridwidth = GridBagConstraints.REMAINDER;
		layout.addLayoutComponent(quitter, contraintes);
		this.add(quitter);

		contraintes.gridwidth = GridBagConstraints.RELATIVE;
		layout.addLayoutComponent(etiSuivre, contraintes);
		layout.addLayoutComponent(valSuivre, contraintes);
		layout.addLayoutComponent(etiRelancer, contraintes);
		contraintes.gridwidth = GridBagConstraints.REMAINDER;
		layout.addLayoutComponent(valRelancer, contraintes);

		this.add(etiSuivre);
		this.add(valSuivre);
		this.add(etiRelancer);
		this.add(valRelancer);
		
		this.validate();
	}
	
	/**
	 * Signale qu'on doit attendre que les autres joueurs jouent
	 * Le seul bouton affiché est quitter
	 */
	public void setQuitter () {
		this.razAffichage();
		contraintes.gridwidth = GridBagConstraints.REMAINDER;
		layout.addLayoutComponent(quitter, contraintes);
		contraintes.gridwidth = GridBagConstraints.RELATIVE;
		layout.addLayoutComponent(etiSuivre, contraintes);
		layout.addLayoutComponent(valSuivre, contraintes);
		layout.addLayoutComponent(etiRelancer, contraintes);
		contraintes.gridwidth = GridBagConstraints.REMAINDER;
		layout.addLayoutComponent(valRelancer, contraintes);

		this.add(quitter);
		this.add(etiSuivre);
		this.add(valSuivre);
		this.add(etiRelancer);
		this.add(valRelancer);
		
		this.validate();
	}
	
	/**
	 * Met à jour la valeur de la relance, et du montant necessaire pour suivre.
	 */
	public void majMise() {
		valSuivre.setText(String.valueOf(Global.valSuivre));
		valRelancer.setText(String.valueOf(Global.valRelancer));
	}
	
	/**
	 * Remet à zéro l'affichage
	 * Il supprime tous
	 */
	private void razAffichage () {
		this.removeAll();
		layout = new GridBagLayout();
		this.setLayout(layout);
		this.repaint();
	}

	/**
	 * Actions a réaliser lors de l'appuis sur un bouton
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == rejoindre) {
			try {
				int i = 0;
				boolean rejoind = Global.serveur.rejoindre(Global.UID, Global.pseudo, i);
				while ((!rejoind) && i < 10) {
					rejoind = Global.serveur.rejoindre(Global.UID, Global.pseudo, i);
					i++;
				}
				
				if (rejoind) {
					Global.isGaming = true;
				}
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		} else if (e.getSource() == seCoucher) {
			try {
				this.setQuitter();
				Global.serveur.setAction(Global.UID, 5, Global.UID);
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		} else if (e.getSource() == suivre) {
			try {
				this.setQuitter();
				Global.serveur.setAction(Global.UID, 2, Global.valSuivre);
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		} else if (e.getSource() == relancer) {
			try {
				this.setQuitter();
				Global.serveur.setAction(Global.UID, 3, Global.valRelancer);
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		} else if (e.getSource() == fin) {
			try {
				this.setQuitter();
				Global.serveur.setAction(Global.UID, 7, Global.UID);
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		} else if (e.getSource() == check) {
			try {
				this.setQuitter();
				Global.serveur.setAction(Global.UID, 1, Global.UID);
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		} else if (e.getSource() == ouvrir) {
			try {
				this.setQuitter();
				Global.serveur.setAction(Global.UID, 6, Global.valSuivre);
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		} else if (e.getSource() == commencer) {
			try {
				Global.serveur.start();
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		} else if (e.getSource() == quitter) {
			try {
				Global.serveur.quitter(Global.UID);
			} catch (RemoteException e1) {

			}
			System.exit(0);
		} else if (e.getSource() == tapis) {
			try {
				this.setQuitter();
				Global.serveur.setAction(Global.UID, 4, Global.banque);
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		}
	}
}
