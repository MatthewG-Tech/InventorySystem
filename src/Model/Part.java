/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author matthewguerra
 */
public abstract class Part {
    int id;
    String name;
    double price;
    int stock;
    int min;
    int max;
    
    Part(int id, String name, double price, int stock, int min, int max){
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
    public boolean equal(Part comparePart){
        if(this.id == comparePart.id && this.name.equals(comparePart.name) && this.price == comparePart.price && this.stock == comparePart.stock && this.min == comparePart.min && this.max == comparePart.max){
            return true;
        }
        return false;
    }
}