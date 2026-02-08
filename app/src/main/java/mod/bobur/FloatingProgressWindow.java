package mod.bobur;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.google.android.material.progressindicator.CircularProgressIndicator;

import pro.sketchware.databinding.WindowProgressBinding;

public class FloatingProgressWindow {

    private WindowManager windowManager;
    private WindowProgressBinding binding;
    private WindowManager.LayoutParams params;
    private final Handler handler = new Handler(Looper.getMainLooper());

    private final int maxValue = 20;
    private int initialX;
    private int initialY;
    private float initialTouchX;
    private float initialTouchY;

    @SuppressLint({"InflateParams", "ClickableViewAccessibility"})
    public void start(Context context) {
        handler.post(() -> {
            if (!Settings.canDrawOverlays(context)) return;
            if (binding == null) {
                windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
                binding = WindowProgressBinding.inflate(LayoutInflater.from(context));

                CircularProgressIndicator progress = binding.progress;
                progress.setMax(maxValue);
                progress.setProgress(0);

                binding.text.setText("0/" + maxValue);

                params = new WindowManager.LayoutParams(
                        WindowManager.LayoutParams.WRAP_CONTENT,
                        WindowManager.LayoutParams.WRAP_CONTENT,
                        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                        PixelFormat.TRANSLUCENT
                );

                params.gravity = Gravity.TOP | Gravity.START;
                params.x = 200;
                params.y = 400;

                binding.getRoot().setOnTouchListener((v, event) -> {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            initialX = params.x;
                            initialY = params.y;
                            initialTouchX = event.getRawX();
                            initialTouchY = event.getRawY();
                            return true;
                        case MotionEvent.ACTION_MOVE:
                            params.x = initialX + (int) (event.getRawX() - initialTouchX);
                            params.y = initialY + (int) (event.getRawY() - initialTouchY);
                            windowManager.updateViewLayout(binding.getRoot(), params);
                            return true;
                    }
                    return false;
                });
            }

            if (binding.getRoot().getParent() == null) {
                windowManager.addView(binding.getRoot(), params);
            } else {
                binding.getRoot().setVisibility(View.VISIBLE);
            }
        });
    }

    public void updateProgress(int value) {
        handler.post(() -> {
            if (binding == null) return;
            int v = Math.max(0, Math.min(value, maxValue));
            binding.progress.setProgressCompat(v, true);
            binding.text.setText(v + "/" + maxValue);
        });
    }

    public void stop() {
        handler.post(() -> {
            if (binding != null && binding.getRoot().getVisibility() == View.VISIBLE) {
                binding.getRoot().setVisibility(View.GONE);
            }
        });
    }

    public void exit() {
        handler.post(() -> {
            if (windowManager != null && binding != null) {
                if (binding.getRoot().getParent() != null) {
                    windowManager.removeView(binding.getRoot());
                }
                binding = null;
            }
        });
    }
}
