package de.platon42.intellij.plugins.m68k

import org.junit.jupiter.api.DisplayNameGeneration
import org.junit.jupiter.api.DisplayNameGenerator
import java.lang.reflect.Method

@DisplayNameGeneration(AbstractM68kTest.CutOffFixtureDisplayNameGenerator::class)
abstract class AbstractM68kTest {

    class CutOffFixtureDisplayNameGenerator : DisplayNameGenerator.ReplaceUnderscores() {
        override fun generateDisplayNameForMethod(testClass: Class<*>?, testMethod: Method?): String {
            val nameForMethod = super.generateDisplayNameForMethod(testClass, testMethod)
            return nameForMethod.substringBefore("$")
        }
    }
}