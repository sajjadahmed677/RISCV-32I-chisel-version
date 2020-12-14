`timescale 1 ns/1 ps
`include "/home/sajjada/RV32I-chisel-version/powered_netlist/Ibtida_top_dffram_cv.v"
`include "/home/sajjada/RV32I-chisel-version/powered_netlist/sky130A/libs.ref/sky130_fd_sc_hd/verilog/sky130_fd_sc_hd.v"
`include "/home/sajjada/RV32I-chisel-version/powered_netlist/sky130A/libs.ref/sky130_fd_sc_hd/verilog/primitives.v"
//`include "/home/zainrizkhan/pdks"
//nclude "~/RV32I-chisel-version/powered_netlist/sky130A/"
//nclude "/home/merlproj/backend-tools/pdks/sky130A/"


//`define UNIT_DELAY #1

//`ifdef SIM

//`define USE_POWER_PINS

/* NOTE: Need to pass the PDK root directory to iverilog with option -I */

/*`include "/home/hamza52/sky130A/libs.ref/sky130_fd_io/verilog/sky130_fd_io.v"
`include "/home/hamza52/sky130A/libs.ref/sky130_fd_io/verilog/sky130_ef_io.v"
`include "/home/hamza52/sky130A/libs.ref/sky130_fd_io/verilog/sky130_ef_io__gpiov2_pad_wrapped.v"

`include "/home/hamza52/sky130A/libs.ref/sky130_fd_sc_hd/verilog/primitives.v"
`include "/home/hamza52/sky130A/libs.ref/sky130_fd_sc_hd/verilog/sky130_fd_sc_hd.v"
`include "/home/hamza52/sky130A/libs.ref/sky130_fd_sc_hvl/verilog/primitives.v"
`include "/home/hamza52/sky130A/libs.ref/sky130_fd_sc_hvl/verilog/sky130_fd_sc_hvl.v"
*/

/*`include "/home/hamza52/projects/efabless/tech/SW/sky130A/libs.ref/sky130_fd_io/verilog/sky130_fd_io.v"
`include "/home/hamza52/projects/efabless/tech/SW/sky130A/libs.ref/sky130_fd_io/verilog/sky130_ef_io.v"
`include "/home/hamza52/projects/efabless/tech/SW/sky130A/libs.ref/sky130_fd_io/verilog/sky130_ef_io__gpiov2_pad_wrapped.v"

`include "/home/hamza52/projects/efabless/tech/SW/sky130A/libs.ref/sky130_fd_sc_hd/verilog/primitives.v"
`include "/home/hamza52/projects/efabless/tech/SW/sky130A/libs.ref/sky130_fd_sc_hd/verilog/sky130_fd_sc_hd.v"
`include "/home/hamza52/projects/efabless/tech/SW/sky130A/libs.ref/sky130_fd_sc_hvl/verilog/primitives.v"
`include "/home/hamza52/projects/efabless/tech/SW/sky130A/libs.ref/sky130_fd_sc_hvl/verilog/sky130_fd_sc_hvl.v"
*/

//`include "/home/merlproj/backend-tools/pdks/sky130A/libs.ref/sky130_fd_io/verilog/sky130_fd_io.v"
//`include "/home/merlproj/backend-tools/pdks/sky130A/libs.ref/sky130_fd_io/verilog/sky130_ef_io.v"
//`include "/home/merlproj/backend-tools/pdks/sky130A/libs.ref/sky130_fd_io/verilog/sky130_ef_io__gpiov2_pad_wrapped.v"

//`include "/home/merlproj/backend-tools/pdks/sky130A/libs.ref/sky130_fd_sc_hd/verilog/primitives.v"
//`include "/home/merlproj/backend-tools/pdks/sky130A/libs.ref/sky130_fd_sc_hd/verilog/sky130_fd_sc_hd.v"
//`include "/home/merlproj/backend-tools/pdks/sky130A/libs.ref/sky130_fd_sc_hvl/verilog/primitives.v"
//`include "/home/merlproj/backend-tools/pdks/sky130A/libs.ref/sky130_fd_sc_hvl/verilog/sky130_fd_sc_hvl.v"

/*`ifdef GL
        `include "gl/mgmt_core.v"
`else
        `include "mgmt_soc.v"
        `include "housekeeping_spi.v"
        `include "caravel_clocking.v"
        `include "mgmt_core.v"
`endif
*/

module main();

  // Testbench uses a 25 MHz clock (same as Go Board)
  // Want to interface to 115200 baud UART
  // 25000000 / 115200 = 217 Clocks Per Bit.
  parameter c_CLOCK_PERIOD_NS = 40;
  parameter c_BIT_PERIOD      = 8600;
  
  wire [15:0] c_CLKS_PER_BIT = 16'd217;
  reg r_Clock = 0;
  reg r_Reset = 0;
  reg r_RX_Serial = 1;
  wire [7:0] w_RX_Byte;
  wire [127:0] la_oen;

  assign la_oen = 128'hFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF;
  wire [37:0]io_oeb;
  wire [37:0]io_out;
  wire [127:0]la_data_out;

  Ibtida_top_dffram_cv ibtida
    (
     .wb_clk_i(r_Clock),
     .wb_rst_i(r_Reset),
     .io_in({32'h00000000,r_RX_Serial,5'b00000}),
     .la_data_in({80'h00000000000000000000,c_CLKS_PER_BIT,32'h00000000}),
     .la_data_out(la_data_out),
     .la_oen(la_oen),
     .io_out(io_out),
     .io_oeb(io_oeb),
     .VPWR(1'b1),
     .VGND(1'b0)
  );

  // Takes in input byte and serializes it 
  task UART_WRITE_BYTE;
    input [7:0] i_Data;
    integer     ii;
    begin
      
      // Send Start Bit
      r_RX_Serial <= 1'b0;
      #(c_BIT_PERIOD);
      #1000;
      
      // Send Data Byte
      for (ii=0; ii<8; ii=ii+1)
        begin
          r_RX_Serial <= i_Data[ii];
          #(c_BIT_PERIOD);
        end
      
      // Send Stop Bit
      r_RX_Serial <= 1'b1;
      #(c_BIT_PERIOD);
     end
  endtask // UART_WRITE_BYTE

  
  always
    #(c_CLOCK_PERIOD_NS/2) r_Clock <= !r_Clock;

  
  // Main Testing:
  initial
    begin
      @(posedge r_Clock);
      r_Reset=1;
      #500
      r_Reset=0;
      // Send a command to the UART (exercise Rx)
      @(posedge r_Clock);
      UART_WRITE_BYTE(8'h13);
      @(posedge r_Clock);
      UART_WRITE_BYTE(8'h00);
      @(posedge r_Clock);
      UART_WRITE_BYTE(8'h00);
      @(posedge r_Clock);
      UART_WRITE_BYTE(8'h00);
      @(posedge r_Clock);
      UART_WRITE_BYTE(8'hff);
      @(posedge r_Clock);
      UART_WRITE_BYTE(8'h0f);
      @(posedge r_Clock);
      UART_WRITE_BYTE(8'h00);
      @(posedge r_Clock);
      UART_WRITE_BYTE(8'h00);      
      // Check that the correct command was received
      
      $display("Executed");
      
    $finish();
    end
  
  initial 
  begin
    // Required to dump signals to EPWave
    $dumpfile("dump.vcd");
    $dumpvars(0);
  end
  
endmodule

