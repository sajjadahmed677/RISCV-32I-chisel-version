// See LICENSE.txt for license details.
package merl

import chisel3.iotesters.{PeekPokeTester, Driver, ChiselFlatSpec}

class regTests(c: reg) extends PeekPokeTester(c) {

	val Wdata=0
	poke(c.io.clock,1)

	poke(c.io.Wdata,Wdata)
	step(1)

}

class regTester extends ChiselFlatSpec {
  behavior of "reg"
  backends foreach {backend =>
    it should s"correctly add randomly generated numbers $backend" in {
      Driver(() => new reg())(c => new regTests(c)) should be (true)
    }
  }
}
