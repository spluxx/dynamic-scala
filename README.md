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
**(2)** imperative and functional paradigms differ not in their problem/solution space but in their intepretation, representation of programs implied by their syntax, idioms, and toolkits. 
<br> 
**(3)** It's not so much about which language is better than another, but about being able to project one's perception of the problem *soundly* onto the target language. 
<br>
<br>
Deterministic algorithms, the subject of this project, require meticulous state manipulations in its very nature. The dryness of [this](https://github.com/vkostyukov/scalacaster) popular open-source project just shows how difficult it is to devise a purely functional algorithm and its associated data structure that is intellectually pleasing and yet, still performant.
<br>
<br>
Hence the `FoldExtension`. <br>
It encapsulates a small snippet of imperative style code within a functional context in a performant and comprehensive way.<br> *Note: State monads should be prefered when devising a set of state operations to be composed with each other.<br> FoldExtension, on the other hand, should be prefered when handling a single state-intensive task)<br>
##### loop over collections
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
The type signature is(disregarding syntactical components) equivalent to that of `fold`.
```scala
T => TraversableOnce[U] => ((T, U) => T) => T
```
##### loop until Break
```C++
// (1) Declare initial state values
double sq = square;
double sqrt = 1.0;
// (2) loop
while(true) {
// (3) Update State on each loop, until break is reached
  if(abs((sqrt*sqrt-sq)/sq) > 1e-6) sqrt = ((sq/sqrt)+sqrt)/2;
  else break;
}
```
```scala
val (sq, sqrt) = 
// (1) Declare initial state values
  init(square, 1.0)
// (2) loop  
    .loop { (x, y) =>
// (3) Update State on each loop, until break is reached
      if(math.abs((y*y-x)/x) > 1e-6) Next(x, ((x/y)+y)/2)
      else Break
    }
```
The type signature is(disregarding syntactical components) `T => (T => Control[T]) => T`
```scala
def recurse[T](t: T)(f: T => Control[T]): T = {
  f(t) match {
    case Next(nt) => recurse(nt)(f)
    case Break => t
  }
}
```

<br>
FoldExtension has zero overhead, and the efficiency of the implementation depends solely on the datastructure representing the state, and it is TBD. <br>

### References
https://www.cs.cmu.edu/~rwh/theses/okasaki.pdf - Okasaki's monumental paper on purely functional data structures

### Note
ANY feedbacks are more than welcome. ih33@duke.edu
