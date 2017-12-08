package fura.com.furapp_android.view;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by jorge on 08/12/2017.
 */

public class FontManager {

    //File path inside the project to the font file.
    public static final String ROOT = "fonts/";
    public static final String FONTAWESOME = ROOT + "fontawesome-webfont.ttf";

    //Get the fonts file for FontAwesome.
    public static Typeface getTypeface(Context context, String font) {
        return Typeface.createFromAsset(context.getAssets(), font);
    }

    //Method to enable buttons to be capable of rendering the icons, which are inside a container (Ex. LinearLayout).
    public static void markAsIconContainer (View v, Typeface typeface) {
        if (v instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) v;
            for (int i = 0; i < vg.getChildCount(); i++) {
                View child = vg.getChildAt(i);
                markAsIconContainer(child, typeface);
            }
        }
        else if (v instanceof Button) {
            ((Button) v).setTypeface(typeface);
        }
    }

}
