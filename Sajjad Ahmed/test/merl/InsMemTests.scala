// See LICENSE.txt for license details.
package merl

import chisel3.iotesters.{PeekPokeTester, Driver, ChiselFlatSpec}

class InsMemTests(c: InsMem) extends PeekPokeTester(c) {

//	poke(c.io.clock,1)
	poke(c.io.wrAddr,0)
	step(1)
}
