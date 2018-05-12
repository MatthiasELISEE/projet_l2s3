package vues;

import java.io.DataInputStream;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.io.InputStream;
import javax.swing.JPanel;
import java.io.IOException;
import java.awt.Image;
import java.io.File;
import javax.swing.JFrame;
import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import modele.*;
import main.*;

import java.io.*;
import javax.sound.sampled.*;

public class VueEnd extends JPanel implements Observer {
	/** On maintient une référence vers le modèle. */
	private Image img;
	private Modele modele;
	private JFrame frame;
	private AudioFormat format;
	private byte[] samples;
	/** Définition d'une taille (en pixels) pour l'affichage des cellules. */
	private final static int TAILLE = 50;
	private VueItems joueurFrame;

	/** Constructeur. */
	public VueEnd(Modele modele) {
		this.modele = modele;
		/** On enregistre la vue [this] en tant qu'observateur de [modele]. */
		modele.addObserver(this);
		/**
		 * Définition et application d'une taille fixe pour cette zone de l'interface,
		 * calculée en fonction du nombre de cellules et de la taille d'affichage.
		 */
		Dimension dim = new Dimension(TAILLE * Modele.LARGEUR, TAILLE * Modele.HAUTEUR);
		this.setPreferredSize(dim);
	}

	/**
	 * L'interface [Observer] demande de fournir une méthode [update], qui sera
	 * appelée lorsque la vue sera notifiée d'un changement dans le modèle. Ici on
	 * se content de réafficher toute la grille avec la méthode prédéfinie
	 * [repaint].
	 */
	public void update() {
		this.paint(this.getGraphics());
	}

	// Lire un son

	public class Sound {
		private AudioFormat format;
		private byte[] samples;

		/**
		 * 
		 * @param filename
		 *            le lien vers le fichier song (URL ou absolute path)
		 */
		public Sound(String filename) {
			try {
				AudioInputStream stream = AudioSystem.getAudioInputStream(new File(filename));
				format = stream.getFormat();
				samples = getSamples(stream);
			} catch (UnsupportedAudioFileException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public byte[] getSamples() {
			return samples;
		}

		public byte[] getSamples(AudioInputStream stream) {
			int length = (int) (stream.getFrameLength() * format.getFrameSize());
			byte[] samples = new byte[length];
			DataInputStream in = new DataInputStream(stream);
			try {
				in.readFully(samples);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return samples;
		}

		public void play(InputStream source) {
			int bufferSize = format.getFrameSize() * Math.round(format.getSampleRate() / 10);
			byte[] buffer = new byte[bufferSize];
			SourceDataLine line;
			try {
				DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
				line = (SourceDataLine) AudioSystem.getLine(info);
				line.open(format, bufferSize);
			} catch (LineUnavailableException e) {
				e.printStackTrace();
				return;
			}
			line.start();
			try {
				int numBytesRead = 0;
				while (numBytesRead != -1) {
					numBytesRead = source.read(buffer, 0, buffer.length);
					if (numBytesRead != -1)
						line.write(buffer, 0, numBytesRead);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			line.drain();
			line.close();
		}
	}

	public void paint(Graphics g) {
		super.paint(g);
		int compt = 0;
		int compt2 = 0;
		/** Pour chaque cellule... */
		for (int i = 1; i <= Modele.LARGEUR; i++) {
			for (int j = 1; j <= Modele.HAUTEUR; j++) {
				if (modele.getCellule(i - 1, j - 1).estSousLEau()) {
					compt++;
				}
				if (compt == 3) {

					try {
						img = ImageIO.read(new File("end.jpg"));
					} catch (IOException exc) {
						exc.printStackTrace();
					}
					g.drawImage(img, 0, 0, TAILLE * 4, TAILLE * 6, this);
					Sound player = new Sound("mario.WAV");
					InputStream stream = new ByteArrayInputStream(player.getSamples());
					player.play(stream);

					try {
						Thread.sleep(5000);
						System.exit(0);
					} catch (InterruptedException ex) {
						Thread.currentThread().interrupt();
					}

				}
				if (modele.getCellule(i - 1, j - 1).YaArtefact()) {
					compt2 = compt2 + 1;
				}
			}
		}

		if (compt2 == 3) {
			try {
				img = ImageIO.read(new File("win.jpg"));
			} catch (IOException exc) {
				exc.printStackTrace();
			}
			g.drawImage(img, 0, 0, TAILLE * 4, TAILLE * 6, this);
			Sound player = new Sound("win.WAV");
			InputStream stream = new ByteArrayInputStream(player.getSamples());
			player.play(stream);
			try {
				Thread.sleep(10000);
				System.exit(0);
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
		}

	}
}
