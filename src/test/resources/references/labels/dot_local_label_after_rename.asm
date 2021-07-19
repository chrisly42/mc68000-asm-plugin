
main
        tst.w   d1
        beq.s   .skip
        moveq.l #42,d0
.narf   move.l  d0,(a0)+
        dbra    d0,.narf
.skip   subq.w  #1,d1
        beq.s   .narf
        rts
