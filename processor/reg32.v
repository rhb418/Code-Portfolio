module reg32(in, out, clk, en, reset);
    input [31:0] in; 
    input clk, en, reset; 
    output [31:0] out; 

    dffe dff0(out[0], in[0], clk, en, reset); 
    dffe dff1(out[1], in[1], clk, en, reset); 
    dffe dff2(out[2], in[2], clk, en, reset); 
    dffe dff3(out[3], in[3], clk, en, reset); 
    dffe dff4(out[4], in[4], clk, en, reset); 
    dffe dff5(out[5], in[5], clk, en, reset); 
    dffe dff6(out[6], in[6], clk, en, reset); 
    dffe dff7(out[7], in[7], clk, en, reset); 
    dffe dff8(out[8], in[8], clk, en, reset); 
    dffe dff9(out[9], in[9], clk, en, reset); 
    dffe dff10(out[10], in[10], clk, en, reset); 
    dffe dff11(out[11], in[11], clk, en, reset); 
    dffe dff12(out[12], in[12], clk, en, reset); 
    dffe dff13(out[13], in[13], clk, en, reset); 
    dffe dff14(out[14], in[14], clk, en, reset); 
    dffe dff15(out[15], in[15], clk, en, reset); 
    dffe dff16(out[16], in[16], clk, en, reset); 
    dffe dff17(out[17], in[17], clk, en, reset); 
    dffe dff18(out[18], in[18], clk, en, reset); 
    dffe dff19(out[19], in[19], clk, en, reset); 
    dffe dff20(out[20], in[20], clk, en, reset); 
    dffe dff21(out[21], in[21], clk, en, reset); 
    dffe dff22(out[22], in[22], clk, en, reset); 
    dffe dff23(out[23], in[23], clk, en, reset); 
    dffe dff24(out[24], in[24], clk, en, reset); 
    dffe dff25(out[25], in[25], clk, en, reset); 
    dffe dff26(out[26], in[26], clk, en, reset); 
    dffe dff27(out[27], in[27], clk, en, reset); 
    dffe dff28(out[28], in[28], clk, en, reset); 
    dffe dff29(out[29], in[29], clk, en, reset); 
    dffe dff30(out[30], in[30], clk, en, reset); 
    dffe dff31(out[31], in[31], clk, en, reset); 


endmodule