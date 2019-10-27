//tests
// See LICENSE.txt for license details.
package merl

import chisel3.iotesters.{PeekPokeTester, Driver, ChiselFlatSpec}

class OperandASelectTests(c:OperandASelect) extends PeekPokeTester(c) {
       
val LUI  = 0
        val JALR = 0
        val JAL = 0
        val out =0
poke(c.io.LUI,LUI)
poke(c.io.JALR,JALR)
poke(c.io.JAL,JAL)
step(1)
expect(c.io.out,0)
}



class OperandASelectTester extends ChiselFlatSpec {
  behavior of "OperandASelect"
  backends foreach {backend =>
    it should s"correctly add randomly generated numbers $backend" in {
      Driver(() => new OperandASelect())(c => new OperandASelectTests(c)) should be (true)
    }
  }
}
