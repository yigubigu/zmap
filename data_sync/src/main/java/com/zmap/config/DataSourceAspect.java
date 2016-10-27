package com.zmap.config;

import com.zmap.util.DatabaseContextHolder;
import com.zmap.util.DatabaseType;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DataSourceAspect {

	@Pointcut("execution(* com.zmap.his.mapper.**.*(..))")
    public void declareJointPointExpression() {
    }

    @Before("declareJointPointExpression()")
    public void setDataSourceKey(JoinPoint point){
        //根据连接点所属的类实例，动态切换数据源
        if (point.getTarget() instanceof com.zmap.his.mapper.PatientRegistrationMapper) {
            DatabaseContextHolder.setDatabaseType(DatabaseType.sqlserver);
        } 
    }
}
