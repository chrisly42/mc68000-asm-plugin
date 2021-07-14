package de.platon42.intellij.jupiter;

import com.intellij.lang.ParserDefinition;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface MyParser {
    Class<? extends ParserDefinition> value();

    String extension();
}
