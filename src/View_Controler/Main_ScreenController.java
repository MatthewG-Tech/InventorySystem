/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controler;

import Model.Inhouse_Part;
import Model.Inventory;
import Model.Part;
import Model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
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
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;



/**
 * FXML Controller class
 *
 * @author matthewguerra
 */
public class Main_ScreenController implements Initializable {

    @FXML
    private TableView<Part> partsTable;
    @FXML
    private TableColumn<Part, Integer> partIdColumn;
    @FXML
    private TableColumn<Part, String> partNameColumn;
    @FXML
    private TableColumn<Part, Integer> partInventoryLevelColumn;
    @FXML
    private TableColumn<Part, Double> pricePriceColumn;
    @FXML
    private TableView<Product> productTable;
    @FXML
    private TableColumn<Product, Integer> productIdColumn;
    @FXML
    private TableColumn<Product, String> productNameColumn;
    @FXML
    private TableColumn<Product, Integer> productInventoryColumn;
    @FXML
    private TableColumn<Product, Double> productPriceColumn;
    @FXML
    private TextField partSearchField;
    @FXML
    private TextField productSeachField;
    @FXML
    private Button partSearchButton;
    @FXML
    private Button partDeleteButton;
    @FXML
    private Button partModifyButton;
    @FXML
    private Button partAddButton;
    @FXML
    private Button productSeachButton;
    @FXML
    private Button productDeleteButton;
    @FXML
    private Button productModifyButton;
    @FXML
    private Button productAddButton;
    @FXML
    private Button mainExitButton;
    
    private static int partToBeModifiedIndex;
    private static Part partToBeModified;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        pricePriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        partsTable.setItems(Inventory.getAllParts());
        
        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        productTable.setItems(Inventory.getAllProducts());
        
    }    

    @FXML
    private void mainExitButtonAction(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Confirm Exit");
        alert.setContentText("Are you sure you want to exit?");

        Optional<ButtonType> result = alert.showAndWait();
        System.out.println("Main Exit");
        if (result.get() == ButtonType.OK){
            
            Platform.exit();
        }
    }

    @FXML
    private void partSearchButtonAction(ActionEvent event) {
        String search = partSearchField.getText();
        if(search.equals("")){
            partsTable.setItems(Inventory.getAllParts());
        }else if(isStringInt(search)){
            ObservableList<Part> temp = FXCollections.observableArrayList();
            temp.add(Inventory.lookupPart(Integer.parseInt(search)));
            partsTable.setItems(temp);
        }else{
            partsTable.setItems(Inventory.lookupPart(search));
        }
        
        System.out.println("Part Search");
    }

    @FXML
    private void productSearchButtonAction(ActionEvent event) {
        String search = productSeachField.getText();
        if(search.equals("")){
            productTable.setItems(Inventory.getAllProducts());
        
        }else if(isStringInt(search)){
            ObservableList<Product> temp = FXCollections.observableArrayList();
            temp.add(Inventory.lookupProduct(Integer.parseInt(search)));
            productTable.setItems(temp);
        }else{
            productTable.setItems(Inventory.lookupProduct(search));
        }
        System.out.println("Product Search");
    }

    @FXML
    private void partDeleteButtonAction(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Confirm Delete");
        alert.setContentText("Are you sure you want to delete?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Inventory.deletePart(partsTable.getSelectionModel().getSelectedItem());
            partsTable.setItems(Inventory.getAllParts());
        }
        
        System.out.println("Part Delete");
    }

    @FXML
    private void partModifyButtonAction(ActionEvent event) throws IOException{
        Part temp = partsTable.getSelectionModel().getSelectedItem();
        Parent modifyProductParent;
        
        if(temp != null){
            if(temp instanceof Inhouse_Part){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Modify_Inhouse_Part_Screen.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene ((Pane) loader.load()));
                Modify_Inhouse_Part_ScreenController modifyInhouseController = loader.<Modify_Inhouse_Part_ScreenController>getController();
                modifyInhouseController.setUp(partToBeModifiedIndex,temp);
                stage.show();
            }else{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Modify_Outsourced_Part_Screen.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene ((Pane) loader.load()));
                Modify_Outsourced_Part_ScreenController modifyOutsourcedController = loader.<Modify_Outsourced_Part_ScreenController>getController();
                modifyOutsourcedController.setUp(partToBeModifiedIndex,temp);
                stage.show();
            }
        }
        System.out.println("Part Modify");
    }

    @FXML
    private void partAddButtonAction(ActionEvent event) throws IOException{
        Parent modifyProductParent = FXMLLoader.load(getClass().getResource("Add_Inhouse_Part_Screen.fxml"));
        Scene modifyProductScreen = new Scene(modifyProductParent);
        Stage inventoryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        inventoryStage.setScene(modifyProductScreen);
        inventoryStage.show();
        System.out.println("Part Add");
    }

    @FXML
    private void productDeleteButtonAction(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Confirm Delete");
        alert.setContentText("Are you sure you want to delete?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Inventory.deleteProduct(productTable.getSelectionModel().getSelectedItem());
            productTable.setItems(Inventory.getAllProducts());
        }
        System.out.println("Product Delete");
    }

    @FXML
    private void productModifyButtonAction(ActionEvent event) throws IOException{
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        int selectedProductIndex = productTable.getSelectionModel().getSelectedIndex();
        Parent modifyProductParent;
        if(selectedProduct != null){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Modify_Product_Screen.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene ((Pane) loader.load()));
            Modify_Product_ScreenController modifyProductController = loader.<Modify_Product_ScreenController>getController();
            modifyProductController.setUp(selectedProductIndex,selectedProduct);
            stage.show();
        }
        System.out.println("Product Modify");
    }

    @FXML
    private void productAddButtonAction(ActionEvent event) throws IOException{
        Parent modifyProductParent = FXMLLoader.load(getClass().getResource("Add_Product_Screen.fxml"));
        Scene modifyProductScreen = new Scene(modifyProductParent);
        Stage inventoryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        inventoryStage.setScene(modifyProductScreen);
        inventoryStage.show();
        System.out.println("Product Add");
    }
    private boolean isStringInt(String s){
        try{
            Integer.parseInt(s);
        }catch(NumberFormatException e ){
            return false;
        }
        return true;
    }

}
