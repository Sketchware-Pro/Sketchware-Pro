package mod.elfilibustero.sketch.editor.manage.resource;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

import com.sketchware.remod.databinding.ManageXmlBinding;
import com.sketchware.remod.R;

import mod.elfilibustero.sketch.beans.ResourceXmlBean;
import mod.elfilibustero.sketch.editor.manage.resource.XmlItemView;
import mod.hey.studios.util.Helper;

public class ManageXmlActivity extends AppCompatActivity implements View.OnClickListener {
	
	private ManageXmlBinding binding;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = ManageXmlBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());
		init();
	}

	@Override
	public void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		addItem(ResourceXmlBean.RES_TYPE_STRING);
		addItem(ResourceXmlBean.RES_TYPE_COLOR);
		addItem(ResourceXmlBean.RES_TYPE_STYLE);
	}

	private void init() {
		setSupportActionBar(binding.toolbar);
		getSupportActionBar().setTitle(Helper.getResString(R.string.text_title_menu_xml_manager));
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		binding.toolbar.setNavigationOnClickListener(Helper.getBackPressedClickListener(this));
	}

	private void addItem(int type) {
		XmlItemView item = new XmlItemView(this);
		item.setTag(type);
		item.setData(type);
		item.setOnClickListener(this);
		binding.itemLayout.addView(item);
	}

	@Override
	public void onClick(View view) {
		Object tag = view.getTag();
		if (tag instanceof Integer) {
			int tagValue = (int) tag;
			switch (tagValue) {
				case ResourceXmlBean.RES_TYPE_STRING:
					toXmlResourceActivity(ResourceXmlBean.RES_TYPE_STRING);
					break;
				case ResourceXmlBean.RES_TYPE_COLOR:
					toXmlResourceActivity(ResourceXmlBean.RES_TYPE_COLOR);
					break;
				case ResourceXmlBean.RES_TYPE_STYLE:
					toXmlResourceActivity(ResourceXmlBean.RES_TYPE_STYLE);
					break;
			}
		}
	}

	private void toXmlResourceActivity(int resType) {
		Intent intent = new Intent(getApplicationContext(), ManageXmlResourceActivity.class);
		intent.putExtra("sc_id", getIntent().getStringExtra("sc_id"));
		intent.putExtra("type", resType);
		startActivity(intent);
	}
}
