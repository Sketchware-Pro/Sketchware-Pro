package dev.aldi.sayuti.editor.manage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.appcompat.app.AppCompatActivity;

import pro.sketchware.R;
import pro.sketchware.databinding.ManageCustomAttributeBinding;

import java.util.LinkedList;
import java.util.List;

import dev.aldi.sayuti.editor.injection.AddCustomAttributeActivity;
import dev.aldi.sayuti.editor.injection.AppCompatInjection;
import mod.remaker.view.CustomAttributeView;

public class ManageCustomAttributeActivity extends AppCompatActivity {

    private final List<String> customAttributeLocations = new LinkedList<>();
    private String sc_id = "";
    private String xmlFilename = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ManageCustomAttributeBinding binding = ManageCustomAttributeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.topAppBar.setNavigationOnClickListener(view -> onBackPressed());

        if (getIntent().hasExtra("sc_id") && getIntent().hasExtra("file_name")) {
            sc_id = getIntent().getStringExtra("sc_id");
            xmlFilename = getIntent().getStringExtra("file_name").replaceAll(".xml", "");
            addType("Toolbar");
            addType("AppBarLayout");
            addType("CoordinatorLayout");
            addType("FloatingActionButton");
            addType("DrawerLayout");
            addType("NavigationDrawer");
            binding.manageAttrListview.setAdapter(new CustomAdapter(customAttributeLocations));
        } else {
            finish();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        AppCompatInjection.refreshInjections();
    }

    private void addType(String type) {
        customAttributeLocations.add(type);
    }

    private class CustomAdapter extends BaseAdapter {

        private final List<String> _data;

        public CustomAdapter(List<String> arrayList) {
            _data = arrayList;
        }

        @Override
        public int getCount() {
            return _data.size();
        }

        @Override
        public String getItem(int position) {
            return _data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            CustomAttributeView attributeView = new CustomAttributeView(parent.getContext());

            attributeView.getImageView().setImageResource(R.drawable.ic_property_inject);
            attributeView.getTextView().setText(getItem(position));
            attributeView.setOnClickListener(v -> {
                Intent i = new Intent();
                i.setClass(getApplicationContext(), AddCustomAttributeActivity.class);
                i.putExtra("sc_id", sc_id);
                i.putExtra("file_name", xmlFilename);
                i.putExtra("widget_type", getItem(position).toLowerCase());
                startActivity(i);
            });

            return attributeView;
        }
    }
}