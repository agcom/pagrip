package pagrip

import java.util.*

fun opt(refs: List<Int>, framesQuantity: Int): Pair<Int, List<Array<Int?>>> {
	val pagesNextRefsTimes = pagesNextRefsTimes(refs).mapValues { (_, v) -> v.toMutableList() }
	val framesContains = HashSet<Int>(framesQuantity) // Efficient `contains`
	val nextRefsOrder = TreeSet(compareBy<Pair<Int, Int>> { it.second }.reversed()) // Pair(page, next ref. index)
	val frames = Array<Int?>(framesQuantity) { null } // Frame table
	val pages = HashMap<Int, Int?>() // Page table

	var pageFaults = 0
	val trace = mutableListOf<Array<Int?>>()
	trace += frames.copyOf()

	refs.forEachIndexed { time, ref ->
		pagesNextRefsTimes[ref]!!.removeFirst()
		nextRefsOrder.remove(ref to time)

		if (ref !in framesContains) {
			pageFaults++
			if (framesContains.size == framesQuantity) { // Empty a frame and put ref in.
				val inPage = ref
				val (outPage, _) = nextRefsOrder.pollFirst()!!
				val frame = pages[outPage]!!

				framesContains -= outPage
				framesContains += inPage
				nextRefsOrder += inPage to (pagesNextRefsTimes[inPage]!!.firstOrNull() ?: Int.MAX_VALUE)
				frames[frame] = inPage
				pages[inPage] = frame
				pages[outPage] = null
			} else {
				// Put in the last empty frame.
				val frame = frames.indexOfFirst { it == null }

				framesContains += ref
				nextRefsOrder += ref to (pagesNextRefsTimes[ref]!!.firstOrNull() ?: Int.MAX_VALUE)
				frames[frame] = ref
				pages[ref] = frame
			}
		} else {
			nextRefsOrder += ref to (pagesNextRefsTimes[ref]!!.firstOrNull() ?: Int.MAX_VALUE)
		}

		trace += frames.copyOf()
	}

	return pageFaults to trace
}

private fun pagesNextRefsTimes(refs: List<Int>): Map<Int, List<Int>> {
	val m = mutableMapOf<Int, MutableList<Int>>()
	refs.forEachIndexed { i, ref ->
		m.getOrPut(ref) { mutableListOf() }.add(i)
	}
	return m
}