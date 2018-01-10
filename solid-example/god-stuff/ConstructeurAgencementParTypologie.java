package fr.pe.gepo.exposition.ex017.services.agencement;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.commons.collections.CollectionUtils;

import fr.pe.gepo.exposition.ex017.beans.navigation.ElementBean;
import fr.pe.gepo.exposition.ex017.beans.navigation.LocaliteBean;
import fr.pe.gepo.exposition.ex017.beans.navigation.NavigationBean;
import fr.pe.gepo.exposition.ex017.beans.navigation.TypeElement;
import fr.pe.gepo.exposition.ex017.coordination.CoordinationService;
import fr.pe.gepo.exposition.ex017.data.enums.TypologieEnum;
import fr.pe.gepo.exposition.ex017.data.model.agencement.ReferenceAgencement;
import fr.pe.gepo.exposition.ex017.data.model.agencement.ReferenceBurger;
import fr.pe.gepo.exposition.ex017.data.model.agencement.ReferenceRegroupement;
import fr.pe.gepo.exposition.ex017.data.model.agencement.ReferenceService;
import fr.pe.gepo.exposition.ex017.data.model.agencement.ReferenceThematique;
import fr.pe.gepo.exposition.ex017.services.agencement.regroupements.FabriqueRegroupementsAgencement;
import fr.pe.gepo.exposition.ex017.services.agencement.thematiques.FabriqueThematiquesAgencement;
import fr.pe.gepo.referentiel.commun.beans.ContexteServices;
import fr.pe.gepo.referentiel.commun.beans.InfosServiceBean;
import fr.pe.gepo.referentiel.commun.services.ServiceReferentiel;
import fr.pe.gepo.referentiel.commun.services.pilotes.ServiceVerificationPilote;

@Singleton
public class ConstructeurAgencementParTypologie {

    // *************************************************
    // @Inject : Dépendances
    // *************************************************

    private final ServiceReferencesAgencements serviceReferencesAgencements;

    private final FabriqueThematiquesAgencement fabriqueThematiques;

    private final FabriqueRegroupementsAgencement fabriqueRegroupements;

    private final ServiceVerificationPilote servicePilote;

    private final ServiceReferentiel serviceReferentiel;

    private final CoordinationService coordinationService;

    @Inject
    public ConstructeurAgencementParTypologie(final FabriqueThematiquesAgencement fabriqueThematiques,
        final FabriqueRegroupementsAgencement fabriqueRegroupements, final ServiceVerificationPilote servicePilote,
        final ServiceReferencesAgencements serviceReferencesAgencements, final ServiceReferentiel serviceReferentiel,
        final CoordinationService coordinationService) {
        this.fabriqueThematiques = fabriqueThematiques;
        this.fabriqueRegroupements = fabriqueRegroupements;
        this.servicePilote = servicePilote;
        this.serviceReferencesAgencements = serviceReferencesAgencements;
        this.serviceReferentiel = serviceReferentiel;
        this.coordinationService = coordinationService;
    }

    // *************************************************
    // METHODES : Services / publiques
    // *************************************************

    public NavigationBean construireNavigation(final TypologieEnum typologie, final ContexteServices contexte) {
        // Recupere l'agencement de reference
        final ReferenceAgencement refAgencement = this.serviceReferencesAgencements.getReferenceAgencement(typologie);
        
        // Valorisation des urls proven
        contexte.setUrlsServices(this.serviceReferencesAgencements.getPropertiesProven());

        // Verifie les pilotes
        if (contexte.getEtatsPilotes() == null) {
            contexte.setEtatsPilotes(this.servicePilote
                            .retournerMapEtatsPilotesServices(contexte.getDonneesIndividu().getAuthentification().getCodePostal()));
        }

        // Construit la navigation
        final NavigationBean navigationBean = new NavigationBean();

        final List<ElementBean> burger = construireBurger(typologie, contexte, refAgencement);
        final ElementBean tooltip = construireTooltip(contexte, refAgencement);
        final ElementBean courriers = construireCourriers(contexte, refAgencement);
        final ElementBean faq = construireFAQ(contexte, refAgencement);
        final LocaliteBean localiteBean = new LocaliteBean();
        localiteBean.setCodeRegion(contexte.getDonneesIndividu().getCodeRegion());
        navigationBean.setLocalite(localiteBean);
        navigationBean.setBurger(burger);
        navigationBean.setTooltip(tooltip);
        navigationBean.setCourriers(courriers);
        navigationBean.setAssistanceTechniqueFAQ(faq);
        navigationBean.setLocalite(localiteBean);
        navigationBean.setTypo(refAgencement.getTypo());
        return navigationBean;
    }

    private ElementBean construireFAQ(final ContexteServices contexte, final ReferenceAgencement refAgencement) {
        ElementBean faq = null;

        if (refAgencement.getAssistanceTechniqueFAQ() != null) {
            final List<ElementBean> FAQServices = buildServicesFromReference(refAgencement.getAssistanceTechniqueFAQ(), contexte);
            faq = new ElementBean();
            faq.setType(TypeElement.FAQ);
            faq.setSousElements(FAQServices);
        }
        return faq;
    }

    private ElementBean construireCourriers(final ContexteServices contexte, final ReferenceAgencement refAgencement) {
        ElementBean courriers = null;

        if (refAgencement.getCourriers() != null) {
            final List<ElementBean> courriersServices = buildServicesFromReference(refAgencement.getCourriers(), contexte);
            courriers = new ElementBean();
            courriers.setType(TypeElement.COURRIERS);
            courriers.setSousElements(courriersServices);
        }
        return courriers;
    }

    private ElementBean construireTooltip(final ContexteServices contexte, final ReferenceAgencement refAgencement) {
        ElementBean tooltip = null;

        if (refAgencement.getTooltip() != null) {
            final List<ElementBean> tooltipServices = buildServicesFromReference(refAgencement.getTooltip(), contexte);
            tooltip = new ElementBean();
            tooltip.setType(TypeElement.TOOLTIP);
            tooltip.setSousElements(tooltipServices);
        }
        return tooltip;
    }

    private List<ElementBean> construireBurger(final TypologieEnum typologie, final ContexteServices contexte,
        final ReferenceAgencement refAgencement) {
        final List<ElementBean> burgerItems = new ArrayList<>();

        final ReferenceBurger burger = refAgencement.getBurger();
        if (burger != null) {
            // Construction des sous-éléments du burger
            final ElementBean espace = construireRegroupementEspace(contexte, burger);
            final List<ElementBean> thematiques = construireRegroupementThematiques(typologie, contexte, burger);
            final ElementBean accueil = construireRegroupementAccueil(contexte, burger);

            if (accueil != null) {
                burgerItems.add(accueil);
            }
            if (espace != null) {
                burgerItems.add(espace);
            }
            if (CollectionUtils.isNotEmpty(thematiques)) {
                burgerItems.addAll(thematiques);
            }
        }
        return burgerItems;
    }

    private ElementBean construireRegroupementAccueil(final ContexteServices contexte, final ReferenceBurger burger) {
        ElementBean accueil = null;

        final ReferenceRegroupement accueilRefAgencement = burger.getAccueil();
        if (accueilRefAgencement != null) {
            final List<ElementBean> accueilServices = buildServicesFromReference(accueilRefAgencement, contexte);
            accueil = new ElementBean();
            accueil.setLibelle(this.serviceReferencesAgencements.getLibelle(accueilRefAgencement));
            accueil.setType(TypeElement.ACCUEIL);
            accueil.setSousElements(accueilServices);
        }
        return accueil;
    }
    
    private ElementBean construireRegroupementEspace(final ContexteServices contexte, final ReferenceBurger burger) {
        ElementBean espace = null;
        
        final ReferenceRegroupement espaceRefAgencement = burger.getEspace();

        if (espaceRefAgencement != null) {
            final List<ElementBean> espaceServices = buildServicesFromReference(espaceRefAgencement, contexte);
            espace = new ElementBean();
            espace.setLibelle(this.serviceReferencesAgencements.getLibelle(espaceRefAgencement));
            espace.setType(TypeElement.ESPACE);
            espace.setSousElements(espaceServices);
        }
        return espace;
    }

    private List<ElementBean> construireRegroupementThematiques(final TypologieEnum typologie, final ContexteServices contexte,
        final ReferenceBurger burger) {
        final List<ElementBean> thematiques = new ArrayList<>();

        for (final ReferenceThematique refThematique : burger.getThematiques()) {
            final ElementBean thematique = this.fabriqueThematiques.fabriquer(refThematique, contexte, typologie);
            // Construction des regroupements associés à la thématique
            thematique.setSousElements(buildRegroupementsFromReference(refThematique, contexte));
            if (CollectionUtils.isNotEmpty(thematique.getSousElements())) {
                thematiques.add(thematique);
            }
        }
        return thematiques;
    }

    private List<ElementBean> buildRegroupementsFromReference(final ReferenceThematique refThematique, final ContexteServices contexte) {
        final List<ElementBean> regroupements = new ArrayList<>();

        for (final ReferenceRegroupement refRegroupement : refThematique.getRegroupements()) {
            final ElementBean regroupement = this.fabriqueRegroupements.fabriquer(refRegroupement, contexte);
            // Construction des services associés au regroupement
            regroupement.setSousElements(buildServicesFromReference(refRegroupement, contexte));

            if (CollectionUtils.isNotEmpty(regroupement.getSousElements())) {
                regroupements.add(regroupement);
            }
        }
        return regroupements;
    }

    private List<ElementBean> buildServicesFromReference(final ReferenceRegroupement refRegroupement, final ContexteServices contexte) {
        final List<ElementBean> services = new ArrayList<>();

        for (final ReferenceService refService : refRegroupement.getServices()) {
            refService.setCodeApplication(this.serviceReferencesAgencements.getCodeApplication(refService));
            refService.setCodeService(this.serviceReferencesAgencements.getCodeService(refService));

            if (estPiloteOuvert(refService, contexte)) {
                final InfosServiceBean retourServiceBean = this.serviceReferentiel.retournerServiceParCode(refService.getCode(), contexte);
                final ElementBean service = this.coordinationService.mapServiceBeanToElementBean(retourServiceBean, refService);
                if ((service != null) && !service.isEmpty())
                    services.add(service);
            }
        }
        return services;
    }

    private boolean estPiloteOuvert(final ReferenceService refService, final ContexteServices contexte) {
        if (contexte.getEtatsPilotes() != null) {
            final Boolean etatPilote = contexte.getEtatsPilotes().get(refService.getCodeApplication() + refService.getCodeService());
            if (etatPilote != null)
                return etatPilote;
        }
        return true;
    }
}
