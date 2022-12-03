package a.a.a;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.besome.sketch.beans.ProjectLibraryBean;

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
        k = findViewById(2131230931);
        l = findViewById(2131231428);
        m = findViewById(2131231430);
        n = findViewById(2131231427);
        o = findViewById(2131231965);
        LayoutParams layoutParams = new LayoutParams(-1, -2);
        layoutParams.topMargin = (int) wB.a(j, 4.0F);
        layoutParams.bottomMargin = (int) wB.a(j, 4.0F);
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
