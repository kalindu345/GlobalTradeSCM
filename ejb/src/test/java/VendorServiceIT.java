import com.globaltrade.scm.entity.Vendor;
import com.globaltrade.scm.enums.VendorStatus;
import com.globaltrade.scm.service.VendorService;

import jakarta.ejb.EJB;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(ArquillianExtension.class)
class VendorServiceIT {

    @EJB
    private VendorService vendorService;

    @Deployment
    public static WebArchive createDeployment() {

        return ShrinkWrap
                .create(
                        WebArchive.class,
                        "vendor-service-test.war"
                )
                .addClass(Vendor.class)
                .addClass(VendorStatus.class)
                .addClass(VendorService.class)
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
    void shouldInjectVendorService() {

        assertNotNull(vendorService);
    }
}