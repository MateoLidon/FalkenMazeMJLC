/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pedro.ieslaencanta.com.falkensmaze;

import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;


@XmlRootElement
public class Size implements Cloneable, Comparable<Size>, Serializable {
    private int width;
    private int height;
   
    /*Constructor del tamaño vacio*/
    public Size(){
    }

    /*Constructor de tamaño lleno*/
    public Size(int width,int height){
        this.width=width;
        this.height=height;
    }
    
    /*se indica que se va a clonar el objeto size 
    con las mismas caracteristicas*/
    
    public Object clone() throws CloneNotSupportedException{
        return new Size(this.getWidth(), this.getHeight());
    }
    
    /*Objeto que se comprueba si es identico al que se acaba de utilizar*/
    /*Devolvera true si ambos son iguales y false si no son iguales*/
    @Override
    public boolean equals(Object o){
        if(! (o instanceof Size)){
            return false;
        }
        if (this.getWidth()==((Size)(o)).getWidth() && this.getHeight()==((Size)(o)).getHeight()){
            return true;
        }else{
            return false;
        }
        
    }
    
    /*Es un metodo que compara 2 size*/
     @Override
    public int compareTo(Size o) {
        if(this.getWidth()==o.getWidth() && this.getHeight()==o.getHeight())
            return 0;
        if(this.getWidth()<o.getWidth())
            return -1;
        else
            return 1;
    }
    
    /*Muestra los valores del objeto Size en forma de 
    String*/
    public String toString(){
        return "W:"+this.width+" H:"+this.height;
    }
    
    /*Devuelve la anchura*/
    /**
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /*Devuelve la altura*/
    /**
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /*Modifica la anchura actual por una nueva*/
    /**
     * @param width the width to set
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /*Modififica la altura actual por una nueva*/
    /**
     * @param height the height to set
     */
    public void setHeight(int height) {
        this.height = height;
    }
 

}
