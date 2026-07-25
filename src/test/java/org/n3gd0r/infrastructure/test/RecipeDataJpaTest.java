package org.n3gd0r.infrastructure.test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.ActiveProfiles;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@DataJpaTest(includeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Repository.class)
})
@Import(TestDatabaseContainer.class)
@ActiveProfiles("datajpatest")
public @interface RecipeDataJpaTest {
}
