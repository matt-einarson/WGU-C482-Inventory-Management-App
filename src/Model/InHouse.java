/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author mcein
 */
public class InHouse extends Part {
    protected IntegerProperty machineID;
    
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineID){
        super(id, name, price, stock, min, max);
        this.machineID = new SimpleIntegerProperty(machineID);
    }
    
    public final void setMachineID(int id){
        this.machineID.set(id);
    }
    
    public int getMachineID(){
        return this.machineID.get();
    }
}
