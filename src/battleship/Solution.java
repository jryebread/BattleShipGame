/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author James
 */
public class Solution 
{  
    private static final int GRIDSIZE  = 16;

    private Image[] imgSunkShips = new Image[10]; //our image array holding all sunk ships
    private Label[][] lblSunk = new Label[GRIDSIZE][GRIDSIZE]; //label array that holds sunk ship labels
    private void loadImages()
    {
          for(int i = 0; i < 6 ; i++)
          {
                imgSunkShips[i] = new Image("file:Images\\batt20" + (i + 1) + ".gif");
          }
     }
//    public void createSolution(Label[][] lblPlayer, char[][] ocean)
//    {
//        lblSunk = lblPlayer;
//        for(int i = 0; i < GRIDSIZE; ++i)
//        {
//            for(int j = 0; j < GRIDSIZE; ++j)
//            {
//                if(ocean[i][j] == 'M' || ocean[i][j] == 'F' ||ocean[i][j] ==  'C' || ocean[i][j] ==  'B')
//                {
//                    Label sunkShip = new Label();
//                    sunkShip.getID();
//                    lblSunk[i][j].setGraphic(new ImageView(this.imgSunkShips[pieces[x]]));
//                }    
//            }
//        }
//    }
}

