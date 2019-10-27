package merl 

import chisel3._

class reg extends Module
{
	val io = IO(new Bundle{
		val clock = Input(Bool())
		val Wdata = Input(UInt(32.W))
		val Rdata = Output(UInt(32.W))
		val pout  = Output(UInt(32.W))
})
	val mem = SyncReadMem(1, UInt(32.W))
	val ad = Module(new Adder())
	
	 mem.write(1.U,io.Wdata)
	io.Rdata:=0.U
	 when (io.clock) 
	{								
		io.Rdata := mem.read(1.U) 
		
		
	}
	        ad.io.b  := 4.U
		ad.io.a  := io.Rdata
		io.pout := ad.io.sum
}
