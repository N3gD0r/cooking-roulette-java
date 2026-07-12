MVN := ./mvnw
MVN_SPRING := $(MVN) clean compile spring-boot:run 

.PHONY: default

default:
	$(MVN_SPRING)

ddl-gen: # @HELP Run spring-boot project with DEV profile (application-dev[.yaml|.properties] file must exists)
	$(MVN_SPRING)-Dspring-boot.run.profiles=ddl

dev: # @HELP Run spring-boot project with DEV profile (application-dev[.yaml|.properties] file must exists)
	$(MVN_SPRING)-Dspring-boot.run.profiles=dev

prod: # @HELP Run spring-boot project with PROD profile (application[.yaml|.properties] file must exists)
	$(MVN_SPRING)-Dspring-boot.run.profiles=prod
