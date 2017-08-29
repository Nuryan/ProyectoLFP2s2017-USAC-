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
    public ActionListener getAnalizar(JTextArea textoLFP, JTextArea textoHTML){
        ActionListener devolver = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                analizar(textoLFP, textoHTML);
            }
        };
        
        return devolver;
    }
    
    private void analizar(JTextArea textoLFP, JTextArea textoHTML){
        //textoHTML.setText(textoLFP.getText());
    
        String[] lineas = textoLFP.getText().split("\n");
        textoHTML.setText(limpiarEntrada(textoLFP.getText()));
    }
    
    private String limpiarEntrada(String texto){
        return texto.replaceAll("\\t", " ");
    }
}
