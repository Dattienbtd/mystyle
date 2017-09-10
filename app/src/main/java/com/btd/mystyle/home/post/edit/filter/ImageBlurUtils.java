package com.btd.mystyle.home.post.edit.filter;

import android.graphics.Color;
import android.media.effect.Effect;
import android.media.effect.EffectContext;
import android.media.effect.EffectFactory;

import java.util.ArrayList;

public class ImageBlurUtils {

    public ImageBlurUtils() {
    }

    public static ArrayList<String> getEffectName() {
        ArrayList<String> effectArray = new ArrayList<String>();
        effectArray.add("none");
        effectArray.add("A");
        effectArray.add("B");
        effectArray.add("C");
        effectArray.add("D");
        effectArray.add("E");
        effectArray.add("F");
        effectArray.add("G");
        effectArray.add("H");
        effectArray.add("I");
        effectArray.add("J");
        effectArray.add("K");
        effectArray.add("L");
        effectArray.add("M");
        effectArray.add("O");
        effectArray.add("P");
        effectArray.add("Q");

        return effectArray;
    }

    public static Effect createEffect(int mCurrentEffect, EffectContext effectContext, int count) {

        EffectFactory effectFactory = effectContext.getFactory();
        Effect effect = null;
        // Initialize the correct effect based on the selected menu/action item
        if (effectFactory == null) {
            return null;
        }
        switch (mCurrentEffect) {

            case 0:
                break;

            case 1:
                effect = effectFactory.createEffect(EffectFactory.EFFECT_AUTOFIX);
                effect.setParameter("scale", pamameter(count, 0.5f, 0));
                break;

            case 2:
                effect = effectFactory.createEffect(EffectFactory.EFFECT_BLACKWHITE);
                effect.setParameter("black", .1f);
                effect.setParameter("white", .7f);
                break;

            case 3:
                effect = effectFactory.createEffect(EffectFactory.EFFECT_BRIGHTNESS);
                effect.setParameter("brightness", pamameter(count, 1.8f, 0.5f));
                break;

            case 4:
                effect = effectFactory.createEffect(EffectFactory.EFFECT_CONTRAST);
                effect.setParameter("contrast", pamameter(count, 1.5f, 0.5f));
                break;

            case 5:
                effect = effectFactory.createEffect(EffectFactory.EFFECT_CROSSPROCESS);
                break;

            case 6:
                effect = effectFactory.createEffect(EffectFactory.EFFECT_DOCUMENTARY);
                break;

            //case 7:
            //	effect = effectFactory.createEffect(EffectFactory.EFFECT_DUOTONE);
            //	effect.setParameter("first_color", Color.YELLOW);
            //	effect.setParameter("second_color", Color.DKGRAY);
            //	break;

            case 7:
                effect = effectFactory.createEffect(EffectFactory.EFFECT_FILLLIGHT);
                effect.setParameter("strength", pamameter(count, 0.7f, 0));
                break;

            //case 9:
            //	effect = effectFactory.createEffect(EffectFactory.EFFECT_FISHEYE);
            //	effect.setParameter("scale", .5f);
            //	break;

            //case 10:
            //	effect = effectFactory.createEffect(EffectFactory.EFFECT_FLIP);
            //	effect.setParameter("vertical", true);
            //	break;
            //
            //case 11:
            //	effect = effectFactory.createEffect(EffectFactory.EFFECT_FLIP);
            //	effect.setParameter("horizontal", true);
            //	break;

            case 8:
                effect = effectFactory.createEffect(EffectFactory.EFFECT_GRAIN);
                effect.setParameter("strength", pamameter(count, 1.0f, 0));
                break;

            case 9:
                effect = effectFactory.createEffect(EffectFactory.EFFECT_GRAYSCALE);
                break;

            case 10:
                effect = effectFactory.createEffect(EffectFactory.EFFECT_LOMOISH);
                break;

            //case 15:
            //	effect = effectFactory.createEffect(EffectFactory.EFFECT_NEGATIVE);
            //	break;
            //
            //case 16:
            //	effect = effectFactory
            //			.createEffect(EffectFactory.EFFECT_POSTERIZE);
            //	break;
            //
            //case 17:
            //	effect = effectFactory.createEffect(EffectFactory.EFFECT_ROTATE);
            //	effect.setParameter("angle", 180);
            //	break;

            case 11:
                effect = effectFactory.createEffect(EffectFactory.EFFECT_SATURATE);
                effect.setParameter("scale", pamameter(count, 0.5f, 0));
                break;

            case 12:
                effect = effectFactory.createEffect(EffectFactory.EFFECT_SEPIA);
                break;

            case 13:
                effect = effectFactory.createEffect(EffectFactory.EFFECT_SHARPEN);
                effect.setParameter("scale", pamameter(count, 1.0f, 0));
                break;

            case 14:
                effect = effectFactory.createEffect(EffectFactory.EFFECT_TEMPERATURE);
                effect.setParameter("scale", pamameter(count, 0.8f, 0));
                break;

            case 15:
                effect = effectFactory.createEffect(EffectFactory.EFFECT_TINT);
                effect.setParameter("tint", Color.MAGENTA);
                break;

            case 16:
                effect = effectFactory.createEffect(EffectFactory.EFFECT_VIGNETTE);
                effect.setParameter("scale", pamameter(count, 0.5f, 0));
                break;

            default:
                break;
        }
        return effect;
    }

    public static float pamameter(int count, float max, float min) {
        return (float) (count * ((max - min) / 100.0) + min);
    }
}
