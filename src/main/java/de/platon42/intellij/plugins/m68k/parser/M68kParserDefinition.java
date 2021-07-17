package de.platon42.intellij.plugins.m68k.parser;

import com.intellij.lang.ASTNode;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import de.platon42.intellij.plugins.m68k.M68kFileElementType;
import de.platon42.intellij.plugins.m68k.lexer.M68kLexer;
import de.platon42.intellij.plugins.m68k.lexer.M68kLexerPrefs;
import de.platon42.intellij.plugins.m68k.psi.M68kFile;
import de.platon42.intellij.plugins.m68k.psi.M68kTypes;
import org.jetbrains.annotations.NotNull;

public class M68kParserDefinition implements ParserDefinition {
    public static final TokenSet STRING_LITERALS = TokenSet.create(M68kTypes.STRINGLIT);
    public static final TokenSet COMMENTS = TokenSet.create(M68kTypes.COMMENT);

    private M68kLexerPrefs lexerPrefs = new M68kLexerPrefs(); // TODO make this configurable

    public M68kParserDefinition() {
    }

    public M68kLexerPrefs getLexerPrefs() {
        return lexerPrefs;
    }

    @Override
    public @NotNull Lexer createLexer(Project project) {
        // TODO take prefs from project somehow
        return new M68kLexer(lexerPrefs);
    }

    @Override
    public @NotNull PsiParser createParser(Project project) {
        return new M68kParser();
    }

    @Override
    public @NotNull IFileElementType getFileNodeType() {
        return M68kFileElementType.INSTANCE;
    }

    @Override
    public @NotNull TokenSet getCommentTokens() {
        return COMMENTS;
    }

    @Override
    public @NotNull TokenSet getStringLiteralElements() {
        return STRING_LITERALS;
    }

    @Override
    public @NotNull PsiElement createElement(ASTNode node) {
        return M68kTypes.Factory.createElement(node);
    }

    @Override
    public @NotNull PsiFile createFile(@NotNull FileViewProvider viewProvider) {
        return new M68kFile(viewProvider);
    }
}
