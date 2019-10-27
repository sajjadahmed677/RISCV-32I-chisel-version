package merl
import chisel3._
import chisel3.util._
class Mux extends Module
{
	val io=IO(new Bundle{
		val a=Input(UInt(32.W))
		val b= Input(UInt(32.W))
		val sel=Input(UInt(32.W))
		val out=Output(UInt(32.W))
})
	io.out:=(io.a&(~io.sel))+(io.sel&io.b)
	
}
