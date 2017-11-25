/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

import java.util.ArrayList;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author James
 */
public class MineSweeper extends Ship{
     
     
    public MineSweeper(char Direction) 
    {
        super("Minesweeper", Direction);
        
        if(this.Direction == 'H')
        {
            int [] horizontalPieces= {0,4};
            this.setPieces(horizontalPieces);
        }
        else //vertical
        {
            int [] verticalPieces = {5,9};
            this.setPieces(verticalPieces);
        }
         setShipLabel();
    }
   
   
  
}
