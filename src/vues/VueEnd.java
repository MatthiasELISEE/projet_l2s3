package vues;

import java.io.DataInputStream;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.InputStream;
import javax.swing.JPanel;
import java.io.IOException;
import java.awt.Image;
import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import modele.*;
import main.*;

import java.io.*;

public class VueEnd extends JPanel implements Observer {
	
	private Image img;
	private Modele modele;

	
	private final static int TAILLE = 50;

	/** Constructeur. */
	public VueEnd(Modele modele) {
		this.modele = modele;
		/** On enregistre la vue [this] en tant qu'observateur de [modele]. */
		modele.addObserver(this);
		
		Dimension dim = new Dimension(TAILLE * Modele.LARGEUR, TAILLE * Modele.HAUTEUR);
		this.setPreferredSize(dim);
	}

	
	public void update() {
		this.paint(this.getGraphics());
	}

	// Lire un son

	public class Sound {
		private AudioFormat format;
		private byte[] samples;

		/**
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
		// Effaéage du précédent affichage par l'appel de la classe mère
		super.paint(g);

		int nombredArtefact = 0;

		// Compter le nombre d'Artefacts
		for (int i = 1; i <= Modele.LARGEUR; i++) {
			for (int j = 1; j <= Modele.HAUTEUR; j++) {
				if (modele.getCellule(i - 1, j - 1).getArtefact() != null) {
					nombredArtefact++;

				}
				//Si la case départ est sous l'eau: Perdu
				if(modele.getCellule(0, 0).estSousLEau()) {
					try {
						img = ImageIO.read(new File("end.jpg"));
					} catch (IOException exc) {
						exc.printStackTrace();
					}
					g.drawImage(img, 0, 0, TAILLE * 4, TAILLE * 3, this);
					g.drawString("Case départ coulée", 10, 175);
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
				
				//Si la case oé est le joueur s'inonde: Perdu
				if (modele.getCellule(i - 1, j - 1).estSousLEau() == true && modele.getCellule(i - 1, j - 1).Yatiljoueur()) {
					try {
						img = ImageIO.read(new File("end.jpg"));
					} catch (IOException exc) {
						exc.printStackTrace();
					}
					g.drawImage(img, 0, 0, TAILLE * 4, TAILLE * 3, this);
					g.drawString("La case s'est inondée", 10, 175);
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
			}
		}

		// Si on a gagné
		if (nombredArtefact == 3 && modele.getCellule(0, 0).getJoueurs().size() == modele.joueurs().size()) {
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
