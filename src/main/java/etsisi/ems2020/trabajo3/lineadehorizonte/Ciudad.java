package etsisi.ems2020.trabajo3.lineadehorizonte;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Ciudad {

	private ArrayList<Edificio> ciudad;

	public Ciudad() {
		ciudad = new ArrayList<Edificio>();
		int numeroEdificios = 5;
		metodoRandom(numeroEdificios);
		
		ciudad = new ArrayList<Edificio>(); // Si se borra falla JUnit
	}

	public Edificio getEdificio(int posicion) {
		return (Edificio) this.ciudad.get(posicion);
	}

	public void addEdificio(Edificio edificio) {
		ciudad.add(edificio);
	}

	public void removeEdificio(int posicion) {
		ciudad.remove(posicion);
	}

	public int size() {
		return ciudad.size();
	}

	public LineaHorizonte getLineaHorizonte() {
		int edificioParteIzquierda = 0;
		int edificioParteDerecha = ciudad.size() - 1;
		return new LineaHorizonte(edificioParteIzquierda, edificioParteDerecha, ciudad);
	}

	public void cargarEdificios(String fichero) {
		try {
			scannerEdificios(fichero);
			
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void scannerEdificios(String fichero) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File(fichero));
		int xIzquierda, y, xDerecha;
		
		while (scanner.hasNext()) {
			xIzquierda = scanner.nextInt();
			xDerecha = scanner.nextInt();
			y = scanner.nextInt();
			this.addEdificio(new Edificio(xIzquierda, y, xDerecha));
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