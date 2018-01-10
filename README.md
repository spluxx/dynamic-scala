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
Deterministic algorithms, the subject of this project, require meticulous state manipulations in its very nature. The dryness of [this](https://github.com/vkostyukov/scalacaster) popular open-source project just shows how difficult it is to devise a purely functional algorithm and its associated data structure that is intellectually pleasing and yet, still performant.
<br>
<br>
Hence the `FoldExtension`. <br>
It encapsulates a small snippet of imperative style code within a functional context in a performant and comprehensive way.<br> *Note: State monads should be prefered when devising a set of state operations to be composed with each other.<br> FoldExtension, on the other hand, should be prefered when handling a single but iterative state mutating task)<br>
##### loop over collections
`C++`
```C++
int fibHead = 1;
int fibTail = 1;
for(int i = 2 ; i <= N ; i ++) {
  int t = fibHead;
  fibHead += fibTail;
  fibTail = t;
}
```
`Scala`
```scala
val (fibTail, fibHead): (Int, Int) = 
  init(1, 1)
    .loop(2 to N) { (fibTail, fibHead, _) =>
      (fibHead, fibHead+fibTail)
    }
```
The type signature is(disregarding syntactical components) equivalent to that of `fold`.
```scala
T => TraversableOnce[U] => ((T, U) => T) => T
```
##### loop until Break
`C++`
```C++
double sq = square;
double sqrt = 1.0;
while(true) {
  if(abs((sqrt*sqrt-sq)/sq) > 1e-6) sqrt = ((sq/sqrt)+sqrt)/2;
  else break;
}
```
`Scala`
```scala
val (sq, sqrt) = 
  init(square, 1.0)
    .loop { (x, y) =>
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
