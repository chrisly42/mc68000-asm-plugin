package de.platon42.intellij.plugins.m68k.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.parser.GeneratedParserUtilBase;

import static de.platon42.intellij.plugins.m68k.psi.M68kTypes.SYMBOL;

public class M68kParserUtilBase extends GeneratedParserUtilBase {

    public static boolean parseMacroCallOrAsmInstruction(PsiBuilder b, int level) {
        boolean r = false;
        if (!nextTokenIs(b, SYMBOL)) return M68kParser.MacroCall(b, level);

        PsiBuilder.Marker m = enter_section_(b);

        //M68kMnemonics.INSTANCE.getMnemonics().stream().findFirst() nextTokenIs(, )
        r = M68kParser.AsmInstruction(b, level);

        exit_section_(b, m, null, r);
        return r;
    }
}
