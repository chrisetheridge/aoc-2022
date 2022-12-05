# AOC 2022 in Clojure

Uses [babashka](https://github.com/babashka/babashka) to run everything.

## Running the code

For a repl:

```
bb nrepl-server 9992
```

To execute a day: 

```
bb -x aoc-2022.day1/part-one
```

You can also use the `run-day` task:

```
bb run-day 1
```

## Starting a new day

You can start a new day with `init-day`:

```
bb init-day -d 10
```

This will create a source file, `src/aoc_2022/day10.clj`, and an input file, `resources/inputs/day10.txt`, for the new day.

The input file will be populated with the input from adventofcode.com if possible. Set the environment variable `AOC_SESSION` to the value of your session cookie so that the input can be read.
