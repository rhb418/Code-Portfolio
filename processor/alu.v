module alu(data_operandA, data_operandB, ctrl_ALUopcode, ctrl_shiftamt, data_result, isNotEqual, isLessThan, overflow);
        
    input [31:0] data_operandA, data_operandB;
    input [4:0] ctrl_ALUopcode, ctrl_shiftamt;

    output [31:0] data_result;
    output isNotEqual, isLessThan, overflow;

    wire [31:0] sub, add, or32, and32, lls32, ars32, b1, b2, notb;
    wire oveflowadd, overflowsub, u1, u2; 
    not32bit n1(notb, data_operandB); 

    // add your code here:
    adder32 adderS(sub,overflowsub, isLessThan, isNotEqual, data_operandA, notb,1'b1);
    adder32 adderA(add,overflowadd, u1, u2, data_operandA, data_operandB,1'b0);
    and32bit and32g(and32, data_operandA, data_operandB); 
    or32bit or32g(or32, data_operandA, data_operandB); 
    llsbarrel llsg(lls32, data_operandA,ctrl_shiftamt);
    arsbarrel arsg(ars32,data_operandA,ctrl_shiftamt); 
    

    mux_8 data(data_result, ctrl_ALUopcode[2:0], add,sub,and32,or32, lls32, ars32, b1, b2); 
    mux_2 ovfc(overflow,ctrl_ALUopcode[0], overflowadd, overflowsub); 

endmodule