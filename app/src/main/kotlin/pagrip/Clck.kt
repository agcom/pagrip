package pagrip

fun clck(refs: List<Int>, framesQuantity: Int): Pair<Int, List<Pair<Array<Pair<Int, Boolean>?>, Int>>> {
	val frames = Array<Pair<Int, Boolean>?>(framesQuantity) { null } // Frame table
	val framePointerIterator = iterator {
		var c = 0
		while (true) {
			if (c == framesQuantity) c = 0
			yield(c++)
		}
	}
	var lastFramePointer = 0
	val pages = HashMap<Int, Int?>() // Page table
	val framesContains = HashSet<Int>(framesQuantity) // Efficient `contains`
	
	var pageFaults = 0
	val trace = mutableListOf<Pair<Array<Pair<Int, Boolean>?>, Int>>()
	trace += frames.copyOf() to lastFramePointer
	
	refs.forEach { ref ->
		if (ref !in framesContains) {
			pageFaults++
			while (true) {
				val frame = framePointerIterator.next()
				lastFramePointer = frame
				val content = frames[frame]
				if (content == null || !content.second) {
					// Put in this frame
					frames[frame] = ref to true
					pages[ref] = frame
					if (content != null) {
						val (outPage, _) = content
						framesContains -= outPage
						pages[outPage] = null
					}
					framesContains += ref
					break
				} else { // Frame in use; toggle it and move on.
					frames[frame] = content.first to false
				}
			}
		} else { // Set frame in use to true.
			val frame = pages[ref]!!
			frames[frame] = ref to true
		}
		
		trace += frames.copyOf() to (if (lastFramePointer + 1 == framesQuantity) 0 else lastFramePointer + 1)
	}
	
	return pageFaults to trace
}