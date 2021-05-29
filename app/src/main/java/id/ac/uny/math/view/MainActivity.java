package id.ac.uny.math.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import id.ac.uny.math.R;
import id.ac.uny.math.data.MhsEntity;
import id.ac.uny.math.data.MhsParcel;

import static id.ac.uny.math.MathApp.mathDatabase;

public class MainActivity extends AppCompatActivity {

    public static int CRUD_REQ = 222;

    LinearLayout linMain;
    FloatingActionButton btnAdd;
    MaterialButton btnSearch;
    TextInputEditText searchinput;
    Button btnDelete;
    MhsParcel mhsParcel;
    MhsEntity mhsEntity;
    List<MhsEntity> mhsEntityList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intiviews();
        initViewData();
        initaction();

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initViewData();
            }
        });
    }

    void updateView(MhsEntity mhsEntity) {
        for (int i = 0; i < linMain.getChildCount(); i++) {
            if (((ViewItemMhs) linMain.getChildAt(i)).getMhsEntity().getId() == mhsEntity.getId()) {
                ((ViewItemMhs) linMain.getChildAt(i)).setMhsEntity(mhsEntity);
                break;
            }
        }
    }

    void addViewData(MhsEntity mhsEntity) {
        ViewItemMhs viewItemMhs = new ViewItemMhs(this);
        viewItemMhs.setMhsEntity(mhsEntity);
        linMain.addView(viewItemMhs, 0);
    }

    void initViewData() {
        if (mathDatabase.getMhsDao().getMhs() == null) return;

        if(searchinput == null){
            mhsEntityList = mathDatabase.getMhsDao().getMhs();
        }
        else {
            mhsEntityList = mathDatabase.getMhsDao().getMhs(searchinput.getText().toString());
        }

        linMain.removeAllViews();
        for (int i = 0; i < mhsEntityList.size(); i++) {
            addViewData(mhsEntityList.get(i));
        }
    }

    void intiviews() {
        linMain = findViewById(R.id.linMain);
        btnAdd = findViewById(R.id.btnAdd);
        btnSearch = findViewById(R.id.btnSearch);
        searchinput = findViewById(R.id.search);
    }

    void initaction() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                startActivityForResult(intent, CRUD_REQ);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CRUD_REQ && resultCode == RESULT_OK) {
            MhsParcel mhsParcel = data.getParcelableExtra("mhsEntity");
            MhsEntity mhsEntity = mhsParcel.toEntity();

            boolean isNew = data.getBooleanExtra("isNew", false);

            if (isNew) {
                addViewData(mhsEntity);
            } else {
                updateView(mhsEntity);
            }
        }
    }
}