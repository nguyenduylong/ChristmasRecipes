package com.duylong.christmasrecipes.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.duylong.christmasrecipes.R;
import com.duylong.christmasrecipes.model.Recipe;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeHolder> {

    private Context context;

    private ArrayList<Recipe> recipeList;

    private LayoutInflater layoutInflater;

    private OnRecyclerClickListener itemClickListener;

    public RecipeAdapter(Context context, ArrayList<Recipe> recipeList, OnRecyclerClickListener itemClickListener) {
        this.context = context;
        this.recipeList = recipeList;
        this.layoutInflater = LayoutInflater.from(context);
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public RecipeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.recipe_item, parent, false);
        RecipeHolder viewHolder = new RecipeHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeAdapter.RecipeHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }


    public class RecipeHolder extends RecyclerView.ViewHolder{
        ImageView recipeImage;
        TextView recipeName;
        TextView recipeDescription;
        MaterialCardView itemCardView;

        public  RecipeHolder(View itemView) {
            super(itemView);
            recipeImage = (ImageView) itemView.findViewById(R.id.recipe_image);
            recipeName = (TextView) itemView.findViewById(R.id.recipe_name);
            recipeDescription = (TextView) itemView.findViewById(R.id.recipe_description);
            itemCardView = (MaterialCardView) itemView.findViewById(R.id.item_cardview);

            itemCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClickListener.onRecyclerViewItemClicked(getAdapterPosition(),view.getId());
                }
            });
        }

        public void bind(final int position) {
            Recipe recipe = recipeList.get(position);
            recipeName.setText(recipe.getName());
            recipeDescription.setText(recipe.getDescription());
            Glide.with(context).load(recipe.getImageUrl()).into(recipeImage);

        }
    }
}
