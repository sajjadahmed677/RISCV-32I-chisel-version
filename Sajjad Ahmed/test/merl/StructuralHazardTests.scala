package merl
import chisel3.iotesters.{PeekPokeTester, Driver, ChiselFlatSpec}
class StructuralHazardTests(c: StructuralHazard) extends PeekPokeTester(c) {
  
  
    poke(c.io.WBrd ,1)
    poke(c.io.IDrs1,1)
    poke(c.io.IDrs2,1)
    poke(c.io.RegWrite,1)
    step(1)
   // expect(c.io.out1,Out1)
    //expect(c.io.out2,)
}