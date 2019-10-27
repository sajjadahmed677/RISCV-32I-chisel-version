// See LICENSE.txt for license details.
package merl

import chisel3.iotesters.{PeekPokeTester, Driver, ChiselFlatSpec}

class PcTests(c: Pc) extends PeekPokeTester(c) {

	val input=8
//	poke(c.io.clock,1)
	poke(c.io.input,input)
	step(1)

}

