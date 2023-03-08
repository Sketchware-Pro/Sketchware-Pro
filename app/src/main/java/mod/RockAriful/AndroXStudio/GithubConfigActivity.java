package mod.RockAriful.AndroXStudio;

import android.Manifest;
import android.animation.*;
import android.app.*;
import android.content.*;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.net.Uri;
import android.os.*;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.View;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import androidx.annotation.*;

import com.sketchware.remod.R;
import mod.SketchwareUtil; 

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.github.angads25.filepicker.*;
import com.google.android.material.appbar.AppBarLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.text.*;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.*;

import org.json.*;

public class GithubConfigActivity extends AppCompatActivity {
	
	private Toolbar _toolbar;
	private AppBarLayout _app_bar;
	private CoordinatorLayout _coordinator;
	private HashMap<String, Object> map = new HashMap<>();
	
	private ArrayList<HashMap<String, Object>> JsonMAP = new ArrayList<>();
	
	private ScrollView vscroll1;
	private LinearLayout linear1;
	private LinearLayout linear2;
	private LinearLayout linear13;
	private LinearLayout all_item_layout;
	private LinearLayout linear14;
	private TextView create_token;
	private TextView textview1;
	private Switch enable;
	private ImageView imageview2;
	private TextView textview13;
	private LinearLayout is_url;
	private LinearLayout linear12;
	private EditText setRefSpecs;
	private LinearLayout credential_layout;
	private TextView textview7;
	private EditText url;
	private TextView textview11;
	private TextView important_btn;
	private TextView textview5;
	private EditText username;
	private TextView textview6;
	private EditText pass;

        private String sc_id ="";
	
	private Intent Ctoken = new Intent();
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.github_config);
		initialize(_savedInstanceState);
		
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
		|| ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
			ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
		} else {
			initializeLogic();
		}
	}
	
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == 1000) {
			initializeLogic();
		}
	}
	
	private void initialize(Bundle _savedInstanceState) {
		_app_bar = findViewById(R.id._app_bar);
		_coordinator = findViewById(R.id._coordinator);
		_toolbar = findViewById(R.id._toolbar);
		setSupportActionBar(_toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _v) {
				onBackPressed();
			}
		});
		vscroll1 = findViewById(R.id.vscroll1);
		linear1 = findViewById(R.id.linear1);
		linear2 = findViewById(R.id.linear2);
		linear13 = findViewById(R.id.linear13);
		all_item_layout = findViewById(R.id.all_item_layout);
		linear14 = findViewById(R.id.linear14);
		create_token = findViewById(R.id.create_token);
		textview1 = findViewById(R.id.textview1);
		enable = findViewById(R.id.enable);
		imageview2 = findViewById(R.id.imageview2);
		textview13 = findViewById(R.id.textview13);
		is_url = findViewById(R.id.is_url);
		linear12 = findViewById(R.id.linear12);
		setRefSpecs = findViewById(R.id.setRefSpecs);
		credential_layout = findViewById(R.id.credential_layout);
		textview7 = findViewById(R.id.textview7);
		url = findViewById(R.id.url);
		textview11 = findViewById(R.id.textview11);
		important_btn = findViewById(R.id.important_btn);
		textview5 = findViewById(R.id.textview5);
		username = findViewById(R.id.username);
		textview6 = findViewById(R.id.textview6);
		pass = findViewById(R.id.pass);
		
		create_token.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				Ctoken.setAction(Intent.ACTION_VIEW);
				Ctoken.setData(Uri.parse("https://github.com/settings/tokens/new"));
				startActivity(Ctoken);
			}
		});
		
		enable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton _param1, boolean _param2) {
				final boolean _isChecked = _param2;
				if (_isChecked) {
					map = new HashMap<>();
					map.put("repository", url.getText().toString());
					map.put("RefSpecs", setRefSpecs.getText().toString());
					map.put("username", username.getText().toString());
					map.put("token", pass.getText().toString());
					JsonMAP.add(map);
					FileUtil.writeFile(FileUtil.getExternalStorageDir()+ "/.sketchware/data/"+sc_id+"/github_config", new Gson().toJson(JsonMAP));
				}
				else {
					FileUtil.writeFile(FileUtil.getExternalStorageDir()+ "/.sketchware/data/"+sc_id+"/github_config", "[]");
				}
			}
		});
	}
	
	private void initializeLogic() {
		setTitle("Github Settings");
                sc_id = getIntent().getStringExtra("sc_id");

		if (FileUtil.isExistFile(FileUtil.getExternalStorageDir() +"/.sketchware/data/"+sc_id+"/github_config") && !FileUtil.readFile(FileUtil.getExternalStorageDir()+"/.sketchware/data/"+sc_id+"/github_config").equals("[]")) {
			JsonMAP = new Gson().fromJson(FileUtil.readFile(FileUtil.getExternalStorageDir()+"/.sketchware/data/"+sc_id+"/github_config"), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
			url.setText(JsonMAP.get((int)0).get("repository").toString());
			setRefSpecs.setText(JsonMAP.get((int)0).get("RefSpecs").toString());
			username.setText(JsonMAP.get((int)0).get("username").toString());
			pass.setText(JsonMAP.get((int)0).get("token").toString());
			enable.setChecked(true);
		}
		else {
			enable.setChecked(false);
			FileUtil.writeFile(FileUtil.getExternalStorageDir()+ "/.sketchware/data/"+sc_id+"/github_config", "[]");
		}
		_setBackground(create_token, 12, 2, "#E91E63", true);
	}
	
	public void _setBackground(final View _view, final double _radius, final double _shadow, final String _color, final boolean _ripple) {
		if (_ripple) {
			android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
			gd.setColor(Color.parseColor(_color));
			gd.setCornerRadius((int)_radius);
			_view.setElevation((int)_shadow);
			android.content.res.ColorStateList clrb = new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{Color.parseColor("#9e9e9e")});
			android.graphics.drawable.RippleDrawable ripdrb = new android.graphics.drawable.RippleDrawable(clrb , gd, null);
			_view.setClickable(true);
			_view.setBackground(ripdrb);
		}
		else {
			android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
			gd.setColor(Color.parseColor(_color));
			gd.setCornerRadius((int)_radius);
			_view.setBackground(gd);
			_view.setElevation((int)_shadow);
		}
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
