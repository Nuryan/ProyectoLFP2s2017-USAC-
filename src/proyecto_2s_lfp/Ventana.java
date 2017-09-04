/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_2s_lfp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author Misa
 */
public class Ventana {

    private JFrame ventana;
    private JPanel contenedor;

    private JScrollPane areaLFP;
    private JScrollPane areaHTML;
    private JTextArea textoLFP;
    private JTextArea textoHTML;
    
    private JMenuBar barrita;
    private JMenu menuArchivo;
    private JMenu menuAyuda;
    private JMenuItem nuevoMenu;
    private JMenuItem abrirMenu;
    private JMenuItem guardarMenuLFP;
    private JMenuItem guardarMenuHTML;
    private JMenuItem guardarMenuAmbos;
    private JMenuItem guardarComoMenuLFP;
    private JMenuItem guardarComoMenuHTML;
    private JMenuItem guardarComoMenuAmbos;
    private JMenuItem traducirMenu;
    private JMenuItem guardarLexemasMenu;
    private JMenuItem salirMenu;
    private JMenuItem manualUsuarioMenu;
    private JMenuItem manualTecnicoMenu;
    private JLabel indicadorLFP;
    private JLabel indicadorHTML;
    
    private ManejoArchivo manejoArchivos;
    
    private Analizar analizar;
    public Ventana() {
        iniciarElementos();

        posicionarElementos();
    }

    private void iniciarElementos() {
        ventana = new JFrame("¡Proyecto Lenguajes 2S 2017!");
        contenedor = new JPanel();
        barrita = new JMenuBar();
        menuArchivo = new JMenu("Archivo");
        menuAyuda = new JMenu("Ayuda"); 
        nuevoMenu = new JMenuItem("Nuevo");
        abrirMenu = new JMenuItem("Abrir .LFP");
        guardarMenuLFP = new JMenuItem("Guardar .LFP");
        guardarMenuHTML = new JMenuItem("Guardar HTML");
        guardarMenuAmbos = new JMenuItem("Guardar .LFP y HTML");
        guardarComoMenuLFP = new JMenuItem("Guardar Como .LFP");
        guardarComoMenuHTML = new JMenuItem("Guardar Como HTML");
        guardarComoMenuAmbos = new JMenuItem("Guardar Como .LFP y HTML");
        traducirMenu = new JMenuItem("Traducir .LCP a HTML");
        salirMenu = new JMenuItem("Salir");
        manualUsuarioMenu = new JMenuItem("Manual de Usuario");
        manualTecnicoMenu = new JMenuItem("Manual Tecnico");
        textoLFP = new JTextArea();
        textoHTML = new JTextArea();
        areaLFP = new JScrollPane(textoLFP);
        areaHTML = new JScrollPane(textoHTML);
        indicadorLFP = new JLabel("Archivo LFP");
        indicadorHTML = new JLabel("Archivo HTML");
        
        manejoArchivos = new ManejoArchivo();
        
        analizar = new Analizar();
    }

    private void posicionarElementos() {
        ventana.add(contenedor);

        contenedor.setLayout(null);

        establecerBarrita();

        contenedor.add(areaLFP);
        contenedor.add(areaHTML);
        contenedor.add(indicadorLFP);
        contenedor.add(indicadorHTML);

        areaLFP.setBounds(7, 25, 495, 500);
        areaHTML.setBounds(512, 25, 495, 500);
        
        indicadorLFP.setBounds(225, 525 , 100,35);
        indicadorHTML.setBounds(725, 525 , 100,35);
        
        textoLFP.setLineWrap(true);
        textoLFP.setWrapStyleWord(true);
        textoHTML.setLineWrap(true);
        textoHTML.setWrapStyleWord(true);
        textoHTML.setEditable(false);

        ventana.setSize(1024, 600);
        ventana.setVisible(true);
        ventana.setLocationRelativeTo(null);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setResizable(false);

    }

    private void establecerBarrita() {
        contenedor.add(barrita);

        barrita.add(menuArchivo);
        barrita.add(menuAyuda);

        menuArchivo.add(nuevoMenu);
        menuArchivo.add(abrirMenu);
        menuArchivo.add(guardarMenuLFP);
        menuArchivo.add(guardarMenuHTML);
        menuArchivo.add(guardarMenuAmbos);
        menuArchivo.add(guardarComoMenuLFP);
        menuArchivo.add(guardarComoMenuHTML);
        menuArchivo.add(guardarComoMenuAmbos);
        menuArchivo.add(traducirMenu);
        menuArchivo.add(salirMenu);

        menuAyuda.add(manualUsuarioMenu);
        menuAyuda.add(manualTecnicoMenu);

        barrita.setBounds(0, 0, 1024, 25);

        ActionListener salir = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        };

        salirMenu.addActionListener(salir);
    
        añadirAccionesBarrita();
    }

    private void añadirAccionesBarrita(){
        guardarComoMenuLFP.addActionListener(manejoArchivos.getGuardarComo(textoLFP, 0));
        guardarMenuLFP.addActionListener(manejoArchivos.getGuardar(textoLFP, 0));
        guardarComoMenuHTML.addActionListener(manejoArchivos.getGuardarComo(textoHTML, 1));
        guardarMenuHTML.addActionListener(manejoArchivos.getGuardar(textoHTML, 1));
        guardarMenuAmbos.addActionListener(manejoArchivos.guardarAmbos(textoLFP, textoHTML));
        guardarComoMenuAmbos.addActionListener(manejoArchivos.guardarComoAmbos(textoLFP, textoHTML));
        traducirMenu.addActionListener(analizar.getAnalizar(textoLFP, textoHTML));
        abrirMenu.addActionListener(manejoArchivos.leerLFP(textoLFP, textoHTML));
        
        
        ActionListener limpieza = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textoHTML.setText("");
                textoLFP.setText("");
            }
        };
        
        manualTecnicoMenu.addActionListener(limpieza);
        
        
    }    
}
