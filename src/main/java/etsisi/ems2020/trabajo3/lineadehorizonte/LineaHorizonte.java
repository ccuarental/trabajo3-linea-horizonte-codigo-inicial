package etsisi.ems2020.trabajo3.lineadehorizonte;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class LineaHorizonte {

	private ArrayList <Punto> lineaHorizonte ;

    public LineaHorizonte() {
    	lineaHorizonte = new ArrayList <Punto>();
    }
    
    public LineaHorizonte(int edificioParteIzquierda, int edificioParteDerecha, ArrayList<Edificio> edificios) {
    	lineaHorizonte = crearLineaHorizonte(edificioParteIzquierda, edificioParteDerecha, edificios).getLineaHorizonte();
    }

    public ArrayList<Punto> getLineaHorizonte() {
    	return this.lineaHorizonte;
    }

    public Punto getPunto(int posicion) {
    	return (Punto)this.lineaHorizonte.get(posicion);
    }

    public void addPunto(Punto punto) {
    	lineaHorizonte.add(punto);
    }    
    
    public void borrarPunto(int posicion) {
    	lineaHorizonte.remove(posicion);
    }

    public int size() {
    	return lineaHorizonte.size();
    }

    public boolean isEmpty() {
    	return lineaHorizonte.isEmpty();
    }

    public void guardaLineaHorizonte (String fichero) throws IOException {
    	try {
    		Punto punto = new Punto();
    		FileWriter fileWriter = new FileWriter(fichero);
    		PrintWriter out = new PrintWriter(fileWriter);

    		for(int i = 0; i < this.size(); i++) {
    			punto = getPunto(i);
    			out.print(punto.getX() + " " + punto.getY() + "\n");
    		}
    		
            out.close();
        } catch(FileNotFoundException e) {
        	System.out.println(e.getMessage());
        }
    }
    
    public void imprimir (){
    	for(int i = 0; i < lineaHorizonte.size(); i++ ) {
    		System.out.println(cadena(i));
    	}
    }
    
    public String cadena (int posicion) {
    	return lineaHorizonte.get(posicion).toString();
    }

    public LineaHorizonte crearLineaHorizonte(int edificioParteIzquierda, int edificioParteDerecha, ArrayList<Edificio> edificios) {
    	LineaHorizonte linea = new LineaHorizonte();

    	if (edificioParteIzquierda == edificioParteDerecha) {
    		crearLineaBase(edificioParteIzquierda, linea, edificios);
    	} else {
    		int medio = (edificioParteIzquierda + edificioParteDerecha) / 2;

    		LineaHorizonte s1 = this.crearLineaHorizonte(edificioParteIzquierda, medio, edificios);
    		LineaHorizonte s2 = this.crearLineaHorizonte(medio + 1, edificioParteDerecha, edificios);
    		linea = LineaHorizonteFussion(s1, s2);
 		}
 		return linea;
 	}

    public void crearLineaBase(int edificioParteIzquierda, LineaHorizonte linea, ArrayList<Edificio>edificios) {
    	Punto punto1 = new Punto();
    	Punto punto2 = new Punto();

    	Edificio edificio = edificios.get(edificioParteIzquierda);

    	punto1.setX(edificio.getXIzquierda());
    	punto1.setY(edificio.getY());
    	punto2.setX(edificio.getXDerecha());
    	punto2.setY(0);
    	linea.addPunto(punto1);
    	linea.addPunto(punto2);
    }
    
    
    public LineaHorizonte LineaHorizonteFussion(LineaHorizonte s1, LineaHorizonte s2) {
    	return new LineaHorizonteFussionCreator(s1, s2).create();
    }
}