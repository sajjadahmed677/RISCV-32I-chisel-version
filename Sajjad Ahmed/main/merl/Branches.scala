package merl 

import chisel3._
import chisel3.util.Cat

class Branches extends Module
{
    val io=IO(new Bundle{
        val in1     = Input(UInt(32.W))
        val in2     = Input(UInt(32.W))
        val control = Input(UInt(3.W))
        val out     = Output(UInt(1.W))
    })
       io.out := 0.U

    when(io.control === "b000".U)
    {
        io.out := io.in1 === io.in2
    }
    .elsewhen(io.control === "b001".U)
    {
        io.out := io.in1 =/= io.in2
    }
    .elsewhen(io.control === "b100".U)
    {
        io.out := io.in1 < io.in2
    }
    .elsewhen(io.control === "b101".U)
    {
        io.out := io.in1 >= io.in2
    }
    .elsewhen(io.control === "b110".U)
    {
        io.out := io.in1 < io.in2
    }
    .elsewhen(io.control === "b111".U)
    {
        io.out := io.in1 >= io.in2
    }
   
}
