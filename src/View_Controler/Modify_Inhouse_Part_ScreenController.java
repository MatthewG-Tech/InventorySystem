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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author matthewguerra
 */
public class Modify_Inhouse_Part_ScreenController implements Initializable {

    @FXML
    private Label modifyPartIdLabel;
    @FXML
    private TextField modifyPartNameField;
    @FXML
    private TextField modifyPartInventoryField;
    @FXML
    private TextField modifyPartPriceField;
    @FXML
    private TextField modifyPartMachineIdField;
    @FXML
    private TextField modifyPartMinField;
    @FXML
    private TextField modifyPartMaxField;
    @FXML
    private RadioButton inHouseButton;
    @FXML
    private RadioButton outsourcedButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Button saveButton;

    private static Part selectedPart;
    private static int selectedPartIndex;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inHouseButton.setSelected(true);
        outsourcedButton.setSelected(false);
    }    

    @FXML
    private void inHouseButtonAction(ActionEvent event){
        inHouseButton.setSelected(true);
        outsourcedButton.setSelected(false);
        System.out.println("Insource");
    }

    @FXML
    private void outsourcedButtonAction(ActionEvent event) throws IOException{
        inHouseButton.setSelected(false);
        outsourcedButton.setSelected(true);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Modify_Outsourced_Part_Screen.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene ((Pane) loader.load()));
        Modify_Outsourced_Part_ScreenController modifyOutsourcedController = loader.<Modify_Outsourced_Part_ScreenController>getController();
        modifyOutsourcedController.setUp(selectedPartIndex,selectedPart);
        stage.show();
        System.out.println("Outsourced");
    }

    @FXML
    private void cancelButtonAction(ActionEvent event) throws IOException{
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
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
    private void saveButtonAction(ActionEvent event) throws IOException{
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("Input Error");
        if(modifyPartNameField.getText().length() != 0 && modifyPartPriceField.getText().length() != 0 && modifyPartMinField.getText().length() != 0 && modifyPartMaxField.getText().length() != 0 && modifyPartMachineIdField.getText().length() != 0 && validInput()){
            int id= Integer.parseInt(modifyPartIdLabel.getText());
            String name = modifyPartNameField.getText();
            double price= Double.parseDouble(modifyPartPriceField.getText());
            int stock= Integer.parseInt(modifyPartInventoryField.getText());
            int min= Integer.parseInt(modifyPartMinField.getText());
            int max = Integer.parseInt(modifyPartMaxField.getText());
            int machineId = Integer.parseInt(modifyPartMachineIdField.getText());
            if(min>max){
                alert.setContentText("Your Min must be less than your Max.");
                alert.showAndWait();
            }else if(min>stock || stock>max){
                alert.setContentText("Your Inventory must be inbetween your Min and Max.");
                alert.showAndWait();
            }else{
                Part temp = new Inhouse_Part( id, name,  price,  stock,  min,  max,  machineId);
                Inventory.updatePart(selectedPartIndex, temp);
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
    public void setUp(int index, Part part){
        selectedPart = part;
        selectedPartIndex = index;
        modifyPartIdLabel.setText(selectedPart.getId()+"");
        modifyPartNameField.setText(selectedPart.getName()+"");
        modifyPartInventoryField.setText(selectedPart.getStock()+"");
        modifyPartPriceField.setText(selectedPart.getPrice()+"");
        if(part instanceof Inhouse_Part){
            Inhouse_Part temp = (Inhouse_Part) selectedPart;
            modifyPartMachineIdField.setText(temp.getMachineId() + "");      
        }else{
            modifyPartMachineIdField.setText("");
        }
        modifyPartMinField.setText(selectedPart.getMin()+"");
        modifyPartMaxField.setText(selectedPart.getMax()+"");
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
        if(isStringDouble(modifyPartPriceField.getText()) && isStringInt(modifyPartMinField.getText()) && isStringInt(modifyPartMaxField.getText()) && isStringInt(modifyPartMachineIdField.getText()) && (modifyPartInventoryField.getText().length() != 0 || isStringInt(modifyPartInventoryField.getText()))){
            return true;
        }
        return false;
    }
}
