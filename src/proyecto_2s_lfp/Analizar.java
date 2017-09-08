/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_2s_lfp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextArea;

/**
 *
 * @author Misa
 */
public class Analizar {

    private String[] palabrasReservadas = {"INICIO", "ENCABEZADO", "TITULO", "PAGINA", "CUERPO", "TEXTO", "PARRAFO",
        "CENTRO", "IZQUIERDA", "DERECHA", "NEGRITA", "NEGRITA", "SUBRAYADO", "COLOR", "TABLA", "FILA", "IMAGEN", "SALTO"};
    private int[] simbolosReservados = {123, 125, 34, 44, 91, 93, 58};
    //la comilla tiene un codigo asci de 34, todo el array esta en ascii 

    public ActionListener getAnalizar(JTextArea textoLFP, JTextArea textoHTML) {
        ActionListener devolver = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                analizar(textoLFP, textoHTML);
            }
        };

        return devolver;
    }

    private void analizar(JTextArea textoLFP, JTextArea textoHTML) {
        String[] entrada = limpiarEntrada(textoLFP);
        int a1, a2, existeError = 0, estadoActual = 0, objetosACerrar = 0;
        int[] estadoEsperado = {0}, estadosRecorridos = {0, 0, 0, 0, 0, 0, 0};
        String lexema = "";

        Lista tokens = new Lista();

        for (a1 = 0; a1 < entrada.length; a1++) {
            for (a2 = 0; a2 < entrada[a1].length(); a2++) {
                if (entrada[a1].length() == 1 && entrada[a1].charAt(a2) == 32) {
                    //esto pasa si la cadena o viene vacia, o solo trae un espacio
                } else {

                    estadoEsperado = getEstadoEsperado(estadoActual);
                }
            }
        }
    }
//3

    private int getEstado(char dato, int[] estadosRecorridos, int[] estadoEsperado) {
        int i, estado, auxiliarEstado = -1;
        for (i = 0; i < estadosRecorridos.length; i++) {
            if (estadosRecorridos[i] == 1) {
                auxiliarEstado = i;
            }
        }

        if (dato == 123) {
            estado = 0;
        } else if (dato == 34) {

            if (auxiliarEstado == 0) {
                estado = 1;
            } else if (auxiliarEstado == 1) {
                estado = 2;
            } else if (auxiliarEstado == 2) {
                estado = 3;
            } else if (auxiliarEstado == 4) {
                estado = 5;
            } else {
                estado = -1;
            }
        } else if (dato > 64 && dato < 91 || dato == 209
                || //lo anterior es para los caracteres alfabeticos en mayusculas
                dato > 96 && dato < 123 || dato == 241
                || //esto es para los caracteres en minusculas, ambos incluyen la ñ 
                dato == 193 /*Á*/ || dato == 201/*É*/
                || dato == 205 /*Í*/ || dato == 211/*Ó*/
                || dato == 218/*Ú*/
                || //letras mayusculas con tilde
                dato == 225 /*Á*/ || dato == 233/*É*/
                || dato == 237 /*Í*/ || dato == 243/*Ó*/
                || dato == 250/*Ú*/
                || //letras minusculas con tilde
                dato > 47 && dato < 58) {
            if (auxiliarEstado == 1) {
                estado = 2;
            } else if (auxiliarEstado == 2) {
                estado = 3;
            } else {
                estado = -1;
            }
        } else if (dato == 58 || dato == 125 || dato == 93) {
            if (auxiliarEstado == 3) {
                estado = 4;
            }
        } else if (dato == 123) {
            if(auxiliarEstado == 4){
                estado = 5;
            }
        } else if (dato ==91) {
            if(auxiliarEstado == 4){
                estado = 5;
            }
        } else if (dato == 44) {
            if(auxiliarEstado == 3){
                estado = 4;
            }else if(auxiliarEstado == 4){
                estado = 6;
            }
        }

    }

    private int[] getEstadoEsperado(int estadoActual) {
        switch (estadoActual) {
            case 0: {
                int[] devolver = {1};
                return devolver;
            }
            case 1: {
                int[] devolver = {2};
                return devolver;
            }
            case 2: {
                int[] devolver = {3, 4};
                return devolver;
            }
            case 3: {
                int[] devolver = {2, 4};
                return devolver;
            }
            case 4: {
                int[] devolver = {1, 5, 6};
                return devolver;
            }
            case 5: {
                int[] devolver = {0, 1, 2};
                return devolver;
            }
            case 6: {
                int[] devolver = {1, -2};//-2 por si se acaba todo, no hay utilizacion en este punto
                return devolver;
            }
            default: {
                int[] devolver = {-1};
                return devolver;
            }
        }
    }

    private int vinoLoEsperado(int estado, int[] esperando) {
        int i;
        for (i = 0; i < esperando.length; i++) {
            if (estado == esperando[i]) {
                return 1;
            }
        }
        return -1;
    }

    private int getTipoLexema(String dato, int existeError) {
        int i;
        if (existeError != 0) {
            return -1;
        } else {
            for (i = 0; i < palabrasReservadas.length; i++) {
                if (dato.equalsIgnoreCase(palabrasReservadas[i])) {
                    return 0;
                }
            }
        }
        return 2;
    }

    private String[] limpiarEntrada(JTextArea textoLFP) {
        String[] entrada = textoLFP.getText().split("\n");
        String[] devolver = new String[entrada.length];
        int i;
        for (i = 0; i < entrada.length; i++) {
            devolver[i] = entrada[i].replaceAll("\\t", " ").replaceAll(" +", " ");
        }
        return devolver;
    }
}
