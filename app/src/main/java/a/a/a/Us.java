package a.a.a;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.besome.sketch.beans.BlockBean;

import java.util.ArrayList;

public class Us extends Rs {

    public ArrayList<BlockBean> sa;
    public TextView ta;

    public Us(Context var1, String var2, String var3, String var4, String var5, ArrayList<BlockBean> var6) {
        super(var1, -1, var5, var2, var3, var4);
        this.sa = var6;
        super.oa = 2;
    }

    private TextView a(String var1) {
        TextView var2 = new TextView(super.a);
        String var3 = super.c;
        String var4 = var1;
        if (var3 != null) {
            var4 = var1;
            if (var3.length() > 0) {
                StringBuilder var6 = new StringBuilder();
                var6.append(super.c);
                var6.append(" : ");
                var6.append(var1);
                var4 = var6.toString();
            }
        }

        var2.setText(var4);
        var2.setTextSize(10.0F);
        var2.setPadding(0, 0, 0, 0);
        var2.setGravity(16);
        var2.setTextColor(-1);
        var2.setTypeface((Typeface) null, 1);
        RelativeLayout.LayoutParams var5 = new RelativeLayout.LayoutParams(-2, super.G);
        var5.setMargins(0, 0, 0, 0);
        var2.setLayoutParams(var5);
        return var2;
    }

    public final int[] b(TextView var1) {
        Rect var2 = new Rect();
        var1.getPaint().getTextBounds(var1.getText().toString(), 0, var1.getText().length(), var2);
        return new int[]{var2.width(), var2.height()};
    }

    public ArrayList<BlockBean> getData() {
        return this.sa;
    }

    public void k() {
        int var1 = super.w;
        int var2 = super.u;
        this.ta.setX((float) var1);
        this.ta.setY((float) var2);
        int[] var3 = this.b(this.ta);
        var2 = var3[0];
        int var4 = var3[1];
        var2 = super.w + var2 + super.x;
        int var5 = super.u;
        int var6 = super.G;
        int var7 = super.v;
        String var15 = super.c;
        var1 = var2;
        if (var15 != null) {
            var1 = var2;
            if (var15.length() > 0) {
                var1 = (int) ((float) var2 + super.D * 8.0F);
            }
        }

        label38:
        {
            if (!super.b.equals("b") && !super.b.equals("d") && !super.b.equals("s")) {
                var2 = var1;
                if (!super.b.equals("a")) {
                    break label38;
                }
            }

            var2 = Math.max(var1, super.W);
        }

        label31:
        {
            if (!super.b.equals(" ") && !super.b.equals("")) {
                var1 = var2;
                if (!super.b.equals("o")) {
                    break label31;
                }
            }

            var1 = Math.max(var2, super.aa);
        }

        label25:
        {
            if (!super.b.equals("c")) {
                var2 = var1;
                if (!super.b.equals("e")) {
                    break label25;
                }
            }

            var2 = Math.max(var1, super.ca);
        }

        var1 = Math.max(var5 + var6 + var7, super.u + var4 + super.v);
        ((Ts) this).a((float) var2, (float) var1, true);
    }

    public void l() {
        byte var1;
        label69:
        {
            var1 = 0;
            ((RelativeLayout) this).setDrawingCacheEnabled(false);
            float var2 = (float) super.W;
            float var3 = super.D;
            super.W = (int) (var2 * var3);
            super.aa = (int) ((float) super.aa * var3);
            super.ba = (int) ((float) super.ba * var3);
            super.ca = (int) ((float) super.ca * var3);
            super.da = (int) ((float) super.da * var3);
            String var4 = super.b;
            int var5 = var4.hashCode();
            if (var5 != 32) {
                if (var5 != 104) {
                    if (var5 != 108) {
                        if (var5 != 112) {
                            if (var5 != 115) {
                                if (var5 != 118) {
                                    switch (var5) {
                                        case 97:
                                            if (var4.equals("a")) {
                                                var1 = 7;
                                                break label69;
                                            }
                                            break;
                                        case 98:
                                            if (var4.equals("b")) {
                                                var1 = 1;
                                                break label69;
                                            }
                                            break;
                                        case 99:
                                            if (var4.equals("c")) {
                                                var1 = 8;
                                                break label69;
                                            }
                                            break;
                                        case 100:
                                            if (var4.equals("d")) {
                                                var1 = 3;
                                                break label69;
                                            }
                                            break;
                                        case 101:
                                            if (var4.equals("e")) {
                                                var1 = 9;
                                                break label69;
                                            }
                                            break;
                                        case 102:
                                            if (var4.equals("f")) {
                                                var1 = 10;
                                                break label69;
                                            }
                                    }
                                } else if (var4.equals("v")) {
                                    var1 = 4;
                                    break label69;
                                }
                            } else if (var4.equals("s")) {
                                var1 = 2;
                                break label69;
                            }
                        } else if (var4.equals("p")) {
                            var1 = 5;
                            break label69;
                        }
                    } else if (var4.equals("l")) {
                        var1 = 6;
                        break label69;
                    }
                } else if (var4.equals("h")) {
                    var1 = 11;
                    break label69;
                }
            } else if (var4.equals(" ")) {
                break label69;
            }

            var1 = -1;
        }

        switch (var1) {
            case 0:
            case 8:
            case 9:
            default:
                break;
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                super.fa = true;
                break;
            case 10:
                super.ga = true;
                break;
            case 11:
                super.ea = true;
        }

        this.ta = this.a(super.T);
        ((RelativeLayout) this).addView(this.ta);
        super.e = ((RelativeLayout) this).getResources().getColor(2131034294);
        this.k();
    }
}
