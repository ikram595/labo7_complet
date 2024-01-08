package com.example.myrecyclerview;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder>{
    private List<Product> productList;
    private Context context;

    public ProductAdapter(List<Product> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);

        // Bind data to views
        holder.productName.setText(product.getName());
        holder.productPrice.setText(String.valueOf(product.getPrice_original()));
        holder.productPromos.setText(String.valueOf(product.getPromos()));

        // Use Glide to load the image from the URL
        Glide.with(context)
                .load(product.getImageUrl())
                .into(holder.productImage);
        // Set the initial rating and number of ratings
        holder.ratingBar.setRating(product.getAverageRating());
        holder.nbRatings.setText(String.valueOf(product.getNbRatings()));

        // Set a listener to capture user ratings
        holder.ratingBar.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            if (fromUser) {
                // Update the Firebase database with the new rating
                updateRatingInFirebase(product, rating);
            }
        });
        holder.itemView.setOnClickListener(v -> {

            Product product123 = productList.get(position);
            Intent intent = new Intent(context, ProductDetailsActivity.class);
            intent.putExtra("productId", product123.getProductId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName;
        TextView productPrice;
        TextView productPromos;
        RatingBar ratingBar;
        TextView nbRatings;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.imageViewProduct);
            productName = itemView.findViewById(R.id.textViewProductName);
            productPrice = itemView.findViewById(R.id.textViewProductPrice);
            productPromos=itemView.findViewById(R.id.textViewProductPromo);
            ratingBar=itemView.findViewById(R.id.ratingBar);
            nbRatings=itemView.findViewById(R.id.nb_ratings);
        }
    }
    private void updateRatingInFirebase(Product product, float newRating) {
        DatabaseReference productRef = FirebaseDatabase.getInstance().getReference("/products")
                .child(product.getProductId());

        // Calculate the new average rating
        float currentRating = product.getAverageRating();
        int nbRatings = product.getNbRatings();

        float newAverageRating = ((currentRating * nbRatings) + newRating) / (nbRatings + 1);
        nbRatings++;

        // Update the product in Firebase
        productRef.child("averageRating").setValue(newAverageRating);
        productRef.child("nbRatings").setValue(nbRatings);
    }

}
