MVN := mvn
MVN_SPRING := spring-boot:run

.PHONY: dev

tests: # @HELP Run project test with datajpatest configured profile
	$(MVN) clean test -Dspring-boot.run.profiles=datajpatest

ddl-gen: # @HELP Run spring-boot project with DEV profile (application-dev[.yaml|.properties] file must exists)
	$(MVN) clean test $(MVN_SPRING) -Dspring-boot.run.profiles=ddl

dev: # @HELP Run spring-boot project with DEV profile (application-dev[.yaml|.properties] file must exists)
	$(MVN) clean test $(MVN_SPRING) -Dspring-boot.run.profiles=dev

prod: # @HELP Run spring-boot project with PROD profile (application[.yaml|.properties] file must exists)
	$(MVN) clean test $(MVN_SPRING) -Dspring-boot.run.profiles=prod
