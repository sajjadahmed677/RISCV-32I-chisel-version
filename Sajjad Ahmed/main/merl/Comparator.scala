package merl 
import chisel3._
import chisel3.util._

class Comparator extends Module
{
	val io=IO(new Bundle{
		val in1=Input(UInt(7.W))
		val in2=Input(UInt(7.W))
		val greater=Output(UInt(1.W))
		val equal=Output(UInt(1.W))
		val lesser=Output(UInt(1.W))
})
	io.greater := io.in1 > io.in2
	io.equal := io.in1 === io.in2
	io.lesser := io.in1 < io.in2
	
}
