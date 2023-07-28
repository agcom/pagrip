package main

import java.util.concurrent.atomic.AtomicBoolean
import java.util.regex.Pattern

private lateinit var pRefs: List<Int>
val refs by lazy {
	readInput()
	pRefs
}

private var pFramesQuantity: Int = 0
val framesQuantity by lazy {
	readInput()
	pFramesQuantity
}

private val read = AtomicBoolean()
fun readInput() {
	if (read.compareAndSet(false, true)) {
		val delim = Pattern.compile("[ \t]*,[ \t]*").toRegex() // ',' delimiter with any spaces around
		pRefs = readln().split(delim)
			.map { requireNotNull(it.toIntOrNull()) { "each page reference must be a 32-bit integer (was $it)" } }
			.onEach { require(it >= 1) { "each page reference must be greater than or equal to 1 (was $it)" } }
		
		pFramesQuantity = requireNotNull(readln().toIntOrNull()) { "frames quantity must be a 32-bit integer" }
			.also { require(it > 0) { "frames quantity must be positive" } }
	}
}