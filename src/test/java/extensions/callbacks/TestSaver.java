package extensions.callbacks;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

//сохранение в файл упавших тестов
public class TestSaver implements AfterTestExecutionCallback, AfterAllCallback {
    public static final Set<String> failedTests = new HashSet<>();

    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        String output = System.getProperty("user.dir") + "/src/test/resources/failed-tests.txt";
        String result = String.join(" ", failedTests);
        Files.writeString(Paths.get(output), result);
    }

    //названия упавших тестов сохраняются в сет
    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        String className = context.getRequiredTestClass().getName();
        Method testMethod = context.getRequiredTestMethod();
        String methodName = testMethod.getName();
        String testToWrite = String.format("--tests %s.%s*", className, methodName);
        context.getExecutionException()
                .ifPresent(exception -> failedTests.add(testToWrite));
    }
}
