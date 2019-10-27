// See LICENSE.txt for license details.
package merl


import chisel3.iotesters.{PeekPokeTester, Driver, ChiselFlatSpec}

class PriorityEncoderTests(c: PriorityEncoder) extends PeekPokeTester(c){
  for (t <- 0 until 4) {
    val I0 = 0
    val I1 = 1
    val I2 = 0
    val I3 = 0
    val I4 = 0
    val I5 = 0
    val I6 = 0
    val I7 = 0
    val out1 = 1
    val out2 = 0
    val out3 = 0
    poke(c.io.I0,I0)
    poke(c.io.I1,I1)
    poke(c.io.I2,I2)
    poke(c.io.I3,I3)
    poke(c.io.I4,I4)
    poke(c.io.I5,I5)
    poke(c.io.I6,I6)
    poke(c.io.I7,I7)
    step(1)
   
    expect(c.io.out1,out1)
    expect(c.io.out2,out2)
    expect(c.io.out3,out3)

  }
}

class PriorityEncoderTester extends ChiselFlatSpec {
  behavior of "PriorityEncoder"
  backends foreach {backend =>
    it should s"correctly add randomly generated numbers $backend" in {
      Driver(() => new PriorityEncoder, backend)(c => new PriorityEncoderTests(c)) should be (true)
    }
  }
}
