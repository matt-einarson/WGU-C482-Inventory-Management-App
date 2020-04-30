/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author mcein
 */
public class Outsourced extends Part{
    protected StringProperty companyName;
    
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName){
        super(id, name, price, stock, min, max);
        this.companyName = new SimpleStringProperty(companyName);
    }
    
    
    public final void setCompanyName(String name){
        this.companyName.set(name);
    }
    
    public String getCompanyName(){
        return this.companyName.get();
    }
}
