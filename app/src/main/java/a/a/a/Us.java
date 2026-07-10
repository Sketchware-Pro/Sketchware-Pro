package a.a.a;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.besome.sketch.beans.BlockBean;
import java.util.ArrayList;




// =========================================================
// /*MyBlockCollectionEntry*/ Us = a single "My Block Collection" group
// =========================================================

// PURPOSE:
    // Sketchware has a "My Block Collection" feature: a saved list of named
    // block-groups, each holding a set of blocks the user bookmarked together.
    // /*MyBlockCollectionEntry*/ Us (extends /*BlockTreeNode*/ Rs)
    // is ONE entry in that collection.
    // it renders as a labeled block and carries the group's saved blocks as its payload.

// CONFIRMED BEHAVIOR:
    // - Always forces blockType = 2
    // - /*MyBlockCollectionEntry*/ Us is the ONLY blockType = 2 I found.
        // (see setBlockType() in Rs).
        // /*blockType*/ oa is a drag-and-drop insertion-mode flag,
        // not a visual-shape flag.
        // Confirmed from "app/src/main/java/com/besome/sketch/editor/LogicEditorActivity.java" touch/drop + delete handling:
            // 0 = normal block already living in the editor's block tree.
                // Goes through the standard reconnect logic (ha/ia/ja, p().k())
                // when dropped, and the standard single-block delete path
                // (via getBean().id).
            // 1 = a palette block representing a single getArg variable getter
                // (the small parameter blocks shown in the script header).
                // Set externally via setBlockType(1) right after
                // BlockUtil.getVariableBlock(...). Dropping it inserts one block.
            // 2 = a "My Block Collection" entry.
                // this is what /*MyBlockCollectionEntry*/ Us forces on itself
                // via super. /*blockType*/ oa = 2.
                // On drop, the editor calls Us.getData() to grab
                // the whole saved group and inserts all of those blocks at once,
                // instead of just one.
                // On delete, it skips the normal single-block
                // delete and instead removes the entire named group by its title.
        // /*MyBlockCollectionEntry*/ Us is the only class that self-assigns blockType = 2
        // (via its constructor)
        // setBlockType() itself is public,
        // so nothing besides convention stops another caller from setting 2 on a plain Rs too.

    // - Always renders its label as "name : value"
        // (
            // Rs only does this for getVar/getArg opcodes
            // /*MyBlockCollectionEntry*/ Us does it unconditionally, since every
            // collection entry needs its name visible
        // ).


// FIELDS
    // /*blocks*/ sa → ArrayList<BlockBean> : the blocks saved under this
    //                                        collection entry. Exposed
    //                                        read-only via getData().
    // /*tvName*/   ta → TextView              : the "name : value" label built
    //                                        in l() and positioned in k().

// =========================================================

public class /*MyBlockCollectionEntry*/ Us extends Rs {
    public ArrayList<BlockBean>  /*blocks*/  sa;
    public TextView              /*tvName*/    ta;




    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    // Forwards to Rs (ctx, tag=-1, spec, blockTypeChar, labelPrefix, opcode),
    // stores the collection's saved blocks,
    // then forces blockType = 2.
    public /*MyBlockCollectionEntry*/ Us (
        Context ctx,
        String blockTypeChar,
        String labelPrefix,
        String opcode,
        String spec,
        ArrayList<BlockBean> savedBlocks
    ) {
        super (ctx, -1, spec, blockTypeChar, labelPrefix, opcode);
        this. /*blocks*/ sa = savedBlocks;
        super. /*blockType*/ oa = 2;
    }
    
    
    
    
    // =========================================================
    // PUBLIC METHODS
    // =========================================================
    
    // ======= Getters =======
    
    // Returns this entry's saved block payload.
    public ArrayList<BlockBean> /*getBlocks*/ getData() { return this. /*blocks*/ sa; }
    
    
    
    // ======= Others =======
    
    // Density-scales inherited geometry, classifies /*blockTypeChar*/ super.b
    // into the fa/ga/ea chain-shape flags, builds the name label, applies
    // the background color, and lays out via k().
    public void /*initialize*/ l() {
        ((RelativeLayout) this).setDrawingCacheEnabled (false);
        
        this.scaleGeometryForDensity();
        this.applyShapeFlags (this.classifyBlockShape());
        
        this. /*tvName*/ ta = this.createTvName (super.T);
        ((RelativeLayout) this).addView (this. /*tvName*/ ta);
        
        // this is the color of blocks
        // at the "My Block Collection" drawer
        // at the "app/src/main/java/com/besome/sketch/editor/LogicEditorActivity.java"
        super.e = ((RelativeLayout) this).getResources().getColor (pro.sketchware.R.color.scolor_red_02);
        
        this.k();
    }
    
    // Measures a TextView's rendered text bounds. Returns {width, height}.
    public final int[] /*measureText*/ b (TextView tvName) {
        Rect textBounds = new Rect();
        tvName.getPaint().getTextBounds (tvName.getText().toString(), 0, tvName.getText().length(), textBounds);
        return new int[] { textBounds.width(), textBounds.height() };
    }
    
    // Positions /*tvName*/ ta at (w, u),
    // then computes and applies this block's final width/height
    // via computeLabelWidth()/computeLabelHeight().
    public void /*layoutAndSize*/ k() {
        this. /*tvName*/ ta.setX ((float) super.w);
        this. /*tvName*/ ta.setY ((float) super.u);
        
        int[] textBounds = this.b (this. /*tvName*/ ta);
        int computedWidth = this.computeLabelWidth (textBounds[0]);
        int computedHeight = this.computeLabelHeight (textBounds[1]);
        
        ((Ts) this).a ((float) computedWidth, (float) computedHeight, true);
    }
    
    
    
    
    
    // =========================================================
    // PRIVATE METHODS
    // =========================================================
    // NEW private helpers — split out of k()/l() for readability.

    // Builds the "name : value" label TextView.
    // If /*name*/ super.c is set,
    // prefixes it before rawText as
    // "name : rawText".
    // (
        // Same visual logic as Rs.a(String),
        // but applied unconditionally instead of only for getVar/getArg opcodes.
        // this is what makes every /*MyBlockCollectionEntry*/ Us entry show its collection name.
    // )
    private TextView createTvName (String rawText) {
        TextView _tvName = new TextView (super. /*ctx*/ a);
        
        handleTvNameTxt (_tvName, rawText);
        handleTvNameShape (_tvName);
        
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams (-2, super.G);
        params.setMargins (0, 0, 0, 0);
        _tvName.setLayoutParams (params);
        
        return _tvName;
    }
    
    private void handleTvNameTxt (TextView _tvName, String rawText) {
        String name = super.c;
        String finalText = rawText;

        if (name != null && name.length() > 0) {
            StringBuilder textBuilder = new StringBuilder();
            textBuilder.append (super.c);
            textBuilder.append (" : ");
            textBuilder.append (rawText);
            finalText = textBuilder.toString();
        }
        _tvName.setText (finalText);
    }
    
    private void handleTvNameShape (TextView _tvName) {
        _tvName.setTextSize (10.0F);
        _tvName.setPadding (0, 0, 0, 0);
        _tvName.setGravity (16);
        _tvName.setTextColor (-1); // White
        _tvName.setTypeface ((Typeface) null, 1);
    }
    
    // Widens contentWidth to the category minimum (W / aa / ca)
    // based on /*blockTypeChar*/ super.b, and pads it further
    // if a name is present.
    private int computeLabelWidth (int textWidth) {
        int contentWidth = super.w + textWidth + super.x;
        String name = super.c;
        int computedWidth = contentWidth;
        
        if (name != null && name.length() > 0) {
            computedWidth = (int) ((float) contentWidth + super.D * 8.0F);
        }
        
        // Widen to /*W*/ super.W minimum for blockType chars "b", "d", "s", "a".
        if (super.b.equals ("b") || super.b.equals ("d") || super.b.equals ("s") || super.b.equals ("a")) {
            computedWidth = Math.max (computedWidth, super.W);
        }
        
        // Widen to /*aa*/ super.aa minimum for blockType chars " ", "", "o".
        if (super.b.equals (" ") || super.b.equals ("") || super.b.equals ("o")) {
            computedWidth = Math.max (computedWidth, super.aa);
        }
        
        // Widen to /*ca*/ super.ca minimum for blockType chars "c", "e".
        if (super.b.equals ("c") || super.b.equals ("e")) {
            computedWidth = Math.max (computedWidth, super.ca);
        }
        
        return computedWidth;
    }

    // Computes the block's final height from
    // inherited offsets vs. the measured label height,
    // whichever is larger.
    private int computeLabelHeight (int textHeight) {
        int topOffset = super.u;
        int labelHeight = super.G;
        int bottomOffset = super.v;
        return Math.max (topOffset + labelHeight + bottomOffset, super.u + textHeight + super.v);
    }

    // Density-scales inherited geometry fields (W / aa / ba / ca / da).
    private void scaleGeometryForDensity() {
        float originalTopWidth = (float) super.W;
        float density = super.D;
        super.W  = (int) (originalTopWidth * density);
        super.aa = (int) ((float) super.aa * density);
        super.ba = (int) ((float) super.ba * density);
        super.ca = (int) ((float) super.ca * density);
        super.da = (int) ((float) super.da * density);
    }

    // Classifies /*blockTypeChar*/ super.b into a shape category.
    // Restored to switch(String) form — behaviorally identical to the
    // original decompiled hashCode()+equals() dispatch chain.
    private int classifyBlockShape() {
        switch (super.b) {
            case " ": return 0;
            case "b": return 1;
            case "s": return 2;
            case "d": return 3;
            case "v": return 4;
            case "p": return 5;
            case "l": return 6;
            case "a": return 7;
            case "c": return 8;
            case "e": return 9;
            case "f": return 10;
            case "h": return 11;
            default:  return -1;
        }
    }

    // Applies the fa/ga/ea chain-shape flags for the given shape category.
    // /*fa*/ super.fa = "can chain to a next block" (categories 1-7)
    // /*ga*/ super.ga = set for category 10
    // /*ea*/ super.ea = set for category 11 ("h")
    private void applyShapeFlags (int shapeCategory) {
        switch (shapeCategory) {
            case 1: case 2: case 3: case 4: case 5: case 6: case 7:
                super.fa = true;
                break;
            case 10:
                super.ga = true;
                break;
            case 11:
                super.ea = true;
                break;
            default:
                break;
        }
    }




    
}


