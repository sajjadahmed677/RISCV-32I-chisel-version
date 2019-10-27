package merl

import chisel3._

class Dff extends Module
{
	val io=IO(new Bundle{
		val in=Input(UInt(1.W))
		val en=Input(UInt(1.W))
		val out=Output(UInt(1.W))
})
	
	when(io.en===0.U)
	{
	io.out := 0.U
	//	when(io.out===1.U)
	//	{io.out := 1.U}
	//	.otherwise
	//	{io.out := 0.U}
	}
	.otherwise
	{io.out := io.in}

}
