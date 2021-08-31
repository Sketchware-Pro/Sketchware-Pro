package a.a.a;

import a.a.a.wB;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils.TruncateAt;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Ws extends RelativeLayout {
	public int a;
	public String b;
	public int c;
	public TextView d;
	public View e;
	public int f = 0;
	
	public Ws(Context context, int id, String name, int color) {
		super(context);
		a = id;
		b = name;
		c = color;
		a(context);
	}
	
	public final void a(Context context) {
		wB.a(context, this, 2131427609);
		d = (TextView) findViewById(2131231894);
		e = findViewById(2131230792);
		f = (int) wB.a(context, 4.0F);
		d.setText(b);
		e.setBackgroundColor(c);
		setSelected(false);
	}
	
	public int getColor() {
		return c;
	}
	
	public int getId() {
		return a;
	}
	
	public String getName() {
		return b;
	}
	
	public void setSelected(boolean selected) {
		if (selected) {
			d.setTextColor(Color.WHITE);
			ViewGroup.LayoutParams params = e.getLayoutParams();
			params.width = ViewGroup.LayoutParams.MATCH_PARENT;
			e.setLayoutParams(params);
		} else {
			d.setTextColor(0xff505050);
			ViewGroup.LayoutParams params2 = e.getLayoutParams();
			params2.width = f;
			e.setLayoutParams(params2);
		}
	}
}
