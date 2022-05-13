package com.example.porqueria;

import android.text.InputFilter;
import android.text.Spanned;

import androidx.appcompat.app.AppCompatActivity;

public class InputFilterMinMax extends AppCompatActivity implements InputFilter {

    private final int min;
    private final int max;

    public InputFilterMinMax(String min, String max) {
        this.min = Integer.parseInt(min);
        this.max = Integer.parseInt(max);
    }

    @Override
    public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
        try {
            int input = Integer.parseInt(spanned.toString() + charSequence.toString());
            if (isInRange(min, max, input))
                return null;
        } catch (NumberFormatException nfe) {
        }
        return "";
    }

    private boolean isInRange(int a, int b, int c) {
        return b > a ? c >= a && c <= b : c >= b && c <= a;
    }

}
