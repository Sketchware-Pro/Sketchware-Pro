package com.besome.sketch;

import a.a.a.FB;
import a.a.a.Ss;
import a.a.a.Ts;
import a.a.a.kq;
import a.a.a.xB;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.besome.sketch.beans.BlockBean;
import com.besome.sketch.editor.logic.BlockPane;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import mod.agus.jcoderz.editor.event.ManageEvent;
import mod.hey.studios.editor.manage.block.ExtraBlockInfo;
import mod.hey.studios.editor.manage.block.v2.BlockLoader;
import mod.hey.studios.project.ProjectTracker;

public class Rs extends Ts {
	public String T;
	public String U;
	public ArrayList<View> V;
	public int W = 30;
	public int aa = 50;
	public int ba = 90;
	public int ca = 90;
	public int da = 4;
	public boolean ea = false;
	public boolean fa = false;
	public boolean ga = false;
	public int ha = -1;
	public int ia = -1;
	public int ja = -1;
	public ArrayList<View> ka = new ArrayList();
	public ArrayList<String> la = new ArrayList();
	public TextView ma = null;
	public int na = 0;
	public int oa = 0;
	public BlockPane pa = null;
	public String qa;
	public String ra;
	private String spec2 = "";
	
	public Rs(Context context, int n, String string, String string2, String string3) {
		super(context, string2, false);
		this.setTag((Object)n);
		this.T = string;
		this.U = string3;
		this.l();
	}
	
	public Rs(Context context, int n, String string, String string2, String string3, String string4) {
		super(context, string2, string3, false);
		this.setTag((Object)n);
		this.T = string;
		this.U = string4;
		this.l();
	}
	
	public final int a(TextView textView) {
		Rect rect = new Rect();
		textView.getPaint().getTextBounds(textView.getText().toString(), 0, textView.getText().length(), rect);
		return rect.width();
	}
	
	
	public final TextView a(String text) {
		String formattedText;
		TextView textView = new TextView(this.context);
		if (!"getVar".equals(this.U)) {
			formattedText = text;
		} else if ("getArg".equals(this.U) && this.c != null && this.c.length() > 0) {
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append(this.c);
			stringBuilder.append(" : ");
			stringBuilder.append(text);
			formattedText = stringBuilder.toString();
		} else {
			formattedText = text;
		}
		textView.setText(formattedText);
		textView.setTextSize(10.0f);
		textView.setPadding(0, 0, 0, 0);
		textView.setGravity(Gravity.CENTER_VERTICAL);
		textView.setTextColor(Color.WHITE);
		textView.setTypeface(null, Typeface.BOLD);
		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, this.G);
		layoutParams.setMargins(0, 0, 0, 0);
		textView.setLayoutParams(layoutParams);
		return textView;
	}
	
	public final void a(Rs rs) {
		if (this.b() && -1 == this.ia) {
			this.e(rs);
		} else {
			Rs rs2 = this.h();
			rs2.ha = (Integer)rs.getTag();
			rs.E = rs2;
		}
	}
	
	public void a(Ts ts, Rs rs) {
		int n = ka.indexOf(ts);
		if (n < 0) {
			return;
		}
		boolean isRs = ts instanceof Rs;
		if (isRs) {
			Rs rs2 = (Rs)ts;
			rs.qa = rs2.qa;
			rs.ra = rs2.ra;
		} else if (ts instanceof Ss) {
			rs.qa = ts.b;
			rs.ra = ts.c;
		}
		
		if (!isRs) {
			removeView((View)ts);
		}
		
		ka.set(n, rs);
		rs.E = this;
		i();
		o();
		
		if (ts != rs && isRs) {
			ts.E = null;
			ts.setX(getX() + getWidthSum() + 10.0f);
			ts.setY(getY() + 5.0f);
			((Rs)ts).k();
		}
	}
	
	public final void a(String string, int n) {
		ArrayList arrayList = FB.c((String)string);
		this.ka = new ArrayList();
		this.la = new ArrayList();
		for (int i = 0; i < arrayList.size(); ++i) {
			View view = this.b((String)arrayList.get(i), n);
			if (view instanceof Ts) {
				((Ts)view).E = this;
			}
			this.ka.add((Object)view);
			string = view instanceof Ss ? (String)arrayList.get(i) : "icon";
			if (view instanceof TextView) {
				string = "label";
			}
			this.la.add((Object)string);
		}
	}
	
	public final View b(String string, int n) {
		if (string.length() >= 2 && string.charAt(0) == '%') {
			n = string.charAt(1);
			String string2 = "";
			if (n == 98) {
				return new Ss(this.a, "b", "");
			}
			if (n == 100) {
				return new Ss(this.a, "d", "");
			}
			if (n == 109) {
				return new Ss(this.a, "m", string.substring(3));
			}
			if (n == 115) {
				Context context = this.a;
				if (string.length() > 2) {
					string2 = string.substring(3);
				}
				return new Ss(context, "s", string2);
			}
		}
		return this.a(FB.d((String)string));
	}
	
	public void b(Rs rs) {
		View view = this.pa.findViewWithTag((Object)this.ha);
		if (view != null) {
			((Rs)view).E = null;
		}
		rs.E = this;
		this.ha = (Integer)rs.getTag();
		if (view != null) {
			rs.a((Rs)view);
		}
	}
	
	public void c(Rs rs) {
		rs.setX(this.getX());
		rs.setY(this.getY() - (float)rs.getHeightSum() + (float)this.h);
		rs.h().b(this);
	}
	
	public void d(Rs rs) {
		rs.setX(this.getX() - (float)this.j);
		rs.setY(this.getY() - (float)this.f());
		this.E = rs;
		rs.ia = (Integer)this.getTag();
	}
	
	public void e(Rs rs) {
		View view = this.pa.findViewWithTag((Object)this.ia);
		if (view != null) {
			((Rs)view).E = null;
		}
		rs.E = this;
		this.ia = (Integer)rs.getTag();
		if (view != null) {
			rs.a((Rs)view);
		}
	}
	
	public void f(Rs rs) {
		View view = this.pa.findViewWithTag((Object)this.ja);
		if (view != null) {
			((Rs)view).E = null;
		}
		rs.E = this;
		this.ja = (Integer)rs.getTag();
		if (view != null) {
			rs.a((Rs)view);
		}
	}
	
	public void g(Rs rs) {
		if (this.ha == (Integer)rs.getTag()) {
			this.ha = -1;
		}
		if (this.ia == (Integer)rs.getTag()) {
			this.ia = -1;
		}
		if (this.ja == (Integer)rs.getTag()) {
			this.ja = -1;
		}
		if (rs.fa) {
			int n = this.ka.indexOf((Object)rs);
			if (n < 0) {
				return;
			}
			rs.qa = "";
			rs.ra = "";
			rs = this.b((String)this.la.get(n), this.e);
			if (rs instanceof Ts) {
				((Ts)rs).E = this;
			}
			this.ka.set(n, (Object)rs);
			this.addView((View)rs);
			this.i();
			this.o();
		}
		this.p().k();
	}
	
	public ArrayList<Rs> getAllChildren() {
		ArrayList arrayList = new ArrayList();
		Rs rs = this;
		do {
			int n;
			arrayList.add((Object)rs);
			for (View view : rs.ka) {
				if (!(view instanceof Rs)) continue;
				arrayList.addAll(((Rs)view).getAllChildren());
			}
			if (rs.b() && (n = rs.ia) != -1) {
				arrayList.addAll(((Rs)this.pa.findViewWithTag((Object)n)).getAllChildren());
			}
			if (rs.c() && (n = rs.ja) != -1) {
				arrayList.addAll(((Rs)this.pa.findViewWithTag((Object)n)).getAllChildren());
			}
			if ((n = rs.ha) == -1) break;
			rs = (Rs)this.pa.findViewWithTag((Object)n);
		} while (true);
		return arrayList;
	}
	
	public BlockBean getBean() {
		BlockBean blockBean = new BlockBean(this.getTag().toString(), this.T, this.b, this.c, this.U);
		blockBean.color = this.e;
		for (View view : this.V) {
			if (view instanceof Ss) {
				blockBean.parameters.add((Object)((Ss)view).getArgValue().toString());
				continue;
			}
			if (!(view instanceof Rs)) continue;
			ArrayList arrayList = blockBean.parameters;
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("@");
			stringBuilder.append(view.getTag().toString());
			arrayList.add((Object)stringBuilder.toString());
		}
		blockBean.subStack1 = this.ia;
		blockBean.subStack2 = this.ja;
		blockBean.nextBlock = this.ha;
		return blockBean;
	}
	
	public int getBlockType() {
		return this.oa;
	}
	
	public int getDepth() {
		int n = 0;
		Rs rs = this;
		while ((rs = rs.E) != null) {
			++n;
		}
		return n;
	}
	
	public int getHeightSum() {
		int totalHeight = 0;
		Rs rs = this;
		do {
			if (totalHeight > 0) {
				totalHeight -= this.h;
			}
			
			totalHeight += rs.getTotalHeight();
			int tag = rs.ha;
			
			if (tag == -1) {
				break;
			}
			
			rs = (Rs)this.pa.findViewWithTag(tag);
		} while (true);
		
		return totalHeight;
	}
	
	public int getWidthSum() {
		int totalWidth = 0;
		Rs rs = this;
		
		do {
			int maxWidth = Math.max(totalWidth, rs.getW());
			
			if (rs.b()) {
				int tag = rs.ia;
				
				if (tag != -1) {
					Rs childRs = (Rs) this.pa.findViewWithTag(tag);
					maxWidth = Math.max(maxWidth, this.j + childRs.getWidthSum());
				}
			}
			
			if (rs.c()) {
				int tag = rs.ja;
				
				if (tag != -1) {
					Rs childRs = (Rs) this.pa.findViewWithTag(tag);
					maxWidth = Math.max(maxWidth, this.j + childRs.getWidthSum());
				}
			}
			
			totalWidth = maxWidth;
			
			int tag = rs.ha;
			
			if (tag == -1) {
				break;
			}
			
			rs = (Rs) this.pa.findViewWithTag(tag);
		} while (true);
		
		return totalWidth;
	}
	
	
	public Rs h() {
		int n;
		Rs rs = this;
		while ((n = rs.ha) != -1) {
			rs = (Rs)this.pa.findViewWithTag((Object)n);
		}
		return rs;
	}
	
	public final void i() {
		this.V = new ArrayList();
		for (int i = 0; i < this.ka.size(); ++i) {
			View view = (View)this.ka.get(i);
			if (!(view instanceof Rs) && !(view instanceof Ss)) continue;
			this.V.add((Object)view);
		}
	}
	
	public final void j() {
		TextView textView = this.ma;
		if (textView != null) {
			textView.bringToFront();
			this.ma.setX((float)this.w);
			this.ma.setY((float)(this.g() - this.n));
		}
	}
	
	
	public void k() {
		bringToFront();
		int currentX = w;
		for (int i = 0; i < ka.size(); ++i) {
			View view = ka.get(i);
			view.bringToFront();
			
			boolean isRs = view instanceof Rs;
			
			if (isRs) {
				view.setX(getX() + currentX);
			} else {
				view.setX(currentX);
			}
			
			int width = la.get(i).equals("label") ? getLabelWidth((TextView) view) : 0;
			
			if (view instanceof Ss) {
				width = ((Ss) view).getW();
			} else if (isRs) {
				width = ((Rs) view).getWidthSum();
			}
			
			currentX += width + da;
			
			if (isRs) {
				float y = getY();
				float yOffset = u;
				int viewOffset = na;
				
				Rs rs = (Rs) view;
				view.setY(y + yOffset + (viewOffset - rs.na - 1) * y);
				rs.k();
			} else {
				view.setY(u + na * y);
			}
		}
		
		int maxWidth;
		
		if (b.equals("b") || b.equals("d") || b.equals("s")) {
			maxWidth = currentX;
		} else if (b.equals("a")) {
			maxWidth = Math.max(currentX, (int) W);
		} else if (b.equals(" ") || b.equals("")) {
			maxWidth = Math.max(currentX, (int) aa);
		} else if (b.equals("f")) {
			maxWidth = Math.max(currentX, (int) aa);
		} else if (b.equals("c")) {
			maxWidth = Math.max(currentX, (int) ca);
		} else if (b.equals("e")) {
			maxWidth = Math.max(currentX, (int) ca);
		} else if (b.equals("h")) {
			maxWidth = Math.max(currentX, (int) ba);
		}
		
		float width = (float) (maxWidth + x);
		float height = (float) (u + G + na * y * 2 + v);
		
		a(width, height, true);
		
		if (b()) {
			int substack1Height = i;
			int substack2Height = ja;
			
			if (substack1Height > -1) {
				Rs rs = (Rs) pa.findViewWithTag(substack1Height);
				rs.setX(getX() + j);
				rs.setY(getY() + f());
				rs.bringToFront();
				rs.k();
				substack1Height = rs.getHeightSum();
			}
			
			setSubstack1Height(substack1Height);
			
			if (substack2Height > -1) {
				Rs rs = (Rs) pa.findViewWithTag(substack2Height);
				rs.setX(getX() + j);
				rs.setY(getY() + g());
				rs.bringToFront();
				rs.k();
				substack2Height = rs.getHeightSum();
				
				if (rs.h().ga) {
					substack2Height = h + substack2Height;
				}
			}
			
			setSubstack2Height(substack2Height);
			j();
		}
		
		int nextBlockTag = ha;
		
		if (nextBlockTag > -1) {
			Rs rs = (Rs) pa.findViewWithTag(nextBlockTag);
			rs.setX(getX());
			rs.setY(getY() + d());
			rs.bringToFront();
			rs.k();
		}
	}
	
	public void l() {
		boolean flag = false;
		setDrawingCacheEnabled(false);
		float originalWidth = W;
		float scaleFactor = D;
		W = (int) (originalWidth * scaleFactor);
		aa = (int) (aa * scaleFactor);
		ba = (int) (ba * scaleFactor);
		ca = (int) (ca * scaleFactor);
		da = (int) (da * scaleFactor);
		int hashCode = b.hashCode();
		
		switch (hashCode) {
			case 32:
			flag = true;
			break;
			case 104:
			flag = true;
			break;
			case 108:
			flag = true;
			break;
			case 112:
			flag = true;
			break;
			case 115:
			flag = true;
			break;
			case 118:
			flag = true;
			break;
			case 97:
			flag = true;
			break;
			case 98:
			flag = true;
			break;
			case 99:
			flag = true;
			break;
			case 100:
			flag = true;
			break;
			case 101:
			flag = true;
			break;
			case 102:
			flag = true;
			break;
		}
		
		switch (hashCode) {
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
			fa = true;
			break;
			case 10:
			ga = true;
			break;
			case 11:
			ea = true;
			break;
		}
		
		int blockInfo = kq.a(U, b);
		
		if (!(ea || U.equals("definedFunc") || U.equals("getVar") || U.equals("getArg") || blockInfo == -7711273)) {
			T = xB.b().a(getContext(), U);
		}
		
		if (blockInfo == -7711273) {
			BlockInfo info = BlockLoader.getBlockInfo(U);
			BlockInfo block = info;
			
			if (ProjectTracker.SC_ID != null && info.isMissing && !ProjectTracker.SC_ID.equals("")) {
				block = BlockLoader.getBlockFromProject(ProjectTracker.SC_ID, U);
				BlockLoader.log("Rs:returned block: " + new Gson().toJson(block));
			}
			
			spec2 = block.getSpec2();
			
			if (T.equals("")) {
				T = block.getSpec();
			}
			
			if (T.equals("")) {
				T = ManageEvent.h(U);
			}
			
			setSpec(T);
			
			int color = block.getColor();
			int paletteColor = block.getPaletteColor();
			
			if (!(U.equals("definedFunc") || U.equals("getVar") || U.equals("getArg"))) {
				if (color != 0) {
					e = color;
					return;
				}
				
				if (paletteColor != 0) {
					e = paletteColor;
					return;
				}
				
				e = blockInfo;
				return;
			}
			
			e = blockInfo;
			return;
		}
		
		if (T.equals("")) {
			T = ManageEvent.h(U);
		}
		
		setSpec(T);
		e = blockInfo;
	}
	
	public void m() {
		Rs rs;
		Rs rs2 = this;
		do {
			rs2.n();
			rs2 = rs = rs2.E;
		} while (rs != null);
	}
	
	public void n() {
		int totalWidth = w;
		for (int i = 0; i < ka.size(); ++i) {
			View view = (View) ka.get(i);
			int viewWidth = (la.get(i).equals("label")) ? a((TextView) view) : 0;
			
			if (view instanceof Ss) {
				viewWidth = ((Ss) view).getW();
			}
			
			if (view instanceof Rs) {
				viewWidth = ((Rs) view).getWidthSum();
			}
			
			totalWidth += viewWidth + da;
		}
		
		if (b.equals("b") || b.equals("d") || b.equals("s")) {
			totalWidth = Math.max(totalWidth, W);
		} else if (b.equals("a")) {
			totalWidth = Math.max(totalWidth, W);
		}
		
		if (!b.equals(" ") && !b.equals("")) {
			if (b.equals("o")) {
				totalWidth = Math.max(totalWidth, aa);
			} else if (b.equals("c")) {
				totalWidth = Math.max(totalWidth, ca);
			} else if (b.equals("e")) {
				totalWidth = Math.max(totalWidth, ca);
			}
		}
		
		if (b.equals("h")) {
			totalWidth = Math.max(totalWidth, ba);
		}
		
		if (ma != null) {
			totalWidth = Math.max(totalWidth, w + ma.getWidth() + 2);
		}
		
		a((float) (totalWidth + x), (float) (u + G + na * y * 2 + v), false);
	}
	
	public void o() {
		Rs rs = this;
		while (rs != null) {
			int n = 0;
			for (View view : rs.V) {
				if (!(view instanceof Rs)) continue;
				n = Math.max((int)n, (int)(((Rs)view).na + 1));
			}
			rs.na = n;
			rs.n();
			if (!rs.fa) break;
			rs = rs.E;
		}
	}
	
	public Rs p() {
		Rs rs;
		Rs rs2 = this;
		while ((rs = rs2.E) != null) {
			rs2 = rs;
		}
		return rs2;
	}
	
	public void setBlockType(int n) {
		this.oa = n;
	}
	
	public void setSpec(String spec) {
		T = spec;
		removeAllViews();
		a(T, e);
		Iterator<View> iterator = ka.iterator();
		while (iterator.hasNext()) {
			addView(iterator.next());
		}
		
		i();
		
		if (b.equals("e") && U.equals("ifElse")) {
			ma = a(xB.b().a(getContext(), "else"));
			addView(ma);
		}
		
		if (b.equals("e") && !spec2.equals("")) {
			ma = a(spec2);
			addView(ma);
		}
		
		k();
		
	}
}
