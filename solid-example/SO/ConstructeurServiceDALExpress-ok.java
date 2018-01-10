package fr.pe.gepo.referentiel.commun.services.fabriques;

import fr.pe.gepo.referentiel.commun.beans.ContexteApplicatifBean;
import fr.pe.gepo.referentiel.commun.beans.DonneesAllocationsBean;
import fr.pe.gepo.referentiel.commun.beans.InfosServiceBean;
import fr.pe.gepo.referentiel.commun.data.enums.ConstantesCodeService;
import fr.pe.gepo.referentiel.commun.data.enums.EligibiliteDalExpressEnum;
import fr.pe.gepo.referentiel.commun.services.ServiceLectureProperties;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ConstructeurServiceDALExpress implements InterfaceConstructeurService<String, InfosServiceBean> {

    @Inject
    private ServiceLectureProperties serviceLectureProperties;

    /**
     * Retourne le {@link InfosServiceBean} correspondant au code service près vérification de l'éligibilité lors du premier appel. <br>
     * L'éligibilite est sauvegardée dans le contexte pour bypasser les appels par la suite.
     *
     * @param code     le code statut
     * @param contexte le contexte
     * @return {@link InfosServiceBean}
     */
    @Override
    public InfosServiceBean construire(final String code, final ContexteApplicatifBean contexte) {
        InfosServiceBean serviceBean = new InfosServiceBean();
        final DonneesAllocationsBean donneesAllocations = contexte.getDonneesAllocations();

        // Verif de l'eligibilite au service via le contexte
        if ((donneesAllocations.getEtatEligibilite().equals(EligibiliteDalExpressEnum.ELIGIBLE_CREATION)
                && ConstantesCodeService.DEPOT_ALLOCATION.equals(code))
                || (donneesAllocations.getEtatEligibilite().equals(EligibiliteDalExpressEnum.ELIGIBLE_REPRISE)
                && ConstantesCodeService.REPRISE_ALLOCATION.equals(code))

                || (donneesAllocations.getEtatEligibilite().equals(EligibiliteDalExpressEnum.NON_ELIGIBILE)
                && ConstantesCodeService.SUIVI_ALLOCATION.equals(code))) {
            serviceBean.setCode(code);
            serviceBean.setLibelle(this.serviceLectureProperties.getLibelle(code));
            serviceBean.setUrl(this.serviceLectureProperties.getURL(code, contexte.getUrlsServices()));
        }

        return serviceBean;
    }
}
