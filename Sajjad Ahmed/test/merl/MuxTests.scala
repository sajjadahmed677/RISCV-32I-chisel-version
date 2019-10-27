// See LICENSE.txt for license details.
package merl

import chisel3.iotesters.{PeekPokeTester, Driver, ChiselFlatSpec}

class MuxTests(c: Mux) extends PeekPokeTester(c) {
  for (t <- 0 until 4) {
    val a = 2
    val b = 4
    val sel = rnd.nextInt(2)

    poke(c.io.a, a)
    poke(c.io.b, b)
    poke(c.io.sel, sel)
    step(1)
    expect(c.io.out,if(sel==0) a else b)
  }
}

class MuxTester extends ChiselFlatSpec {
  behavior of "Mux"
  backends foreach {backend =>
    it should s"correctly add randomly generated numbers $backend" in {
      Driver(() => new Mux())(c => new MuxTests(c)) should be (true)
    }
  }
}
