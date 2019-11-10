package merl

import chisel3._

class ForwardM extends Module 
{
    val io= IO(new Bundle{
        val EMrs2   = Input(UInt(5.W))
        val EMwrite= Input(UInt(1.W))
        val MBread = Input(UInt(1.W))
        val MBrd   = Input(UInt(5.W))
        val out    = Output(UInt(1.W))
    })

    when((io.EMwrite === 1.U) && (io.MBread === 1.U) && (io.MBrd =/= 0.U) && (io.MBrd === io.EMrs2))
    {
        io.out := 1.U
    }
    .otherwise
    {
        io.out := 0.U
    }
}