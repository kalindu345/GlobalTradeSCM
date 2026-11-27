import com.globaltrade.scm.entity.Vendor;
import com.globaltrade.scm.enums.VendorStatus;
import com.globaltrade.scm.integration.support.CmtRollbackTestBean;

import jakarta.ejb.EJB;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ArquillianExtension.class)
class CmtRollbackIT {

    @EJB
    private CmtRollbackTestBean testBean;

    @Deployment
    public static WebArchive createDeployment() {

        return ShrinkWrap
                .create(
                        WebArchive.class,
                        "cmt-rollback-test.war"
                )

                .addClass(Vendor.class)
                .addClass(VendorStatus.class)
                .addClass(CmtRollbackTestBean.class)

                .addAsResource(
                        "META-INF/persistence.xml",
                        "META-INF/persistence.xml"
                )

                .addAsWebInfResource(
                        EmptyAsset.INSTANCE,
                        "beans.xml"
                );
    }

    @Test
    void shouldRollbackContainerManagedTransaction() {

        String email =
                "rollback."
                        + System.currentTimeMillis()
                        + "@test.com";

        assertThrows(
                RuntimeException.class,
                () ->
                        testBean.createVendorThenFail(
                                email
                        )
        );

        long count =
                testBean.countByEmail(
                        email
                );

        assertEquals(
                0L,
                count
        );
    }
}