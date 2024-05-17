/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pedro.ieslaencanta.com.falkensmaze.model;

import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/*Es una Clase creada para crear los bloques que tendra 
el maze cuando se inicie el proyecto*/
@XmlRootElement
public class Block implements  Serializable {
    private String value;
    
    
    /*Es un constructor del bloque vacio.Por eso su
    valor es nulo*/
    public Block(){
        this.value=null;
    }
    
    
    /*Es el metodo encargado de modificar 
     el valor actual*/ 
    public String getValue(){
        return this.value;
    }
    
    public void setValue(String value){
        this.value=value;
    }
    
    /*Metodo que comprueba si el bloque tiene un valor
    nulo*/F
    public boolean isEmpty(){
        return this.value==null;
    }
}
