package merl 
import chisel3._

class Subtractor extends Module
{
	val io=IO(new Bundle{

	 val a= Input(UInt(32.W))	
	 val b= Input(UInt(32.W))
	 val sub= Output(UInt(32.W)) 
 
})
	io.sub:=io.a-io.b
}
