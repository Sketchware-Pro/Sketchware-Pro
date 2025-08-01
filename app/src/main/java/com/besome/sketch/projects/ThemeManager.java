package com.besome.sketch.projects;

import android.graphics.Color;
import java.util.Random;

public class ThemeManager {
    
    public static class ThemePreset {
        public String name;
        public int colorAccent;
        public int colorPrimary;
        public int colorPrimaryDark;
        public int colorControlHighlight;
        public int colorControlNormal;
        
        public ThemePreset(String name, int colorAccent, int colorPrimary, int colorPrimaryDark, 
                          int colorControlHighlight, int colorControlNormal) {
            this.name = name;
            this.colorAccent = colorAccent;
            this.colorPrimary = colorPrimary;
            this.colorPrimaryDark = colorPrimaryDark;
            this.colorControlHighlight = colorControlHighlight;
            this.colorControlNormal = colorControlNormal;
        }
    }
    
    private static final ThemePreset[] THEME_PRESETS = {
        // Material Design 3 - Purple
        new ThemePreset("✨ Purple Elegance", 
            0xFF03DAC5, 0xFF6200EE, 0xFF3700B3, 0xFFE8EAF6, 0xFFBDBDBD),
        
        // Material Design 3 - Blue
        new ThemePreset("🌊 Ocean Blue", 
            0xFF03DAC5, 0xFF1976D2, 0xFF0D47A1, 0xFFE3F2FD, 0xFFBDBDBD),
        
        // Material Design 3 - Green
        new ThemePreset("🌿 Nature Green", 
            0xFF03DAC5, 0xFF388E3C, 0xFF1B5E20, 0xFFE8F5E8, 0xFFBDBDBD),
        
        // Material Design 3 - Red
        new ThemePreset("🔥 Fire Red", 
            0xFF03DAC5, 0xFFD32F2F, 0xFFB71C1C, 0xFFFFEBEE, 0xFFBDBDBD),
        
        // Material Design 3 - Orange
        new ThemePreset("🍊 Sunset Orange", 
            0xFF03DAC5, 0xFFF57C00, 0xFFE65100, 0xFFFFF3E0, 0xFFBDBDBD),
        
        // Material Design 3 - Teal
        new ThemePreset("💎 Teal Gem", 
            0xFF03DAC5, 0xFF00796B, 0xFF004D40, 0xFFE0F2F1, 0xFFBDBDBD),
        
        // Material Design 3 - Indigo
        new ThemePreset("🌙 Midnight Indigo", 
            0xFF03DAC5, 0xFF3F51B5, 0xFF1A237E, 0xFFE8EAF6, 0xFFBDBDBD),
        
        // Material Design 3 - Pink
        new ThemePreset("🌸 Cherry Blossom", 
            0xFF03DAC5, 0xFFC2185B, 0xFF880E4F, 0xFFFCE4EC, 0xFFBDBDBD),
        
        // Dark Theme - Purple
        new ThemePreset("🌑 Dark Purple", 
            0xFF03DAC5, 0xFFBB86FC, 0xFF3700B3, 0xFF1F1F1F, 0xFF666666),
        
        // Dark Theme - Blue
        new ThemePreset("🌑 Dark Blue", 
            0xFF03DAC5, 0xFF64B5F6, 0xFF1976D2, 0xFF1F1F1F, 0xFF666666),
        
        // Light Theme - Purple
        new ThemePreset("☀️ Light Purple", 
            0xFF03DAC5, 0xFF6200EE, 0xFF3700B3, 0xFFF3E5F5, 0xFFE1BEE7),
        
        // Light Theme - Blue
        new ThemePreset("☀️ Light Blue", 
            0xFF03DAC5, 0xFF1976D2, 0xFF0D47A1, 0xFFE3F2FD, 0xFFBBDEFB)
    };
    
    public static ThemePreset[] getThemePresets() {
        return THEME_PRESETS;
    }
    
    public static ThemePreset generateRandomTheme() {
        Random random = new Random();
        
        // Generate a random primary color
        int primaryColor = Color.HSVToColor(new float[]{
            random.nextFloat() * 360, // Hue
            0.6f + random.nextFloat() * 0.3f, // Saturation (0.6-0.9)
            0.5f + random.nextFloat() * 0.4f  // Value (0.5-0.9)
        });
        
        // Generate a dark primary color based on the primary color
        int primaryDarkColor = darkenColor(primaryColor, 0.3f);
        
        // Generate a complementary accent color
        float[] hsv = new float[3];
        Color.colorToHSV(primaryColor, hsv);
        hsv[0] = (hsv[0] + 180) % 360; // Complementary color
        hsv[1] = 0.7f + random.nextFloat() * 0.2f; // Saturation (0.7-0.9)
        hsv[2] = 0.8f + random.nextFloat() * 0.2f; // Value (0.8-1.0)
        int accentColor = Color.HSVToColor(hsv);
        
        // Generate control colors based on the primary color
        int controlHighlightColor = lightenColor(primaryColor, 0.9f);
        int controlNormalColor = Color.GRAY;
        
        return new ThemePreset("Random Theme", accentColor, primaryColor, primaryDarkColor, 
                              controlHighlightColor, controlNormalColor);
    }
    
    private static int darkenColor(int color, float factor) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] *= (1 - factor); // Reduce the value (brightness)
        return Color.HSVToColor(hsv);
    }
    
    private static int lightenColor(int color, float factor) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] = Math.min(1.0f, hsv[2] + factor); // Increase the value (brightness)
        hsv[1] = Math.max(0.0f, hsv[1] - factor * 0.5f); // Reduce the saturation
        return Color.HSVToColor(hsv);
    }
    
    public static int[] getThemeColors(ThemePreset theme) {
        return new int[]{
            theme.colorAccent,
            theme.colorPrimary,
            theme.colorPrimaryDark,
            theme.colorControlHighlight,
            theme.colorControlNormal
        };
    }
} 