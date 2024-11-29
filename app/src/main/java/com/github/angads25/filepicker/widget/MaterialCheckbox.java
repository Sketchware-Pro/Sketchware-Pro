/*
 * Copyright (C) 2017 Angad Singh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.angads25.filepicker.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.material.color.MaterialColors;

/**
 * <p>
 * Created by Angad on 19-05-2017.
 * </p>
 */

public class MaterialCheckbox extends View {
    private int minDim;
    private Paint paint;
    private RectF bounds;
    private boolean checked;
    private OnCheckedChangeListener onCheckedChangeListener;
    private Path tick;

    public MaterialCheckbox(Context context) {
        super(context);
        initView();
    }

    public MaterialCheckbox(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MaterialCheckbox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public void initView() {
        checked = false;
        tick = new Path();
        paint = new Paint();
        bounds = new RectF();
        OnClickListener onClickListener = v -> {
            setChecked(!checked);
            onCheckedChangeListener.onCheckedChanged(this, isChecked());
        };

        setOnClickListener(onClickListener);
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        if (isChecked()) {
            paint.reset();
            paint.setAntiAlias(true);
            bounds.set((float) minDim / 10, (float) minDim / 10, minDim - ((float) minDim / 10), minDim - ((float) minDim / 10));
            paint.setColor(MaterialColors.getColor(this, com.google.android.material.R.attr.colorPrimary));
            canvas.drawRoundRect(bounds, (float) minDim / 8, (float) minDim / 8, paint);

            paint.setColor(Color.parseColor("#FFFFFF"));
            paint.setStrokeWidth((float) minDim / 10);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeJoin(Paint.Join.BEVEL);
            canvas.drawPath(tick, paint);
        } else {
            paint.reset();
            paint.setAntiAlias(true);
            bounds.set((float) minDim / 10, (float) minDim / 10, minDim - ((float) minDim / 10), minDim - ((float) minDim / 10));
            paint.setColor(Color.parseColor("#C1C1C1"));
            canvas.drawRoundRect(bounds, (float) minDim / 8, (float) minDim / 8, paint);

            bounds.set((float) minDim / 5, (float) minDim / 5, minDim - ((float) minDim / 5), minDim - ((float) minDim / 5));
            paint.setColor(Color.parseColor("#FFFFFF"));
            canvas.drawRect(bounds, paint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = getMeasuredHeight();
        int width = getMeasuredWidth();
        minDim = Math.min(width, height);
        bounds.set((float) minDim / 10, (float) minDim / 10, minDim - ((float) minDim / 10), minDim - ((float) minDim / 10));
        tick.moveTo((float) minDim / 4, (float) minDim / 2);
        tick.lineTo(minDim / 2.5f, minDim - ((float) minDim / 3));

        tick.moveTo(minDim / 2.75f, minDim - (minDim / 3.25f));
        tick.lineTo(minDim - ((float) minDim / 4), (float) minDim / 3);
        setMeasuredDimension(width, height);
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
        invalidate();
    }

    public void setOnCheckedChangedListener(OnCheckedChangeListener onCheckedChangeListener) {
        this.onCheckedChangeListener = onCheckedChangeListener;
    }
}
