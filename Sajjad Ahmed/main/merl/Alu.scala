package merl 
import chisel3._
import chisel3.util._

class Alu extends Module
{
	val io=IO(new Bundle{
		val in1=Input(UInt(32.W))
		val in2=Input(UInt(32.W))
		var func=Input(UInt(5.W))
		val branch=Output(UInt(1.W))
	//	val zero=Output(UInt(1.W))
		var out=Output(UInt(32.W))
		
})
	when(io.func===0.U)
	{io.out := io.in1+io.in2}
	.elsewhen(io.func===1.U)
	{io.out := io.in1 << io.in2(4,0)}
	.elsewhen(io.func===2.U)
	{
	 io.out := io.in1 < io.in2
	}
	.elsewhen(io.func===3.U) // for unsigned 
	{
	io.out := io.in1 < io.in2
	}
	.elsewhen(io.func===4.U)
	{io.out := io.in1 ^ io.in2}
	.elsewhen(io.func===5.U)
	{io.out := io.in1 >> io.in2(4,0)}
	.elsewhen(io.func===6.U)
	{io.out := io.in1 | io.in2}
	.elsewhen(io.func===7.U)
	{io.out := io.in1 & io.in2}
	.elsewhen(io.func===8.U)
	{io.out := io.in1 - io.in2}
	.elsewhen(io.func===13.U)
	{io.out := io.in1 >> io.in2(4,0)}
	.elsewhen(io.func===16.U)
	{
	 io.out := io.in1 === io.in2
	}
	.elsewhen(io.func===17.U)
	{io.out := io.in1=/= io.in2}
	.elsewhen(io.func===20.U)
	{io.out := io.in1 < io.in2}
	.elsewhen(io.func===21.U)
	{io.out := io.in1 >= io.in2}
	.elsewhen(io.func===22.U)
	{
		io.out := io.in1 < io.in2
	}
	.elsewhen(io.func===23.U)
	{io.out := io.in1 >= io.in2}
	.elsewhen(io.func===31.U)
	{io.out := io.in1}
	.otherwise
	{io.out := 0.U}
	io.branch := (io.func(4,3)===2.U) & (io.out===1.U)
	
}
