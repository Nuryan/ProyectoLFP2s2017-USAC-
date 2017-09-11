/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_2s_lfp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Misa
 */
public class ManejoArchivo {

    private File archivoAbiertoLFP;
    private File archivoAbiertoHTML;

    public ManejoArchivo() {
        archivoAbiertoLFP = null;
        archivoAbiertoHTML = null;
    }

    public ActionListener getGuardarComo(JTextArea textoLFP, int LFPoHTML) {
        ActionListener devolver = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarComo(textoLFP, LFPoHTML);
            }
        };
        return devolver;
    }

    public ActionListener getGuardar(JTextArea textoLFP, int LFPoHTML) {
        ActionListener devolver = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardar(textoLFP, LFPoHTML);
            }
        };

        return devolver;
    }

    public ActionListener leerLFP(JTextArea textoLFP, JTextArea textoHTML) {
        ActionListener devolver = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirLFP(textoLFP, textoHTML);
            }
        };

        return devolver;
    }

    public ActionListener guardarAmbos(JTextArea textoLFP, JTextArea textoHTML) {
        ActionListener devolver = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardar(textoLFP, 0);
                guardar(textoHTML, 1);
            }
        };
        return devolver;
    }

    public ActionListener guardarComoAmbos(JTextArea textoLFP, JTextArea textoHTML) {
        ActionListener devolver = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarComo(textoLFP, 0);
                guardarComo(textoHTML, 1);
            }
        };
        return devolver;
    }

    public ActionListener guardarLexemasComo(Lista tokens) {
        ActionListener devolver = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarLexemas(tokens);
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

    private void abrirLFP(JTextArea textoLFP, JTextArea textoHTML) {
        if (archivoAbiertoLFP == null) {
            if (textoLFP.getText().length() < 2) {
                leerArchivo(textoLFP, textoHTML);
            } else {
                int continuar = JOptionPane.showConfirmDialog(null, "¿Guardar?", "¿Guardar?", 0, 0);
                if (continuar == 0) {
                    guardarComo(textoLFP, 0);
                    leerArchivo(textoLFP, textoHTML);
                } else {
                    leerArchivo(textoLFP, textoHTML);
                }
            }
        } else {
            int continuar = JOptionPane.showConfirmDialog(null, "¿Guardar?", "¿Guardar?", 0, 0);
            if (continuar == 0) {
                guardar(textoLFP, 0);
                leerArchivo(textoLFP, textoHTML);
            } else {
                leerArchivo(textoLFP, textoHTML);
            }
        }
    }

    private void leerArchivo(JTextArea textoLFP, JTextArea textoHTML) {
        try {
            int comienzo = 0;
            JFileChooser seleccionar = new JFileChooser();
            FileNameExtensionFilter filtro = new FileNameExtensionFilter(".LFP", "LFP");
            seleccionar.setFileFilter(filtro);
            seleccionar.showOpenDialog(seleccionar);
            File archivo = seleccionar.getSelectedFile();
            FileInputStream entrando = new FileInputStream(archivo);
            InputStreamReader entrada = new InputStreamReader(entrando);
            BufferedReader lector = new BufferedReader(entrada);
            setArchivoAbiertoLFP(archivo);
            String linea = "";

            while ((linea = lector.readLine()) != null) {
                if (comienzo == 0) {
                    textoLFP.setText(linea);
                    comienzo++;
                } else {
                    textoLFP.setText(textoLFP.getText() + "\n" + linea);
                }
            }

            lector.close();

            textoHTML.setText("");
            setArchivoAbiertoHTML(null);
        } catch (Exception e) {
            System.out.println("error en metodo leerArchivo, clase ManejoArhcivo  " + e.toString());
        }
    }

    private void guardarComo(JTextArea textoLFP, int LFPoHTML) {
        try {

            String terminacion = "";
            String buscando = "";
            switch (LFPoHTML) {
                case 0:
                    buscando = "LFP";
                    terminacion = ".LFP";
                    break;
                case 1:
                    buscando = "html";
                    terminacion = ".html";
                    break;
            }

            JFileChooser archivo = new JFileChooser();
            FileNameExtensionFilter filtro = new FileNameExtensionFilter(terminacion, buscando);
            archivo.setFileFilter(filtro);
            archivo.showSaveDialog(archivo);
            File guardar = archivo.getSelectedFile();
            BufferedWriter escritor;

            String[] lineas = textoLFP.getText().split("\n");

            if (guardar.exists()) {
                //pasos a dar si el fichero ya existe    

                int continuar = JOptionPane.showConfirmDialog(null, "El archivo ya existe, ¿Sobre-escribir?", "¿Sobre-escribir?", 0, 0);

                if (continuar == 0) {
                    escritor = new BufferedWriter(new FileWriter(guardar));
                    int auxiliarFor;
                    for (auxiliarFor = 0; auxiliarFor < lineas.length; auxiliarFor++) {
                        escritor.write(lineas[auxiliarFor] + "\r\n");
                    }
                } else {
                    return;
                }

            } else {
                escritor = new BufferedWriter(new FileWriter(guardar + terminacion));
                int auxiliarFor;
                for (auxiliarFor = 0; auxiliarFor < lineas.length; auxiliarFor++) {
                    escritor.write(lineas[auxiliarFor] + "\r\n");
                }
            }

            if (LFPoHTML == 0) {
                setArchivoAbiertoLFP(guardar);
            } else {
                setArchivoAbiertoHTML(guardar);
            }
            escritor.close();
        } catch (Exception e) {
            System.out.println("problemas en el metodo guardar como, clase ManejoArchivo " + e.toString());
        }
    }

    private void guardar(JTextArea textoLFP, int LFPoHTML) {
        File archivoGuardando = null;
        String terminacion = "";
        switch (LFPoHTML) {
            case 0:
                terminacion = ".LFP";
                archivoGuardando = archivoAbiertoLFP;
                break;
            case 1:
                terminacion = ".html";
                archivoGuardando = archivoAbiertoHTML;
                break;
        }

        if (archivoGuardando == null) {
            guardarComo(textoLFP, LFPoHTML);
        } else {
            try {
                String[] lineas = textoLFP.getText().split("\n");
                BufferedWriter escritor = new BufferedWriter(new FileWriter(archivoGuardando + terminacion));
                int auxiliarFor;
                for (auxiliarFor = 0; auxiliarFor < lineas.length; auxiliarFor++) {
                    escritor.write(lineas[auxiliarFor] + "\r\n");
                }
                escritor.close();

            } catch (Exception e) {
                System.out.println("problema en el metodo de guardar, clase ManejoArchivo" + e.toString());
            }
        }
    }

    /**
     * @param archivoAbiertoLFP the archivoAbiertoLFP to set
     */
    public void setArchivoAbiertoLFP(File archivoAbiertoLFP) {
        this.archivoAbiertoLFP = archivoAbiertoLFP;
    }

    /**
     * @param archivoAbiertoHTML the archivoAbiertoHTML to set
     */
    public void setArchivoAbiertoHTML(File archivoAbiertoHTML) {
        this.archivoAbiertoHTML = archivoAbiertoHTML;
    }
}
