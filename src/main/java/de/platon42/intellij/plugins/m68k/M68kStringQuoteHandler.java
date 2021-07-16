package de.platon42.intellij.plugins.m68k;

import com.intellij.codeInsight.editorActions.SimpleTokenSetQuoteHandler;
import de.platon42.intellij.plugins.m68k.psi.M68kTypes;

public class M68kStringQuoteHandler extends SimpleTokenSetQuoteHandler {
    public M68kStringQuoteHandler() {
        super(M68kTypes.STRINGLIT);
    }
}
