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

### FoldExtension 
An unrefined but insightful reasoning behind the creation of `FoldExtension` is that 
<br> 
**(1)** all programs for execution are, at the very bottom, a function `State => (State, Result)` and hence 
<br>
**(2)** imperative and functional paradigms differ not in their problem/solution space but in their intepretation, representation of programs implied by their vocabularies and idioms. 
<br> 
**(3)** It's not so much about which language is better than another, but about being able to project one's perception of the problem soundly onto the language.
<br>
<br>
Deterministic algorithms, the subject of this project, require meticulous state manipulations in its very nature. The dryness of [this](https://github.com/vkostyukov/scalacaster) popular open-source project just shows how difficult it is to devise a purely functional algorithm and its associated data structure that is intellectually pleasing yet still performant.
<br>
<br>
Hence the `FoldExtension`. <br>
It encapsulates the imperative style within a functional context, in a performant and comprehensive way.
```C++
// (1) Declare initial state values
int fibHead = 1;
int fibTail = 1;
// (2) Declare what to loop on
for(int i = 2 ; i <= N ; i ++) {
// (3) Update State on each loop
  int t = fibHead;
  fibHead += fibTail;
  fibTail = t;
}
```
```scala
val (fibTail, fibHead): (Int, Int) = 
// (1) Declare initial state values
  init(1, 1)
// (2) Declare what to loop on
    .loop(2 to N) { (fibTail, fibHead, _) =>
// (3) Update State on each loop
      (fibHead, fibHead+fibTail)
    }
```
The type signature is(disregarding syntactical components) basically a `fold` as the name suggests.
```scala
T => TraversableOnce[U] => ((T, U) => T) => T
```
<br>
FoldExtension has basically zero overhead(... debatable), and the efficiency of the implementation depends solely on the datastructure representing the state, and it is TBD. <br>

### References
https://www.cs.cmu.edu/~rwh/theses/okasaki.pdf - Okasaki's monumental paper on purely functional data structures

### Note
ANY feedbacks are more than welcome. ih33@duke.edu
