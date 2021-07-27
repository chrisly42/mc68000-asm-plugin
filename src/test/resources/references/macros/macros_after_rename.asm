PRINTF  MACRO
        printf
        ENDM

UAEExitWarp  MACRO
        ENDM

main    PRINTF 10,<"Enter MAIN!">,d0
        rts

exit    PRINTF 10,<"Exit!">,d0
        illegal
        rts