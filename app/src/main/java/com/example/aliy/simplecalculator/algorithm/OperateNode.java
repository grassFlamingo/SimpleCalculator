package com.example.aliy.simplecalculator.algorithm;

import java.util.Arrays;

public abstract class OperateNode {
	protected OperateNode[] nodes;
	protected int nodeCount = 0;
	protected AutoNumber value = null;
	protected enum NodeType{Plus, Subtract, Multiply, Division, EmptyNode, NumberNode};
	protected NodeType type;

	protected abstract void compute();

	public abstract int getPriority();
	
	@Override
	public String toString() {
		return "OperateNode [nodes=" + Arrays.toString(nodes) + ", value=" + value
				+ ", priority=" + getPriority() + ", type=" + type + "]";
	}

	/**
	 * Before visit: If value is null then this node is not the leaf node
	 * After visit: value will be replace by compute result
	 * @param value
	 */
	public OperateNode(AutoNumber value) {
		this.value = value;
	}
	
	public OperateNode() {
		
	}

	public AutoNumber getValue() {
		return value;
	}

	public void addOperator(OperateNode o) {
		if(o == null || o.type == NodeType.EmptyNode) {
			return;
		}
		try {
			nodes[nodeCount++] = o;
		}catch(IndexOutOfBoundsException e) {
			nodeCount--;
			throw e;
		}
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OperateNode)) return false;
        OperateNode that = (OperateNode) o;
        return getValue().equals(that.getValue()) && type == that.type;
    }

    @Override
    public int hashCode() {
        int result = getValue().hashCode();
        result = 31 * result + type.hashCode();
        return result;
    }

    public OperateNode visit() {
		for (int i = 0; i < nodeCount; i++) {
			if(nodes[i].value == null) {
				nodes[i].visit();	
			}
		}
		compute();
		return this;
	}


}
