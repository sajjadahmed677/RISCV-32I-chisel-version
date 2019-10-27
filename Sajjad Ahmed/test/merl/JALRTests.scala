// See LICENSE.txt for license details.
package merl

import chisel3.iotesters.{PeekPokeTester, Driver, ChiselFlatSpec}
class JALRTests(c: JALR) extends PeekPokeTester(c) {
       
        val a  = 0
        val b  = 4
        val out =4
poke(c.io.a,a)
poke(c.io.b,b)
step(1)
expect(c.io.out,out)
}



class JALRTester extends ChiselFlatSpec {
  behavior of "JALR"
  backends foreach {backend =>
    it should s"correctly add randomly generated numbers $backend" in {
      Driver(() => new JALR())(c => new JALRTests(c)) should be (true)
    }
  }
}
