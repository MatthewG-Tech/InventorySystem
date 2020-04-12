/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controler;

import Model.Inhouse_Part;
import Model.Inventory;
import Model.Part;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author matthewguerra
 */
public class Add_Inhouse_Part_ScreenController implements Initializable {
    
    @FXML
    private Label addPartIdLabel;
    @FXML
    private TextField addPartNameField;
    @FXML
    private TextField addPartInventoryField;
    @FXML
    private TextField addPartPriceField;
    @FXML
    private TextField addPartMachineIdField;
    @FXML
    private TextField addPartMinField;
    @FXML
    private TextField addPartMaxField;
    @FXML
    private RadioButton inHouseButton;
    @FXML
    private RadioButton outsourcedButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Button saveButton;
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inHouseButton.setSelected(true);
        outsourcedButton.setSelected(false);
        addPartIdLabel.setText(Inventory.getNextPartId()+"");
    }    

    @FXML
    private void inHouseButtonAction(ActionEvent event) {
        inHouseButton.setSelected(true);
        outsourcedButton.setSelected(false);
        System.out.println("Inhouse");
    }

    @FXML
    private void outsourcedButtonAction(ActionEvent event) throws IOException{
        inHouseButton.setSelected(false);
        outsourcedButton.setSelected(true);
        Parent modifyProductParent = FXMLLoader.load(getClass().getResource("Add_Outsourced_Part_Screen.fxml"));
        Scene modifyProductScreen = new Scene(modifyProductParent);
        Stage inventoryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        inventoryStage.setScene(modifyProductScreen);
        inventoryStage.show();
        System.out.println("Outsourced");
        
    }

    @FXML
    private void cancelButtonAction(ActionEvent event) throws IOException{
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Confirm Cancel");
        alert.setContentText("Are you sure you want to cancel?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Parent modifyProductParent = FXMLLoader.load(getClass().getResource("Main_Screen.fxml"));
            Scene modifyProductScreen = new Scene(modifyProductParent);
            Stage inventoryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            inventoryStage.setScene(modifyProductScreen);
            inventoryStage.show();
        }
        System.out.println("Cancel");
    }

    @FXML
    private void saveButtonAction(ActionEvent event) throws IOException {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("Input Error");
        if(addPartNameField.getText().length() != 0 && addPartPriceField.getText().length() != 0 && addPartMinField.getText().length() != 0 && addPartMaxField.getText().length() != 0 && addPartMachineIdField.getText().length() != 0 && validInput()){
            int id= Integer.parseInt(addPartIdLabel.getText());
            String name = addPartNameField.getText();
            double price= Double.parseDouble(addPartPriceField.getText());
            int stock = 0;
            if(addPartInventoryField.getText().length() != 0){
                stock = Integer.parseInt(addPartInventoryField.getText());
            }
            int min= Integer.parseInt(addPartMinField.getText());
            int max = Integer.parseInt(addPartMaxField.getText());
            int machineId = Integer.parseInt(addPartMachineIdField.getText());

            if(min>max){
                alert.setContentText("Your Min must be less than your Max.");
                alert.showAndWait();
            }else if(min>stock || stock>max){
                alert.setContentText("Your Inventory must be inbetween your Min and Max.");
                alert.showAndWait();
            }else{
                Part temp = new Inhouse_Part( id, name,  price,  stock,  min,  max,  machineId);
                Inventory.addPart(temp);
                Parent modifyProductParent = FXMLLoader.load(getClass().getResource("Main_Screen.fxml"));
                Scene modifyProductScreen = new Scene(modifyProductParent);
                Stage inventoryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                inventoryStage.setScene(modifyProductScreen);
                inventoryStage.show();
            } 
        }else{
            alert.setContentText("All fields must be filled out correctly. Ensure that all fields are filled and that strings are not used for items that requre a number.");
            alert.showAndWait();
        }
        System.out.println("Save");
    }
    private boolean isStringInt(String s){
        try{
            Integer.parseInt(s);
        }catch(NumberFormatException e ){
            return false;
        }
        return true;
    }
    private boolean isStringDouble(String s){
        try{
            Double.parseDouble(s);
        }catch(NumberFormatException e ){
            return false;
        }
        return true;
    }
    private boolean validInput(){
        if(isStringDouble(addPartPriceField.getText()) && isStringInt(addPartMinField.getText()) && isStringInt(addPartMaxField.getText()) && isStringInt(addPartMachineIdField.getText()) && (addPartInventoryField.getText().length() != 0 || isStringInt(addPartInventoryField.getText()))){
            return true;
        }
        return false;
    }
}
