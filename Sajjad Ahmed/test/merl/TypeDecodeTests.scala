// See LICENSE.txt for license details.
package merl

import chisel3.iotesters.{PeekPokeTester, Driver, ChiselFlatSpec}

class TypeDecodeTests(c: TypeDecode) extends PeekPokeTester(c) {

	val opcode=99
	poke(c.io.opcode,opcode)
	step(1)
	opcode match
		{
		   case 51 => expect(c.io.R_type, 1)
		   case 3 => expect(c.io.load, 1)
		   case 35 => expect(c.io.store, 1) 
		   case 99 => expect(c.io.branch, 1)
		   case 19 => expect(c.io.I_type, 1)
		   case 103 => expect(c.io.JALR, 1)
		   case 111 => expect(c.io.JAL, 1) 
		   case 55 => expect(c.io.Lui, 1)
		}	
}	



class TypeDecodeTester extends ChiselFlatSpec {
  behavior of "TypeDecode"
  backends foreach {backend =>
    it should s"correctly add randomly generated numbers $backend" in {
      Driver(() => new TypeDecode())(c => new TypeDecodeTests(c)) should be (true)
    }
  }
}
