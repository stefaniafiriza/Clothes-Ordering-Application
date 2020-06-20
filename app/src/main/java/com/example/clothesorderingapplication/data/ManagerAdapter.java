package com.example.clothesorderingapplication.data;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.example.clothesorderingapplication.ManagerActivity;
import com.example.clothesorderingapplication.R;
import com.example.clothesorderingapplication.api.API;
import com.example.clothesorderingapplication.api.interfaces.ICallback;

import java.util.LinkedList;

public class ManagerAdapter extends RecyclerView.Adapter<ManagerAdapter.ViewHolder> {

    LinkedList<Pair<Product, Product>> productList = new LinkedList<>();
    Context context;

    public ManagerAdapter(LinkedList<Product> productList, Context context){
        this.context = context;

        for(int i =0; i < productList.size(); i+=2){
            Pair<Product, Product> pair;
            if(i +1 < productList.size()){
                // not the last one
                pair = new Pair<Product, Product>(productList.get(i), productList.get(i+1));

            }else {
                pair = new Pair<Product, Product>(productList.get(i), null);
            }
            this.productList.add(pair);
        }

    }

    @NonNull
    @Override
    public ManagerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.item_manager, parent, false);

        return new ManagerAdapter.ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull ManagerAdapter.ViewHolder holder, int position) {
        final Pair<Product, Product> p = this.productList.get(position);
        final API api = new API(this.context);
        final ICallback deleteCallback = new ICallback() {
            @Override
            public void onFinish(String response, Context context) {
                if(response.contains("error")){
                    Toast.makeText( context, "Could not delete from the database.", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText( context, "Deleted the item from the database.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(VolleyError error, Context context) {
                Toast.makeText( context, "Could not delete from the database.", Toast.LENGTH_SHORT).show();
            }
        };

        holder.remove1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                api.deleteProduct(p.first.getName(), deleteCallback);
            }
        });
        holder.c1.setBackgroundResource(p.first.getImg_id());

        holder.edit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit(p.first, api);
            }
        });

        if(p.second != null){
            holder.remove2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    api.deleteProduct(p.second.getName(), deleteCallback);
                }
            });
            holder.c2.setBackgroundResource(p.second.getImg_id());

            holder.edit2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    edit(p.second, api);
                }
            });

        }else{
            holder.remove2.setVisibility(View.INVISIBLE);
            holder.edit2.setVisibility(View.INVISIBLE);
        }
    }

    private static String id_edited;
    public void edit(Product p, final API api){
        ManagerActivity ma = (ManagerActivity) context;
        ma.setContentView(R.layout.edit_product);
        final EditText name, type, size, price, stock, desc;

        name = (EditText)ma.findViewById(R.id.name_edit);
        type = (EditText)ma.findViewById(R.id.type_edit);
        size = (EditText)ma.findViewById(R.id.size_edit);
        price = (EditText)ma.findViewById(R.id.price_edit);
        stock = (EditText)ma.findViewById(R.id.stock_edit);
        desc = (EditText)ma.findViewById(R.id.description_edit);
        name.setText(p.getName());
        type.setText(p.getType());
        size.setText(p.getSize());
        price.setText(p.getPrice());
        stock.setText(p.getStock());
        desc.setText(p.getDescription());
        id_edited = p.getId();

        Button save = ma.findViewById(R.id.save_man);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                api.editProduct(id_edited, name.getText().toString(), type.getText().toString(),
                        size.getText().toString(), price.getText().toString(), stock.getText().toString(),
                        desc.getText().toString(), new ICallback() {
                            @Override
                            public void onFinish(String response, Context context) {
                                if(response.contains("error")){
                                    Toast.makeText( context, "Could not modify the item on the database.", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText( context, "Modified the item in the database.", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onError(VolleyError error, Context context) {
                                Toast.makeText( context, "Could not modify the item on the database.", Toast.LENGTH_SHORT).show();
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public ImageView c1;
        public ImageView c2;
        public ImageButton remove1;
        public ImageButton remove2;
        public ImageButton edit1;
        public ImageButton edit2;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            c1 = (ImageView) itemView.findViewById(R.id.c1_man);
            c2 = (ImageView) itemView.findViewById(R.id.c2_man);
            remove1 = (ImageButton) itemView.findViewById(R.id.remove_man1);
            remove2 = (ImageButton) itemView.findViewById(R.id.remove_man2);
            edit1 = (ImageButton) itemView.findViewById(R.id.edit_man1);
            edit2 = (ImageButton) itemView.findViewById(R.id.edit_man2);
        }
    }
}
