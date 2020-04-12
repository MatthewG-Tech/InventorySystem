/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;
import javafx.collections.*;
/**
 *
 * @author matthewguerra
 */
public class Product {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    
    public Product(int id, String name, double price, int stock, int min, int max){
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;

    }
    public void setId(int id){
        this.id = id;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setPrice(double price){
        this.price = price;
    }
    public void setStock(int stock){
        this.stock = stock;
    }
    public void setMin(int min){
        this.min = min;
    }
    public void setMax(int max){
        this.max = max;
    }
    public int getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public double getPrice(){
        return price;
    }
    public int getStock(){
        return stock;
    }
    public int getMin(){
        return min;
    }
    public int getMax(){
        return max;
    }
    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }
    public boolean deleteAssociatedPart(Part selectedAspart){
        int index = associatedParts.indexOf(selectedAspart);
        if(index != -1){
            associatedParts.remove(index);
            return true;
        }
        return false;
    }
    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }
    public boolean equal(Product compareProduct){
        if(this.id == compareProduct.id && this.name.equals(compareProduct.name) && this.price == compareProduct.price && this.stock == compareProduct.stock && this.min == compareProduct.min && this.max == compareProduct.max){
            return true;
        }
        return false;
    }
}
