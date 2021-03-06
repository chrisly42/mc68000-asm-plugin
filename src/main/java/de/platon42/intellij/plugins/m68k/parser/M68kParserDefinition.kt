package de.platon42.intellij.plugins.m68k.parser

import com.intellij.lang.ASTNode
import com.intellij.lang.ParserDefinition
import com.intellij.lexer.Lexer
import com.intellij.openapi.project.Project
import com.intellij.psi.FileViewProvider
import com.intellij.psi.tree.TokenSet
import de.platon42.intellij.plugins.m68k.lexer.M68kLexer
import de.platon42.intellij.plugins.m68k.lexer.M68kLexerPrefs
import de.platon42.intellij.plugins.m68k.psi.M68kFile
import de.platon42.intellij.plugins.m68k.psi.M68kTypes
import de.platon42.intellij.plugins.m68k.settings.M68kProjectSettings
import de.platon42.intellij.plugins.m68k.stubs.M68kElementTypes

class M68kParserDefinition : ParserDefinition {

    override fun createLexer(project: Project): Lexer {
        val settings = project.getService(M68kProjectSettings::class.java)
        return M68kLexer(settings?.settings ?: M68kLexerPrefs())
    }

    override fun createParser(project: Project) = M68kParser()

    override fun getFileNodeType() = M68kElementTypes.FILE

    override fun getCommentTokens() = COMMENTS

    override fun getStringLiteralElements() = STRING_LITERALS

    override fun createElement(node: ASTNode) = M68kTypes.Factory.createElement(node)

    override fun createFile(viewProvider: FileViewProvider) = M68kFile(viewProvider)

    companion object {
        val STRING_LITERALS = TokenSet.create(M68kTypes.STRINGLIT)
        val COMMENTS = TokenSet.create(M68kTypes.COMMENT)
    }
}