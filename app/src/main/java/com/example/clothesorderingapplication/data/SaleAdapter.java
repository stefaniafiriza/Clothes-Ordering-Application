package com.example.clothesorderingapplication.data;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.example.clothesorderingapplication.R;
import com.example.clothesorderingapplication.api.API;
import com.example.clothesorderingapplication.api.interfaces.ICallback;

import java.util.LinkedList;

public class SaleAdapter extends RecyclerView.Adapter<SaleAdapter.ViewHolder>{
    LinkedList<Pair<Product, Product>> productsList = new LinkedList<>();
    Context context;

    public SaleAdapter(LinkedList<Product> productsList, Context context){
        this.context = context;
        LinkedList<Product> products = new LinkedList<>();
        for(int i =0; i < productsList.size(); i++){
            Product p = productsList.get(i);
            if(!(p.getType().equals("belt") || p.getType().equals("bag") || p.getType().equals("scarf"))){
                products.add(p);
            }
        }
        for(int i =0; i < products.size(); i+=2){
            Pair<Product, Product> pair;
            if(i +1 < products.size()){
                // not the last one
                pair = new Pair<Product, Product>(products.get(i), products.get(i+1));

            }else {
                pair = new Pair<Product, Product>(products.get(i), null);

            }
            this.productsList.add(pair);
        }
    }
    @NonNull
    @Override
    public SaleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.item_accessory, parent, false);

        return new SaleAdapter.ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull SaleAdapter.ViewHolder holder, int position) {
        final Pair<Product, Product> p = this.productsList.get(position);
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

        holder.c1.setBackgroundResource(p.first.getImg_id());

        if(p.second != null){
            holder.price2.setText(p.second.getPrice() + "€");
            holder.addToCart2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    api.addToCart(User.logged_in_user.getShoppingCartID(), p.second.getId(), "1", callback);
                }
            });

            holder.c2.setBackgroundResource(p.second.getImg_id());
        }else{
            holder.addToCart2.setVisibility(View.INVISIBLE);
        }
    }


    @Override
    public int getItemCount() {
        return productsList.size();
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
