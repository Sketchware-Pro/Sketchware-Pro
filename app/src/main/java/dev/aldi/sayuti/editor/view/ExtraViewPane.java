package dev.aldi.sayuti.editor.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;

import com.besome.sketch.beans.ProjectResourceBean;
import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.editor.view.ViewPane;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import a.a.a.bB;
import a.a.a.kC;
import dev.aldi.sayuti.editor.view.item.ItemCircleImageView;

public class ExtraViewPane {
    public static void a(View view, ViewBean viewBean, ViewPane viewPane, kC kCVar) {
        String lastPath = getLastPath(viewBean.convert);
        if (lastPath.equals("SearchView")) {
            a((EditText) view, viewBean);
        }
        if (lastPath.equals("AutoCompleteTextView")) {
            b((AutoCompleteTextView) view, viewBean);
        }
        if (lastPath.equals("MultiAutoCompleteTextView")) {
            c((MultiAutoCompleteTextView) view, viewBean);
        }
        if (lastPath.equals("CircleImageView") && viewBean.type == 43) {
            d((ItemCircleImageView) view, viewBean, viewPane, kCVar);
        }
    }

    public static void a(EditText editText, ViewBean viewBean) {
        editText.setHint(viewBean.text.hint);
        editText.setHintTextColor(viewBean.text.hintColor);
        editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 2131166060, 0);
    }

    public static void b(AutoCompleteTextView autoCompleteTextView, ViewBean viewBean) {
        autoCompleteTextView.setHint(viewBean.text.hint);
        autoCompleteTextView.setHintTextColor(viewBean.text.hintColor);
    }

    public static void c(MultiAutoCompleteTextView multiAutoCompleteTextView, ViewBean viewBean) {
        multiAutoCompleteTextView.setHint(viewBean.text.hint);
        multiAutoCompleteTextView.setHintTextColor(viewBean.text.hintColor);
    }

    public static void d(ItemCircleImageView itemCircleImageView, ViewBean viewBean, ViewPane viewPane, kC kCVar) {
        if (kCVar.h(viewBean.image.resName) == ProjectResourceBean.PROJECT_RES_TYPE_RESOURCE) {
            itemCircleImageView.setImageResource(viewPane.getContext().getResources().getIdentifier(viewBean.image.resName, "drawable", viewPane.getContext().getPackageName()));
        } else if (viewBean.image.resName.equals("default_image")) {
            itemCircleImageView.setImageResource(2131165522);
        } else {
            try {
                Bitmap decodeFile = BitmapFactory.decodeFile(kCVar.f(viewBean.image.resName));
                int round = Math.round(viewPane.getResources().getDisplayMetrics().density / 2.0f);
                itemCircleImageView.setImageBitmap(Bitmap.createScaledBitmap(decodeFile, decodeFile.getWidth() * round, round * decodeFile.getHeight(), true));
            } catch (Exception e) {
                itemCircleImageView.setImageResource(2131165522);
            }
        }
        itemCircleImageView.setScaleType(ImageView.ScaleType.valueOf("CENTER_CROP"));
        Iterator it = new ArrayList(Arrays.asList(viewBean.inject.split("\n"))).iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            if (str.contains("border_color")) {
                str = str.replaceAll("app:civ_border_color=\"|\"", "");
                try {
                    itemCircleImageView.setBorderColor(Color.parseColor(str));
                } catch (Exception e2) {
                    itemCircleImageView.setBorderColor(Color.parseColor("#FF008DCD"));
                    bB.a(viewPane.getContext(), "Invalid border color!", 0).show();
                }
            }
            if (str.contains("background_color")) {
                str = str.replaceAll("app:civ_circle_background_color=\"|\"", "");
                try {
                    itemCircleImageView.setCircleBackgroundColor(Color.parseColor(str));
                } catch (Exception e3) {
                    itemCircleImageView.setBorderColor(Color.parseColor("#FF008DCD"));
                    bB.a(viewPane.getContext(), "Invalid backgroud color!", 0).show();
                }
            }
            if (str.contains("border_width")) {
                try {
                    itemCircleImageView.setBorderWidth(Integer.valueOf(str.replaceAll("app:civ_border_width=\"|dp\"", "")).intValue());
                } catch (Exception e4) {
                    itemCircleImageView.setBorderWidth(3);
                }
            }
        }
    }

    public static String getLastPath(String str) {
        if (!str.contains(".")) {
            return str;
        }
        String[] split = str.split("\\.");
        return split[split.length - 1];
    }
}
