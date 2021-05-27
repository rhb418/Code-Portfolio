module stallControl(FDIR, DXIR, stall);

input [31:0] FDIR, DXIR; 
output stall; 

wire [4:0] DXOP, FDOP, FDRD, FDRS, FDRT, DXRD;

assign FDOP = FDIR[31:27];
assign DXOP = DXIR[31:27]; 
assign DXRD = DXIR[26:22]; 

assign FDRD = FDIR[26:22];
assign FDRS = FDIR[21:17];
assign FDRT = FDIR[16:12];

wire aluOpCase, storeLoadCase, branchCondCase, jrCase;

assign aluOpCase = (FDOP == 0) && ((FDRS == DXRD) || (FDRT == DXRD));
assign storeLoadCase = (FDOP == 5 || FDOP == 7 || FDOP == 8) && (FDRS == DXRD);
assign branchCondCase = (FDOP == 2 || FDOP == 6) && ((FDRS == DXRD) || (FDRD == DXRD));
assign jrCase = (FDOP == 4) && (FDRD == DXRD); 

assign stall = (DXOP == 8) && (aluOpCase || storeLoadCase || branchCondCase || jrCase);



endmodule