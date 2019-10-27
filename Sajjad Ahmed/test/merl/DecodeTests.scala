// See LICENSE.txt for license details.
package merl

import chisel3.iotesters.{PeekPokeTester, Driver, ChiselFlatSpec}

class DecodeTests(c: Decode) extends PeekPokeTester(c) {

	val instruction = 4286574831L
	val pc          = 0
	poke(c.io.instruction,instruction)
	poke(c.io.pc,pc)
	step(1)
	expect(c.io.Memwrite,0)
	expect(c.io.Branch,0)
	expect(c.io.Memread,0)
	expect(c.io.MemtoReg,0)
	expect(c.io.Regwrite,1)
	expect(c.io.AluOp,3)
	expect(c.io.OpA_sel,2)
	expect(c.io.OpB_sel,0)
	expect(c.io.Extend_sel,0)
	expect(c.io.NextPc_sel,2)
	expect(c.io.Readreg_1,31)
	expect(c.io.Readreg_2,23)
	expect(c.io.WriteReg,1)
	expect(c.io.SUI,4294967287L)
	expect(c.io.Sb_type,4294967232L)
	expect(c.io.Uj_type,4194284)
//	expect(c.io.Func,31)
	
}	



class DecodeTester extends ChiselFlatSpec {
  behavior of "Decode"
  backends foreach {backend =>
    it should s"correctly add randomly generated numbers $backend" in {
      Driver(() => new Decode())(c => new DecodeTests(c)) should be (true)
    }
  }
}
