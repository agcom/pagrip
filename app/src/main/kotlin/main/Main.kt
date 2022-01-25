package main

import pagrip.clck
import pagrip.fifo
import pagrip.lru
import pagrip.opt

fun main() {
	readInput()
	p1("Opt", ::opt)
	p1("LRU", ::lru)
	p1("FIFO", ::fifo)
	pClck()
}

private fun p1(title: String, p: (List<Int>, Int) -> Pair<Int, List<Array<Int?>>>) {
	println("$title:")
	val (pageFaults, trace) = p(refs, framesQuantity)
	println("\tPF = $pageFaults")
	print("\tTrace: ${trace.toTraceString()}")
	println()
	println()
}

private fun Array<Int?>.toFramesString(): String {
	return joinToString(" ", "[", "]") { it?.toString() ?: "-" }
}

private fun List<Array<Int?>>.toTraceString(): String {
	return joinToString(" ") { it.toFramesString() }
}

private fun pClck() {
	println("Clock:")
	val (pageFaults, trace) = clck(refs, framesQuantity)
	println("\tPF = $pageFaults")
	print("\tTrace: ${trace.toClckTraceString()}")
	println()
	println()
}

private fun List<Pair<Array<Pair<Int, Boolean>?>, Int>>.toClckTraceString(): String {
	return joinToString(" ") { it.toClckFramesString() }
}

private fun Pair<Array<Pair<Int, Boolean>?>, Int>.toClckFramesString(): String {
	val (frames, framesPointer) = this
	var i = 0
	return frames.joinToString(" ", "[", "]") {
		val pointed = i == framesPointer
		if (it != null) {
			val (page, inUse) = it
			val sb = StringBuilder()
			if (pointed) sb.append("->")
			sb.append(page)
			if (inUse) sb.append('*')
			i++
			sb.toString()
		} else {
			i++
			if (pointed) "->-" else "-"
		}
	}
}