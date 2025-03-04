package br.com.spring.sale.utils.others

import br.com.spring.sale.utils.others.ConstantsUtils.FROM
import br.com.spring.sale.utils.others.ConstantsUtils.UNTIL
import kotlin.random.Random

fun generateCode(): Long {
    return Random.nextLong(from = FROM, until = UNTIL)
}
