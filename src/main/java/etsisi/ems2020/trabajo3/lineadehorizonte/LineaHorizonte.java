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
    	int s1y = -1, s2y = -1, prev = -1;
    	LineaHorizonte salida = new LineaHorizonte();
    	
    	Punto punto1 = new Punto();
    	Punto punto2 = new Punto();

    	imprimirLineas(s1, s2);

    	while (sNoVacias(s1, s2)) { 
        	Punto puntoAux = new Punto();
            punto1 = s1.getPunto(0);
            punto2 = s2.getPunto(0);
            int punto1x = punto1.getX();
            int punto2x = punto2.getX();
            int punto1y = punto1.getY();
            int punto2y = punto2.getY();
            
            if (puntoXEsMenor(punto1x, punto2x)) {
            	puntoAux.setX(punto1x);
                puntoAux.setY(Math.max(punto1y, s2y));

                prev = aniadirPuntoSalida(puntoAux, prev, salida);
                s1y = punto1y;
                s1.borrarPunto(0);
            } else if (puntoXEsMenor(punto2x, punto1x)) {
                puntoAux.setX(punto2x);
                puntoAux.setY(Math.max(punto2y, s1y));

                prev = aniadirPuntoSalida(puntoAux, prev, salida);
                s2y = punto2y;
                s2.borrarPunto(0);
            } else {
            			if (puntoYEsMayor(punto1y, punto2y) && puntosSonDistintos(punto1y, prev)) {
            				salida.addPunto(punto1);
            				prev = punto1y;
            			}
            		
            			if (puntoYEsMenorIgual(punto1y, punto2y) && puntosSonDistintos(punto2y, prev)) {
            				salida.addPunto(punto2);
            				prev = punto2y;
            			}
                
            			s1y = punto1y;
            			s2y = punto2y;
            			s1.borrarPunto(0);
            			s2.borrarPunto(0);
            		}
        }
        
        quedanElementos(s1, salida, prev);
		quedanElementos(s2, salida, prev);
        
		return salida;
    }


    
    public boolean sNoVacias(LineaHorizonte s1, LineaHorizonte s2) {
    	return (!s1.isEmpty() && !s2.isEmpty());
    }
    
    public boolean puntoXEsMenor(int puntoA, int puntoB) {
    	return puntoA < puntoB;
    }

    public boolean puntoYEsMayor(int puntoA, int puntoB) {
    	return puntoA > puntoB;
    }
    
    public boolean puntoYEsMenorIgual(int puntoA, int puntoB) {
    	return puntoA <= puntoB;
    }
    
    public boolean puntosSonDistintos(int punto, int prev) {
    	return punto != prev;
    }
    
    public int aniadirPuntoSalida(Punto puntoAux, int prev, LineaHorizonte salida) {
    	if (puntoAux.getY() != prev) {
    		salida.addPunto(puntoAux);
    		prev = puntoAux.getY();
    	}

    	return prev;
    }

    public void quedanElementos(LineaHorizonte s1, LineaHorizonte salida, int prev ) {
    	while (!s1.isEmpty()) {
    		Punto puntoAux = s1.getPunto(0);

    		if (puntoAux.getY() != prev) {
    			salida.addPunto(puntoAux);
    			prev = puntoAux.getY();
    		}
		
    		s1.borrarPunto(0);
    	}
    }

    public static void imprimirLineas (LineaHorizonte s1, LineaHorizonte s2) {
    	System.out.println("==== S1 ====");
    	s1.imprimir();
    	System.out.println("==== S2 ====");
    	s2.imprimir();
    	System.out.println("\n");
    }
}