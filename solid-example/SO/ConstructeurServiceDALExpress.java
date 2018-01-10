package fr.pe.gepo.referentiel.commun.services.fabriques;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.pe.d12dal.service.es.dal.RetourLireListeSyntheseDalWeb;
import fr.pe.gepo.referentiel.commun.beans.ContexteServices;
import fr.pe.gepo.referentiel.commun.beans.DonneesAllocationsBean;
import fr.pe.gepo.referentiel.commun.beans.InfosServiceBean;
import fr.pe.gepo.referentiel.commun.data.enums.EligibiliteDalExpressEnum;
import fr.pe.gepo.referentiel.commun.data.sortie.EligibiliteDalExpress;
import fr.pe.gepo.referentiel.commun.services.ServiceLectureProperties;
import fr.pe.gepo.referentiel.commun.services.distant.ServiceCali;
import fr.pe.gepo.referentiel.commun.services.distant.ServiceD12Dal;
import fr.unedic.cali.logique.donnees.cali.service.vls.SortieLectureContexteExamen;

public class ConstructeurServiceDALExpress {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConstructeurServiceDALExpress.class);
    private static final String SERVICE_DEPOT_DEMANDE_ALLOCATION = "depot-demande-allocation";
    private static final String SERVICE_REPRISE_DEMANDE_ALLOCATION = "reprise-demande-allocation";

    @Inject
    private ServiceLectureProperties serviceLectureProperties;

    @Inject
    private ServiceD12Dal serviceD12Dal;

    @Inject
    private ServiceCali serviceCali;

    /**
     * Retourne le {@link InfosServiceBean} correspondant au code service près vérification de l'éligibilité lors du premier appel. <br>
     * L'éligibilite est sauvegardée dans le contexte pour bypasser les appels par la suite.
     *
     * @param refService le refService
     * @param contexte le contexte
     * @return {@link InfosServiceBean}
     */
    public InfosServiceBean construire(final String code, final ContexteServices contexte) {
        InfosServiceBean serviceBean = new InfosServiceBean();
        DonneesAllocationsBean donneesAllocations = contexte.getDonneesAllocations();

        // Premier passage : on effectue les appels successif aux services DR7, D12DAL et Cali
        if ((donneesAllocations == null) || (donneesAllocations.getEtatEligibilite() == null)) {
            final EligibiliteDalExpressEnum eligibiliteDalExpress = constuireEligibilite(contexte);

            // Sauvegarde dans le contexte pour eviter de futurs appels
            donneesAllocations = new DonneesAllocationsBean();
            donneesAllocations.setEtatEligibilite(eligibiliteDalExpress);
            contexte.setDonneesAllocations(donneesAllocations);
        }

        // Verif de l'eligibilite au service via le contexte
        if ((donneesAllocations.getEtatEligibilite().equals(EligibiliteDalExpressEnum.ELIGIBLE_CREATION)
                        && SERVICE_DEPOT_DEMANDE_ALLOCATION.equals(code))
                        || (donneesAllocations.getEtatEligibilite().equals(EligibiliteDalExpressEnum.ELIGIBLE_REPRISE)
                                        && SERVICE_REPRISE_DEMANDE_ALLOCATION.equals(code))) {
            serviceBean.setCode(code);
            serviceBean.setLibelle(this.serviceLectureProperties.getLibelle(code));
            serviceBean.setUrl(this.serviceLectureProperties.getURL(code, contexte.getUrlsServices()));
        } else {
            serviceBean = null;
        }
        return serviceBean;
    }

    protected EligibiliteDalExpressEnum constuireEligibilite(final ContexteServices contexte) {
        // Appel D12DAL
        final EligibiliteDalExpress eligibiliteDalExpress = calculerEligibiliteDalExpress(contexte);
        EligibiliteDalExpressEnum eligibiliteDalExpressEnum = eligibiliteDalExpress.getEligibiliteDalExpressEnum();

        if (!eligibiliteDalExpress.isForcageEligibilite() && !eligibiliteDalExpressEnum.equals(EligibiliteDalExpressEnum.NON_ELIGIBILE)) {
            // Appel Cali
            if (!isEligibleCali(contexte)) {
                eligibiliteDalExpressEnum = EligibiliteDalExpressEnum.NON_ELIGIBILE;
            }
        }
        return eligibiliteDalExpressEnum;
    }

    protected EligibiliteDalExpress calculerEligibiliteDalExpress(final ContexteServices contexte) {
        final EligibiliteDalExpress eligibiliteDalExpress = new EligibiliteDalExpress();
        try {
            final RetourLireListeSyntheseDalWeb retourD12Dal = this.serviceD12Dal.lireListeSyntheseDalWeb(contexte.getDonneesIndividu());

            final Boolean topEligibleDalExpresse = retourD12Dal.getTopEligibleDalExpresse();
            Boolean topForcageEligibiliteDalExpresse = retourD12Dal.getTopForcageEligibiliteDalExpresse();
            if (topForcageEligibiliteDalExpresse == null) {
                topForcageEligibiliteDalExpresse = Boolean.FALSE;
            }
            eligibiliteDalExpress.setForcageEligibilite(topForcageEligibiliteDalExpresse);

            if (Boolean.TRUE.equals(topEligibleDalExpresse) || Boolean.TRUE.equals(topForcageEligibiliteDalExpresse)) {
                if (Boolean.TRUE.equals(retourD12Dal.getTopPresenceDalExpresseBrouillon())) {
                    eligibiliteDalExpress.setEligibiliteDalExpressEnum(EligibiliteDalExpressEnum.ELIGIBLE_REPRISE);
                } else {
                    eligibiliteDalExpress.setEligibiliteDalExpressEnum(EligibiliteDalExpressEnum.ELIGIBLE_CREATION);
                }
            }
        } catch (final Exception e) {
            LOGGER.error("Erreur lors de l'appel au service D12DAL", e);
        }
        return eligibiliteDalExpress;
    }

    protected boolean isEligibleCali(final ContexteServices contexte) {
        boolean eligibleCali = false;
        SortieLectureContexteExamen retourCali;

        try {
            retourCali = this.serviceCali.lectureContexteExamen(contexte.getDonneesIndividu());

            final Integer presenceDemandeAttenteDecisionInt = retourCali.getPresenceDemandeAttenteDecision();
            final Integer presenceReliquatAreValideInt = retourCali.getPresenceReliquatAreValide();
            final Integer estReliquatSpectacleInt = retourCali.getEstReliquatSpectacle();

            if ((presenceDemandeAttenteDecisionInt != null) || (presenceReliquatAreValideInt != null)) {
                final boolean presenceDemandeAttenteDecision = integerToBoolean(presenceDemandeAttenteDecisionInt);
                final boolean presenceReliquatAreValide = integerToBoolean(presenceReliquatAreValideInt);
                final boolean estReliquatSpectacle = integerToBoolean(estReliquatSpectacleInt);

                if (presenceDemandeAttenteDecision || (presenceReliquatAreValide && !estReliquatSpectacle)) {
                    eligibleCali = false;
                } else {
                    eligibleCali = true;
                }
            }
        } catch (final Exception e) {
            LOGGER.error("Erreur lors de l'appel au service CALI", e);
        }
        return eligibleCali;
    }

    private boolean integerToBoolean(final Integer value) {
        return (value != null) && value.equals(1);
    }
}
