package de.platon42.intellij.plugins.m68k;

import com.intellij.psi.PsiFile;
import com.intellij.psi.StubBuilder;
import com.intellij.psi.stubs.DefaultStubBuilder;
import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.tree.IStubFileElementType;
import de.platon42.intellij.plugins.m68k.psi.M68kFile;
import de.platon42.intellij.plugins.m68k.stubs.M68kFileStub;
import org.jetbrains.annotations.NotNull;

public class M68kFileElementType extends IStubFileElementType<M68kFileStub> {
    public static final IStubFileElementType<M68kFileStub> INSTANCE = new M68kFileElementType();
    public static final int VERSION = 1;

    private M68kFileElementType() {
        super("MC68000_FILE", MC68000Language.Companion.getINSTANCE());
    }

    @Override
    public int getStubVersion() {
        return VERSION;
    }

    @Override
    public StubBuilder getBuilder() {
        return new DefaultStubBuilder() {

            @Override
            protected @NotNull StubElement createStubForFile(@NotNull PsiFile file) {
                if (file instanceof M68kFile) return new M68kFileStub((M68kFile) file);
                return super.createStubForFile(file);
            }
        };
    }
}
