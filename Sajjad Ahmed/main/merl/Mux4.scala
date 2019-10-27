package merl

import chisel3._
class Mux4 extends Module
{
	val io=IO(new Bundle{
		val in1=Input(UInt(32.W))
		val in2=Input(UInt(32.W))
		val in3=Input(UInt(32.W))
		val in4=Input(UInt(32.W))
		val sel=Input(UInt(2.W))
		val out=Output(UInt(32.W))
})
	val m1=Module(new Mux())
	m1.io.sel:=io.sel(0)
	m1.io.a:=io.in1
	m1.io.b:=io.in2
	val m2=Module(new Mux())
	m2.io.sel:=io.sel(0)
	m2.io.a:=io.in3
	m2.io.b:=io.in4
	val m3=Module(new Mux())
	m3.io.sel:=io.sel(1)
	m3.io.a:=m1.io.out
	m3.io.b:=m2.io.out

	io.out:=m3.io.out
} 



