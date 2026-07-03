package a.a.a;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.besome.sketch.beans.BlockBean;
import java.util.ArrayList;




// =========================================================
// /*DataBlockW*/ Us = block view that carries attached block data
// =========================================================

// PURPOSE:
    // A block view (extends /*BlockTreeNodeW*/ Rs) that, in addition to the
    // usual spec/label rendering, carries its own ArrayList<BlockBean> payload.
    // Always forces blockType = 2 (see setBlockType() in Rs) and always renders
    // its label as "prefix : value"
    // (Rs only does this for getVar/getArg blocks; Us does it unconditionally by buildLabel (String) ).

// NOTE ON CONFIDENCE:
    // Exact original name/intent NOT confirmed — a.a.a is closed-source, unlike
    // MB.java's source. "/*DataBlockW*/" is a best-effort label from behavior only.

// FIELDS (obfuscated names preserved for compatibility):
    // /*data*/  sa → ArrayList<BlockBean> : extra block payload this view carries
    //                                           (e.g. nested statements/args). Exposed
    //                                           read-only via getData().
    // /*label*/ ta → TextView             : the "prefix : value" label built in
    //                                           l() and positioned in k().

// =========================================================

public class Us extends Rs {
   public ArrayList<BlockBean>  /*data*/  sa;
   public TextView              /*label*/ ta;




   // =========================================================
   // CONSTRUCTOR
   // =========================================================

   // var1 ctx, var2 blockTypeChar, var3 labelPrefix, var4 opcode, var5 spec, var6 data
   // Forwards to Rs(ctx, tag=-1, spec, blockTypeChar, labelPrefix, opcode),
   // stores the data payload, then forces blockType = 2.
   public Us(Context var1, String var2, String var3, String var4, String var5, ArrayList<BlockBean> var6) {
      super(var1, -1, var5, var2, var3, var4);
      this.sa = var6;
      super.oa = 2;
   }




   // =========================================================
   // PRIVATE METHODS
   // =========================================================

   // Builds the "prefix : value" label TextView.
   // If /*subSpecLabel*/ super.c is set, prefixes it before var1 as "c : var1".
   // (Same logic as Rs.a(String),
   //     but applied unconditionally instead of only
   //     for getVar/getArg opcodes —
   //     this is what makes Us a "labeled data" block.)
   private TextView buildLabel (String var1) {
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
      var2.setTypeface((Typeface)null, 1);
      RelativeLayout.LayoutParams var5 = new RelativeLayout.LayoutParams(-2, super.G);
      var5.setMargins(0, 0, 0, 0);
      var2.setLayoutParams(var5);
      return var2;
   }




   // =========================================================
   // PUBLIC METHODS
   // =========================================================

   // Measures a TextView's rendered text bounds.
   // Returns {width, height} in pixels.
   public final int[] /*measureText*/ b(TextView var1) {
      Rect var2 = new Rect();
      var1.getPaint().getTextBounds(var1.getText().toString(), 0, var1.getText().length(), var2);
      return new int[]{var2.width(), var2.height()};
   }

   // Returns the attached block-data payload.
   public ArrayList<BlockBean> getData() {
      return this.sa;
   }

   // Positions /*label*/ ta at (w, u) then computes and applies this block's
   // final width/height (via Ts.a(width, height, resize)), widening the width
   // to the category minimum (W / aa / ca, per /*blockTypeChar*/ super.b) and
   // padding it further if a subSpec label is present.
   public void k() {
      int var1 = super.w;
      int var2 = super.u;
      this.ta.setX((float)var1);
      this.ta.setY((float)var2);
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
            var1 = (int)((float)var2 + super.D * 8.0F);
         }
      }

      // Widen to /*W*/ super.W minimum for blockType chars "b", "d", "s", "a".
      label38: {
         if (!super.b.equals("b") && !super.b.equals("d") && !super.b.equals("s")) {
            var2 = var1;
            if (!super.b.equals("a")) {
               break label38;
            }
         }

         var2 = Math.max(var1, super.W);
      }

      // Widen to /*aa*/ super.aa minimum for blockType chars " ", "", "o".
      label31: {
         if (!super.b.equals(" ") && !super.b.equals("")) {
            var1 = var2;
            if (!super.b.equals("o")) {
               break label31;
            }
         }

         var1 = Math.max(var2, super.aa);
      }

      // Widen to /*ca*/ super.ca minimum for blockType chars "c", "e".
      label25: {
         if (!super.b.equals("c")) {
            var2 = var1;
            if (!super.b.equals("e")) {
               break label25;
            }
         }

         var2 = Math.max(var1, super.ca);
      }

      var1 = Math.max(var5 + var6 + var7, super.u + var4 + super.v);
      ( (Ts)this ).a( (float) var2, (float) var1, true );
   }

   // Density-scales inherited geometry, classifies /*blockTypeChar*/ super.b into
   // the fa/ga/ea chain-shape flags (same category mapping as Ts.l()/Rs.l()),
   // builds the label, applies the background color, and lays out via k().
   public void l() {
      byte var1;
      label69: {
         var1 = 0;
         ((RelativeLayout)this).setDrawingCacheEnabled(false);
         float var2 = (float)super.W;
         float var3 = super.D;
         super.W = (int)(var2 * var3);
         super.aa = (int)((float)super.aa * var3);
         super.ba = (int)((float)super.ba * var3);
         super.ca = (int)((float)super.ca * var3);
         super.da = (int)((float)super.da * var3);
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

      // /*fa*/ super.fa = "can chain to a next block" (categories 1-7)
      // /*ga*/ super.ga = set for category 10
      // /*ea*/ super.ea = set for category 11 ("h")
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

      this.ta = this.buildLabel (super.T);
      ((RelativeLayout)this).addView(this.ta);
      // TODO: confirm what color resource 2131034294 maps to (not verified).
      // super.e = ((RelativeLayout)this).getResources().getColor(2131034294);
      super.e = 0xffffc107; // was: getResources().getColor(2131034294)
      this.k();
   }
}


