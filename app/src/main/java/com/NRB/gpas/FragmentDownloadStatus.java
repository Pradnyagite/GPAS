package com.NRB.gpas;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class FragmentDownloadStatus extends Fragment implements VisitorAdaptor.OnVisitorListener {
    private static final String URL_VISITORS = IPString.ip;
    List<VisitorInfo> visitorInfoList;
    RecyclerView recyclerView;
    Button b;
    TextView emptyView;
    private Spinner spinner1;
    private OnFragmentInteractionListener mListener;

    public FragmentDownloadStatus() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_download_status, container, false);
        b = v.findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createExcelSheet();
            }
        });
        recyclerView = v.findViewById(R.id.recyclerView4);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        spinner1 = v.findViewById(R.id.spinner);
        emptyView=v.findViewById(R.id.list_empty);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loadVisitors();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return v;
    }

    private void loadVisitors() {
        visitorInfoList = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_VISITORS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject visitor = array.getJSONObject(i);

                                String status = String.valueOf(spinner1.getSelectedItem());
                                if (visitor.getString("status").equals(status)) {
                                    //adding the product to product list
                                    visitorInfoList.add(new VisitorInfo(
                                            visitor.getInt("id"),
                                            visitor.getString("name"),
                                            visitor.getString("address"),
                                            visitor.getString("email"),
                                            visitor.getString("contact"),
                                            visitor.getString("Vehicle"),
                                            visitor.getString("org"),
                                            visitor.getString("vD"),
                                            visitor.getString("vT"),
                                            visitor.getString("leaveT"),
                                            visitor.getString("conernP"),
                                            visitor.getString("purpose"),
                                            visitor.getString("status"),
                                            visitor.getString("Start_Meet"),
                                            visitor.getString("Close_Meet")
                                    ));
                                }
                            }
                            //creating adapter object and setting it to recyclerview
                            VisitorAdaptor adapter = new VisitorAdaptor(visitorInfoList, FragmentDownloadStatus.this);
                            if(adapter.getItemCount() == 0) {
                                emptyView.setVisibility(View.VISIBLE);
                                recyclerView.setVisibility(View.GONE);
                            }
                            else {
                                emptyView.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.VISIBLE);
                                recyclerView.setAdapter(adapter);
                            }                         } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        Volley.newRequestQueue(getActivity()).add(stringRequest);
    }

    @Override
    public void onVisitorClick(int position) {
        VisitorInfo visitorInfo = visitorInfoList.get(position);
        VisitorCardDialog visitorCardDialog = new VisitorCardDialog();
        visitorCardDialog.getObject(visitorInfo);
        visitorCardDialog.show(getFragmentManager(), "Visitor info dialog");
    }

    public void createExcelSheet() {
        String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());
        String Fnamexls = "Visitor data " + currentDateTimeString + ".xls";
        File sdCard = Environment.getExternalStorageDirectory();
        File directory = new File(sdCard.getAbsolutePath() + "/Visitors");
        directory.mkdirs();
        File file = new File(directory, Fnamexls);

        WorkbookSettings wbSettings = new WorkbookSettings();

        wbSettings.setLocale(new Locale("en", "EN"));

        WritableWorkbook workbook;
        try {
            int a = 1;
            workbook = Workbook.createWorkbook(file, wbSettings);
            //workbook.createSheet("Report", 0);
            WritableFont titleFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, true);
            WritableCellFormat titleformat = new WritableCellFormat(titleFont);
            WritableSheet sheet = workbook.createSheet("First Sheet", 0);


            try {
                sheet.addCell(new Label(0, 0, "Name", titleformat));
                sheet.addCell(new Label(1, 0, "Address", titleformat));
                sheet.addCell(new Label(2, 0, "Email Id", titleformat));
                sheet.addCell(new Label(3, 0, "Contact No.", titleformat));
                sheet.addCell(new Label(4, 0, "Vehicle No.", titleformat));
                sheet.addCell(new Label(5, 0, "Organisation Name", titleformat));
                sheet.addCell(new Label(6, 0, "Visit Date", titleformat));
                sheet.addCell(new Label(7, 0, "Visit Time", titleformat));
                sheet.addCell(new Label(8, 0, "Leave Time", titleformat));
                sheet.addCell(new Label(9, 0, "Concern Person", titleformat));
                sheet.addCell(new Label(10, 0, "Purpose", titleformat));
                sheet.addCell(new Label(11, 0, "Status", titleformat));
                sheet.addCell(new Label(12, 0, "Campus Entry", titleformat));
                sheet.addCell(new Label(13, 0, "Campus Exit", titleformat));

                int nextRow = 1;
                Iterator i = visitorInfoList.iterator();
                while (i.hasNext()) {
                    VisitorInfo res = (VisitorInfo) i.next();
                    sheet.addCell(new Label(0, nextRow, res.getName()));
                    sheet.addCell(new Label(1, nextRow, res.getAddress()));
                    sheet.addCell(new Label(2, nextRow, res.getEmail()));
                    sheet.addCell(new Label(3, nextRow, res.getContact()));
                    sheet.addCell(new Label(4, nextRow, res.getVehicle()));
                    sheet.addCell(new Label(5, nextRow, res.getOrg()));
                    sheet.addCell(new Label(6, nextRow, res.getVDate()));
                    sheet.addCell(new Label(7, nextRow, res.getVTime()));
                    sheet.addCell(new Label(8, nextRow, res.getLTime()));
                    sheet.addCell(new Label(9, nextRow, res.getConcernPerson()));
                    sheet.addCell(new Label(10, nextRow, res.getPurpose()));
                    sheet.addCell(new Label(11, nextRow, res.getStatus()));
                    sheet.addCell(new Label(12, nextRow, res.getStartMeet()));
                    sheet.addCell(new Label(13, nextRow, res.getCloseMeet()));

                    nextRow++;
                }
            } catch (RowsExceededException e) {
                e.printStackTrace();
            } catch (WriteException e) {
                e.printStackTrace();
            }


            workbook.write();
            Toast.makeText(getContext(), "Data Downloaded to "+file, Toast.LENGTH_SHORT).show();

            try {
                workbook.close();
            } catch (WriteException e) {
                e.printStackTrace();
            }
            //createExcel(excelSheet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
