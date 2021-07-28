package come.besome.sketch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.*;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.appbar.AppBarLayout;
import android.app.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.content.*;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.webkit.*;
import android.animation.*;
import android.view.animation.*;
import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.text.*;
import org.json.*;
import java.util.HashMap;
import android.widget.LinearLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ScrollView;
import com.google.android.material.button.*;
import android.view.View;
import com.google.gson.Gson;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;


public class Debug4Activity extends AppCompatActivity {


    private Toolbar _toolbar;
    private AppBarLayout _app_bar;
    private CoordinatorLayout _coordinator;
    private HashMap<String, Object> m = new HashMap<>();
    private HashMap<String, Object> m1 = new HashMap<>();
    private HashMap<String, Object> m3 = new HashMap<>();

    private LinearLayout linear1;
    private ImageView imageview1;
    private TextView textview3;
    private TextView textview2;
    private ScrollView vscroll1;
    private LinearLayout linear2;
    private TextView textview1;
    private MaterialButton materialbutton1;
    private MaterialButton materialbutton2;

    private RequestNetwork rn;
    private RequestNetwork.RequestListener _rn_request_listener;

    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.debug4);
        initialize(_savedInstanceState);

        initializeLogic();
    }

    private void initialize(Bundle _savedInstanceState) {

        _app_bar = (AppBarLayout) findViewById(R.id._app_bar);
        _coordinator = (CoordinatorLayout) findViewById(R.id._coordinator);
        _toolbar = (Toolbar) findViewById(R.id._toolbar);
        setSupportActionBar(_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        _toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _v) {
                onBackPressed();
            }
        });
        linear1 = findViewById(R.id.linear1);
        imageview1 = findViewById(R.id.imageview1);
        textview3 = findViewById(R.id.textview3);
        textview2 = findViewById(R.id.textview2);
        vscroll1 = findViewById(R.id.vscroll1);
        linear2 = findViewById(R.id.linear2);
        textview1 = findViewById(R.id.textview1);
        materialbutton1 = findViewById(R.id.materialbutton1);
        materialbutton2 = findViewById(R.id.materialbutton2);
        rn = new RequestNetwork(this);

        materialbutton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                m = new HashMap<>();
                m.put("Device", textview3.getText().toString());
                m.put("Error", textview1.getText().toString());
                m1 = new HashMap<>();
                m1.put("Content-Type", "application/json");
                m3 = new HashMap<>();
                m3.put("json", new Gson().toJson(m));
                rn.setParams(m3, RequestNetworkController.REQUEST_BODY);
                rn.setHeaders(m1);
                rn.startRequestNetwork(RequestNetworkController.POST, "https://sheetdb.io/api/v1/wlitoriamt1iy/import/json", "a", _rn_request_listener);
                finishAffinity();
            }
        });

        materialbutton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                finishAffinity();
            }
        });

        _rn_request_listener = new RequestNetwork.RequestListener() {
            @Override
            public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
                final String _tag = _param1;
                final String _response = _param2;
                final HashMap<String, Object> _responseHeaders = _param3;

            }

            @Override
            public void onErrorResponse(String _param1, String _param2) {
                final String _tag = _param1;
                final String _message = _param2;

            }
        };
    }

    private void initializeLogic() {
        setTitle("An Error Occured");
        textview1.setText(getIntent().getStringExtra("error"));
        textview3.setText("Device : ".concat(Build.BRAND.concat(" ".concat(Build.MANUFACTURER.concat(" ".concat(Build.MODEL))))));
    }

    @Override
    protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
        super.onActivityResult(_requestCode, _resultCode, _data);

        switch (_requestCode) {

            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
    @Deprecated
    public void showMessage(String _s) {
        Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
    }

    @Deprecated
    public int getLocationX(View _v) {
        int _location[] = new int[2];
        _v.getLocationInWindow(_location);
        return _location[0];
    }

    @Deprecated
    public int getLocationY(View _v) {
        int _location[] = new int[2];
        _v.getLocationInWindow(_location);
        return _location[1];
    }

    @Deprecated
    public int getRandom(int _min, int _max) {
        Random random = new Random();
        return random.nextInt(_max - _min + 1) + _min;
    }

    @Deprecated
    public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
        ArrayList<Double> _result = new ArrayList<Double>();
        SparseBooleanArray _arr = _list.getCheckedItemPositions();
        for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
            if (_arr.valueAt(_iIdx))
                _result.add((double)_arr.keyAt(_iIdx));
        }
        return _result;
    }

    @Deprecated
    public float getDip(int _input) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
    }

    @Deprecated
    public int getDisplayWidthPixels() {
        return getResources().getDisplayMetrics().widthPixels;
    }

    @Deprecated
    public int getDisplayHeightPixels() {
        return getResources().getDisplayMetrics().heightPixels;
    }
}