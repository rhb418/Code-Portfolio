nop 	# simple bne test case
nop 
nop 
nop
nop
nop
addi    $r1, $r0, 4     # $r1 = 4
addi    $r2, $r0, 5     # $r2 = 5
nop
nop
sub     $r3, $r0, $r1   # $r3 = -4
sub     $r4, $r0, $r2   # $r4 = -5
nop
nop
nop 	
bne 	$r1, $r2, b1			
addi 	$r20, $r20, 1	
addi 	$r20, $r20, 1	
addi 	$r20, $r20, 1	
b1: 
addi 	$r10, $r10, 1	
nop
nop
nop
bne     $r1, $r1, b2
addi    $r13, $r13, 1
nop		
b2:	
nop			
