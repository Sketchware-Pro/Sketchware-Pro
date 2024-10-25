package a.a.a;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.sketchware.remod.R;

@SuppressLint("StaticFieldLeak")
public abstract class MA extends AsyncTask<Void, String, String> {

    public Context a;

    public MA(Context var1) {
        a = var1;
    }

    @Override
    protected String doInBackground(Void... params) {
        try {
            if (isCancelled()) {
                return "";
            }
            b();
            return "";
        } catch (Exception e) {
            Log.e("MA", e.getMessage(), e);
            return xB.b().a(a, R.string.common_error_an_error_occurred) + "[" + e.getMessage() + "]";
        }
    }

    public abstract void a();

    public abstract void a(String var1);

    public abstract void b();

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (result.isEmpty()) {
            a();
        } else {
            a(result);
            bB.b(a, result, 1).show();
        }
    }
}
