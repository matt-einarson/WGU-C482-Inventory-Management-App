/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewController;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Part;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
public class ModifyPartController implements Initializable {
    
    Stage stage;
    Parent scene;
    
    //flag to keep track of which part type is selected
    private boolean trueInHouse;
    
    @FXML
    private RadioButton ModifyPartInHouse;
    @FXML
    private RadioButton ModifyPartOutsourced;
    @FXML
    private TextField ModifyPartMax;
    @FXML
    private TextField ModifyPartMin;
    @FXML
    private TextField ModifyPartID;
    @FXML
    private TextField ModifyPartName;
    @FXML
    private TextField ModifyPartInv;
    @FXML
    private TextField ModifyPartCost;
    @FXML
    private TextField ModifyPartVarField;
    @FXML
    private Button ModifyPartSave;
    @FXML
    private Button ModifyPartCancel;
    @FXML
    private ToggleGroup InOrOut;
    @FXML
    private Label PartType;
    
    //used in the validation, it holds an exception messages reutrned from one of the validating methods
    private String exMsg = new String();
    //Keeps track of the index, which is needed for the Inventroy.updatePart() method 
    
            
    //This is the part counter in the inventory that we use to auto assign a part ID
    protected int autoID = MainScreenController.partModIndex;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Autofills the fields with the part's exsisting values
        ModifyPartID.setText(Integer.toString(autoID + 1));
        Part partMod = Inventory.getAllParts().get(autoID);
        ModifyPartName.setText(partMod.getName());
        ModifyPartInv.setText( Integer.toString(partMod.getStock()));
        ModifyPartMin.setText(Integer.toString(partMod.getMin()));
        ModifyPartMax.setText(Integer.toString(partMod.getMax()));
        ModifyPartCost.setText(Double.toString(partMod.getPrice()));
        if(partMod instanceof InHouse){
            ModifyPartInHouse.setSelected(true);
            PartType.setText("Machine ID");
            //partMod is of class Part not InHouse so I have to grab cast this to anInHouse part
            ModifyPartVarField.setText(Integer.toString(((InHouse)Inventory.getAllParts().get(autoID)).getMachineID()));
        } else {
            ModifyPartInHouse.setSelected(false);
            PartType.setText("Company Name");
            //partMod is of class Part not InHouse so I have to grab cast this to Outsourced part
            ModifyPartVarField.setText(((Outsourced)Inventory.getAllParts().get(autoID)).getCompanyName());
        }
    }    

    @FXML
    private void setInHouse(MouseEvent event) {
        PartType.setText("Machine ID");
        trueInHouse = true;
    }

    @FXML
    private void setOutsourced(MouseEvent event) {
        PartType.setText("Company Name");
        trueInHouse = false;
    }

    @FXML
    private void savePart(MouseEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        
        //Grab whatever the text fields contain to validate them and modify the part Accordingly
        int pMin = Integer.parseInt(ModifyPartMax.getText());
        int pMax = Integer.parseInt(ModifyPartMin.getText());
        String pName = new String(ModifyPartName.getText());
        int pInv = Integer.parseInt(ModifyPartInv.getText());
        double pPrice = Double.parseDouble(ModifyPartCost.getText());
        String pType = ModifyPartVarField.getText();
        
        try {

            exMsg = Part.maxOverMin(pMin, pMax);
        
            if(exMsg.length() > 0){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Add Part Failed");
                alert.setContentText(exMsg);
                alert.showAndWait();
            } else {
                if(trueInHouse){
                    int machineID = Integer.parseInt(pType);
                    InHouse part = new InHouse(autoID, pName, pPrice, pInv, pMin, pMax, machineID);
                    Inventory.updatePart(autoID, part);
                    
                } else {
                    String companyName = new String(pType);
                    Outsourced part = new Outsourced(autoID, pName, pPrice, pInv, pMin, pMax, companyName);
                    Inventory.updatePart(autoID, part);
                }
            }
        
        } catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Add Part Failed");
            alert.setContentText("Part cannot be added, empty or invalid values in one or more fields");
            alert.showAndWait();
            
        }
        
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
