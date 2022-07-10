package a.a.a;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sketchware.remod.R;

public class Ws extends RelativeLayout {

    private final int id;
    private final String name;
    private final int color;
    private TextView tv_category;
    private View background;
    private int unselectedBackgroundWidth = 0;

    public Ws(Context context, int id, String name, int color) {
        super(context);
        this.id = id;
        this.name = name;
        this.color = color;
        initialize(context);
    }

    private void initialize(Context context) {
        wB.a(context, this, R.layout.palette_selector_item);
        tv_category = findViewById(R.id.tv_category);
        background = findViewById(R.id.bg);
        unselectedBackgroundWidth = (int) wB.a(context, 4f);
        tv_category.setText(name);
        background.setBackgroundColor(color);
        setSelected(false);
    }

    public int getColor() {
        return color;
    }

    @Override
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public void setSelected(boolean selected) {
        if (selected) {
            tv_category.setTextColor(Color.WHITE);
            ViewGroup.LayoutParams params = background.getLayoutParams();
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            background.setLayoutParams(params);
        } else {
            tv_category.setTextColor(0xff505050);
            ViewGroup.LayoutParams params = background.getLayoutParams();
            params.width = unselectedBackgroundWidth;
            background.setLayoutParams(params);
        }
    }
}
