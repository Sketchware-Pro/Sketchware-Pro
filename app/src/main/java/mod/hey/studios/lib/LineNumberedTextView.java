package mod.hey.studios.lib;

import a.a.a.wB;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LineNumberedTextView extends LinearLayout {
    private Context context;
    private TextView line_numbered_textview_lines;
    private TextView line_numbered_textview_main;

    public LineNumberedTextView(Context context2) {
        super(context2);
        this.context = context2;
        initialize();
    }

    public LineNumberedTextView(Context context2, AttributeSet attributeSet) {
        super(context2, attributeSet);
        this.context = context2;
        initialize();
    }

    public LineNumberedTextView(Context context2, AttributeSet attributeSet, int i) {
        super(context2, attributeSet, i);
        this.context = context2;
        initialize();
    }

    private void initialize() {
        View a = wB.a(this.context, this, 2131427801);
        this.line_numbered_textview_main = (TextView) a.findViewById(2131232467);
        this.line_numbered_textview_lines = (TextView) a.findViewById(2131232466);
    }

    public void setText(CharSequence charSequence) {
        this.line_numbered_textview_main.setText(charSequence);
        refreshLines(charSequence.toString());
    }

    private void refreshLines(String str) {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= str.split("\n").length; i++) {
            sb.append(i);
            sb.append("\n");
        }
        this.line_numbered_textview_lines.setText(sb.toString().trim());
    }

    public void setTextSize(float f) {
        this.line_numbered_textview_main.setTextSize(f);
        this.line_numbered_textview_lines.setTextSize(f);
    }

    public void setTextColor(int i) {
        this.line_numbered_textview_main.setTextColor(i);
    }

    public void setTextIsSelectable(boolean z) {
        this.line_numbered_textview_main.setTextIsSelectable(z);
    }

    @Override
    public void setHorizontalScrollBarEnabled(boolean z) {
        this.line_numbered_textview_main.setHorizontalScrollBarEnabled(z);
    }

    @Override
    public void setOnLongClickListener(View.OnLongClickListener onLongClickListener) {
        this.line_numbered_textview_main.setOnLongClickListener(onLongClickListener);
    }

    @Override
    public void setPadding(int i, int i2, int i3, int i4) {
        this.line_numbered_textview_main.setPadding(i, i2, i3, i4);
    }
}
