// See LICENSE.txt for license details.
package merl

import chisel3.iotesters.PeekPokeTester

class dMemoTests(c: dMemo) extends PeekPokeTester(c) {
  def rd(data: Int) = {
   
    poke(c.io.ren, 1)
 //  poke(c.io.rdAddr, addr)
    step(1)
   expect(c.io.rdData, 10)
   // expect(c.io.rdData,14)  
}
  def wr(addr: Int,data: Int)  = {
    poke(c.io.wen,    1)
    poke(c.io.wrAddr, addr)
    poke(c.io.wrData, data)
    step(1)
  }
 wr(8,10)
 rd(8)
 // wr(9, 11)
  //rd(9, 11)
}
