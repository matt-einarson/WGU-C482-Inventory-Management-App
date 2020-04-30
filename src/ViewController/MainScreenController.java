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
public class MainScreenController implements Initializable {
    
    Stage stage; 
    Parent scene;

    @FXML
    private Button MainPartSearchButton;
    @FXML
    private TextField MainPartSearch;
    @FXML
    private TableView<Part> MainPartTable;
    @FXML
    private TableColumn<Part, Integer> pIDColumn;
    @FXML
    private TableColumn<Part, String> pNameColumn;
    @FXML
    private TableColumn<Part, Integer> pInvColumn;
    @FXML
    private TableColumn<Part, Double> pCostColumn;
    @FXML
    private Button MainPartAdd;
    @FXML
    private Button MainPartModify;
    @FXML
    private Button MainPartDelete;
    @FXML
    private Button MainProductSearchButton;
    @FXML
    private TextField MainProductSearch;
    @FXML
    private TableView<Product> MainProductTable;
    @FXML
    private TableColumn<Product, Integer> prodIDColumn;
    @FXML
    private TableColumn<Product, String> prodNameColumn;
    @FXML
    private TableColumn<Product, Integer> prodInvColumn;
    @FXML
    private TableColumn<Product, Double> prodPriceColumn;
    @FXML
    private Button MainProductAdd;
    @FXML
    private Button MainProductModify;
    @FXML
    private Button MainProductDelete;
    @FXML
    private Button MainExit;
    
    //This gets the index of the selected part so that we can autopopulate the ID value and load the correct part
    //Must give this a default value so the program doens't crash if you hit the modify button without selecting a part
    protected static int partModIndex;
    //These two are same as above just with products
    private static Product prodMod;
    protected static int prodModIndex;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //Populate Parts table
        MainPartTable.setItems(Inventory.getAllParts());
        pIDColumn.setCellValueFactory(cellData -> cellData.getValue().pIDProperty().asObject());
        pNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        pInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        pCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        //Populate Products table
        MainProductTable.setItems(Inventory.getAllProducts());
        prodIDColumn.setCellValueFactory(cellData -> cellData.getValue().productIDProperty().asObject());
        prodNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        prodInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        prodPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        
    }    
    
    //The event handlers for the buttons
    
    @FXML
    private void searchForPart(MouseEvent event) throws IOException {
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        
        String search = MainPartSearch.getText();
        ObservableList<Part> found = FXCollections.observableArrayList();
        found = Inventory.lookupPart(search);
        
        if(found.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No part Found");
            alert.setContentText("No part found with name " +search);
            alert.showAndWait();
        } else {
            MainPartTable.setItems(found);
        }
    
    }

    @FXML
    private void addPart(MouseEvent event) throws IOException {
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/ViewController/AddPart.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    private void updatePart(MouseEvent event) throws IOException {
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        
        //The index starts art 0 so I need to add a one here to make it work
        partModIndex = Inventory.getAllParts().indexOf((MainPartTable.getSelectionModel().getSelectedItem()));
        //We want to pass the part index to the modify part controller so that it can load the part of the specified index
        
        scene = FXMLLoader.load(getClass().getResource("/ViewController/ModifyPart.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    private void deletePart(MouseEvent event) throws IOException {
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        
        Part part = MainPartTable.getSelectionModel().getSelectedItem();
       
        Inventory.deletePart(part);
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Deleted");
        alert.setHeaderText("Part Removed");
        alert.setContentText("Part Successfully Deleted");
        alert.showAndWait();
    }

    @FXML
    private void searchForProduct(MouseEvent event) throws IOException {
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        
        String search = MainProductSearch.getText();
        ObservableList<Product> found = FXCollections.observableArrayList();
        found = Inventory.lookupProduct(search);
        
        if(found.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No product Found");
            alert.setContentText("No product found with name " +search);
            alert.showAndWait();
        } else {
            MainProductTable.setItems(found);
        }

    }

    @FXML
    private void addProduct(MouseEvent event) throws IOException {
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/ViewController/AddProduct.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    private void updateProduct(MouseEvent event) throws IOException {
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        
        prodModIndex = Inventory.getAllParts().indexOf((MainPartTable.getSelectionModel().getSelectedItem())) + 1;
        
        
        scene = FXMLLoader.load(getClass().getResource("/ViewController/ModifyProduct.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    private void deleteProduct(MouseEvent event) throws IOException {
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();  
        
        Product product = MainProductTable.getSelectionModel().getSelectedItem();
        for(int i = 0; i < Inventory.getAllParts().size(); i++){
            if(Inventory.deleteValidation(Inventory.getAllParts().get(i)).equals("")){
                Inventory.deleteProduct(product);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Deleted");
                alert.setHeaderText("Product Removed");
                alert.setContentText("Product Successfully Deleted");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Cannot delete product");
                alert.setContentText("Make sure product contains no parts before deleting");
                alert.showAndWait();
            }
        }
      

    }
    
    @FXML
    private void exitProgram(MouseEvent event) throws IOException {
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        stage.close();

    }
    

}
