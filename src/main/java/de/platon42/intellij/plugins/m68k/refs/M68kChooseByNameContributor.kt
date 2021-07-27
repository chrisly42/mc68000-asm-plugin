package de.platon42.intellij.plugins.m68k.refs

import com.intellij.navigation.ChooseByNameContributorEx
import com.intellij.navigation.NavigationItem
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.stubs.StubIndex
import com.intellij.util.Processor
import com.intellij.util.indexing.FindSymbolParameters
import com.intellij.util.indexing.IdFilter
import de.platon42.intellij.plugins.m68k.psi.M68kGlobalLabel
import de.platon42.intellij.plugins.m68k.psi.M68kSymbolDefinition
import de.platon42.intellij.plugins.m68k.stubs.M68kGlobalLabelStubIndex
import de.platon42.intellij.plugins.m68k.stubs.M68kSymbolDefinitionStubIndex

class M68kChooseByNameContributor : ChooseByNameContributorEx {

//    override fun processNames(processor: Processor<in String>, parameters: FindSymbolParameters) {
//        processNames(processor, parameters.searchScope, parameters.idFilter)
//    }

//    override fun getItemsByName(name: String, pattern: String, project: Project, includeNonProjectItems: Boolean): Array<NavigationItem> {
//        val result: MutableList<NavigationItem> = ArrayList()
//        processElementsWithName(name, result::add, FindSymbolParameters.wrap(pattern, project, includeNonProjectItems))
//        return result.toTypedArray()
//    }

    override fun processNames(processor: Processor<in String>, scope: GlobalSearchScope, filter: IdFilter?) {
        StubIndex.getInstance().processAllKeys(M68kGlobalLabelStubIndex.KEY, processor, scope, filter)
        StubIndex.getInstance().processAllKeys(M68kSymbolDefinitionStubIndex.KEY, processor, scope, filter)
    }

    override fun processElementsWithName(name: String, processor: Processor<in NavigationItem>, parameters: FindSymbolParameters) {
        StubIndex.getInstance()
            .processElements(M68kGlobalLabelStubIndex.KEY, name, parameters.project, parameters.searchScope, M68kGlobalLabel::class.java, processor)
        StubIndex.getInstance()
            .processElements(M68kSymbolDefinitionStubIndex.KEY, name, parameters.project, parameters.searchScope, M68kSymbolDefinition::class.java, processor)
    }
}