package

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

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

        var dialog = new MaterialAlertDialogBuilder(this)
                .setTitle("An error occurred")
                .setMessage(madeErrorMessage)
                .setPositiveButton("End Application", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .create();
        dialog.show();
        ((TextView) dialog.findViewById(android.R.id.message)).setTextIsSelectable(true);
    }
}
