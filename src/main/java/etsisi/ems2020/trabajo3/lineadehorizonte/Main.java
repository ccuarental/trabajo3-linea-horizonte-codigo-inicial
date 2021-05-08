package etsisi.ems2020.trabajo3.lineadehorizonte;

public class Main {

	public static void main(String[] args) {
        Ciudad ciudad = new Ciudad();
        ciudad.cargarEdificios("ciudad1.txt");
        
        LineaHorizonte linea = new LineaHorizonte();
        linea = ciudad.getLineaHorizonte();
        linea.guardaLineaHorizonte("salida.txt");
        
        System.out.println("-- Proceso finalizado Correctamente --");
	}
}