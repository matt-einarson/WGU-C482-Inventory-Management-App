/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author mcein
 */
public class Inventory {
    
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    
    //Part and product counts are used to automatically generate part & product IDs
    //Aren't currently being used, will return to this feature later
    private static int partCounter = 0;
    private static int productCounter = 0;
    
    public Inventory(){
    }
    
    public static void addPart(Part part){
        allParts.add(part);
    }
    
    public static void addProduct(Product product){
        allProducts.add(product);
    }
    
    public static int assignPartID(){
        partCounter++;
        return partCounter;
    }

     public static int assignProductID(){
        productCounter++;
        return productCounter;
    }
    
    public static Part lookupPart(int partID){
        if(!allParts.isEmpty()){
            for (int i = 0; i < allParts.size(); i++){
                if(allParts.get(i).getID() == partID){
                    return allParts.get(i);
                }
            }
        }
        return null;
    } 
    
    
    public static Product lookupProduct(int productID){
        if(!allProducts.isEmpty()){
            for (int i = 0; i < allProducts.size(); i++){
                if(allProducts.get(i).getID() == productID){
                    return allProducts.get(i);
                }
            }
        }
        return null;
    } 
    
    public static ObservableList<Part> lookupPart(String partName){
        //Not sure a varianle is 100% necessary here, can't figure out how to do with without variable though. 
        ObservableList<Part> results = FXCollections.observableArrayList();
        
        if(!allParts.isEmpty()){
            for (int i = 0; i < allParts.size(); i++){
                if(allParts.get(i).getName().toLowerCase().contains(partName.toLowerCase())){
                    results.add(allParts.get(i));
                }
            }
        } else{
            return results;
        }
        return results;
    } 
    
    public static ObservableList<Product> lookupProduct(String productName){
       
    ObservableList<Product> results = FXCollections.observableArrayList();
    
        if(!allProducts.isEmpty()){
            for (int i = 0; i < allProducts.size(); i++){
                if(allProducts.get(i).getName().toLowerCase().equals(productName.toLowerCase())){
                    results.add(allProducts.get(i));
                }
            }
        } else {
        return results;
        }
        
        return results;
    }
    
    public static void updatePart(int index, Part selectedPart){
        allParts.set(index, selectedPart);
    }
    
    public static void updateProducts(int index, Product selectedProduct){
        allProducts.set(index, selectedProduct);
    }
    
    public static boolean deletePart(Part selectedPart){
        return allParts.remove(selectedPart);
    } 
    
    public static boolean deleteProduct(Product selectedProduct) {
        return allProducts.remove(selectedProduct);
    }
    
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }
    
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
    
    //Here we are going to check that a part doesn't belong to a product, if it does we tell the user which product
    public static String deleteValidation(Part part){
        for(int i = 0; i < allParts.size() ; i++){
            if(allProducts.get(i).getAllAssociatedParts().contains(part)){
                return allProducts.get(i).getName();
            }
        }
        return "";
    }
}

           
