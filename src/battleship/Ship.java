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
 * @author gstev
 */
public class Ship
{
	protected String shipName;
	protected int[] shipPieces; 
	protected char Direction;
        protected Image[] imgShips = new Image[10]; //our image array holding all ship images, we will access it in our derived classes
        protected Image[] imgSunkShips = new Image[10]; //our image array holding all ship images, we will access it in our derived classes
	protected ArrayList<Label> imgLabel = new ArrayList<Label>();	
	Ship(String name, char Direction)
	{
		this.Direction = Direction;
		this.shipName = name;	
                loadImages(); 
	}
        
        public void setPieces(int [] pieces)
        {
            shipPieces = new int[pieces.length];
            for(int i = 0; i < pieces.length; i++)
            shipPieces[i] = pieces[i];
        }
	public String getName()
	{
		return this.shipName;
	}
	public int[] getShipPieces()
	{
		return shipPieces;
	}
	public int getDirection()
	{
		return this.Direction;
	}
	public int length()
	{
		return shipPieces.length;
	}
        private void loadImages()
        {
            for(int i = 0; i < 10 ; i++)
            {
                imgShips[i] = new Image("file:Images\\batt" + (i + 1) + ".gif");
            }
        }
        private void loadSunkImages()
        {
            for(int i = 0; i < 10 ; i++)
            {
                imgSunkShips[i] = new Image("file:Images\\batt20" + (i + 1) + ".gif");
            }
        }
        public Label getLabels(int index)
        {
            return imgLabel.get(index);
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
        public void setSunkShipLabel() //set the sunk ship label array so we can later return them piece by piece
        {
           int [] pieces = this.getShipPieces();
           for(int x = 0; x < pieces.length; ++x)
           {
                Label shipLbl = new Label();
                shipLbl.setGraphic(new ImageView(this.imgShips[pieces[x]]));
                this.imgLabel.add(shipLbl);
           }
        }
        public void setShipSunk()
        {
            
        }
                
        
}