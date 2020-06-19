package com.example.clothesorderingapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class ManagerActivity extends AppCompatActivity {

    protected ImageButton[] addBtn = new ImageButton[55];
    protected ImageButton[] removeBnt = new ImageButton[55];
    protected ArrayList<ImageButton> addButton = new ArrayList<>();
    protected ArrayList<ImageButton> removeButton = new ArrayList<>();
    protected MenuItem menuLogOut;
    protected Button managerItem, managerOrder;
    protected Button[] accept = new Button[6];
    protected ArrayList<Button> Accept = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

        managerItem = findViewById(R.id.manager_items);
        managerOrder = findViewById(R.id.manager_orders);

        managerItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.manager_items);

                addBtn[0] = findViewById(R.id.manager_add1);
                addBtn[1] = findViewById(R.id.manager_add2);
                addBtn[2] = findViewById(R.id.manager_add3);
                addBtn[3] = findViewById(R.id.manager_add4);
                addBtn[4] = findViewById(R.id.manager_add5);
                addBtn[5] = findViewById(R.id.manager_add6);
                addBtn[6] = findViewById(R.id.manager_add7);
                addBtn[7] = findViewById(R.id.manager_add8);
                addBtn[8] = findViewById(R.id.manager_add9);
                addBtn[9] = findViewById(R.id.manager_add10);
                addBtn[10] = findViewById(R.id.manager_add11);
                addBtn[11] = findViewById(R.id.manager_add12);
                addBtn[12] = findViewById(R.id.manager_add13);
                addBtn[13] = findViewById(R.id.manager_add14);
                addBtn[14] = findViewById(R.id.manager_add15);
                addBtn[15] = findViewById(R.id.manager_add16);
                addBtn[16] = findViewById(R.id.manager_add17);
                addBtn[17] = findViewById(R.id.manager_add18);
                addBtn[18] = findViewById(R.id.manager_add19);
                addBtn[19] = findViewById(R.id.manager_add20);
                addBtn[20] = findViewById(R.id.manager_add21);
                addBtn[21] = findViewById(R.id.manager_add22);
                addBtn[22] = findViewById(R.id.manager_add23);
                addBtn[23] = findViewById(R.id.manager_add24);
                addBtn[24] = findViewById(R.id.manager_add25);
                addBtn[25] = findViewById(R.id.manager_add26);
                addBtn[26] = findViewById(R.id.manager_add27);
                addBtn[27] = findViewById(R.id.manager_add28);
                addBtn[28] = findViewById(R.id.manager_add29);
                addBtn[29] = findViewById(R.id.manager_add30);
                addBtn[30] = findViewById(R.id.manager_add31);
                addBtn[31] = findViewById(R.id.manager_add32);
                addBtn[32] = findViewById(R.id.manager_add33);
                addBtn[33] = findViewById(R.id.manager_add34);
                addBtn[34] = findViewById(R.id.manager_add35);
                addBtn[35] = findViewById(R.id.manager_add36);
                addBtn[36] = findViewById(R.id.manager_add37);
                addBtn[37] = findViewById(R.id.manager_add38);
                addBtn[38] = findViewById(R.id.manager_add39);
                addBtn[39] = findViewById(R.id.manager_add40);
                addBtn[40] = findViewById(R.id.manager_add41);
                addBtn[41] = findViewById(R.id.manager_add42);
                addBtn[42] = findViewById(R.id.manager_add43);
                addBtn[43] = findViewById(R.id.manager_add44);
                addBtn[44] = findViewById(R.id.manager_add45);
                addBtn[45] = findViewById(R.id.manager_add46);
                addBtn[46] = findViewById(R.id.manager_add47);
                addBtn[47] = findViewById(R.id.manager_add48);
                addBtn[48] = findViewById(R.id.manager_add49);
                addBtn[49] = findViewById(R.id.manager_add50);
                addBtn[50] = findViewById(R.id.manager_add51);
                addBtn[51] = findViewById(R.id.manager_add52);
                addBtn[52] = findViewById(R.id.manager_add53);
                addBtn[53] = findViewById(R.id.manager_add54);
                addBtn[54] = findViewById(R.id.manager_add55);

                Collections.addAll(addButton, addBtn);

                for (ImageButton current : addButton) {
                    current.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(getApplicationContext(), "Another product has been added", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                removeBnt[0] = findViewById(R.id.manager_remove1);
                removeBnt[1] = findViewById(R.id.manager_remove2);
                removeBnt[2] = findViewById(R.id.manager_remove3);
                removeBnt[3] = findViewById(R.id.manager_remove4);
                removeBnt[4] = findViewById(R.id.manager_remove5);
                removeBnt[5] = findViewById(R.id.manager_remove6);
                removeBnt[6] = findViewById(R.id.manager_remove7);
                removeBnt[7] = findViewById(R.id.manager_remove8);
                removeBnt[8] = findViewById(R.id.manager_remove9);
                removeBnt[9] = findViewById(R.id.manager_remove10);
                removeBnt[10] = findViewById(R.id.manager_remove11);
                removeBnt[11] = findViewById(R.id.manager_remove12);
                removeBnt[12] = findViewById(R.id.manager_remove13);
                removeBnt[13] = findViewById(R.id.manager_remove14);
                removeBnt[14] = findViewById(R.id.manager_remove15);
                removeBnt[15] = findViewById(R.id.manager_remove16);
                removeBnt[16] = findViewById(R.id.manager_remove17);
                removeBnt[17] = findViewById(R.id.manager_remove18);
                removeBnt[18] = findViewById(R.id.manager_remove19);
                removeBnt[19] = findViewById(R.id.manager_remove20);
                removeBnt[20] = findViewById(R.id.manager_remove21);
                removeBnt[21] = findViewById(R.id.manager_remove22);
                removeBnt[22] = findViewById(R.id.manager_remove23);
                removeBnt[23] = findViewById(R.id.manager_remove24);
                removeBnt[24] = findViewById(R.id.manager_remove25);
                removeBnt[25] = findViewById(R.id.manager_remove26);
                removeBnt[26] = findViewById(R.id.manager_remove27);
                removeBnt[27] = findViewById(R.id.manager_remove28);
                removeBnt[28] = findViewById(R.id.manager_remove29);
                removeBnt[29] = findViewById(R.id.manager_remove30);
                removeBnt[30] = findViewById(R.id.manager_remove31);
                removeBnt[31] = findViewById(R.id.manager_remove32);
                removeBnt[32] = findViewById(R.id.manager_remove33);
                removeBnt[33] = findViewById(R.id.manager_remove34);
                removeBnt[34] = findViewById(R.id.manager_remove35);
                removeBnt[35] = findViewById(R.id.manager_remove36);
                removeBnt[36] = findViewById(R.id.manager_remove37);
                removeBnt[37] = findViewById(R.id.manager_remove38);
                removeBnt[38] = findViewById(R.id.manager_remove39);
                removeBnt[39] = findViewById(R.id.manager_remove40);
                removeBnt[40] = findViewById(R.id.manager_remove41);
                removeBnt[41] = findViewById(R.id.manager_remove42);
                removeBnt[42] = findViewById(R.id.manager_remove43);
                removeBnt[43] = findViewById(R.id.manager_remove44);
                removeBnt[44] = findViewById(R.id.manager_remove45);
                removeBnt[45] = findViewById(R.id.manager_remove46);
                removeBnt[46] = findViewById(R.id.manager_remove47);
                removeBnt[47] = findViewById(R.id.manager_remove48);
                removeBnt[48] = findViewById(R.id.manager_remove49);
                removeBnt[49] = findViewById(R.id.manager_remove50);
                removeBnt[50] = findViewById(R.id.manager_remove51);
                removeBnt[51] = findViewById(R.id.manager_remove52);
                removeBnt[52] = findViewById(R.id.manager_remove53);
                removeBnt[53] = findViewById(R.id.manager_remove54);
                removeBnt[54] = findViewById(R.id.manager_remove55);

                Collections.addAll(removeButton, removeBnt);

                for (ImageButton current : removeButton) {
                    current.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(getApplicationContext(), "A product has been removed", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        managerOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.manager_orders);

                accept[0] = findViewById(R.id.accept1);
                accept[1] = findViewById(R.id.accept2);
                accept[2] = findViewById(R.id.accept3);
                accept[3] = findViewById(R.id.accept4);
                accept[4] = findViewById(R.id.accept5);
                accept[5] = findViewById(R.id.accept6);

                Collections.addAll(Accept,accept);

                for(final Button current: accept){
                    current.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(ManagerActivity.this, "The order has been accepted", Toast.LENGTH_SHORT).show();
                            current.setVisibility(View.INVISIBLE);
                        }
                    });
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.manager_menu, menu);
        menuLogOut = menu.findItem(R.id.log_out);

        menuLogOut.setActionView(R.layout.log_out);
        menuLogOut.getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManagerActivity.this, LoginActivity.class));
                Toast.makeText(ManagerActivity.this, "Successful log out", Toast.LENGTH_SHORT).show();
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}
