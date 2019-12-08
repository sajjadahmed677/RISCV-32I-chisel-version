package merl 

import chisel3._

class StructuralHazard extends Module
{
    val io=IO(new Bundle{
        val WBrd = Input(UInt(5.W))
        val IDrs1= Input(UInt(5.W))
        val IDrs2= Input(UInt(5.W))
        val RegWrite= Input(UInt(1.W))
        val out1 = Output(UInt(1.W))
        val out2 = Output(UInt(1.W))
    })

    io.out1 := 0.U
    io.out2 := 0.U

    when((io.RegWrite === 1.U) && (io.WBrd === io.IDrs1) && (io.WBrd === io.IDrs2))
    {
        io.out1 := "b1".U
        io.out2 := "b1".U
    }
    .elsewhen((io.RegWrite === 1.U) && (io.WBrd === io.IDrs1))
    {
        io.out1 := "b1".U
    }
    .elsewhen((io.RegWrite === 1.U) && (io.WBrd === io.IDrs2))
    {
        io.out2 := "b1".U
    }

}