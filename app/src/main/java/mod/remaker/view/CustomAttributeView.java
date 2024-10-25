package mod.remaker.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.sketchware.remod.R;

import a.a.a.wB;

public class CustomAttributeView extends FrameLayout {
    private View container;

    public ImageView icon;
    public TextView text;

    public CustomAttributeView(Context context) {
        super(context);
        wB.a(context, this, R.layout.custom_view_attribute);

        container = findViewById(R.id.cus_attr_layout);
        text = findViewById(R.id.cus_attr_text);
        icon = findViewById(R.id.cus_attr_btn);

        setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    @Override
    public void setOnClickListener(View.OnClickListener listener) {
        container.setOnClickListener(listener);
    }

    @Override
    public void setOnLongClickListener(View.OnLongClickListener listener) {
        container.setOnLongClickListener(listener);
    }
}
