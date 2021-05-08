package etsisi.ems2020.trabajo3.lineadehorizonte;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/*
 Clase fundamental.
 Sirve para hacer la lectura del fichero de entrada que contiene los datos de como
 están situados los edificios en el fichero de entrada. xIzquierda, xDerecha, h, siendo. Siendo
 xIzquierda la coordenada en X origen del edificio iésimo, xDerecha la coordenada final en X, y h la altura del edificio.
 
 */
public class Ciudad {

	private ArrayList<Edificio> ciudad;

	public Ciudad() {

		/*
		 * Generamos una ciudad de manera aleatoria para hacer pruebas.
		 */
		ciudad = new ArrayList<Edificio>();
		metodoRandom(5);

		ciudad = new ArrayList<Edificio>();
	}

	public Edificio getEdificio(int i) {
		return (Edificio) this.ciudad.get(i);
	}

	public void addEdificio(Edificio e) {
		ciudad.add(e);
	}

	public void removeEdificio(int i) {
		ciudad.remove(i);
	}

	public int size() {
		return ciudad.size();
	}

	public LineaHorizonte getLineaHorizonte() {
		// parteIzquierda y parteDerecha, representan los edificios de la izquierda y de la derecha.
		int parteIzquierda = 0;
		int parteDerecha = ciudad.size() - 1;
		return new LineaHorizonte(parteIzquierda, parteDerecha, ciudad);
	}

	/*
	 * Método que carga los edificios que me pasan en el archivo cuyo nombre se
	 * encuentra en "fichero". El formato del fichero nos lo ha dado el profesor en
	 * la clase del 9/3/2020, pocos días antes del estado de alarma.
	 */

	public void cargarEdificios(String fichero) {
		try {
			int xIzquierda, y, xDerecha;
			Scanner sr = new Scanner(new File(fichero));

			while (sr.hasNext()) {
				xIzquierda = sr.nextInt();
				xDerecha = sr.nextInt();
				y = sr.nextInt();
				this.addEdificio(new Edificio(xIzquierda, y, xDerecha));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	public void metodoRandom(int n) {
		int i = 0;
		int xIzquierda, y, xDerecha;
		for (i = 0; i < n; i++) {
			xIzquierda = (int) (Math.random() * 100);
			y = (int) (Math.random() * 100);
			xDerecha = (int) (xIzquierda + (Math.random() * 100));
			this.addEdificio(new Edificio(xIzquierda, y, xDerecha));
		}
	}
}
