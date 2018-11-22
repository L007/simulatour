package id.ac.unej.ilkom.simulatour.Activities;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.ac.unej.ilkom.simulatour.Adapters.PenginapanAdapter;
import id.ac.unej.ilkom.simulatour.Models.Penginapan;
import id.ac.unej.ilkom.simulatour.Models.Wisata;
import id.ac.unej.ilkom.simulatour.Models.mPenginapan;
import id.ac.unej.ilkom.simulatour.Networks.AppController;
import id.ac.unej.ilkom.simulatour.Networks.BaseApi;
import id.ac.unej.ilkom.simulatour.R;

public class PenginapanActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.titleMain)
    Button button;
    @BindView(R.id.listHome)
    ListView listView;

    private PenginapanAdapter adapter;
    private List<Penginapan> list;
    private String detail;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penginapan);
        ButterKnife.bind(this);
        button.setText("Penginapan");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                    finish();
                }
            });
        }
        list = new ArrayList<>();

        //list.add(new mPenginapan("1", "", "Judul1","10000"));
        adapter = new PenginapanAdapter(this, list);
        listView.setAdapter(adapter);
        getPenginapan();
    }
    public void getPenginapan() {
        pDialog = new ProgressDialog(this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();
        list.clear();


        // Creating volley request obj
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseApi.getAllPenginapan,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(String.valueOf(this), response.toString());
                        hidePDialog();
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray jsonArray = obj.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject objPenginapan = jsonArray.getJSONObject(i);
                                final Penginapan p = new Penginapan();
                                p.setId(objPenginapan.getString("id"));
                                p.setNama(objPenginapan.getString("nama"));
                                p.setAlamat(objPenginapan.getString("alamat"));
                                p.setKontak(objPenginapan.getString("kontak"));
                                p.setJenisPenginapan(objPenginapan.getString("jenis_penginapan"));
                                p.setFasilitas(objPenginapan.getString("fasilitas"));
                                p.setHarga(objPenginapan.getString("harga"));
                                p.setDeskripsi(objPenginapan.getString("deskripsi"));
                                p.setFoto(BaseApi.imageURL + objPenginapan.getString("foto"));
                                list.add(p);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(String.valueOf(this), "Error: " + error.getMessage());
                hidePDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("id_user", "1");
                map.put("token", "2b6898a282eece7bae4cdb706d4dcb1203433eee69d7ab317eaa081737ee5636");
                return map;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

}

