package com.example.aliy.simplecalculator.algorithm;

public class OperatorReader {

    private OReaderIterator itChar;

    public OperatorReader(String operation) {
        final char[] opera = operation.trim().toCharArray();
        itChar = new OReaderIterator() {
            int index = 0;

            @Override
            public String toString() {
                return "itChar[index=" + index + ", get:" + get() + "]";
            }

            @Override
            public char get() {
                return opera[index];
            }

            @Override
            public boolean hasNext() {
                return index < opera.length;
            }

            @Override
            public char next() {
                return ++index < opera.length ? opera[index] : 0;
            }

            @Override
            public void previous() {
                index--;
                if (index < 0) {
                    index = 0;
                }
            }

            @Override
            public boolean isNumber() {
                if (opera[index] >= '0' && opera[index] <= '9' || opera[index] == 'E') {
                    return true;
                }
                int pre = index;
                if (opera[pre] == '-' || opera[pre] == '+'){
                    pre--;
                    return ( pre < 0 ) || ( pre >= 0 && opera[pre] == '(' );
                }
                pre = index;
                return opera[pre] == '.' && ++pre < opera.length &&
                        (opera[pre] >= '0' && opera[pre] <= '9');
            }
        };
    }

    public OReaderIterator getItChar() {
        return itChar;
    }

    public AutoNumber readNumber() {
        if (!itChar.hasNext()) {
            return null;
        }
        char tChar = itChar.get();
        boolean negativeNumber = false;
        int iPart = 0;
        if (tChar == '-') {
            negativeNumber = true;
            tChar = itChar.next();
        }
        if (tChar == '+') {
            tChar = itChar.next();
        }
        // number integer part
        while (tChar >= '0' && tChar <= '9') {
            iPart = iPart * 10 + tChar - '0';
            if (itChar.hasNext()) {
                tChar = itChar.next();
            } else {
                break;
            }
        }
        if (tChar == '.') {
            tChar = itChar.next();
        } else {
            return new AutoNumber(negativeNumber ? -1 * iPart : iPart);
        }
        double dPart = 0.0;
        // decimal part
        int[] bufDouble = new int[64];
        int i = 0;
        while (tChar >= '0' && tChar <= '9') {
            bufDouble[i++] = tChar - '0';
            if (itChar.hasNext()) {
                tChar = itChar.next();
            } else {
                break;
            }
        }
        while (i >= 0) {
            dPart = dPart * 0.1 + bufDouble[i--];
        }
        dPart *= 0.1;
        double dpa = dPart + iPart;
        if (itChar.hasNext() && tChar == 'E' ){
            tChar = itChar.next();
            int pow;
            if(tChar == '-'){
                tChar = itChar.next();
                pow = -1 * (tChar - '0');
            }else {
                pow = tChar - '0';
            }
            dpa *= Math.pow(10, pow);
            itChar.next();
        }
        return new AutoNumber(negativeNumber ? -1 * dpa : dpa);
    }

}
