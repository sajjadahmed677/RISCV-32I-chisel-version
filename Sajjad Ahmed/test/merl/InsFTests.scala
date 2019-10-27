// See LICENSE.txt for license details.
package merl

import chisel3.iotesters.{PeekPokeTester, Driver, ChiselFlatSpec}

class InsFTests(c: InsF) extends PeekPokeTester(c) {

//	poke(c.io.clock,1)
//	poke(c.io.in,0)
	step(1)
}
