package mod.hey.studios.moreblock;

import android.content.Context;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sketchware.remod.Resources;

public class ImportMoreblockHelper {

    public static TextView optimizedBlockView(Context context, String str) {
        TextView textView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(Resources.layout.block_customview, null).findViewById(Resources.id.spec);

        textView.setText(ReturnMoreblockManager.getMbName(str));
        String moreblockChar = ReturnMoreblockManager.getMoreblockChar(str);

        switch (moreblockChar) {
            case "s":
                textView.setBackgroundResource(Resources.drawable.block_string);
                break;

            case "b":
                textView.setBackgroundResource(Resources.drawable.block_boolean);
                break;

            case "d":
                textView.setBackgroundResource(Resources.drawable.block_num);
                break;

            default:
                textView.setBackgroundResource(Resources.drawable.block_ori);
                break;
        }

        textView.getBackground().setColorFilter(0xff8a55d7, PorterDuff.Mode.MULTIPLY);

        if (textView.getParent() != null) {
            ((ViewGroup) textView.getParent()).removeView(textView);
        }

        return textView;
    }
}
