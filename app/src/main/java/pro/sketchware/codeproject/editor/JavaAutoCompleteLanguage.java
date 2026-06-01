package pro.sketchware.codeproject.editor;

import android.os.Bundle;

import androidx.annotation.NonNull;

import io.github.rosemoe.sora.lang.completion.CompletionItemKind;
import io.github.rosemoe.sora.lang.completion.CompletionPublisher;
import io.github.rosemoe.sora.lang.completion.SimpleCompletionItem;
import io.github.rosemoe.sora.langs.java.JavaLanguage;
import io.github.rosemoe.sora.text.CharPosition;
import io.github.rosemoe.sora.text.ContentReference;

public class JavaAutoCompleteLanguage extends JavaLanguage {

    private static final String[] ANDROID_CLASSES = {
            "Activity", "AppCompatActivity", "Fragment", "Bundle", "Intent",
            "View", "TextView", "Button", "ImageView", "EditText",
            "RecyclerView", "LinearLayout", "RelativeLayout", "FrameLayout", "ConstraintLayout",
            "Toast", "Log", "Context", "SharedPreferences", "Handler",
            "ArrayList", "HashMap", "List", "Map", "Set",
            "String", "Integer", "Boolean", "Object", "Exception",
            "Runnable", "Thread", "AsyncTask", "LiveData", "ViewModel",
            "AlertDialog", "ProgressDialog", "Notification", "Service", "BroadcastReceiver",
            "ContentProvider", "Cursor", "ContentValues", "SQLiteDatabase",
            "LayoutInflater", "ViewGroup", "Adapter", "ViewHolder",
            "OnClickListener", "OnLongClickListener", "TextWatcher",
            "Drawable", "Color", "Bitmap", "Canvas", "Paint",
            "File", "InputStream", "OutputStream", "BufferedReader", "FileOutputStream"
    };

    private static final String[] ANDROID_METHODS = {
            "onCreate", "onStart", "onResume", "onPause", "onStop", "onDestroy",
            "onCreateView", "onViewCreated", "onActivityResult",
            "findViewById", "setContentView", "getIntent", "getExtras",
            "getString", "getResources", "getApplicationContext", "getSystemService",
            "startActivity", "startService", "sendBroadcast", "finish",
            "setText", "getText", "setVisibility", "setEnabled", "setOnClickListener",
            "setAdapter", "notifyDataSetChanged", "invalidate", "requestLayout",
            "runOnUiThread", "post", "postDelayed",
            "putExtra", "getStringExtra", "getIntExtra", "getBooleanExtra",
            "inflate", "getLayoutInflater",
            "show", "dismiss", "cancel", "execute",
            "add", "remove", "get", "put", "size", "clear", "contains",
            "toString", "equals", "hashCode", "valueOf"
    };

    @Override
    public void requireAutoComplete(@NonNull ContentReference content,
                                    @NonNull CharPosition position,
                                    @NonNull CompletionPublisher publisher,
                                    @NonNull Bundle extraArguments) {
        super.requireAutoComplete(content, position, publisher, extraArguments);

        String prefix = computePrefix(content, position);
        if (prefix.isEmpty()) return;

        String prefixLower = prefix.toLowerCase();
        int prefixLen = prefix.length();

        for (String cls : ANDROID_CLASSES) {
            if (cls.toLowerCase().startsWith(prefixLower)) {
                SimpleCompletionItem item = new SimpleCompletionItem(cls, prefixLen, cls);
                item.desc("Android class");
                item.kind(CompletionItemKind.Class);
                publisher.addItem(item);
            }
        }

        for (String method : ANDROID_METHODS) {
            if (method.toLowerCase().startsWith(prefixLower)) {
                SimpleCompletionItem item = new SimpleCompletionItem(method, prefixLen, method);
                item.desc("Android method");
                item.kind(CompletionItemKind.Method);
                publisher.addItem(item);
            }
        }
    }

    private String computePrefix(ContentReference content, CharPosition position) {
        int line = position.getLine();
        int col = position.getColumn();
        if (col == 0) return "";

        String lineContent = content.getLine(line);
        int start = col;
        while (start > 0) {
            char c = lineContent.charAt(start - 1);
            if (Character.isLetterOrDigit(c) || c == '_') {
                start--;
            } else {
                break;
            }
        }
        if (start == col) return "";
        return lineContent.substring(start, col);
    }
}
