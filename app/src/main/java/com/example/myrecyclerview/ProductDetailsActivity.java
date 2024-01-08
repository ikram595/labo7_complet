package com.example.myrecyclerview;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

public class ProductDetailsActivity extends AppCompatActivity {
    private ImageView imageViewProduct;
    private TextView textViewProductName;
    private TextView textViewProductPrice;
    private TextView textViewProductPromo;
    private RatingBar ratingBar;
    private TextView nbRatings;
    private Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        // Initialize UI elements
        imageViewProduct = findViewById(R.id.imageViewProduct);
        textViewProductName = findViewById(R.id.textViewProductName);
        textViewProductPrice = findViewById(R.id.textViewProductPrice);
        textViewProductPromo = findViewById(R.id.textViewProductPromo);
        ratingBar = findViewById(R.id.ratingBar);
        nbRatings = findViewById(R.id.nb_ratings);

        // get product data from Intent
        Intent intent = getIntent();
        String productId = intent.getStringExtra("productId");

        // fetch product details from Firebase
        DatabaseReference productRef = FirebaseDatabase.getInstance().getReference("/products").child(productId);
        productRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Get product data from the snapshot
                    Product product = snapshot.getValue(Product.class);
                    assert product != null;
                    textViewProductName.setText(product.getName());
                    textViewProductPrice.setText(String.valueOf(product.getPrice_original()));
                    textViewProductPromo.setText(String.valueOf(product.getPromos()));
                    // product img
                    Glide.with(ProductDetailsActivity.this)
                            .load(product.getImageUrl())
                            .into(imageViewProduct);

                    // Set initial rating and number of ratings
                    ratingBar.setRating(product.getAverageRating());
                    nbRatings.setText("("+product.getNbRatings()+")");

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });

        // Set an OnRatingBarChangeListener for the RatingBar
        ratingBar.setOnRatingBarChangeListener((ratingBar1, rating, fromUser) -> {
            if (fromUser) {
                // Handle setting the rating for the product (e.g., update Firebase)
                updateProductRating(productId, rating);
            }
        });
    }
    private void updateProductRating(String productId, float rating) {
        // Implement logic to update the product's rating in Firebase or another data source
        // For example, update the average rating and number of ratings in the "products" node
        DatabaseReference productRef = FirebaseDatabase.getInstance().getReference("/products").child(productId);

        productRef.runTransaction(new Transaction.Handler() {
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                Product product = currentData.getValue(Product.class);

                if (product == null) {
                    // Product not found
                    return Transaction.success(currentData);
                }

                // Update average rating and number of ratings
                int numberOfRatings = product.getNbRatings() + 1;
                float newAverageRating = (product.getAverageRating() * product.getNbRatings() + rating) / numberOfRatings;

                product.setNbRatings(numberOfRatings);
                product.setAverageRating(newAverageRating);

                // Set the updated product data
                currentData.setValue(product);

                return Transaction.success(currentData);
            }

            @Override
            public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {
                if (committed) {
                    // Rating updated successfully
                    nbRatings.setText("Number of Ratings: " + product.getNbRatings());


                } else {
                    // Handle transaction failure
                }
            }
        });
    }
}