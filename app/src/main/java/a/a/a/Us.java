package a.a.a;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.Gravity;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import com.besome.sketch.beans.BlockBean;

import java.util.ArrayList;

import pro.sketchware.utility.ThemeUtils;

public class Us extends Rs {

    public ArrayList<BlockBean> blocks;
    public TextView tvSpec;

    public Us(Context context, String var2, String var3, String var4, String var5, ArrayList<BlockBean> blocks) {
        super(context, -1, var5, var2, var3, var4);
        this.blocks = blocks;
        super.oa = 2;
    }

    private TextView createSpec(String spec) {
        TextView textView = new TextView(super.a);
        String text = (super.c != null && !super.c.isEmpty()) ? super.c + " : " + spec : spec;

        textView.setText(text);
        textView.setTextSize(10.0f);
        textView.setPadding(0, 0, 0, 0);
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setTextColor(0xffffffff);
        textView.setTypeface(null, Typeface.BOLD);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, super.G);
        params.setMargins(0, 0, 0, 0);
        textView.setLayoutParams(params);
        return textView;
    }

    public final int[] getTextDimensions(TextView textView) {
        Rect bounds = new Rect();
        textView.getPaint().getTextBounds(textView.getText().toString(), 0, textView.getText().length(), bounds);
        return new int[]{bounds.width(), bounds.height()};
    }

    public ArrayList<BlockBean> getData() {
        return blocks;
    }

    public void k() {
        tvSpec.setX((float) super.w);
        tvSpec.setY((float) super.u);

        int[] dimensions = getTextDimensions(tvSpec);
        int textWidth = dimensions[0];
        int textHeight = dimensions[1];

        int width = super.w + textWidth + super.x;
        if (super.c != null && !super.c.isEmpty()) {
            width += (int) (super.D * 8.0f);
        }

        if (super.b.equals("b") || super.b.equals("d") || super.b.equals("s") || super.b.equals("a")) {
            width = Math.max(width, super.W);
        }

        if (super.b.equals(" ") || super.b.isEmpty() || super.b.equals("o")) {
            width = Math.max(width, super.aa);
        }

        if (super.b.equals("c") || super.b.equals("e")) {
            width = Math.max(width, super.ca);
        }

        int height = Math.max(super.u + super.G + super.v, super.u + textHeight + super.v);
        a((float) width, (float) height, true);
    }

    public void l() {
        setDrawingCacheEnabled(false);
        float density = super.D;
        super.W = (int) ((float) super.W * density);
        super.aa = (int) ((float) super.aa * density);
        super.ba = (int) ((float) super.ba * density);
        super.ca = (int) ((float) super.ca * density);
        super.da = (int) ((float) super.da * density);

        switch (super.b) {
            case "a":
            case "b":
            case "d":
            case "s":
            case "v":
            case "p":
            case "l":
                super.fa = true;
                break;

            case "f":
                super.ga = true;
                break;

            case "h":
                super.ea = true;
                break;
        }

        tvSpec = createSpec(super.T);
        addView(tvSpec);
        super.e = ThemeUtils.getColor(tvSpec, androidx.appcompat.R.attr.colorError);
        k();
    }
}
