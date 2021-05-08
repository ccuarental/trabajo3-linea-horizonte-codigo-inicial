package etsisi.ems2020.trabajo3.lineadehorizonte;

class LineaHorizonteFussionCreator {
	private LineaHorizonte s1;
	private LineaHorizonte s2;
	
	private int s1y = -1, s2y = -1, prev = -1;
	
	private LineaHorizonte salida;
	
	private Punto punto1;
	private Punto punto2;
		
	LineaHorizonteFussionCreator(LineaHorizonte s1, LineaHorizonte s2) {
		this.s1 = s1;
		this.s2 = s2;
		this.salida = new LineaHorizonte();
		this.punto1 = new Punto();
		this.punto2 = new Punto();
	}
	
    LineaHorizonte create() {
    	imprimirLineas();

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

                prev = aniadirPuntoSalida(puntoAux);
                s1y = punto1y;
                s1.borrarPunto(0);
            } else if (puntoXEsMenor(punto2x, punto1x)) {
                puntoAux.setX(punto2x);
                puntoAux.setY(Math.max(punto2y, s1y));

                prev = aniadirPuntoSalida(puntoAux);
                s2y = punto2y;
                s2.borrarPunto(0);
            } else {
            	comparacionPuntos(punto1y, punto2y);
            			s1y = punto1y;
            			s2y = punto2y;
            			s1.borrarPunto(0);
            			s2.borrarPunto(0);
            		}
        }
        
        quedanElementos(s1);
		quedanElementos(s2);
        
		return salida;
    }

    public void comparacionPuntos(int puntoA, int puntoB) {
    	if (puntoYEsMayor(puntoA, puntoB) && puntosSonDistintos(puntoA, prev)) {
			salida.addPunto(punto1);
			prev = puntoA;
		}
	
		if (puntoYEsMenorIgual(puntoA, puntoB) && puntosSonDistintos(puntoB, prev)) {
			salida.addPunto(punto2);
			prev = puntoB;
		}
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
    
    public int aniadirPuntoSalida(Punto puntoAux) {
    	if (puntoAux.getY() != prev) {
    		salida.addPunto(puntoAux);
    		prev = puntoAux.getY();
    	}

    	return prev;
    }

    public void quedanElementos(LineaHorizonte s) {
    	while (!s.isEmpty()) {
    		Punto puntoAux = s.getPunto(0);

    		if (puntoAux.getY() != prev) {
    			salida.addPunto(puntoAux);
    			prev = puntoAux.getY();
    		}
		
    		s.borrarPunto(0);
    	}
    }
    
    public void imprimirLineas() {
    	System.out.println("==== S1 ====");
    	s1.imprimir();
    	System.out.println("==== S2 ====");
    	s2.imprimir();
    	System.out.println("\n");
    }
}