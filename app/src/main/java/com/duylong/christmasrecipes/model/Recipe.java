package com.duylong.christmasrecipes.model;

public class Recipe {
    private String name;
    private String imageUrl;
    private String skillLevel;
    private String description;
    private String nutrition;
    private String ingredients;
    private String methods;

    public Recipe(String name, String skillLevel) {
        this.name = name;
        this.skillLevel = skillLevel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(String skillLevel) {
        this.skillLevel = skillLevel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNutrition() {
        return nutrition;
    }

    public void setNutrition(String nutrition) {
        this.nutrition = nutrition;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getMethods() {
        return methods;
    }

    public void setMethods(String methods) {
        this.methods = methods;
    }
}
