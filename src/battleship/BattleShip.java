 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.image.*;
import java.awt.Color;
import javafx.geometry.Insets;
import java.util.Random;

/**
 *
 * @author gstev
 */
public class BattleShip extends Application {
    
    private static final int MAXSHIPS = 14;
    private static final int GRIDSIZE  = 16;
    private GridPane pnlPlayer = new GridPane();
    private Label[][] lblPlayer = new Label[GRIDSIZE][GRIDSIZE];
    private Image[] imgShips = new Image[10];
    private Ship[] shipInfo = new Ship[8];
    private char[][] ocean = new char[16][16];    
    
    
    @Override
    public void start(Stage primaryStage) {
                
        BorderPane root = new BorderPane();
                
        Scene scene = new Scene(root, 290, 290);
        
        primaryStage.setTitle("Battleship");
        primaryStage.setScene(scene);
        primaryStage.show();
        this.initOcean();
        this.createPlayerPanel();
        createShips();
        root.setCenter(pnlPlayer);
        placeShips();
    }
    private void createPlayerPanel()
    {
       pnlPlayer.setStyle("-fx-background-color:#0000FF;");
       for(int row = 0; row < GRIDSIZE; row++)
       {
           for(int col = 0; col < GRIDSIZE; col++)
           {
               lblPlayer[row][col] = new Label();               
               Image imgShip = new Image("file:Images\\batt100.gif"); //loads all lbls with ocean
               lblPlayer[row][col].setGraphic(new ImageView(imgShip));
               lblPlayer[row][col].setMaxSize(16.0, 16.0);
               lblPlayer[row][col].setStyle("-fx-border-width:1;-fx-border-color:black;");
               pnlPlayer.add(lblPlayer[row][col], col, row);      
           
           }
       }
      
    }
    private void createShips()
    {
        this.createShipInfo();
    }
   
    private void createShipInfo()
    {
        //Start with the minesweeper, we create 2 of them here but will place 3 total randomly it as two images
		//int[] mineSweepH = {0,4};
		shipInfo[0] = new MineSweeper('H');
		//int[] mineSweepV = {5,9};
		shipInfo[1] = new MineSweeper('V');
		
        // Create the frigate it has 3 pieces
		//int[] frigateH = {0,1,4};
		shipInfo[2] = new Frigate( 'H');
		//int[] frigateV = {5,6,9};
		shipInfo[3] = new Frigate( 'V');
	//Cruisers 4	
		//int[] cruiserH = {0,1,2,4};
		shipInfo[4] = new Cruiser( 'H');
		//int[] cruiserV = {5,6,7,9};
		shipInfo[5] = new Cruiser( 'V');
        //BattleShips 5
		//int[] battleShipH = {0,1,2,3,4};
		shipInfo[6] = new BShip('H');
		//int[] battleShipV = {5,6,7,8,9};
		shipInfo[7] = new BShip( 'V'); 
    }
    private void initOcean() //initializes char array as all "O"s for ocean
    {
        for(int row = 0; row < 16; row++)
        {
            for(int col = 0; col < 16; col++)
            {
                    ocean[row][col] = 'O';
            }
        }
    }
    private void placeShips()
    {
        
       // Create a Random object to select ships
        Random r = new Random();

        // Create random objects to place the ship at a row and a column
        Random randCol = new Random();
        Random randRow = new Random();

        //Place the ships, typically there are 14
        for(int ships = 0; ships < MAXSHIPS; ships++)
        {
                createShips();
                 //Get a random ship
                Ship ship = shipInfo[r.nextInt(8)];

                int row = randRow.nextInt(16);
                int col = randCol.nextInt(16);
                int direction = checkDirection(ship, row, col);
                while(direction == 0) // 0 direction says that we can not place the ship
                {
                        row = randRow.nextInt(16);
                        col = randCol.nextInt(16);
                        direction = checkDirection(ship, row, col);
                }
                // got a clear path, let put the ship on the ocean
                int shipPieces[] = ship.getShipPieces();
                if(ship.getDirection() == 'H')  // place horizontal
                {
                        if(direction == 1)
                        {
                            for(int i = col, j = 0; i < col + ship.length(); i++, j++)
                            {         
                                
                                lblPlayer[row][i].setGraphic(ship.getLabels(j)); //sets the panel graphic to a ship
                                String name = ship.getName();
                                ocean[row][i] = name.charAt(0); //updates the char array
                            }
                        }
                        else
                        {
                            for(int i = col + ship.length(), j = 0 ; i > col; i--, j++)
                            {
                                lblPlayer[row][i].setGraphic(ship.getLabels(j));	
                                String name = ship.getName();
                                ocean[row][i] = name.charAt(0);
                            }
                        }
                }
                else // Must be vertical direction
                {
                        if(direction == 1) // place pieces in positive direction
                        {
                            for(int i = row, j = 0; i < row + ship.length(); i++, j++)
                            {
                                lblPlayer[i][col].setGraphic((ship.getLabels(j)));	
                                String name = ship.getName();
                                ocean[i][col] = name.charAt(0);
                            }
                        }
                        else
                        {
                                for(int i = row + ship.length(), j = 0; i > row; i--, j++)
                                {
                                    lblPlayer[i][col].setGraphic(ship.getLabels(j));	
                                    String name = ship.getName();
                                    ocean[i][col] = name.charAt(0);
                                }
                        }
                }			
        } 
    }
    private int checkDirection(Ship si, int row, int col)
    {
        if(si.getDirection() == 'H')
            return checkHorizontal(si, row, col);
        else
            return checkVertical(si, row, col);
    }
    int checkHorizontal(Ship si,int row, int col)
    {
            boolean clearPath = true;

            int len = si.length();
            System.out.println(len);
            for(int i = col; i < (col + si.length()); i++)
            {
                    if(i >= GRIDSIZE) //This would put us outside the ocean
                    {
                            clearPath = false;
                            break;
                    }
                    if(ocean[row][i] != 'O') // Ship already exists in this spot
                    {
                            clearPath = false;
                            break;
                    }
            }
            if(clearPath == true) // ok to move in the positive direction
                    return 1; 

            //Next Chec the negative direction
            for(int i = col; i > (col - si.length()); i--)
            {
                    if(i < 0) //This would put us outside the ocean
                    {
                            clearPath = false;
                            break;
                    }
                    if(ocean[row][i] != 'O') // Ship already exists in this spot
                    {
                            clearPath = false;
                            break;
                    }			

            }
            if(clearPath == true) //Ok to move in negative direction
                    return -1;
            else
                    return 0;   // No place to move			

    }
	
    int checkVertical(Ship si,int row, int col)
    {
            boolean clearPath = true;
            int len = si.length();
            System.out.println(len);

            for(int i = row; i < (row + si.length()); i++)
            {
                if(i >= GRIDSIZE) //This would put us outside the ocean
                {
                        clearPath = false;
                        break;
                }
                if(ocean[i][col] != 'O') // Ship already exists in this spot
                {
                        clearPath = false;
                        break;
                }
            }
            if(clearPath == true) // ok to move in the positive direction
                    return 1; 

            //Next Check the negative direction
            for(int i = row; i > (row - si.length() ); i--)
            {
                if(i < 0) //This would put us outside the ocean
                {
                        clearPath = false;
                        break;
                }
                if(ocean[i][col] != 'O') // Ship already exists in this spot
                {
                        clearPath = false;
                        break;
                }			

            }
            if(clearPath == true) //Ok to move in negative direction
                return -1;
            else
                return 0;   // No place to move			

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
