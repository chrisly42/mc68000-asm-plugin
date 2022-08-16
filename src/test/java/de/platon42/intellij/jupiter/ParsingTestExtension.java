package de.platon42.intellij.jupiter;


import com.intellij.lang.ParserDefinition;
import com.intellij.mock.MockProjectEx;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.util.Disposer;
import com.intellij.psi.PsiFile;
import com.intellij.testFramework.ParsingTestCase;
import com.intellij.testFramework.TestLoggerFactory;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.extension.*;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;
import org.junit.jupiter.api.extension.ExtensionContext.Store;
import org.junit.runner.Description;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class ParsingTestExtension implements ParameterResolver, AfterTestExecutionCallback, InvocationInterceptor {

    private static final Logger LOG = Logger.getLogger(ParsingTestExtension.class.getName());

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        Parameter parameter = parameterContext.getParameter();
        return parameter.isAnnotationPresent(MyTestCase.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        ParsingTestCaseWrapper testCase = getWrapper(extensionContext);
        Parameter parameter = parameterContext.getParameter();
        if (parameter.isAnnotationPresent(MyTestCase.class)) {
            return testCase;
        }
        return null;
    }

    private ParsingTestCaseWrapper getWrapper(ExtensionContext extensionContext) {
        Store store = getStore(extensionContext);
        return (ParsingTestCaseWrapper) store.getOrComputeIfAbsent("testCase",
                key -> {
                    MyParser myParser = extensionContext.getRequiredTestClass().getAnnotation(MyParser.class);
                    ParserDefinition parserDefinition;
                    try {
                        parserDefinition = myParser.value().getDeclaredConstructor().newInstance();
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
                        throw new RuntimeException("Could not instantiate ParserDefinition!", ex);
                    }
                    ParsingTestCaseWrapper wrapper = new ParsingTestCaseWrapper(extensionContext, myParser.extension(), parserDefinition);
                    try {
                        wrapper.setUp();
                    } catch (Exception e) {
                        LOG.severe("Exception during setUp(): " + e);
                        throw new IllegalStateException("Exception during setUp()", e);
                    }
                    return wrapper;
                });
    }

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        Store store = getStore(context);
        ParsingTestCaseWrapper testCase = (ParsingTestCaseWrapper) store.get("testCase");
        if (testCase != null) {
            testCase.tearDown();
        }
    }

    private static Store getStore(ExtensionContext context) {
        return context.getStore(Namespace.create(ParsingTestExtension.class, context.getRequiredTestMethod()));
    }

    @Override
    public void interceptTestMethod(Invocation<Void> invocation, ReflectiveInvocationContext<Method> invocationContext, ExtensionContext extensionContext) throws Throwable {
        Throwable[] throwables = new Throwable[1];
        Description testDescription = Description.createTestDescription(extensionContext.getRequiredTestClass(), getWrapper(extensionContext).getName());

        try {
            TestLoggerFactory.onTestStarted();
            invocation.proceed();
            TestLoggerFactory.onTestFinished(true, testDescription);
        } catch (Throwable e) {
            TestLoggerFactory.onTestFinished(false, testDescription);
            throwables[0] = e;
        }

        if (throwables[0] != null) {
            throw throwables[0];
        }
    }

    public interface IParsingTestCase {

        MockProjectEx getProject();

        ParserDefinition getParserDefinition();

        void ensureNoErrorElements();

        void doTest(boolean checkResult);

        void doTest(boolean checkResult, boolean ensureNoErrorElements);

        PsiFile parseFile(String name, String text);

        void doCodeTest(@NotNull String code) throws IOException;

        PsiFile getFile();

        void ensureCorrectReparse();
    }

    private static class ParsingTestCaseWrapper extends ParsingTestCase implements IParsingTestCase {
        private ExtensionContext extensionContext;

        private ParserDefinition parserDefinition;
        private static ExtensionContext extensionContextHack;

        private ParsingTestCaseWrapper(ExtensionContext extensionContext, String extension, ParserDefinition parserDefinition) {
            super(passthroughInitHack(extensionContext), extension, parserDefinition);
            this.extensionContext = extensionContext;
            this.parserDefinition = parserDefinition;
        }

        private static String passthroughInitHack(ExtensionContext extensionContext) {
            extensionContextHack = extensionContext;
            return "";
        }

        @Override
        public void setUp() throws Exception {
            super.setUp();
            Store store = getStore(extensionContext);
            store.put("disposable", Disposer.newDisposable("ParsingTestCaseWrapper"));
        }

        @Override
        public void tearDown() throws Exception {
            Store store = getStore(extensionContext);
            Disposable disposable = (Disposable) store.get("disposable");
            if (disposable != null) {
                Disposer.dispose(disposable);
                store.remove("disposable");
            }
            super.tearDown(); // Clears fields!
        }

        @Override
        public ParserDefinition getParserDefinition() {
            return parserDefinition;
        }

        @Override
        public String getName() {
            String testname = extensionContext.getDisplayName().replace(" ", "_");
            ParserResultFile parserResultFile = extensionContext.getRequiredTestMethod().getAnnotation(ParserResultFile.class);
            if (parserResultFile != null) {
                testname = parserResultFile.value();
            }
            return testname;
        }

        @Override
        protected String getTestDataPath() {
            if (extensionContext == null) // Method is called by super-constructor, damnit!
            {
                extensionContext = extensionContextHack;
            }
            TestDataPath testDataPath = getMethodOrClassAnnotation(TestDataPath.class);
            if (testDataPath == null) {
                return super.getTestDataPath();
            }
            TestDataSubPath testDataSubPath = getMethodOrClassAnnotation(TestDataSubPath.class);
            if (testDataSubPath == null) {
                return testDataPath.value();
            }
            return Paths.get(testDataPath.value(), testDataSubPath.value()).toString();
        }

        private <T extends Annotation> T getMethodOrClassAnnotation(Class<T> clazz) {
            T annotation = extensionContext.getRequiredTestMethod().getAnnotation(clazz);
            if (annotation == null) {
                annotation = extensionContext.getRequiredTestClass().getAnnotation(clazz);
            }
            return annotation;
        }

        @Override
        public void ensureNoErrorElements() {
            super.ensureNoErrorElements();
        }

        @Override
        public void doTest(boolean checkResult) {
            super.doTest(checkResult);
        }

        @Override
        public void doTest(boolean checkResult, boolean ensureNoErrorElements) {
            super.doTest(checkResult, ensureNoErrorElements);
        }

        @Override
        public PsiFile parseFile(String name, String text) {
            return super.parseFile(name, text);
        }

        @Override
        public void doCodeTest(@NotNull String code) throws IOException {
            super.doCodeTest(code);
        }

        @Override
        public PsiFile getFile() {
            return myFile;
        }

        @Override
        public void ensureCorrectReparse() {
            ensureCorrectReparse(myFile);
        }
    }
}
