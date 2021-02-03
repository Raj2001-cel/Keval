package com.notkamui.keval

/**
 * Wrapper class for Keval,
 * Contains a companion object with the evaluation method
 *
 * @property generator is the DSL generator of Keval (defaults to the default resources)
 */
class Keval(
    private val generator: KevalDSL.() -> Unit = { includeDefault() }
) {
    companion object {
        /**
         * Evaluates a mathematical expression to a double value
         *
         * @param mathExpression is the expression to evaluate
         * @return the value of the expression
         * @throws KevalInvalidSymbolException in case there's an invalid operator in the expression
         * @throws KevalInvalidExpressionException in case the expression is invalid (i.e. mismatched parenthesis)
         * @throws KevalZeroDivisionException in case of a zero division
         */
        @Throws(
            KevalInvalidSymbolException::class,
            KevalInvalidSymbolException::class,
            KevalZeroDivisionException::class
        )
        fun eval(
            mathExpression: String,
        ): Double {
            return mathExpression.toAbstractSyntaxTree(KevalDSL.DEFAULT_RESOURCES).eval()
        }
    }

    /**
     * Evaluates a mathematical expression to a double value with given resources
     *
     * @param mathExpression is the expression to evaluate
     * @return the value of the expression
     * @throws KevalInvalidSymbolException in case there's an invalid operator in the expression
     * @throws KevalInvalidExpressionException in case the expression is invalid (i.e. mismatched parenthesis)
     * @throws KevalZeroDivisionException in case of a zero division
     * @throws KevalDSLException if at least one of the field isn't set properly
     */
    @Throws(
        KevalInvalidSymbolException::class,
        KevalInvalidSymbolException::class,
        KevalZeroDivisionException::class,
        KevalDSLException::class
    )
    fun eval(
        mathExpression: String,
    ): Double {
        val resources = KevalDSL()
        resources.generator()

        // The tokenizer assumes multiplication, hence disallowing overriding `*` operator
        val operators = resources.resources
            .plus("*" to KevalBinaryOperator(3, true) { a, b -> a * b })

        return mathExpression.toAbstractSyntaxTree(operators).eval()
    }
}

/**
 * Evaluates a mathematical expression to a double value with given resources
 *
 * @receiver is the expression to evaluate
 * @param generator is the DSL generator of Keval
 * @return the value of the expression
 * @throws KevalInvalidSymbolException in case there's an invalid operator in the expression
 * @throws KevalInvalidExpressionException in case the expression is invalid (i.e. mismatched parenthesis)
 * @throws KevalZeroDivisionException in case of a zero division
 * @throws KevalDSLException if at least one of the field isn't set properly
 */
@Throws(
    KevalInvalidSymbolException::class,
    KevalInvalidSymbolException::class,
    KevalZeroDivisionException::class,
    KevalDSLException::class
)
fun String.keval(
    generator: KevalDSL.() -> Unit
): Double {
    return Keval(generator).eval(this)
}

/**
 * Evaluates a mathematical expression to a double value with the default resources
 *
 * @receiver is the expression to evaluate
 * @return the value of the expression
 * @throws KevalInvalidSymbolException in case there's an invalid operator in the expression
 * @throws KevalInvalidExpressionException in case the expression is invalid (i.e. mismatched parenthesis)
 * @throws KevalZeroDivisionException in case of a zero division
 */
@Throws(
    KevalInvalidSymbolException::class,
    KevalInvalidSymbolException::class,
    KevalZeroDivisionException::class
)
fun String.keval(): Double {
    return Keval.eval(this)
}
