// See LICENSE.txt for license details.
package merl

import chisel3.iotesters.{PeekPokeTester, Driver, ChiselFlatSpec}

class Pc1Tests(c: Pc1) extends PeekPokeTester(c) {

	val in=8
//	poke(c.io.clock,1)
	poke(c.io.in,in)
	step(1)

}

class Pc1Tester extends ChiselFlatSpec {
  behavior of "Pc1"
  backends foreach {backend =>
    it should s"correctly add randomly generated numbers $backend" in {
      Driver(() => new Pc1())(c => new Pc1Tests(c)) should be (true)
    }
  }
}
