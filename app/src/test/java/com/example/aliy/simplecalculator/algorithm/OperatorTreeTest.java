package com.example.aliy.simplecalculator.algorithm;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Aliy on 22/12/2017.
 */
public class OperatorTreeTest {
    private class ExpAuto{

        public ExpAuto(String exp, int v) {
            this.exp = exp;
            this.v = new AutoNumber(v);
        }

        public ExpAuto(String exp, double v) {
            this.exp = exp;
            this.v = new AutoNumber(v);
        }
        public String exp;
        public AutoNumber v;

        @Override
        public String toString() {
            return "ExpAuto{" +
                    "exp='" + exp + '\'' +
                    ", v=" + v +
                    '}';
        }
    }
    @Test
    public void test() {
        ExpAuto[] eaList = new ExpAuto[]{
                new ExpAuto("1 + 1", 2),
                new ExpAuto("1+2*2", 5),
                new ExpAuto("1+2*2-3", 2),
                new ExpAuto("1+2*2 / 3+10", 12.333333333333332),
                new ExpAuto("1+2*2/4+10", 12.0),
                new ExpAuto("(1+2)*2+10", 16),
                new ExpAuto("(1+( 38 + 8 )* 2 ) *2/99+10", 11.878787878787879),
                new ExpAuto("99*3.141592653589793", 311.01767270538954),
                new ExpAuto("-12", -12),
                new ExpAuto("13", 13),
                new ExpAuto("(100)", 100),
                new ExpAuto("(-100)", -100),
                new ExpAuto("(+100)", 100),
                new ExpAuto("99 * .1", 9.9),
                new ExpAuto("2.381E3+6", 2387.0)
//                new ExpAuto("6E-7/9999999999", 6.0000000006e-17)
        };

        for (ExpAuto e : eaList) {
            System.out.println(e);
            assertEquals(e.v, getComputedNode(e.exp).value);
        }
    }

    private OperateNode getComputedNode(String expression){
        return new OperatorTree(expression)
                .createTree()
                .visit();
    }


}