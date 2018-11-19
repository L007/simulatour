package id.ac.unej.ilkom.simulatour;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import id.ac.unej.ilkom.simulatour.Adapter.HomeAdapter;
import id.ac.unej.ilkom.simulatour.Adapter.PenginapanAdapter;
import id.ac.unej.ilkom.simulatour.Model.mPenginapan;

public class Penginapan extends AppCompatActivity {
    private ListView listView;
    private PenginapanAdapter adapter;
    private List<mPenginapan> list;
    private String detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penginapan);
        list = new ArrayList<>();
        listView = (ListView) findViewById(R.id.listHome);
        list.add(new mPenginapan("1", "", "Judul1","10000"));
        adapter = new PenginapanAdapter(getApplicationContext(), list);
        listView.setAdapter(adapter);
    }
}
