package com.example.clothesorderingapplication.data;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.example.clothesorderingapplication.R;
import com.example.clothesorderingapplication.api.API;
import com.example.clothesorderingapplication.api.interfaces.ICallback;


import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

public class AccessoryAdapter extends RecyclerView.Adapter<AccessoryAdapter.ViewHolder> {

    LinkedList<Pair<Product, Product>> accessoryList = new LinkedList<>();
    Context context;

    public AccessoryAdapter(LinkedList<Product> productList, Context context){
        this.context = context;
        LinkedList<Product> accs = new LinkedList<>();
        for(int i =0; i < productList.size(); i++){
            Product p = productList.get(i);
            if(p.getType().equals("belt") || p.getType().equals("bag") || p.getType().equals("scarf")){
                accs.add(p);
            }

        }
        for(int i =0; i < accs.size(); i+=2){
            Pair<Product, Product> pair;
            if(i +1 < accs.size()){
                // not the last one
                pair = new Pair<Product, Product>(accs.get(i), accs.get(i+1));

            }else {
                pair = new Pair<Product, Product>(accs.get(i), null);

            }
            accessoryList.add(pair);
        }

    }

    @NonNull
    @Override
    public AccessoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.item_accessory, parent, false);

        return new AccessoryAdapter.ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull AccessoryAdapter.ViewHolder holder, int position) {
        final Pair<Product, Product> p = this.accessoryList.get(position);
        final API api = new API(this.context);
        final ICallback callback = new ICallback() {
            @Override
            public void onFinish(String response, Context context) {
                if(response.contains("error")){
                    Toast.makeText( context, "Could not add the item to the shopping basket.", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText( context, "Added the item to the shopping basket.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(VolleyError error, Context context) {
                Toast.makeText( context, "Could not add the item to the shopping basket.", Toast.LENGTH_SHORT).show();
            }
        };

        holder.price1.setText(p.first.getPrice() + "€");
        holder.addToCart1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                api.addToCart(User.logged_in_user.getShoppingCartID(), p.first.getId(), "1", callback);
            }
        });
        setImages(holder.c1,p.first);

        if(p.second != null){
            holder.price2.setText(p.second.getPrice() + "€");
            holder.addToCart2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    api.addToCart(User.logged_in_user.getShoppingCartID(), p.second.getId(), "1", callback);
                }
            });
            setImages(holder.c2,p.second);
        }else{
            holder.addToCart2.setVisibility(View.INVISIBLE);
        }


    }

    void setImages(ImageView iv, Product p){
        int id;
        if(p.getType().equals("belt")){
            id = getRandomId(new int[]{R.drawable.c1,R.drawable.c2,R.drawable.c3,R.drawable.c4,R.drawable.c5,R.drawable.c6});

        }else if(p.getType().equals("bag")){
            id = getRandomId(new int[]{R.drawable.g1,R.drawable.g2,R.drawable.g3,R.drawable.g4,R.drawable.g5,R.drawable.g6});
        }else{
            id = getRandomId(new int[]{R.drawable.e1,R.drawable.e2,R.drawable.e3,R.drawable.e4});
        }

        iv.setBackgroundResource(id);

    }

    int getRandomId(int[] array){
        int rnd = ThreadLocalRandom.current().nextInt(0, array.length);
        return array[rnd];
    }

    @Override
    public int getItemCount() {
        return accessoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public ImageView c1;
        public ImageView c2;
        public ImageButton addToCart1;
        public ImageButton addToCart2;
        public TextView price1;
        public TextView price2;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            c1 = (ImageView) itemView.findViewById(R.id.c1_acc);
            c2 = (ImageView) itemView.findViewById(R.id.c2_acc);
            price1 = (TextView) itemView.findViewById(R.id.price_acc1);
            price2 = (TextView) itemView.findViewById(R.id.price_acc2);
            addToCart1 = (ImageButton) itemView.findViewById(R.id.add_acc1);
            addToCart2 = (ImageButton) itemView.findViewById(R.id.add_acc2);
        }
    }
}
