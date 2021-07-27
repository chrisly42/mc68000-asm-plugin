PUTMSG  MACRO
        printf
        ENDM

UAEExitWarp  MACRO
        ENDM

main    PUTM<caret>SG 10,<"Enter MAIN!">,d0
        rts

exit    PUTMSG 10,<"Exit!">,d0
        illegal
        rts