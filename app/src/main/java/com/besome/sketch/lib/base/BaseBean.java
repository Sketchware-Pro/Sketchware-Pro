package com.besome.sketch.lib.base;



import java.lang.reflect.Field;

import java.lang.reflect.Modifier;



public class BaseBean {
  
  
  public String toString(BaseBean baseBean) {

		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder.append("[");

		Field[] fields = baseBean.getClass().getFields();

		for (int i = 0; i < fields.length; i++) {

			Field field = fields[i];

			if (!Modifier.isStatic(field.getModifiers()) && Modifier.isPublic(field.getModifiers())) {

				try {

					stringBuilder.append(field.getName()).append("=").append(field.get(baseBean));

					if (i < fields.length - 1) {

						stringBuilder.append(",");

					}

				} catch (Exception exception) {

					exception.printStackTrace();

				}

			}

		}

		stringBuilder.append("]");

		return stringBuilder.toString();

	}
  
  
}
