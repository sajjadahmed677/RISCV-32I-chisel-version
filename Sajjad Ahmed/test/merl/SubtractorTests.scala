// See LICENSE.txt for license details.
package merl

import chisel3.iotesters.{PeekPokeTester, Driver, ChiselFlatSpec}

class SubtractorTests(c: Subtractor) extends PeekPokeTester(c) {

    val  a=40
    val b =20
    val sub =20
    poke(c.io.a, a)
    poke(c.io.b, b)
    step(1)
    expect(c.io.sub, sub)
}

class SubtractorTester extends ChiselFlatSpec {
  behavior of "Subtractor"
  backends foreach {backend =>
    it should s"correctly add randomly generated numbers $backend" in {
      Driver(() => new Subtractor())(c => new SubtractorTests(c)) should be (true)
    }
  }
}
