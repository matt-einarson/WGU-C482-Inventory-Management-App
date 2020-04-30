/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewController;

import Model.InHouse;
import static Model.Inventory.getAllParts;
import Model.Inventory;
import Model.Outsourced;
import Model.Part;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author mcein
 */
public class AddPartController implements Initializable {
    
    Stage stage;
    Parent scene;
    

    @FXML
    private RadioButton SelectInHouse;
    @FXML
    private ToggleGroup PartType;
    @FXML
    private RadioButton SelectOutsourced;
    
    //This is true if InHouse is selected, false if Outsourced
    //this lets us see which is selected later so we can change which part type to make add
    private boolean trueInHouse;
    
    @FXML
    private Label TypeLabel;
    @FXML
    private TextField AddPartMax;
    @FXML
    private TextField AddPartMin;
    @FXML
    private TextField AddPartID;
    @FXML
    private TextField AddPartName;
    @FXML
    private TextField AddPartInv;
    @FXML
    private TextField AddPartPrice;
    @FXML
    private TextField AddPartDynamicField;
    @FXML
    private Button addPartSave;
    @FXML
    private Button addPartCancel;
    
    //used in the validation, it holds an exception messages reutrned from one of the validating methods
    private String exMsg = new String();
    
    //This is the part counter in the inventory that we use to auto assign a part ID
    private int autoID;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        autoID = Inventory.assignPartID();
        AddPartID.setText(Integer.toString(autoID + 1));
    }    

    @FXML
    private void renderMachineID(MouseEvent event) {
        TypeLabel.setText("Machine ID");
        trueInHouse = true;
    }

    @FXML
    private void renderOutsourced(MouseEvent event) {
        TypeLabel.setText("Company Name");
        trueInHouse = false;
    }

    @FXML
    private void addPart(MouseEvent event) throws IOException {
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        
        //Grab whatever the text fields contain to validate them and then create a part with them
        int pMin = Integer.parseInt(AddPartMin.getText());
        int pMax = Integer.parseInt(AddPartMax.getText());
        String pName = new String(AddPartName.getText());
        int pInv = Integer.parseInt(AddPartInv.getText());
        double pPrice = Double.parseDouble(AddPartPrice.getText());
        String pType = AddPartDynamicField.getText();
        
        //Fulfilling the validation requirement in Set 1, make sure min is not greater than max
        //Also making sure that strings aren't being entered where an int is required
        try {

            exMsg = Part.maxOverMin(pMin, pMax);
        
            if(exMsg.length() > 0){
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Add Part Failed");
                alert.setContentText(exMsg);
                alert.showAndWait();
            } else {
                if(trueInHouse){
                    int machineID = Integer.parseInt(pType);
                    InHouse part = new InHouse(autoID, pName, pPrice, pInv, pMin, pMax, machineID);
                    Inventory.addPart(part);
                    
                } else {
                    String companyName = new String(pType);
                    Outsourced part = new Outsourced(autoID, pName, pPrice, pInv, pMin, pMax, companyName);
                }
            }
        
        } catch (NumberFormatException e){
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Add Part Failed");
            alert.setContentText("Part cannot be added, empty or invalid values in one or more fields");
            alert.showAndWait();
            
        }
        
        //Go back to main screen once part is added
        scene = FXMLLoader.load(getClass().getResource("/ViewController/MainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    private void cancel(MouseEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/ViewController/MainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    
    
}
