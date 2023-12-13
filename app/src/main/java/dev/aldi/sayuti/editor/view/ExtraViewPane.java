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
import com.sketchware.remod.R;

import a.a.a.kC;
import dev.aldi.sayuti.editor.view.item.ItemCircleImageView;
import mod.SketchwareUtil;
import mod.agus.jcoderz.beans.ViewBeans;

public class ExtraViewPane {
    public static void a(View view, ViewBean viewBean, ViewPane viewPane, kC kCVar) {
        try {
            switch (getLastPath(viewBean.convert)) {
                case "SearchView" -> handleSearchView((EditText) view, viewBean);
                case "AutoCompleteTextView" ->
                        handleAutoCompleteTextView((AutoCompleteTextView) view, viewBean);
                case "MultiAutoCompleteTextView" ->
                        handleMultiAutoCompleteTextView((MultiAutoCompleteTextView) view, viewBean);
                case "CircleImageView" -> {
                    if (viewBean.type == ViewBeans.VIEW_TYPE_WIDGET_CIRCLEIMAGEVIEW) {
                        handleCircleImageView((ItemCircleImageView) view, viewBean, viewPane, kCVar);
                    }
                }
            }
        } catch (Exception ignored) {

        }
    }

    private static void handleSearchView(EditText item, ViewBean viewBean) {
        item.setHint(viewBean.text.hint);
        item.setHintTextColor(viewBean.text.hintColor);
        item.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.search_icon_grey, 0);
    }

    private static void handleAutoCompleteTextView(AutoCompleteTextView item, ViewBean viewBean) {
        item.setHint(viewBean.text.hint);
        item.setHintTextColor(viewBean.text.hintColor);
    }

    private static void handleMultiAutoCompleteTextView(MultiAutoCompleteTextView item, ViewBean viewBean) {
        item.setHint(viewBean.text.hint);
        item.setHintTextColor(viewBean.text.hintColor);
    }

    private static void handleCircleImageView(ItemCircleImageView item, ViewBean viewBean, ViewPane viewPane, kC kC) {
        if (kC.h(viewBean.image.resName) == ProjectResourceBean.PROJECT_RES_TYPE_RESOURCE) {
            item.setImageResource(viewPane.getContext().getResources().getIdentifier(viewBean.image.resName, "drawable", viewPane.getContext().getPackageName()));
        } else if (viewBean.image.resName.equals("default_image")) {
            item.setImageResource(R.drawable.default_image);
        } else {
            try {
                Bitmap decodeFile = BitmapFactory.decodeFile(kC.f(viewBean.image.resName));
                int round = Math.round(viewPane.getResources().getDisplayMetrics().density / 2.0f);
                item.setImageBitmap(Bitmap.createScaledBitmap(decodeFile, decodeFile.getWidth() * round, round * decodeFile.getHeight(), true));
            } catch (Exception e) {
                item.setImageResource(R.drawable.default_image);
            }
        }
        item.setScaleType(ImageView.ScaleType.CENTER_CROP);
        for (String line : viewBean.inject.split("\n")) {
            if (line.contains("border_color")) {
                line = line.replaceAll("app:civ_border_color=\"|\"", "");

                if (!line.startsWith("@")) {
                    try {
                        item.setBorderColor(Color.parseColor(line));
                    } catch (Exception e) {
                        item.setBorderColor(0xff008dcd);
                        SketchwareUtil.toastError("Invalid border color in CircleImageView " + viewBean.id + "!");
                    }
                }
            }

            if (line.contains("background_color")) {
                line = line.replaceAll("app:civ_circle_background_color=\"|\"", "");

                if (!line.startsWith("@")) {
                    try {
                        item.setCircleBackgroundColor(Color.parseColor(line));
                    } catch (Exception e) {
                        item.setBorderColor(0xff008dcd);
                        SketchwareUtil.toastError("Invalid background color in CircleImageView " + viewBean.id + "!");
                    }
                }
            }
            if (line.contains("border_width")) {
                try {
                    item.setBorderWidth(Integer.parseInt(line.replaceAll("app:civ_border_width=\"|dp\"", "")));
                } catch (Exception e) {
                    item.setBorderWidth(3);
                }
            }
        }
    }

    private static String getLastPath(String str) {
        if (!str.contains(".")) {
            return str;
        }
        String[] split = str.split("\\.");
        return split[split.length - 1];
    }
}
