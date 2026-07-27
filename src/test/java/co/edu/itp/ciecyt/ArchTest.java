package co.edu.itp.ciecyt;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("co.edu.itp.ciecyt");

        noClasses()
            .that()
                .resideInAnyPackage("co.edu.itp.ciecyt.service..")
            .or()
                .resideInAnyPackage("co.edu.itp.ciecyt.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..co.edu.itp.ciecyt.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
