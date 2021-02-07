package com.example.todoreminder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, RecyclerViewAdapter.onRecyclerListener {
    private GoogleSignInClient mGoogleSignInClient;
    private RecyclerView recycler;
    private FirebaseFirestore firebasefirestore;
    private ArrayList<TODO> dataList;
    private RecyclerViewAdapter adapter;
    private CollectionReference collectionReference ;
    private DocumentReference mdocumentreference;





    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recycler = findViewById(R.id.recyclerView);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        firebasefirestore = FirebaseFirestore.getInstance();
        collectionReference = firebasefirestore.collection("TODO");
        mdocumentreference = collectionReference.document();

        FloatingActionButton addTodoActivity = findViewById(R.id.goOnAddingTodoActivity);


        addTodoActivity.setOnClickListener(this);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        loadData();

    }




    public void loadData(){
        firebasefirestore.collection("TODO").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments() ;
                        for (DocumentSnapshot d:list){
                            TODO obj = d.toObject(TODO.class);
                            dataList.add(obj);
                        }
                        adapter.notifyDataSetChanged();

                    }
                });
        dataList = new ArrayList<>();
        adapter = new RecyclerViewAdapter(dataList, this);
        recycler.setAdapter(adapter);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.logOut){
            Log.d("LogOut", "LogOut");
            FirebaseAuth.getInstance().signOut();
            mGoogleSignInClient.signOut();
            Intent logoutIntent = new Intent(this, LogInAtctivity.class);
            logoutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(logoutIntent);
            finish();

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.goOnAddingTodoActivity){
            Intent x = new Intent(this,AddingTodo.class);
            startActivity(x);

        } else if(i == R.id.addItem){
            loadData();
        }
    }



    @Override
    public void onRecyclerClick(int index) {
        Log.d("Clicked", "Clicked on recyclerView"  + index);
        AlertDialog();
    }
    private void AlertDialog(){
      TODO todo =  new TODO();
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Delete");


        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Delete",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("Clicked", "deleted");
                        dialog.dismiss();
                        Toast.makeText(MainActivity.this,"Todo removed successfully", Toast.LENGTH_LONG).show();
                        deleteData();
                        loadData();
                    }
                });
        alertDialog.show();

        Button btnPositive = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        Button btnNegative = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) btnPositive.getLayoutParams();
        layoutParams.weight = 10;
        btnPositive.setLayoutParams(layoutParams);
        btnNegative.setLayoutParams(layoutParams);


    }
    private void deleteData() {
        firebasefirestore.collection("TODO").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments() ;
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            TODO todo = documentSnapshot.toObject(TODO.class);
                            todo.setIde((documentSnapshot.getId()));
                            String id = todo.getIde();
                            firebasefirestore.collection("TODO").document(id).delete();
                        }


                    }
                });
        loadData();
    }

}




