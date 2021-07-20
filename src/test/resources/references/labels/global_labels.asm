        bsr     init
        bsr     main<caret>
        bsr     exit
        rts

init    moveq.l #-1,d0
        rts

main    moveq.l #0,d0
        rts

exit    illegal
        rts