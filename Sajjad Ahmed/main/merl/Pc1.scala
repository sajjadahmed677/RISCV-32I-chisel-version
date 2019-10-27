package merl
import chisel3._
class Pc1 extends Module
{
	val io=IO(new Bundle{
		var in=Input(UInt(32.W))
		val out=Output(UInt(32.W))
		val p  = Output(UInt(32.W))
		
})

	var reg=RegInit(0.U(32.W))
	reg:=io.in
	io.out:=reg+4.U
	io.p := reg
 
}
