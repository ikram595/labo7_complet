package com.example.myrecyclerview;

public class Product {
    private String productId;
    private String name;
    private double price_original;
    private double promos;
    private String imageUrl;
    private float averageRating;
    private int nbRatings;
    // Constructeurs,

    public Product(String productId, String name,double price_original, double promos,String imageUrl) {
        this.productId = productId;
        this.name = name;
        this.promos = promos;
        this.price_original=price_original;
        this.imageUrl = imageUrl;
    }
    public Product() {
    }
    //getters and setters
    public String getProductId() {
        return productId;
    }
    public void setProductId(String productId) {
        this.productId = productId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getPrice_original() {
        return price_original;
    }
    public void setPrice_original(double price_original) {
        this.price_original = price_original;
    }
    public double getPromos() {
        return promos;
    }
    public void setPromos(double promos) {
        this.promos = promos;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public float getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(float averageRating) {
        this.averageRating = averageRating;
    }

    public int getNbRatings() {
        return nbRatings;
    }

    public void setNbRatings(int nbRatings) {
        this.nbRatings = nbRatings;
    }
}
