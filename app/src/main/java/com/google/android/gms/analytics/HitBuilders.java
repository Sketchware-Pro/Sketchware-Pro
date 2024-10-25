package com.google.android.gms.analytics;

import java.util.HashMap;
import java.util.Map;


public class HitBuilders {
    public HitBuilders() {
    }

    public static class EventBuilder extends HitBuilders.HitBuilder<HitBuilders.EventBuilder> {
        public EventBuilder() {
        }

        public HitBuilders.EventBuilder setAction(String var1) {
            return this;
        }

        public HitBuilders.EventBuilder setCategory(String var1) {
            return this;
        }

        public HitBuilders.EventBuilder setLabel(String var1) {
            return this;
        }

        public HitBuilders.EventBuilder setValue(long var1) {
            return this;
        }
    }

    public static class HitBuilder<T extends HitBuilders.HitBuilder> {

        public HitBuilder() {
        }

        public Map<String, String> build() {
            return new HashMap<>();
        }
    }

    public static class ScreenViewBuilder extends HitBuilders.HitBuilder<HitBuilders.ScreenViewBuilder> {
        public ScreenViewBuilder() {
        }
    }
}
