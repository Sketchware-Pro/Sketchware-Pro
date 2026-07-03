package a.a.a;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.annotation.StringRes;

import com.google.android.material.textfield.TextInputLayout;

import mod.hey.studios.util.Helper;




// =========================================================
// /*BaseValidatorW*/ MB = Base validator watcher component
// =========================================================

// PURPOSE:
    // Shared base for all field validators in the project.
    // Wires a TextInputLayout + EditText pair to a TextWatcher
    // and an InputFilter so subclasses only need to override
    // onTextChanged() and implement their own rules.

// FIELDS (obfuscated names preserved for compatibility):

    /*TODO: DELETE THIS ↓↓↓*/
    // /*ctx*/      a → Context          : used to resolve @string resources via getString()
    /*TODO: DELETE THIS ↑↑↑*/
    
    // /*til*/      b → TextInputLayout  : the outer wrapper, used to show/hide errors
    // /*et*/       c → EditText         : the inner input field
    // /*isValid*/  d → boolean          : true = current input is valid, false = invalid
    // /*strResId*/ e → int (@StringRes) : optional custom error string resource id.
    //                                         When 0 → subclass uses its own default string.
    //                                         When set → subclass uses this id instead,
    //                                         allowing callers to swap the error message
    //                                         without subclassing.
    //                                         Example:
    //                                             at strings.xml →
    //                                                 <string name="invalid_value_max_words">You can use maximum %d words</string>
    //                                             
    //                                             validator. /*strResId*/ e = R.string.invalid_value_max_words;
    //                                             
    //                                             Then inside the subclass:
    //                                                 int maxLength = 50;
    //                                                 /*ctx*/ a.getString(/*strResId*/ e, maxLength)
    //                                                 resolves to e.g. "You can use maximum 50 words"

// USAGE:
    // Extend /*BaseValidatorW*/ MB, override onTextChanged(), call Result methods.
    // Use /*checkAndFocus ()*/ b() to check validity before form submission.

// =========================================================
// DESIGN NOTE — Inverted ownership:
    // Normally a View owns its watcher.
    //     view.addTextChangedListener(myWatcher);
    // The watcher is just a passive listener the View calls into.
    // The View is in charge.
    //
    // Here it is flipped. /*BaseValidatorW*/ MB is in charge.
    // /*BaseValidatorW*/ MB receives a TextInputLayout and immediately wires itself in:
    //     c.setFilters (new InputFilter[] { this });
    //     c.addTextChangedListener (this);
    // The View has no knowledge of /*BaseValidatorW*/ MB at all.
    // /*BaseValidatorW*/ MB is both the watcher AND the one who registered itself.
    //
    // This is an outside-in pattern.
    // Benefit:
        // One reusable class you can point at any field anywhere.
        // No need to touch the View itself.
        // Just new it and pass the TIL:
        //     new PackageNameValidator (myTil);
        //     OR
        //     new PackageNameValidator (context, myTil); // TODO: DELETE THIS ←←←
        // Done. The field is now validated.
    

// =========================================================

// TODO:
    // ======= RENAME =======
        // 1. class MB                → BaseValidatorW
        
        // 2. field a (Context)       → ctx
        // 3. field b (TIL)           → til
        // 4. field c (EditText)      → et
        // 5. field d (boolean)       → isValid
        // 6. field e (int @StringRes)→ strResId

        // 7. method a()              → getText ()
        // 8. method a(String)        → setText ()
        // 9. method b()              → checkAndFocus ()
    
    // ======= PRIVATE FIELDS =======
        // Make all fields private with getters/setters.
        // WHY:
            // Public fields break encapsulation.
            // Any caller can bypass or crash the validator directly:
                // validator.d = true; // bypass validation entirely
                // validator.b = null; // crash the validator
        // WHY NOT NOW:
            // Fields are accessed directly across SW — migrate callers first.
        // PRIORITY:
            // d (isValid) first — most dangerous, can bypass all validation.
    
    // ======= CONSTRUCTOR =======
        // DELETE the "/*BaseValidatorW*/ MB (Context ..., TextInputLayout ...) {...}"
            // THERE'S ABSOLUTELY NO NEED TO PASS THE CONTEXT SEPARATELY
            // that constructor is used in multiple places at SW right now,
            // so it's not safe to delete it now.
            // it will be deleted later once we update all the references of it.
            
        // Please use "/*BaseValidatorW*/ MB (TextInputLayout ...) {...}" instead
            // Context is now pulled directly from the TIL:
            //     /*ctx*/ a = textInputLayout.getContext();
            // A View cannot exist without a Context,
            // so this is always safe.
    
     // ======= CONTEXT =======
        // Storing Context is not safe and not recommend
        // it risks memory leaks & crashes
        // if the validator outlives the Activity.
        // Field /*ctx*/ a should be removed.
        // Replace with a getCtx() method that pulls fresh
        // from the TIL on each call.

// =========================================================

public abstract class /*BaseValidatorW*/ MB implements TextWatcher, InputFilter {




    // =========================================================
    // FIELDS
    // =========================================================
    
    /*TODO: DELETE THIS ↓↓↓*/
    public Context           /*ctx*/      a; // Application context. Used for getString().
    /*TODO: DELETE THIS ↑↑↑*/
    
    /*TODO: MAKE PRIVATE ↓↓↓*/
    /*private*/ public TextInputLayout   /*til*/      b; // Outer TIL wrapper. Shows error messages.
    /*private*/ public EditText          /*et*/       c; // Inner EditText. Source of input text.
    /*private*/ public boolean           /*isValid*/  d; // Validity flag. true = valid input.
    /*private*/ public int               /*strResId*/ e; // Optional @StringRes override for error messages.
                                                             // 0 means no override — use the default string.
    /*TODO: MAKE PRIVATE ↑↑↑*/




    // =========================================================
    // CONSTRUCTOR
    // =========================================================
    public /*BaseValidatorW*/ MB (TextInputLayout textInputLayout) {
        /*til*/ b = textInputLayout;
        
        /*TODO: DELETE THIS ↓↓↓*/
        // Storing application context, not activity context.
        // Activity context risks memory leaks if the validator outlives the activity.
        // Application context is safe — it lives for the entire app lifetime.
        // /*ctx*/ a is a public field accessed directly by external SW code,
        // so we keep it populated until all callers migrate to getCtx().
        /*ctx*/ a = getCtx().getApplicationContext();
        /*TODO: DELETE THIS ↑↑↑*/
        
        /*et*/  c = textInputLayout.getEditText();

        // Register this as both a filter and a watcher on the EditText.
        // InputFilter  → intercepts characters as they are typed.
        // TextWatcher  → notified after the text changes.
        /*et*/ c.setFilters (new InputFilter[] { this });
        /*et*/ c.addTextChangedListener (this);
    }

    /*TODO: DELETE THIS ↓↓↓*/
    // And why the hell are we passing the context separately here?
        // we can just get it from the view
    /** @deprecated Use {@link #MB(TextInputLayout)} instead. */
    @Deprecated (since = "7.0.0", forRemoval = true)
    public /*BaseValidatorW*/ MB (Context context, TextInputLayout textInputLayout) {
        /*ctx*/ a = context;
        /*til*/ b = textInputLayout;
        /*et*/  c = textInputLayout.getEditText();
        
        /*et*/ c.setFilters (new InputFilter[] { this });
        /*et*/ c.addTextChangedListener (this);
    }
    /*TODO: DELETE THIS ↑↑↑*/




    // =========================================================
    // PUBLIC METHODS
    // =========================================================
    
    // ======= Getters =======
    public Context                      getCtx()      { return /*til*/ b.getContext(); } // Gets the context directly & safely from til
    public TextInputLayout              getTil()      { return b; }
    public EditText                     getEt()       { return c; }
    public boolean                      isValid()     { return d; }
    public int                          getStrResId() { return e; }
    
    public String /*getText*/           a()           { return Helper.getText (/*et*/ c); } // Returns the current trimmed text from the EditText.
    
    // Returns the current validity state.
    // If invalid, requests focus on the field so the user sees the error.
    public boolean /*checkAndFocus ()*/ b()           {
        if ( ! /*isValid*/ d ) /*et*/ c.requestFocus();
        return /*isValid*/ d;
    }
    
    
    
    // ======= Setters =======
    public void             setStrResId (@StringRes int resId) { e = resId; }
    
    // Sets the field text programmatically and marks it valid.
    // isValid = true here because we are setting a known value, not user input.
    public void /*setText*/ a (String str)                     {
        /*isValid*/ d = true;
        /*et*/      c.setText (str);
    }



    // =========================================================
    // TextWatcher — subclasses override onTextChanged()
    // =========================================================

    @Override
    public void beforeTextChanged (CharSequence s, int start, int count, int after) {}

    @Override
    public void onTextChanged (CharSequence s, int start, int before, int count) {}

    // Clears the error when the field becomes empty.
    // Subclasses should not need to override this.
    @Override
    public void afterTextChanged (Editable editable) {
        if (editable.toString().isEmpty()) {
            /*til*/ b.setError (null);
            /*til*/ b.setErrorEnabled (false);
        }
    }




    // =========================================================
    // InputFilter
    // =========================================================

    // Returns null = allow all characters through.
    // Subclasses can override this to block characters early,
    // before onTextChanged fires.
    @Override
    public CharSequence filter (
        CharSequence source,
        int start, int end,
        Spanned dest,
        int dstart, int dend
    ) {
        return null;
    }




}


