
package com.example.test.nuvoco3.lead;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.test.nuvoco3.R;
import com.example.test.nuvoco3.signup.ObjectSerializer;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.example.test.nuvoco3.signup.LoginActivity.DATABASE_URL;

public class InsertCustomerActivity extends AppCompatActivity {
    public static final String TAG = "Insert Customer Activity";
    String categoryPosition = null, areaPosition = null, districtPosition = null, statePosition = null;
    SearchableSpinner searchableSpinnerCategory, searchableSpinnerDistrict, searchableSpinnerArea, searchableSpinnerState, searchableSpinnerType;
    FloatingActionButton floatingActionButtonAddData;
    TextInputEditText editTextName, editTextAddress, editTextPhone, editTextEmail;
    String mName, mType, mCategory, mAddress, mArea, mDistrict, mState, mPhone, mEmail, mStatus, mCreatedOn, mCreatedBy, mUpdatedOn, mUpdatedBy;
    RequestQueue queue;
    String category[] = {"Default", "Dealer", "Subdealer", "Individual"};
    String area[] = {"Default", "Area 1", "Area 2", "Area 3", "Area 4"};
    String district[] = {"Default", "Mumbai", "Pune", "Aurangabad", "Nagpur"};
    String state[] = {"Default", "Maharashtra", "Gujrat", "Rajasthan", "Madhya Pradesh", "Chattissgarh"};
    String type[] = {"New", "Prospect"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_customer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        initializeViews();
        queue = Volley.newRequestQueue(this);
        floatingActionButtonAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
            }
        });
        initializeSpinners();
    }

    //  Populates the spinners
    private void initializeSpinners() {
        ArrayAdapter<String> categoryArrayAdapter = new ArrayAdapter<String>(InsertCustomerActivity.this, android.R.layout.simple_dropdown_item_1line, category);
        ArrayAdapter<String> areaArrayAdapter = new ArrayAdapter<String>(InsertCustomerActivity.this, android.R.layout.simple_dropdown_item_1line, area);
        ArrayAdapter<String> districtArrayAdapter = new ArrayAdapter<String>(InsertCustomerActivity.this, android.R.layout.simple_dropdown_item_1line, district);
        ArrayAdapter<String> stateArrayAdapter = new ArrayAdapter<String>(InsertCustomerActivity.this, android.R.layout.simple_dropdown_item_1line, state);
        ArrayAdapter<String> typeArrayAdapter = new ArrayAdapter<>(InsertCustomerActivity.this, android.R.layout.simple_list_item_1, type);
        searchableSpinnerCategory.setPositiveButton("Ok");
        searchableSpinnerCategory.setTitle("Select Item");
        searchableSpinnerCategory.setAdapter(categoryArrayAdapter);
        searchableSpinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mCategory = category[position];

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mCategory = "default";
            }
        });
        searchableSpinnerArea.setPositiveButton("Ok");
        searchableSpinnerArea.setTitle("Select Item");
        searchableSpinnerArea.setAdapter(areaArrayAdapter);
        searchableSpinnerArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mArea = area[position];

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mArea = "default";
            }
        });
        searchableSpinnerDistrict.setPositiveButton("Ok");
        searchableSpinnerDistrict.setTitle("Select Item");
        searchableSpinnerDistrict.setAdapter(districtArrayAdapter);
        searchableSpinnerDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mDistrict = district[position];

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mDistrict = "default";
            }
        });
        searchableSpinnerState.setPositiveButton("Ok");
        searchableSpinnerState.setTitle("Select Item");
        searchableSpinnerState.setAdapter(stateArrayAdapter);
        searchableSpinnerState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mState = state[position];

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mState = "default";
            }
        });
        searchableSpinnerType.setAdapter(typeArrayAdapter);
        searchableSpinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mType = type[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mType = "default";
            }
        });

    }

    //  initialize the views on the screen
    private void initializeViews() {
        floatingActionButtonAddData = findViewById(R.id.fabAddData);
        editTextName = findViewById(R.id.textInputEditName);
        editTextAddress = findViewById(R.id.textInputEditAddress);
        editTextPhone = findViewById(R.id.textInputEditPhone);
        editTextEmail = findViewById(R.id.textInputEditEmail);
        searchableSpinnerCategory = findViewById(R.id.searchCategory);
        searchableSpinnerArea = findViewById(R.id.searchArea);
        searchableSpinnerDistrict = findViewById(R.id.searchDistrict);
        searchableSpinnerState = findViewById(R.id.searchState);
        searchableSpinnerType = findViewById(R.id.searchType);

    }

    //  Function to provide current data and time
    private String getDateTime() {
//        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);
    }

    //Takes the Variable from Edit texts inside the string and starts storedata function to store data in the server
    public void validateData() {

        //I edit the prefs and add my string set and label it as "List"
        mName = editTextName.getText().toString();
        mAddress = editTextAddress.getText().toString();
        mPhone = editTextPhone.getText().toString().trim();
        mEmail = editTextEmail.getText().toString().trim();
        mStatus = "1";
        mCreatedOn = getDateTime();
        mCreatedBy = getCustomerId();
        mUpdatedOn = getDateTime();
        mUpdatedBy = getCustomerId();
        if (TextUtils.isEmpty(mName)) {
            editTextName.setError("Enter Name");
        }
        if (TextUtils.isEmpty(mAddress)) {
            editTextAddress.setError("Enter Address");
        }
        if (TextUtils.isEmpty(mPhone)) {
            editTextPhone.setError("Enter Phone Number");
        }
        if (TextUtils.isEmpty(mEmail)) {
            editTextEmail.setError("Enter Email Address");
        }
        if (TextUtils.equals(mArea, "default"))
            Toast.makeText(this, "Select Area", Toast.LENGTH_SHORT).show();
        if (TextUtils.equals(mDistrict, "default"))
            Toast.makeText(this, "Select Category", Toast.LENGTH_SHORT).show();
        if (TextUtils.equals(mState, "default"))
            Toast.makeText(this, "Select State", Toast.LENGTH_SHORT).show();
        if (TextUtils.equals(mType, "default"))
            Toast.makeText(this, "Select Type", Toast.LENGTH_SHORT).show();
        if (TextUtils.equals(mCategory, "default"))
            Toast.makeText(this, "Select Category", Toast.LENGTH_SHORT).show();
        if (!TextUtils.isEmpty(mName) && !TextUtils.isEmpty(mAddress) && !TextUtils.isEmpty(mPhone) && !TextUtils.isEmpty(mEmail) && !TextUtils.equals(mArea, "default") && !TextUtils.equals(mState, "default") && !TextUtils.equals(mDistrict, "default") && !TextUtils.equals(mType, "default") && !TextUtils.equals(mCategory, "default"))
        storeData();

    }

    //  Stores data to Server
    private void storeData() {


        Map<String, String> postParam = new HashMap<>();

//
        postParam.put("1", mName);
        postParam.put("2", mType);
        postParam.put("3", mCategory);
        postParam.put("4", mAddress);
        postParam.put("5", mArea);
        postParam.put("6", mDistrict);
        postParam.put("7", mState);
        postParam.put("8", mPhone);
        postParam.put("9", mEmail);
        postParam.put("10", mStatus);
        postParam.put("11", mCreatedOn);
        postParam.put("12", mCreatedBy);
        postParam.put("13", mUpdatedOn);
        postParam.put("14", mUpdatedBy);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, DATABASE_URL + "/insertCus", new JSONObject(postParam),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG, response.toString());
                        try {
                            if (response.getString("status").equals("updated")) {

                                Toast.makeText(InsertCustomerActivity.this, "Successfully Inserted data", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(InsertCustomerActivity.this, InsertCustomerContactActivity.class);
                                intent.putExtra("customerId", response.getString("allot_id"));
                                Log.i(TAG, "onResponse: " + response.getString("allot_id"));
                                intent.putExtra("customerName", mName);
                                intent.putExtra("createdBy", mCreatedBy);
                                intent.putExtra("createdOn", mCreatedOn);
                                intent.putExtra("updatedBy", mUpdatedBy);
                                intent.putExtra("updatedOn", mUpdatedOn);
                                intent.putExtra("email", mEmail);
                                startActivity(intent);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error with Connection: " + error.getMessage() + "lol");
            }
        }) {

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }


        };

        jsonObjReq.setTag("LOL");
        // Adding request to request queue
        queue.add(jsonObjReq);

    }

    private String getCustomerId() {
        ArrayList<String> newArralist = new ArrayList<>();
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.test.nuvoco3", Context.MODE_PRIVATE);

        try {
            newArralist = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("CustomerData", ObjectSerializer.serialize(new ArrayList<String>())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (newArralist.size() > 0)
            return newArralist.get(6);

        return "Invalid User";

    }


}
