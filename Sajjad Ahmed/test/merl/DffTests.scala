// See LICENSE.txt for license details.
package merl

import chisel3.iotesters.{PeekPokeTester, Driver, ChiselFlatSpec}

class DffTests(c: Dff) extends PeekPokeTester(c) {
    val in = 1
    val en = 0
    poke(c.io.in, in)
    poke(c.io.en, en)
    step(1)
 //   expect(c.io.sum, sum)
}

class DffTester extends ChiselFlatSpec {
  behavior of "Dff"
  backends foreach {backend =>
    it should s"correctly add randomly generated numbers $backend" in {
      Driver(() => new Dff())(c => new DffTests(c)) should be (true)
    }
  }
}
