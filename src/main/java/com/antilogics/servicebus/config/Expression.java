package com.antilogics.servicebus.config;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.antilogics.servicebus.core.HttpMessage;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import javax.el.ELManager;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

@JsonDeserialize(using = JakartaExpressionDeserializer.class)
@RequiredArgsConstructor
@ToString(of = {"expression"})
@Slf4j
public class Expression {
    private final String expression;


    public Object eval(@NonNull HttpMessage httpMessage) {
        ELManager elManager = new ELManager();
        ExpressionFactory factory = ELManager.getExpressionFactory();
        elManager.defineBean("env", System.getenv());
        elManager.defineBean("message", httpMessage);
        ValueExpression exp = factory.createValueExpression(elManager.getELContext(), expression, Object.class);
        return exp.getValue(elManager.getELContext());
    }


    public String evalString(HttpMessage httpMessage) {
        Object result = eval(httpMessage);
        if (result == null) {
            log.warn("Null value eval for message {} for expression {}", httpMessage, expression);
        }
        return result.toString();
    }
}
