package com.togettech.kmerdelices.Carousel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.togettech.kmerdelices.R;

public class CarouselAdapter extends PagerAdapter {

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Object b = instanceItem(container, position);
        return b;
    }

    Context context;
    LayoutInflater layoutInflater;

    public CarouselAdapter(Context context) {

        this.context = context;
    }


    public int[] slide_images = {
            R.drawable.kmer,
            R.drawable.achat,
            R.drawable.shop,
            R.drawable.security
    };

    public String[] slide_headinds = {
            "TOUT SAVOIR",
            "ACHATS SIMPLE",
            "LIVRAISON",
            "SECURITE"

    };
    public String[] slide_descs = {

            "Découvrez les recettes des plats Camerounais les plus connus et la diversité du reste du monde",
            "Passez vos commandes en toute simplicité",
            "Profitez de notre nouveau service de livaisons à domicile, Nous sommes prèt de vous",
            "Traitement fiable et confidentiel, Vous êtes garanti dans toute vos opérations"
    };

    @Override
    public int getCount() {
        return slide_headinds.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    public Object instanceItem(ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.caroussel_view, container, false);

        ImageView slideImageView = view.findViewById(R.id.slide_image);
        TextView slideheading = view.findViewById(R.id.slide_heading);
        TextView slidescription = view.findViewById(R.id.slide_descs);
        slideImageView.setImageResource(slide_images[position]);
        slideheading.setText(slide_headinds[position]);
        slidescription.setText(slide_descs[position]);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }
}
