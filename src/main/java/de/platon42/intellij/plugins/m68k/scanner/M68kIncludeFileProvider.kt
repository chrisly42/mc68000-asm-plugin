package de.platon42.intellij.plugins.m68k.scanner

import com.intellij.openapi.fileTypes.FileType
import com.intellij.openapi.fileTypes.FileTypeRegistry
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.impl.include.FileIncludeInfo
import com.intellij.psi.impl.include.FileIncludeProvider
import com.intellij.psi.impl.source.tree.LightTreeUtil
import com.intellij.util.Consumer
import com.intellij.util.PathUtilRt
import com.intellij.util.indexing.FileContent
import com.intellij.util.indexing.PsiDependentFileContent
import de.platon42.intellij.plugins.m68k.M68kFileType.Companion.INSTANCE
import de.platon42.intellij.plugins.m68k.lexer.LexerUtil
import de.platon42.intellij.plugins.m68k.psi.M68kTypes

class M68kIncludeFileProvider : FileIncludeProvider() {

    override fun getId(): String {
        return "mc68000_include"
    }

    override fun acceptFile(file: VirtualFile): Boolean {
        return FileTypeRegistry.getInstance().isFileOfType(file, INSTANCE)
    }

    override fun registerFileTypesUsedForIndexing(fileTypeSink: Consumer<in FileType?>) {
        fileTypeSink.consume(INSTANCE)
    }

    override fun getIncludeInfos(content: FileContent): Array<FileIncludeInfo> {
        if (content.contentAsText.indexOfAny(listOf("include", "incbin"), ignoreCase = true) < 0) return emptyArray()

        val tree = (content as PsiDependentFileContent).lighterAST
        val statements = LightTreeUtil.getChildrenOfType(tree, tree.root, M68kTypes.STATEMENT).asSequence()
            .mapNotNull { LightTreeUtil.firstChildOfType(tree, it, M68kTypes.PREPROCESSOR_DIRECTIVE) }
            .mapNotNull {
                val keywordNode = LightTreeUtil.firstChildOfType(tree, it, M68kTypes.PREPROCESSOR_KEYWORD) ?: return@mapNotNull null
                val keyword = LightTreeUtil.toFilteredString(tree, keywordNode, null)
                if (keyword.equals("include", true) || keyword.equals("incbin", true)) {
                    keyword to it
                } else {
                    null
                }
            }
            .mapNotNull {
                val pathNode = LightTreeUtil.firstChildOfType(tree, it.second, M68kTypes.LITERAL_EXPR) ?: return@mapNotNull null
                val path = LexerUtil.unquoteString(LightTreeUtil.toFilteredString(tree, pathNode, null)).replace("\\", "/")
                if (it.first.equals("include", true)) {
                    FileIncludeInfo(path)
                } else {
                    FileIncludeInfo(PathUtilRt.getFileName(path), path, 0, true)
                }
            }
            .toList()

        return statements.toTypedArray()
    }
}