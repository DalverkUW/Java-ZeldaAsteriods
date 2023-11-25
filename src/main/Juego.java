package main;

import Recursos.grafic;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import Recursos.player;
import Recursos.enemy;
import Recursos.disparo;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;


public class Juego extends JFrame implements Runnable{
    private static final int ach=1000,alt=700; //Tamaño de ventana del juego en ejecucion
    private Canvas canv;    //Para la funcion paint que pinta el fondo de la ventana
    private Thread hilo;
    private boolean runn,stop,exit;
    private BufferStrategy buf;
    private Graphics grf;
    
    private int max,tim;
    private ArrayList<enemy> enm;
    private JPanel keys;
    private player p1;
    private disparo b;
    private Font fuente;
    private boolean damg;
    
    private double TIMEREQ = 1000000000/60;
    private double delta=0;
    
    private int flowers[][];
    
    public Juego(){
        
        setTitle("Tektite Hunting"); //Titulo que aparece en la parte superior de la ventana mientras se ejecuta
        setSize(ach, alt);       //Tamaño de la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);     //Para qur la ventana de juego no se redimensione
        setLocationRelativeTo(null);
        
        
        keys = new JPanel();    //Abre ventana del juego
        setContentPane(keys);
        keys.setFocusable(true);
        
        keys.addKeyListener(new KeyListener(){

            @Override
            public void keyTyped(KeyEvent ke) {
            }

            @Override
            public void keyPressed(KeyEvent ke) {
                if(ke.getKeyCode()==KeyEvent.VK_ENTER){
                    if(exit){
                        restart();
                    }else if(stop){
                        stop=false;
                    }else{
                        stop=true;
                    }
                    
                }
                if(ke.getKeyCode()==KeyEvent.VK_RIGHT){ //Flecha derecha para rotar derecha
                    p1.rout(+Math.PI/5); //Rota el personaje (+Math.PI/10) es velocidad de rotacion
                }
                if(ke.getKeyCode()==KeyEvent.VK_LEFT){ //Flecha izquierda para rotar izquierda
                    p1.rout(-Math.PI/5); //Rota el personaje (+Math.PI/10) es velocidad de rotacion
                }
                if(ke.getKeyCode()==KeyEvent.VK_UP){  //flecha hacia arriba para avanzar
                    p1.move();
                }
                
                if(ke.getKeyCode()==KeyEvent.VK_DOWN){ //flecha hacia abajo para avanzar de reversa
                    p1.move2();
                }
                
                if(ke.getKeyCode()==KeyEvent.VK_SPACE){ //Espacio para disparar flechas
                    p1.fire();
                }
                if(ke.getKeyCode()==KeyEvent.VK_ESCAPE){ //Escape para salir
                    if(stop||exit){ //Solo si se esta detenido (en pause) se puede salir
                        while(stop()){
                            
                        }
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent ke) {
            }
        });
             
        canv = new Canvas();
        canv.setPreferredSize(new Dimension(ach,alt));
        canv.setMaximumSize(new Dimension(ach,alt));
        canv.setMinimumSize(new Dimension(ach,alt));
        canv.setFocusable(true);
        add(canv);
        fuente=new Font("Engravers MT",Font.BOLD, 18);
        
        damg=false;
        enm=new ArrayList<enemy>();
        runn=false;
        stop=false;
        exit=false;
        p1=new player();
        
        flowers=new int[200][100];  //Area donde se dispersan las matas de pasto
        for(int i=0;i<200;i++){
            flowers[i][0]=(int)(Math.random()*ach);
            flowers[i][1]=(int)(Math.random()*alt);
        }
    }
    public void restart(){
        enm=new ArrayList<enemy>();
        p1=new player();
        stop=false;
        exit=false;
    }
    
    private void upd(){
        if(p1.getlive()<=0){    //Si jugador muere exit toma el valor de TRUE
            exit=true;
        }
        
        max=(int) ((p1.getpunt()/200)+1);
        
        while(enm.size()<max){
            enm.add(new enemy(ach-40,alt-40)); //Añade un nuevo tektite de tamanio 40*40
            //enm.add(new enemy(ach-40,alt-40)); //Añade un tektite extra de tamanio 40*40
            //enm.add(new enemy(ach-40,alt-40)); //Añade un tektite extra de tamanio 40*40
            //enm.add(new enemy(ach-40,alt-40)); //Añade un tektite extra de tamanio 40*40
            //enm.add(new enemy(ach-40,alt-40)); //Añade un tektite extra de tamanio 40*40
        }
        
        for(int j=0;j<enm.size();j++){
            try{
                if(enm.get(j).updat()){
                    enm.remove(j);
                }
                if(!enm.get(j).isdest()){
                    for(int i=(int)enm.get(j).getx();i<enm.get(j).getxx();i++){
                        for(int k=(int)enm.get(j).gety();k<enm.get(j).getyy();k++){
                            if(i>p1.getx()+10&&i<p1.getx()+40&&k>p1.gety()+10&&k<p1.gety()+28){
                                p1.damag();
                                enm.get(j).destroy();
                                damg=true;
                                tim=5;
                                break;
                            }
                        }
                        if(damg)break;
                    }

                    if(enm.get(j).getliv()==0){ //Si el enemigo muere
                        enm.get(j).destroy();
                        p1.puntmor();           //añade puntuacion si el enemigo muere
                        //enm.add(new enemy(ach-40,alt-40));
                        
                    }
                }
                
                for(int i=0;i<p1.getlev()+1&&i<10;i++){
                    b=p1.getfir(i);
                    if(enm.get(j).damag(b)){    //Si el enemigo recibe daño
                        b.destroy();            //El enemigo muere (se destruye)
                }
                    
                    if(b.upd()){
                        p1.dell(i);
                         enm.add(new enemy(ach-40,alt-40)); //añade mas enemigos cuando muere uno
                         //enm.add(new enemy(ach-40,alt-40));
                       
                    }
                    
                }
            }catch (IndexOutOfBoundsException e){}
        }
    }
    
    //Para el dibujado del fondo//
    private void prin(){
        buf = canv.getBufferStrategy();
        if(buf==null){
            canv.createBufferStrategy(3);
            return;
        }
        grf=buf.getDrawGraphics();
        Graphics2D grf2 = (Graphics2D)grf; 
        
        //-------Empieza a dibujar----------//
            
                //Fondo de pantalla de juego//
            //grf2.drawImage(grafic.loadImg("/imagenes/Fondo.png"), 0, 0, null);
            Color ColorPasto=new Color(7, 140, 47);  //Crea un nuevo color mediante paleta RGB
            grf.setColor(ColorPasto);      //Asigna el color
            grf.fillRect(0,0,ach,alt);      //llena el rectangulo(pantalla) de verde
            
            
                //Matas de Pasto//
            Color ColorPasto2=new Color(0, 115, 50); //Crea un nuevo color
            grf.setColor(ColorPasto2);  //Asigna el color
            for(int i=0; i<200; i++){    //Esparce las matas de pasto        
            grf.fillRect(flowers[i][0], flowers[i][1], 3, 6); //Crea el pasto y les da ancho y alto
            }
            
            //------FIN DEL DIBUJADO---------//
            
            
            
            for(int i=0;i<enm.size();i++){
                try{
                    grf2.drawImage(enm.get(i).getimg(),enm.get(i).getat(),null);
                }catch (NullPointerException e){}
            }
            
            for(int i=0;i< p1.getlev()+1&&i<10;i++){
                try{
                    b=p1.getfir(i);
                    grf2.drawImage(b.getpic(),b.getat(),null);
                    if(b.getx()>1027||b.getx()<-27||b.gety()>727||b.gety()<-27){
                        p1.dell(i);
                    }
                }catch (IndexOutOfBoundsException e){}
            }
            
            
            //SALUD//
            grf2.drawImage(p1.getimg(),p1.getat(),null);
            grf2.setColor(Color.red);   //Color Barra de Salud
            grf2.fillRoundRect(45,33,p1.getlive()*2,20,5,5); //Rectangulo de salud
            grf2.setFont(fuente);
            
            //RUPEES//
            grf2.setColor(Color.yellow); //Color de la fuente en Rupees
            grf2.drawString("Rupees x "+((int)p1.getpunt()), 50, 80); //Posicion de contador de Rupees
            
            //Si el jugador recibe daño//
            if(damg){                          
                grf2.setColor(Color.blue);   //Barra de Salud cambia con el daño
                grf2.fillRoundRect(45,33,p1.getlive()*2,20,5,5); //Rectangulo de salud
                grf2.setColor(Color.red); //Barra de salud vuelve a ser rojo
                tim--;
                if(tim==0){
                    damg=false;         //Jugador deja de recibir daño
                }
            }
            
            //Si se presiona ENTER para pausar
            if(stop){       
                grf2.drawImage(grafic.loadImg("/imagenes/Pause.jpg"), 0, 0, null); //imagen de PAUSE
            }
            
            if(exit){   //GAME OVER//
                grf2.drawImage(grafic.loadImg("/imagenes/GameOver.png"), 0, 0, null); //Carga imagen Game Over
                grf2.setFont(new Font("Engravers MT",Font.BOLD, 50));
                grf2.setFont(fuente);
                
                Color ColorMenu=new Color(147, 133, 222);
                grf2.setColor(ColorMenu);
                
                grf2.drawString("Press ESC to exit.", 395, 400);
                grf2.drawString("Press ENTER to retry.", 360, 460);
            }
            
        grf2.dispose();
        buf.show();
    }

    //HILOS//
    
    //Metodo RUN 
    @Override
    public void run() {
        long now=0;
        long ltime=System.nanoTime();
        
        while(runn){
            System.out.print("");//NO BORRAR ocupa hacer algo para ejecutarse
            try{
                    now=System.nanoTime();
                    delta+=(now-ltime)/TIMEREQ;
                    ltime=now;
                    setVisible(true);
                    if(delta>=1){
                        if(!exit && !stop)upd();
                        prin();
                        delta--;
                    
                }
            }catch(NullPointerException e){}
        }
        setVisible(false);
    }
    
    //Para comenzar el juego
    public void start(){
        hilo=new Thread(this);
        hilo.start();
        runn=true;
    }
    
    //Para la funcion de PAUSE
    private boolean stop(){
        try{
            runn=false;
            hilo.join();
            return false;
        }catch (InterruptedException e){    //se lanza un thread
            e.printStackTrace();
        }
        return true;
    }
}
