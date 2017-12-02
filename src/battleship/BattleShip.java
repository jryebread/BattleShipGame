 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

import java.awt.BorderLayout;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.image.*;
import java.awt.Color;
import java.awt.Panel;
import java.awt.Toolkit;
import java.util.ArrayList;
import javafx.geometry.Insets;
import java.util.Random;
import javafx.scene.input.MouseEvent;


/**
 *
 * @author gstev
 */
public class BattleShip extends Application {
    
    private static final int MAXSHIPS = 14;
    private static final int GRIDSIZE  = 16;
    private GridPane pnlPlayer = new GridPane();
    private Label[][] lblPlayer = new Label[GRIDSIZE][GRIDSIZE];
    private Label[][] lblShips = new Label[GRIDSIZE][GRIDSIZE];
    private Ship[] shipInfo = new Ship[8];
    private char[][] ocean = new char[16][16];    
    private int countMisses = 0;
    private int countHits = 0;
    private ArrayList<String> markedSpots = new ArrayList<>();
       
    //D
    private Label missCount = new Label("Misses:" + countMisses);
    Button resetButton = new Button("Reset");
    Button showShipsButton = new Button("Show Ships");
    
    @Override
    public void start(Stage primaryStage) {
                
        BorderPane root = new BorderPane();
                
        Scene scene = new Scene(root, 290, 310);
        HBox bottom = new HBox();
        bottom.getChildren().addAll(missCount, resetButton, showShipsButton);
        root.setBottom(bottom);
        Label test = new Label();
     
        // Array list to help check if spots are marked already
        markedSpots.add("file:Images\\batt102.gif");
        markedSpots.add("file:Images\\batt103.gif");
        
        primaryStage.setTitle("Battleship");
        primaryStage.setScene(scene);
        primaryStage.show();
        this.initOcean();
        this.createPlayerPanel();
        createShips();
        root.setCenter(pnlPlayer);
        placeShips();
        //initial hide of ships
        fillWater();
        //createSolution(lblPlayer, ocean, shipInfo);
        checkPlayerClick();
     
    
    }
    private boolean checkPlayerClick() 
    {
            resetButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                
                reset();
            }
        });
        showShipsButton.setOnAction(new EventHandler<ActionEvent>() { 
            @Override
            public void handle(ActionEvent event) {
                    showShips();
            }
        });
        
        for(int i = 0; i < GRIDSIZE; ++i)
        {
            for(int j = 0; j < GRIDSIZE; ++j)
            {
                final int i2 = i;
                final int j2 = j;
                lblPlayer[i][j].setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        
                        if(ocean[i2][j2] == 'O')
                        {
                            System.out.println("Miss!");
                            Target miss = new Target();
                            ++countMisses;
                            missCount.setText("Misses: " + countMisses);
                            lblPlayer[i2][j2].setGraphic(miss.returnMiss());
                            
                        }
                        else if(ocean[i2][j2] == 'M' || ocean[i2][j2] == 'F' ||ocean[i2][j2] ==  'C' ||ocean[i2][j2] ==  'B')
                        {
                          System.out.println("Hit!");
                          countHits++;
                          Target hit = new Target();
                          lblPlayer[i2][j2].setGraphic(hit.returnHit());
                          
                        }
                        
                        //if something is hit
                        Ship si = new Ship(null, 'H');//get the ship pieces 
                        //set them all to red
                          
                    }
                });
            }
        }
        return true;
    }
  
    private void createPlayerPanel()
    {
       pnlPlayer.setStyle("-fx-background-color:#0000FF;");
       for(int row = 0; row < GRIDSIZE; row++)
       {
           for(int col = 0; col < GRIDSIZE; col++)
           {
               lblPlayer[row][col] = new Label();       
               lblShips[row][col] = new Label();
               Image imgShip = new Image("file:Images\\batt100.gif"); //loads all lbls with ocean
               lblPlayer[row][col].setGraphic(new ImageView(imgShip));
               lblShips[row][col].setGraphic(new ImageView(imgShip));
               lblPlayer[row][col].setMaxSize(16.0, 16.0);
               lblShips[row][col].setMaxSize(16.0, 16.0);
               lblPlayer[row][col].setStyle("-fx-border-width:1;-fx-border-color:black;");
               lblShips[row][col].setStyle("-fx-border-width:1;-fx-border-color:black;");
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
		shipInfo[0] = new MineSweeper('H');
		shipInfo[1] = new MineSweeper('V');
		
		shipInfo[2] = new Frigate( 'H');
		shipInfo[3] = new Frigate( 'V');
                
		shipInfo[4] = new Cruiser( 'H');
		shipInfo[5] = new Cruiser( 'V');
    
		shipInfo[6] = new BShip('H');
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
               
                if(ship.getDirection() == 'H')  // place horizontal
                {
                        if(direction == 1)
                        {
                            for(int i = col, j = 0; i < col + ship.length(); i++, j++)
                            {         
                                
                                lblPlayer[row][i].setGraphic(ship.getLabels(j)); //sets the panel graphic to a ship
                                lblShips[row][i].setGraphic(ship.getLabels(j)); //sets the panel graphic to a ship
                                String name = ship.getName();
                                ocean[row][i] = name.charAt(0); //updates the char array
                            }
                        }
                        else
                        {
                            for(int i = col + ship.length(), j = 0 ; i > col; i--, j++)
                            {
                                lblPlayer[row][i].setGraphic(ship.getLabels(j));
                                lblShips[row][i].setGraphic(ship.getLabels(j)); 
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
                                lblShips[i][col].setGraphic((ship.getLabels(j)));
                                String name = ship.getName();
                                ocean[i][col] = name.charAt(0);
                            }
                        }
                        else
                        {
                                for(int i = row + ship.length(), j = 0; i > row; i--, j++)
                                {
                                    lblPlayer[i][col].setGraphic(ship.getLabels(j));	
                                    lblShips[i][col].setGraphic(ship.getLabels(j));
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
    
    private void reset()
    {
        // reset the miss count and display
        this.countMisses = 0;
        this.countHits = 0;
      
        this.missCount.setText("Misses = " + countMisses);
        //this.hiddenShips.clear();

        // Clear Labels
        fillWater();

        // Create new ship positions
        initOcean();
        createShips();
        placeShips();
        
        //hide ships
        fillWater();

       
        
    }
    private void showShips() 
    {
        for(int row = 0; row < 16; row++)
        {
            for(int col = 0; col < 16; col++)
            {
                lblPlayer[row][col].setGraphic(lblShips[row][col].getGraphic());
                lblShips[row][col].setGraphic(new ImageView("file:Images\\batt100.gif"));
            }
        }
        
    }

    private void fillWater()
    {
        // hides the placement of the ships to the user
        for(int row = 0; row < 16; row++)
        {
            for(int col = 0; col < 16; col++)
            {
                lblPlayer[row][col].setGraphic(new ImageView("file:Images\\batt100.gif"));
            }
        }
    }
    
}
