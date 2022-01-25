package com.sketchware.remod;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.LinearLayout;
import io.github.rosemoe.sora.widget.CodeEditor;
import io.github.rosemoe.sora.widget.SymbolChannel;
import android.view.*;
import android.view.View.*;
import android.text.*;
import android.text.style.*;

public class SymbolInputView extends LinearLayout {


    public SymbolInputView(Context context) {
        super(context);
     setBackgroundColor(Color.TRANSPARENT);
        setOrientation(HORIZONTAL);
    }

    public SymbolInputView(Context context, AttributeSet attrs) {
        super(context, attrs);
   setBackgroundColor(Color.TRANSPARENT);
        setOrientation(HORIZONTAL);
    }

    public SymbolInputView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
  setBackgroundColor(Color.TRANSPARENT);
        setOrientation(HORIZONTAL);
    }

    public SymbolInputView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
          setBackgroundColor(Color.TRANSPARENT);
        setOrientation(HORIZONTAL);
    }

    private SymbolChannel channel;

    public void bindEditor(CodeEditor editor) {
        channel = editor.createNewSymbolChannel();
    }

    public void removeSymbols() {
        removeAllViews();
    }


    public void addSymbols(String[] display, final String[] insertText) {
        int count = Math.max(display.length, insertText.length);
		
        for (int i = 0; i < count; i++) {
           final Button btn = new Button(getContext(), null, android.R.attr.buttonStyleSmall);
            btn.setText(display[i]);
			 btn.setTypeface(null, android.graphics.Typeface.BOLD);
			 ////drak mod this code in off
			 int nightModeFlags = getResources().getConfiguration().uiMode & android.content.res.Configuration.UI_MODE_NIGHT_MASK; 
if (nightModeFlags == android.content.res.Configuration.UI_MODE_NIGHT_YES)
 { 
			btn.setTextColor(0xFFFFFFFF);
			
			btn.setTextSize(15);
			} else { 
				btn.setTextColor(0xFFFFFFFF);
				btn.setTextSize(16);
				}
				///end code
		//	btn.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/myfont.ttf"), 0);
            btn.setBackground(new ColorDrawable(0));
            addView(btn, new LinearLayout.LayoutParams(-2, -1));
             final int finalI = i;
            
			btn.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {

channel.insertSymbol(btn.getText().toString(), 1);
          
					}
				});
        }
    }

}
