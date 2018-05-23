package com.example.aliy.simplecalculator.algorithm;

import java.util.Stack;

public class OperatorTree {
    String expression;

    OperatorReader oReader;
    OReaderIterator itChar;

    /**
     * @param expression
     */
    public OperatorTree(String expression) {
        this.expression = expression;
        oReader = new OperatorReader(expression);
        itChar = oReader.getItChar();
    }

    public OperateNode createTree() {
        Stack<OperateNode> opStack = new Stack<OperateNode>();
        OperateNode cNode = null; // current
        OperateNode nNode = null; // number node ? now node
        while (itChar.hasNext()) {
            if (itChar.isNumber()) {
                nNode = OperatorFactory.NumberNode(oReader.readNumber());
            } else if (itChar.get() == '(') {
                itChar.next();
                nNode = createTree();
            } else if (itChar.get() == ')') {
                itChar.next();
                break;
            } else {
                switch (itChar.get()) {
                    case '+':
                        cNode = OperatorFactory.Plus();
                        break;
                    case '-':
                        cNode = OperatorFactory.Subtract();
                        break;
                    case '*':
                        cNode = OperatorFactory.Multiply();
                        break;
                    case '/':
                        cNode = OperatorFactory.Division();
                        break;
                    default:
                        itChar.next();
                        continue;
                }
                if (opStack.isEmpty()) {
                    cNode.addOperator(nNode);
                    opStack.push(cNode);
                } else {
                    OperateNode tNode = opStack.peek();
                    if (cNode.getPriority() < tNode.getPriority()) {
                        tNode.addOperator(nNode);
                        while (cNode.getPriority() < tNode.getPriority()) {
                            if (opStack.isEmpty()) {
                                break;
                            }
                            tNode = opStack.pop();
                        }
                        cNode.addOperator(tNode);
                    } else {
                        cNode.addOperator(nNode);
                        tNode.addOperator(cNode);
                    }
                    opStack.push(cNode);
                }
                itChar.next();
            }
        }
        if (cNode == null) {
            return nNode;
        }
        cNode.addOperator(nNode);
        if (opStack.isEmpty()) {
            opStack.push(cNode);
        }
        return opStack.get(0);
    }

}
