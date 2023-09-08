package mod.elfilibustero.sketch.editor.manage.resource;

import a.a.a.wB;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;

import com.sketchware.remod.databinding.ManageXmlBaseItemBinding;
import com.sketchware.remod.R;

import mod.elfilibustero.sketch.beans.ResourceXmlBean;
import mod.hey.studios.util.Helper;

public class XmlItemView extends CardView {
    private ManageXmlBaseItemBinding binding;

    public XmlItemView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        binding = ManageXmlBaseItemBinding.inflate(LayoutInflater.from(context), this, true);
        LayoutParams layoutParams = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.leftMargin = (int) wB.a(context, 8f);
        layoutParams.topMargin = (int) wB.a(context, 4f);
        layoutParams.bottomMargin = (int) wB.a(context, 4f);
        layoutParams.rightMargin = (int) wB.a(context, 8f);
        setLayoutParams(layoutParams);
    }

    public void setData(int type) {
        binding.icon.setImageResource(ResourceXmlBean.getXmlIcon(type));
        binding.title.setText(Helper.getResString(ResourceXmlBean.getXmlResName(type)));
        binding.desc.setText(Helper.getResString(ResourceXmlBean.getXmlResDesc(type)));
    }
}
