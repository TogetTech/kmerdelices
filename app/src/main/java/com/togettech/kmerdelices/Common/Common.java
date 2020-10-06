package com.togettech.kmerdelices.Common;

import com.togettech.kmerdelices.Model.AddonModel;
import com.togettech.kmerdelices.Model.BoulangerieModel;
import com.togettech.kmerdelices.Model.CategoryModel;
import com.togettech.kmerdelices.Model.FoodModel;
import com.togettech.kmerdelices.Model.RestaurantModel;
import com.togettech.kmerdelices.Model.SizeModel;
import com.togettech.kmerdelices.Model.UserModel;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

public class Common {
    public static final String USER_REFERENCESER = "Users";
    public static final String POPULAR_CATEGORY_REF = "MostPopular";
    public static final String BEST_DEALS_REF = "BestDeals";

    public static final int DEFAULT_COLUMN_COUNT = 0;
    public static final int FULL_WIDTH_COLUMN = 1;

    public static final String CATEGORY_REF = "Category";
    public static final String COMMENT_REF = "Comments";



    public static UserModel currentUser;
    public static CategoryModel categorySelected;
    public static FoodModel selectedFood;

    public static final String RESTAURANT_REF = "Restaurant";
    public static final String COMMENT_RESTAURANT_REF = "CommentsRestaurant";


    public static final String BOULANGERIE_REF = "Boulangerie";

    public static RestaurantModel restaurantSelected;
    public static BoulangerieModel boulangerieSelected;
    public static UserModel userSelected;


    public static String formatPrice(double price){
        if (price != 0){
            DecimalFormat df = new DecimalFormat("#,##0.00");
            df.setRoundingMode(RoundingMode.UP);
            String finalPrice = new StringBuilder(df.format(price)).toString();
            return finalPrice.replace(".", ",");
        }
        else
            return "0,00";
    }

    public static Double calculateExtraPrice(SizeModel userSelectedSize, List<AddonModel> userSelectedAddon) {
        Double result = 0.0;
        if (userSelectedSize == null && userSelectedAddon == null)
            return 0.0;
        else if (userSelectedSize == null){
            for (AddonModel addonModel : userSelectedAddon)
                result+=addonModel.getPrice();
            return result;
        }else if (userSelectedAddon == null){
            return userSelectedSize.getPrice()*1.0;
        } else {
            result = userSelectedSize.getPrice()*1.0;
            for (AddonModel addonModel : userSelectedAddon)
                result+=addonModel.getPrice();
            return result;
        }
    }
}
