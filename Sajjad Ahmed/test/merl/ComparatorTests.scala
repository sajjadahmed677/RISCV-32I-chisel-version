// See LICENSE.txt for license details.
package merl

import chisel3.iotesters.{PeekPokeTester, Driver, ChiselFlatSpec}

class ComparatorTests(c: Comparator) extends PeekPokeTester(c) {
  for (t <- 0 until 4) {
    val in1 = 99
    val in2 = 99
    poke(c.io.in1, in1)
    poke(c.io.in2, in2)
    step(1)
    expect(c.io.greater, 0)
    expect(c.io.equal,1)
    expect(c.io.lesser,0)
  }
}

class ComparatorTester extends ChiselFlatSpec {
  behavior of "Comparator"
  backends foreach {backend =>
    it should s"correctly add randomly generated numbers $backend" in {
      Driver(() => new Comparator())(c => new ComparatorTests(c)) should be (true)
    }
  }
}
