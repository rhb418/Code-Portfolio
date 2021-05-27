module signExtend(DXIR, out);

input [31:0] DXIR;
output [31:0] out; 

wire [31:0] imm, target;
wire [4:0] DXIROP; 

assign DXIROP = DXIR[31:27]; 

assign target[26:0] = DXIR[26:0]; 
assign target[27] = DXIR[26];
assign target[28] = DXIR[26];
assign target[28] = DXIR[26];
assign target[29] = DXIR[26];
assign target[30] = DXIR[26];
assign target[31] = DXIR[26];

assign imm[16:0] = DXIR[16:0]; 
assign imm[17] = DXIR[16];
assign imm[18] = DXIR[16];
assign imm[19] = DXIR[16];
assign imm[20] = DXIR[16];
assign imm[21] = DXIR[16];
assign imm[22] = DXIR[16];
assign imm[23] = DXIR[16];
assign imm[24] = DXIR[16];
assign imm[25] = DXIR[16];
assign imm[26] = DXIR[16];
assign imm[27] = DXIR[16];
assign imm[28] = DXIR[16];
assign imm[29] = DXIR[16];
assign imm[30] = DXIR[16];
assign imm[31] = DXIR[16];

wire targetOps;
assign targetOps = (DXIROP == 1) || (DXIROP == 21) || (DXIROP ==22);

assign out = targetOps ? target : imm; 

endmodule