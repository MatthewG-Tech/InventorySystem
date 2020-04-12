
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author matthewguerra
 */
public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    
    public static void addPart(Part newPart){
        allParts.add(newPart);
    }
    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }
    public static Part lookupPart(int partId){
        for(int i = 0; i<allParts.size(); i++){
            if(allParts.get(i).getId() == partId){
                return allParts.get(i);
            }
        }
        return null;
    }
    public static Product lookupProduct(int productId){
       for(int i = 0; i<allProducts.size(); i++){
            if(allProducts.get(i).getId() == productId){
                return allProducts.get(i);
            }
        }
        return null; 
    }
    public static ObservableList<Part> lookupPart(String partName){
        ObservableList<Part> temp = FXCollections.observableArrayList();
        for(int i = 0; i < allParts.size(); i++){
            if(allParts.get(i).getName().contains(partName)){
                temp.add(allParts.get(i));
            }
        }
        return temp;
    }
    public static ObservableList<Product> lookupProduct(String productName){
        ObservableList<Product> temp = FXCollections.observableArrayList();
        for(int i = 0; i < allProducts.size(); i++){
            if(allProducts.get(i).getName().contains(productName)){
                temp.add(allProducts.get(i));
            }
        }
        return temp;
    }
    public static void updatePart(int index, Part selectedPart){
        allParts.remove(index);
        allParts.add(index, selectedPart);
    }
    public static void updateProduct(int index, Product newProduct){
        allProducts.remove(index);
        allProducts.add(index, newProduct);
    }
    public static boolean deletePart(Part selectedPart){
        for(int i = 0; i < allParts.size(); i++){
            if(allParts.get(i).equals(selectedPart)){
                allParts.remove(i);
                return true;
            }
        }
        return false;
    }
    public static boolean deleteProduct(Product selectedProduct){
        for(int i = 0; i < allProducts.size(); i++){
            if(allProducts.get(i).equals(selectedProduct)){
                allProducts.remove(i);
                return true;
            }
        }
        return false;
    }
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }
    public static int getNextPartId(){
        if(allParts.size() == 0){
            return 1;
        }
        return allParts.get(allParts.size()-1).getId() + 1;
    }
    public static int getNextProductId(){
        if(allProducts.size() == 0){
            return 1;
        }
        return allProducts.get(allProducts.size()-1).getId() + 1;
    }
}
