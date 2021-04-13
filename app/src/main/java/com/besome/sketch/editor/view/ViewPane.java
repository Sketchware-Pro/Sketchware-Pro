//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.besome.sketch.editor.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.NinePatch;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.NinePatchDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.CalendarView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.besome.sketch.beans.ImageBean;
import com.besome.sketch.beans.LayoutBean;
import com.besome.sketch.beans.ProjectResourceBean;
import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.editor.view.item.ItemAdView;
import com.besome.sketch.editor.view.item.ItemButton;
import com.besome.sketch.editor.view.item.ItemCalendarView;
import com.besome.sketch.editor.view.item.ItemCheckBox;
import com.besome.sketch.editor.view.item.ItemEditText;
import com.besome.sketch.editor.view.item.ItemFloatingActionButton;
import com.besome.sketch.editor.view.item.ItemHorizontalScrollView;
import com.besome.sketch.editor.view.item.ItemImageView;
import com.besome.sketch.editor.view.item.ItemLinearLayout;
import com.besome.sketch.editor.view.item.ItemListView;
import com.besome.sketch.editor.view.item.ItemMapView;
import com.besome.sketch.editor.view.item.ItemProgressBar;
import com.besome.sketch.editor.view.item.ItemSeekBar;
import com.besome.sketch.editor.view.item.ItemSpinner;
import com.besome.sketch.editor.view.item.ItemSwitch;
import com.besome.sketch.editor.view.item.ItemTextView;
import com.besome.sketch.editor.view.item.ItemVerticalScrollView;
import com.besome.sketch.editor.view.item.ItemWebView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import a.a.a.Gx;
import a.a.a.kC;
import a.a.a.sy;
import a.a.a.ty;
import a.a.a.wB;
import a.a.a.zB;
import dev.aldi.sayuti.editor.view.ExtraViewPane;
import mod.agus.jcoderz.editor.view.ViewPanes;
import mod.hey.studios.util.ProjectFile;

public class ViewPane extends RelativeLayout {

    public ViewGroup a = null;
    public int b = 99;
    public ArrayList<Object[]> c = new ArrayList<>();
    public Object[] d = null;
    public TextView e;
    public kC f;
    public String sc_id;

    public ViewPane(Context var1) {
        super(var1);
        this.a(var1);
    }

    public ViewPane(Context var1, AttributeSet var2) {
        super(var1, var2);
        this.a(var1);
    }

    public sy a(String var1) {
        View var2;
        if (var1.charAt(0) == '_') {
            var2 = this.findViewWithTag(var1);
        } else {
            var2 = this.a.findViewWithTag(var1);
        }

        if (var2 == null) {
            return null;
        } else {
            return var2 instanceof sy ? (sy) var2 : null;
        }
    }

    public View a(ViewBean var1) {
        View var2 = this.findViewWithTag("_fab");
        View var4;
        if (var2 != null) {
            var4 = var2;
        } else {
            ItemFloatingActionButton var5 = new ItemFloatingActionButton(this.getContext());
            var5.setTag("_fab");
            var5.setLayoutParams(new LayoutParams(-2, -2));
            var5.setMainColor(ProjectFile.getColor(this.sc_id, "color_accent"));
            var5.setFixed(true);
            if (var1 == null) {
                var1 = new ViewBean("_fab", 16);
                LayoutBean var3 = var1.layout;
                var3.marginLeft = 16;
                var3.marginTop = 16;
                var3.marginRight = 16;
                var3.marginBottom = 16;
                var3.layoutGravity = 85;
                var5.setBean(var1);
            } else {
                var5.setBean(var1);
            }

            this.addView(var5);
            this.b(var5, var5.getBean());
            var4 = var5;
        }

        return var4;
    }

    public void a() {
        ViewBean var1 = new ViewBean("root", 0);
        LayoutBean var2 = var1.layout;
        var2.width = -1;
        var2.height = -1;
        var2.orientation = 1;
        var1.parentType = 0;
        View var3 = this.b(var1);
        ((ItemLinearLayout) var3).setFixed(true);
        this.a = (ViewGroup) var3;
        this.a.setBackgroundColor(-1118482);
        this.addView(var3);
    }

    public void a(int var1, int var2, int var3, int var4) {
        Object[] var5 = this.a(var1, var2);
        if (var5 == null) {
            this.a(true);
        } else {
            if (this.d != var5) {
                this.a(true);
                ViewGroup var6 = (ViewGroup) var5[1];
                var6.addView(this.e, (Integer) var5[2]);
                if (var6 instanceof LinearLayout) {
                    this.e.setLayoutParams(new android.widget.LinearLayout.LayoutParams(var3, var4));
                } else if (var6 instanceof FrameLayout) {
                    this.e.setLayoutParams(new android.widget.FrameLayout.LayoutParams(var3, var4));
                } else {
                    this.e.setLayoutParams(new LayoutParams(var3, var4));
                }

                this.e.setVisibility(VISIBLE);
                this.d = var5;
            }

        }
    }

    public final void a(Context var1) {
        this.setBackgroundColor(-1);
        this.a();
        this.c();
    }

    public final void a(Rect var1, View var2, int var3, int var4) {
        this.c.add(new Object[]{var1, var2, var3, var4});
    }

    public void a(View var1) {
        ViewBean var2 = ((sy) var1).getBean();
        ViewGroup var3 = this.a.findViewWithTag(var2.parent);
        var3.addView(var1, var2.index);
        if (var3 instanceof ty) {
            ((ty) var3).a();
        }

    }

    public final void a(View var1, ViewBean var2) {
        LayoutBean var3 = var2.layout;
        int var4 = var3.width;
        int var5 = var3.height;
        int var6 = var4;
        if (var4 > 0) {
            var6 = (int) wB.a(this.getContext(), (float) var2.layout.width);
        }

        var4 = var5;
        if (var5 > 0) {
            var4 = (int) wB.a(this.getContext(), (float) var2.layout.height);
        }

        var1.setBackgroundColor(var2.layout.backgroundColor);
        android.widget.LinearLayout.LayoutParams var9;
        if (var2.id.equals("root")) {
            var9 = new android.widget.LinearLayout.LayoutParams(var6, var4);
            var9.leftMargin = (int) wB.a(this.getContext(), (float) var2.layout.marginLeft);
            var9.topMargin = (int) wB.a(this.getContext(), (float) var2.layout.marginTop);
            var9.rightMargin = (int) wB.a(this.getContext(), (float) var2.layout.marginRight);
            var9.bottomMargin = (int) wB.a(this.getContext(), (float) var2.layout.marginBottom);
            LayoutBean var8 = var2.layout;
            var1.setPadding(var8.paddingLeft, var8.paddingTop, var8.paddingRight, var8.paddingBottom);
            var1.setLayoutParams(var9);
        } else {
            LayoutBean var7;
            if (var2.parentType == 0) {
                var9 = new android.widget.LinearLayout.LayoutParams(var6, var4);
                var9.leftMargin = (int) wB.a(this.getContext(), (float) var2.layout.marginLeft);
                var9.topMargin = (int) wB.a(this.getContext(), (float) var2.layout.marginTop);
                var9.rightMargin = (int) wB.a(this.getContext(), (float) var2.layout.marginRight);
                var9.bottomMargin = (int) wB.a(this.getContext(), (float) var2.layout.marginBottom);
                var7 = var2.layout;
                var1.setPadding(var7.paddingLeft, var7.paddingTop, var7.paddingRight, var7.paddingBottom);
                var6 = var2.layout.layoutGravity;
                if (var6 != 0) {
                    var9.gravity = var6;
                }

                var9.weight = (float) var2.layout.weight;
                var1.setLayoutParams(var9);
            } else {
                android.widget.FrameLayout.LayoutParams var10 = new android.widget.FrameLayout.LayoutParams(var6, var4);
                var10.leftMargin = (int) wB.a(this.getContext(), (float) var2.layout.marginLeft);
                var10.topMargin = (int) wB.a(this.getContext(), (float) var2.layout.marginTop);
                var10.rightMargin = (int) wB.a(this.getContext(), (float) var2.layout.marginRight);
                var10.bottomMargin = (int) wB.a(this.getContext(), (float) var2.layout.marginBottom);
                var7 = var2.layout;
                var1.setPadding(var7.paddingLeft, var7.paddingTop, var7.paddingRight, var7.paddingBottom);
                var6 = var2.layout.layoutGravity;
                if (var6 != 0) {
                    var10.gravity = var6;
                }

                var1.setLayoutParams(var10);
            }
        }

    }

    public final void a(EditText var1, ViewBean var2) {
        var1.setHint(var2.text.hint);
        var1.setHintTextColor(var2.text.hintColor);
    }

    public final void a(TextView var1, ViewBean var2) {
        String var3 = var2.text.text;
        String var4 = var3;
        if (var3 != null) {
            var4 = var3;
            if (var3.length() > 0) {
                var4 = var3;
                if (var3.contains("\\n")) {
                    var4 = var2.text.text.replaceAll("\\\\n", "\n");
                }
            }
        }

        var1.setText(var4);
        var1.setTypeface(null, var2.text.textType);
        var1.setTextColor(var2.text.textColor);
        var1.setTextSize((float) var2.text.textSize);
        var1.setLines(var2.text.line);
        boolean var5;
        var5 = var2.text.singleLine != 0;

        var1.setSingleLine(var5);
    }

    public void a(ViewBean var1, int var2, int var3) {
        Object[] var4 = this.d;
        if (var4 != null) {
            View var5 = (View) var4[1];
            if (var5 instanceof LinearLayout) {
                var1.preIndex = var1.index;
                var1.index = (Integer) var4[2];
                var1.preParent = var1.parent;
                var1.parent = var5.getTag().toString();
                var1.parentType = 0;
            } else if (var5 instanceof ItemVerticalScrollView) {
                var1.preIndex = var1.index;
                var1.index = (Integer) var4[2];
                var1.preParent = var1.parent;
                var1.parent = var5.getTag().toString();
                var1.parentType = 12;
                var1.layout.height = -2;
            } else if (var5 instanceof ItemHorizontalScrollView) {
                var1.preIndex = var1.index;
                var1.index = (Integer) var4[2];
                var1.preParent = var1.parent;
                var1.parent = var5.getTag().toString();
                var1.parentType = 2;
                var1.layout.width = -2;
            }
        } else {
            var1.preIndex = var1.index;
            var1.preParent = var1.parent;
            var1.parent = "root";
            var1.parentType = 0;
            var1.index = -1;
        }

    }

    public final void a(ViewBean var1, ViewGroup var2) {
        int var3 = var2.getChildCount();
        int var4 = 0;

        int var5;
        int var7;
        for (var5 = 0; var4 < var3; var5 = var7) {
            View var6 = var2.getChildAt(var4);
            var7 = var5;
            if (var6 != null) {
                if (var1 != null && var1.id != null && var6.getTag().equals(var1.id)) {
                    var7 = var5;
                } else if (var6.getVisibility() != VISIBLE) {
                    var7 = var5;
                } else {
                    ++var5;
                    if (var6 instanceof ItemLinearLayout) {
                        this.a(var1, (ItemLinearLayout) var6);
                        var7 = var5;
                    } else if (var6 instanceof ItemHorizontalScrollView) {
                        this.a(var1, (ViewGroup) var6);
                        var7 = var5;
                    } else {
                        var7 = var5;
                        if (var6 instanceof ItemVerticalScrollView) {
                            this.a(var1, (ViewGroup) var6);
                            var7 = var5;
                        }
                    }
                }
            }

            ++var4;
        }

        if (var5 < 1) {
            int[] var8 = new int[2];
            var2.getLocationOnScreen(var8);
            var7 = var8[0];
            var4 = var8[1];
            this.a(new Rect(var7, var4, (int) ((float) var2.getWidth() * this.getScaleX()) + var7, (int) ((float) var2.getHeight() * this.getScaleY()) + var4), var2, -1, this.b(var2));
        }

    }

    public final void a(ViewBean var1, ItemLinearLayout var2) {
        int[] var3 = new int[2];
        var2.getLocationOnScreen(var3);
        int var4 = var2.getLayoutGravity();
        int var5 = var4 & 7;
        int var6 = var4 & 112;
        var4 = var3[0];
        int var7 = var3[1];
        this.a(new Rect(var4, var7, (int) ((float) var2.getWidth() * this.getScaleX()) + var4, (int) ((float) var2.getHeight() * this.getScaleY()) + var7), var2, -1, this.b(var2));
        var7 = var3[0];
        int var8 = (int) ((float) var2.getPaddingLeft() * this.getScaleX());
        var4 = var3[1] + (int) ((float) var2.getPaddingTop() * this.getScaleY());
        int var9 = var2.getChildCount();
        var7 += var8;
        int var10 = 0;

        int var13;
        for (var8 = 0; var10 < var9; var4 = var13) {
            View var11 = var2.getChildAt(var10);
            if (var11 != null && var11.getTag() != null && (var1 == null || var1.id == null || !var11.getTag().equals(var1.id)) && var11.getVisibility() == VISIBLE) {
                int[] var12 = new int[2];
                var11.getLocationOnScreen(var12);
                int var14;
                int var15;
                Rect var16;
                if (var2.getOrientation() == LinearLayout.HORIZONTAL) {
                    var13 = ((android.widget.LinearLayout.LayoutParams) var11.getLayoutParams()).leftMargin;
                    var14 = ((android.widget.LinearLayout.LayoutParams) var11.getLayoutParams()).rightMargin;
                    if (var5 == 1) {
                        if (var10 == 0) {
                            var4 = var12[0] - (int) ((float) var13 * this.getScaleX());
                            var15 = var3[1];
                            this.a(new Rect(var7, var15, var4, (int) ((float) var2.getMeasuredHeight() * this.getScaleY()) + var15), var2, 0, this.b(var2) + 1);
                            var7 = var4;
                        }

                        var4 = (int) ((float) (var13 + var11.getMeasuredWidth() + var14) * this.getScaleX()) + var7;
                        var13 = var3[1];
                        var16 = new Rect(var7, var13, var4, (int) ((float) var2.getMeasuredHeight() * this.getScaleY()) + var13);
                        var7 = var8 + 1;
                        this.a(var16, var2, var8, this.b(var2) + 1);
                        var8 = var13;
                    } else if (var5 == 5) {
                        var4 = var12[0];
                        var15 = (int) ((float) var13 * this.getScaleX());
                        var13 = var3[1];
                        this.a(new Rect(var7, var13, var4 - var15, (int) ((float) var2.getMeasuredHeight() * this.getScaleY()) + var13), var2, var8, this.b(var2) + 1);
                        var4 = (int) ((float) (var12[0] + var11.getMeasuredWidth() + var14) * this.getScaleX());
                        var7 = var8 + 1;
                        var8 = var13;
                    } else {
                        var4 = (int) ((float) (var13 + var11.getMeasuredWidth() + var14) * this.getScaleX()) + var7;
                        var13 = var3[1];
                        var16 = new Rect(var7, var13, var4, (int) ((float) var2.getMeasuredHeight() * this.getScaleY()) + var13);
                        var7 = var8 + 1;
                        this.a(var16, var2, var8, this.b(var2) + 1);
                        var8 = var13;
                    }
                } else {
                    var14 = ((android.widget.LinearLayout.LayoutParams) var11.getLayoutParams()).topMargin;
                    var13 = ((android.widget.LinearLayout.LayoutParams) var11.getLayoutParams()).bottomMargin;
                    if (var6 == 16) {
                        if (var10 == 0) {
                            var7 = var12[1] - (int) ((float) var14 * this.getScaleY());
                            var15 = var3[0];
                            this.a(new Rect(var15, var4, (int) ((float) var2.getMeasuredWidth() * this.getScaleX()) + var15, var7), var2, 0, this.b(var2) + 1);
                            var4 = var7;
                        }

                        var14 = var4 + (int) ((float) (var14 + var11.getMeasuredHeight() + var13) * this.getScaleY());
                        var13 = var3[0];
                        var16 = new Rect(var13, var4, (int) ((float) var2.getMeasuredWidth() * this.getScaleX()) + var13, var14);
                        var7 = var8 + 1;
                        this.a(var16, var2, var8, this.b(var2) + 1);
                        var8 = var14;
                        var4 = var13;
                    } else if (var6 == 80) {
                        var15 = var12[1];
                        var14 = (int) ((float) var14 * this.getScaleY());
                        var7 = var3[0];
                        this.a(new Rect(var7, var4, (int) ((float) var2.getMeasuredWidth() * this.getScaleX()) + var7, var15 - var14), var2, var8, this.b(var2) + 1);
                        var13 = (int) ((float) (var12[1] + var11.getMeasuredHeight() + var13) * this.getScaleY());
                        var14 = var8 + 1;
                        var4 = var7;
                        var8 = var13;
                        var7 = var14;
                    } else {
                        var7 = var4 + (int) ((float) (var14 + var11.getMeasuredHeight() + var13) * this.getScaleY());
                        var13 = var3[0];
                        this.a(new Rect(var13, var4, (int) ((float) var2.getMeasuredWidth() * this.getScaleX()) + var13, var7), var2, var8, this.b(var2) + 1);
                        var4 = var13;
                        var13 = var8 + 1;
                        var8 = var7;
                        var7 = var13;
                    }
                }

                if (var11 instanceof ItemLinearLayout) {
                    this.a(var1, (ItemLinearLayout) var11);
                } else if (var11 instanceof ItemHorizontalScrollView) {
                    this.a(var1, (ViewGroup) var11);
                } else if (var11 instanceof ItemVerticalScrollView) {
                    this.a(var1, (ViewGroup) var11);
                }

                var13 = var8;
                var8 = var7;
                var7 = var4;
            } else {
                var13 = var4;
            }

            ++var10;
        }

    }

    public void a(boolean var1) {
        this.e.setVisibility(GONE);
        ViewParent var2 = this.e.getParent();
        if (var2 != null) {
            ((ViewGroup) var2).removeView(this.e);
        }

        if (var1) {
            this.d = null;
        }

    }

    public Object[] a(int var1, int var2) {
        Object[] var3 = null;
        int var4 = 0;

        int var9;
        for (int var5 = -1; var4 < this.c.size(); var5 = var9) {
            Object[] var6 = this.c.get(var4);
            Rect var7 = (Rect) var6[0];
            Object[] var8 = var3;
            var9 = var5;
            if (var1 >= var7.left) {
                if (var1 < var7.right) {
                    if (var2 >= var7.top) {
                        if (var2 < var7.bottom) {
                            if (var5 < (Integer) var6[3]) {
                                var9 = (Integer) var6[3];
                                var8 = var6;
                            }
                        }
                    }
                }
            }

            ++var4;
            var3 = var8;
        }

        return var3;
    }

    public final int b(View var1) {
        int var2;
        for (var2 = 0; var1 != null && var1 != this.a; var1 = (View) var1.getParent()) {
            ++var2;
        }

        return var2 * 2;
    }

    public View b(ViewBean var1) {
        int var2 = var1.type;
        View var3;
        switch (var2) {
            case 0:
                var3 = new ItemLinearLayout(this.getContext());
                break;
            case 1:
            case 16:
            default:
                var3 = ViewPanes.a(var2, this.getContext());
                break;
            case 2:
                var3 = new ItemHorizontalScrollView(this.getContext());
                break;
            case 3:
                var3 = new ItemButton(this.getContext());
                break;
            case 4:
                var3 = new ItemTextView(this.getContext());
                break;
            case 5:
                var3 = new ItemEditText(this.getContext());
                break;
            case 6:
                var3 = new ItemImageView(this.getContext());
                break;
            case 7:
                var3 = new ItemWebView(this.getContext());
                break;
            case 8:
                var3 = new ItemProgressBar(this.getContext());
                break;
            case 9:
                var3 = new ItemListView(this.getContext());
                break;
            case 10:
                var3 = new ItemSpinner(this.getContext());
                break;
            case 11:
                var3 = new ItemCheckBox(this.getContext());
                break;
            case 12:
                var3 = new ItemVerticalScrollView(this.getContext());
                break;
            case 13:
                var3 = new ItemSwitch(this.getContext());
                break;
            case 14:
                var3 = new ItemSeekBar(this.getContext());
                break;
            case 15:
                var3 = new ItemCalendarView(this.getContext());
                break;
            case 17:
                var3 = new ItemAdView(this.getContext());
                break;
            case 18:
                var3 = new ItemMapView(this.getContext());
        }

        var2 = this.b + 1;
        this.b = var2;
        var3.setId(var2);
        var3.setTag(var1.id);
        ((sy) var3).setBean(var1);
        this.b(var3, var1);
        return var3;
    }

    public void b() {
        this.a(true);
        this.c = new ArrayList<>();
        ((ty) this.a).setChildScrollEnabled(true);
    }

    public final void b(View var1, ViewBean var2) {
        ExtraViewPane.a(var1, var2, this, this.f);
        char var3 = var2.id.charAt(0);
        boolean var4 = true;
        String var5;
        int var13;
        Bitmap var16;
        if (var3 == '_') {
            LayoutParams var19 = new LayoutParams(-2, -2);
            var19.leftMargin = (int) wB.a(this.getContext(), (float) var2.layout.marginLeft);
            var19.topMargin = (int) wB.a(this.getContext(), (float) var2.layout.marginTop);
            var19.rightMargin = (int) wB.a(this.getContext(), (float) var2.layout.marginRight);
            var19.bottomMargin = (int) wB.a(this.getContext(), (float) var2.layout.marginBottom);
            var13 = var2.layout.layoutGravity;
            if ((var13 & 3) == 3) {
                var19.addRule(9);
            }

            if ((var13 & 48) == 48) {
                var19.addRule(10);
            }

            if ((var13 & 5) == 5) {
                var19.addRule(11);
            }

            if ((var13 & 80) == 80) {
                var19.addRule(12);
            }

            if ((var13 & 1) == 1) {
                var19.addRule(14);
            }

            if ((var13 & 16) == 16) {
                var19.addRule(15);
            }

            if ((var13 & 17) == 17) {
                var19.addRule(13);
            }

            var1.setLayoutParams(var19);
            if (var2.getClassInfo().b("FloatingActionButton")) {
                ImageBean var21 = var2.image;
                if (var21 != null) {
                    var5 = var21.resName;
                    if (var5 != null && var5.length() > 0) {
                        try {
                            var16 = BitmapFactory.decodeFile(this.f.f(var2.image.resName));
                            var13 = Math.round(this.getResources().getDisplayMetrics().density / 2.0F);
                            var16 = Bitmap.createScaledBitmap(var16, var16.getWidth() * var13, var16.getHeight() * var13, true);
                            ((FloatingActionButton) var1).setImageBitmap(var16);
                        } catch (Exception ignored) {
                        }
                    }
                }
            }

            var1.setRotation((float) var2.image.rotate);
            var1.setAlpha(var2.alpha);
            var1.setTranslationX(wB.a(this.getContext(), var2.translationX));
            var1.setTranslationY(wB.a(this.getContext(), var2.translationY));
            var1.setScaleX(var2.scaleX);
            var1.setScaleY(var2.scaleY);
            var1.setVisibility(VISIBLE);
        } else {
            this.a(var1, var2);
            var1.setRotation((float) var2.image.rotate);
            var1.setAlpha(var2.alpha);
            var1.setTranslationX(wB.a(this.getContext(), var2.translationX));
            var1.setTranslationY(wB.a(this.getContext(), var2.translationY));
            var1.setScaleX(var2.scaleX);
            var1.setScaleY(var2.scaleY);
            var5 = var2.layout.backgroundResource;
            Bitmap var7;
            if (var5 != null) {
                try {
                    if (this.f.h(var5) == ProjectResourceBean.PROJECT_RES_TYPE_RESOURCE) {
                        var1.setBackgroundResource(this.getContext().getResources().getIdentifier(var2.layout.backgroundResource, "drawable", this.getContext().getPackageName()));
                    } else {
                        String var6 = this.f.f(var2.layout.backgroundResource);
                        if (var6.endsWith(".9.png")) {
                            var7 = zB.a(var6);
                            byte[] var14 = var7.getNinePatchChunk();
                            if (NinePatch.isNinePatchChunk(var14)) {
                                Resources var8 = this.getResources();
                                Rect var9 = new Rect();
                                NinePatchDrawable var17 = new NinePatchDrawable(var8, var7, var14, var9, null);
                                var1.setBackground(var17);
                            } else {
                                BitmapDrawable var15 = new BitmapDrawable(this.getResources(), var6);
                                var1.setBackground(var15);
                            }
                        } else {
                            var16 = BitmapFactory.decodeFile(var6);
                            var13 = Math.round(this.getResources().getDisplayMetrics().density / 2.0F);
                            var16 = Bitmap.createScaledBitmap(var16, var16.getWidth() * var13, var16.getHeight() * var13, true);
                            BitmapDrawable var20 = new BitmapDrawable(this.getResources(), var16);
                            var1.setBackground(var20);
                        }
                    }
                } catch (Exception var12) {
                    Log.e("DEBUG", var12.getMessage(), var12);
                }
            }

            Gx var18 = var2.getClassInfo();
            if (var18.b("LinearLayout")) {
                LinearLayout var22 = (LinearLayout) var1;
                var22.setOrientation(var2.layout.orientation);
                var22.setWeightSum((float) var2.layout.weightSum);
                if (var1 instanceof ItemLinearLayout) {
                    ((ItemLinearLayout) var1).setLayoutGravity(var2.layout.gravity);
                }
            }

            if (var18.a("TextView")) {
                TextView var23 = (TextView) var1;
                this.a(var23, var2);
                if (!var18.b("Button") && !var18.b("Switch")) {
                    var23.setGravity(var2.layout.gravity);
                } else {
                    var13 = var2.layout.gravity;
                    if (var13 == 0) {
                        var23.setGravity(17);
                    } else {
                        var23.setGravity(var13);
                    }
                }
            }

            if (var18.b("EditText")) {
                this.a((EditText) var1, var2);
            }

            if (var18.b("ImageView")) {
                if (this.f.h(var2.image.resName) == ProjectResourceBean.PROJECT_RES_TYPE_RESOURCE) {
                    ((ImageView) var1).setImageResource(this.getContext().getResources().getIdentifier(var2.image.resName, "drawable", this.getContext().getPackageName()));
                } else if (var2.image.resName.equals("default_image")) {
                    ((ImageView) var1).setImageResource(2131165522);
                } else {
                    try {
                        var7 = BitmapFactory.decodeFile(this.f.f(var2.image.resName));
                        var13 = Math.round(this.getResources().getDisplayMetrics().density / 2.0F);
                        var7 = Bitmap.createScaledBitmap(var7, var7.getWidth() * var13, var7.getHeight() * var13, true);
                        ((ImageView) var1).setImageBitmap(var7);
                    } catch (Exception var11) {
                        ((ImageView) var1).setImageResource(2131165522);
                    }
                }

                ((ImageView) var1).setScaleType(ScaleType.valueOf(var2.image.scaleType));
            }

            if (var18.a("CompoundButton")) {
                CompoundButton var24 = (CompoundButton) var1;
                if (var2.checked == 0) {
                    var4 = false;
                }

                var24.setChecked(var4);
            }

            if (var18.b("SeekBar")) {
                SeekBar var25 = (SeekBar) var1;
                var25.setProgress(var2.progress);
                var25.setMax(var2.max);
            }

            if (var18.b("ProgressBar")) {
                ((ItemProgressBar) var1).setProgressBarStyle(var2.progressStyle);
            }

            if (var18.b("CalendarView")) {
                ((CalendarView) var1).setFirstDayOfWeek(var2.firstDayOfWeek);
            }

            if (var18.b("AdView")) {
                ((ItemAdView) var1).setAdSize(var2.adSize);
            }

            var1.setVisibility(VISIBLE);
        }
    }

    public final void c() {
        this.e = new TextView(this.getContext());
        this.e.setBackgroundResource(2131165670);
        android.widget.LinearLayout.LayoutParams var1 = new android.widget.LinearLayout.LayoutParams(-2, -2);
        this.e.setLayoutParams(var1);
        this.e.setVisibility(GONE);
    }

    public void c(ViewBean var1) {
        this.a(var1, (ItemLinearLayout) this.a);
    }

    public sy d(ViewBean var1) {
        View var2 = this.a.findViewWithTag(var1.id);
        if (var1.id.charAt(0) == '_') {
            var2 = this.findViewWithTag(var1.id);
        }

        String var3 = var1.preParent;
        if (var3 != null && var3.length() > 0 && !var1.parent.equals(var1.preParent)) {
            ViewGroup var4 = this.a.findViewWithTag(var1.preParent);
            var4.removeView(var2);
            ((ty) var4).a();
            this.a(var2);
        } else if (var1.index != var1.preIndex) {
            ((ViewGroup) this.a.findViewWithTag(var1.parent)).removeView(var2);
            this.a(var2);
        }

        var1.preId = "";
        var1.preIndex = -1;
        var1.preParent = "";
        var1.preParentType = -1;
        var2.setVisibility(VISIBLE);
        return (sy) var2;
    }

    public void d() {
        this.a.removeAllViews();
    }

    public void e() {
        View var1 = this.findViewWithTag("_fab");
        if (var1 != null) {
            this.removeView(var1);
        }
    }

    public void e(ViewBean var1) {
        this.d = null;
        this.c(var1);
        ((ty) this.a).setChildScrollEnabled(false);
    }

    public void f(ViewBean var1) {
        ViewGroup var2 = this.a.findViewWithTag(var1.parent);
        var2.removeView(this.a.findViewWithTag(var1.id));
        if (var2 instanceof ty) {
            ((ty) var2).a();
        }

    }

    public sy g(ViewBean var1) {
        String var2 = var1.preId;
        if (var2 != null && var2.length() > 0 && !var1.preId.equals(var1.id)) {
            this.a.findViewWithTag(var1.preId).setTag(var1.id);
            var1.preId = "";
        }

        View var3;
        if (var1.id.charAt(0) == '_') {
            var3 = this.findViewWithTag(var1.id);
        } else {
            var3 = this.a.findViewWithTag(var1.id);
        }

        this.b(var3, var1);
        return (sy) var3;
    }

    public ItemFloatingActionButton getFab() {
        View var1 = this.findViewWithTag("_fab");
        return var1 == null ? null : (ItemFloatingActionButton) var1;
    }

    public kC getResourceManager() {
        return this.f;
    }

    public void setResourceManager(kC var1) {
        this.f = var1;
    }

    public ViewGroup getRoot() {
        return this.a;
    }

    public void setScId(String var1) {
        this.sc_id = var1;
    }
}