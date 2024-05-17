/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pedro.ieslaencanta.com.falkensmaze.components;

/**
 *
 * @author Pedro
 */

/*Interfaz relacionada con el funcionamiento de los bloques*/

public interface IBlockListener {
    
    /*Metodo que mostrara que pasara ciando se hace click en 
    un bloque*/
    
    public void onClicked(Block b);
    
    
    /*Metodo que mostrara que pasara cuando se hace doble 
    click en  un bloque*/
    public void onDoubleClicked(Block b);
}
