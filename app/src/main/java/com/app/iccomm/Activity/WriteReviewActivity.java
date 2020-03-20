package com.app.iccomm.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.EditText;

import com.app.iccomm.Adapters.WriteReviewAdapter;
import com.app.iccomm.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class WriteReviewActivity extends AppCompatActivity {
    EditText ed_reviewHeading, ed_reviewText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_review);
        ed_reviewHeading = findViewById(R.id.ed_reviewHeading);
        ed_reviewText = findViewById(R.id.ed_reviewText);


        Date presentTime_Date = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        dateFormat.format(presentTime_Date);

        Log.d("date:", dateFormat.format(presentTime_Date));

    }


}
