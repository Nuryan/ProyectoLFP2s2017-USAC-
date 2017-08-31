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
        int a1, a2, estado = 0;
        String lexema = "";

        for (a1 = 0; a1 < entrada.length; a1++) {
            for (a2 = 0; a2 < entrada[a1].length(); a2++) {
                switch (entrada[a1].charAt(a2)) {
                    case 0:
                        break;
                    case 2:
                        if ((int) entrada[a1].charAt(a2) > 64 && (int) entrada[a1].charAt(a2) < 91 || (int) entrada[a1].charAt(a2) == 209
                                || //lo anterior es para los caracteres alfabeticos en mayusculas
                                (int) entrada[a1].charAt(a2) > 96 && (int) entrada[a1].charAt(a2) < 123 || (int) entrada[a1].charAt(a2) == 241
                                || //esto es para los caracteres en minusculas, ambos incluyen la ñ 
                                (int) entrada[a1].charAt(a2) == 193 /*Á*/ || (int) entrada[a1].charAt(a2) == 201/*É*/
                                || (int) entrada[a1].charAt(a2) == 205 /*Í*/ || (int) entrada[a1].charAt(a2) == 211/*Ó*/
                                || (int) entrada[a1].charAt(a2) == 218/*Ú*/
                                || //letras mayusculas con tilde
                                (int) entrada[a1].charAt(a2) == 225 /*Á*/ || (int) entrada[a1].charAt(a2) == 233/*É*/
                                || (int) entrada[a1].charAt(a2) == 237 /*Í*/ || (int) entrada[a1].charAt(a2) == 243/*Ó*/
                                || (int) entrada[a1].charAt(a2) == 250/*Ú*/
                                || //letras minusculas con tilde
                                (int) entrada[a1].charAt(a2) > 47 && (int) entrada[a1].charAt(a2) < 58) {
                            // numeros

                        }
                        break;
                }
            }
        }
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
