package merl
import chisel3._
import chisel3.util.Cat
class NextPcSelect extends Module
{
val io= IO(new Bundle{
    val b  = Input(UInt(1.W))
    val j  = Input(UInt(1.W))
    val jr = Input(UInt(1.W))
    val out = Output(UInt(2.W))

})
val and1 = (~io.b) & (~io.j) & (io.jr)
val and2 = (~io.b) & (io.j) & (~io.jr)
val out1  = and1 | and2
val and3 = (~io.b) & (~io.j) & (io.jr)
val and4 = (io.b) & (~io.j) & (~io.jr)
val out2 = and3 | and4
io.out := Cat(out1,out2)
}
