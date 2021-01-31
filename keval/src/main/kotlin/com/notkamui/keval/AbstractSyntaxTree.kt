package com.notkamui.keval

import java.lang.reflect.InvocationTargetException

data class BinaryOperator(val implementation: (Double, Double) -> Double, val precedence: Int, val isLeftAssociative: Boolean)

/**
 * Represents a node in an AST and can evaluate its value
 *
 * Can either be an operator, or a leaf (a value)
 */
internal interface Node {
    /**
     * Evaluates the value of this node
     *
     * @return the value of the node
     * @throws KevalZeroDivisionException in case of a zero division
     */
    fun eval(): Double
}

/**
 * An binary operator node
 *
 * @property left is its left child
 * @property op is the actual operator
 * @property right is its right child
 * @constructor Creates an operator node
 */
internal data class OperatorNode(
        private val left: Node,
        private val op: (Double, Double) -> Double,
        private val right: Node
) : Node {
    override fun eval(): Double {
        try {
            return op.invoke(left.eval(), right.eval())
        } catch (e: InvocationTargetException) {
            throw e.cause!!
        }
    }
}

/**
 * A value node (leaf)
 *
 * @property value is its value
 * @constructor Creates a value node
 */
internal data class ValueNode(
        private val value: Double
) : Node {
    override fun eval(): Double = value
}