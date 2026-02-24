package com.example.lab5_starter;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements CityDialogFragment.CityDialogListener {

    private Button addCityButton;
    private ListView cityListView;

    private ArrayList<City> cityArrayList;
    private ArrayAdapter<City> cityArrayAdapter;

    // Firestore instance
    private FirebaseFirestore db;
    private CollectionReference citiesRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Set views
        addCityButton = findViewById(R.id.buttonAddCity);
        cityListView = findViewById(R.id.listviewCities);

        // Create city array
        cityArrayList = new ArrayList<>();
        cityArrayAdapter = new CityArrayAdapter(this, cityArrayList);
        cityListView.setAdapter(cityArrayAdapter);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();
        citiesRef = db.collection("cities");

        // Snapshot listener — keeps list in sync with Firestore
        citiesRef.addSnapshotListener((querySnapshots, error) -> {
            if (error != null) {
                Log.e("Firestore", error.toString());
                return;
            }
            if (querySnapshots != null) {
                cityArrayList.clear();
                for (QueryDocumentSnapshot doc : querySnapshots) {
                    String name = doc.getString("city");
                    String province = doc.getString("province");
                    cityArrayList.add(new City(name, province));
                }
                cityArrayAdapter.notifyDataSetChanged();
            }
        });

        // Set listeners
        addCityButton.setOnClickListener(view -> {
            CityDialogFragment cityDialogFragment = new CityDialogFragment();
            cityDialogFragment.show(getSupportFragmentManager(), "Add City");
        });

        cityListView.setOnItemClickListener((adapterView, view, i, l) -> {
            City city = cityArrayAdapter.getItem(i);
            CityDialogFragment cityDialogFragment = CityDialogFragment.newInstance(city);
            cityDialogFragment.show(getSupportFragmentManager(), "City Details");
        });

        // Long click to delete
        cityListView.setOnItemLongClickListener((adapterView, view, i, l) -> {
            City city = cityArrayAdapter.getItem(i);
            deleteCity(city);
            return true;
        });
    }

    @Override
    public void updateCity(City city, String name, String province) {
        // Delete old document and add updated one
        citiesRef.document(city.getCityName()).delete();

        city.setName(name);
        city.setProvince(province);

        HashMap<String, String> data = new HashMap<>();
        data.put("city", city.getCityName());
        data.put("province", city.getProvince());

        citiesRef.document(city.getCityName())
                .set(data)
                .addOnSuccessListener(aVoid ->
                        Log.d("Firestore", "City updated successfully!"))
                .addOnFailureListener(e ->
                        Log.e("Firestore", "Error updating city: " + e));
    }

    @Override
    public void addCity(City city) {
        HashMap<String, String> data = new HashMap<>();
        data.put("city", city.getCityName());
        data.put("province", city.getProvince());

        citiesRef.document(city.getCityName())
                .set(data)
                .addOnSuccessListener(aVoid ->
                        Log.d("Firestore", "DocumentSnapshot successfully written!"))
                .addOnFailureListener(e ->
                        Log.e("Firestore", "Error adding city: " + e));
    }

    public void deleteCity(City city) {
        citiesRef.document(city.getCityName())
                .delete()
                .addOnSuccessListener(aVoid ->
                        Log.d("Firestore", "City successfully deleted!"))
                .addOnFailureListener(e ->
                        Log.e("Firestore", "Error deleting city: " + e));
    }
}














