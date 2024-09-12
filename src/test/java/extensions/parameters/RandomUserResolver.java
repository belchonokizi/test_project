package extensions.parameters;

import extensions.models.User;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.util.Random;

//создание одноразового пользователя для теста
public class RandomUserResolver implements ParameterResolver {
    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.isAnnotated(RandomUser.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        Class<?> type = parameterContext.getParameter().getType();
        if (User.class.equals(type)) {
            Random random = new Random();
            User user = new User();
            user.setName("testUser" + Math.abs(random.nextInt()));
            user.setJob("testJob" + Math.abs(random.nextInt()));
            return user;
        }
        throw new ParameterResolutionException("No generator implemented for " + type);
    }
}
