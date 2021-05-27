/**
 * READ THIS DESCRIPTION!
 *
 * This is your processor module that will contain the bulk of your code submission. You are to implement
 * a 5-stage pipelined processor in this module, accounting for hazards and implementing bypasses as
 * necessary.
 *
 * Ultimately, your processor will be tested by a master skeleton, so the
 * testbench can see which controls signal you active when. Therefore, there needs to be a way to
 * "inject" imem, dmem, and regfile interfaces from some external controller module. The skeleton
 * file, Wrapper.v, acts as a small wrapper around your processor for this purpose. Refer to Wrapper.v
 * for more details.
 *
 * As a result, this module will NOT contain the RegFile nor the memory modules. Study the inputs 
 * very carefully - the RegFile-related I/Os are merely signals to be sent to the RegFile instantiated
 * in your Wrapper module. This is the same for your memory elements. 
 *
 *
 */
module processor(
    // Control signals
    clock,                          // I: The master clock
    reset,                          // I: A reset signal

    // Imem
    address_imem,                   // O: The address of the data to get from imem
    q_imem,                         // I: The data from imem

    // Dmem
    address_dmem,                   // O: The address of the data to get or put from/to dmem
    data,                           // O: The data to write to dmem
    wren,                           // O: Write enable for dmem
    q_dmem,                         // I: The data from dmem

    // Regfile
    ctrl_writeEnable,               // O: Write enable for RegFile
    ctrl_writeReg,                  // O: Register to write to in RegFile
    ctrl_readRegA,                  // O: Register to read from port A of RegFile
    ctrl_readRegB,                  // O: Register to read from port B of RegFile
    data_writeReg,                  // O: Data to write to for RegFile
    data_readRegA,                  // I: Data from port A of RegFile
    data_readRegB                   // I: Data from port B of RegFile
	 
	);

	// Control signals
	input clock, reset;
	
	// Imem
    output [31:0] address_imem;
	input [31:0] q_imem;

	// Dmem
	output [31:0] address_dmem, data;
	output wren;
	input [31:0] q_dmem;

	// Regfile
	output ctrl_writeEnable;
	output [4:0] ctrl_writeReg, ctrl_readRegA, ctrl_readRegB;
	output [31:0] data_writeReg;
	input [31:0] data_readRegA, data_readRegB;

	
    /* YOUR CODE STARTS HERE */
    wire notClock;
    not n1(notClock,clock);

    wire [31:0] pcOut, pcPlusT, pcPlus1, newPC, newerPC, outLFSR, outEX;
    wire [31:0] pcIn, inFDIR; 
    wire dc1, dc2, dc3, ne, lt, dc6, dc7, dc8, stall, multStall, loadStall, branchTaken;
    wire [31:0] outPCDX, outIRDX, outADX, outBDX, SXout, aluInA, aluInB_before_mux, aluInB, alu_out, outIRXM, outOXM, outBXM, outIRMW, outOMW, outDMW, inIRXM, inOXM, inDXIR, dataAfterM1, dataAfterM2, multResult, PWResultOut, PWINSOut; 
    wire [31:0] outMDA, outMDB, outMDIR, outPCXM, outPCMW; 
    wire [1:0] aSelect, bSelect; 
    wire [4:0] aluOp, sham, DXOPCODE; 
    wire SXmux, ovf, memSelect, ctrl_MULT, ctrl_DIV, data_exception, data_resultRDY, PWReadyOut, commitMultDiv, jump, jr; 

    assign address_imem = pcOut;
    assign stall =  (loadStall || multStall);  

    assign newPC = jump ? SXout : pcPlusT;
    assign newerPC = jr ? aluInA : newPC; 
    assign pcIn = branchTaken ? newerPC : pcPlus1; 
    reg32 PC(pcIn, pcOut, notClock,!stall , reset);
    adder32 pcAdder(pcPlus1,dc1, dc2, dc3, pcOut, 32'b1, 1'b0); 

    wire [31:0] outPCFD, outIRFD;
    assign inFDIR = branchTaken ? 0 : q_imem;
    FD pFD(inFDIR, pcIn, notClock, reset, !stall, outPCFD, outIRFD);


    assign inDXIR = (stall || branchTaken) ? 0 : outIRFD; 

    //DX TO XM
    DX pDX(inDXIR, outPCFD, data_readRegA, data_readRegB, notClock, reset, 1'b1, outIRDX, outPCDX, outADX, outBDX);    
    signExtend se(outIRDX, SXout);

    adder32 sxPC(pcPlusT,dc6, dc7, dc8, outPCDX, SXout, 1'b0); 

    mux_4 aluInAMux(aluInA, aSelect, outADX, outOXM, data_writeReg, 32'b0);
    mux_4 aluInBMux(aluInB_before_mux, bSelect, outBDX, outOXM, data_writeReg, 32'b0);

    assign aluInB = SXmux ? SXout : aluInB_before_mux;

    alu alu1(aluInA, aluInB, aluOp, sham, alu_out, ne, lt, ovf);
    exceptionHandler ex(outIRDX, alu_out, ovf, inIRXM, outEX);
    LFSR lfsr1(clock, outLFSR);

    assign DXOPCODE = outIRDX[31:27];
    assign inOXM =  (DXOPCODE == 9) ? outLFSR : outEX; 
    assign jump = (DXOPCODE == 1) || (DXOPCODE == 3) || (DXOPCODE == 4) || ((DXOPCODE==22) && ne); 
    assign branchTaken = ((DXOPCODE == 2) && ne) || ((DXOPCODE == 6) && lt) || (DXOPCODE == 1) || (DXOPCODE == 3) || (DXOPCODE == 4) || ((DXOPCODE==22) && ne); 
    assign jr = (DXOPCODE == 4); 

    multData multdata1(aluInA, aluInB_before_mux, outIRDX, notClock, reset, outMDA, outMDB, outMDIR, ctrl_MULT, ctrl_DIV); 

    multdiv multD(outMDA, outMDB, ctrl_MULT, ctrl_DIV, clock, multResult, data_exception, data_resultRDY);
    PW pw1(multResult, outMDIR, data_resultRDY, notClock, 1'b1, reset, PWReadyOut, PWResultOut, PWINSOut);


    XM pXM(inIRXM, outPCDX, inOXM, aluInB_before_mux, notClock, reset, 1'b1, outIRXM, outPCXM, outOXM, outBXM);
    assign address_dmem = outOXM;
    assign data = memSelect ? data_writeReg : outBXM; 
    assign wren = (outIRXM[31:27] == 7) ? 1'b1 : 1'b0; 

 
    MW pMW(outIRXM,outPCXM, outOXM, q_dmem, notClock, reset, 1'b1, outIRMW,outPCMW, outOMW, outDMW);

    assign dataAfterM1 = (outIRMW[31:27] == 8) ? outDMW : outOMW; 
    assign dataAfterM2 = (commitMultDiv) ? PWResultOut : dataAfterM1; 
    assign data_writeReg = (outIRMW[31:27] == 3) ? outPCMW : dataAfterM2; 
    
    
    multControl mc1(outIRDX, PWReadyOut, clock, multStall, commitMultDiv);

    regFileAndSXControl rc1(outIRFD, outIRDX, outIRMW, PWINSOut, commitMultDiv, ctrl_readRegA, ctrl_readRegB, ctrl_writeReg, ctrl_writeEnable);
	
    bypassControl bc(outIRDX, outIRXM, outIRMW, aSelect, bSelect,memSelect);

    aluControl ac(outIRDX, aluOp, sham, SXmux);

    stallControl sc(outIRFD, outIRDX, loadStall); 

	/* END CODE */

endmodule
