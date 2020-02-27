package com.NRB.gpas;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class FragmentAdminAllAppointments extends Fragment{

//    private static final String URL_VISITORS = "http://192.168.43.229/android/scrpit.php";
//    List<VisitorInfo> visitorInfoList;
//    RecyclerView recyclerView;

    private OnFragmentInteractionListener mListener;

    public FragmentAdminAllAppointments() {
        // Required empty public constructor
    }


//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
////        loadVisitors();
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_admin_all_appointments, container, false);
//        recyclerView = v.findViewById(R.id.recyclerView);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        visitorInfoList = new ArrayList<>();
//        loadVisitors();

        BottomNavigationView bottomNav = v.findViewById(R.id.all_appointments_bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        //I added this if statement to keep the selected fragment when rotating the device
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new AllAppointmentsToday()).commit();
        }

        return v;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.nav_yesterday:
                            selectedFragment = new AllAppointmentsYesterday();
                            break;
                        case R.id.nav_today:
                            selectedFragment = new AllAppointmentsToday();
                            break;
                        case R.id.nav_tomorrow:
                            selectedFragment = new AllAppointmentsTomorrow();
                            break;
                    }

                    getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };

//    private void loadVisitors() {
//
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_VISITORS,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            //converting the string to json array object
//                            JSONArray array = new JSONArray(response);
//
//                            //traversing through all the object
//                            for (int i = 0; i < array.length(); i++) {
//
//                                //getting product object from json array
//                                JSONObject visitor = array.getJSONObject(i);
//
//                                //adding the product to product list
//                                visitorInfoList.add(new VisitorInfo(
//                                        visitor.getInt("id"),
//                                        visitor.getString("name"),
//                                        visitor.getString("address"),
//                                        visitor.getString("email"),
//                                        visitor.getString("contact"),
//                                        visitor.getString("gender"),
//                                        visitor.getString("org"),
//                                        visitor.getString("vD"),
//                                        visitor.getString("vT"),
//                                        visitor.getString("leaveT"),
//                                        visitor.getString("conernP"),
//                                        visitor.getString("purpose"),
//                                        visitor.getString("status")
//                                ));
//                            }
//
//                            //creating adapter object and setting it to recyclerview
//                            VisitorAdaptor adapter = new VisitorAdaptor(visitorInfoList);
//                            recyclerView.setAdapter(adapter);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                    }
//                });
//
//        //adding our stringrequest to queue
//        Volley.newRequestQueue(getActivity()).add(stringRequest);
//    }



    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
