package in.gov.cgg.redcrossphase1.ui_cgcitizen.home_distrcit;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import in.gov.cgg.redcrossphase1.R;

public class ShowDistrictMandal extends AppCompatActivity {

    RecyclerView recyclerView_top, recyclerView_bottom;
    DistrictAdapter adapter1, adapter2;

    List<DistrictAdapter> districtAdapterList;
    List<DistrictResponse> topdistrictResponseList = new ArrayList<>();
    List<DistrictResponse> bottomdistrictResponseList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showlist);

        recyclerView_top = findViewById(R.id.recyclerview_top);
        recyclerView_bottom = findViewById(R.id.recyclerview_bottom);

        recyclerView_top.setHasFixedSize(true);
        recyclerView_top.setLayoutManager(new LinearLayoutManager(ShowDistrictMandal.this));
        adapter1 = new DistrictAdapter(ShowDistrictMandal.this, topdistrictResponseList);
        recyclerView_top.setAdapter(adapter1);


        recyclerView_bottom.setHasFixedSize(true);
        recyclerView_bottom.setLayoutManager(new LinearLayoutManager(ShowDistrictMandal.this));
        adapter2 = new DistrictAdapter(ShowDistrictMandal.this, bottomdistrictResponseList);
        recyclerView_bottom.setAdapter(adapter2);

        DistrictViewModel model = ViewModelProviders.of(this).get(DistrictViewModel.class);

        topDistricts();
        bottomDtricts();

//        model.getHeroes().observe(this, new Observer<DistrictResponse>() {
//            @Override
//            public void onChanged(@Nullable DistrictResponse districtResponse) {
//                adapter = new DistrictAdapter(ShowDistrictMandal.this, districtResponseList);
//                recyclerView_top.setAdapter(adapter);
//            }
//        });

    }

    private void bottomDtricts() {

        DistrictResponse d1 = new DistrictResponse();
        d1.setDistrictName1("Medak");
        d1.setCount("30");
        DistrictResponse d2 = new DistrictResponse();
        d2.setDistrictName1("Jagityal");
        d2.setCount("28");
        DistrictResponse d3 = new DistrictResponse();
        d3.setDistrictName1("Mulugu");
        d3.setCount("41");
        DistrictResponse d4 = new DistrictResponse();
        d4.setDistrictName1("Nirmal");
        d4.setCount("21");
        DistrictResponse d5 = new DistrictResponse();
        d5.setDistrictName1("Siddipet");
        d5.setCount("31");

        bottomdistrictResponseList.add(0, d1);
        bottomdistrictResponseList.add(1, d2);
        bottomdistrictResponseList.add(2, d3);
        bottomdistrictResponseList.add(3, d4);
        bottomdistrictResponseList.add(4, d5);


    }

    private void topDistricts() {
        DistrictResponse d1 = new DistrictResponse();
        d1.setDistrictName1("Adilabad");
        d1.setCount("30");
        DistrictResponse d2 = new DistrictResponse();
        d2.setDistrictName1("Nizamabad");
        d2.setCount("28");
        DistrictResponse d3 = new DistrictResponse();
        d3.setDistrictName1("Nalgonda");
        d3.setCount("41");
        DistrictResponse d4 = new DistrictResponse();
        d4.setDistrictName1("Khammam");
        d4.setCount("21");
        DistrictResponse d5 = new DistrictResponse();
        d5.setDistrictName1("Karimnagar");
        d5.setCount("31");

        topdistrictResponseList.add(0, d1);
        topdistrictResponseList.add(1, d2);
        topdistrictResponseList.add(2, d3);
        topdistrictResponseList.add(3, d4);
        topdistrictResponseList.add(4, d5);

    }

}
