package com.besome.sketch.editor.view;

import a.a.a.Gx;
import a.a.a.kC;
import a.a.a.sy;
import a.a.a.ty;
import a.a.a.wB;
import a.a.a.zB;

import android.annotation.SuppressLint;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout.LayoutParams;
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
import dev.aldi.sayuti.editor.view.ExtraViewPane;
import java.util.ArrayList;
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
       a(var1);
    }

    public ViewPane(Context var1, AttributeSet var2) {
        super(var1, var2);
       a(var1);
    }

    public sy a(String var1) {
        View var2;
        if (var1.charAt(0) == '_') {
            var2 =findViewWithTag(var1);
        } else {
            var2 =a.findViewWithTag(var1);
        }

        if (var2 == null) {
            return null;
        } else {
            return var2 instanceof sy ? (sy)var2 : null;
        }
    }

    public View a(ViewBean var1) {
        View var2 =findViewWithTag("_fab");
        Object var4;
        if (var2 != null) {
            var4 = var2;
        } else {
            ItemFloatingActionButton var6 = new ItemFloatingActionButton(this.getContext());
            var6.setTag("_fab");
            var6.setLayoutParams(new LayoutParams(-2, -2));
            var6.setMainColor(ProjectFile.getColor(this.sc_id, "color_accent"));
            var6.setFixed(true);
            if (var1 == null) {
                ViewBean var3 = new ViewBean("_fab", 16);
                LayoutBean var5 = var3.layout;
                var5.marginLeft = 16;
                var5.marginTop = 16;
                var5.marginRight = 16;
                var5.marginBottom = 16;
                var5.layoutGravity = 85;
                var6.setBean(var3);
            } else {
                var6.setBean(var1);
            }

           addView(var6);
           b(var6, var6.getBean());
            var4 = var6;
        }

        return (View)var4;
    }

    public void a() {
        ViewBean var1 = new ViewBean("root", 0);
        LayoutBean var2 = var1.layout;
        var2.width = -1;
        var2.height = -1;
        var2.orientation = 1;
        var1.parentType = 0;
        View var3 =b(var1);
        ((ItemLinearLayout)var3).setFixed(true);
       a = (ViewGroup)var3;
       a.setBackgroundColor(-1118482);
       addView(var3);
    }

    public void a(int var1, int var2, int var3, int var4) {
        Object[] var5 = a(var1, var2);
        if (var5 == null) {
           a(true);
        } else {
            if (this.d != var5) {
               a(true);
                ViewGroup var6 = (ViewGroup)var5[1];
                var6.addView(e, (Integer) var5[2]);
                if (var6 instanceof LinearLayout) {
                    e.setLayoutParams(new android.widget.LinearLayout.LayoutParams(var3, var4));
                } else if (var6 instanceof FrameLayout) {
                   e.setLayoutParams(new android.widget.FrameLayout.LayoutParams(var3, var4));
                } else {
                   e.setLayoutParams(new LayoutParams(var3, var4));
                }

               e.setVisibility(View.VISIBLE);
               d = var5;
            }
        }
    }

    public final void a(Context var1) {
       setBackgroundColor(-1);
       a();
       c();
    }

    public final void a(Rect var1, View var2, int var3, int var4) {
       c.add(new Object[]{var1, var2, var3, var4});
    }

    public void a(View var1) {
        ViewBean var2 = ((sy)var1).getBean();
        ViewGroup var3 = (ViewGroup)this.a.findViewWithTag(var2.parent);
        var3.addView(var1, var2.index);
        if (var3 instanceof ty) {
            ((ty)var3).a();
        }
    }

    public final void a(View var1, ViewBean var2) {
        LayoutBean var3 = var2.layout;
        int var4 = var3.width;
        int var5 = var3.height;
        int var6 = var4;
        if (var4 > 0) {
            var6 = (int)wB.a(this.getContext(), (float)var2.layout.width);
        }

        var4 = var5;
        if (var5 > 0) {
            var4 = (int)wB.a(this.getContext(), (float)var2.layout.height);
        }

        var1.setBackgroundColor(var2.layout.backgroundColor);
        if (var2.id.equals("root")) {
            android.widget.LinearLayout.LayoutParams var9 = new android.widget.LinearLayout.LayoutParams(var6, var4);
            var9.leftMargin = (int)wB.a(this.getContext(), (float)var2.layout.marginLeft);
            var9.topMargin = (int)wB.a(this.getContext(), (float)var2.layout.marginTop);
            var9.rightMargin = (int)wB.a(this.getContext(), (float)var2.layout.marginRight);
            var9.bottomMargin = (int)wB.a(this.getContext(), (float)var2.layout.marginBottom);
            LayoutBean var8 = var2.layout;
            var1.setPadding(var8.paddingLeft, var8.paddingTop, var8.paddingRight, var8.paddingBottom);
            var1.setLayoutParams(var9);
        } else if (var2.parentType == 0) {
            android.widget.LinearLayout.LayoutParams var7 = new android.widget.LinearLayout.LayoutParams(var6, var4);
            var7.leftMargin = (int)wB.a(this.getContext(), (float)var2.layout.marginLeft);
            var7.topMargin = (int)wB.a(this.getContext(), (float)var2.layout.marginTop);
            var7.rightMargin = (int)wB.a(this.getContext(), (float)var2.layout.marginRight);
            var7.bottomMargin = (int)wB.a(this.getContext(), (float)var2.layout.marginBottom);
            var3 = var2.layout;
            var1.setPadding(var3.paddingLeft, var3.paddingTop, var3.paddingRight, var3.paddingBottom);
            var6 = var2.layout.layoutGravity;
            if (var6 != 0) {
                var7.gravity = var6;
            }

            var7.weight = (float)var2.layout.weight;
            var1.setLayoutParams(var7);
        } else {
            android.widget.FrameLayout.LayoutParams var15 = new android.widget.FrameLayout.LayoutParams(var6, var4);
            var15.leftMargin = (int)wB.a(this.getContext(), (float)var2.layout.marginLeft);
            var15.topMargin = (int)wB.a(this.getContext(), (float)var2.layout.marginTop);
            var15.rightMargin = (int)wB.a(this.getContext(), (float)var2.layout.marginRight);
            var15.bottomMargin = (int)wB.a(this.getContext(), (float)var2.layout.marginBottom);
            var3 = var2.layout;
            var1.setPadding(var3.paddingLeft, var3.paddingTop, var3.paddingRight, var3.paddingBottom);
            var6 = var2.layout.layoutGravity;
            if (var6 != 0) {
                var15.gravity = var6;
            }

            var1.setLayoutParams(var15);
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
        var1.setTextSize((float)var2.text.textSize);
        var1.setLines(var2.text.line);
        boolean var5 = var2.text.singleLine != 0;

        var1.setSingleLine(var5);
    }

    public void a(ViewBean var1, int var2, int var3) {
        Object[] var4 =d;
        if (var4 != null) {
            View var5 = (View)var4[1];
            if (var5 instanceof LinearLayout) {
                var1.preIndex = var1.index;
                var1.index = (int) var4[2];
                var1.preParent = var1.parent;
                var1.parent = var5.getTag().toString();
                var1.parentType = 0;
            } else if (var5 instanceof ItemVerticalScrollView) {
                var1.preIndex = var1.index;
                var1.index = (int) var4[2];
                var1.preParent = var1.parent;
                var1.parent = var5.getTag().toString();
                var1.parentType = 12;
                var1.layout.height = -2;
            } else if (var5 instanceof ItemHorizontalScrollView) {
                var1.preIndex = var1.index;
                var1.index = (int) var4[2];
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
        for(var5 = 0; var4 < var3; var5 = var7) {
            View var6 = var2.getChildAt(var4);
            var7 = var5;
            if (var6 != null) {
                if (var6.getTag() == null) {
                    var7 = var5;
                } else if (var1 != null && var1.id != null && var6.getTag().equals(var1.id)) {
                    var7 = var5;
                } else if (var6.getVisibility() != View.VISIBLE) {
                    var7 = var5;
                } else {
                    ++var5;
                    if (var6 instanceof ItemLinearLayout) {
                       a(var1, (ItemLinearLayout)var6);
                        var7 = var5;
                    } else if (var6 instanceof ItemHorizontalScrollView) {
                       a(var1, (ViewGroup)var6);
                        var7 = var5;
                    } else {
                        var7 = var5;
                        if (var6 instanceof ItemVerticalScrollView) {
                           a(var1, (ViewGroup)var6);
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
            var4 = var8[0];
            var7 = var8[1];
           a(
                    new Rect(var4, var7, (int)((float)var2.getWidth() *getScaleX()) + var4, (int)((float)var2.getHeight() *getScaleY()) + var7),
                    var2,
                    -1,
                   b(var2)
            );
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
       a(
                new Rect(var4, var7, (int)((float)var2.getWidth() *getScaleX()) + var4, (int)((float)var2.getHeight() *getScaleY()) + var7),
                var2,
                -1,
               b(var2)
        );
        var7 = var3[0];
        int var8 = (int)((float)var2.getPaddingLeft() *getScaleX());
        var4 = var3[1] + (int)((float)var2.getPaddingTop() *getScaleY());
        int var9 = var2.getChildCount();
        var7 += var8;
        int var10 = 0;

        int var13;
        for(var8 = 0; var10 < var9; var7 = var13) {
            View var11 = var2.getChildAt(var10);
            if (var11 != null && var11.getTag() != null && (var1 == null || var1.id == null || !var11.getTag().equals(var1.id)) && var11.getVisibility() == View.VISIBLE) {
                label62: {
                    label61: {
                        int[] var12 = new int[2];
                        var11.getLocationOnScreen(var12);
                        if (var2.getOrientation() == LinearLayout.HORIZONTAL) {
                            var13 = ((android.widget.LinearLayout.LayoutParams)var11.getLayoutParams()).leftMargin;
                            int var14 = ((android.widget.LinearLayout.LayoutParams)var11.getLayoutParams()).rightMargin;
                            if (var5 == 1) {
                                if (var10 == 0) {
                                    var4 = var12[0] - (int)((float)var13 *getScaleX());
                                    int var15 = var3[1];
                                   a(
                                            new Rect(var7, var15, var4, (int)((float)var2.getMeasuredHeight() *getScaleY()) + var15),
                                            var2,
                                            0,
                                           b(var2) + 1
                                    );
                                    var7 = var4;
                                }

                                var4 = (int)((float)(var13 + var11.getMeasuredWidth() + var14) *getScaleX()) + var7;
                                var13 = var3[1];
                                Rect var32 = new Rect(var7, var13, var4, (int)((float)var2.getMeasuredHeight() *getScaleY()) + var13);
                                var7 = var8 + 1;
                               a(var32, var2, var8,b(var2) + 1);
                                var8 = var13;
                            } else if (var5 == 5) {
                                var4 = var12[0];
                                int var46 = (int)((float)var13 *getScaleX());
                                var13 = var3[1];
                               a(
                                        new Rect(var7, var13, var4 - var46, (int)((float)var2.getMeasuredHeight() *getScaleY()) + var13),
                                        var2,
                                        var8,
                                       b(var2) + 1
                                );
                                var4 = (int)((float)(var12[0] + var11.getMeasuredWidth() + var14) *getScaleX());
                                var7 = var8 + 1;
                                var8 = var13;
                            } else {
                                var4 = (int)((float)(var13 + var11.getMeasuredWidth() + var14) *getScaleX()) + var7;
                                var13 = var3[1];
                                Rect var33 = new Rect(var7, var13, var4, (int)((float)var2.getMeasuredHeight() *getScaleY()) + var13);
                                var7 = var8 + 1;
                               a(var33, var2, var8,b(var2) + 1);
                                var8 = var13;
                            }
                        } else {
                            int var44 = ((android.widget.LinearLayout.LayoutParams)var11.getLayoutParams()).topMargin;
                            var13 = ((android.widget.LinearLayout.LayoutParams)var11.getLayoutParams()).bottomMargin;
                            if (var6 != 16) {
                                if (var6 != 80) {
                                    var7 = var4 + (int)((float)(var44 + var11.getMeasuredHeight() + var13) *getScaleY());
                                    var13 = var3[0];
                                   a(
                                            new Rect(var13, var4, (int)((float)var2.getMeasuredWidth() *getScaleX()) + var13, var7),
                                            var2,
                                            var8,
                                           b(var2) + 1
                                    );
                                    var4 = var13;
                                    ++var8;
                                    break label62;
                                }

                                int var48 = var12[1];
                                var44 = (int)((float)var44 *getScaleY());
                                var7 = var3[0];
                               a(
                                        new Rect(var7, var4, (int)((float)var2.getMeasuredWidth() *getScaleX()) + var7, var48 - var44),
                                        var2,
                                        var8,
                                       b(var2) + 1
                                );
                                var13 = (int)((float)(var12[1] + var11.getMeasuredHeight() + var13) *getScaleY());
                                ++var8;
                                var4 = var7;
                                var7 = var13;
                                var13 = var8;
                                break label61;
                            }

                            if (var10 == 0) {
                                var7 = var12[1] - (int)((float)var44 *getScaleY());
                                int var47 = var3[0];
                               a(new Rect(var47, var4, (int)((float)var2.getMeasuredWidth() *getScaleX()) + var47, var7), var2, 0,b(var2) + 1);
                                var4 = var7;
                            }

                            var7 = var4 + (int)((float)(var44 + var11.getMeasuredHeight() + var13) *getScaleY());
                            var13 = var3[0];
                            Rect var34 = new Rect(var13, var4, (int)((float)var2.getMeasuredWidth() *getScaleX()) + var13, var7);
                            var4 = var8 + 1;
                           a(var34, var2, var8,b(var2) + 1);
                            var8 = var7;
                            var7 = var4;
                            var4 = var13;
                        }

                        var13 = var7;
                        var7 = var8;
                    }

                    var8 = var13;
                }

                if (var11 instanceof ItemLinearLayout) {
                   a(var1, (ItemLinearLayout)var11);
                } else if (var11 instanceof ItemHorizontalScrollView) {
                   a(var1, (ViewGroup)var11);
                } else if (var11 instanceof ItemVerticalScrollView) {
                   a(var1, (ViewGroup)var11);
                }

                var13 = var4;
            } else {
                var13 = var7;
                var7 = var4;
            }

            ++var10;
            var4 = var7;
        }
    }

    public void a(boolean var1) {
       e.setVisibility(View.GONE);
        ViewParent var2 =e.getParent();
        if (var2 != null) {
            ((ViewGroup)var2).removeView(this.e);
        }

        if (var1) {
           d = null;
        }
    }

    public Object[] a(int var1, int var2) {
        Object[] var3 = null;
        int var4 = 0;

        int var9;
        for(int var5 = -1; var4 <c.size(); var5 = var9) {
            Object[] var6 = (Object[])this.c.get(var4);
            Rect var7 = (Rect)var6[0];
            Object[] var8 = var3;
            var9 = var5;
            if (var1 >= var7.left) {
                var8 = var3;
                var9 = var5;
                if (var1 < var7.right) {
                    var8 = var3;
                    var9 = var5;
                    if (var2 >= var7.top) {
                        var8 = var3;
                        var9 = var5;
                        if (var2 < var7.bottom) {
                            var8 = var3;
                            var9 = var5;
                            if (var5 < (int)var6[3]) {
                                var9 = (int) var6[3];
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
        for(var2 = 0; var1 != null && var1 !=a; var1 = (View)var1.getParent()) {
            ++var2;
        }

        return var2 * 2;
    }

    public View b(ViewBean var1) {
        int var2 = var1.type;
        View var3 = switch (var2) {
            case 0 -> new ItemLinearLayout(this.getContext());
            default -> ViewPanes.a(var2, getContext());
            case 2 -> new ItemHorizontalScrollView(this.getContext());
            case 3 -> new ItemButton(this.getContext());
            case 4 -> new ItemTextView(this.getContext());
            case 5 -> new ItemEditText(this.getContext());
            case 6 -> new ItemImageView(this.getContext());
            case 7 -> new ItemWebView(this.getContext());
            case 8 -> new ItemProgressBar(this.getContext());
            case 9 -> new ItemListView(this.getContext());
            case 10 -> new ItemSpinner(this.getContext());
            case 11 -> new ItemCheckBox(this.getContext());
            case 12 -> new ItemVerticalScrollView(this.getContext());
            case 13 -> new ItemSwitch(this.getContext());
            case 14 -> new ItemSeekBar(this.getContext());
            case 15 -> new ItemCalendarView(this.getContext());
            case 17 -> new ItemAdView(this.getContext());
            case 18 -> new ItemMapView(this.getContext());
        };

        var2 = b + 1;
       b = var2;
        var3.setId(var2);
        var3.setTag(var1.id);
        ((sy)var3).setBean(var1);
       b(var3, var1);
        return var3;
    }

    public void b() {
       a(true);
       c = new ArrayList<>();
        ((ty)this.a).setChildScrollEnabled(true);
    }

    @SuppressLint("DiscouragedApi")
    public final void b(View var1, ViewBean var2) {
        ExtraViewPane.a(var1, var2, this,f);
        int var3 = var2.id.charAt(0);
        boolean var4 = true;
        if (var3 == 95) {
            LayoutParams var23 = new LayoutParams(-2, -2);
            var23.leftMargin = (int)wB.a(this.getContext(), (float)var2.layout.marginLeft);
            var23.topMargin = (int)wB.a(this.getContext(), (float)var2.layout.marginTop);
            var23.rightMargin = (int)wB.a(this.getContext(), (float)var2.layout.marginRight);
            var23.bottomMargin = (int)wB.a(this.getContext(), (float)var2.layout.marginBottom);
            var3 = var2.layout.layoutGravity;
            if ((var3 & 3) == 3) {
                var23.addRule(9);
            }

            if ((var3 & 48) == 48) {
                var23.addRule(10);
            }

            if ((var3 & 5) == 5) {
                var23.addRule(11);
            }

            if ((var3 & 80) == 80) {
                var23.addRule(12);
            }

            if ((var3 & 1) == 1) {
                var23.addRule(14);
            }

            if ((var3 & 16) == 16) {
                var23.addRule(15);
            }

            if ((var3 & 17) == 17) {
                var23.addRule(13);
            }

            var1.setLayoutParams(var23);
            if (var2.getClassInfo().b("FloatingActionButton")) {
                ImageBean var24 = var2.image;
                if (var24 != null) {
                    String var25 = var24.resName;
                    if (var25 != null && var25.length() > 0) {
                        try {
                            Bitmap var26 = BitmapFactory.decodeFile(this.f.f(var2.image.resName));
                            var3 = Math.round(this.getResources().getDisplayMetrics().density / 2.0F);
                            Bitmap var27 = Bitmap.createScaledBitmap(var26, var26.getWidth() * var3, var26.getHeight() * var3, true);
                            ((FloatingActionButton)var1).setImageBitmap(var27);
                        } catch (Exception var10) {
                        }
                    }
                }
            }

            var1.setRotation((float)var2.image.rotate);
            var1.setAlpha(var2.alpha);
            var1.setTranslationX(wB.a(this.getContext(), var2.translationX));
            var1.setTranslationY(wB.a(this.getContext(), var2.translationY));
            var1.setScaleX(var2.scaleX);
            var1.setScaleY(var2.scaleY);
            var1.setVisibility(View.VISIBLE);
        } else {
           a(var1, var2);
            var1.setRotation((float)var2.image.rotate);
            var1.setAlpha(var2.alpha);
            var1.setTranslationX(wB.a(this.getContext(), var2.translationX));
            var1.setTranslationY(wB.a(this.getContext(), var2.translationY));
            var1.setScaleX(var2.scaleX);
            var1.setScaleY(var2.scaleY);
            String var5 = var2.layout.backgroundResource;
            if (var5 != null) {
                try {
                    if (this.f.h(var5) == ProjectResourceBean.PROJECT_RES_TYPE_RESOURCE) {
                        var1.setBackgroundResource(
                               getContext().getResources().getIdentifier(var2.layout.backgroundResource, "drawable",getContext().getPackageName())
                        );
                    } else {
                        String var6 =f.f(var2.layout.backgroundResource);
                        if (var6.endsWith(".9.png")) {
                            Bitmap var18 = zB.a(var6);
                            byte[] var7 = var18.getNinePatchChunk();
                            if (NinePatch.isNinePatchChunk(var7)) {
                                Resources var28 =getResources();
                                Rect var9 = new Rect();
                                NinePatchDrawable var8 = new NinePatchDrawable(var28, var18, var7, var9, null);
                                var1.setBackground(var8);
                            } else {
                                BitmapDrawable var19 = new BitmapDrawable(this.getResources(), var6);
                                var1.setBackground(var19);
                            }
                        } else {
                            Bitmap var20 = BitmapFactory.decodeFile(var6);
                            var3 = Math.round(this.getResources().getDisplayMetrics().density / 2.0F);
                            Bitmap var29 = Bitmap.createScaledBitmap(var20, var20.getWidth() * var3, var20.getHeight() * var3, true);
                            BitmapDrawable var21 = new BitmapDrawable(this.getResources(), var29);
                            var1.setBackground(var21);
                        }
                    }
                } catch (Exception var12) {
                    Log.e("DEBUG", var12.getMessage(), var12);
                }
            }

            Gx var22 = var2.getClassInfo();
            if (var22.b("LinearLayout")) {
                LinearLayout var30 = (LinearLayout)var1;
                var30.setOrientation(var2.layout.orientation);
                var30.setWeightSum((float)var2.layout.weightSum);
                if (var1 instanceof ItemLinearLayout) {
                    ((ItemLinearLayout)var1).setLayoutGravity(var2.layout.gravity);
                }
            }

            if (var22.a("TextView")) {
                TextView var31 = (TextView)var1;
               a(var31, var2);
                if (!var22.b("Button") && !var22.b("Switch")) {
                    var31.setGravity(var2.layout.gravity);
                } else {
                    var3 = var2.layout.gravity;
                    if (var3 == 0) {
                        var31.setGravity(17);
                    } else {
                        var31.setGravity(var3);
                    }
                }
            }

            if (var22.b("EditText")) {
               a((EditText)var1, var2);
            }

            if (var22.b("ImageView")) {
                if (this.f.h(var2.image.resName) == ProjectResourceBean.PROJECT_RES_TYPE_RESOURCE) {
                    ((ImageView)var1)
                            .setImageResource(this.getContext().getResources().getIdentifier(var2.image.resName, "drawable",getContext().getPackageName()));
                } else if (var2.image.resName.equals("default_image")) {
                    ((ImageView)var1).setImageResource(2131165522);
                } else {
                    try {
                        Bitmap var32 = BitmapFactory.decodeFile(this.f.f(var2.image.resName));
                        var3 = Math.round(this.getResources().getDisplayMetrics().density / 2.0F);
                        var32 = Bitmap.createScaledBitmap(var32, var32.getWidth() * var3, var32.getHeight() * var3, true);
                        ((ImageView)var1).setImageBitmap(var32);
                    } catch (Exception var11) {
                        ((ImageView)var1).setImageResource(2131165522);
                    }
                }

                ((ImageView)var1).setScaleType(ScaleType.valueOf(var2.image.scaleType));
            }

            if (var22.a("CompoundButton")) {
                CompoundButton var34 = (CompoundButton)var1;
                if (var2.checked == 0) {
                    var4 = false;
                }

                var34.setChecked(var4);
            }

            if (var22.b("SeekBar")) {
                SeekBar var35 = (SeekBar)var1;
                var35.setProgress(var2.progress);
                var35.setMax(var2.max);
            }

            if (var22.b("ProgressBar")) {
                ((ItemProgressBar)var1).setProgressBarStyle(var2.progressStyle);
            }

            if (var22.b("CalendarView")) {
                ((CalendarView)var1).setFirstDayOfWeek(var2.firstDayOfWeek);
            }

            if (var22.b("AdView")) {
                ((ItemAdView)var1).setAdSize(var2.adSize);
            }

            var1.setVisibility(View.VISIBLE);
        }
    }

    public final void c() {
       e = new TextView(this.getContext());
       e.setBackgroundResource(2131165670);
        android.widget.LinearLayout.LayoutParams var1 = new android.widget.LinearLayout.LayoutParams(-2, -2);
       e.setLayoutParams(var1);
       e.setVisibility(View.GONE);
    }

    public void c(ViewBean var1) {
       a(var1, (ItemLinearLayout)this.a);
    }

    public sy d(ViewBean var1) {
        View var2 = a.findViewWithTag(var1.id);
        if (var1.id.charAt(0) == '_') {
            var2 =findViewWithTag(var1.id);
        }

        String var3 = var1.preParent;
        if (var3 != null && var3.length() > 0 && !var1.parent.equals(var1.preParent)) {
            ViewGroup var4 = a.findViewWithTag(var1.preParent);
            var4.removeView(var2);
            ((ty)var4).a();
           a(var2);
        } else if (var1.index != var1.preIndex) {
            ((ViewGroup) a.findViewWithTag(var1.parent)).removeView(var2);
           a(var2);
        }

        var1.preId = "";
        var1.preIndex = -1;
        var1.preParent = "";
        var1.preParentType = -1;
        var2.setVisibility(View.VISIBLE);
        return (sy)var2;
    }

    public void d() {
       a.removeAllViews();
    }

    public void e() {
        View var1 =findViewWithTag("_fab");
        if (var1 != null) {
           removeView(var1);
        }
    }

    public void e(ViewBean var1) {
       d = null;
       c(var1);
        ((ty)this.a).setChildScrollEnabled(false);
    }

    public void f(ViewBean var1) {
        ViewGroup var2 = this.a.findViewWithTag(var1.parent);
        var2.removeView(this.a.findViewWithTag(var1.id));
        if (var2 instanceof ty) {
            ((ty)var2).a();
        }
    }

    public sy g(ViewBean var1) {
        String var2 = var1.preId;
        if (var2 != null && var2.length() > 0 && !var1.preId.equals(var1.id)) {
           a.findViewWithTag(var1.preId).setTag(var1.id);
            var1.preId = "";
        }

        View var3;
        if (var1.id.charAt(0) == '_') {
            var3 =findViewWithTag(var1.id);
        } else {
            var3 =a.findViewWithTag(var1.id);
        }

       b(var3, var1);
        return (sy)var3;
    }

    public ItemFloatingActionButton getFab() {
        View var1 =findViewWithTag("_fab");
        return var1 == null ? null : (ItemFloatingActionButton)var1;
    }

    public kC getResourceManager() {
        return f;
    }

    public ViewGroup getRoot() {
        return a;
    }

    public void setResourceManager(kC var1) {
       f = var1;
    }

    public void setScId(String var1) {
       sc_id = var1;
    }
}
