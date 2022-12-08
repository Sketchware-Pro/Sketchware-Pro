package a.a.a;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.besome.sketch.beans.ProjectLibraryBean;
import com.sketchware.remod.R;

public class qu extends CardView {

    public Context j;
    public View k;
    public ImageView l;
    public TextView m;
    public TextView n;
    public TextView o;

    public qu(Context var1) {
        super(var1);
        j = var1;
    }

    public void a(int resLayout) {
        wB.a(j, this, resLayout);
        l = findViewById(R.id.lib_icon);
        m = findViewById(R.id.lib_title);
        n = findViewById(R.id.lib_desc);
        o = findViewById(R.id.tv_enable);
        LayoutParams layoutParams = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.topMargin = (int) wB.a(j, 4f);
        layoutParams.bottomMargin = (int) wB.a(j, 4f);
        setLayoutParams(layoutParams);
    }

    public void setData(ProjectLibraryBean projectLibraryBean) {
        l.setImageResource(ProjectLibraryBean.getLibraryIcon(projectLibraryBean.libType));
        m.setText(xB.b().a(getContext(), ProjectLibraryBean.getLibraryResName(projectLibraryBean.libType)));
        n.setText(xB.b().a(getContext(), ProjectLibraryBean.getLibraryResDesc(projectLibraryBean.libType)));
        String var3;
        if ("Y".equals(projectLibraryBean.useYn)) {
            var3 = "ON";
        } else {
            var3 = "OFF";
        }

        o.setText(var3);
        o.setSelected("Y".equals(projectLibraryBean.useYn));
    }
}
