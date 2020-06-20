package com.example.clothesorderingapplication.data;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.example.clothesorderingapplication.HomeActivity;
import com.example.clothesorderingapplication.R;
import com.example.clothesorderingapplication.ShopBasketActivity;
import com.example.clothesorderingapplication.api.API;
import com.example.clothesorderingapplication.api.interfaces.ICallback;

import java.util.LinkedList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    LinkedList<Product> productList;
    LinkedList<Long> amount;
    Context con;

    public ProductAdapter(LinkedList<Product> productList, LinkedList<Long> amount, Context con){
        this.productList = productList;
        this.con = con;
        this.amount = amount;
    }

    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.item_product_shopping_cart, parent, false);

        return new ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductAdapter.ViewHolder holder, final int position) {
        final Product product = productList.get(position);

        // Set item views based on your views and data model
        TextView textView = holder.nameTextView;
        textView.setText(product.getName());
        Button button = holder.deleteButton;
        button.setText("Delete");
        button.setEnabled(true);
        final API api = new API(this.con);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                api.removeFromCart(
                        User.logged_in_user.getShoppingCartID(),
                        product.getId(),
                        "1",
                        new ICallback() {
                            @Override
                            public void onFinish(String response, Context context) {
                                if(response.contains("error")){
                                    // error
                                    Toast.makeText( context, "Could not delete the item from the shopping basket.", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText( context, "Deleted the item from the shopping basket.", Toast.LENGTH_SHORT).show();
                                    removeAt(position);
                                }


                            }

                            @Override
                            public void onError(VolleyError error, Context context) {

                            }
                        }
                );
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.productList.size();
    }

    public void removeAt(int position){
        productList.remove(position);
        amount.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, productList.size());

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView nameTextView;
        public Button deleteButton;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            nameTextView =  itemView.findViewById(R.id.product_name);
            deleteButton =  itemView.findViewById(R.id.delete_button);
        }
    }
}
