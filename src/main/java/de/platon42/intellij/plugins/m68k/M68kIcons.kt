package de.platon42.intellij.plugins.m68k

import com.intellij.icons.AllIcons
import com.intellij.openapi.util.IconLoader

object M68kIcons {
    val FILE = IconLoader.getIcon("/icons/FileType_m68k.svg", javaClass)
    val LOCAL_LABEL = AllIcons.Nodes.AbstractMethod
    val GLOBAL_LABEL = AllIcons.Nodes.Method
    val SYMBOL_DEF = AllIcons.Nodes.ClassInitializer
    val MACRO_DEF = AllIcons.Nodes.MultipleTypeDefinitions
    val REGISTER = AllIcons.Nodes.DataTables // AllIcons.Nodes.Record only available starting from 2020.1
    val MNEMONIC = AllIcons.Nodes.C_protected
}