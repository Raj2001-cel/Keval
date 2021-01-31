package com.notkamui.keval

import com.notkamui.keval.framework.Resources
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Tests on the Tokenizer
 */
class TokenizerTest {

    /**
     * Tests for String.tokenize()
     */
    @Test
    fun parseString() {
        val operators = Resources().loadBuiltInOperators()
        val tokens = "((34+8)/3)+3.3*(5+2)%2^6".tokenize(operators.keys)
        assertEquals(
            listOf(
                "(",
                "(",
                "34",
                "+",
                "8",
                ")",
                "/",
                "3",
                ")",
                "+",
                "3.3",
                "*",
                "(",
                "5",
                "+",
                "2",
                ")",
                "%",
                "2",
                "^",
                "6"
            ),
            tokens
        )

        val tokens2 = "(3+4 ) (2-5) ".tokenize(operators.keys) // check auto mul
        assertEquals(
            listOf("(", "3", "+", "4", ")", "*", "(", "2", "-", "5", ")"),
            tokens2
        )

        assertTrue {
            try {
                "(37+4)a+5".tokenize(operators.keys)
                false
            } catch (e: KevalInvalidOperatorException) {
                e.invalidOperator == "a" && e.position == 6 && e.expression == "(37+4)a+5"
            }
        }
    }
}
