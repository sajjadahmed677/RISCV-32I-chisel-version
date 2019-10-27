// See LICENSE.txt for license details.
package merl

import chisel3.iotesters.{PeekPokeTester, Driver, ChiselFlatSpec}

class AluTests(c: Alu) extends PeekPokeTester(c) {

	val in1=3
  	val in2=2
	var func=1
	poke(c.io.in1,in1)
	poke(c.io.in2,in2)
	poke(c.io.func,func)
	step(1)
	func match
		{
		   case 0 => expect(c.io.out, 5)
		   case 1 => expect(c.io.out, 12)
		   case 2 => expect(c.io.out, 0) 
		   case 3 => expect(c.io.out, 0)
		   case 4 => expect(c.io.out, 1)
		   case 5 => expect(c.io.out, 0) 
		   case 6 => expect(c.io.out, 3)
		   case 7 => expect(c.io.out, 2)
		   case 8 => expect(c.io.out, 1)
		   case 13 => expect(c.io.out,0)
		   case 16 => expect(c.io.out, 0) 
		   case 17 => expect(c.io.out, 1)
		   case 20 => expect(c.io.out, 0)
		   case 21 => expect(c.io.out, 1)
		   case 22 => expect(c.io.out, 0)
		   case 23 => expect(c.io.out, 1) 
		   case 31 => expect(c.io.out, in1)
		}	
}	



class AluTester extends ChiselFlatSpec {
  behavior of "Alu"
  backends foreach {backend =>
    it should s"correctly add randomly generated numbers $backend" in {
      Driver(() => new Alu())(c => new AluTests(c)) should be (true)
    }
  }
}
