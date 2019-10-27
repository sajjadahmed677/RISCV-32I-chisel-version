// See LICENSE.txt for license details.
package merl

import chisel3.iotesters.{PeekPokeTester, Driver, ChiselFlatSpec}

class AdderTests(c: Adder) extends PeekPokeTester(c) {
    val a = -1
    val b = 3
    val sum = 2
    poke(c.io.a, a)
    poke(c.io.b, b)
    step(1)
    expect(c.io.sum, sum)
}

class AdderTester extends ChiselFlatSpec {
  behavior of "Adder"
  backends foreach {backend =>
    it should s"correctly add randomly generated numbers $backend" in {
      Driver(() => new Adder())(c => new AdderTests(c)) should be (true)
    }
  }
}
