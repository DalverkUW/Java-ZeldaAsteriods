package Recursos;

import java.awt.Graphics;
import javax.swing.ImageIcon;

public class fondo extends javax.swing.JPanel {

    public fondo() {
        initComponents(); //Inicializa los componentes del fondo
    }
    String nombreImagen;

    public fondo(String nombreImagen) { //Constructor del panel
        this.nombreImagen=nombreImagen;
        initComponents();
        this.setSize(600, 450); //Resolucion de la ventana
    }
    
    
    @Override
    public void paintComponent(Graphics g){ //Método que dibuja la imagen
        ImageIcon imagen=new ImageIcon(getClass().getResource(nombreImagen));
        g.drawImage(imagen.getImage(), 0, 0, imagen.getIconWidth(),imagen.getIconHeight(),null);
        setOpaque(false);
        super.paintComponent(g);
    }

  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
