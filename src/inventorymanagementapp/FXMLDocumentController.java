/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventorymanagementapp;

import Model.Part;
import Model.Product;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author mcein
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private TextField MainPartSearch;
    @FXML
    private TextField MainProductSearch;
    @FXML
    private TableView<Part> MainPartTable;
    @FXML
    private Button MainPartAdd;
    @FXML
    private Button MainPartModify;
    @FXML
    private Button MainPartDelete;
    @FXML
    private TableView<Product> MainProductTable;
    @FXML
    private Button MainProductAdd;
    @FXML
    private Button MainProductModify;
    @FXML
    private Button MainProductDelete;
    @FXML
    private Button MainExit;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
