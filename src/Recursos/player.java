package Recursos;

import Recursos.disparo;
import Recursos.grafic;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class player {
    private String nom;
    private double x,y,xx,yy;
    private int cdis,lev,live;
    private ArrayList<disparo> fir;
    private double punt,angl;
    private static BufferedImage picture;
    private AffineTransform at;
    
    public player(){
        x=475;
        y=330;
        xx=x+50;
        yy=y+38;
        live=100;                   //Salud total del jugador
        angl=0;
        picture = grafic.loadImg("/imagenes/link.png");
        at=AffineTransform.getTranslateInstance(x, y);
        at.rotate(Math.PI/2, 25, 20); //acomoda al jugador para que no esté volteando al lado erroneo
        lev=1;
        fir=new ArrayList<disparo>();
    }
    public void fire(){ //Lanza un disparo
        if(fir.size()<lev+2){
            fir.add(new disparo(x,y,angl));
        }
    }
    public void rout(double fex){
        angl+=fex;
        if(angl>6.28319){
            angl-=6.28319;//6.28319;
        }
        if(angl<0.0174533){
            angl+=6.28319;//6.28319;
        }
        at.rotate(fex, 25, 20);
    }
    public void move(){         //Metodo que mueve al Jugador
        x+=Math.cos(angl)*40;   //Velocidad default del jugador en x
        y+=Math.sin(angl)*40;   //Velocidad default del jugador en y
        xx=x+50;
        yy=y+38;
        at=AffineTransform.getTranslateInstance(x, y); //la variable ayudará a rotar
        
        //Estabilizan la rotación del personaje
        at.rotate(+Math.PI/2, 25, 20);
        at.rotate(angl, 25, 20);
    }
    
    public void move2(){         //Metodo que mueve al Jugador de reversa
        x-=Math.cos(angl)*40;   //Velocidad default del jugador en -x
        y-=Math.sin(angl)*40;   //Velocidad default del jugador en -y
        xx=x-50;
        yy=y-38;
        at=AffineTransform.getTranslateInstance(x, y); //la variable ayudará a rotar
        
        //Estabilizan la rotación del personaje
        at.rotate(+Math.PI/2, 25, 20);
        at.rotate(angl, 25, 20);
    }
    
    
    public void damag(){    //perdida de salud
        live-=10;
    }
    public void puntmor(){
        punt+=5;      //Añade 5 puntos por monstruo eliminado
        lev=(int)((punt/200)+1);
    }
    public double getx(){
        return x;
    }
    public double gety(){
        return y;
    }
    public double getxx(){
        return xx;
    }
    public double getyy(){
        return yy;
    }
    public disparo getfir(int i){
        return fir.get(i);
    }
    public int getlive(){
        return live;
    }
    public int getlev(){
        return lev;
    }
    public double getpunt(){
        return punt;
    }
    public AffineTransform getat(){
        return at;
    }
    public BufferedImage getimg(){
        return picture;
    }
    public String getnom(){
        return nom;
    }
    public void setnom(String n){
        nom=n;
    }
    public void dell(int i) {
        fir.remove(i);
    }  
}
