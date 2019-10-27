// See LICENSE.txt for license details.
package merl

import chisel3.iotesters.{PeekPokeTester, Driver, ChiselFlatSpec}

class ControlDecodeTests(c: ControlDecode) extends PeekPokeTester(c) {

	val R_type=0
	val load=0
	val store=0
	val branch=0
	val I_type=1
	val JALR = 0
	val JAL  = 0
	val Lui  = 0
	poke(c.io.R_type,R_type)
	poke(c.io.load,load)
	poke(c.io.store,store)
	poke(c.io.branch,branch)
	poke(c.io.I_type,I_type)
	poke(c.io.JALR,JALR)
	poke(c.io.JAL,JAL)
	poke(c.io.Lui,Lui)
	step(1)
	expect(c.io.Aluoperation,1)
	expect(c.io.MemtoReg,0)
	expect(c.io.Regwrite,1)
	expect(c.io.MemRead,0)
	expect(c.io.Memwrite,0)
	expect(c.io.Branch,0)
	expect(c.io.OpAsel,0)
	expect(c.io.OpBsel,1)
	expect(c.io.extendsel,0)
	expect(c.io.nextpcsel,0)	
}	



class ControlDecodeTester extends ChiselFlatSpec {
  behavior of "ControlDecode"
  backends foreach {backend =>
    it should s"correctly add randomly generated numbers $backend" in {
      Driver(() => new ControlDecode())(c => new ControlDecodeTests(c)) should be (true)
    }
  }
}
