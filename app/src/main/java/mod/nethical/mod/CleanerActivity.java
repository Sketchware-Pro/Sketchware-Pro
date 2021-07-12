//Gg
package mod.nethical.mod;

import android.app.Activity;
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
import java.util.*;
import java.util.regex.*;
import java.text.*;
import org.json.*;
import java.util.ArrayList;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Button;
import java.util.Timer;
import java.util.TimerTask;
import android.view.View;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.DialogFragment;
import android.Manifest;
import android.content.pm.PackageManager;


public class CleanerActivity extends  Activity { 
	
	private Timer _timer = new Timer();
	
	private double temp2 = 0;
	private double temp = 0;
	private boolean cashe = false;
	private String filepath = "";
	private String tmp = "";
	private double cachesize = 0;
	
	private ArrayList<String> tempPath = new ArrayList<>();
	private ArrayList<String> cacheFiles = new ArrayList<>();
	private ArrayList<String> temppat2 = new ArrayList<>();
	
	private LinearLayout linear1;
	private ProgressBar progressbar;
	private LinearLayout linear3;
	private LinearLayout linear2;
	private TextView title;
	private TextView cacheamount;
	private LinearLayout linear4;
	private Button clean;
	
	private TimerTask tim;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.cleaner);
		initialize(_savedInstanceState);
		if (Build.VERSION.SDK_INT >= 23) {
			if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
			|| checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
				requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
			}
			else {
				initializeLogic();
			}
		}
		else {
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
		
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		progressbar = (ProgressBar) findViewById(R.id.progressbar);
		linear3 = (LinearLayout) findViewById(R.id.linear3);
		linear2 = (LinearLayout) findViewById(R.id.linear2);
		title = (TextView) findViewById(R.id.title);
		cacheamount = (TextView) findViewById(R.id.cacheamount);
		linear4 = (LinearLayout) findViewById(R.id.linear4);
		clean = (Button) findViewById(R.id.clean);
		
		clean.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				cacheamount.setVisibility(View.GONE);
				progressbar.setVisibility(View.VISIBLE);
				clean.setEnabled(false);
				clean.setBackgroundColor(0xFFB3E5FC);
				title.setText("Clearing ".concat(String.valueOf((long)(cachesize)).concat(" Cache Files")));
				temp = 0;
				for(int _repeat13 = 0; _repeat13 < (int)(cacheFiles.size()); _repeat13++) {
					_clearCache(cacheFiles.get((int)(temp)));
					temp++;
				}
				title.setText("Cleared ".concat(String.valueOf((long)(cachesize)).concat(" Cache Files")));
				progressbar.setVisibility(View.GONE);
				clean.setVisibility(View.GONE);
			}
		});
	}
	
	private void initializeLogic() {
		clean.setVisibility(View.GONE);
		cacheamount.setVisibility(View.GONE);
		FileUtil.listDir("/storage/emulated/0/.sketchware/mysc/", tempPath);
		temp = 0;
		cachesize = 0;
		for(int _repeat12 = 0; _repeat12 < (int)(tempPath.size()); _repeat12++) {
			_checkForCache(tempPath.get((int)(temp)));
			temp++;
		}
		if (cachesize == 0) {
			clean.setVisibility(View.GONE);
			title.setText("No cache!");
			cacheamount.setVisibility(View.GONE);
			progressbar.setVisibility(View.GONE);
		}
		else {
			clean.setVisibility(View.VISIBLE);
			clean.setText("Clean Cache");
			progressbar.setVisibility(View.GONE);
		}
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			
			default:
			break;
		}
	}
	
	public void _clearCache (final String _projectPath) {
		tempPath.clear();
		FileUtil.listDir(_projectPath, tempPath);
		temp2 = 0;
		for(int _repeat93 = 0; _repeat93 < (int)(tempPath.size()); _repeat93++) {
			if (!FileUtil.isDirectory(tempPath.get((int)(temp2)).concat("/"))) {
				FileUtil.deleteFile(tempPath.get((int)(temp2)));
			}
			temp2++;
		}
	}
	
	
	public void _checkForCache (final String _projectPath) {
		temppat2.clear();
		if (FileUtil.isExistFile(_projectPath.concat("/bin/"))) {
			cashe = true;
			cacheFiles.add(_projectPath.concat("/bin/"));
		}
		else {
			//ngl just added for timepass
		}
		
		temp2 = 0;
		FileUtil.listDir(_projectPath.concat("/bin/"), temppat2);
		for(int _repeat27 = 0; _repeat27 < (int)(temppat2.size()); _repeat27++) {
			if (!FileUtil.isDirectory(temppat2.get((int)(temp2)).concat("/"))) {
				cachesize++;
				tim = new TimerTask() {
					@Override
					public void run() {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								cacheamount.setVisibility(View.VISIBLE);
								cacheamount.setText(String.valueOf((long)(cachesize)).concat(" Cache Files Found!"));
							}
						});
					}
				};
				_timer.schedule(tim, (int)(50));
			}
			temp2++;
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
	public float getDip(int _input){
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
	}
	
	@Deprecated
	public int getDisplayWidthPixels(){
		return getResources().getDisplayMetrics().widthPixels;
	}
	
	@Deprecated
	public int getDisplayHeightPixels(){
		return getResources().getDisplayMetrics().heightPixels;
	}
	
}
