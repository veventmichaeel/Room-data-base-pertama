package id.ac.uny.math.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import id.ac.uny.math.R;
import id.ac.uny.math.data.MhsEntity;
import id.ac.uny.math.data.MhsParcel;

import static id.ac.uny.math.MathApp.mathDatabase;

public class DeleteActivity extends AppCompatActivity {
    MhsParcel mhsParcel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        mhsParcel = getIntent().getParcelableExtra("mhsEntity");
        MhsEntity mhsEntity = mhsParcel.toEntity();

        mathDatabase.getMhsDao().delete(mhsEntity.getId());

        startActivity(new Intent(DeleteActivity.this, MainActivity.class));
        finish();

    }
}