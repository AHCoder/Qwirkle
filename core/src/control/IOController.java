package control;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import model.Qwirkle;

public class IOController {

	private GameController gCtrl;

	public IOController(GameController gCtrl) {
		this.gCtrl = gCtrl;
	}

	/**
	 * Lädt das aktuelle Qwirkleobjekt mit allen Abhängigkeiten aus der Datei
	 * qwirkle
	 * 
	 * @precondition Qwirkle muss zuvor mindestens einmal gespeichert werden
	 * @postcondition das zuletzt abgespeicherte Qwirkleobjekt wurde geladen
	 * @return Exception, falls das Laden nicht möglich war
	 * 
	 */
	public void load() throws IOException {
		try {
			ObjectInputStream stream = new ObjectInputStream(
					new FileInputStream("qwirkle"));
			gCtrl.setQwirkle((Qwirkle) stream.readObject());
			stream.close();
		} catch (ClassNotFoundException cnfex) {
			System.err
					.println("Die Klasse des geladenen Objekts konnte nicht gefunden werden.");
		}
	}

	/**
	 * Lädt das aktuelle Qwirkleobjekt mit allen Abhängigkeiten aus der Datei
	 * qwirkle und gibt dieses als Objekt zurück
	 * 
	 * @precondition Qwirkle muss zuvor mindestens einmal gespeichert werden
	 * @postcondition das zuletzt abgespeicherte Qwirkleobjekt wurde geladen und zurückgeben
	 * @return aktuelle Qwirkleobjekt oder Exception, falls das Laden nicht möglich war
	 * 
	 */
	public Qwirkle loadQwirkle() {
		try {
			ObjectInputStream stream = new ObjectInputStream(
					new FileInputStream("qwirkle"));
			Qwirkle qwirkle = (Qwirkle) stream.readObject();
			stream.close();
			return qwirkle;
		} catch (ClassNotFoundException cnfex) {
			System.err
					.println("Die Klasse des geladenen Objekts konnte nicht gefunden werden.");
			return null;
		} catch (IOException ioex) {
			System.err.println("Das Objekt konnte nicht geladen werden.");
			ioex.printStackTrace();
			return null;
		}
	}

	/**
	 * Speichert das aktuelle Qwirkleobjekt mit allen Abhängigkeiten aus der
	 * Datei qwirkle
	 * 
	 * @postcondition Qwirkle wurde mit allen Objekten in eine Datei "qwirkle"
	 *                abgespeichert
	 * @return Exception, falls das Speichern nicht möglich war
	 * 
	 */
	public void save() {
		try {
			ObjectOutputStream stream = new ObjectOutputStream(
					new FileOutputStream("qwirkle"));
			stream.writeObject(gCtrl.getQwirkle());
			stream.close();

		} catch (IOException ioex) {
			System.err
					.println("Fehler beim Schreiben des Objekts aufgetreten.");
			ioex.printStackTrace();
		}
	}
}
