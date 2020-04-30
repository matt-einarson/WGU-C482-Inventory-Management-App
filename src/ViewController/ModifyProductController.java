/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewController;

import Model.Inventory;
import Model.Part;
import Model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author mcein
 */
public class ModifyProductController implements Initializable {
    
    Stage stage;
    Parent scene;
    //Keeps track of parts that are associated with the prodct already
    
    private String exMsg = new String();
    
    private ObservableList<Part> partsToAdd = FXCollections.observableArrayList();

    @FXML
    private TextField ModifyProductMin;
    @FXML
    private TextField ModifyProductMax;
    @FXML
    private TextField ModifyProductID;
    @FXML
    private TextField ModifyProductName;
    @FXML
    private TextField ModifyProductInv;
    @FXML
    private TextField ModifyProductPrice;
    @FXML
    private Button ModifyProductSearchButton;
    @FXML
    private TextField ModifyProductSearch;
    @FXML
    private TableView<Part> ModifyProductAddTable;
    @FXML
    private TableColumn<Part, Double> addPricePerUnit;
    @FXML
    private TableColumn<Part, Integer> addInvLevel;
    @FXML
    private TableColumn<Part, String> addPartName;
    @FXML
    private TableColumn<Part, Integer> addPartID;
    @FXML
    private Button ModifyProductAdd;
    @FXML
    private TableView<Part> ModifyProductDeleteTable;
    @FXML
    private TableColumn<Part, Double> deletePricePerUnit;
    @FXML
    private TableColumn<Part, Integer> deleteInvLevel;
    @FXML
    private TableColumn<Part, String> deletePartName;
    @FXML
    private TableColumn<Part, Integer> deletePartID;
    @FXML
    private Button ModifyProductDelete;
    @FXML
    private Button ModifyProductSave;
    @FXML
    private Button ModifyProductCancel;
    
    protected int autoID = MainScreenController.prodModIndex + 1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Populate Add Parts table
        ModifyProductAddTable.setItems(Inventory.getAllParts());
        addPartID.setCellValueFactory(cellData -> cellData.getValue().pIDProperty().asObject());
        addPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        addInvLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addPricePerUnit.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        //Populate Delete Parts table
        ModifyProductDeleteTable.setItems(Inventory.getAllProducts().get(autoID).getAllAssociatedParts());
        deletePartID.setCellValueFactory(cellData -> cellData.getValue().pIDProperty().asObject());
        deletePartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        deleteInvLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        deletePricePerUnit.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        //Autofills the fields with the part's exsisting values
        ModifyProductID.setText(Integer.toString(autoID + 1));
        Product prodMod = Inventory.getAllProducts().get(autoID);
        ModifyProductName.setText(prodMod.getName());
        ModifyProductInv.setText( Integer.toString(prodMod.getStock()));
        ModifyProductMin.setText(Integer.toString(prodMod.getMax()));
        ModifyProductMax.setText(Integer.toString(prodMod.getMin()));
        ModifyProductPrice.setText(Double.toString(prodMod.getPrice()));
    }    

    @FXML
    private void addPart(MouseEvent event) {
        partsToAdd.add(Inventory.lookupPart((Inventory.getAllParts().indexOf((ModifyProductAddTable.getSelectionModel().getSelectedItem())) + 1)));
    }

    @FXML
    private void deletePart(MouseEvent event) {
        Part part = ModifyProductDeleteTable.getSelectionModel().getSelectedItem();
       
       Inventory.getAllProducts().get(autoID).deleteAssociatedPart(part);
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Deleted");
        alert.setHeaderText("Part Removed");
        alert.setContentText("Part Successfully Deleted");
        alert.showAndWait();
    }

    @FXML
    private void saveProduct(MouseEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        
        int pID = Integer.parseInt(ModifyProductID.getText());
        int pMin = Integer.parseInt(ModifyProductMin.getText());
        int pMax = Integer.parseInt(ModifyProductMax.getText());
        String pName = new String(ModifyProductName.getText());
        int pInv = Integer.parseInt(ModifyProductInv.getText());
        double pPrice = Double.parseDouble(ModifyProductPrice.getText());
        
        //Fulfilling the validation requirement in Set 1, make sure min is not greater than max
        //Also making sure that strings aren't being entered where an int is required
        try {

            exMsg = Product.maxOverMin(pMin, pMax);
        
            if(exMsg.length() > 0){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Add Product Failed");
                alert.setContentText(exMsg);
                alert.showAndWait();
            } else {
                    
                    Product product = new Product(pID, pName, pPrice, pInv, pMin, pMax);
                    Inventory.updateProducts(autoID, product);
            }
            } catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Add product Failed");
            alert.setContentText("Product cannot be added, empty or invalid values in one or more fields");
            alert.showAndWait();
            
        }
        
        scene = FXMLLoader.load(getClass().getResource("/ViewController/MainScreen.fxml"));
        stage.setScene(new Scene (scene));
        stage.show();
    }

    @FXML
    private void cancel(MouseEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/ViewController/MainScreen.fxml"));
        stage.setScene(new Scene (scene));
        stage.show();
    }

    @FXML
    private void lookupPart(MouseEvent event) {
         String search = ModifyProductSearch.getText();
        ObservableList<Part> found = FXCollections.observableArrayList();
        found = Inventory.lookupPart(search);
        
        if(found.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No part Found");
            alert.setContentText("No part found with name " +search);
            alert.showAndWait();
        } else {
            ModifyProductAddTable.setItems(found);
        }
    }
    
}
