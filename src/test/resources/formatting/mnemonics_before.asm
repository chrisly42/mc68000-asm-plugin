 moveq.l #10,d1
 add.l   #10,d1
  subq.b  #2,d2
   bra.s  .foo
  nop
.foo move.l d2,d1
     rts
.verylonglabel: stop #$2000

.narf
    moveq.l #2,d0

globallabel: moveq.l #0,d0
