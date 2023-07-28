# PagRIP

Simulation of the following page replacement algorithms:

- [Optimal](https://en.wikipedia.org/wiki/Page_replacement_algorithm#The_theoretically_optimal_page_replacement_algorithm)
- [LRU](https://en.wikipedia.org/wiki/Page_replacement_algorithm#Least_recently_used)
- [FIFO](https://en.wikipedia.org/wiki/Page_replacement_algorithm#First-in,_first-out)
- [Clock](https://en.wikipedia.org/wiki/Page_replacement_algorithm#Clock)

[![Usage](https://asciinema.org/a/WVkCtpxqBrfRGhUyXA0GS4QTM.svg)](https://asciinema.org/a/WVkCtpxqBrfRGhUyXA0GS4QTM)

This project was made as an assessment for a Operating Systems university course.

## Simple Build & Run

### Linux

```shell
cat ./sample-input.txt | ./gradlew --quiet :app:run
```

### Windows

```powershell
cat ./sample-input.txt | gradlew.bat --quiet :app:run
```

## Input Specification

See [the sample input file](./sample-input.txt); this is a line-by-line description:

1. Ordered page requests separated with commas
2. Memory frames' quantity

## Output Specification

1. Number of page faults (PF)
2. Memory frames' contents after each page request (trace)

	> For the clock algorithm, the trace contains use-bit (*) and pointer (->) indicators.