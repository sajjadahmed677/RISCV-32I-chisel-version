// See LICENSE.txt for license details.
package merl

import chisel3.iotesters.{PeekPokeTester, Driver, ChiselFlatSpec}

class AluControllTests(c: AluControll) extends PeekPokeTester(c) {

	val func3=3
  	val func7=0
	var Aluop=0
	poke(c.io.func3,func3)
	poke(c.io.func7,func7)
	poke(c.io.Aluop,Aluop)
	step(1)
	
	expect(c.io.out,3)	
}	



class AluControllTester extends ChiselFlatSpec {
  behavior of "AluControll"
  backends foreach {backend =>
    it should s"correctly add randomly generated numbers $backend" in {
      Driver(() => new AluControll())(c => new AluControllTests(c)) should be (true)
    }
  }
}
