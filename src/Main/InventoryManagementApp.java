
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author mcein
 */
public class InventoryManagementApp extends Application {
    
    Inventory inv = new Inventory();
    
    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        addTestData(inv);
        
        Parent root = FXMLLoader.load(getClass().getResource("/ViewController/MainScreen.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
    
    //Used to make and auto-increment ID values
     
    // Sample/Test data
    void addTestData(Inventory inv){
        ///In House Parts
        Part oneR = new InHouse((Inventory.assignPartID()), "Part One", 3.50, 20, 5, 100, 101);
        Part twoR = new InHouse((Inventory.assignPartID()), "Part Two", 1.99, 40, 10, 110, 102);
        Part threeR = new InHouse((Inventory.assignPartID()), "Part Three", 10.99, 10, 2, 50, 103);
        Inventory.addPart(oneR);
        Inventory.addPart(twoR);
        Inventory.addPart(threeR);
        Inventory.addPart(new InHouse((Inventory.assignPartID()), "Part Four", 20.00, 30, 10, 70, 104));
        Inventory.addPart(new InHouse((Inventory.assignPartID()), "Part Five", 1.00, 80, 15, 90, 105));
        
        //Outsourced Parts
        Part oneS = new Outsourced((Inventory.assignPartID()), "Part Six", 3.50, 20, 5, 100, "Company One");
        Part twoS = new Outsourced((Inventory.assignPartID()), "Part Seven", 11.99, 40, 10, 110, "Company Two");
        Part threeS = new Outsourced((Inventory.assignPartID()), "Part Eight", 10.99, 10, 2, 50, "Company Three");
        Inventory.addPart(oneS);
        Inventory.addPart(twoS);
        Inventory.addPart(threeS);
        Inventory.addPart(new Outsourced((Inventory.assignPartID()), "Part Nine", 21.00, 30, 10, 70, "Company Four"));
        Inventory.addPart(new Outsourced((Inventory.assignPartID()), "Part Ten", 17.00, 80, 15, 90, "Company Five"));
        
        //Products
        Product p1 = new Product((Inventory.assignProductID()), "Product One", 10.00, 20, 5, 100);
        Product p2 = new Product((Inventory.assignProductID()), "Product Two", 20.00, 50, 5, 110);
        Product p3 = new Product((Inventory.assignProductID()), "Product Three", 25.00, 10, 5, 80);
        p1.addAssociatedPart(oneR);
        p1.addAssociatedPart(threeR);
        p2.addAssociatedPart(twoR);
        p2.addAssociatedPart(oneS);
        p3.addAssociatedPart(twoS);
        p3.addAssociatedPart(threeS);
        Inventory.addProduct(p1);
        Inventory.addProduct(p2);
        Inventory.addProduct(p3);
         
        }
    
     
}
