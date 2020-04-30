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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author mcein
 */
public class Product {
    
    protected ObservableList<Part> associatedParts = FXCollections.observableArrayList();
  
    protected IntegerProperty id;
    protected StringProperty name;
    protected DoubleProperty price;
    protected IntegerProperty stock;
    protected IntegerProperty min;
    protected IntegerProperty max;
    
    public Product(int id, String name, double price, int stock, int min, int max){
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleDoubleProperty(price);
        this.stock = new SimpleIntegerProperty(stock);
        this.min = new SimpleIntegerProperty(min);
        this.max = new SimpleIntegerProperty(max);
    }
    
    public Product(){
        
    }
    
    public final void setID (int productID){
        this.id.set(productID);
    }
    
    public final void setName (String productName){
        this.name.set(productName);
    }
    
    public final void setPrice(double productPrice){
        this.price.set(productPrice);
    }
    
    public final void setStock(int productStock){
        this.stock.set(productStock);
    }
    
    public final void setMin(int productMin){
        this.min.set(productMin);
    }
    
    public final void setMax(int productMax){
        this.min.set(productMax);
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
    
    public IntegerProperty productIDProperty(){
        return id;
    }
    
    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }
    
    public boolean deleteAssociatedPart(Part part){
        if(associatedParts.contains(part)){
           associatedParts.remove(part);
           return true;
        } else {
            return false;
        }  
    }
    
    public ObservableList getAllAssociatedParts(){
        return this.associatedParts;
    }
    
    //This will be used as validation requirements in Set 1 (Ensure that the min is not above the max)
    public static String maxOverMin(int min, int max){
        return (min > max) ? "" : "The Min value cannot be more than the max value.";
    }
}
