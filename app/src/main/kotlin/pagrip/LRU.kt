package pagrip

fun lru(refs: List<Int>, framesQuantity: Int): Pair<Int, List<Array<Int?>>> {
	val framesDs =
		LinkedHashSet<Int>(framesQuantity) // Efficient `contains` & keeps recently-used order (contract: least recently used on first, most recently used on last).
	val frames = Array<Int?>(framesQuantity) { null } // or say, frame table.
	val pages = HashMap<Int, Int?>() // or say, page table.
	
	var pageFaults = 0
	val trace: MutableList<Array<Int?>> = mutableListOf()
	trace.add(frames.copyOf()) // Initial frames state
	
	refs.forEach { ref ->
		if (ref !in framesDs) {
			pageFaults++
			if (framesDs.size == framesQuantity) { // Frames are full; empty one frame, and put ref in.
				val page = framesDs.first() // Least recently used
				framesDs -= page
				
				val frame = pages.replace(page, null)!! // Update page table.
				frames[frame] = null // Empty the corresponding frame.
				
				framesDs += ref
				frames[frame] = ref
				pages[ref] = frame
			} else {
				// Put in the last empty frame.
				val frame = frames.indexOfFirst { it == null }
				
				framesDs += ref
				frames[frame] = ref
				pages[ref] = frame
			}
		} else {
			framesDs -= ref
			framesDs += ref
		}
		
		trace.add(frames.copyOf())
	}
	
	return pageFaults to trace
}