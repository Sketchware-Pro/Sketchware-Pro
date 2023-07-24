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
import com.sketchware.remod.R;

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
        View fab = findViewWithTag("_fab");
        if (fab != null) {
            return fab;
        } else {
            ItemFloatingActionButton floatingActionButton = new ItemFloatingActionButton(this.getContext());
            floatingActionButton.setTag("_fab");
            floatingActionButton.setLayoutParams(new LayoutParams(-2, -2));
            floatingActionButton.setMainColor(ProjectFile.getColor(this.sc_id, "color_accent"));
            floatingActionButton.setFixed(true);
            if (var1 == null) {
                ViewBean bean = new ViewBean("_fab", 16);
                LayoutBean layoutBean = bean.layout;
                layoutBean.marginLeft = 16;
                layoutBean.marginTop = 16;
                layoutBean.marginRight = 16;
                layoutBean.marginBottom = 16;
                layoutBean.layoutGravity = 85;
                floatingActionButton.setBean(bean);
            } else {
                floatingActionButton.setBean(var1);
            }

           addView(floatingActionButton);
           b(floatingActionButton, floatingActionButton.getBean());
            return floatingActionButton;
        }
    }

    public void a() {
        ViewBean rootBean = new ViewBean("root", 0);
        LayoutBean layoutBean = rootBean.layout;
        layoutBean.width = -1;
        layoutBean.height = -1;
        layoutBean.orientation = 1;
        rootBean.parentType = 0;
        ItemLinearLayout linearLayout = (ItemLinearLayout) b(rootBean);
        linearLayout.setFixed(true);
       a = linearLayout;
       a.setBackgroundColor(-1118482);
       addView(linearLayout);
    }

    public void a(int var1, int var2, int var3, int var4) {
        Object[] var5 = a(var1, var2);
        if (var5 == null) {
           a(true);
        } else {
            if (this.d != var5) {
               a(true);
                ViewGroup group = (ViewGroup)var5[1];
                group.addView(e, (Integer) var5[2]);
                if (group instanceof LinearLayout) {
                    e.setLayoutParams(new android.widget.LinearLayout.LayoutParams(var3, var4));
                } else if (group instanceof FrameLayout) {
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

    public void a(boolean clear) {
       e.setVisibility(View.GONE);
        ViewParent parent =e.getParent();
        if (parent != null) {
            ((ViewGroup)parent).removeView(this.e);
        }

        if (clear) {
           d = null;
        }
    }

    public Object[] a(int var1, int var2) {
        Object[] var3 = null;
        int index = 0;

        int var9;
        for(int i = -1; index < c.size(); i = var9) {
            Object[] props = this.c.get(index);
            Rect rect = (Rect)props[0];
            Object[] var8 = var3;
            var9 = i;
            if (var1 >= rect.left) {
                var8 = var3;
                if (var1 < rect.right) {
                    var8 = var3;
                    if (var2 >= rect.top) {
                        var8 = var3;
                        if (var2 < rect.bottom) {
                            var8 = var3;
                            if (i < (int)props[3]) {
                                var9 = (int) props[3];
                                var8 = props;
                            }
                        }
                    }
                }
            }

            ++index;
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

    public View b(ViewBean bean) {
        int type = bean.type;
        View view = switch (type) {
            case 0 -> new ItemLinearLayout(this.getContext());
            default -> ViewPanes.a(type, getContext());
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

        type = b + 1;
       b = type;
       if (view == null) return null;
        view.setId(type);
        view.setTag(bean.id);
        ((sy)view).setBean(bean);
       b(view, bean);
        return view;
    }

    public void b() {
       a(true);
       c = new ArrayList<>();
        ((ty)this.a).setChildScrollEnabled(true);
    }

    @SuppressLint("DiscouragedApi")
    public final void b(View view, ViewBean bean) {
        ExtraViewPane.a(view, bean, this,f);
        int c1 = bean.id.charAt(0);
        boolean var4 = true;
        if (c1 == 95) {
            LayoutParams params = new LayoutParams(-2, -2);
            params.leftMargin = (int)wB.a(this.getContext(), (float)bean.layout.marginLeft);
            params.topMargin = (int)wB.a(this.getContext(), (float)bean.layout.marginTop);
            params.rightMargin = (int)wB.a(this.getContext(), (float)bean.layout.marginRight);
            params.bottomMargin = (int)wB.a(this.getContext(), (float)bean.layout.marginBottom);
            c1 = bean.layout.layoutGravity;
            if ((c1 & 3) == 3) {
                params.addRule(9);
            }

            if ((c1 & 48) == 48) {
                params.addRule(10);
            }

            if ((c1 & 5) == 5) {
                params.addRule(11);
            }

            if ((c1 & 80) == 80) {
                params.addRule(12);
            }

            if ((c1 & 1) == 1) {
                params.addRule(14);
            }

            if ((c1 & 16) == 16) {
                params.addRule(15);
            }

            if ((c1 & 17) == 17) {
                params.addRule(13);
            }

            view.setLayoutParams(params);
            if (bean.getClassInfo().b("FloatingActionButton")) {
                ImageBean imageBean = bean.image;
                if (imageBean != null) {
                    String resName = imageBean.resName;
                    if (resName != null && resName.length() > 0) {
                        try {
                            Bitmap bitmap = BitmapFactory.decodeFile(this.f.f(bean.image.resName));
                            c1 = Math.round(this.getResources().getDisplayMetrics().density / 2.0F);
                            Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() * c1, bitmap.getHeight() * c1, true);
                            ((FloatingActionButton)view).setImageBitmap(scaledBitmap);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            view.setRotation((float)bean.image.rotate);
            view.setAlpha(bean.alpha);
            view.setTranslationX(wB.a(this.getContext(), bean.translationX));
            view.setTranslationY(wB.a(this.getContext(), bean.translationY));
            view.setScaleX(bean.scaleX);
            view.setScaleY(bean.scaleY);
            view.setVisibility(View.VISIBLE);
        } else {
           a(view, bean);
            view.setRotation((float)bean.image.rotate);
            view.setAlpha(bean.alpha);
            view.setTranslationX(wB.a(this.getContext(), bean.translationX));
            view.setTranslationY(wB.a(this.getContext(), bean.translationY));
            view.setScaleX(bean.scaleX);
            view.setScaleY(bean.scaleY);
            String resource = bean.layout.backgroundResource;
            if (resource != null) {
                try {
                    if (this.f.h(resource) == ProjectResourceBean.PROJECT_RES_TYPE_RESOURCE) {
                        view.setBackgroundResource(
                               getContext().getResources().getIdentifier(bean.layout.backgroundResource, "drawable",getContext().getPackageName())
                        );
                    } else {
                        String name = f.f(bean.layout.backgroundResource);
                        if (name.endsWith(".9.png")) {
                            Bitmap var18 = zB.a(name);
                            byte[] bytes = var18.getNinePatchChunk();
                            if (NinePatch.isNinePatchChunk(bytes)) {
                                NinePatchDrawable ninePatchDrawable = new NinePatchDrawable(getResources(), var18, bytes, new Rect(), null);
                                view.setBackground(ninePatchDrawable);
                            } else {
                                view.setBackground(new BitmapDrawable(this.getResources(), name));
                            }
                        } else {
                            Bitmap bitmap = BitmapFactory.decodeFile(name);
                            c1 = Math.round(this.getResources().getDisplayMetrics().density / 2.0F);
                            Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() * c1, bitmap.getHeight() * c1, true);
                            BitmapDrawable drawable = new BitmapDrawable(this.getResources(), scaledBitmap);
                            view.setBackground(drawable);
                        }
                    }
                } catch (Exception e) {
                    Log.e("DEBUG", e.getMessage(), e);
                }
            }

            Gx beanClassInfo = bean.getClassInfo();
            if (beanClassInfo.b("LinearLayout")) {
                LinearLayout linearLayout = (LinearLayout)view;
                linearLayout.setOrientation(bean.layout.orientation);
                linearLayout.setWeightSum((float)bean.layout.weightSum);
                if (view instanceof ItemLinearLayout) {
                    ((ItemLinearLayout)view).setLayoutGravity(bean.layout.gravity);
                }
            }

            if (beanClassInfo.a("TextView")) {
                TextView textView = (TextView)view;
               a(textView, bean);
                if (!beanClassInfo.b("Button") && !beanClassInfo.b("Switch")) {
                    textView.setGravity(bean.layout.gravity);
                } else {
                    c1 = bean.layout.gravity;
                    if (c1 == 0) {
                        textView.setGravity(17);
                    } else {
                        textView.setGravity(c1);
                    }
                }
            }

            if (beanClassInfo.b("EditText")) {
               a((EditText)view, bean);
            }

            if (beanClassInfo.b("ImageView")) {
                if (f.h(bean.image.resName) == ProjectResourceBean.PROJECT_RES_TYPE_RESOURCE) {
                    ((ImageView)view)
                            .setImageResource(this.getContext().getResources().getIdentifier(bean.image.resName, "drawable",getContext().getPackageName()));
                } else if (bean.image.resName.equals("default_image")) {
                    ((ImageView)view).setImageResource(R.drawable.default_image);
                } else {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeFile(this.f.f(bean.image.resName));
                        c1 = Math.round(this.getResources().getDisplayMetrics().density / 2.0F);
                        bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() * c1, bitmap.getHeight() * c1, true);
                        ((ImageView)view).setImageBitmap(bitmap);
                    } catch (Exception e) {
                        e.printStackTrace();
                        ((ImageView)view).setImageResource(R.drawable.default_image);
                    }
                }

                ((ImageView)view).setScaleType(ScaleType.valueOf(bean.image.scaleType));
            }

            if (beanClassInfo.a("CompoundButton")) {
                CompoundButton button = (CompoundButton)view;
                if (bean.checked == 0) {
                    var4 = false;
                }

                button.setChecked(var4);
            }

            if (beanClassInfo.b("SeekBar")) {
                SeekBar seekbar = (SeekBar)view;
                seekbar.setProgress(bean.progress);
                seekbar.setMax(bean.max);
            }

            if (beanClassInfo.b("ProgressBar")) {
                ((ItemProgressBar)view).setProgressBarStyle(bean.progressStyle);
            }

            if (beanClassInfo.b("CalendarView")) {
                ((CalendarView)view).setFirstDayOfWeek(bean.firstDayOfWeek);
            }

            if (beanClassInfo.b("AdView")) {
                ((ItemAdView)view).setAdSize(bean.adSize);
            }

            view.setVisibility(View.VISIBLE);
        }
    }

    public final void c() {
       e = new TextView(getContext());
       e.setBackgroundResource(R.drawable.highlight);
       LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-2, -2);
       e.setLayoutParams(params);
       e.setVisibility(View.GONE);
    }

    public void c(ViewBean var1) {
       a(var1, (ItemLinearLayout)this.a);
    }

    public sy d(ViewBean bean) {
        View view = a.findViewWithTag(bean.id);
        if (bean.id.charAt(0) == '_') {
            view = findViewWithTag(bean.id);
        }

        String parent = bean.preParent;
        if (parent != null && parent.length() > 0 && !bean.parent.equals(bean.preParent)) {
            ViewGroup group = a.findViewWithTag(bean.preParent);
            group.removeView(view);
            ((ty)group).a();
           a(view);
        } else if (bean.index != bean.preIndex) {
            ((ViewGroup) a.findViewWithTag(bean.parent)).removeView(view);
           a(view);
        }

        bean.preId = "";
        bean.preIndex = -1;
        bean.preParent = "";
        bean.preParentType = -1;
        view.setVisibility(View.VISIBLE);
        return (sy)view;
    }

    public void d() {
       a.removeAllViews();
    }

    public void e() {
        View fab =findViewWithTag("_fab");
        if (fab != null) {
           removeView(fab);
        }
    }

    public void e(ViewBean bean) {
       d = null;
       c(bean);
        ((ty) a).setChildScrollEnabled(false);
    }

    public void f(ViewBean bean) {
        ViewGroup group = a.findViewWithTag(bean.parent);
        group.removeView(a.findViewWithTag(bean.id));
        if (group instanceof ty) {
            ((ty)group).a();
        }
    }

    public sy g(ViewBean bean) {
        String preId = bean.preId;
        if (preId != null && preId.length() > 0 && !bean.preId.equals(bean.id)) {
           a.findViewWithTag(bean.preId).setTag(bean.id);
            bean.preId = "";
        }

        View view;
        if (bean.id.charAt(0) == '_') {
            view = findViewWithTag(bean.id);
        } else {
            view = a.findViewWithTag(bean.id);
        }

       b(view, bean);
        return (sy)view;
    }

    public ItemFloatingActionButton getFab() {
        View bean = findViewWithTag("_fab");
        if (!(bean instanceof ItemFloatingActionButton)) {
            return null;
        }
        return (ItemFloatingActionButton) bean;
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
