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
public class Dato {

    private Dato anterior;
    private Dato siguiente;
    private String dato;
    private int linea;
    private int posicion;

    public Dato(String dato, int linea, int posicion) {
        this.anterior = null;
        this.siguiente = null;
        this.dato = dato;
        this.linea = linea;
        this.posicion = posicion;
    }

    /**
     * @return the anterior
     */
    public Dato getAnterior() {
        return anterior;
    }

    /**
     * @param anterior the anterior to set
     */
    public void setAnterior(Dato anterior) {
        this.anterior = anterior;
    }

    /**
     * @return the siguiente
     */
    public Dato getSiguiente() {
        return siguiente;
    }

    /**
     * @param siguiente the siguiente to set
     */
    public void setSiguiente(Dato siguiente) {
        this.siguiente = siguiente;
    }

    /**
     * @return the dato
     */
    public String getDato() {
        return dato;
    }

    /**
     * @param dato the dato to set
     */
    public void setDato(String dato) {
        this.dato = dato;
    }

    /**
     * @return the linea
     */
    public int getLinea() {
        return linea;
    }

    /**
     * @param linea the linea to set
     */
    public void setLinea(int linea) {
        this.linea = linea;
    }

    /**
     * @return the posicion
     */
    public int getPosicion() {
        return posicion;
    }

    /**
     * @param posicion the posicion to set
     */
    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }
}
