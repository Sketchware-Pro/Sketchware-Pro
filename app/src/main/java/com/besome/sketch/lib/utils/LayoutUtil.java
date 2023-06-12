package com.besome.sketch.lib.utils;


import android.content.Context; 

import android.content.res.Resources; 

import android.util.DisplayMetrics; 

import android.util.TypedValue; 

import android.view.LayoutInflater; 

import android.view.View; 

import android.view.ViewGroup; 


public class LayoutUtil {
  
  private static LayoutInflater sInflater;

	public static float getDip(Context context, float f) {

		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, f, context.getResources().getDisplayMetrics());

	}

	

	public static View inflate(Context context, int resId) {

		if (sInflater == null) {

			sInflater = LayoutInflater.from(context);

		}

		return sInflater.inflate(resId, null);

	}

	

	public static View inflate(Context context, ViewGroup parent, int resId) {

		if (sInflater == null) {

			sInflater = LayoutInflater.from(context);

		}

		return sInflater.inflate(resId, parent, true);

	}

	

	public static View inflate(Context context, ViewGroup parent, int resId, boolean attachToRoot) {

		if (sInflater == null) {

			sInflater = LayoutInflater.from(context);

		}

		return sInflater.inflate(resId, parent, attachToRoot);

	}
}
