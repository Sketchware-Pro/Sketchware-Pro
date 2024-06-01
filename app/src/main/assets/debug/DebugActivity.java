package <?package_name?>;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;
import android.widget.TextView;

public class DebugActivity extends Activity {

    private String[] exceptionTypes = {
            "StringIndexOutOfBoundsException",
            "IndexOutOfBoundsException",
            "ArithmeticException",
            "NumberFormatException",
            "ActivityNotFoundException"
    };

    private String[] exceptionMessages = {
            "Invalid string operation\n",
            "Invalid list operation\n",
            "Invalid arithmetical operation\n",
            "Invalid toNumber block operation\n",
            "Invalid intent operation"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String errorMessage = "";
        String madeErrorMessage = "";

        if (intent != null) {
            errorMessage = intent.getStringExtra("error");

            String[] split = errorMessage.split("\n");
            //errorMessage = split[0];
            try {
                for (int j = 0; j < exceptionTypes.length; j++) {
                    if (split[0].contains(exceptionTypes[j])) {
                        madeErrorMessage = exceptionMessages[j];

                        int addIndex = split[0].indexOf(exceptionTypes[j]) + exceptionTypes[j].length();

                        madeErrorMessage += split[0].substring(addIndex, split[0].length());
                        madeErrorMessage += "\n\nDetailed error message:\n" + errorMessage;
                        break;
                    }
                }

                if (madeErrorMessage.isEmpty()) {
                    madeErrorMessage = errorMessage;
                }
            } catch (Exception e) {
                madeErrorMessage = madeErrorMessage + "\n\nError while getting error: " + Log.getStackTraceString(e);
            }
        }

        setTitle(getTitle() + " Crashed");

        TextView error = new TextView(this);
        error.setText(madeErrorMessage);
        error.setTextIsSelectable(true);

        HorizontalScrollView hscroll = new HorizontalScrollView(this);
        ScrollView vscroll = new ScrollView(this);

        hscroll.addView(vscroll);
        vscroll.addView(error);

        setContentView(hscroll);
    }
}
