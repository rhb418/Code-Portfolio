module regFileAndSXControl(FDIR, DXIR, MWIR, PWIR, multOrDivReady, readA, readB, writeD, wE);

input [31:0] FDIR, DXIR, MWIR, PWIR; 
input multOrDivReady; 
output [4:0] readA, readB, writeD; 
output wE; 

wire [4:0] MWOP, FDOP, PWOP, FDRD, FDRS, FDRT, readBAluOrLoadStore, aluOpWriteD, jrWD, readBBranch, readABranch;
assign MWOP = MWIR[31:27];
assign FDOP = FDIR[31:27];
assign PWOP = PWIR[31:27];

assign FDRD = FDIR[26:22];
assign FDRS = FDIR[21:17];
assign FDRT = FDIR[16:12];

wire isBranch, isLoadOrStore, isAluOP, isBranchOrJr, isEx; 

assign isBranchOrJr = (FDOP == 2) || (FDOP == 6) || (FDOP ==4); 
assign isLoadOrStore = (FDOP == 7) || (FDOP ==8); 
assign isAluOP = (FDOP == 0); 
assign isEx = (FDOP == 21) || (FDOP ==22);



assign readABranch = isBranchOrJr ? FDRD : FDRS;
assign readA = (isEx) ? 0 : readABranch;
assign readBAluOrLoadStore = isLoadOrStore ? FDRD : FDRT;
assign readBBranch = isBranchOrJr ? FDRS : readBAluOrLoadStore; 
assign readB = (isEx) ? 30 : readBBranch; 

assign wE = (MWOP == 0)  || ((PWOP == 0) && multOrDivReady) || (MWOP == 5) || (MWOP == 8) || (MWOP ==3) || (MWOP == 21) || (MWOP == 9); 
assign aluOpWriteD = multOrDivReady ? PWIR[26:22] : MWIR[26:22];
assign jrWD = (MWOP ==3) ? 31 : aluOpWriteD; 
assign writeD = (MWOP == 21) ? 30 : jrWD; 
endmodule