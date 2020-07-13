package com.duylong.christmasrecipes.constant;

import com.duylong.christmasrecipes.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FilterList {

    public static ArrayList<String> SKILLS = new ArrayList<String>(Arrays.asList("A challenge", "More effort", "Easy"));

    public static int[] SKILL_DRAWABLES = new int[] {R.drawable.challenge, R.drawable.moreeffort, R.drawable.easy};

    public static ArrayList<String> CALORIES = new ArrayList<String>(Arrays.asList("High", "Medium", "Low"));

    public static int[] CALORIE_DRAWABLES = new int[] {R.drawable.high, R.drawable.medium, R.drawable.low};

    public static ArrayList<String> KINDS = new ArrayList<String>(Arrays.asList("Candy", "Pizza", "Fruit", "Cake", "Ice Cream", "Salad", "Pudding"));

    public static int[] KIND_DRAWABLES = new int[] {R.drawable.candy, R.drawable.pizza, R.drawable.fruit, R.drawable.cake, R.drawable.icecream, R.drawable.salad, R.drawable.pudding};

    public static ArrayList<String> INGREDIENTS = new ArrayList<String>(Arrays.asList("potato", "peanut", "corn", "meat", "cherry", "cheese", "carrot", "broccoli", "banana",
            "avocado", "egg", "butter", "bread", "milk", "onion"));

    public static int[] INGREDIENT_DRAWABLES = new int[] { R.drawable.potato, R.drawable.peanut, R.drawable.corn, R.drawable.meat, R.drawable.cherries,
            R.drawable.cheese, R.drawable.carrot, R.drawable.broccoli, R.drawable.banana, R.drawable.avocado, R.drawable.egg, R.drawable.butter, R.drawable.bread,
            R.drawable.milk, R.drawable.onion };
}
