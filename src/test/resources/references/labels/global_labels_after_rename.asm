        bsr     init
        bsr     intro_main
        bsr     exit
        rts

init    moveq.l #-1,d0
        rts

intro_main    moveq.l #0,d0
        rts

exit    illegal
        rts