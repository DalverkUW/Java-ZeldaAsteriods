package Recursos;

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class disparo {
    
    private double xi,yi,xf,yf,xx,yy,xa,ya;
    private int time;
    private double angl;
    private boolean destro;
    private BufferedImage picture;
    private AffineTransform att;
    private long now;
    private long ltime;
    private long delta;
    private double avance=1000000000/120;
    
    public disparo(double x,double y,double dir){
        xi=x+24;
        yi=y+18;
        xx=xi+20;
        yy=yi+20;
        xf=Math.cos(dir)*300;
        yf=Math.sin(dir)*300;
        xa=Math.cos(dir)*10;
        ya=Math.sin(dir)*10;
        angl=dir;
        picture = grafic.loadImg("/imagenes/arrow.png");
        att=AffineTransform.getTranslateInstance(xi, yi);
        att.rotate((Math.PI/2)+angl,10,10);
        ltime=System.nanoTime();
        now=0;
    }
    public void destroy(){      //Metodo cuando impacta un laser
        picture = grafic.loadImg("/imagenes/impact.png");
        destro=true;
        time=5;
    }
    public boolean isdest(){
        return destro;
    }
    public boolean upd(){
        
        if(!destro){
            if(xi!=xf||yi!=yf){
                now=System.nanoTime();
                delta+=(now-ltime)/avance;
                time+=now-ltime;
                ltime=now;
                if(delta>=1){
                    xi+=xa;
                    yi+=ya;
                    xx=xi+20;
                    yy=yi+20;
                    att=AffineTransform.getTranslateInstance(xi, yi);
                    att.rotate(+Math.PI/2,10,10);
                    att.rotate(angl,10,10);
                    delta--;
                }
            }else{
                destroy();
            }
            return false;
        }else if(time<=0){
            return true;
        }else{
            destro=true;
            time--;
            return false;
        }
    }
    public double getx(){
        return xi;
    }
    public double gety(){
        return yi;
    }
    public double getxx(){
        return xx;
    }
    public double getyy(){
        return yy;
    }
    public BufferedImage getpic(){
        return picture;
    }
    public AffineTransform getat(){
        return att;
    }
}
