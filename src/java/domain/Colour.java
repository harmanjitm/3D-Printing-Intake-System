/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

/**
 *
 * @author Ben Wozak
 */
public class Colour {
    
    private String color;
    private String status;

    /**
     * Initiates a new Color.
     */
    public Colour() {
    }

    /**
     * Initiates a new Color.
     * @param color the color
     * @param status the color
     */
    public Colour(String color, String status) {
        this.color = color;
        this.status = status;
    }

    /**
     * Gets the color of a color object
     * @return 
     */
    public String getColor() {
        return color;
    }

    /**
     * Sets the color of a color object
     * @param color 
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Gets the status of a color object
     * @return status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status of a color object
     * @param status 
     */
    public void setStatus(String status) {
        this.status = status;
    }
    
    @Override
    public String toString()
    {
        return color;
    }
    
}
