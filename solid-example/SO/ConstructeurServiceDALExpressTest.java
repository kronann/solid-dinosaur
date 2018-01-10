package fr.pe.gepo.referentiel.commun.services.fabriques;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import fr.pe.d12dal.service.es.dal.RetourLireListeSyntheseDalWeb;
import fr.pe.gepo.referentiel.commun.beans.ContexteServices;
import fr.pe.gepo.referentiel.commun.beans.DonneesAllocationsBean;
import fr.pe.gepo.referentiel.commun.beans.DonneesIndividuBean;
import fr.pe.gepo.referentiel.commun.beans.InfosServiceBean;
import fr.pe.gepo.referentiel.commun.data.enums.EligibiliteDalExpressEnum;
import fr.pe.gepo.referentiel.commun.data.sortie.EligibiliteDalExpress;
import fr.pe.gepo.referentiel.commun.services.ServiceLectureProperties;
import fr.pe.gepo.referentiel.commun.services.distant.ServiceCali;
import fr.pe.gepo.referentiel.commun.services.distant.ServiceD12Dal;
import fr.unedic.cali.logique.donnees.cali.service.vls.SortieLectureContexteExamen;

@RunWith(MockitoJUnitRunner.class)
public class ConstructeurServiceDALExpressTest {
    @InjectMocks
    private ConstructeurServiceDALExpress constructeurServiceDALExpress;

    @Mock
    private ServiceLectureProperties serviceLectureProperties;

    @Mock
    private ServiceD12Dal serviceD12Dal;

    @Mock
    private ServiceCali serviceCali;

    @Test
    public void test_construire_ELIGIBLE_CREATION_premier_passage_AVEC_appels_services_D12DAL_Cali() throws Exception {
        // Set up D12DAL mock
        final RetourLireListeSyntheseDalWeb retourD12Dal = RetourLireListeSyntheseDalWeb.getInstance();
        retourD12Dal.setTopEligibleDalExpresse(Boolean.TRUE);
        retourD12Dal.setTopForcageEligibiliteDalExpresse(Boolean.FALSE);
        retourD12Dal.setTopPresenceDalExpresseBrouillon(Boolean.FALSE);
        when(this.serviceD12Dal.lireListeSyntheseDalWeb(any(DonneesIndividuBean.class))).thenReturn(retourD12Dal);

        // Set up Cali mock
        final SortieLectureContexteExamen retourCali = SortieLectureContexteExamen.getInstance();
        retourCali.setPresenceDemandeAttenteDecision(0);
        when(this.serviceCali.lectureContexteExamen(any(DonneesIndividuBean.class))).thenReturn(retourCali);

        final ContexteServices context = new ContexteServices(123L);
        final InfosServiceBean serviceBean = this.constructeurServiceDALExpress.construire("depot-demande-allocation", context);

        verify(this.serviceD12Dal, Mockito.atLeastOnce()).lireListeSyntheseDalWeb(any(DonneesIndividuBean.class));
        verify(this.serviceCali, Mockito.atLeastOnce()).lectureContexteExamen(any(DonneesIndividuBean.class));
        assertNotNull(serviceBean);
        assertEquals(serviceBean.getCode(), "depot-demande-allocation");
    }

    @Test
    public void test_construire_ELIGIBLE_REPRISE_second_passage_SANS_appels_services_D12DAL_Cali() throws Exception {

        final DonneesAllocationsBean donneesAllocations = new DonneesAllocationsBean();
        donneesAllocations.setEtatEligibilite(EligibiliteDalExpressEnum.ELIGIBLE_REPRISE);

        final ContexteServices context = new ContexteServices(123L);
        context.setDonneesAllocations(donneesAllocations);

        final InfosServiceBean serviceBean = this.constructeurServiceDALExpress.construire("reprise-demande-allocation", context);

        verify(this.serviceD12Dal, Mockito.never()).lireListeSyntheseDalWeb(any(DonneesIndividuBean.class));
        verify(this.serviceCali, Mockito.never()).lectureContexteExamen(any(DonneesIndividuBean.class));
        assertNotNull(serviceBean);
        assertEquals(serviceBean.getCode(), "reprise-demande-allocation");
    }

    @Test
    public void test_construire_NON_ELIGIBLE() throws Exception {
        // Set up D12DAL mock
        final RetourLireListeSyntheseDalWeb retourD12Dal = RetourLireListeSyntheseDalWeb.getInstance();
        retourD12Dal.setTopEligibleDalExpresse(Boolean.FALSE);
        when(this.serviceD12Dal.lireListeSyntheseDalWeb(any(DonneesIndividuBean.class))).thenReturn(retourD12Dal);
        final ContexteServices context = new ContexteServices(123L);
        final InfosServiceBean serviceBean = this.constructeurServiceDALExpress.construire("depot-demande-allocation", context);
        verify(this.serviceD12Dal, Mockito.atLeastOnce()).lireListeSyntheseDalWeb(any(DonneesIndividuBean.class));
        verify(this.serviceCali, Mockito.never()).lectureContexteExamen(any(DonneesIndividuBean.class));
        assertNull(serviceBean);
    }

    @Test
    public void test_constuireEligibilite_non_eligible_D12DAL() throws Exception {
        // Set up D12DAL mock
        final RetourLireListeSyntheseDalWeb retourD12Dal = RetourLireListeSyntheseDalWeb.getInstance();
        retourD12Dal.setTopEligibleDalExpresse(Boolean.FALSE);
        retourD12Dal.setTopForcageEligibiliteDalExpresse(Boolean.FALSE);
        retourD12Dal.setTopPresenceDalExpresseBrouillon(Boolean.FALSE);
        when(this.serviceD12Dal.lireListeSyntheseDalWeb(any(DonneesIndividuBean.class))).thenReturn(retourD12Dal);

        final ContexteServices context = new ContexteServices(123L);
        final EligibiliteDalExpressEnum eligibiliteDalExpress = this.constructeurServiceDALExpress.constuireEligibilite(context);

        verify(this.serviceD12Dal, Mockito.atLeastOnce()).lireListeSyntheseDalWeb(any(DonneesIndividuBean.class));
        verify(this.serviceCali, Mockito.never()).lectureContexteExamen(any(DonneesIndividuBean.class));
        assertEquals(EligibiliteDalExpressEnum.NON_ELIGIBILE, eligibiliteDalExpress);
    }

    @Test
    public void test_constuireEligibilite_non_eligible_Cali() throws Exception {
        // Set up D12DAL mock
        final RetourLireListeSyntheseDalWeb retourD12Dal = RetourLireListeSyntheseDalWeb.getInstance();
        retourD12Dal.setTopEligibleDalExpresse(Boolean.TRUE);
        retourD12Dal.setTopForcageEligibiliteDalExpresse(Boolean.FALSE);
        retourD12Dal.setTopPresenceDalExpresseBrouillon(Boolean.FALSE);
        when(this.serviceD12Dal.lireListeSyntheseDalWeb(any(DonneesIndividuBean.class))).thenReturn(retourD12Dal);

        // Set up Cali mock
        final SortieLectureContexteExamen retourCali = SortieLectureContexteExamen.getInstance();
        retourCali.setPresenceDemandeAttenteDecision(1);
        when(this.serviceCali.lectureContexteExamen(any(DonneesIndividuBean.class))).thenReturn(retourCali);

        final ContexteServices context = new ContexteServices(123L);
        final EligibiliteDalExpressEnum eligibiliteDalExpress = this.constructeurServiceDALExpress.constuireEligibilite(context);

        verify(this.serviceD12Dal, Mockito.atLeastOnce()).lireListeSyntheseDalWeb(any(DonneesIndividuBean.class));
        verify(this.serviceCali, Mockito.atLeastOnce()).lectureContexteExamen(any(DonneesIndividuBean.class));
        assertEquals(EligibiliteDalExpressEnum.NON_ELIGIBILE, eligibiliteDalExpress);
    }

    @Test
    public void test_constuireEligibilite_eligible_reprise() throws Exception {
        // Set up D12DAL mock
        final RetourLireListeSyntheseDalWeb retourD12Dal = RetourLireListeSyntheseDalWeb.getInstance();
        retourD12Dal.setTopEligibleDalExpresse(Boolean.TRUE);
        retourD12Dal.setTopForcageEligibiliteDalExpresse(Boolean.FALSE);
        retourD12Dal.setTopPresenceDalExpresseBrouillon(Boolean.TRUE);
        when(this.serviceD12Dal.lireListeSyntheseDalWeb(any(DonneesIndividuBean.class))).thenReturn(retourD12Dal);

        // Set up Cali mock
        final SortieLectureContexteExamen retourCali = SortieLectureContexteExamen.getInstance();
        retourCali.setPresenceDemandeAttenteDecision(0);
        retourCali.setPresenceReliquatAreValide(0);
        when(this.serviceCali.lectureContexteExamen(any(DonneesIndividuBean.class))).thenReturn(retourCali);

        final ContexteServices context = new ContexteServices(123L);
        final EligibiliteDalExpressEnum eligibiliteDalExpress = this.constructeurServiceDALExpress.constuireEligibilite(context);

        verify(this.serviceD12Dal, Mockito.atLeastOnce()).lireListeSyntheseDalWeb(any(DonneesIndividuBean.class));
        verify(this.serviceCali, Mockito.atLeastOnce()).lectureContexteExamen(any(DonneesIndividuBean.class));
        assertEquals(EligibiliteDalExpressEnum.ELIGIBLE_REPRISE, eligibiliteDalExpress);
    }

    @Test
    public void test_calculerEligibiliteDalExpress_exception_appel_D12DAL() throws Exception {
        when(this.serviceD12Dal.lireListeSyntheseDalWeb(any(DonneesIndividuBean.class))).thenThrow(new RuntimeException());

        final ContexteServices context = new ContexteServices(123L);
        final EligibiliteDalExpress eligibiliteDalExpress = this.constructeurServiceDALExpress.calculerEligibiliteDalExpress(context);

        assertEquals(EligibiliteDalExpressEnum.NON_ELIGIBILE, eligibiliteDalExpress.getEligibiliteDalExpressEnum());
    }

    @Test
    public void test_calculerEligibiliteDalExpress_reponse_D12DAL_vide() throws Exception {
        final RetourLireListeSyntheseDalWeb emptyRetourD12Dal = RetourLireListeSyntheseDalWeb.getInstance();
        when(this.serviceD12Dal.lireListeSyntheseDalWeb(any(DonneesIndividuBean.class))).thenReturn(emptyRetourD12Dal);

        final ContexteServices context = new ContexteServices(123L);
        final EligibiliteDalExpress eligibiliteDalExpress = this.constructeurServiceDALExpress.calculerEligibiliteDalExpress(context);

        assertEquals(EligibiliteDalExpressEnum.NON_ELIGIBILE, eligibiliteDalExpress.getEligibiliteDalExpressEnum());
    }

    @Test
    public void test_calculerEligibiliteDalExpress_non_eligible_DAL() throws Exception {
        final RetourLireListeSyntheseDalWeb retourD12Dal = RetourLireListeSyntheseDalWeb.getInstance();
        retourD12Dal.setTopEligibleDalExpresse(Boolean.FALSE);
        when(this.serviceD12Dal.lireListeSyntheseDalWeb(any(DonneesIndividuBean.class))).thenReturn(retourD12Dal);

        final ContexteServices context = new ContexteServices(123L);
        final EligibiliteDalExpress eligibiliteDalExpress = this.constructeurServiceDALExpress.calculerEligibiliteDalExpress(context);

        assertEquals(EligibiliteDalExpressEnum.NON_ELIGIBILE, eligibiliteDalExpress.getEligibiliteDalExpressEnum());
    }

    @Test
    public void test_calculerEligibiliteDalExpress_forcage_eligibilite_DAL() throws Exception {
        final RetourLireListeSyntheseDalWeb retourD12Dal = RetourLireListeSyntheseDalWeb.getInstance();
        retourD12Dal.setTopEligibleDalExpresse(Boolean.FALSE);
        retourD12Dal.setTopForcageEligibiliteDalExpresse(Boolean.TRUE);
        when(this.serviceD12Dal.lireListeSyntheseDalWeb(any(DonneesIndividuBean.class))).thenReturn(retourD12Dal);

        final ContexteServices context = new ContexteServices(123L);
        final EligibiliteDalExpress eligibiliteDalExpress = this.constructeurServiceDALExpress.calculerEligibiliteDalExpress(context);

        assertEquals(EligibiliteDalExpressEnum.ELIGIBLE_CREATION, eligibiliteDalExpress.getEligibiliteDalExpressEnum());
        assertTrue(eligibiliteDalExpress.isForcageEligibilite());
    }

    @Test
    public void test_calculerEligibiliteDalExpress_presence_brouillon_DAL() throws Exception {
        final RetourLireListeSyntheseDalWeb retourD12Dal = RetourLireListeSyntheseDalWeb.getInstance();
        retourD12Dal.setTopEligibleDalExpresse(Boolean.TRUE);
        retourD12Dal.setTopForcageEligibiliteDalExpresse(Boolean.FALSE);
        retourD12Dal.setTopPresenceDalExpresseBrouillon(Boolean.TRUE);
        when(this.serviceD12Dal.lireListeSyntheseDalWeb(any(DonneesIndividuBean.class))).thenReturn(retourD12Dal);

        final ContexteServices context = new ContexteServices(123L);
        final EligibiliteDalExpress eligibiliteDalExpress = this.constructeurServiceDALExpress.calculerEligibiliteDalExpress(context);

        assertEquals(EligibiliteDalExpressEnum.ELIGIBLE_REPRISE, eligibiliteDalExpress.getEligibiliteDalExpressEnum());
    }

    @Test
    public void test_calculerEligibiliteDalExpress_OK() throws Exception {
        final RetourLireListeSyntheseDalWeb retourD12Dal = RetourLireListeSyntheseDalWeb.getInstance();
        retourD12Dal.setTopEligibleDalExpresse(Boolean.TRUE);
        retourD12Dal.setTopForcageEligibiliteDalExpresse(Boolean.FALSE);
        retourD12Dal.setTopPresenceDalExpresseBrouillon(Boolean.FALSE);
        when(this.serviceD12Dal.lireListeSyntheseDalWeb(any(DonneesIndividuBean.class))).thenReturn(retourD12Dal);

        final ContexteServices context = new ContexteServices(123L);
        final EligibiliteDalExpress eligibiliteDalExpress = this.constructeurServiceDALExpress.calculerEligibiliteDalExpress(context);

        assertEquals(EligibiliteDalExpressEnum.ELIGIBLE_CREATION, eligibiliteDalExpress.getEligibiliteDalExpressEnum());
    }

    @Test
    public void test_isEligibleCali_KO_exception_appel_Cali() throws Exception {
        when(this.serviceCali.lectureContexteExamen(any(DonneesIndividuBean.class))).thenThrow(new RuntimeException());

        final ContexteServices context = new ContexteServices(123L);
        final boolean eligibleCali = this.constructeurServiceDALExpress.isEligibleCali(context);

        assertFalse(eligibleCali);
    }

    @Test
    public void test_isEligibleCali_KO_reponse_Cali_vide() throws Exception {
        final SortieLectureContexteExamen emptyRetourCali = SortieLectureContexteExamen.getInstance();
        when(this.serviceCali.lectureContexteExamen(any(DonneesIndividuBean.class))).thenReturn(emptyRetourCali);

        final ContexteServices context = new ContexteServices(123L);
        final boolean eligibleCali = this.constructeurServiceDALExpress.isEligibleCali(context);

        assertFalse(eligibleCali);
    }

    @Test
    public void test_isEligibleCali_KO_en_attente_decision() throws Exception {
        final SortieLectureContexteExamen retourCali = SortieLectureContexteExamen.getInstance();
        retourCali.setPresenceDemandeAttenteDecision(1);
        when(this.serviceCali.lectureContexteExamen(any(DonneesIndividuBean.class))).thenReturn(retourCali);

        final ContexteServices context = new ContexteServices(123L);
        final boolean eligibleCali = this.constructeurServiceDALExpress.isEligibleCali(context);

        assertFalse(eligibleCali);
    }

    @Test
    public void test_isEligibleCali_KO_presence_reliquat_ARE_non_spectacle() throws Exception {
        final SortieLectureContexteExamen retourCali = SortieLectureContexteExamen.getInstance();
        retourCali.setPresenceDemandeAttenteDecision(null);
        retourCali.setPresenceReliquatAreValide(1);
        retourCali.setEstReliquatSpectacle(0);
        when(this.serviceCali.lectureContexteExamen(any(DonneesIndividuBean.class))).thenReturn(retourCali);

        final ContexteServices context = new ContexteServices(123L);
        final boolean eligibleCali = this.constructeurServiceDALExpress.isEligibleCali(context);

        assertFalse(eligibleCali);
    }

    @Test
    public void test_isEligibleCali_OK_no_attente_decision_no_reliquat_ARE() throws Exception {
        final SortieLectureContexteExamen retourCali = SortieLectureContexteExamen.getInstance();
        retourCali.setPresenceDemandeAttenteDecision(0);
        retourCali.setPresenceReliquatAreValide(0);
        when(this.serviceCali.lectureContexteExamen(any(DonneesIndividuBean.class))).thenReturn(retourCali);

        final ContexteServices context = new ContexteServices(123L);
        final boolean eligibleCali = this.constructeurServiceDALExpress.isEligibleCali(context);

        assertTrue(eligibleCali);
    }

    @Test
    public void test_isEligibleCali_OK_presence_reliquat_ARE_spectacle() throws Exception {
        final SortieLectureContexteExamen retourCali = SortieLectureContexteExamen.getInstance();
        retourCali.setPresenceDemandeAttenteDecision(0);
        retourCali.setPresenceReliquatAreValide(1);
        retourCali.setEstReliquatSpectacle(1);
        when(this.serviceCali.lectureContexteExamen(any(DonneesIndividuBean.class))).thenReturn(retourCali);

        final ContexteServices context = new ContexteServices(123L);
        final boolean eligibleCali = this.constructeurServiceDALExpress.isEligibleCali(context);

        assertTrue(eligibleCali);
    }
}
