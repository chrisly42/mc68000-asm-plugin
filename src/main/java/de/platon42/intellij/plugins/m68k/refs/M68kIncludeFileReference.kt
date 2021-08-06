package de.platon42.intellij.plugins.m68k.refs

import com.intellij.psi.*
import com.intellij.psi.impl.source.resolve.ResolveCache
import com.intellij.psi.search.FileTypeIndex
import com.intellij.psi.search.GlobalSearchScope
import de.platon42.intellij.plugins.m68k.M68kFileType
import de.platon42.intellij.plugins.m68k.lexer.LexerUtil
import de.platon42.intellij.plugins.m68k.psi.M68kPreprocessorDirective

class M68kIncludeFileReference(element: M68kPreprocessorDirective) : PsiPolyVariantReferenceBase<M68kPreprocessorDirective>(element, true) {

    companion object {
        val INSTANCE = Resolver()
    }

    class Resolver : ResolveCache.PolyVariantResolver<M68kIncludeFileReference> {
        override fun resolve(ref: M68kIncludeFileReference, incompleteCode: Boolean): Array<ResolveResult> {
            val project = ref.element.project
            val allFiles = FileTypeIndex.getFiles(M68kFileType.INSTANCE, GlobalSearchScope.allScope(project))

            val pathName = LexerUtil.unquoteString(ref.element.exprList.first().text).replace('\\', '/')
            val fileMatched = ((allFiles.firstOrNull { it.path.equals(pathName, ignoreCase = true) }
                ?: allFiles.firstOrNull { it.path.endsWith(pathName.removePrefix("../"), ignoreCase = true) })
                ?: allFiles.firstOrNull { it.path.endsWith(pathName.substringAfterLast('/'), ignoreCase = true) }) ?: return emptyArray()
            return PsiElementResolveResult.createResults(PsiManager.getInstance(project).findFile(fileMatched))
        }
    }

    override fun multiResolve(incompleteCode: Boolean): Array<ResolveResult> =
        ResolveCache.getInstance(element.project)
            .resolveWithCaching(this, INSTANCE, false, incompleteCode)

    override fun resolve(): PsiElement? = multiResolve(false).singleOrNull()?.element

    override fun getVariants(): Array<Any> = emptyArray()

//    override fun getRangeInElement(): TextRange {
//        val unquoted = LexerUtil.unquoteString(element.text)
//
//        val path = element.exprList.first()
//        return TextRange.from(
//            path.textRange.startOffset + path.text.indexOf(unquoted), unquoted.length
//        )
//    }
}