package fr.pe.gepo.referentiel.commun.services.fabriques;

import javax.inject.Inject;
import javax.inject.Singleton;

import fr.pe.gepo.referentiel.commun.beans.ContexteServices;
import fr.pe.gepo.referentiel.commun.beans.InfosServiceBean;

@Singleton
public class FabriqueServices extends AbstractServiceValorisationContexteServices implements InterfaceFabriqueService<String, InfosServiceBean> {

    // services spécifiques
    private final static String PE_REGION = "pe-region";
    private final static String DEPOT_ALLOCATION = "depot-demande-allocation";
    private final static String REPRISE_ALLOCATION = "reprise-demande-allocation";

    @Inject
    private ConstructeurServiceParDefaut constructeurParDefaut;

    @Inject
    private ConstructeurServiceAgencementPERegion constructeurPERegion;

    @Inject
    private ConstructeurServiceDALExpress constructeurServiceEligibilite;

    @Override
    public InfosServiceBean fabriquer(final String code, final ContexteServices contexte) {

        switch (code) {
            case PE_REGION:
                contexte.setDonneesIndividu(this.recupererDonneesIndividuD01DECEtDR7(contexte));
                return this.constructeurPERegion.construire(code, contexte);
            case DEPOT_ALLOCATION:
            case REPRISE_ALLOCATION:
                return this.constructeurServiceEligibilite.construire(code, contexte);
            default:
                return this.constructeurParDefaut.construire(code, contexte);
        }
    }

}