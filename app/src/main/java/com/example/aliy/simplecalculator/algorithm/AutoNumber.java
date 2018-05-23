package com.example.aliy.simplecalculator.algorithm;

import java.text.DecimalFormat;

public class AutoNumber {
    private NumType ntype;

    ;
    private Object v;

    public AutoNumber(int value) {
        v = value;
        ntype = NumType.AInt;
    }

    public AutoNumber(double value) {
        v = value;
        ntype = NumType.ADouble;
    }

    @Override
    public String toString() {
        return "AutoNumber [type=" + ntype + ", value=" + v + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof AutoNumber)) {
            return false;
        }
        AutoNumber other = (AutoNumber) obj;
        return other.ntype == this.ntype && other.getValue().equals(v);
    }

    public boolean isInteger() {
        return ntype == NumType.AInt;
    }

    public boolean isDouble() {
        return ntype == NumType.ADouble;
    }

    public int intValue() {
        return ntype == NumType.AInt ? (int) v : (int) (double) v;
    }

    public double doubleValue() {
        return ntype == NumType.ADouble ? (double) v : (int) v;
    }

    public Object getValue() {
        return v;
    }

    public String getFormattedValue() {
        if (ntype == NumType.ADouble) {
            double d = ((double) v) * 10000;
            int c = (int) d;
            if (d - c > 0) {
                return new DecimalFormat("#.###E0").format(v);
            }
        }
        return v.toString();
    }

    private enum NumType {
        AInt, ADouble
    }

}
