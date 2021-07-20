package de.platon42.intellij.plugins.m68k.parser

import com.intellij.lang.ASTNode
import com.intellij.lang.ParserDefinition
import com.intellij.lexer.Lexer
import com.intellij.openapi.project.Project
import com.intellij.psi.FileViewProvider
import com.intellij.psi.tree.TokenSet
import de.platon42.intellij.plugins.m68k.M68kFileElementType
import de.platon42.intellij.plugins.m68k.lexer.M68kLexer
import de.platon42.intellij.plugins.m68k.lexer.M68kLexerPrefs
import de.platon42.intellij.plugins.m68k.psi.M68kFile
import de.platon42.intellij.plugins.m68k.psi.M68kTypes

class M68kParserDefinition : ParserDefinition {

    val lexerPrefs = M68kLexerPrefs() // TODO make this configurable

    override fun createLexer(project: Project): Lexer {
        // TODO take prefs from project somehow
        return M68kLexer(lexerPrefs)
    }

    override fun createParser(project: Project) = M68kParser()

    override fun getFileNodeType() = M68kFileElementType.INSTANCE

    override fun getCommentTokens() = COMMENTS

    override fun getStringLiteralElements() = STRING_LITERALS

    override fun createElement(node: ASTNode) = M68kTypes.Factory.createElement(node)

    override fun createFile(viewProvider: FileViewProvider) = M68kFile(viewProvider)

    companion object {
        val STRING_LITERALS = TokenSet.create(M68kTypes.STRINGLIT)
        val COMMENTS = TokenSet.create(M68kTypes.COMMENT)
    }
}