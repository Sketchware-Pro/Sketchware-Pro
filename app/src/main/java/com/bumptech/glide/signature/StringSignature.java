package com.bumptech.glide.signature;

import androidx.annotation.NonNull;

import com.bumptech.glide.load.Key;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class StringSignature implements Key {

    public final String signature;

    public StringSignature(String key) {
        if (key == null) {
            throw new NullPointerException("Signature cannot be null!");
        }
        signature = key;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else if (other != null && StringSignature.class == other.getClass()) {
            return signature.equals(((StringSignature) other).signature);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return signature.hashCode();
    }

    @NonNull
    @Override
    public String toString() {
        return "StringSignature{signature='" + signature + "'}";
    }

    @Override
    public void updateDiskCacheKey(MessageDigest messageDigest) {
        messageDigest.update(signature.getBytes(StandardCharsets.UTF_8));
    }
}
