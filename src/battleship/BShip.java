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
public class BShip extends Ship {
    

 
    public BShip(char Direction) 
    {
        super("Battleship", Direction);
    
        if(this.Direction == 'H')
        {
            int [] horizontalPieces= {0,1,2,3,4};
            this.setPieces(horizontalPieces);
        }
        else //vertical
        {
            int [] verticalPieces = {5,6,7,8,9};
            this.setPieces(verticalPieces);
        }
         setShipLabel();
    }
    public void setShipLabel() //set the ship label array so we can later return them piece by piece
    {
        int [] pieces = this.getShipPieces();
        
        for(int x = 0; x < pieces.length; ++x)
        {
           Label shipLbl = new Label();
           shipLbl.setGraphic(new ImageView(this.imgShips[pieces[x]]));
           this.imgLabel.add(shipLbl);
        }
    }
    
}
