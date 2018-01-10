package fr.pe.gepo.referentiel.commun.services.fabriques;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import fr.pe.gepo.referentiel.commun.beans.ContexteApplicatifBean;
import fr.pe.gepo.referentiel.commun.beans.DonneesAllocationsBean;
import fr.pe.gepo.referentiel.commun.beans.InfosServiceBean;
import fr.pe.gepo.referentiel.commun.data.enums.EligibiliteDalExpressEnum;
import fr.pe.gepo.referentiel.commun.services.ServiceLectureProperties;

@RunWith(MockitoJUnitRunner.class)
public class ConstructeurServiceDALExpressTest {
    @InjectMocks
    private ConstructeurServiceDALExpress constructeurServiceDALExpress;

    @Mock
    private ServiceLectureProperties serviceLectureProperties;

    @Test
    public void test_construire_ELIGIBLE_CREATION_premier_passage_AVEC_appels_services() throws Exception {

        final ContexteApplicatifBean context = new ContexteApplicatifBean();
        final DonneesAllocationsBean donneesAllocationsBean = new DonneesAllocationsBean();
        donneesAllocationsBean.setEtatEligibilite(EligibiliteDalExpressEnum.ELIGIBLE_CREATION);
        context.setDonneesAllocations(donneesAllocationsBean);

        final InfosServiceBean serviceBean = this.constructeurServiceDALExpress.construire("depot-demande-allocation", context);

        assertNotNull(serviceBean);
        assertEquals(serviceBean.getCode(), "depot-demande-allocation");
    }

    @Test
    public void test_construire_ELIGIBLE_REPRISE_second_passage_SANS_appels_services() throws Exception {

        final DonneesAllocationsBean donneesAllocations = new DonneesAllocationsBean();
        donneesAllocations.setEtatEligibilite(EligibiliteDalExpressEnum.ELIGIBLE_REPRISE);

        final ContexteApplicatifBean context = new ContexteApplicatifBean();
        context.setDonneesAllocations(donneesAllocations);

        final InfosServiceBean serviceBean = this.constructeurServiceDALExpress.construire("reprise-demande-allocation", context);

        assertNotNull(serviceBean);
        assertEquals(serviceBean.getCode(), "reprise-demande-allocation");
    }



    @Test
    public void test_construire_NON_ELIGIBLE() throws Exception {
        // Set up D12DAL mock
        final ContexteApplicatifBean context = new ContexteApplicatifBean();
        final DonneesAllocationsBean donneesAllocationsBean = new DonneesAllocationsBean();
        donneesAllocationsBean.setEtatEligibilite(EligibiliteDalExpressEnum.NON_ELIGIBILE);
        context.setDonneesAllocations(donneesAllocationsBean);

        final InfosServiceBean serviceBean = this.constructeurServiceDALExpress.construire("depot-demande-allocation", context);

        assertNotNull(serviceBean);
    }


    @Test
    public void test_construire_ELIGIBLE_ALLOCATION() throws Exception {

        final ContexteApplicatifBean context = new ContexteApplicatifBean();
        final DonneesAllocationsBean donneesAllocationsBean = new DonneesAllocationsBean();
        donneesAllocationsBean.setEtatEligibilite(EligibiliteDalExpressEnum.NON_ELIGIBILE);
        context.setDonneesAllocations(donneesAllocationsBean);

        final InfosServiceBean serviceBean = this.constructeurServiceDALExpress.construire("suivi-demande-allocation", context);

        assertNotNull(serviceBean);
        assertEquals(serviceBean.getCode(), "suivi-demande-allocation");
    }


}
