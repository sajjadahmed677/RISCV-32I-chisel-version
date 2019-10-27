// See LICENSE.txt for license details.
package merl
import chisel3.iotesters.{PeekPokeTester, Driver, ChiselFlatSpec}
class ForwardTests(c: Forward) extends PeekPokeTester(c) {
    val EMregW = 1
    val EMrd    = 1
    val IDrs1   = 1
    val  IDrs2   = 1
    val MBregW = 1
    val MBrd   = 1
   // val Out1 = 1
  //  val Out2 = 1
    poke(c.io.EMregW, EMregW)
    poke(c.io.EMrd ,EMrd)
    poke(c.io. IDrs1,IDrs1)
    poke(c.io.IDrs2,IDrs2)
    poke(c.io.MBregW,MBregW)
    poke(c.io.MBrd,MBrd)
    step(1)
   // expect(c.io.out1,Out1)
    //expect(c.io.out2,)
}

class ForwardTester extends ChiselFlatSpec {
  behavior of "Forward"
  backends foreach {backend =>
    it should s"correctly add randomly generated numbers $backend" in {
      Driver(() => new Forward())(c => new ForwardTests(c)) should be (true)
    }
  }
}
