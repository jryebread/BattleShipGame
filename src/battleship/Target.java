/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Target extends Label{
    private Image missImg = new Image("file:Images\\batt102.gif");
    private Image hitImg = new Image("file:Images\\batt103.gif");
    
    public Label returnHit()
    {
        Label hit = new Label();
        hit.setGraphic(new ImageView(hitImg));
        return hit;
    }
    public Label returnMiss()
    {
        Label miss = new Label();
        miss.setGraphic(new ImageView(missImg));
        return miss;
    }
}
