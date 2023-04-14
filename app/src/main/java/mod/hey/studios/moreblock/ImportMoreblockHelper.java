package mod.hey.studios.moreblock;

import android.content.Context;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sketchware.remod.R;

import mod.tsd.ui.AppThemeApply;

public class ImportMoreblockHelper {

    public static TextView optimizedBlockView(Context context, String str) {
    	AppThemeApply.setUpTheme(context);
        TextView textView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.block_customview, null).findViewById(R.id.spec);

        textView.setText(ReturnMoreblockManager.getMbName(str));
        String moreblockChar = ReturnMoreblockManager.getMoreblockChar(str);

        switch (moreblockChar) {
            case "s":
                textView.setBackgroundResource(R.drawable.block_string);
                break;

            case "b":
                textView.setBackgroundResource(R.drawable.block_boolean);
                break;

            case "d":
                textView.setBackgroundResource(R.drawable.block_num);
                break;

            default:
                textView.setBackgroundResource(R.drawable.block_ori);
                break;
        }

        textView.getBackground().setColorFilter(0xff8a55d7, PorterDuff.Mode.MULTIPLY);

        if (textView.getParent() != null) {
            ((ViewGroup) textView.getParent()).removeView(textView);
        }

        return textView;
    }
}
