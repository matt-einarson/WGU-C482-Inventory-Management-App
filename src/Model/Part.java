/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author mcein
 */
public abstract class Part {
    
    protected IntegerProperty id;
    protected StringProperty name;
    protected DoubleProperty price;
    protected IntegerProperty stock;
    protected IntegerProperty min;
    protected IntegerProperty max;
      
    
    public Part(int id, String name, double price, int stock, int min, int max){
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleDoubleProperty(price);
        this.stock = new SimpleIntegerProperty(stock);
        this.min = new SimpleIntegerProperty(min);
        this.max = new SimpleIntegerProperty(max);
    }
    
    public Part(){
        
    }

    public final void setID (int partID){
        this.id.set(partID);
    }
    
    public final void setName (String partName){
        this.name.set(partName);
    }
    
    public final void setPrice(double partPrice){
        this.price.set(partPrice);
    }
    
    public final void setStock(int partStock){
        this.stock.set(partStock);
    }
    
    public final void setMin(int partMin){
        this.min.set(partMin);
    }
    
    public final void setMax(int partMax){
        this.min.set(partMax);
    }
    
    public int getID(){
        return this.id.get();
    }
    
    public String getName(){
        return this.name.get();
    }
    
    public double getPrice(){
        return this.price.get();
    }
    
    public int getStock(){
        return this.stock.get();
    }
    
    public int getMin(){
        return this.min.get();
    }
    
    public int getMax(){
        return this.max.get();
    }
    
    public IntegerProperty pIDProperty(){
        return id;
    }
    
    //This will be used as validation requirements in Set 1 (Ensure that the min is not above the max)
    public static String maxOverMin(int min, int max){
        return (min > max) ? "" : "The Min value cannot be more than the max value.";
    }
}