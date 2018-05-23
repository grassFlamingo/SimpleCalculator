package com.example.aliy.simplecalculator.algorithm;

public class OperatorFactory {
	public static OperateNode Plus() {
		return new OperateNode() {
			// code block
			{
				nodes = new OperateNode[2];
				type = NodeType.Plus;
			}

			@Override
			public void compute() {
				AutoNumber left = nodes[0].value;
				AutoNumber right = nodes[1].value;
				value = (left.isDouble() || right.isDouble())
						? new AutoNumber(left.doubleValue() + right.doubleValue())
						: new AutoNumber(left.intValue() + right.intValue());
			}

			@Override
			public int getPriority() {
				return 1;
			}
		};
	}

	public static OperateNode Subtract() {
		return new OperateNode() {
			// code block
			{
				nodes = new OperateNode[2];
				type = NodeType.Subtract;
			}

			@Override
			public void compute() {
				AutoNumber left = nodes[0].value;
				AutoNumber right = nodes[1].value;
				value = (left.isDouble() || right.isDouble())
						? new AutoNumber(left.doubleValue() - right.doubleValue())
						: new AutoNumber(left.intValue() - right.intValue());
			}

			@Override
			public int getPriority() {
				return 1;
			}
		};
	}

	public static OperateNode Multiply() {
		return new OperateNode() {
			// code block
			{
				nodes = new OperateNode[2];
				type = NodeType.Multiply;
			}

			@Override
			public void compute() {
				AutoNumber left = nodes[0].value;
				AutoNumber right = nodes[1].value;
				value = (left.isDouble() || right.isDouble())
						? new AutoNumber(left.doubleValue() * right.doubleValue())
						: new AutoNumber(left.intValue() * right.intValue());
			}

			@Override
			public int getPriority() {
				return 2;
			}
		};
	}

	public static OperateNode Division() {
		return new OperateNode() {
			// code block
			{
				nodes = new OperateNode[2];
				type = NodeType.Division;
			}

			@Override
			public void compute() {
				AutoNumber left = nodes[0].value;
				AutoNumber right = nodes[1].value;
				if (left.isDouble() || right.isDouble()) {
					value = new AutoNumber(left.doubleValue() / right.doubleValue());
				} else {
					int vl = left.intValue();
					int vr = right.intValue();
					value = vl % vr == 0 ? new AutoNumber(vl / vr) : new AutoNumber((double) vl / vr);
				}
			}

			@Override
			public int getPriority() {
				return 2;
			}
		};
	}

	public static OperateNode EmptyNode() {
		return new OperateNode() {
			{
				nodes = new OperateNode[1];
				type = NodeType.EmptyNode;
			}

			@Override
			public int getPriority() {
				return 0;
			}

			@Override
			public void compute() {
				value = nodes[0].value;
			}
		};
	}

	public static OperateNode NumberNode(final AutoNumber num) {
		return new OperateNode() {
			// code block
			{
				value = num;
				type = NodeType.NumberNode;
			}

			@Override
			public void addOperator(OperateNode o) {

			}

			@Override
			public void compute() {
				
			}

			@Override
			public OperateNode visit() {
				return this;
			}

			@Override
			public int getPriority() {
				return 0;
			}
		};
	}
}
