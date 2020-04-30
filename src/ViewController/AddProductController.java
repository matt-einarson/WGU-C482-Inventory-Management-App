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
public class AddProductController implements Initializable {
    
    Stage stage;
    Parent scene;
    
    private String exMsg = new String();
    
    //Keeps track of parts that the user wants to add in to the inventory
    private ObservableList<Part> partsToAdd = FXCollections.observableArrayList();
    Inventory toAdd = new Inventory();
    
    private int autoID;

    @FXML
    private TextField AddProductMin;
    @FXML
    private TextField AddProductMax;
    @FXML
    private TextField AddProductID;
    @FXML
    private TextField AddProductName;
    @FXML
    private TextField AddProductInv;
    @FXML
    private TextField AddProductPrice;
    @FXML
    private Button AddProductSearchButton;
    @FXML
    private TextField AddProductSearch;
    @FXML
    private TableView<Part> AddProductAddTable;
    @FXML
    private TableColumn<Part, Double> addUnitPrice;
    @FXML
    private TableColumn<Part, Integer> addInvLevel;
    @FXML
    private TableColumn<Part, String> addPartName;
    @FXML
    private TableColumn<Part, Integer> addPartID;
    @FXML
    private Button AddProductAdd;
    @FXML
    private TableView<Part> AddProductDeleteTable;
    @FXML
    private TableColumn<Part, Double> deletePricePerUnit;
    @FXML
    private TableColumn<Part, Integer> deleteInvLevel;
    @FXML
    private TableColumn<Part, String> deletePartName;
    @FXML
    private TableColumn<Part, Integer> deletePartID;
    @FXML
    private Button AddProductDelete;
    @FXML
    private Button AddProductSave;
    @FXML
    private Button AddProductCancel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Populate Add Parts table
        AddProductAddTable.setItems(Inventory.getAllParts());
        addPartID.setCellValueFactory(cellData -> cellData.getValue().pIDProperty().asObject());
        addPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        addInvLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addUnitPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        //Populate Delete Products table
        AddProductDeleteTable.setItems(partsToAdd);
        deletePartID.setCellValueFactory(cellData -> cellData.getValue().pIDProperty().asObject());
        deletePartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        deleteInvLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        deletePricePerUnit.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        autoID = Inventory.assignProductID();
        AddProductID.setText(Integer.toString(autoID + 1));
    }    

    @FXML
    private void lookupPart(MouseEvent event) {
        
        String search = AddProductSearch.getText();
        ObservableList<Part> found = FXCollections.observableArrayList();
        found = Inventory.lookupPart(search);
        
        if(found.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No part Found");
            alert.setContentText("No part found with name " +search);
            alert.showAndWait();
        } else {
            AddProductAddTable.setItems(found);
        }
    }

    @FXML
    private void addPart(MouseEvent event) throws IOException {
        partsToAdd.add(Inventory.lookupPart((Inventory.getAllParts().indexOf((AddProductAddTable.getSelectionModel().getSelectedItem())) + 1)));
    }
    

    @FXML
    private void removePart(MouseEvent event) {
        Part part = AddProductDeleteTable.getSelectionModel().getSelectedItem();
       
        partsToAdd.remove(part);
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Deleted");
        alert.setHeaderText("Part Removed");
        alert.setContentText("Part Successfully Deleted");
        alert.showAndWait();
        
    }

    @FXML
    private void cancel(MouseEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/ViewController/MainScreen.fxml"));
        stage.setScene(new Scene (scene));
        stage.show();
    }

    @FXML
    private void saveProduct(MouseEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        
        
        int pID = Integer.parseInt(AddProductID.getText());
        int pMin = Integer.parseInt(AddProductMin.getText());
        int pMax = Integer.parseInt(AddProductMax.getText());
        String pName = new String(AddProductName.getText());
        int pInv = Integer.parseInt(AddProductInv.getText());
        double pPrice = Double.parseDouble(AddProductPrice.getText());
        
        //Fulfilling the validation requirement in Set 1, make sure min is not greater than max
        //Also making sure that strings aren't being entered where an int is required
        try {

            exMsg = Product.maxOverMin(pMin, pMax);
        
            if(exMsg.length() > 0){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Add Part Failed");
                alert.setContentText(exMsg);
                alert.showAndWait();
            } else {
                    
                    Product product = new Product(pID, pName, pPrice, pInv, pMin, pMax);
                    Inventory.addProduct(product);
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
    }

