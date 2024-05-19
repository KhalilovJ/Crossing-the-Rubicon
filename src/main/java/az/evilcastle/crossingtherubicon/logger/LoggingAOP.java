package az.evilcastle.crossingtherubicon.logger;

import az.evilcastle.crossingtherubicon.model.constant.LogLevel;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggingAOP {

    @Pointcut("execution(* az.evilcastle.crossingtherubicon.service..*(..))")
    public void applicationPackagePointcut() {
    }

    @Pointcut("execution(!private * *(..))")
    public void methodModifierPointcut() {
    }

    @Around("applicationPackagePointcut() && methodModifierPointcut()")
    public Object logMethodCall(ProceedingJoinPoint joinPoint) throws Throwable {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = joinPoint.getSignature().getName();
        Loggable loggableAnnotation = signature.getMethod().getAnnotation(Loggable.class);

        LogLevel logLevel = defineLogLevel(loggableAnnotation);
        int[] chosenArgsIds = defineLogArgs(loggableAnnotation);

        var parameterNames = signature.getParameterNames();
        var allArgs = joinPoint.getArgs();

        var chosenArgs = getLoggableArguments(chosenArgsIds, parameterNames, allArgs);

        logWithLevel(logLevel, "ActionLog.{}.start. {}", methodName, chosenArgs);
        Object result = joinPoint.proceed();
        logWithLevel(logLevel, "ActionLog.{}.end. {}", methodName, chosenArgs);

        return result;
    }

    private LogLevel defineLogLevel(Loggable loggableAnnotation) {
        return loggableAnnotation != null ? loggableAnnotation.level() : LogLevel.DEBUG;
    }

    private int[] defineLogArgs(Loggable loggableAnnotation) {
        return loggableAnnotation != null ? loggableAnnotation.args() : new int[]{};
    }

    private String getLoggableArguments(int[] chosenArgsIds, String[] parameterNames, Object[] allArgs) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int index : chosenArgsIds) {
            if (index >= 0 && index < allArgs.length) {
                stringBuilder
                        .append(parameterNames[index])
                        .append(": ")
                        .append(allArgs[index])
                        .append(", ");
            }
        }

        if (stringBuilder.length() > 0) {
            stringBuilder.setLength(stringBuilder.length() - 2);
        }

        return stringBuilder.toString();
    }

    private void logWithLevel(LogLevel logLevel, String message, String methodName, String args) {
        switch (logLevel) {
            case DEBUG -> log.debug(message, methodName, args);
            case INFO -> log.info(message, methodName, args);
            case WARN -> log.warn(message, methodName, args);
            case ERROR -> log.error(message, methodName, args);
            default -> log.debug(message);
        }
    }
}
