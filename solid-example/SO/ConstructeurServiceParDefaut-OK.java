package fr.pe.gepo.referentiel.commun.services.fabriques;

import javax.inject.Inject;
import javax.inject.Singleton;

import fr.pe.gepo.referentiel.commun.beans.ContexteServices;
import fr.pe.gepo.referentiel.commun.beans.InfosServiceBean;
import fr.pe.gepo.referentiel.commun.services.ServiceLectureProperties;

@Singleton
public class ConstructeurServiceParDefaut implements InterfaceConstructeurService<String, InfosServiceBean> {

    @Inject
    private ServiceLectureProperties serviceLectureProperties;

    @Override
    public InfosServiceBean construire(final String code, final ContexteServices contexte) {
        final InfosServiceBean serviceBean = new InfosServiceBean();
        serviceBean.setCode(code);
        serviceBean.setLibelle(this.serviceLectureProperties.getLibelle(code));
        serviceBean.setCategory(this.serviceLectureProperties.getCategory(code));
        serviceBean.setUrl(this.serviceLectureProperties.getURL(code, contexte.getUrlsServices()));
        return serviceBean;
    }

}
