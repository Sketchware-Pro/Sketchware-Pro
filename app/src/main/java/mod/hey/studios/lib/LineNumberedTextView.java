package mod.hey.studios.lib;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sketchware.remod.Resources;

import a.a.a.wB;

public class LineNumberedTextView extends LinearLayout {

    private final Context context;

    private TextView line_numbered_textview_main;
    private TextView line_numbered_textview_lines;

    public LineNumberedTextView(Context context) {
        super(context);
        this.context = context;
        initialize();
    }

    public LineNumberedTextView(Context context, AttributeSet attr) {
        super(context, attr);
        this.context = context;
        initialize();
    }

    public LineNumberedTextView(Context context, AttributeSet attr, int defStyle) {
        super(context, attr, defStyle);
        this.context = context;
        initialize();
    }

    private void initialize() {
        View view = wB.a(context, this, Resources.layout.line_numbered_textview_layout); //View.inflate(context, 0x7F0B01D9, this);

        line_numbered_textview_main = view.findViewById(Resources.id.line_numbered_textview_main);
        line_numbered_textview_lines = view.findViewById(Resources.id.line_numbered_textview_lines);
    }

    public void setText(CharSequence c) {
        line_numbered_textview_main.setText(c);
        refreshLines(c.toString());
    }

    private void refreshLines(String text) {
        StringBuilder b = new StringBuilder();

        for (int i = 1; i <= text.split("\n").length; i++) {
            b.append(i);
            b.append("\n");
        }

        line_numbered_textview_lines.setText(b.toString().trim());
    }

    public void setTextSize(float size) {
        line_numbered_textview_main.setTextSize(size);
        line_numbered_textview_lines.setTextSize(size);
    }

    public void setTextColor(int color) {
        line_numbered_textview_main.setTextColor(color);
    }

    public void setTextIsSelectable(boolean textIsSelectable) {
        line_numbered_textview_main.setTextIsSelectable(textIsSelectable);
    }

    @Override
    public void setHorizontalScrollBarEnabled(boolean horizontalScrollBarEnabled) {
        line_numbered_textview_main.setHorizontalScrollBarEnabled(horizontalScrollBarEnabled);
    }

    @Override
    public void setOnLongClickListener(View.OnLongClickListener l) {
        line_numbered_textview_main.setOnLongClickListener(l);
    }

    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        line_numbered_textview_main.setPadding(left, top, right, bottom);
    }
}