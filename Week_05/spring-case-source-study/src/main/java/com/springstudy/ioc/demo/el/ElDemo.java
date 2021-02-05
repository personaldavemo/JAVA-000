package com.springstudy.ioc.demo.el;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.SimpleEvaluationContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

@Component
public class ElDemo {
    private String name;
    private String info;
    private Date time;

    public ElDemo(String name, String info) {
        this.name = name;
        this.info = info;
        this.time = new Date();
    }

    public ElDemo(String name, String info, Date time) {
        this.name = name;
        this.info = info;
        this.time = time;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "ElDemo{" +
                "name='" + name + '\'' +
                ", info='" + info + '\'' +
                '}';
    }

    public static void main(String[] args) {
        ExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression("'hello el'.concat('!')");
        System.out.println((String) expression.getValue());

        Expression expression2 = parser.parseExpression("'hello el'.bytes.length");
        System.out.println(expression2.getValue());

        Expression expression3 = parser.parseExpression("new String('hello el').toUpperCase()");
        System.out.println(expression3.getValue(String.class));

        GregorianCalendar calendar = new GregorianCalendar();
        calendar.set(1856,7,9);

        ElDemo demo = new ElDemo("Dave","el-test",calendar.getTime());
        //获取构造器中的某个参数
        Expression expression4 = parser.parseExpression("name");
        System.out.println((String) expression4.getValue(demo));

        Expression expression5 = parser.parseExpression("name == 'Dave'");
        System.out.println(expression5.getValue(demo,Boolean.class));

        ElDemo.Simple simple = new ElDemo.Simple();
        simple.booleanList.add(true);
        EvaluationContext context = SimpleEvaluationContext.forReadOnlyDataBinding().build();
        parser.parseExpression("booleanList[0]").setValue(context,simple,"false");
        System.out.println(simple.booleanList.get(0));

    }
    static class Simple {
        public List<Boolean> booleanList = new ArrayList<>();
    }
}
