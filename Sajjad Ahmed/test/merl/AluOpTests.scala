// See LICENSE.txt for license details.
package merl

import chisel3.iotesters.{PeekPokeTester, Driver, ChiselFlatSpec}

class AluOpTests(c: AluOp) extends PeekPokeTester(c) {

	val R_type =0
	val I_type =1
	val store  =0
	val load   =0
	val Branch =0
	val JALR =0
	val JAL  =0
	val Lui  =0
	poke(c.io.R_type,R_type)
	poke(c.io.I_type,I_type)
	poke(c.io.store,store)
	poke(c.io.load, load )
	poke(c.io.Branch,Branch)
	poke(c.io.JALR,JALR)
	poke(c.io.JAL,JAL)	
	poke(c.io.Lui,Lui)
	step(1)
	if(R_type==1)
	{expect(c.io.Aluop,0)}
	else if(I_type==1)
	{expect(c.io.Aluop,1)}
	else if(store==1)
	{expect(c.io.Aluop,5)}
	else if(load==1)
	{expect(c.io.Aluop,4)}
	else if(Branch==1)
	{expect(c.io.Aluop,2)}
	else if((JALR==1)||(JAL==1))
	{expect(c.io.Aluop,3)}
	else if(Lui==1)
	{expect(c.io.Aluop,6)}
	else
	{expect(c.io.Aluop,7)}
}


class AluOpTester extends ChiselFlatSpec {
  behavior of "AluOp"
  backends foreach {backend =>
    it should s"correctly add randomly generated numbers $backend" in {
      Driver(() => new AluOp())(c => new AluOpTests(c)) should be (true)
    }
  }
}
