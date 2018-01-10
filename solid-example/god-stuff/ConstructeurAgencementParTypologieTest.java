package fr.pe.gepo.exposition.ex017.services.agencement;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import edu.emory.mathcs.backport.java.util.Collections;
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
import fr.pe.gepo.referentiel.commun.beans.DonneesIndividuBean;
import fr.pe.gepo.referentiel.commun.beans.InfosServiceBean;
import fr.pe.gepo.referentiel.commun.services.ServiceReferentiel;
import fr.pe.gepo.referentiel.commun.services.pilotes.ServiceVerificationPilote;

@RunWith(MockitoJUnitRunner.class)
public class ConstructeurAgencementParTypologieTest {
    
    @InjectMocks
    private ConstructeurAgencementParTypologie constructeurAgencementParTypologie;
    
    @Mock
    private ServiceReferencesAgencements serviceReferencesAgencements;
    
    @Mock
    private FabriqueThematiquesAgencement fabriqueThematiques;
    
    @Mock
    private FabriqueRegroupementsAgencement fabriqueRegroupements;
    
    @Mock
    private ServiceVerificationPilote servicePilote;
    
    @Mock
    private ServiceReferentiel serviceReferentiel;
    
    @Spy
    @InjectMocks
    private final CoordinationService coordinationService = new CoordinationService();
    
    private ReferenceAgencement referenceAgencement;
    
    @Before
    public void setUp() {
        this.referenceAgencement = creerReferenceAgencement();
    }
    
    @Test
    public void construireNavigationPiloteOuvert() {
        final ElementBean dummyThematique = fabriquerDummyThematique();
        final ElementBean dummyRegroupement = fabriquerDummyRegroupement();
        final InfosServiceBean dummyService = fabriquerDummyService();
        final ContexteServices contexte = new ContexteServices(123456789L);
        contexte.setDonneesIndividu(new DonneesIndividuBean());
        contexte.getDonneesIndividu().setCodeRegion("60");
        when(this.serviceReferencesAgencements.getReferenceAgencement(any(TypologieEnum.class))).thenReturn(this.referenceAgencement);
        when(this.serviceReferencesAgencements.getCategorie(any(ReferenceService.class))).thenReturn("candidat");
        
        final ReferenceService refService1 = new ReferenceService();
        refService1.setCode("foo");
        refService1.setCodeApplication("code-appli-1");
        refService1.setCodeService("code-espaceService-1");
        
        final ReferenceService refService2 = new ReferenceService();
        refService2.setCode("bar");
        refService2.setCodeApplication("code-appli-2");
        refService2.setCodeService("code-espaceService-2");
        
        when(this.serviceReferencesAgencements.getCodeApplication(refService1)).thenReturn("code-appli-1");
        when(this.serviceReferencesAgencements.getCodeService(refService1)).thenReturn("code-espaceService-1");
        
        when(this.serviceReferencesAgencements.getCodeApplication(refService2)).thenReturn("code-appli-2");
        when(this.serviceReferencesAgencements.getCodeService(refService2)).thenReturn("code-espaceService-2");
        
        when(this.fabriqueThematiques.fabriquer(any(ReferenceThematique.class), any(ContexteServices.class), any(TypologieEnum.class)))
                        .thenReturn(dummyThematique);
        when(this.fabriqueRegroupements.fabriquer(any(ReferenceRegroupement.class), any(ContexteServices.class))).thenReturn(dummyRegroupement);
        when(this.serviceReferentiel.retournerServiceParCode(any(String.class), any(ContexteServices.class))).thenReturn(dummyService);
        
        final HashMap<String, Boolean> map = new HashMap<>();
        map.put("code-appli-1" + "code-espaceService-1", Boolean.TRUE);
        map.put("code-appli-2" + "code-espaceService-2", Boolean.TRUE);
        when(this.servicePilote.retournerMapEtatsPilotesServices(anyString())).thenReturn(map);
        
        final NavigationBean navigation = this.constructeurAgencementParTypologie.construireNavigation(TypologieEnum.C1, contexte);
        
        // Check Navigation
        assertThat(navigation).isNotNull();
        assertThat(navigation.getTooltip()).isNull();
        assertThat(navigation.getCourriers()).isNull();
        assertThat(navigation.getAssistanceTechniqueFAQ()).isNull();
        
        final LocaliteBean localite = navigation.getLocalite();
        assertThat(localite).isNotNull();
        assertThat(localite.getCodeRegion()).isEqualTo("60");
        
        // Check Burger
        final List<ElementBean> burger = navigation.getBurger();
        assertThat(burger).isNotNull();
        assertThat(burger).hasSize(3);
        
        // Test Burger Accueil
        final ElementBean accueil = burger.get(0);
        assertThat(accueil.getType()).isEqualTo(TypeElement.ACCUEIL);
        
        // Test Burger Espace
        final ElementBean espace = burger.get(1);
        assertThat(espace.getType()).isEqualTo(TypeElement.ESPACE);
        assertThat(espace.getSousElements()).hasSize(2);
        
        // Test Burger Espace Services
        final ElementBean espaceService = espace.getSousElements().get(0);
        assertThat(espaceService.getType()).isEqualTo(TypeElement.SERVICE);
        
        // Test Burger Thematiques
        final ElementBean thematiques = burger.get(2);
        assertThat(thematiques.getType()).isEqualTo(TypeElement.THEMATIQUE);
        assertThat(thematiques.getSousElements()).hasSize(1);
        
        // Test Burger Thematique Regroupement
        final ElementBean thematiqueRegroupement = thematiques.getSousElements().get(0);
        assertThat(thematiqueRegroupement.getType()).isEqualTo(TypeElement.REGROUPEMENT);
        
        // Test Burger Thematique Regroupement Service
        final List<ElementBean> regroupementServices = thematiqueRegroupement.getSousElements();
        assertThat(regroupementServices).hasSize(2);
        final ElementBean regroupementService = regroupementServices.get(0);
        assertThat(regroupementService.getType()).isEqualTo(TypeElement.SERVICE);
    }
    
    @Test
    public void construireNavigationPiloteOuvertDE1() {
        final ElementBean dummyThematique = fabriquerDummyThematique();
        final ElementBean dummyRegroupement = fabriquerDummyRegroupement();
        final InfosServiceBean dummyService = fabriquerDummyService();
        final ContexteServices contexte = new ContexteServices(123456789L);
        contexte.setDonneesIndividu(new DonneesIndividuBean());
        contexte.getDonneesIndividu().setCodeRegion("60");
        when(this.serviceReferencesAgencements.getReferenceAgencement(any(TypologieEnum.class)))
                        .thenReturn(creerReferenceAgencementAvecToolTip());
        when(this.serviceReferencesAgencements.getCategorie(any(ReferenceService.class))).thenReturn("candidat");
        
        final ReferenceService refService1 = new ReferenceService();
        refService1.setCode("foo");
        refService1.setCodeApplication("code-appli-1");
        refService1.setCodeService("code-espaceService-1");
        
        final ReferenceService refService2 = new ReferenceService();
        refService2.setCode("bar");
        refService2.setCodeApplication("code-appli-2");
        refService2.setCodeService("code-espaceService-2");
        
        when(this.serviceReferencesAgencements.getCodeApplication(refService1)).thenReturn("code-appli-1");
        when(this.serviceReferencesAgencements.getCodeService(refService1)).thenReturn("code-espaceService-1");
        
        when(this.serviceReferencesAgencements.getCodeApplication(refService2)).thenReturn("code-appli-2");
        when(this.serviceReferencesAgencements.getCodeService(refService2)).thenReturn("code-espaceService-2");
        
        when(this.fabriqueThematiques.fabriquer(any(ReferenceThematique.class), any(ContexteServices.class), any(TypologieEnum.class)))
                        .thenReturn(dummyThematique);
        when(this.fabriqueRegroupements.fabriquer(any(ReferenceRegroupement.class), any(ContexteServices.class))).thenReturn(dummyRegroupement);
        when(this.serviceReferentiel.retournerServiceParCode(any(String.class), any(ContexteServices.class))).thenReturn(dummyService);
        
        final HashMap<String, Boolean> map = new HashMap<>();
        map.put("code-appli-1" + "code-espaceService-1", Boolean.TRUE);
        map.put("code-appli-2" + "code-espaceService-2", Boolean.TRUE);
        when(this.servicePilote.retournerMapEtatsPilotesServices(anyString())).thenReturn(map);
        
        final NavigationBean navigation = this.constructeurAgencementParTypologie.construireNavigation(TypologieEnum.DE1, contexte);
        
        // Check Navigation
        assertThat(navigation).isNotNull();
        assertThat(navigation.getTooltip()).isNotNull();
        assertThat(navigation.getCourriers()).isNotNull();
        assertThat(navigation.getAssistanceTechniqueFAQ()).isNotNull();
        
        final LocaliteBean localite = navigation.getLocalite();
        assertThat(localite).isNotNull();
        assertThat(localite.getCodeRegion()).isEqualTo("60");
        
        // Check Burger
        final List<ElementBean> burger = navigation.getBurger();
        assertThat(burger).isNotNull();
        assertThat(burger).hasSize(3);
        
        // Test Burger Accueil
        final ElementBean accueil = burger.get(0);
        assertThat(accueil.getType()).isEqualTo(TypeElement.ACCUEIL);
        
        // Test Burger Espace
        final ElementBean espace = burger.get(1);
        assertThat(espace.getType()).isEqualTo(TypeElement.ESPACE);
        assertThat(espace.getSousElements()).hasSize(2);
        
        // Test Burger Espace Services
        final ElementBean espaceService = espace.getSousElements().get(0);
        assertThat(espaceService.getType()).isEqualTo(TypeElement.SERVICE);
        
        // Test Burger Thematiques
        final ElementBean thematiques = burger.get(2);
        assertThat(thematiques.getType()).isEqualTo(TypeElement.THEMATIQUE);
        assertThat(thematiques.getSousElements()).hasSize(1);
        
        // Test Burger Thematique Regroupement
        final ElementBean thematiqueRegroupement = thematiques.getSousElements().get(0);
        assertThat(thematiqueRegroupement.getType()).isEqualTo(TypeElement.REGROUPEMENT);
        
        // Test Burger Thematique Regroupement Service
        final List<ElementBean> regroupementServices = thematiqueRegroupement.getSousElements();
        assertThat(regroupementServices).hasSize(2);
        final ElementBean regroupementService = regroupementServices.get(0);
        assertThat(regroupementService.getType()).isEqualTo(TypeElement.SERVICE);
    }
    
    @Test
    public void construireNavigationPiloteFerme() {
        final ElementBean dummyThematique = fabriquerDummyThematique();
        final ElementBean dummyRegroupement = fabriquerDummyRegroupement();
        final InfosServiceBean dummyService = fabriquerDummyService();
        final ContexteServices contexte = new ContexteServices(123456789L);
        contexte.setDonneesIndividu(new DonneesIndividuBean());
        contexte.getDonneesIndividu().setCodeRegion("60");
        
        when(this.serviceReferencesAgencements.getReferenceAgencement(any(TypologieEnum.class))).thenReturn(this.referenceAgencement);
        when(this.serviceReferencesAgencements.getCategorie(any(ReferenceService.class))).thenReturn("candidat");
        
        final ReferenceService refService1 = new ReferenceService();
        refService1.setCode("foo");
        refService1.setCodeApplication("code-appli-1");
        refService1.setCodeService("code-espaceService-1");
        
        final ReferenceService refService2 = new ReferenceService();
        refService2.setCode("bar");
        refService2.setCodeApplication("code-appli-2");
        refService2.setCodeService("code-espaceService-2");
        
        when(this.serviceReferencesAgencements.getCodeApplication(refService1)).thenReturn("code-appli-1");
        when(this.serviceReferencesAgencements.getCodeService(refService1)).thenReturn("code-espaceService-1");
        
        when(this.serviceReferencesAgencements.getCodeApplication(refService2)).thenReturn("code-appli-2");
        when(this.serviceReferencesAgencements.getCodeService(refService2)).thenReturn("code-espaceService-2");
        
        when(this.fabriqueThematiques.fabriquer(any(ReferenceThematique.class), any(ContexteServices.class), any(TypologieEnum.class)))
                        .thenReturn(dummyThematique);
        when(this.fabriqueRegroupements.fabriquer(any(ReferenceRegroupement.class), any(ContexteServices.class))).thenReturn(dummyRegroupement);
        when(this.serviceReferentiel.retournerServiceParCode(any(String.class), any(ContexteServices.class))).thenReturn(dummyService);
        
        final HashMap<String, Boolean> map = new HashMap<>();
        map.put("code-appli-1" + "code-espaceService-1", Boolean.FALSE);
        map.put("code-appli-2" + "code-espaceService-2", Boolean.TRUE);
        when(this.servicePilote.retournerMapEtatsPilotesServices(anyString())).thenReturn(map);
        
        final NavigationBean navigation = this.constructeurAgencementParTypologie.construireNavigation(TypologieEnum.C1, contexte);
        
        // Check Navigation
        assertThat(navigation).isNotNull();
        assertThat(navigation.getTooltip()).isNull();
        assertThat(navigation.getCourriers()).isNull();
        assertThat(navigation.getAssistanceTechniqueFAQ()).isNull();
        
        final LocaliteBean localite = navigation.getLocalite();
        assertThat(localite).isNotNull();
        assertThat(localite.getCodeRegion()).isEqualTo("60");
        
        // Check Burger
        final List<ElementBean> burger = navigation.getBurger();
        assertThat(burger).isNotNull();
        assertThat(burger).hasSize(3);
        
        // Test Burger Accueil
        final ElementBean accueil = burger.get(0);
        assertThat(accueil.getType()).isEqualTo(TypeElement.ACCUEIL);
        
        // Test Burger Espace
        final ElementBean espace = burger.get(1);
        assertThat(espace.getType()).isEqualTo(TypeElement.ESPACE);
        assertThat(espace.getSousElements()).hasSize(1);
        
        // Test Burger Espace Services
        final ElementBean espaceService = espace.getSousElements().get(0);
        assertThat(espaceService.getType()).isEqualTo(TypeElement.SERVICE);
        
        // Test Burger Thematiques
        final ElementBean thematiques = burger.get(2);
        assertThat(thematiques.getType()).isEqualTo(TypeElement.THEMATIQUE);
        assertThat(thematiques.getSousElements()).hasSize(1);
        
        // Test Burger Thematique Regroupement
        final ElementBean thematiqueRegroupement = thematiques.getSousElements().get(0);
        assertThat(thematiqueRegroupement.getType()).isEqualTo(TypeElement.REGROUPEMENT);
        
        // Test Burger Thematique Regroupement Service
        final List<ElementBean> regroupementServices = thematiqueRegroupement.getSousElements();
        assertThat(regroupementServices).hasSize(1);
        final ElementBean regroupementService = regroupementServices.get(0);
        assertThat(regroupementService.getType()).isEqualTo(TypeElement.SERVICE);
    }
    
    private ReferenceAgencement creerReferenceAgencement() {
        final ReferenceService refService1 = new ReferenceService();
        refService1.setCode("foo");
        refService1.setCodeApplication("code-appli-1");
        refService1.setCodeService("code-service-1");
        
        final ReferenceService refService2 = new ReferenceService();
        refService2.setCode("bar");
        refService2.setCodeApplication("code-appli-2");
        refService2.setCodeService("code-service-2");
        
        final ArrayList<ReferenceService> services = new ArrayList<>();
        services.add(refService1);
        services.add(refService2);
        
        // Create Espace with 2 services
        final ReferenceRegroupement refEspace = new ReferenceRegroupement();
        refEspace.setServices(services);
        
        // Create Thematiques with 1 thematique having 2 services
        final ReferenceRegroupement refRegroupement1 = new ReferenceRegroupement();
        refRegroupement1.setServices(services);
        
        final ReferenceThematique refThematique1 = new ReferenceThematique();
        refThematique1.setRegroupements(Collections.singletonList(refRegroupement1));
        
        final List<ReferenceThematique> refThematiques = new ArrayList<>();
        refThematiques.add(refThematique1);
        
        // Create Accueil with NO services
        final ReferenceRegroupement refAccueil = new ReferenceRegroupement();
        refAccueil.setServices(new ArrayList<ReferenceService>());
        
        // Create Burger
        final ReferenceBurger refBurger = new ReferenceBurger();
        refBurger.setEspace(refEspace);
        refBurger.setThematiques(refThematiques);
        refBurger.setAccueil(refAccueil);
        
        final ReferenceAgencement refAgencement = new ReferenceAgencement();
        refAgencement.setBurger(refBurger);
        
        return refAgencement;
    }
    
    private ReferenceAgencement creerReferenceAgencementAvecToolTip() {
        final ReferenceService refService1 = new ReferenceService();
        refService1.setCode("foo");
        refService1.setCodeApplication("code-appli-1");
        refService1.setCodeService("code-service-1");
        
        final ReferenceService refService2 = new ReferenceService();
        refService2.setCode("bar");
        refService2.setCodeApplication("code-appli-2");
        refService2.setCodeService("code-service-2");
        
        final ArrayList<ReferenceService> services = new ArrayList<>();
        services.add(refService1);
        services.add(refService2);
        
        // Create Espace with 2 services
        final ReferenceRegroupement refEspace = new ReferenceRegroupement();
        refEspace.setServices(services);
        
        // Create Thematiques with 1 thematique having 2 services
        final ReferenceRegroupement refRegroupement1 = new ReferenceRegroupement();
        refRegroupement1.setServices(services);
        
        final ReferenceThematique refThematique1 = new ReferenceThematique();
        refThematique1.setRegroupements(Collections.singletonList(refRegroupement1));
        
        final List<ReferenceThematique> refThematiques = new ArrayList<>();
        refThematiques.add(refThematique1);
        
        // Create Accueil with NO services
        final ReferenceRegroupement refAccueil = new ReferenceRegroupement();
        refAccueil.setServices(new ArrayList<ReferenceService>());
        
        // Create Burger
        final ReferenceBurger refBurger = new ReferenceBurger();
        refBurger.setEspace(refEspace);
        refBurger.setThematiques(refThematiques);
        refBurger.setAccueil(refAccueil);
        
        final ReferenceAgencement refAgencement = new ReferenceAgencement();
        refAgencement.setBurger(refBurger);

        final ReferenceService refService1Tool = new ReferenceService();
        refService1Tool.setCode("footool");
        refService1Tool.setCodeApplication("code-appli-tool1");
        refService1Tool.setCodeService("code-service-tool1");
        
        final ReferenceService refService2Tool = new ReferenceService();
        refService2Tool.setCode("bartoo");
        refService2Tool.setCodeApplication("code-appli-tool2");
        refService2Tool.setCodeService("code-service-tool2");

        final ArrayList<ReferenceService> servicesTool = new ArrayList<>();
        servicesTool.add(refService1Tool);
        servicesTool.add(refService2Tool);
        final ReferenceRegroupement tooltip = new ReferenceRegroupement();
        tooltip.setServices(servicesTool);
        refAgencement.setTooltip(tooltip);
        
        final ReferenceRegroupement assistanceTechniqueFAQ = new ReferenceRegroupement();
        refAgencement.setAssistanceTechniqueFAQ(assistanceTechniqueFAQ);
        
        final ReferenceRegroupement courriers = new ReferenceRegroupement();
        refAgencement.setCourriers(courriers);
        
        return refAgencement;
    }
    
    private ElementBean fabriquerDummyThematique() {
        final ElementBean thematique = new ElementBean();
        thematique.setCode("CODE_THEMATIQUE");
        thematique.setLibelle("libellé thématique");
        thematique.setType(TypeElement.THEMATIQUE);
        
        final List<ElementBean> sousElements = new ArrayList<>();
        sousElements.add(new ElementBean());
        
        thematique.setSousElements(sousElements);
        return thematique;
    }
    
    private ElementBean fabriquerDummyRegroupement() {
        final ElementBean regroupement = new ElementBean();
        regroupement.setCode("CODE_REGROUPEMENT");
        regroupement.setLibelle("libellé regroupement");
        regroupement.setType(TypeElement.REGROUPEMENT);
        
        final List<ElementBean> sousElements = new ArrayList<>();
        sousElements.add(new ElementBean());
        
        regroupement.setSousElements(sousElements);
        return regroupement;
    }
    
    private InfosServiceBean fabriquerDummyService() {
        final InfosServiceBean service = new InfosServiceBean();
        service.setCode("CODE_SERVICE");
        service.setLibelle("libellé service");
        return service;
    }
}
