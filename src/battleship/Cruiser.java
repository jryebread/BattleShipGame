/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

/**
 *
 * @author James
 */
public class Cruiser extends Ship{
    

     
    public Cruiser(char Direction) 
    {
        super("Cruiser", Direction);
    
        if(this.Direction == 'H')
        {
            int [] horizontalPieces= {0,1,2,4};
            this.setPieces(horizontalPieces);
        }
        else //vertical
        {
            int [] verticalPieces = {5,6,7,9};
            this.setPieces(verticalPieces);
        }
         setShipLabel();
    }
    
    
}
