package com.example.scannerapp;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;

public class ViewItems extends AppCompatActivity implements View.OnClickListener {



    FirebaseFirestore db;
    // creating variables for our array list,
    // dbhandler, adapter and recycler view.
    private ArrayList<ItemModal> itemModalArrayList;
    private RecyclerView itemsRV;
    MyAdapter  myAdapter;

    TextView tvScanContent, tvScanFormat, idtvItemName,idtvItemPrice,idtvItemCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item);

        // initializing our all variables.
        itemModalArrayList = new ArrayList<ItemModal>();
        tvScanContent = findViewById(R.id.tvScanContent);
        tvScanFormat = findViewById(R.id.tvScanFormat);
        idtvItemName = findViewById(R.id.tvItemName);
        idtvItemPrice = findViewById(R.id.tvItemPrice);
        idtvItemCode = findViewById(R.id.tvItemCode);
        db = FirebaseFirestore.getInstance();
        // on below line passing our array lost to our adapter class.
        itemsRV = findViewById(R.id.idRVItems);
        itemsRV.setHasFixedSize(true);
        itemsRV.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new MyAdapter(ViewItems.this,itemModalArrayList);
        itemsRV.setAdapter(myAdapter);
        EventChangeListener();


    }

    private void EventChangeListener() {
        db.collection("items").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null){

                    Log.e("Firestore error",error.getMessage());
                    return;
                }
                for (DocumentChange dc : value.getDocumentChanges()){
                    if (dc.getType() == DocumentChange.Type.ADDED){
                        itemModalArrayList.add(dc.getDocument().toObject(ItemModal.class));
                    }
                }
                myAdapter.notifyDataSetChanged();



            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onClick(View view) {

    }
}








