# AdventOfCode2020
This repository holds my solutions and helper code for [Advent Of Code 2020](https://adventofcode.com/2020).
These are by no means the "best" or "fastest" solutions out there. These are just my attempt at the challenge.
I may add proper benchmarking at a later date if I feel like it.

### Running A Specific Day
This program runs a specific day based on a few factors inside the `Main` class.
If it is the month of December in EST time, whatever current day it is in EST time will be selected to run.
If this program is not run during the month of December, it will run ALL days by default.
An output from a day looks like the following:
```text
Day 1:
Part 1: 123
Part 2: 456
Completed in 0.529s
```
Note that "Completed in X.XXXs" is not a real or accurate benchmark but my own hacky way to measure how long a day takes to run, approximately.
If you would like to run a specific day outside the month of the December, use `run(int)` in the `Main` class.

### Information About Data Manager
This program can *optionally* read input data for a specified day **using the Advent Of Code servers**.
To enable this feature, you must include a `session.txt` file in the working directory.
This file should hold your session cookie from the Advent Of Code website, which can be found with browser inspection.

#### Retrieving Input File
If the input file is successfully retrieved from the servers, this data is cached in a text file relative to your run directory in the `aoc_input` directory.
Fetching data from the Advent Of Code servers is **only** used if a file with the input data for a specified day cannot be found.
See the documentation on `DataManager#read` for more detail.
