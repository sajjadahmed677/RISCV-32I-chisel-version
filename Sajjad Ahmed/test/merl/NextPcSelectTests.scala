// See LICENSE.txt for license details.
package merl

import chisel3.iotesters.{PeekPokeTester, Driver, ChiselFlatSpec}

class NextPcSelectTests(c: NextPcSelect) extends PeekPokeTester(c) {
       
        val b  = 0
        val j  = 0
        val jr = 0
        val out =0
poke(c.io.b,b)
poke(c.io.j,j)
poke(c.io.jr,jr)
step(1)
expect(c.io.out,0)
}



class NextPcSelectTester extends ChiselFlatSpec {
  behavior of "NextPcSelect"
  backends foreach {backend =>
    it should s"correctly add randomly generated numbers $backend" in {
      Driver(() => new NextPcSelect())(c => new NextPcSelectTests(c)) should be (true)
    }
  }
}
