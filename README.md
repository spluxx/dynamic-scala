# Dynamic-Scala
## Overview
This is an experimental project to investigate long-debated efficiency and readability issues
of purely functional algorithms and data structures.

The goal is to 
1. Investigate the cost/benefits of various state-encapsulation strategies(State, ST, folds, ...)
2. Implement algorithms based on observations from 1

### How?
Every implementation will be followed by correctness test and microbenchmarking via (
[ScalaTest](https://github.com/scalatest/scalatest) and
[ScalaMeter](https://github.com/scalameter/scalameter))

### Why FoldExtension 
An unrefined reasoning behind the creation of `FoldExtension` is that 
<br> 
**(1)** all programs for execution are, at the very bottom, a function `State => (State, Result)` and hence 
<br>
**(2)** imperative and functional paradigms differ not in their problem/solution space but in their intepretation, representation of programs implied by their vocabularies and idioms. 
<br> 
**(3)** It's not so much about which language is better than another, but about being able to project one's perception of the problem soundly onto the language.
<br>
<br>
Deterministic algorithms are extremely low-entropy composition of `State => (State, Result)` function in a sense that they (psychotically) minimize and organize the domain in a principled way, until just enough composition is done to produce the answer. It is evident that meticulous state manipulations are required in its very nature.
<br>
<br>
Doing so in idiomatic Scala isn't as easy nor efficient. The dryness of [this](https://github.com/vkostyukov/scalacaster) popular open-source project just shows how difficult it is to devise a purely functional algorithm that is asthetically pleasing yet still performant.
<br>
<br>
TBD

### References
https://www.cs.cmu.edu/~rwh/theses/okasaki.pdf - Okasaki's monumental paper on purely functional data structures
