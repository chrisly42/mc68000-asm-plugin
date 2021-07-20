package de.platon42.intellij.plugins.m68k.refs

import com.intellij.navigation.ChooseByNameContributor
import com.intellij.navigation.NavigationItem
import com.intellij.openapi.project.Project
import de.platon42.intellij.plugins.m68k.psi.M68kLookupUtil.findAllGlobalLabels
import de.platon42.intellij.plugins.m68k.psi.M68kLookupUtil.findAllSymbolDefinitions

class M68kChooseByNameContributor : ChooseByNameContributor {
    override fun getNames(project: Project, includeNonProjectItems: Boolean): Array<String> {
        return listOf(findAllGlobalLabels(project), findAllSymbolDefinitions(project))
            .asSequence()
            .flatten()
            .mapNotNull { it.name }
            .toList()
            .toTypedArray()
    }

    override fun getItemsByName(name: String, pattern: String, project: Project, includeNonProjectItems: Boolean): Array<NavigationItem> {
        return listOf(findAllGlobalLabels(project), findAllSymbolDefinitions(project))
            .flatten()
            .toTypedArray()
    }
}