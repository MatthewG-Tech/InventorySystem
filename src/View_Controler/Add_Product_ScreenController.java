/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controler;

import Model.Inventory;
import Model.Part;
import Model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


/**
 * FXML Controller class
 *
 * @author matthewguerra
 */
public class Add_Product_ScreenController implements Initializable {

    @FXML
    private TableView<Part> partSearchTabel;
    @FXML
    private TableColumn<Part, Integer> partSearchIdColumn;
    @FXML
    private TableColumn<Part, String> partSearchNameColumn;
    @FXML
    private TableColumn<Part, Integer> partSearchInventoryColumn;
    @FXML
    private TableColumn<Part, Double> partSearchPriceColumn;
    @FXML
    private TableView<Part> partIncludeTabel;
    @FXML
    private TableColumn<Part, Integer> partIncludedIdColumn;
    @FXML
    private TableColumn<Part, String> partIncludedNameColumn;
    @FXML
    private TableColumn<Part, Integer> partIncludeInventoryColumn;
    @FXML
    private TableColumn<Part, Double> partIncludePriceColumn;
    @FXML
    private TextField addProductSeachField;
    @FXML
    private Label addProductIdLabel;
    @FXML
    private TextField addProductNameField;
    @FXML
    private TextField addProductInventoryField;
    @FXML
    private TextField addProductPriceField;
    @FXML
    private TextField addProductMinField;
    @FXML
    private TextField addProductMaxField;
    @FXML
    private Button partAddButton;
    @FXML
    private Button partDeleteButton;
    @FXML
    private Button productCancelButton;
    @FXML
    private Button productSaveButton;
    @FXML
    private Button addProductSeachButton;
    
    private ObservableList<Part> partInclude = FXCollections.observableArrayList();
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        partSearchIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partSearchNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partSearchInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partSearchPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        partIncludedIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partIncludedNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partIncludeInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partIncludePriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        addProductIdLabel.setText(Inventory.getNextProductId()+"");
        
        partSearchTabel.setItems(Inventory.getAllParts());
        partIncludeTabel.setItems(partInclude);
    }    

    @FXML
    private void addProductSeachButtonAction(ActionEvent event) {
        String search = addProductSeachField.getText();
        if(search.equals("")){
            partSearchTabel.setItems(Inventory.getAllParts());
        }else if(isStringInt(search)){
            ObservableList<Part> temp = FXCollections.observableArrayList();
            temp.add(Inventory.lookupPart(Integer.parseInt(search)));
            partSearchTabel.setItems(temp);
        }else{
            partSearchTabel.setItems(Inventory.lookupPart(search));
        }
        System.out.println("Part Search");
    }

    @FXML
    private void partAddButtonAction(ActionEvent event) {
        Part temp = partSearchTabel.getSelectionModel().getSelectedItem();
        System.out.println(temp);
        if(temp != null){
            partInclude.add(temp);
        }
    }

    @FXML
    private void partDeleteButtonAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Confirm Delete");
        alert.setContentText("Are you sure you want to delete?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Part temp = partIncludeTabel.getSelectionModel().getSelectedItem();
            for(int i = 0; i < partInclude.size(); i++){
                System.out.println("ID: " +partInclude.get(i).getId());
                if(partInclude.get(i).getId()==temp.getId()){

                    partInclude.remove(i);
                    i = partInclude.size();
                }
            }
        }
        System.out.println("Delete");
    }

    @FXML
    private void productCancelButtonAction(ActionEvent event) throws IOException{
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
    private void productSaveButtonAction(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("Input Error");
        if(addProductNameField.getText().length() != 0 && addProductPriceField.getText().length() != 0 && addProductMinField.getText().length() != 0 && addProductMaxField.getText().length() != 0 && validInput()){
            int id= Integer.parseInt(addProductIdLabel.getText());
            String name = addProductNameField.getText();
            double price= Double.parseDouble(addProductPriceField.getText());
            int stock = 0;
            if(addProductInventoryField.getText().length() != 0){
                stock = Integer.parseInt(addProductInventoryField.getText());
            }
            int min= Integer.parseInt(addProductMinField.getText());
            int max = Integer.parseInt(addProductMaxField.getText());
            Product temp = new Product(id, name,  price,  stock,  min,  max);
            double allPartPrice = 0;
            for(int i = 0; i < partInclude.size(); i++){
                System.out.println(partInclude.get(i));
                temp.addAssociatedPart(partInclude.get(i));
                allPartPrice += partInclude.get(i).getPrice();
            }
            if(min>max){
                alert.setContentText("Your Min must be less than your Max.");
                alert.showAndWait();
            }else if(min>stock || stock>max){
                alert.setContentText("Your Inventory must be inbetween your Min and Max.");
                alert.showAndWait();
            }else if(temp.getAllAssociatedParts().size() == 0){
                alert.setContentText("Your Product must include at least one Part.");
                alert.showAndWait();
            }else if(price<allPartPrice){
                alert.setContentText("Your Product price must greater than or equal to the sum of all Part prices.");
                alert.showAndWait();
            }else{
                Inventory.addProduct(temp);
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
        if(isStringDouble(addProductPriceField.getText()) && isStringInt(addProductMinField.getText()) && isStringInt(addProductMaxField.getText()) && (addProductInventoryField.getText().length() != 0 || isStringInt(addProductInventoryField.getText()))){
            return true;
        }
        return false;
    }
}
