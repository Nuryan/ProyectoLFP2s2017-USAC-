/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_2s_lfp;

/**
 *
 * @author Misa
 */
public class Lista {

    private Dato cabeza;
    private int correlativo;
    public Lista() {
        cabeza = null;
        correlativo = 0;
    }
/* 
    Tabla de tipos >>
    
    -1 >> error
    0  >> palabra reservada
    1  >> simbolo
    2  >> identificador
    3  >> valor
    */
    public void push(String dato, int linea, int posicion, int tipo) {
        Dato ingresando = new Dato(dato, linea, posicion, tipo, correlativo);
        if (cabeza == null) {
            cabeza = ingresando;
            cabeza.setAnterior(cabeza);
            cabeza.setSiguiente(null);
        } else {
            Dato auxiliar = cabeza;
            while (auxiliar.getSiguiente() != null) {
                auxiliar = auxiliar.getSiguiente();
            }
            auxiliar.setSiguiente(ingresando);
            ingresando.setAnterior(auxiliar);
        }
        correlativo++;
    }

    public Dato get(int posicionDato) {
        Dato auxiliar = cabeza;
        int i;
        for (i = 0; i < posicionDato; i++) {
            auxiliar = auxiliar.getSiguiente();
        }
        return auxiliar;
    }

    public int length() {
        int tamaño = 1;
        if (cabeza == null) {
            return 0;
        } else {
            Dato auxiliar = cabeza;
            while (auxiliar.getSiguiente() != null) {
                auxiliar = auxiliar.getSiguiente();
                tamaño++;
            }
            return tamaño;
        }
    }

}
