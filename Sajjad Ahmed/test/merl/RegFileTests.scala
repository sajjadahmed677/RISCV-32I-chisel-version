// See LICENSE.txt for license details.
package merl

import chisel3.iotesters.PeekPokeTester

class RegFileTests(c: RegFile) extends PeekPokeTester(c) {

   
   // expect(c.io.rdData,14)  

  def wr( wn: Int,wr_sel: Int,r_sel1: Int,r_sel2: Int)  = {
    val a=100
    val b=200
    poke(c.io.wEn,    wn)
    poke(c.io.write_sel, wr_sel)
    poke(c.io.write_data, a)
    poke(c.io.write_data, b)
   
  //if((r_sel1!=0) && (r_sel2!=0))
  //{
     poke(c.io.read_sel1, r_sel1)
     poke(c.io.read_sel2, r_sel2)
    step(1)
   // expect(c.io.read_data1, a)
  // expect(c.io.read_data2,b)
//}
  }
 wr(1,2,1,1)
 //wr(0,0,0,0,1,2)
 //rd(2, 3)
 // wr(9, 11)
  //rd(9, 11)
}




