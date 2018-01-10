package fr.pe.gepo.referentiel.commun.services.fabriques;

import javax.inject.Inject;
import javax.inject.Singleton;

import fr.pe.gepo.referentiel.commun.beans.ContexteApplicatifBean;
import fr.pe.gepo.referentiel.commun.beans.InfosServiceBean;
import fr.pe.gepo.referentiel.commun.data.enums.ConstantesCodeService;

@Singleton
public class FabriqueServices implements InterfaceFabriqueService<String, InfosServiceBean> {

    @Inject
    private ConstructeurServiceParDefaut constructeurParDefaut;

    @Inject
    private ConstructeurServiceAgencementPERegion constructeurPERegion;

    @Inject
    private ConstructeurServiceDALExpress constructeurServiceEligibilite;

    @Override
    public InfosServiceBean fabriquer(final String codeService, final ContexteApplicatifBean contexte) {

        switch (codeService) {
            case ConstantesCodeService.PE_REGION:
                return this.constructeurPERegion.construire(codeService, contexte);
            case ConstantesCodeService.DEPOT_ALLOCATION:
            case ConstantesCodeService.REPRISE_ALLOCATION:
            case ConstantesCodeService.SUIVI_ALLOCATION:
                return this.constructeurServiceEligibilite.construire(codeService, contexte);
            default:
                return this.constructeurParDefaut.construire(codeService, contexte);
        }
    }

}