package <?package_name?>;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class DebugActivity extends Activity {

    private static final Map<String, String> exceptionMap = new HashMap<String, String>() {{
        put("StringIndexOutOfBoundsException", "Invalid string operation\n");
        put("IndexOutOfBoundsException", "Invalid list operation\n");
        put("ArithmeticException", "Invalid arithmetical operation\n");
        put("NumberFormatException", "Invalid toNumber block operation\n");
        put("ActivityNotFoundException", "Invalid intent operation\n");
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SpannableStringBuilder formattedMessage = new SpannableStringBuilder();
        Intent intent = getIntent();
        String errorMessage = "";

        if (intent != null) {
            errorMessage = intent.getStringExtra("error");
        }

        if (!errorMessage.isEmpty()) {
            String[] split = errorMessage.split("\n");

            String exceptionType = split[0];
            String message = exceptionMap.getOrDefault(exceptionType, "");

            if (!message.isEmpty()) {
                formattedMessage.append(message);
            }

            for (int i = 1; i < split.length; i++) {
                formattedMessage.append(split[i]);
                formattedMessage.append("\n");
            }
        } else {
            formattedMessage.append("No error message available.");
        }

        setTitle(getTitle() + " Crashed");

        TextView errorView = new TextView(this);
        errorView.setText(formattedMessage);
        errorView.setTextIsSelectable(true);

        HorizontalScrollView hscroll = new HorizontalScrollView(this);
        ScrollView vscroll = new ScrollView(this);

        hscroll.addView(vscroll);
        vscroll.addView(errorView);

        setContentView(hscroll);
    }
}
