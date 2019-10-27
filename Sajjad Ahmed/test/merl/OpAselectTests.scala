// See LICENSE.txt for license details.
package merl

import chisel3.iotesters.{PeekPokeTester, Driver, ChiselFlatSpec}

class OpAselectTests(c: OpAselect) extends PeekPokeTester(c) {

	
	val JALR =0
	val JAL  =1
	val Lui  =0
	poke(c.io.JALR,JALR)
	poke(c.io.JAL,JAL)	
	poke(c.io.Lui,Lui)
	step(1)
	if((JALR==1)||(JAL==1))
	{expect(c.io.OpAsel,2)}
	else if(Lui==1)
	{expect(c.io.OpAsel,3)}
	else
	{expect(c.io.OpAsel,0)}
}


class OpAselectTester extends ChiselFlatSpec {
  behavior of "OpAselect"
  backends foreach {backend =>
    it should s"correctly add randomly generated numbers $backend" in {
      Driver(() => new OpAselect())(c => new OpAselectTests(c)) should be (true)
    }
  }
}
