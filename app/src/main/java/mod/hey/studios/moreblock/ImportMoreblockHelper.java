package mod.hey.studios.moreblock;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

public class ImportMoreblockHelper {
    public static TextView optimizedBlockView(Context context, String str) {
        TextView textView = (TextView) ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(2131427813, (ViewGroup) null).findViewById(2131232553);
        textView.setText(ReturnMoreblockManager.getMbName(str));
        String moreblockChar = ReturnMoreblockManager.getMoreblockChar(str);
        if (moreblockChar.equals(" ")) {
            textView.setBackgroundResource(2131166371);
        } else if (moreblockChar.equals("s")) {
            textView.setBackgroundResource(2131166373);
        } else if (moreblockChar.equals("b")) {
            textView.setBackgroundResource(2131166369);
        } else if (moreblockChar.equals("d")) {
            textView.setBackgroundResource(2131166370);
        } else {
            textView.setBackgroundResource(2131166371);
        }
        textView.getBackground().setColorFilter(Color.parseColor("#8a55d7"), PorterDuff.Mode.MULTIPLY);
        if (textView.getParent() != null) {
            ((ViewGroup) textView.getParent()).removeView(textView);
        }
        return textView;
    }
}
