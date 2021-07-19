
main
        tst.w   d1
        beq.s   .skip
        moveq.l #42,d0
        IFEQ    DATAWIDTH=4
loop$   move.l  d0,(a0)+
        ELSE
loop$   move.w  d0,(a0)+
        ENDC
        dbra    d0,l<caret>oop$
.skip   subq.w  #1,d1
        beq.s   loop$
        rts
