// See LICENSE.txt for license details.
package merl

import chisel3.iotesters.{PeekPokeTester, Driver, ChiselFlatSpec}

class ImmGenTests(c: ImmGen) extends PeekPokeTester(c) {

  	val instruction=4286574831L
	var pc = 0
	poke(c.io.instruction,4286574831L)
	poke(c.io.pc,0)
	step(1)
	expect(c.io.s_im,4294967265L)
       expect(c.io.sb_im,4294967232L )
	expect(c.io.u_im,4286574592L)
       expect(c.io.uj_im,4294967276L)
	expect(c.io.i_im,4294967287L)
	
}	



class ImmGenTester extends ChiselFlatSpec {
  behavior of "ImmGen"
  backends foreach {backend =>
    it should s"correctly add randomly generated numbers $backend" in {
      Driver(() => new ImmGen())(c => new ImmGenTests(c)) should be (true)
    }
  }
}
