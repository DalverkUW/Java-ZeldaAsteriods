package Recursos;

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class enemy {
    
    private int live,time;
    private BufferedImage picture;
    private double x, y, gx, gy, xx, yy;
    private AffineTransform at;
    private boolean destro;
    
    public enemy(int ach,int alt){
        x=(Math.random()*50)+950;
        y=Math.random()*alt;
        gx=Math.random()*2;
        gy=Math.random()*2;
        xx=x+43;
        yy=y+43;
        live=(int) (Math.random()*2)+1;
        picture = grafic.loadImg("/imagenes/tektite.png");
        destro=false;
    }
    
    public void destroy(){  //Destruccion del enemigo
        picture = grafic.loadImg("/imagenes/Rupee.png");
        destro=true;
        time=10; //Tiempo que se muestra la Rupee
    }
    
    public boolean isdest(){
        return destro;
    }
    
    public boolean damag(disparo b){
        for(int i=(int)b.getx();i<b.getxx();i++){
            try{
                for(int k=(int)b.gety();k<b.getyy();k++){
                    if(i>x+5&&i<x+38&&k>y+5&&k<y+38){
                        live--;
                        return true;
                    }
                }
            }catch(NullPointerException e){}
        }
        return false;
    }
    public boolean updat(){
        if(!destro){
            x+=gx;
            y+=gy;
            at=AffineTransform.getTranslateInstance(x,y);
            
            //Velocidad el enemigo//
            //NOTA: valores distintos para simular vida//
            if(x>=(1000-40)){
                gx=-(Math.random()*9); 
            }
            if(y>=(700-80)){
                gy=-(Math.random()*5);
            }
            if(x<=20){
                gx=(Math.random()*9);
            }
            if(y<=20){
                gy=(Math.random()*5);
            }
            xx=x+43;
            yy=y+43;
            return false;
        }else if(time<=0){
            return true;
        }else{
            time--;
            return false;
        }
        
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
    public int getliv(){
        return live;
    }
    public BufferedImage getimg(){
        return picture;
    }
    public AffineTransform getat(){
        return at;
    }
}
