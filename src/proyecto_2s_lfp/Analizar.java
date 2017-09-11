/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_2s_lfp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Misa
 */
public class Analizar {

    private Lista listaGuardadaEnMemoria = null;
    
    private String[] palabrasReservadas = {"INICIO", "ENCABEZADO", "TITULOPAGINA", "CUERPO", "Fondo", "texto",
        "face", "parrafo", "párrafo", "centro", "izuiqerda", "derecha", "negrita", "cursiva", "subrayado", "titulo", "tamaño",
        "posicion", "posición", "color", "tabla", "imagen", "salto", "tabla", "fila", "imagen", "t1", "t2", "t3", "t4", "t5", "t6","Array"};
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
        int a1, a2, existeError = 0, tipoLexema = 0;
        String lexema = "";
        Lista tokens = new Lista();
        Lista preLista = new Lista();

        for (a1 = 0; a1 < entrada.length; a1++) {
            for (a2 = 0; a2 < entrada[a1].length(); a2++) {

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
                    lexema = lexema + entrada[a1].charAt(a2);
                } else if ((int) entrada[a1].charAt(a2) == 123 //{
                        || (int) entrada[a1].charAt(a2) == 125//}
                        || (int) entrada[a1].charAt(a2) == 34//"
                        || (int) entrada[a1].charAt(a2) == 44//,
                        || (int) entrada[a1].charAt(a2) == 91//[
                        || (int) entrada[a1].charAt(a2) == 93//]
                        || (int) entrada[a1].charAt(a2) == 58) {//:
                    if (lexema.length() > 0) {
                        preLista.push(lexema, a1 + 1, a2 - lexema.length() + 1, getTipoLexema(lexema, existeError));
                    }
                    preLista.push("" + entrada[a1].charAt(a2), a1 + 1, a2 + 1, 1);
                    lexema = "";
                    existeError = 0;
                } else if ((int) entrada[a1].charAt(a2) == 32) {
                    if (lexema.length() > 0) {
                        preLista.push(lexema, a1 + 1, a2 - lexema.length() + 1, getTipoLexema(lexema, existeError));
                        lexema = "";
                        existeError = 0;
                    }
                } else {
                    lexema = lexema + entrada[a1].charAt(a2);
                    existeError++;
                }
            }

            //System.out.println(tokens.length());
        }
        tokens = reordenar(preLista);
        setListaGuardadaEnMemoria(tokens);
        int a;
//        for (a = 0; a < tokens.length(); a++) {
//            System.out.println(tokens.get(a).getDato() + "  y es  :" + tokens.get(a).getTipo());
//
//        }

        for (a = 0; a < tokens.length(); a++) {
            System.out.println(tokens.get(a).getDato() + "  y es  :" + tokens.get(a).getTipo());

        }

        System.out.println(preLista.length());

        textoHTML.setText(convertirAHTML(tokens));
    }

    public ActionListener guardarTokensComoHTML(){
        ActionListener devolver = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarLexemas(listaGuardadaEnMemoria);
            }
        };
        
        return devolver;
    }
    
    private void guardarLexemas(Lista tokens) {
        try {
            JFileChooser archivo = new JFileChooser();
            FileNameExtensionFilter filtro = new FileNameExtensionFilter(".html", "html");
            archivo.setFileFilter(filtro);
            archivo.showSaveDialog(archivo);
            File guardar = archivo.getSelectedFile();
            BufferedWriter escritor;
            String terminacion = ".html";

            if (guardar.exists()) {
                //pasos a dar si el fichero ya existe    

                int continuar = JOptionPane.showConfirmDialog(null, "El archivo ya existe, ¿Sobre-escribir?", "¿Sobre-escribir?", 0, 0);

                if (continuar == 0) {
                    escritor = new BufferedWriter(new FileWriter(guardar));
                    int auxiliarFor;
                    escritor.write("<html><head><title>cabeza</title></head><body><table>");
                    escritor.write("<tr>" + "\r\n");
                    escritor.write("<td>Token</td>" + "\r\n");
                    escritor.write("<td>Lexema</td>" + "\r\n");
                    escritor.write("<td>Linea</td>" + "\r\n");
                    escritor.write("<td>Columna</td>" + "\r\n");
                    escritor.write("</tr>" + "\r\n");
                    for (auxiliarFor = 0; auxiliarFor < tokens.length(); auxiliarFor++) {
                        escritor.write("<tr>" + "\r\n");
                        switch (tokens.get(auxiliarFor).getTipo()) {
                            case -1:
                                escritor.write("<td> Error </td>" + "\r\n");
                                break;
                            case 0:
                                escritor.write("<td> Palabra Reservada</td>" + "\r\n");
                                break;
                            case 1:
                                escritor.write("<td> Simbolo</td>" + "\r\n");
                                break;
                            case 2:
                                escritor.write("<td>Identificador</td>" + "\r\n");
                                break;
                        }

                        escritor.write("<td>" + tokens.get(auxiliarFor).getDato()+"</td>" + "\r\n");
                        escritor.write("<td>" + tokens.get(auxiliarFor).getLinea()+"</td>" + "\r\n");
                        escritor.write("<td>" + tokens.get(auxiliarFor).getPosicion()+"</td>" + "\r\n");
                        escritor.write("</tr>" + "\r\n");
                    }
                    escritor.write("</table></body></html>");
                    escritor.close();
                } else {
                    return;
                }

            } else {
                escritor = new BufferedWriter(new FileWriter(guardar + terminacion));
                int auxiliarFor;
                escritor.write("<html><head><title>cabeza</title></head><body><table>");
                    escritor.write("<tr>" + "\r\n");
                    escritor.write("<td>Token</td>" + "\r\n");
                    escritor.write("<td>Lexema</td>" + "\r\n");
                    escritor.write("<td>Linea</td>" + "\r\n");
                    escritor.write("<td>Columna</td>" + "\r\n");
                    escritor.write("</tr>" + "\r\n");
                    for (auxiliarFor = 0; auxiliarFor < tokens.length(); auxiliarFor++) {
                        escritor.write("<tr>" + "\r\n");
                        switch (tokens.get(auxiliarFor).getTipo()) {
                            case -1:
                                escritor.write("<td> Error </td>" + "\r\n");
                                break;
                            case 0:
                                escritor.write("<td> Palabra Reservada</td>" + "\r\n");
                                break;
                            case 1:
                                escritor.write("<td> Simbolo</td>" + "\r\n");
                                break;
                            case 2:
                                escritor.write("<td>Identificador</td>" + "\r\n");
                                break;
                        }

                        escritor.write("<td>" + tokens.get(auxiliarFor).getDato()+"</td>" + "\r\n");
                        escritor.write("<td>" + tokens.get(auxiliarFor).getLinea()+"</td>" + "\r\n");
                        escritor.write("<td>" + tokens.get(auxiliarFor).getPosicion()+"</td>" + "\r\n");
                        escritor.write("</tr>" + "\r\n");
                    }
                    escritor.write("</table></body></html>");
                    escritor.close();
            }
        } catch (Exception e) {
            System.out.println("Problemas en el metodo guardar lexema, clase manejo archivos " + e.toString());
        }
    }
    
    private String convertirAHTML(Lista tokens) {
        int i, seRequiereParametro = 0;
        String aEscribir = "", elQueRequiereParametro = "";
        ArrayList<String> etiquetasCerradas = new ArrayList();
        //ArrayList<Integer> prioridad = new ArrayList();
        for (i = 0; i < tokens.length(); i++) {
            if (tokens.get(i).getTipo() == 0) {
                if (seRequiereParametro == 1) {
                    seRequiereParametro = 0;
                }
                if (tokens.get(i).getDato().equalsIgnoreCase("Inicio")) {
                    aEscribir = aEscribir + "<html> \n";
                    etiquetasCerradas.add("</html>");
                } else if (tokens.get(i).getDato().equalsIgnoreCase("titulo")) {
                    seRequiereParametro = 1;
                    elQueRequiereParametro = "titulo";
                } else if (tokens.get(i).getDato().equalsIgnoreCase("pagina")) {
                    aEscribir = aEscribir + "<title> \n";
                    etiquetasCerradas.add("</title>");
                } else if (tokens.get(i).getDato().equalsIgnoreCase("tituloPagina")) {
                    aEscribir = aEscribir + "<title> \n";
                    etiquetasCerradas.add("</title>");
                } else if (tokens.get(i).getDato().equalsIgnoreCase("encabezado")) {
                    aEscribir = aEscribir + " <head>\n";
                    etiquetasCerradas.add("</head>");
                } else if (tokens.get(i).getDato().equalsIgnoreCase("Cuerpo")) {
                    aEscribir = aEscribir + " <body>\n";
                    etiquetasCerradas.add("</body>");
                } else if (tokens.get(i).getDato().equalsIgnoreCase("texto")) {

                } else if (tokens.get(i).getDato().equalsIgnoreCase("face")) {
                    aEscribir = aEscribir + " <font face = " + String.valueOf((char) 34);
                    etiquetasCerradas.add("</font face>");
                    seRequiereParametro = 1;
                } else if (tokens.get(i).getDato().equalsIgnoreCase("negrita")) {
                    seRequiereParametro = 1;
                    aEscribir = aEscribir + " <b>\n";
                    etiquetasCerradas.add("</b>");
                } else if (tokens.get(i).getDato().equalsIgnoreCase("cursiva")) {
                    seRequiereParametro = 1;
                    aEscribir = aEscribir + " <i>\n";
                    etiquetasCerradas.add("</i>");
                } else if (tokens.get(i).getDato().equalsIgnoreCase("subrayado")) {
                    seRequiereParametro = 1;
                    aEscribir = aEscribir + " <u>\n";
                    etiquetasCerradas.add("</u>");
                } else if (tokens.get(i).getDato().equalsIgnoreCase("centro")) {
                    aEscribir = aEscribir + " <center>\n";
                    etiquetasCerradas.add("</center>");
                } else if (tokens.get(i).getDato().equalsIgnoreCase("izquierda")) {
                    aEscribir = aEscribir + " <p align = left>\n";
                    etiquetasCerradas.add("</p>");
                } else if (tokens.get(i).getDato().equalsIgnoreCase("derecha")) {
                    aEscribir = aEscribir + " <p align = right>\n";
                    etiquetasCerradas.add("</p>");
                } else if (tokens.get(i).getDato().equalsIgnoreCase("parrafo")) {
                    aEscribir = aEscribir + " <p>\n";
                    etiquetasCerradas.add("</p>");
                } else if (tokens.get(i).getDato().equalsIgnoreCase("párrafo")) {
                    aEscribir = aEscribir + " <p>\n";
                    etiquetasCerradas.add("</p>");
                } else if (tokens.get(i).getDato().equalsIgnoreCase("Color")) {
                    seRequiereParametro = 1;
                    aEscribir = aEscribir + " <p style = " + String.valueOf((char) 34) + "color:";
                    etiquetasCerradas.add("</p>");
                    elQueRequiereParametro = "color";
                } else if (tokens.get(i).getDato().equalsIgnoreCase("Salto")) {
                    aEscribir = aEscribir + " <br/>\n";
                } else if (tokens.get(i).getDato().equalsIgnoreCase("Tabla")) {
                    aEscribir = aEscribir + " <table>\n";
                    etiquetasCerradas.add("</table>");

                } else if (tokens.get(i).getDato().equalsIgnoreCase("t1")) {
                    aEscribir = aEscribir + " <h1>\n";
                    etiquetasCerradas.add("</h1>");
                } else if (tokens.get(i).getDato().equalsIgnoreCase("t2")) {
                    aEscribir = aEscribir + " <h2>\n";
                    etiquetasCerradas.add("</h2>");
                } else if (tokens.get(i).getDato().equalsIgnoreCase("t3")) {
                    aEscribir = aEscribir + " <h3>\n";
                    etiquetasCerradas.add("</h3>");
                } else if (tokens.get(i).getDato().equalsIgnoreCase("t4")) {
                    aEscribir = aEscribir + " <h4>\n";
                    etiquetasCerradas.add("</h4>");
                } else if (tokens.get(i).getDato().equalsIgnoreCase("t5")) {
                    aEscribir = aEscribir + " <h5>\n";
                    etiquetasCerradas.add("</h5>");
                } else if (tokens.get(i).getDato().equalsIgnoreCase("t6")) {
                    aEscribir = aEscribir + " <h6>\n";
                    etiquetasCerradas.add("</h6>");
//                } else if (tokens.get(i).getDato().equalsIgnoreCase("")) {
//                    aEscribir = aEscribir + " <>\n";
//                    etiquetasCerradas.add("</>");
//                } else if (tokens.get(i).getDato().equalsIgnoreCase("")) {
//                    aEscribir = aEscribir + " <>\n";
//                    etiquetasCerradas.add("</>");
//                } else if (tokens.get(i).getDato().equalsIgnoreCase("")) {
//                    aEscribir = aEscribir + " <>\n";
//                    etiquetasCerradas.add("</>");
//                } else if (tokens.get(i).getDato().equalsIgnoreCase("")) {
//                    aEscribir = aEscribir + " <>\n";
//                    etiquetasCerradas.add("</>");
//                } else if (tokens.get(i).getDato().equalsIgnoreCase("")) {
//                    aEscribir = aEscribir + " <>\n";
//                    etiquetasCerradas.add("</>");
//                } else if (tokens.get(i).getDato().equalsIgnoreCase("")) {
//                    aEscribir = aEscribir + " <>\n";
//                    etiquetasCerradas.add("</>");
//                } else if (tokens.get(i).getDato().equalsIgnoreCase("")) {
//                    aEscribir = aEscribir + " <>\n";
//                    etiquetasCerradas.add("</>");
//                } else if (tokens.get(i).getDato().equalsIgnoreCase("")) {
//                    aEscribir = aEscribir + " <>\n";
//                    etiquetasCerradas.add("</>");
//                } else if (tokens.get(i).getDato().equalsIgnoreCase("")) {
//                    aEscribir = aEscribir + " <>\n";
//                    etiquetasCerradas.add("</>");
//                } else if (tokens.get(i).getDato().equalsIgnoreCase("")) {
//                    aEscribir = aEscribir + " <>\n";
//                    etiquetasCerradas.add("</>");
//                } else if (tokens.get(i).getDato().equalsIgnoreCase("")) {
//                    aEscribir = aEscribir + " <>\n";
//                    etiquetasCerradas.add("</>");
                }
            } else if (tokens.get(i).getTipo() == 2) {
                if (seRequiereParametro == 0) {
                    aEscribir = aEscribir + tokens.get(i).getDato() + " \n";
                } else if (elQueRequiereParametro.equalsIgnoreCase("titulo")) {
                    seRequiereParametro = 0;
                    if (tokens.get(i).getDato().equalsIgnoreCase("t1")) {
                        aEscribir = aEscribir + "h1>";
                        etiquetasCerradas.add("</h1>");
                    } else if (tokens.get(i).getDato().equalsIgnoreCase("t2")) {
                        aEscribir = aEscribir + "h2>";
                        etiquetasCerradas.add("</h2>");
                    } else if (tokens.get(i).getDato().equalsIgnoreCase("t3")) {
                        aEscribir = aEscribir + "h3>";
                        etiquetasCerradas.add("</h3>");
                    } else if (tokens.get(i).getDato().equalsIgnoreCase("t4")) {
                        aEscribir = aEscribir + "h4>";
                        etiquetasCerradas.add("</h4>");
                    } else if (tokens.get(i).getDato().equalsIgnoreCase("t5")) {
                        aEscribir = aEscribir + "h5>";
                        etiquetasCerradas.add("</h5>");
                    } else if (tokens.get(i).getDato().equalsIgnoreCase("t6")) {
                        aEscribir = aEscribir + "h6>";
                        etiquetasCerradas.add("</h6>");
                    }
                } else if (elQueRequiereParametro.equalsIgnoreCase("color")) {
                    aEscribir = aEscribir + tokens.get(i).getDato() + ";" + String.valueOf((char)34) + "> \n";
                    seRequiereParametro = 0;
                } else {
                    seRequiereParametro = 0;
                    aEscribir = aEscribir + tokens.get(i).getDato() + String.valueOf((char) 34) + "> \n";
                }
            } else if (tokens.get(i).getTipo() == 1) {

                if (i != 1) {
                    if (tokens.get(i).getDato().equalsIgnoreCase(String.valueOf((char) 125))) {
                        aEscribir = aEscribir + etiquetasCerradas.get(etiquetasCerradas.size() - 1);
                        etiquetasCerradas.remove(etiquetasCerradas.size() - 1);
                    }
                } else if (tokens.get(i).getDato().equalsIgnoreCase(String.valueOf((char) 125))) {
                    aEscribir = aEscribir + etiquetasCerradas.get(etiquetasCerradas.size());
                    etiquetasCerradas.remove(etiquetasCerradas.size());
                } else if (tokens.get(i).getDato().equalsIgnoreCase(String.valueOf((char) 123))) {
                    seRequiereParametro = 0;
                }

            }
        }

        if (etiquetasCerradas.size()
                != 0) {
            for (i = etiquetasCerradas.size() - 1; i >= 0; i--) {
                aEscribir = aEscribir + etiquetasCerradas.get(i);
            }
        }

        return aEscribir;

    }

    private Lista reordenar(Lista preLista) {
        //metodo para reord
        String lexemaAuxiliar = "";
        Lista devolver = new Lista();
        int i, insertandoLexema = 0, valorColumnaInicial = 0, valorLineaInicial = 0, valor = preLista.getCorrelativo(), numComillas = 0;
        for (i = 0; i < valor; i++) {
            if (preLista.get(i).getTipo() == 2) {
                if (insertandoLexema == 0) {
                    lexemaAuxiliar = preLista.get(i).getDato();
                    valorColumnaInicial = preLista.get(i).getPosicion();
                    valorLineaInicial = preLista.get(i).getLinea();
                    insertandoLexema++;
                } else {
                    lexemaAuxiliar = lexemaAuxiliar + " " + preLista.get(i).getDato();
                }
            } else {
                if (preLista.get(i).getDato().charAt(0) == 34) {
                    numComillas++;
                } else if (preLista.get(i).getDato().charAt(0) == 91 || preLista.get(i).getDato().charAt(0) == 123) {
                    numComillas = 0;
                }

                if (numComillas == 3) {
                    lexemaAuxiliar = lexemaAuxiliar + " " + preLista.get(i).getDato();
                } else if (insertandoLexema == 0) {
                    devolver.push(preLista.get(i));
                } else {
                    insertandoLexema = 0;
                    devolver.push(lexemaAuxiliar, valorLineaInicial, valorColumnaInicial, 2);
                    devolver.push(preLista.get(i));
                }

            }
        }
        setListaGuardadaEnMemoria(devolver);
        return devolver;
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

    /**
     * @return the listaGuardadaEnMemoria
     */
    public Lista getListaGuardadaEnMemoria() {
        return listaGuardadaEnMemoria;
    }

    /**
     * @param listaGuardadaEnMemoria the listaGuardadaEnMemoria to set
     */
    public void setListaGuardadaEnMemoria(Lista listaGuardadaEnMemoria) {
        this.listaGuardadaEnMemoria = listaGuardadaEnMemoria;
    }
}
