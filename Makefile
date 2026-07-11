MVN := ./mvnw
MVN_SPRING := $(MVN) spring-boot:run -Dspring-boot.run.profiles

dev: # @HELP Run spring-boot project with DEV profile (application-dev[.yaml|.properties] file must exists)
	$(MVN_SPRING)=dev

prod: # @HELP Run spring-boot project with PROD profile (application[.yaml|.properties] file must exists)
	$(MVN_SPRING)=prod