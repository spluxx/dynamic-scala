# Dynamic-Scala
## Overview
This is an experimental project to investigate long-debated efficiency and readability issues
of purely functional algorithms and data structures.

The goal is to 
1. grasp the actual cost/benefit(with emphasis on efficiency and readability) 
of various implementation strategy for concealing mutability - State, ST monads, folds, local vars(!!)
2. devise an implementation based on observations in 1
3. develop a deeper understanding of the JVM for optimization 

### How?
Every implementation will be followed by correctness test and microbenchmarking via (
<a href="https://github.com/scalatest/scalatest">ScalaTest</a> and
<a href="https://github.com/scalameter/scalameter">ScalaMeter</a>)

### References
https://www.cs.cmu.edu/~rwh/theses/okasaki.pdf - Okasaki's monumental paper on purely functional data structures
