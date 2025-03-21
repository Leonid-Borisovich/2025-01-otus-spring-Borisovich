package ru.otus.hw.testing;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.hw.dao.QuestionDao;

public class DaoParameterResolver implements ParameterResolver {
    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
            throws ParameterResolutionException {
        return parameterContext.getParameter().getType() == QuestionDao.class;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        context.getBean("questionDao");
        return parameterContext.getParameter().getType() == QuestionDao.class
                ? context.getBean("questionDao")    // we get CsvQuestionDao()
                : null;
    }
}
