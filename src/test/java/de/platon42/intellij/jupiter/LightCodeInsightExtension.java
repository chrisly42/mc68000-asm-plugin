package de.platon42.intellij.jupiter;


import com.intellij.openapi.Disposable;
import com.intellij.openapi.util.Disposer;
import com.intellij.testFramework.EdtTestUtilKt;
import com.intellij.testFramework.TestLoggerFactory;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import com.intellij.testFramework.fixtures.CodeInsightTestFixture;
import com.intellij.testFramework.fixtures.IdeaTestExecutionPolicy;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.extension.*;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;
import org.junit.jupiter.api.extension.ExtensionContext.Store;
import org.junit.runner.Description;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class LightCodeInsightExtension implements ParameterResolver, AfterTestExecutionCallback, InvocationInterceptor {

    private static final Logger LOG = Logger.getLogger(LightCodeInsightExtension.class.getName());

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        Parameter parameter = parameterContext.getParameter();
        return parameter.isAnnotationPresent(MyFixture.class)
                || parameter.isAnnotationPresent(MyTestCase.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        LightCodeInsightFixtureTestCaseWrapper testCase = getWrapper(extensionContext);
        Parameter parameter = parameterContext.getParameter();
        if (parameter.isAnnotationPresent(MyFixture.class)) {
            return testCase.getMyFixture();
        } else if (parameter.isAnnotationPresent(MyTestCase.class)) {
            return testCase;
        }
        return null;
    }

    private LightCodeInsightFixtureTestCaseWrapper getWrapper(ExtensionContext extensionContext) {
        Store store = getStore(extensionContext);
        return (LightCodeInsightFixtureTestCaseWrapper) store.getOrComputeIfAbsent("testCase",
                key -> {
                    LightCodeInsightFixtureTestCaseWrapper wrapper = new LightCodeInsightFixtureTestCaseWrapper(extensionContext);
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
        LightCodeInsightFixtureTestCaseWrapper testCase = (LightCodeInsightFixtureTestCaseWrapper) store.get("testCase");
        if (testCase != null) testCase.tearDown();
    }

    private static Store getStore(ExtensionContext context) {
        return context.getStore(Namespace.create(LightCodeInsightExtension.class, context.getRequiredTestMethod()));
    }

    @Override
    public void interceptTestMethod(Invocation<Void> invocation, ReflectiveInvocationContext<Method> invocationContext, ExtensionContext extensionContext) throws Throwable {
        Throwable[] throwables = new Throwable[1];
        Description testDescription = Description.createTestDescription(extensionContext.getRequiredTestClass(), getWrapper(extensionContext).getName());

        Runnable runnable = () -> {
            try {
                TestLoggerFactory.onTestStarted();
                invocation.proceed();
                TestLoggerFactory.onTestFinished(true, testDescription);
            } catch (Throwable e) {
                TestLoggerFactory.onTestFinished(false, testDescription);
                throwables[0] = e;
            }
        };

        invokeTestRunnable(runnable);

        if (throwables[0] != null) throw throwables[0];
    }

    private static void invokeTestRunnable(@NotNull Runnable runnable) {
        IdeaTestExecutionPolicy policy = IdeaTestExecutionPolicy.current();
        if (policy != null && !policy.runInDispatchThread()) runnable.run();
        else EdtTestUtilKt.runInEdtAndWait(() -> {
            runnable.run();
            return null;
        });
    }

    private static class LightCodeInsightFixtureTestCaseWrapper extends BasePlatformTestCase {
        private final ExtensionContext extensionContext;

        private LightCodeInsightFixtureTestCaseWrapper(ExtensionContext extensionContext) {
            this.extensionContext = extensionContext;
        }

        @Override
        public void setUp() throws Exception {
            super.setUp();
            Store store = getStore(extensionContext);
            store.put("disposable", Disposer.newDisposable("LightCodeInsightFixtureTestCaseWrapper"));
        }

        @Override
        public void tearDown() throws Exception {
            Store store = getStore(extensionContext);
            Disposable disposable = (Disposable) store.get("disposable");
            if (myFixture != null && disposable != null) {
                Disposer.dispose(disposable);
                store.remove("disposable");
            }
            super.tearDown();
        }

        @Override
        protected String getTestDataPath() {
            TestDataPath testDataPath = getMethodOrClassAnnotation(TestDataPath.class);
            if (testDataPath == null) return super.getTestDataPath();
            TestDataSubPath testDataSubPath = getMethodOrClassAnnotation(TestDataSubPath.class);
            if (testDataSubPath == null) return testDataPath.value();
            return Paths.get(testDataPath.value(), testDataSubPath.value()).toString();
        }

        public CodeInsightTestFixture getMyFixture() {
            return myFixture;
        }

        private <T extends Annotation> T getMethodOrClassAnnotation(Class<T> clazz) {
            T annotation = extensionContext.getRequiredTestMethod().getAnnotation(clazz);
            if (annotation == null) annotation = extensionContext.getRequiredTestClass().getAnnotation(clazz);
            return annotation;
        }
    }
}
